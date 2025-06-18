package com.wang.rpc.core.exchange.filter;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.invoker.Invoker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangjiabao
 */
public class FilterChain {
    
    private static final List<Filter> FILTERS;

    private static final Set<Invoker> HAD_BUILD_FILTER_CHAIN;
    
    static {
        FILTERS = new ArrayList<>();
        HAD_BUILD_FILTER_CHAIN = new HashSet<>();

        // TODO SPI load filter
    }

    /**
     * get filters
     * 
     * @return
     */
    public static List<Filter> getFilters() {
        return FILTERS;
    }


    /**
     * build filter chain for invoker
     *
     * @param invoker
     * @return
     */
    public static Invoker buildFilterChain(Invoker invoker) {
        if (HAD_BUILD_FILTER_CHAIN.contains(invoker)) {
            return invoker;
        }

        List<Filter> filters = getFilters();
        if (filters.isEmpty()) {
            return invoker;
        }

        Invoker nextInvoker = invoker;

        // build filter chain, filter1 -> filter2 - > filter3 - > invoker
        for (int i = filters.size() - 1; i >= 0; i--) {
            Filter filter = filters.get(i);

            final Invoker finalNextInvoker = nextInvoker;

            // wrap filter to invoker
            nextInvoker = new Invoker() {


                @Override
                public InvokeResult invoke(Invocation invocation) {
                    return filter.invoke(finalNextInvoker, invocation);
                }
            };
        }

        HAD_BUILD_FILTER_CHAIN.add(invoker);
        return nextInvoker;
    }
    
}

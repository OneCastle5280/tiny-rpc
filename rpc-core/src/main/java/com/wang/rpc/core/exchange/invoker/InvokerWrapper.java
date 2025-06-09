package com.wang.rpc.core.exchange.invoker;

import com.wang.rpc.core.exchange.filter.FilterChain;

/**
 *
 * invoker wrapper
 *
 * @author wangjiabao
 */
public class InvokerWrapper {

    private final Invoker invoker;

    public InvokerWrapper(Invoker invoker) {
        // build filter chain
        this.invoker = FilterChain.buildFilterChain(invoker);
    }

    /**
     * Return a proxy invoker if contains filters
     * @see com.wang.rpc.core.exchange.filter.Filter
     *
     * @return
     */
    public Invoker getInvoker() {
        return this.invoker;
    }
}

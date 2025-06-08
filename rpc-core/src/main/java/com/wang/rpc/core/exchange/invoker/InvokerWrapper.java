package com.wang.rpc.core.exchange.invoker;

import com.wang.rpc.core.exchange.filter.FilterChain;

/**
 *
 * invoker wrapper
 *
 * @author wangjiabao
 */
public class InvokerWrapper {

    private Invoker invoker;

    public InvokerWrapper(Invoker invoker) {
        // build filter chain
        this.invoker = FilterChain.buildFilterChain(invoker);
    }

    public Invoker getInvoker() {
        return this.invoker;
    }
}

package com.wang.rpc.core.exchange.filter;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.invoker.Invoker;

/**
 * @author wangjiabao
 */
public interface Filter {

    /**
     * invoke
     *
     * @param invoker
     * @param invocation
     * @return
     */
    InvokeResult invoke(Invoker invoker, Invocation invocation);
}

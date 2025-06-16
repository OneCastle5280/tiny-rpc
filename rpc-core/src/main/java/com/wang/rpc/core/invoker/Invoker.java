package com.wang.rpc.core.invoker;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;

/**
 * invoke
 *
 * @author wangjiabao
 */
public interface Invoker {

    /**
     * invoke
     *
     * @param invocation invocation info
     * @return
     */
    InvokeResult invoke(Invocation invocation);
}

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
     * @return service implement interfaces
     */
    Class<?>[] getInterfaces();

    /**
     * service invoke
     *
     * @param invocation invocation info
     * @return invoke result
     */
    InvokeResult invoke(Invocation invocation);
}

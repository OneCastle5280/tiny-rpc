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
     * service implement interface
     *
     * @return interface Class
     */
    Class<?> getInterface();

    /**
     * service version
     *
     * @return service version
     */
    String getVersion();

    /**
     * service invoke
     *
     * @param invocation invocation info
     * @return invoke result
     */
    InvokeResult invoke(Invocation invocation);
}

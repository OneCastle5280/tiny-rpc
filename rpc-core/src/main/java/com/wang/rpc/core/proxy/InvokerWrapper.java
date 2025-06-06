package com.wang.rpc.core.proxy;

import com.wang.rpc.core.proxy.domain.InvokeRequest;
import com.wang.rpc.core.proxy.domain.InvokeResult;

/**
 * invoker wrapper
 *
 * @author wangjiabao
 */
public interface InvokerWrapper {
    /**
     * generate invoker key
     *
     * @param request invoke request
     * @return unique key
     * @throws IllegalArgumentException if invoker key is exist, will throw IllegalArgumentException
     */
    String uniqueKey(InvokeRequest request) throws IllegalArgumentException;

    /**
     * invoke
     *
     * @param request invocation info
     * @return invoke result
     */
    InvokeResult invoke(InvokeRequest request);
}

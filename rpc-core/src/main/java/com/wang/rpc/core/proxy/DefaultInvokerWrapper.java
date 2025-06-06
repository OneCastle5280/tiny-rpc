package com.wang.rpc.core.proxy;

import com.wang.rpc.core.proxy.domain.InvokeRequest;

/**
 * default invoker wrapper
 *
 * @author wangjiabao
 */
public class DefaultInvokerWrapper extends AbstractInvokerWrapper {

    @Override
    public String uniqueKey(InvokeRequest request) throws IllegalArgumentException {
        return null;
    }
}

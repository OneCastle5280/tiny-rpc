package com.wang.rpc.core.exchange.service;

/**
 * @author wangjiabao
 */
public interface RpcService<T> {
    /**
     * service name
     */
    String serviceName();

    /**
     * method name
     */
    String methodName();

    /**
     * version
     */
    String version();

    /**
     * service instance
     */
    T serviceInstance();
}

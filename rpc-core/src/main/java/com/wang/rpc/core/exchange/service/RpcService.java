package com.wang.rpc.core.exchange.service;

import com.wang.rpc.core.exchange.domain.HandleResult;
import com.wang.rpc.core.exchange.domain.TinyRequest;

/**
 * @author wangjiabao
 */
public interface RpcService {
    /**
     * service name
     *
     * @return
     */
    String serviceName();

    /**
     * method name
     *
     * @return
     */
    String methodName();

    /**
     * version
     *
     * @return
     */
    String version();


}

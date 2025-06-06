package com.wang.rpc.core.proxy.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * invoke request
 *
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class InvokeRequest {
    /**
     * service name
     */
    private String serviceName;

    /**
     * method name
     */
    private String methodName;

    /**
     * method params type
     */
    private Class<?>[] paramsType;

    /**
     * method params
     */
    private Object[] params;

    /**
     * version
     */
    private String version;
}

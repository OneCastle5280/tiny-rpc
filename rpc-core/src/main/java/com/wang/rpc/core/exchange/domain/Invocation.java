package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * invocation info
 *
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class Invocation {
    /**
     * service name
     */
    private String serviceName;
    /**
     * method name
     */
    private String methodName;
    /**
     * version
     */
    private String version;
    /**
     * param types
     */
    private Class<?>[] paramTypes;
    /**
     * params
     */
    private Object[] params;
}

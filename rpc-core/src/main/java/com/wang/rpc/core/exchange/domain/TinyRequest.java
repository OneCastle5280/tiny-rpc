package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class TinyRequest {
    /**
     * request id
     */
    private String id;
    /**
     * interface name
     */
    private String interfaceName;
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

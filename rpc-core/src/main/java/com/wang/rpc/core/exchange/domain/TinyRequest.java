package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class TinyRequest {
    public TinyRequest() {

    }

    public TinyRequest(Long id) {
        this.id = id;
    }

    /**
     * request id
     */
    private Long id;
    /**
     * service name
     */
    private String serviceName;
    /**
     * method
     */
    private String method;
    /**
     * param types
     */
    private Class<?>[] paramTypes;
    /**
     * params
     */
    private Object[] params;
}

package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class TinyResponse {
    public static final Byte OK = 10;
    public static final Byte ERROR = 20;

    public TinyResponse(){}

    public TinyResponse(String id) {
        this.id = id;
    }

    /**
     * request id
     */
    private String id;

    /**
     * status
     */
    private Byte status;

    /**
     * result
     */
    private Object result;

    /**
     * err msg
     */
    private String errMsg;
}

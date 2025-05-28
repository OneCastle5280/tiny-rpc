package com.wang.rpc.core.exchange.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
public class TinyRpcResponse implements Serializable {
    private static final long serialVersionUID = -1L;
    /**
     * data
     */
    private Object data;
    /**
     * message
     */
    private String message;
}

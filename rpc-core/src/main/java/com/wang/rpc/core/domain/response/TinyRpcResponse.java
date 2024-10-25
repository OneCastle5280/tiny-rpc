package com.wang.rpc.core.domain.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
public class TinyRpcResponse implements Serializable {
    /**
     * data
     */
    private Object data;
    /**
     * message
     */
    private String message;
}

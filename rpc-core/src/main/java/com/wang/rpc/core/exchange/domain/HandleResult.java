package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class HandleResult {
    /**
     * handle result
     */
    private Object result;
    /**
     * exception
     */
    private Throwable exception;
    /**
     * err message
     */
    private String errMessage;
}

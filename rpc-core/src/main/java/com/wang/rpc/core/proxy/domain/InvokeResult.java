package com.wang.rpc.core.proxy.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * invoke result
 *
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class InvokeResult {
    /**
     * method invoke result
     */
    private Object result;
    /**
     * invoke exception
     */
    private Throwable exception;
}

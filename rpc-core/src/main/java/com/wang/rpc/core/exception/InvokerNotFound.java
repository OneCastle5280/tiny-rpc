package com.wang.rpc.core.exception;

/**
 * @author wangjiabao
 */
public class InvokerNotFound extends RuntimeException{

    public InvokerNotFound() {
        super();
    }

    public InvokerNotFound(String message) {
        super(message);
    }
}

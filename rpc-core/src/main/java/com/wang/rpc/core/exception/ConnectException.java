package com.wang.rpc.core.exception;

/**
 * @author wangjiabao
 */
public class ConnectException extends RuntimeException{

    public ConnectException() {
        super();
    }

    public ConnectException(String message) {
        super(message);
    }
}

package com.wang.rpc.core.exception;

/**
 * @author wangjiabao
 */
public class RpcServiceNotFound extends RuntimeException{

    public RpcServiceNotFound() {
        super();
    }

    public RpcServiceNotFound(String message) {
        super(message);
    }
}

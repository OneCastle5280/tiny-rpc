package com.wang.rpc.core.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * client rpc invocation handler
 *
 * @author wangjiabao
 */
public class RpcInvocationHandler implements InvocationHandler {

    private Object target;

    public RpcInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = method.invoke(target, args);
        return result;
    }
}

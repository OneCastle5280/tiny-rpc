package com.wang.rpc.core.proxy.jdk;

import com.wang.rpc.core.exchange.domain.Invocation;
import com.wang.rpc.core.exchange.domain.InvokeResult;
import com.wang.rpc.core.invoker.Invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * jdk invocation handler
 *
 * @author wangjiabao
 */
public class JdkInvocationHandler implements InvocationHandler {

    /**
     * proxy target invoker
     */
    private final Invoker invoker;

    public JdkInvocationHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // todo skip special method, eg hashCode()、equals()
        String methodName = method.getName();
        Invocation invocation = new Invocation();
        invocation.setServiceName();
        invocation.setMethodName(methodName);
        invocation.setParams(args);
        invocation.setParamTypes()
        InvokeResult result = this.invoker.invoke(invocation);
        return result;
    }
}

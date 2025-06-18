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

    public static final String TO_STRING = "toString";
    public static final String HASH_CODE = "hashCode";
    public static final String EQUALS = "equals";
    /**
     * proxy target invoker
     */
    private final Invoker invoker;

    public JdkInvocationHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            // object method skip
            return method.invoke(this.invoker, args);
        }
        // skip special method, eg hashCode()、equals()
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            if (TO_STRING.equals(methodName)) {
                return this.invoker.toString();
            } else if (HASH_CODE.equals(methodName)) {
                return this.invoker.hashCode();
            }
        } else if (parameterTypes.length == 1 && EQUALS.equals(methodName)) {
            return this.invoker.equals(args[0]);
        }
        // rpc invoke
        Invocation invocation = new Invocation(
                this.invoker.getInterface().getName(),
                methodName,
                this.invoker.getVersion(),
                parameterTypes,
                args
        );

        // actual invoke
        InvokeResult result = this.invoker.invoke(invocation);
        return result;
    }
}

package com.wang.rpc.core.proxy.jdk;

import java.lang.reflect.Proxy;

/**
 * jdk proxy factory
 *
 * @author wangjiabao
 */
public class JdkProxyFactory {

    /**
     * get proxy instance
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Object getProxyInstance(T t) {
        return Proxy.newProxyInstance(
                t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                new RpcInvocationHandler(t)
        );

    }
}

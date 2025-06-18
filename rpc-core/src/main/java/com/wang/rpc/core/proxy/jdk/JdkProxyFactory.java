package com.wang.rpc.core.proxy.jdk;

import com.wang.rpc.core.invoker.Invoker;
import com.wang.rpc.core.proxy.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * jdk reflect proxy
 *
 * @author wangjiabao
 */
public class JdkProxyFactory implements ProxyFactory {

    @Override
    public <T> T getProxy(Invoker invoker) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{invoker.getInterface()}, new JdkInvocationHandler(invoker));
    }
}

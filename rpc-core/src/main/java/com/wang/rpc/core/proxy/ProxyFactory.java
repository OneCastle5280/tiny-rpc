package com.wang.rpc.core.proxy;

import com.wang.rpc.core.invoker.Invoker;

/**
 * @author wangjiabao
 */
public interface ProxyFactory {

    /**
     * acquire invoker proxy instance
     *
     * @param invoker
     * @param <T>
     * @return
     */
    <T> T getProxy(Invoker invoker);
}

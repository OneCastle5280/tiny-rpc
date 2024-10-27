package com.wang.rpc.core.register;

import com.wang.rpc.core.domain.RpcProvider;

/**
 * rpc 注册接口
 * 1. 注册
 * 2. 取消注册
 *
 * @author wangjiabao
 */
public interface RpcRegister {

    /**
     * 注册服务
     *
     * @param provider
     * @throws Exception
     */
    void register(RpcProvider provider) throws Exception;

    /**
     * 下线服务
     *
     * @param provider
     * @throws Exception
     */
    void unRegister(RpcProvider provider) throws Exception;

    /**
     * 服务关闭
     *
     * @throws Exception
     */
    void destroy() throws Exception;
}

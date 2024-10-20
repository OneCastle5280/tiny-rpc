package com.wang.rpc.core.discovery;

import com.wang.rpc.core.domain.RpcProvider;

/**
 * 服务发现
 *
 * @author wangjiabao
 */
public interface RpcDiscovery {

    /**
     * 发现服务
     *
     * @param serviceName
     * @return
     * @throws Exception
     */
    RpcProvider discovery(String serviceName) throws Exception;
}

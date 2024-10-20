package com.wang.rpc.core.balancer;

import com.wang.rpc.core.domain.RpcProvider;

import java.util.List;

/**
 * 负载均衡接口
 *
 * @author wangjiabao
 */
public interface RcpLoadBalance {

    /**
     * 从 providers 返回其中一个 providers
     *
     * @param providers
     * @return
     */
    RpcProvider get(List<RpcProvider> providers);
}

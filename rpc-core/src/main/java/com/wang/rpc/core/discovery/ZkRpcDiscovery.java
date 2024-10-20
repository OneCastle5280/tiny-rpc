package com.wang.rpc.core.discovery;

import com.wang.rpc.core.balancer.RcpLoadBalance;
import com.wang.rpc.core.constants.RpcConstants;
import com.wang.rpc.core.domain.RpcProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 使用 zk 作为服务发现
 *
 * @author wangjiabao
 */
@Slf4j
public class ZkRpcDiscovery implements RpcDiscovery {

    private ServiceDiscovery<RpcProvider> serviceDiscovery;

    private RcpLoadBalance rcpLoadBalance;

    public ZkRpcDiscovery(String registerAddress, RcpLoadBalance balance) {
        try {
            // 均衡策略
            this.rcpLoadBalance = balance;
            // zk client
            CuratorFramework client = CuratorFrameworkFactory.newClient(registerAddress, new ExponentialBackoffRetry(1000, 3));
            client.start();

            // 构建服务发现实例
            InstanceSerializer<RpcProvider> serializer = new JsonInstanceSerializer<>(RpcProvider.class);
            this.serviceDiscovery = ServiceDiscoveryBuilder.builder(RpcProvider.class)
                    .client(client)
                    .basePath(RpcConstants.BASE_PATH)
                    .serializer(serializer)
                    .build();
            this.serviceDiscovery.start();
        } catch (Exception e) {
            log.error("[ZkRpcDiscovery] exception",e);
        }
    }

    @Override
    public RpcProvider discovery(String serviceName) throws Exception {
        Collection<ServiceInstance<RpcProvider>> serviceInstances = this.serviceDiscovery.queryForInstances(serviceName);
        if (serviceInstances == null || serviceInstances.size() == 0) {
            return null;
        }
        List<RpcProvider> rpcProvider = serviceInstances.stream().map(ServiceInstance::getPayload).collect(Collectors.toList());
        // 负载均衡
        return this.rcpLoadBalance.get(rpcProvider);
    }
}

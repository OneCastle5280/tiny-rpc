package com.wang.rpc.core.register;

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

/**
 * ZK 作为服务注册中心
 *
 * @author wangjiabao
 */
@Slf4j
public class ZkRpcRegister implements RpcRegister{

    private ServiceDiscovery<RpcProvider> serviceDiscovery;

    /**
     * 构造 zk 注册中心
     *
     * @param registerAddress zk 注册地址
     */
    public ZkRpcRegister(String registerAddress) {
        CuratorFramework client = null;
        try {
            // zk client
            client = CuratorFrameworkFactory.newClient(registerAddress, new ExponentialBackoffRetry(1000, 3));
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
            log.error("[ZkRpcRegister] exception", e);
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Override
    public void register(RpcProvider provider) throws Exception {
        // 注册 rpc 接口
        ServiceInstance<RpcProvider> instance = ServiceInstance.<RpcProvider>builder()
                .name(provider.getServiceName())
                .address(provider.getAddress())
                .port(provider.getPort())
                .payload(provider)
                .build();
        this.serviceDiscovery.registerService(instance);
    }

    @Override
    public void unRegister(RpcProvider provider) throws Exception {
        // 下线 rpc 接口
        ServiceInstance<RpcProvider> instance = ServiceInstance.<RpcProvider>builder()
                .name(provider.getServiceName())
                .address(provider.getAddress())
                .port(provider.getPort())
                .payload(provider)
                .build();
        this.serviceDiscovery.unregisterService(instance);
    }

    @Override
    public void destroy() throws Exception {
        this.serviceDiscovery.close();
    }
}

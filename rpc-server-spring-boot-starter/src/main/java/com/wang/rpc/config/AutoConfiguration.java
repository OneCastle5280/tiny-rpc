package com.wang.rpc.config;


import com.wang.rpc.RpcServer;
import com.wang.rpc.core.register.RpcRegister;
import com.wang.rpc.core.register.ZkRpcRegister;
import com.wang.rpc.netty.NettyTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author wangjiabao
 */
@Configurable
@EnableConfigurationProperties(ServiceProperties.class)
public class AutoConfiguration {
    @Autowired
    private ServiceProperties serviceProperties;

    @Bean
    @ConditionalOnMissingBean
    public RpcRegister rpcRegister() {
        return new ZkRpcRegister(this.serviceProperties.getRegisterAddress());
    }

    @Bean
    @ConditionalOnMissingBean(NettyTransport.class)
    public NettyTransport nettyTransport() {
        return new NettyTransport();
    }

    @Bean
    @ConditionalOnMissingBean(RpcServer.class)
    public RpcServer rpcServer(RpcRegister register, NettyTransport nettyTransport, ServiceProperties properties) {
        return new RpcServer(register, properties, nettyTransport);
    }

}

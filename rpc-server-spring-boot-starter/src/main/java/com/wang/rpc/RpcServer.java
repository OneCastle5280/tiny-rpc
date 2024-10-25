package com.wang.rpc;

import com.wang.rpc.annotation.TinyRpcService;
import com.wang.rpc.config.ServiceProperties;
import com.wang.rpc.core.domain.RpcProvider;
import com.wang.rpc.core.register.RpcRegister;
import com.wang.rpc.netty.NettyTransport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.CommandLineRunner;

import java.net.InetAddress;

/**
 * 扫描 tinyRpcService && 启动服务
 *
 * @author wangjiabao
 */
@Slf4j
@AllArgsConstructor
public class RpcServer implements BeanPostProcessor, CommandLineRunner {

    private RpcRegister rpcRegister;
    private ServiceProperties serviceProperties;
    private NettyTransport nettyTransport;


    /**
     * 将使用了 {@link com.wang.rpc.annotation.TinyRpcService} 注解的 Bean 发布到注册中心
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            TinyRpcService rpcService = bean.getClass().getAnnotation(TinyRpcService.class);
            if (rpcService != null) {
                String serviceName = rpcService.interfaceClass().getName();
                String version = rpcService.version();

                RpcProvider rpcProvider = new RpcProvider();
                rpcProvider.setAppName(this.serviceProperties.getAppName());
                rpcProvider.setServiceName(serviceName + "-" + version);
                rpcProvider.setVersion(version);
                rpcProvider.setAddress(InetAddress.getLocalHost().getHostAddress());
                rpcProvider.setPort(this.serviceProperties.getPort());

                // 将 service 发布到注册中心
                rpcRegister.register(rpcProvider);
            }
        } catch (Exception e) {
            log.error("{} register fail", beanName, e);
        }
        return bean;
    }

    /**
     * 启动 rpc 服务
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> this.nettyTransport.start()).start();

    }
}

package com.wang.rpc.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wangjiabao
 */
@Data
@Component
@ConfigurationProperties(prefix = "tiny.rpc.server")
public class ServiceProperties {

    /**
     * tiny.rpc.server 启动端口
     */
    private Integer port = 8088;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 注册中心地址: ip + port
     */
    private String registerAddress = "127.0.0.1:2181";
}

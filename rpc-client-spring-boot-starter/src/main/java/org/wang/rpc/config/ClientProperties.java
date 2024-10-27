package org.wang.rpc.config;

/**
 * @author wangjiabao
 */
public class ClientProperties {

    /**
     *  序列化
     */
    private String serialization;

    /**
     *  服务发现地址
     */
    private String discoveryAddr = "127.0.0.1:2181";

    /**
     *  服务调用超时
     */
    private Integer timeout;
}

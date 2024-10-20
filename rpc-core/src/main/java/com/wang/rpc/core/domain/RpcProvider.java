package com.wang.rpc.core.domain;

import lombok.Data;

/**
 * rpc provider 相关信息
 *
 * @author wangjiabao
 */
@Data
public class RpcProvider {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * provider 名称
     */
    private String name;
    /**
     * 版本
     */
    private String version;
    /**
     * 地址
     */
    private String address;
    /**
     * 端口
     */
    private Integer port;
}

package com.wang.rpc.core.transport;

/**
 * @author wangjiabao
 */
public interface Transport {
    /**
     * 启动服务
     * @param port 端口
     */
    void start(int port);
}

package com.wang.rpc.core.exchange;

import com.wang.rpc.core.Client;
import com.wang.rpc.core.Server;
import com.wang.rpc.core.transport.netty.NettyTransport;

import java.net.SocketAddress;

/**
 * TODO 通过 SPI 能力获取 transport
 * @author wangjiabao
 */
public class ServerExchanger {

    public Server bind(int port) {
        return new NettyTransport().bind(port);
    }
}

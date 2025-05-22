package com.wang.rpc.core.exchange;

import com.wang.rpc.core.Client;
import com.wang.rpc.core.transport.netty.NettyTransport;

import java.net.SocketAddress;

/**
 * TODO spi
 *
 * @author wangjiabao
 */
public class ClientExchanger {

    public Client connect(SocketAddress socketAddress) {
        return new NettyTransport().connect(socketAddress);
    }
}

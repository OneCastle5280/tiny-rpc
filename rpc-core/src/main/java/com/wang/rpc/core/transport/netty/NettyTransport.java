package com.wang.rpc.core.transport.netty;

import com.wang.rpc.core.Client;
import com.wang.rpc.core.Server;
import com.wang.rpc.core.transport.Transport;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;


/**
 * holder netty server, default transport
 *
 * @author wangjiabao
 */
@Slf4j
public class NettyTransport implements Transport {

    @Override
    public Server bind(int port) {
        return new NettyServer(port).bind();
    }

    @Override
    public Client connect(SocketAddress socketAddress) {
        return new NettyClient(socketAddress).connect();
    }
}

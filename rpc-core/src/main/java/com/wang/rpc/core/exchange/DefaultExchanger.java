package com.wang.rpc.core.exchange;

import com.wang.rpc.core.Client;
import com.wang.rpc.core.Server;

import java.net.SocketAddress;

/**
 * default exchanger
 *
 * @author wangjiabao
 */
public class DefaultExchanger implements Exchange{

    @Override
    public Server bind(int port) {
        return new ServerExchanger().bind(port);
    }

    @Override
    public Client connect(SocketAddress socketAddress) {
        return new ClientExchanger().connect(socketAddress);
    }
}

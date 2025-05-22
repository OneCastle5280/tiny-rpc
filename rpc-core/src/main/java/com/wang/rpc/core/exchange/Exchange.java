package com.wang.rpc.core.exchange;

import com.wang.rpc.core.Client;
import com.wang.rpc.core.Server;

import java.net.SocketAddress;

/**
 * @author wangjiabao
 */
public interface Exchange{

    /**
     * bind a server
     *
     * @param port
     * @return
     */
    Server bind(int port);

    /**
     * connect a server
     *
     * @param socketAddress
     * @return
     */
    Client connect(SocketAddress socketAddress);
}

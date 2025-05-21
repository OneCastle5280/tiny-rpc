package com.wang.rpc.core;

import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageProtocol;
import com.wang.rpc.core.transport.ClientTransport;
import com.wang.rpc.core.transport.ServerTransport;
import io.netty.channel.local.LocalAddress;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author wangjiabao
 */
public class TransportTest {

    @Test
    public void testTransport() {
        String hostname = "127.0.0.1";
        int port = 8099;

        // start server
        ServerTransport serverTransport = new ServerTransport(port);
        serverTransport.start(true);

        // start client
        ClientTransport clientTransport = new ClientTransport(new InetSocketAddress(hostname, port));
        clientTransport.start(true);

        // close client
        clientTransport.close(true);

        // close server
        serverTransport.close(true);
    }
}

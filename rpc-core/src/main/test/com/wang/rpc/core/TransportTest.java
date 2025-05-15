package com.wang.rpc.core;

import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageProtocol;
import com.wang.rpc.core.transport.ClientTransport;
import com.wang.rpc.core.transport.ServerTransport;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author wangjiabao
 */
public class TransportTest {

    @Test
    public void testTransport() {
        new ServerTransport(8099).start();
    }
}

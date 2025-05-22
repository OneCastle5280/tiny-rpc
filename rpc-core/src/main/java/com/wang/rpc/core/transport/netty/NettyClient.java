package com.wang.rpc.core.transport.netty;

import com.wang.rpc.core.codecs.RpcDecoder;
import com.wang.rpc.core.codecs.RpcEncoder;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.handler.RpcResponseHandler;
import com.wang.rpc.core.pool.NettyChannelPool;
import com.wang.rpc.core.protocol.MessageProtocol;
import com.wang.rpc.core.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * @author wangjiabao
 */
public class NettyClient implements Client {

    private NettyChannelPool nettyChannelPool;
    private final Bootstrap bootstrap;
    private SocketAddress socketAddress;

    public NettyClient(SocketAddress socketAddress) {
        this.bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        this.bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                /*
                                  1. 入站：解码，将 字节流 转化成 POJO
                                  2. 出战：编码，将 POJO 转化成 字节流
                                 */
                                .addLast(new RpcDecoder())
                                .addLast(new RpcResponseHandler())
                                .addLast(new RpcEncoder<MessageProtocol<TinyRpcRequest>>())
                        ;
                    }
                });
    }

    public NettyClient connect() {
        return this;
    }

}

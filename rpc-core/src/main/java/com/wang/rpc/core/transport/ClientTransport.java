package com.wang.rpc.core.transport;

import com.wang.rpc.core.codecs.RpcDecoder;
import com.wang.rpc.core.codecs.RpcEncoder;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.handler.RpcResponseHandler;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 借助 netty 实现客户端与服务端的数据传输
 *
 * @author wangjiabao
 */
public class ClientTransport implements Transport{

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public ClientTransport() {
        this.bootstrap = new Bootstrap();
        this.eventLoopGroup = new NioEventLoopGroup();

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

    @Override
    public void start(int port) {

    }
}

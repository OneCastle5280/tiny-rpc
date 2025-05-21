package com.wang.rpc.core.transport;

import com.wang.rpc.core.codecs.RpcDecoder;
import com.wang.rpc.core.codecs.RpcEncoder;
import com.wang.rpc.core.domain.pool.PooledChannel;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.handler.RpcResponseHandler;
import com.wang.rpc.core.pool.NettyChannelPool;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.SocketAddress;

/**
 * client transport
 *
 * @author wangjiabao
 */
public class ClientTransport implements Transport{

    private NettyChannelPool nettyChannelPool;
    private final Bootstrap bootstrap;
    private SocketAddress socketAddress;

    public ClientTransport(SocketAddress socketAddress) {
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


    @Override
    public void start(boolean sync) {
        // TODO 2、10 dynamic config
        nettyChannelPool = new NettyChannelPool(bootstrap, socketAddress, 10, 2);
    }

    @Override
    public void close(boolean sync) {
        // close channel pool
        this.nettyChannelPool.close();
    }
}

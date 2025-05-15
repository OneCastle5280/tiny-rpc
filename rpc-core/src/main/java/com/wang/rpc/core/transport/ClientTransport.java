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
 * 借助 netty 实现客户端与服务端的数据传输
 *
 * @author wangjiabao
 */
public class ClientTransport<T> {

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


    /**
     * start client transport
     */
    public void start() {
        // TODO 10, 2 config dynamic
        this.nettyChannelPool = new NettyChannelPool(this.bootstrap, socketAddress, 10, 2);
    }

    /**
     * send message to server
     *
     * @param messageProtocol
     * @param flush
     * @throws Exception
     */
    public void send(MessageProtocol<T> messageProtocol, boolean flush) throws Exception {
        PooledChannel pooledChannel = this.nettyChannelPool.borrowChannel();
        Channel channel = pooledChannel.getChannel();
        if (flush) {
            channel.writeAndFlush(messageProtocol);
        } else {
            channel.write(messageProtocol);
        }
    }

    /**
     * send message to server, default writeAndFlush()
     *
     * @param messageProtocol
     * @throws Exception
     */
    public void send(MessageProtocol<T> messageProtocol) throws Exception {
        this.send(messageProtocol, true);
    }

}

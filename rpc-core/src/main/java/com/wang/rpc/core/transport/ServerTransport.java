package com.wang.rpc.core.transport;

import com.wang.rpc.core.codecs.RpcDecoder;
import com.wang.rpc.core.codecs.RpcEncoder;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.handler.RpcRequestHandler;
import com.wang.rpc.core.protocol.MessageProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;


/**
 * @author wangjiabao
 */
@Slf4j
public class ServerTransport {

    private final ServerBootstrap serverBootstrap;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;
    private final Integer port;
    private ChannelFuture future;

    public ServerTransport(int port) {
        // TODO 1,4 dynamic config
        this.bossGroup = new NioEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(4);
        this.port = port;

        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                /*
                                  1. 入站：解码，将 字节流 转化成 POJO
                                  2. 出战：编码，将 POJO 转化成 字节流
                                 */
                                .addLast(new RpcDecoder())
                                .addLast(new RpcRequestHandler())
                                .addLast(new RpcEncoder<MessageProtocol<TinyRpcResponse>>());

                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    /**
     * start server transport
     */
    public void start() {
        try {
            future = this.serverBootstrap.bind(port).sync();
            if (future.isSuccess()) {
                log.info("server start on {}", port);
            }
            future.channel().closeFuture().addListener(f -> {
                if (f.isSuccess()) {
                    log.info("server close success");
                }
            }).sync();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            this.bossGroup.shutdownGracefully();
            this.workerGroup.shutdownGracefully();
        }
    }

    public void close() {
        if (future != null) {
            future.channel().close();
        }
    }

    public static void main(String[] args) {
        ServerTransport transport = new ServerTransport(8099);
        transport.start();
        transport.close();
    }
}

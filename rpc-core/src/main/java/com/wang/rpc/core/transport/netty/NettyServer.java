package com.wang.rpc.core.transport.netty;

import com.wang.rpc.core.Server;
import com.wang.rpc.core.transport.netty.codec.CodecHolder;
import com.wang.rpc.core.transport.netty.handler.NettyHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * netty server
 *
 * @author wangjiabao
 */
@Slf4j
public class NettyServer implements Server {
    /**
     * handle connect
     */
    private static final Integer DEFAULT_BOSS_GROUP_THREAD_NUM = 1;
    /**
     * handle read/write, cpu * 2
     */
    private static final Integer DEFAULT_WORKER_GROUP_THREAD_NUM = Math.min(Runtime.getRuntime().availableProcessors()*2, 32);

    private final ServerBootstrap serverBootstrap;
    private final NioEventLoopGroup bossGroup;
    private final NioEventLoopGroup workerGroup;
    private ChannelFuture future;
    private final Integer port;

    public NettyServer(int port) {
        this.bossGroup = new NioEventLoopGroup(DEFAULT_BOSS_GROUP_THREAD_NUM);
        this.workerGroup = new NioEventLoopGroup(DEFAULT_WORKER_GROUP_THREAD_NUM);
        this.port = port;

        // TODO codec SPI
        CodecHolder codecHolder = new CodecHolder(null);

        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(codecHolder.getDecoder())
                                .addLast(codecHolder.getEncoder())
                                .addLast(new NettyHandler());

                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);
    }

    /**
     * bind server
     */
    public NettyServer bind() {
        // async
        future = this.serverBootstrap.bind(this.port).addListener(f -> {
            if (f.isSuccess()) {
                log.info("[NettyTransport] bind success on {}", port);
            }
        });
        return this;
    }

    /**
     * close server
     */
    public void close(boolean sync) {
        future = this.future.channel().close();
        if (sync) {
            try {
                future = future.sync();
                if (future.isSuccess()) {
                    log.info("[NettyTransport] close success on {}", port);
                }
            } catch (InterruptedException e) {
                log.error("[NettyTransport] InterruptedException when close server");
                Thread.currentThread().interrupt();
            }
        }
        this.workerGroup.shutdownGracefully();
        this.bossGroup.shutdownGracefully();
    }

    @Override
    public boolean isBound() {
        return this.future.isSuccess();
    }
}

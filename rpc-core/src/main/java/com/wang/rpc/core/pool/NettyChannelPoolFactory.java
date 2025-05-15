package com.wang.rpc.core.pool;

import com.wang.rpc.core.domain.pool.PooledChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.DestroyMode;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.ConnectException;
import java.net.SocketAddress;


/**
 * netty channel pool factory
 *
 * @author wangjiabao
 */
@Slf4j
public class NettyChannelPoolFactory extends BasePooledObjectFactory<PooledChannel> {

    private final Bootstrap bootstrap;
    private final SocketAddress socketAddress;

    public NettyChannelPoolFactory(Bootstrap bootstrap, SocketAddress socketAddress) {
        this.bootstrap = bootstrap;
        this.socketAddress = socketAddress;
    }

    @Override
    public PooledChannel create() throws Exception {
        ChannelFuture channelFuture = null;
        try {
            channelFuture = this.bootstrap.connect(socketAddress).sync();
            if (channelFuture.isSuccess()) {
                // connect success
                return new PooledChannel(channelFuture.channel(), socketAddress);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (channelFuture != null) {
                channelFuture.channel().eventLoop().shutdownGracefully();
            }
        }
        throw new ConnectException("connect exception");
    }

    @Override
    public PooledObject<PooledChannel> wrap(PooledChannel obj) {
        return new DefaultPooledObject<>(obj);
    }

    @Override
    public boolean validateObject(PooledObject<PooledChannel> p) {
        Channel channel = p.getObject().getChannel();
        return channel != null && channel.isActive();
    }

    @Override
    public void destroyObject(PooledObject<PooledChannel> p, DestroyMode destroyMode) throws Exception {
        Channel channel = p.getObject().getChannel();
        if (channel != null && channel.isActive()) {
            channel.close();
        }
    }
}

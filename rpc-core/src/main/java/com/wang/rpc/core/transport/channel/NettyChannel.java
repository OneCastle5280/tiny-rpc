package com.wang.rpc.core.transport.channel;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class NettyChannel implements TinyChannel {

    private final Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * netty ==> NettyChannel
     */
    public static final Map<Channel, NettyChannel> CHANNEL_MAP = new ConcurrentHashMap<>();


    public static NettyChannel getOrAddChannel(Channel ch) {
        return CHANNEL_MAP.putIfAbsent(ch, new NettyChannel(ch));
    }

    @Override
    public boolean isActive() {
        return this.channel.isActive();
    }

    @Override
    public void send(Object msg) throws RuntimeException{
        this.channel.writeAndFlush(msg);
    }
}

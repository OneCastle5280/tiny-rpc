package com.wang.rpc.core.transport.netty;

import com.wang.rpc.core.channel.TinyChannel;
import com.wang.rpc.core.exchange.domain.TinyResponse;
import io.netty.channel.Channel;

import java.net.SocketAddress;
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
    public void send(Object msg) throws RuntimeException{
        // find worker channel to send msg
        this.channel.writeAndFlush(msg);
    }
}

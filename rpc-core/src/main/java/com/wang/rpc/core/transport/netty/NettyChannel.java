package com.wang.rpc.core.transport.netty;

import com.wang.rpc.core.channel.TinyChannel;
import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class NettyChannel implements TinyChannel {

    private Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * netty ==> NettyChannel
     */
    public static final Map<Channel, NettyChannel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static NettyChannel getOrAddChannel(Channel ch) {
        NettyChannel nettyChannel = CHANNEL_MAP.get(ch);
        if (nettyChannel == null) {
            nettyChannel = new NettyChannel(ch);
            CHANNEL_MAP.put(ch, nettyChannel);
        }
        return nettyChannel;
    }
}

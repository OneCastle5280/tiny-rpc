package com.wang.rpc.core.domain.pool;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.net.SocketAddress;

/**
 * 池化对象
 *
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PooledChannel {
    /**
     * netty TinyChannel
     */
    private Channel channel;
    /**
     * remote socket
     */
    private SocketAddress socketAddress;
}

package com.wang.rpc.core.transport.channel;

import com.google.common.collect.Lists;
import com.wang.rpc.core.loadbalance.LoadBalance;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * wrap {@code TinyChannel}, manage add, get, remove, check connect status
 *
 * @author wangjiabao
 */
public class ChannelWrapper {
    /**
     * LoadBalance
     */
    private static final LoadBalance<TinyChannel> loadBalance;
    /**
     * /host:port ==> {@link TinyChannel}
     * eg:
     *   /127.0.0.1:8080 ==> {@link TinyChannel}
     */
    private static final Map<String, TinyChannel> channelMap;
    /**
     * index for load balance
     */
    private static final AtomicInteger index;

    static {
        index = new AtomicInteger(0);
        channelMap = new ConcurrentHashMap<>();
        loadBalance = list -> {
            // TODO SPI default polling
            int i = index.getAndAdd(1) % list.size();
            return list.get(i);
        };
    }

    public ChannelWrapper() {
    }

    /**
     * get all {@code TinyChannel}
     *
     * @return
     */
    public List<TinyChannel> getChannels() {
        return Lists.newArrayList(channelMap.values());
    }

    /**
     * get {@code TinyChannel} with {@link LoadBalance}
     *
     * @return
     */
    public TinyChannel getWithLoadBalance() {
        return loadBalance.select(this.getChannels());
    }

    public TinyChannel newChannel(SocketAddress address) {

    }
}

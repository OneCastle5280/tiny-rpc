package com.wang.rpc.core.pool;

import com.wang.rpc.core.domain.pool.PooledChannel;
import io.netty.bootstrap.Bootstrap;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.net.SocketAddress;

/**
 * @author wangjiabao
 */
public class NettyChannelPool {

    private final GenericObjectPool<PooledChannel> objectPool;

    /**
     * init netty channel pool
     *
     * @param bootstrap      netty bootstrap
     * @param socketAddress  socket address
     * @param maxTotal       max channel size
     * @param minIdle        min idle channel size
     */
    public NettyChannelPool(Bootstrap bootstrap, SocketAddress socketAddress, int maxTotal, int minIdle) {
        GenericObjectPoolConfig<PooledChannel> config = new GenericObjectPoolConfig<>();
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);

        objectPool = new GenericObjectPool<>(
                new NettyChannelPoolFactory(bootstrap, socketAddress),
                config
        );
    }

    public PooledChannel borrowChannel() throws Exception {
        return this.objectPool.borrowObject();
    }

    public void returnChannel(PooledChannel pooledChannel) throws Exception {
        this.objectPool.returnObject(pooledChannel);
    }

    public void close() {
        this.objectPool.close();
    }
}

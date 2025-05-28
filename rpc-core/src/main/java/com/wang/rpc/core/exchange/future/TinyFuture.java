package com.wang.rpc.core.exchange.future;

import com.wang.rpc.core.channel.TinyChannel;
import com.wang.rpc.core.exchange.domain.TinyRequest;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wangjiabao
 */
public class TinyFuture extends CompletableFuture<Object> {

    /**
     * requestId ==> Future
     */
    private static Map<Long, TinyFuture> FUTURE_MAP = new ConcurrentHashMap<>();

    private TinyChannel channel;

    public TinyFuture(TinyChannel channel, TinyRequest request, int timout) {
        this.channel = channel;
        // add to FUTURE_MAP
        FUTURE_MAP.put(request.getId(), this);
        // TODO timeout check

    }

    /**
     * acquire TinyFuture
     *
     * @param requestId request id
     * @return
     */
    public static TinyFuture acquireTinyFuture(String requestId) {
        return FUTURE_MAP.get(requestId);
    }
}

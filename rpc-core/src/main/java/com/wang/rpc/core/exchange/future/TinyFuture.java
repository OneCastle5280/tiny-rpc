package com.wang.rpc.core.exchange.future;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * tiny future
 *
 * @author wangjiabao
 */
public class TinyFuture extends CompletableFuture<Object> {

    /**
     * requestId —> tinyFuture
     */
    private static final Map<String, TinyFuture> FUTURE_MAP;

    private static final int CORE_THREAD_SIZE = 20;
    private static final int MAX_THREAD_SIZE = 100;
    private static final long KEEP_ALIVE_TIME = 40;

    private static final AtomicInteger THREAD_NUM;
    private static final ExecutorService THREAD_POOL;

    static {
        THREAD_NUM = new AtomicInteger(1);
        FUTURE_MAP = new ConcurrentHashMap<>();
        THREAD_POOL = new ThreadPoolExecutor(
                CORE_THREAD_SIZE,
                MAX_THREAD_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000),
                r -> {
                    String threadName = "tiny-future-handle-thread-pool-" + THREAD_NUM.getAndIncrement();
                    return new Thread(r, threadName);
                }
        );

    }

    public TinyFuture() {
        super();
    }

    public TinyFuture(Supplier<?> supplier) {

    }


    public static TinyFuture addTinyFuture(String requestId, TinyFuture future) {
        return FUTURE_MAP.putIfAbsent(requestId, future);
    }


}

package com.wang.rpc.core.exchange.future;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * tiny future
 *
 * @author wangjiabao
 */
public class TinyFuture implements Future<Object> {

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

    private final TinyTask task;

    public TinyFuture(TinyTask task) {
        this.task = task;
    }

    public TinyFuture(Callable<Object> callable) {
        this.task = new TinyTask(callable);
    }

    /**
     * add callable task to FutureMap
     *
     * @param requestId     FutureMap key
     * @param callable      callable task
     * @return
     */
    public static TinyFuture addToFutureMap(String requestId, Callable<Object> callable) {
        return FUTURE_MAP.putIfAbsent(requestId, new TinyFuture(callable));
    }

    /**
     * supply async callable task
     *
     * @param callable
     * @return
     */
    public static TinyFuture supplyAsync(Callable<Object> callable) {
        // new TinyFuture
        TinyFuture tinyFuture = new TinyFuture(new TinyTask(callable));

        // submit task
        THREAD_POOL.submit(tinyFuture.task());
        return tinyFuture;
    }

    /**
     *  remove {@code TinyFuture}
     *
     * @param requestId Request unique identifier
     */
    public static TinyFuture removeTinyFuture(String requestId) {
        return FUTURE_MAP.remove(requestId);
    }

    /**
     * @return {@code TinyTask}
     */
    public TinyTask task() {
        return task;
    }


    /**
     * when task complete will consumer
     *
     * @param consumer
     */
    public void whenComplete(Consumer<Object, Throwable> consumer) {
        // add listener
        this.task.addListener(new TinyTaskListener() {
            @Override
            public void runWhenComplete(Object result, Throwable throwable) {
                consumer.accept(result, throwable);
            }
        });
    }

    public void complete(Object result) {
        task.complete(result);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return task.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return task.isCancelled();
    }

    @Override
    public boolean isDone() {
        return task.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        return task.get();
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return task.get(timeout, unit);
    }
}

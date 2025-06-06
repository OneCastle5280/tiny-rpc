package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.exception.RpcServiceNotFound;
import com.wang.rpc.core.exchange.domain.HandleResult;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.domain.TinyResponse;
import com.wang.rpc.core.exchange.service.RpcService;
import com.wang.rpc.core.exchange.service.RpcServiceFactory;
import com.wang.rpc.core.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * handle request msg
 *
 * @author wangjiabao
 */
@Slf4j
public class RequestHandler {

    /**
     * requestId ==> completableFuture
     */
    private static final Map<String, CompletableFuture<Object>> FUTURE_MAP = new ConcurrentHashMap<>();

    private static final int CORE_THREAD_SIZE = 20;
    private static final int MAX_THREAD_SIZE = 100;
    private static final long KEEP_ALIVE_TIME = 40;

    private final AtomicInteger threadNum;
    private final ExecutorService threadPool;

    public RequestHandler() {
        this.threadNum = new AtomicInteger(1);
        // request handle
        this.threadPool = new ThreadPoolExecutor(
                CORE_THREAD_SIZE,
                MAX_THREAD_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10000),
                r -> {
                    String threadName = "request-handle-thread-pool-" + threadNum.getAndIncrement();
                    return new Thread(r, threadName);
                }
        );
    }

    public CompletableFuture<Object> handleRequest(TinyRequest request) {
        if (request == null) {
            throw new NullPointerException();
        }

        return FUTURE_MAP.putIfAbsent(
                String.valueOf(request.getId()),
                CompletableFuture.supplyAsync(() -> this.doHandleRequest(request), this.threadPool)
        );
    }

    private Object doHandleRequest(TinyRequest request) {
        // TODO filter chain
        HandleResult result = new HandleResult();
        try {
            RpcService rpcService = RpcServiceFactory.getRpcService(request);
            result = rpcService.handleRequest(request);
        } catch (RpcServiceNotFound e) {
            log.error("[RequestHandler] doHandleRequest err", e);
            result.setException(e);
            result.setErrMessage(ThrowableUtil.toString(e));
        } catch (Exception e) {
            result.setException(e);
            result.setErrMessage(ThrowableUtil.toString(e));
        }
        return result;
    }



}

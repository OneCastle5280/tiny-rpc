package com.wang.rpc.core.exchange.future;

/**
 * {@see TinyTask} listener
 *
 * @author wangjiabao
 */
public interface TinyTaskListener {
    /**
     * run when task complete
     *
     * @param result     task result
     * @param throwable  task exception
     */
    void runWhenComplete(Object result, Throwable throwable);
}

package com.wang.rpc.core.exchange.future;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author wangjiabao
 */
@Slf4j
public class TinyTask extends FutureTask<Object> {

    /**
     * TinyTask listeners
     */
    private final List<TinyTaskListener> listeners = Collections.synchronizedList(new ArrayList<>());

    public TinyTask(Callable<Object> callable) {
        super(callable);
    }

    public TinyTask(Runnable runnable, Object result) {
        super(runnable, result);
    }

    /**
     * add listener
     *
     * @param listener
     */
    public void addListener(TinyTaskListener listener) {
        listeners.add(listener);
    }

    /**
     * complete task
     *
     * @param result
     */
    public void complete(Object result) {
        // set result
        this.set(result);
    }

        @Override
    protected void done() {
        if (listeners.isEmpty()) {
            // noting
            return;
        }

        Object r = null;
        Throwable t = null;

        try {
            r = get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            t = e;
        } catch (Exception e) {
            t = e;
        }

        // exec listeners
        for (TinyTaskListener listener : listeners) {
            try {
                listener.runWhenComplete(r, t);
            } catch (Exception e) {
                log.error("TinyTask listener run when complete error", e);
                // continue
            }
        }
    }
}

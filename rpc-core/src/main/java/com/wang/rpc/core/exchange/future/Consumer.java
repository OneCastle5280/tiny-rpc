package com.wang.rpc.core.exchange.future;

/**
 * @author wangjiabao
 *
 * @param <T>
 * @param <U>
 */
@FunctionalInterface
public interface Consumer<T, U> {
    /**
     * consumer accept
     *
     * @param t
     * @param u
     */
    void accept(T t, U u);
}

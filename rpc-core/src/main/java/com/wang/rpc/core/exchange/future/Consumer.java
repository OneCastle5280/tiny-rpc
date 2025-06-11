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
     * accept
     */
    void accept(T t, U u);
}

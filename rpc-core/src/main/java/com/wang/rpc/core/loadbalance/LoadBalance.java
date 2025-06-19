package com.wang.rpc.core.loadbalance;

import java.util.List;

/**
 * @author wangjiabao
 */
public interface LoadBalance<T> {

    /**
     * select one from list
     *
     * @param list
     * @return
     */
    T select(List<T> list);
}

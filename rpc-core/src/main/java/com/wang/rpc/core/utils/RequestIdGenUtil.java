package com.wang.rpc.core.utils;


import java.util.UUID;

/**
 * @author wangjiabao
 */
public class RequestIdGenUtil {
    private RequestIdGenUtil() {
    }

    public static String gen() {
        // TODO
        return UUID.randomUUID().toString();
    }
}

package com.wang.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明为 rpc service 接口
 *
 * @author wangjiabao
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TinyRpcService {

    /**
     * rpc service 接口 class
     *
     * @return
     */
    Class<?> interfaceClass() default Object.class;

    /**
     * rpc service version
     *
     * @return
     */
    String version() default "1.0.0";
}

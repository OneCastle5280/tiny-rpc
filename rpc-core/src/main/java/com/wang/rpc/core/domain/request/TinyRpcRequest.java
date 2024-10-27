package com.wang.rpc.core.domain.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class TinyRpcRequest implements Serializable {
    /**
     * 请求的服务名 + 版本
     */
    private String serviceName;

    /**
     * 请求调用的方法
     */
    private String method;

    /**
     *  参数类型
     */
    private Class<?>[] parameterTypes;

    /**
     *  参数
     */
    private Object[] parameters;
}

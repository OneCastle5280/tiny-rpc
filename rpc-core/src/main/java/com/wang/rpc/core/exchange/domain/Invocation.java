package com.wang.rpc.core.exchange.domain;

import com.wang.rpc.core.enums.InvokeMode;
import com.wang.rpc.core.enums.InvokerType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * invocation info
 *
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Invocation {
    /**
     * interface name
     */
    private String interfaceName;
    /**
     * method name
     */
    private String methodName;
    /**
     * version
     */
    private String version;
    /**
     * param types
     */
    private Class<?>[] paramTypes;
    /**
     * params
     */
    private Object[] params;
    /**
     * invoke mode
     */
    private InvokeMode invokeMode;
}

package com.wang.rpc.core.exchange.domain;

import com.wang.rpc.core.enums.InvokeMode;

/**
 * @author wangjiabao
 */
public class ServerInvokerResult extends AbstractInvokeResult{

    public ServerInvokerResult(InvokeMode invokeMode) {
        super(invokeMode);
    }
}

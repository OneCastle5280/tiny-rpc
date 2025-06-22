package com.wang.rpc.core.exchange.domain;

import com.wang.rpc.core.enums.InvokeMode;

/**
 * @author wangjiabao
 */
public class ClientInvokeResult extends AbstractInvokeResult{

    public ClientInvokeResult(InvokeMode invokeMode) {
        super(invokeMode);
    }
}

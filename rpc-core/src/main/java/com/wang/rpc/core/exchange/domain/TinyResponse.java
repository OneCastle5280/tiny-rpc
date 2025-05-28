package com.wang.rpc.core.exchange.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class TinyResponse {
    /**
     * request id
     */
    private Long id;

    /**
     * status
     */
    private Byte status;

    /**
     * result
     */
    private Object result;
}

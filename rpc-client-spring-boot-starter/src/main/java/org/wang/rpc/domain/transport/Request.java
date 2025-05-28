package org.wang.rpc.domain.transport;

import com.wang.rpc.core.exchange.domain.TinyRpcRequest;
import com.wang.rpc.core.protocol.MessageProtocol;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wangjiabao
 */
@Data
@Accessors(chain = true)
public class Request implements Serializable {
    private static final long serialVersionUID = -1L;

    /**
     * 调用具体信息
     */
    private MessageProtocol<TinyRpcRequest> requestMessageProtocol;
    /**
     * 服务端 ip
     */
    private String address;
    /**
     * 服务端 端口
     */
    private Integer port;
}

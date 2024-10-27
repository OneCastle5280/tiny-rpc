package org.wang.rpc.proxy;

import com.wang.rpc.core.constants.RpcConstants;
import com.wang.rpc.core.discovery.RpcDiscovery;
import com.wang.rpc.core.domain.RpcProvider;
import com.wang.rpc.core.domain.enums.MessageStatusEnum;
import com.wang.rpc.core.domain.enums.MessageTypeEnum;
import com.wang.rpc.core.domain.enums.SerializationTypeEnum;
import com.wang.rpc.core.domain.request.TinyRpcRequest;
import com.wang.rpc.core.domain.response.TinyRpcResponse;
import com.wang.rpc.core.protocol.MessageHeader;
import com.wang.rpc.core.protocol.MessageProtocol;
import lombok.AllArgsConstructor;
import org.wang.rpc.domain.transport.Request;
import org.wang.rpc.transport.netty.NettyTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 代理逻辑，封装调用逻辑
 *
 * @author wangjiabao
 */
@AllArgsConstructor
public class RpcClientInvocationHandler implements InvocationHandler {

    private RpcDiscovery rpcDiscovery;

    private Class<?> clazz;

    private String version;

    private NettyTransport nettyTransport;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        String serviceName = this.clazz.getName() + "-" + this.version;
        RpcProvider rpcProvider = this.rpcDiscovery.discovery(serviceName);
        if (rpcProvider == null) {
            throw new IllegalArgumentException(serviceName + "is not exist");
        }
        // 将消息编码
        MessageProtocol<TinyRpcRequest> messageProtocol = new MessageProtocol<>();
        MessageHeader header = new MessageHeader()
                .setMagic(RpcConstants.MAGIC)
                .setVersion(RpcConstants.VERSION)
                .setSerialization(SerializationTypeEnum.HESSIAN.getType())
                .setType(MessageTypeEnum.REQUEST.getType())
                .setStatus(MessageStatusEnum.SUCCESS.getCode())
                .setRequestId(UUID.randomUUID().toString().replaceAll("-",""))
                ;
        TinyRpcRequest request = new TinyRpcRequest()
                .setServiceName(serviceName)
                .setMethod(method.getName())
                .setParameterTypes(method.getParameterTypes())
                .setParameters(args)
                ;
        messageProtocol.setHeader(header);
        messageProtocol.setBody(request);
        // 发送网络请求
        MessageProtocol<TinyRpcResponse> responseMessageProtocol = this.nettyTransport.sendRequest(
                new Request()
                        .setRequestMessageProtocol(messageProtocol)
                        .setAddress(rpcProvider.getAddress())
                        .setPort(rpcProvider.getPort())
        );
        if (responseMessageProtocol == null
                || responseMessageProtocol.getHeader() == null
                || !MessageStatusEnum.isSuccess(responseMessageProtocol.getHeader().getStatus())) {
            throw new Exception();
        }
        return responseMessageProtocol.getBody().getData();
    }
}

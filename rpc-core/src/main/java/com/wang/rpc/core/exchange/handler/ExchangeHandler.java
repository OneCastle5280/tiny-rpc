package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.transport.channel.TinyChannel;
import com.wang.rpc.core.exchange.domain.TinyRequest;
import com.wang.rpc.core.exchange.domain.TinyResponse;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangjiabao
 */
@Slf4j
public class ExchangeHandler {

    private final RequestHandler requestHandler;

    private final ResponseHandler responseHandler;

    public ExchangeHandler() {
        this.requestHandler = new RequestHandler();
        this.responseHandler = new ResponseHandler();
    }

    /**
     * receive msg from transport channel
     *
     * @param channel
     * @param msg
     */
    public void receive(TinyChannel channel, Object msg) {
        if (msg instanceof TinyRequest) {
            // request
            handleRequest(channel, (TinyRequest) msg);
        } else if (msg instanceof TinyResponse) {
            // response
            handleResponse(channel, (TinyResponse) msg);
        }
    }

    /**
     * handle response
     */
    private void handleResponse(TinyChannel channel, TinyResponse response) {
        this.responseHandler.handleResponse(response);
    }

    /**
     * async handle request
     */
    private void handleRequest(TinyChannel channel, TinyRequest request) {
        TinyResponse response = new TinyResponse(request.getId());

        // async handle request
        try {
            this.requestHandler.handleRequest(request).whenComplete((handleResult, throwable) -> {
                if (throwable == null) {
                    // success
                    response.setStatus(TinyResponse.OK);
                    response.setResult(handleResult);
                } else {
                    // exception
                    response.setStatus(TinyResponse.ERROR);
                    response.setErrMsg(ThrowableUtil.toString(throwable));
                }
                // reply resp
                reply(channel, response);
            });
        } catch (Exception e) {
            response.setStatus(TinyResponse.ERROR);
            response.setErrMsg(ThrowableUtil.toString(e));
            reply(channel, response);
        } finally {
            // remove TinyFuture
            TinyFuture.removeTinyFuture(request.getId());
        }
    }

    /**
     * send response to channel
     *
     * @param channel
     * @param response
     */
    private void reply(TinyChannel channel, TinyResponse response) {
        try {
            channel.send(response);
        } catch (Exception e) {
            log.error("[handleRequest] exception", e);
        }
    }

}

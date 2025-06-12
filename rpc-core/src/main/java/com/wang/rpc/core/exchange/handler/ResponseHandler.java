package com.wang.rpc.core.exchange.handler;

import com.wang.rpc.core.exchange.domain.TinyResponse;
import com.wang.rpc.core.exchange.future.TinyFuture;
import com.wang.rpc.core.exchange.future.TinyTask;

/**
 * response handler
 *
 * @author wangjiabao
 */
public class ResponseHandler {

    /**
     * handle response
     *
     * @param response
     */
    public void handleResponse(TinyResponse response) {
        // remove first
        TinyFuture future = TinyFuture.removeTinyFuture(String.valueOf(response.getId()));
        // handle response
        this.doHandleResponse(future, response);
    }

    private void doHandleResponse(TinyFuture future,TinyResponse response) {
        if (future == null) {
            // TODO check
            return;
        }

        if (TinyResponse.OK.equals(response.getStatus())) {
            handleWhenSuccess(future, response);
        } else if (TinyResponse.ERROR.equals(response.getStatus())) {
            handleWhenError(future, response);
        } else {
            // TODO timeout
        }

    }

    private void handleWhenSuccess(TinyFuture future, TinyResponse response){
        if (future == null) {
            return;
        }
        future.task()
    }

    private void handleWhenError(TinyFuture future, TinyResponse response){


    }


}

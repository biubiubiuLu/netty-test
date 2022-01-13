package com.lk.netty.test.client;

import com.alibaba.fastjson.JSONObject;
import com.lk.netty.test.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;

/**
 * @author lukai
 * @creare 2022-01-13 16:26
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Promise<Response> promise;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client read ="+msg);
        Response response = JSONObject.parseObject(msg.toString(), Response.class);
        promise.setSuccess(response);
    }

    public Promise<Response> getPromise() {
        return promise;
    }

    public void setPromise(Promise<Response> promise) {
        this.promise = promise;
    }
}

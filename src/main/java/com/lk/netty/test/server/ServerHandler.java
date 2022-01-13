package com.lk.netty.test.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lk.netty.test.RequestFuture;
import com.lk.netty.test.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        if (msg instanceof ByteBuf) {
            String msgStr = ((ByteBuf) msg).toString(Charset.defaultCharset());
            RequestFuture request = JSON.parseObject(msgStr, RequestFuture.class);
            long id = request.getId();
            Response response = new Response();
            response.setId(id);
            response.setResult("服务器响应ok");
            ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        }
//
    }
}

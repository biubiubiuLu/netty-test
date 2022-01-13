package com.lk.netty.test.client;

import com.alibaba.fastjson.JSONObject;
import com.lk.netty.test.RequestFuture;
import com.lk.netty.test.Response;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.nio.charset.Charset;

/**
 * @author lukai
 * @creare 2022-01-13 15:54
 */
public class NettyClient {

    public static EventLoopGroup group = null;

    public static Bootstrap bootstrap = null;

    static {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(group);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
    }

    public static void main(String[] args) throws Exception {
        Promise<Response> promise = new DefaultPromise<>(group.next());
        final ClientHandler handler = new ClientHandler();
        handler.setPromise(promise);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel channel) throws Exception {
//                channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//                channel.pipeline().addLast(new StringDecoder());
                channel.pipeline().addLast(handler);
//                channel.pipeline().addLast(new LengthFieldPrepender(4, false));
//                channel.pipeline().addLast(new StringEncoder(Charset.forName("UTF-8")));
            }
        });

        ChannelFuture future = bootstrap.connect("127.0.0.1",8080).sync();
        RequestFuture request = new RequestFuture();
        request.setId(1);
        request.setRequest("hello world");
        String requestStr = JSONObject.toJSONString(request);
        future.channel().writeAndFlush(requestStr);
//        Response response = promise.get();
//        System.out.println(JSONObject.toJSONString(response));
    }
}

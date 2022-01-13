package com.lk.netty.test;

import java.util.ArrayList;
import java.util.List;

public class FutureMain {
    public static void main(String[] args) {
        List<RequestFuture> reqs = new ArrayList();
        for (int i = 1; i < 100; i++) {
            long id = i;
            RequestFuture req = new RequestFuture();
            req.setId(id);
            req.setRequest("hello world");
            RequestFuture.addFuture(req);
            reqs.add(req);
            sendMsg(req);
            SubThread subThread = new SubThread(req);
            subThread.start();
        }
    }

    private static void sendMsg(RequestFuture req) {
        System.out.println("客户端发送数据，请求id为=====" + req.getId());
    }
}

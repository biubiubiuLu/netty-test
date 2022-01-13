package com.lk.netty.test;

public class SubThread extends Thread{

    private RequestFuture request;

    public SubThread(RequestFuture request){
        this.request = request;
    }


    @Override
    public void run() {
        Response resp = new Response();
        resp.setId(request.getId());
        resp.setResult("server response"+Thread.currentThread().getId());
        try{
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestFuture.received(resp);
    }
}



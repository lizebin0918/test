package com.lzb.concurrent.async;

import com.lzb.concurrent.async.manager.AsyncReplyManager;
import com.lzb.concurrent.async.model.AsyncReply;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 客户端测试<br/>
 * Created on : 2021-07-14 22:23
 *
 * @author lizebin
 */
public class Client {

    public static void main(String[] args) throws Exception {
        String uuid = UUID.randomUUID().toString();
        AsyncReply<String, String> reply = new AsyncReply<String, String>(uuid) {
            @Override
            public void send(String request) throws Exception {
                System.out.println("发送请求:" + request);
                System.out.println("待回应......");
            }
        };

        //异步回应
        asyncResponse(uuid);

        System.out.println("开始发送请求...");
        String response = reply.getReply("request", 5, TimeUnit.SECONDS);
        System.out.println("this is response:" + response);
    }

    public static void asyncResponse(String uuid) {
        //休眠1秒设置response
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AsyncReply<String, String> reply = AsyncReplyManager.getInstance().get(uuid);
            reply.setReply("response");
            System.out.println("回应请求...");
        }).start();
    }

}

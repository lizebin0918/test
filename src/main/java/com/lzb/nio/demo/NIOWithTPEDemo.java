package com.lzb.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ThreadPoolExecutor;

import static com.lzb.nio.demo.Utils.*;

/**
 * 单线程接收，多线程处理
 * @author hj
 * @version 1.0
 * @description: QPS 130
 * @date 2021/5/17 21:30
 */
public class NIOWithTPEDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT),BACK_LOG);
        ThreadPoolExecutor threadPoolExecutor = buildThreadPoolExecutor();
        System.out.println("启动服务器");
        for (; ; ) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            threadPoolExecutor.execute(() -> {
                try {
                    System.out.println(socketChannel.getRemoteAddress());
                    String resp = buildHttpResp();
                    doSomeWork();
                    socketChannel.write(ByteBuffer.wrap(resp.getBytes()));
                } catch (Exception e) {

                }
            });

        }
    }
}

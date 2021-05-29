package com.lzb.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import static com.lzb.nio.demo.Utils.*;

/**
 * @author hj
 * @version 1.0
 * @description: 普通NIO，只用byteBuffer和channel只能保证 QPS 2.0
 * @date 2021/5/17 21:30
 */
public class NIODemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT), BACK_LOG);
        System.out.println("启动服务器");
        for (; ; ) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println(socketChannel.getRemoteAddress());
            String resp = buildHttpResp();
            doSomeWork();
            socketChannel.write(ByteBuffer.wrap(resp.getBytes()));
        }
    }
}

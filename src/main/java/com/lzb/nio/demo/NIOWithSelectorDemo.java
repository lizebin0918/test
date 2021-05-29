package com.lzb.nio.demo;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import static com.lzb.nio.demo.Utils.*;

/**
 * @author hj
 * @version 1.0
 * @description: 吞吐量极其低下
 * @date 2021/5/17 21:30
 */
public class NIOWithSelectorDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT), BACK_LOG);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("启动服务器");
        for (; ; ) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            System.out.println("---------------------");
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    ServerSocketChannel serverSockChannel = (ServerSocketChannel) next.channel();
                    SocketChannel acceptSocketChannel = serverSockChannel.accept();
                    System.out.println(acceptSocketChannel.getRemoteAddress());
                    acceptSocketChannel.configureBlocking(false);
                    acceptSocketChannel.register(selector, SelectionKey.OP_WRITE);
                    System.out.println(1);
                }
                if (next.isWritable()) {
                    System.out.println(2);
                    SocketChannel socketChannel = (SocketChannel) next.channel();
                    String resp = buildHttpResp();
                    try {
                        doSomeWork();
                        socketChannel.write(ByteBuffer.wrap(resp.getBytes()));
                    } catch (Exception e) {
                    }
                }
                iterator.remove();
            }

        }
    }
}

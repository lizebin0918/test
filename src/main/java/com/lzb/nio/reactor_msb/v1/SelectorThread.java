package com.lzb.nio.reactor_msb.v1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 每个线程对应一个selector<br/>
 * 多线程情况下，该主机，该程序的并发客户端被分配到多个selector上
 * Created on : 2021-06-12 11:16
 *
 * @author lizebin
 */
public class SelectorThread implements Runnable {

    /**
     * 用于被其他线程唤醒
     */
    LinkedBlockingQueue<Channel> selectorQueue = new LinkedBlockingQueue<>();
    Selector selector = null;
    SelectorThread() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {

            try {
                //1.如果返回0，表示有其他线程唤醒-wakeup()
                System.out.println(Thread.currentThread().getName() + ":开始select()");
                int nums = selector.select();
                System.out.println(Thread.currentThread().getName() + ":结束select()");
                //2.有selectionKeys
                if (nums > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> i = selectionKeys.iterator();
                    while (i.hasNext()) {//线性处理
                        SelectionKey key = i.next();
                        i.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        } else if (key.isWritable()) {

                        }
                    }
                }
                //3.处理task
                if (selectorQueue.isEmpty()) continue;
                Channel channel = selectorQueue.poll();
                if (channel instanceof ServerSocketChannel) {
                    ServerSocketChannel server = (ServerSocketChannel) channel;
                    server.register(selector, SelectionKey.OP_ACCEPT);
                } else if (channel instanceof SocketChannel) {
                    SocketChannel client = (SocketChannel) channel;
                    ByteBuffer b = ByteBuffer.allocateDirect(4096);
                    client.register(selector, SelectionKey.OP_READ, b);
                    System.out.println(
                            Thread.currentThread().getName() + " read byte data:" + StandardCharsets.UTF_8.decode(b)
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void readHandler(SelectionKey key) {
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        SocketChannel client = (SocketChannel) key.channel();
        buffer.clear();
        while (true) {
            try {
                int num = client.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (num == 0) {
                    break;
                } else {
                    //客户端断开了
                    System.out.println("客户端断开了:" + client.getRemoteAddress());
                    key.cancel();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void acceptHandler(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            //注册到对应的selector上
            selectorQueue.add(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

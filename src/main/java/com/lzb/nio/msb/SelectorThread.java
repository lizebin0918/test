package com.lzb.nio.msb;

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
    SelectorThreadGroup group = null;

    SelectorThread(SelectorThreadGroup group) {
        try {
            this.group = group;
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
                int nums = selector.select();
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
                    System.out.println(Thread.currentThread().getName() + " regist listen");
                } else if (channel instanceof SocketChannel) {
                    System.out.println(Thread.currentThread().getName() + " regist client:" + ((SocketChannel) channel).getRemoteAddress());
                    SocketChannel client = (SocketChannel) channel;
                    ByteBuffer b = ByteBuffer.allocateDirect(4096);
                    client.register(selector, SelectionKey.OP_READ, b);
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
                    buffer.mark();
                    System.out.println("read from client:" + new String(StandardCharsets.UTF_8.decode(buffer).array()));
                    buffer.reset();
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
            System.out.println(Thread.currentThread().getName() + " accept client:" + client.getRemoteAddress());
            client.configureBlocking(false);
            //注册到对应的selector上
            group.nextSelector(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

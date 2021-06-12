package com.lzb.nio.reactor_msb.v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程组<br/>
 * Created on : 2021-06-12 13:06
 *
 * @author lizebin
 */
public class SelectorThreadGroup {

    SelectorThread[] selectors = null;
    ServerSocketChannel serverSocketChannel = null;
    private final AtomicInteger counter = new AtomicInteger();

    public SelectorThreadGroup(int size) {
        selectors = new SelectorThread[size];
        for (int i = 0; i < size; i++) {
            selectors[i] = new SelectorThread();
            new Thread(selectors[i]).start();
        }
    }

    public void bind(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务端注册.....，端口:" + port);
            nextSelector(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param channel ServerSocketChannel or SocketChannel
     */
    public void nextSelector(Channel c) {
        System.out.println("channel注册...");
        SelectorThread st = next();

        //SelectorThread已经在内部selector.select()阻塞，通过什么方式唤醒select()
        //并注册accept事件？？？？通过队列
        /*ServerSocketChannel ssc = (ServerSocketChannel) c;
        try {
            ssc.register(st.selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }*/

        st.selectorQueue.add(c);
        st.selector.wakeup();
    }

    private SelectorThread next() {
        int index = counter.incrementAndGet() % selectors.length;
        return selectors[index];
    }

}
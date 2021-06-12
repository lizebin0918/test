package com.lzb.nio.reactor_msb.v1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
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
            selectors[i] = new SelectorThread(this);
            new Thread(selectors[i]).start();
        }
    }

    public void bind(int port) {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("服务端注册.....，端口: " + port);
            nextSelector(serverSocketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param channel ServerSocketChannel or SocketChannel
     */
    public void nextSelector(Channel c) {

        //SelectorThread已经在内部selector.select()阻塞，通过什么方式唤醒select()
        //并注册accept事件？？？？通过队列
        /*ServerSocketChannel ssc = (ServerSocketChannel) c;
        try {
            ssc.register(st.selector, SelectionKey.OP_ACCEPT);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }*/

        //混杂模式：channel的事件均匀注册到不同的selector上
        //st.selectorQueue.add(c);
        //st.selector.wakeup();

        //仿照netty的模式，selectors[0]只接收accept事件，selectors[n]接收读写事件
        if (c instanceof ServerSocketChannel) {
            selectors[0].selectorQueue.add(c);
            selectors[0].selector.wakeup();
        }

        if (c instanceof SocketChannel) {
            SelectorThread st = nextV1();
            st.selectorQueue.add(c);
            st.selector.wakeup();
        }
    }

    private SelectorThread next() {
        int index = counter.incrementAndGet() % selectors.length;
        return selectors[index];
    }

    private SelectorThread nextV1() {
        int index = counter.incrementAndGet() % (selectors.length - 1);
        return selectors[index + 1];
    }

}

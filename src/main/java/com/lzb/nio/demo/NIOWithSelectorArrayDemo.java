package com.lzb.nio.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import static com.lzb.nio.demo.Utils.*;

/**
 * @author hj
 * @version 1.0
 * @description: TODO
 * @date 2021/5/17 21:30
 */
public class NIOWithSelectorArrayDemo {
    private static final int POLLER_NUM = Runtime.getRuntime().availableProcessors() * 2;
    private static Poller[] pollers;

    public static ServerSocketChannel createServerSocketChannel() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(true);
        serverSocketChannel.bind(new InetSocketAddress(DEFAULT_PORT), BACK_LOG);
        return serverSocketChannel;
    }

    public static void initPoller() throws IOException {
        pollers = new Poller[POLLER_NUM];
        for (int i = 0; i < pollers.length; i++) {
            Poller poller = new Poller();
            poller.init();
            pollers[i] = poller;
        }
    }

    public static void startPoller() {
        for (int i = 0; i < pollers.length; i++) {
            pollers[i].start();
        }
    }

    public static class Poller extends Thread {
        private Selector selector;
        private BlockingQueue<SocketChannel> socketChannelBlockingQueue = new LinkedBlockingQueue<>();
        private AtomicBoolean atomicBoolean = new AtomicBoolean();

        @Override
        public void run() {
            for (; ; ) {
                try {
                    SocketChannel socketChannel = socketChannelBlockingQueue.poll();
                    if (socketChannel != null) {
                        socketChannel.configureBlocking(false);
                        doSomeWork();
                        String resp = buildHttpResp();
                        socketChannel.register(selector, SelectionKey.OP_WRITE, ByteBuffer.wrap(resp.getBytes()));

                    }
                    atomicBoolean.set(true);
                    selector.select(1000);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        if (next.isWritable()) {
                            SocketChannel sc = (SocketChannel) next.channel();

                            try {
                                sc.write((ByteBuffer) next.attachment());
                            } catch (Exception e) {
                            }
                        } else {
                            System.out.println("未知SelectionKey");
                        }
                        iterator.remove();
                    }
                } catch (Exception e) {
                }
            }
        }

        public void init() throws IOException {
            selector = Selector.open();
        }

        public void addSocketChannel(SocketChannel socketChannel) {
            try {
                socketChannelBlockingQueue.put(socketChannel);
                if (atomicBoolean.get()) {
                    selector.wakeup();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) throws Exception {
            ServerSocketChannel serverSocketChannel = createServerSocketChannel();
            System.out.println("启动服务器");
            initPoller();
            startPoller();
            long count = 0;
            int m = pollers.length - 1;
            for (; ; ) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println(socketChannel.getRemoteAddress());
                pollers[(int) (count++ & m)].addSocketChannel(socketChannel);
            }
        }

    }
}

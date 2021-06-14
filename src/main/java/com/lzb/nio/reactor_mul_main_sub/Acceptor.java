package com.lzb.nio.reactor_mul_main_sub;

import java.io.IOException;
import java.nio.channels.*;

/**
 *
 * 最大的不同在于Acceptor类的构造方法，我开了8个线程，8个subReactor，8个selector，
 * 程序一启动，8个线程就会执行，执行的就是subReactor中定义的run方法，监听事件。
 */
public class Acceptor implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private final int CORE = 8;

    private int index;

    private SubReactor[] subReactors = new SubReactor[CORE];
    private Thread[] threads = new Thread[CORE];
    private final Selector[] selectors = new Selector[CORE];

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
        for (int i = 0; i < CORE; i++) {
            try {
                selectors[i] = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
            subReactors[i] = new SubReactor(selectors[i]);
            threads[i] = new Thread(subReactors[i]);
            threads[i].start();
        }
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("有客户端连接上来了," + socketChannel.getRemoteAddress());
            socketChannel.configureBlocking(false);
            selectors[index].wakeup();
            SelectionKey selectionKey = socketChannel.register(selectors[index], SelectionKey.OP_READ);
            selectionKey.attach(new WorkHandler(socketChannel));
            if (++index == threads.length) {
                index = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


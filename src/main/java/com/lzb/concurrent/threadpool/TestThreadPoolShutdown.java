package com.lzb.concurrent.threadpool;

import java.util.concurrent.*;

/**
 * 线程池关闭<br/>
 * Created on : 2021-06-18 13:35
 *
 * @author chenpi
 */
public class TestThreadPoolShutdown {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        threadPool.execute(() -> {
            int time = 20;
            while (!Thread.currentThread().isInterrupted() && --time > 0 && !threadPool.isShutdown()) {
                try {
                    System.out.println("thread start sleep");
                    Thread.sleep(100);
                    System.out.println("thread end sleep");
                } catch (InterruptedException e) {
                    System.out.println("thread interrupt");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("thread done");
        });

        System.out.println("main start sleep");
        Thread.sleep(1000);
        System.out.println("main end sleep");
        threadPool.shutdown();
        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程无法关闭，强制关闭");
            threadPool.shutdownNow();
        }

        System.out.println("main done");

    }

}

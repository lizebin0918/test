package com.lzb.concurrent.completablefuture;

import java.util.concurrent.*;

/**
 * 测试CompletableFuture是否为守护线程<br/>
 * Created on : 2021-06-04 10:22
 * @author chenpi 
 */
public class TestDaemen {

    public static volatile boolean run = true;
    static int threadSize = 8;
    public static final Semaphore semaphore = new Semaphore(threadSize);

    public static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            while (run) {
                try {
                    semaphore.acquire();
                    CompletableFuture.runAsync(() -> {
                       String tn = Thread.currentThread().getName();
                        System.out.println(tn + " start sleep ");
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println(tn + " sleep interrupt ");
                            e.printStackTrace();
                        }
                        System.out.println(tn + " end sleep ");
                    }, threadPool);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }
        });

        t.start();

        Thread.sleep(2000);

        run = false;
        while (semaphore.hasQueuedThreads() || semaphore.availablePermits() != threadSize) {
            try {
                System.out.println("waiting stop");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }
        System.out.println("stop");

        threadPool.shutdown();
    }
}

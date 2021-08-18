package com.lzb.concurrent.completablefuture;

import java.util.concurrent.*;

/**
 * 测试CompletableFuture是否为守护线程<br/>
 * 守护线程定义：守护进程不会影响jvm的退出，当jvm只剩余守护进程时，jvm 进行退出!!!
 * Created on : 2021-06-04 10:22
 * @author chenpi 
 */
public class TestDaemen {

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            // CompletableFuture 无声无息地干掉了
            /*CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " start sleep");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end sleep");
            }).whenCompleteAsync((r, e) -> {
                System.out.println("complete");
            });*/
            // 用户线程不会被强行kill
            /*ExecutorService threadPool = Executors.newSingleThreadExecutor();
            threadPool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " start sleep");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " end sleep");
            });
            System.out.println(Thread.currentThread().getName() + " exit ");
            threadPool.shutdown();*/
        });

        t.start();
        Thread.sleep(2000);
        System.out.println("done");
    }
}

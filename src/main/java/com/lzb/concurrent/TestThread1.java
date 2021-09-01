package com.lzb.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <br/>
 * Created on : 2021-09-01 19:12
 *
 * @author lizebin
 */
public class TestThread1 {

    public static void main(String[] args) throws InterruptedException {
        Runnable r = () -> {
            System.out.println(Thread.currentThread().getName() + ": start sleep");
            System.out.println(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.interrupted();
            }
        };
        /*CompletableFuture.runAsync(r, threadPoolTaskExecutor);*/

        //Executors.newSingleThreadExecutor().execute(r);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1,1, 5, TimeUnit.DAYS, new LinkedBlockingQueue<>()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println("shutdown???????");
            }

            void onShutdown() {
                System.out.println("shutdown!!!!!!!!!!!!");
            }
        };
        threadPoolExecutor.execute(r);
        /*new Thread(r).start();*/

        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + " done");
    }

}

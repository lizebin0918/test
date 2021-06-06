package com.lzb.concurrent.completablefuture;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * https://dzone.com/articles/20-examples-of-using-javas-completablefuture<br/>
 * 1.CompletableFuture由一个个CompletableStage组成，每个Stage是上一个Stage的观察者，后面同理
 * Created on : 2021-06-02 16:19
 * @author chenpi 
 */
public class TestCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        test();

        FLAG.set(false);

        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            try {
                Thread.sleep(2500);
                System.out.println("sleep end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return s.toUpperCase();
        });
        try {
            cf.get(1, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("msg:" + cf.get());

        //如果设置守护线程，主线程终止，子线程也会终止，毫无感知
        Thread t = new Thread(() -> {
            try {
                System.out.println(" start sleep");
                Thread.sleep(2500);
                System.out.println(" end sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.setDaemon(true);
        t.start();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("main done");
    }

    static AtomicBoolean FLAG = new AtomicBoolean(true);
    public static void test() {
        new Thread(() -> {
            while (FLAG.get()) {
                CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
                    System.out.println(Thread.currentThread().getName() + " start sleep ");
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " end sleep ");
                }).whenComplete((r, e) -> {
                    System.out.println(Thread.currentThread().getName() + " done ");
                }).exceptionally((e) -> {
                    System.out.println(Thread.currentThread().getName() + " exception ");
                    return null;
                });
            }

            System.out.println("flag:" + FLAG.get());

        }).start();
    }

}

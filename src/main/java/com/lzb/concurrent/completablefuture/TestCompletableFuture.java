package com.lzb.concurrent.completablefuture;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://dzone.com/articles/20-examples-of-using-javas-completablefuture<br/>
 * 1.CompletableFuture由一个个CompletableStage组成，每个Stage是上一个Stage的观察者，后面同理
 * Created on : 2021-06-02 16:19
 *
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

        combine();

        System.out.println(IntStream.iterate(0, i -> i + 1).limit(100).boxed().collect(Collectors.toList()));

        parallel();
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

    public static void combine() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " first task");
            return "Hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " second task");
            return " World";
        }), (s1, s2) -> s1 + s2);

        System.out.println(completableFuture.get());
    }

    public static void parallel() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> 1);
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> "1");
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(task1, task2);

        Void v = combinedFuture.get();
        System.out.println("v = " + v);
        System.out.println("task1 = " + task1.get());
        System.out.println("task2 = " + task2.get());
    }

}

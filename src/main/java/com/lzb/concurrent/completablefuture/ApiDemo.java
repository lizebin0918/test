package com.lzb.concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * api方法<br/>
 * Created on : 2021-06-08 22:41
 *
 * @author lizebin
 */
public class ApiDemo {

    public static void main(String[] args) throws Exception {

        demo();
        System.out.println("-------------------");
        demo1();
        System.out.println("-------------------");
        demo2();
        System.out.println("-------------------");
        demo3();
        System.out.println("-------------------");
        demo4();
    }

    public static void demo() throws ExecutionException, InterruptedException {
        //demo1:直接返回成功结果
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message");
        System.out.println(cf.isDone());
        System.out.println(cf.get());
    }

    /**
     * 异步执行任务，获取结果
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void demo1() throws ExecutionException, InterruptedException {
        //demo2:异步执行任务，默认是Fork-join线程，主线程停止，子线程也会销毁，除非调用join()等待执行完
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("thread name:" + Thread.currentThread().getName());
            System.out.println("isDaemon:" + Thread.currentThread().isDaemon());
        });
        //cf1.join();
        //如果cf1未执行完，会返回false
        System.out.println("cf1.isDnoe()=" + cf1.isDone());

        //等价于 cf1.join()
        Void v = cf1.get();
        System.out.println(v == null);
    }

    /**
     * 连接多个CompletableFuture
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void demo2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            return s.toUpperCase();
        });
        System.out.println(cf.get());
    }

    /**
     * 多个异步任务执行，是否采用同一个线程，相当于串行
     */
    public static void demo3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> cf =
            CompletableFuture.supplyAsync(() -> {
                System.out.println("supply async thread name:" + Thread.currentThread().getName());
                return "message";
            }).thenApplyAsync(s -> {
                System.out.println("then apply async thread name:" + Thread.currentThread().getName());
                return s.toUpperCase();
            });
        String s = cf.get();
        System.out.println(s);
    }

    public static void demo4() throws ExecutionException, InterruptedException {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(
            "thenAccept message")
            .thenAccept(s -> result.append(s));
        System.out.println(cf.get());
        System.out.println(result);
    }

}

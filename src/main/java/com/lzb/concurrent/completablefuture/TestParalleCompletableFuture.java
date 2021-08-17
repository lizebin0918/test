package com.lzb.concurrent.completablefuture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 并行执行任务<br/>
 * Created on : 2021-08-16 07:36
 *
 * 问题：
 * 1.whenCompelete() 是表示正常完成？还是异常完成？
 * 2.如果不指定线程池，默认的ForkJoin是守护线程？
 *
 * @author lizebin
 */
public class TestParalleCompletableFuture {

    public static void main(String[] args) throws InterruptedException {
        //paralleToAll();
        //paralleToAny();
        long start = System.nanoTime();
        paralleToAllForSameResultType();
        long end = System.nanoTime();
        System.out.println((end - start) / (1000 * 1000L));

    }

    /**
     * 执行不同类型任务，最终通过 get() 或者 join() 同步阻塞
     */
    public static void paralleToAll() {
        List<Integer> task1Result = new ArrayList<>();
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            int taskId = 2;
            task(taskId);
            task1Result.add(taskId);
        });
        List<String> task2Result = new ArrayList<>();
        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            int taskId = 4;
            task(taskId);
            task2Result.add(Integer.toString(taskId));
        });
        System.out.println(Thread.currentThread().getName() + " start execute:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        CompletableFuture<Void> main = CompletableFuture.allOf(task1, task2).thenRunAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": done");
        });

        // 主线程阻塞等待结果
        main.join();
        System.out.println(Thread.currentThread().getName() + " end execute:" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // 多个相同类型的任务执行，最终通过join()合并结果集
        /*
        Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
         */
    }

    /**
     * 多个任务执行，返回相同类型的Future，最终合并
     * Note:如果提交任务数多于本机核数，后续的任务
     */
    public static void paralleToAllForSameResultType() {
        int taskCount = 100;
        List<CompletableFuture<String>> tasks = new ArrayList<>(taskCount);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < taskCount; i++) {
            int taskId = i + 1;
            tasks.add(CompletableFuture.supplyAsync(() -> {
                task(taskId);
                return Integer.toString(taskId);
            }));
        }
        System.out.println(tasks.stream().map(CompletableFuture::join).collect(Collectors.joining(" ")));
        threadPool.shutdown();
    }

    public static void paralleToAllForSameResultType1() {
        int taskCount = 16;
        List<CompletableFuture<String>> tasks = new ArrayList<>(taskCount);
        ExecutorService threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                0L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i = 0; i < taskCount; i++) {
            int taskId = i + 1;
            tasks.add(CompletableFuture.supplyAsync(() -> {
                task(taskId);
                return Integer.toString(taskId);
            }));
        }
        System.out.println(allOf(tasks).join());
    }

    public static <T> CompletableFuture<List<T>> allOf(Collection<CompletableFuture<T>> futures) {
        return futures.stream().collect(Collectors.collectingAndThen(
                Collectors.toList(),
                l -> CompletableFuture.allOf(l.toArray(new CompletableFuture[0]))
                        .thenApply(v -> l.stream().map(CompletableFuture::join).collect(Collectors.toList()))
                )
        );
    }

    /**
     * 执行任意一个
     */
    public static void paralleToAny() {
        CompletableFuture<Integer> task1 = CompletableFuture.supplyAsync(() -> {
            int taskId = 2;
            task(taskId);
            return taskId;
        });
        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            int taskId = 4;
            task(taskId);
            return Integer.toString(taskId);
        });

        Object result = CompletableFuture.anyOf(task1, task2).join();

        System.out.println("执行完成:" + result);

    }

    public static void task(int taskId) {
        System.out.println(Thread.currentThread().getName() + ":" + taskId + "-task start, sleep " + taskId + " : "  + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        try {
            Thread.sleep(taskId * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + taskId + "-task end : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

}

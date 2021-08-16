package com.lzb.concurrent.completablefuture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 并行执行任务<br/>
 * Created on : 2021-08-16 07:36
 *
 * @author lizebin
 */
public class TestParalleCompletableFuture {

    public static void main(String[] args) {
        paralleToCombine();
    }


    public static void paralleToCombine() {
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

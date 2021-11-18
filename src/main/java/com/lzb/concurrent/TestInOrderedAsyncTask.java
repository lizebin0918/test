package com.lzb.concurrent;

import lombok.SneakyThrows;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 按顺序获取CompletableFuture结果<br/>
 * Created on : 2021-11-18 10:52
 *
 * @author lizebin
 */
public class TestInOrderedAsyncTask {

    public static void main(String[] args) throws InterruptedException {
        int taskCount = 10;
        LinkedBlockingQueue<List<Integer>> taskQueue = new LinkedBlockingQueue<>(taskCount);
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < taskCount; i++) {
            int sleepSecond = i;
            executor.execute(() -> {
                taskQueue.add(task(sleepSecond));
            });
        }

        while (taskCount > 0) {
            System.out.println("返回：" + taskQueue.take());
            --taskCount;
        }

        System.out.println("done");
    }

    @SneakyThrows
    public static List<Integer> task(int sleepSeconds) {
        System.out.println(Thread.currentThread().getName() + " start sleep ");
        Thread.sleep(sleepSeconds * 1000L);
        System.out.println(Thread.currentThread().getName() + " end sleep ");
        return Collections.singletonList(sleepSeconds);
    }

}

package com.lzb.concurrent.threadpool;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 测试异步执行所有任务，返回不同类型如何区分<br/>
 * Created on : 2021-08-13 09:46
 *
 * @author lizebin
 */
public class TestThreadPoolExecutorInvokeAll {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var threadPool = Executors.newCachedThreadPool();

        // 泛型只能声明成Object
        Callable<Object> t1 = () -> 1;
        Callable<Object> t2 = () -> "1";

        var results = threadPool.invokeAll(Arrays.asList(t1, t2));

        // 最终获取Future也要区分类型
        for (Future<Object> result : results) {
            Object o = result.get();
            System.out.println(o);
        }

        threadPool.shutdown();

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> System.out.println(1));
        Thread.sleep(1000);
        //task2.get();

        CompletableFuture.allOf(CompletableFuture.supplyAsync(() -> 1),
                CompletableFuture.supplyAsync(() -> 1));

    }

}

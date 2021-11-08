package com.lzb.concurrent.completablefuture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * <br/>
 * Created on : 2021-11-08 20:57
 *
 * @author lizebin
 */
public class TestCompletionService {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newCachedThreadPool();
        CompletionService<List<Integer>> completionService = new ExecutorCompletionService<>(ForkJoinPool.commonPool());
        List<Future<List<Integer>>> futureList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futureList.add(completionService.submit(task(1 + ThreadLocalRandom.current().nextInt(10))));
        }
        System.out.println("after submit");
        for (Future<List<Integer>> f : futureList) {
            // 这个方法是阻塞的
            System.out.println("result: " + f.get());
        }
        System.out.println("after get");
        service.shutdown();
    }

    public static Callable<List<Integer>> task(int i) {
        return () -> {
            System.out.println(Thread.currentThread().getName() + "开始睡眠:" + i);
            try {
                Thread.sleep(i * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "结束睡眠:" + i);
            return Collections.singletonList(i);
        };
    }

}

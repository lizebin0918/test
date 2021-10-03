package com.lzb.concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 是否能响应中断<br/>
 * Created on : 2021-08-18 09:53
 *
 * @author lizebin
 */
public class TestInterrupt {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        CompletableFuture.runAsync(() -> {
            System.out.println("start sleep");

            int i = 1 / 0;

            System.out.println("end sleep");
        }, threadPool).exceptionally(e -> {
            System.out.println("exception:" + e);
            System.out.println("--------");
            e.printStackTrace();
            System.out.println("--------");
            return null;
        }).whenComplete((v, e) -> {
            System.out.println("completable future done:" + e);
            e.printStackTrace();
        });

        TimeUnit.SECONDS.sleep(2);
        threadPool.shutdownNow();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("main done");


    }

}

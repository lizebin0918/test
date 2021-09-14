package com.lzb.concurrent.completablefuture;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * TestTimeout<br/>
 * Created on : 2021-09-14 14:18
 *
 * @author lizebin
 */
public class TestTimeout {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // CompletableFuture<List<String>> f = CompletableFuture.supplyAsync(() -> select()).completeOnTimeout(Collections.emptyList(), 5, TimeUnit.SECONDS);
        CompletableFuture<List<String>> f1 = CompletableFuture.supplyAsync(() -> select());
        CompletableFuture<List<String>> f2 = CompletableFuture.supplyAsync(() -> select());

        long start = System.nanoTime();
        CompletableFuture.allOf(f1, f2).get(2, TimeUnit.SECONDS);
        long end = System.nanoTime();

        System.out.println("sleep :" + (end - start) / 1000 / 1000L);

        System.out.println(f1.get());
        System.out.println(f2.get());
    }

    public static List<String> select() {
        Random r = new Random();
        int second = r.nextInt(5);
        System.out.println(Thread.currentThread().getName() + " 睡眠: " + second);
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Arrays.asList("1", "2", "3");
    }

}

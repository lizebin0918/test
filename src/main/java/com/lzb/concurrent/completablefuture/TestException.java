package com.lzb.concurrent.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 测试异常捕获机制<br/>
 * Created on : 2021-08-20 09:46
 *
 * @author lizebin
 */
public class TestException {

    public static void main(String[] args) {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 0;
        }).exceptionally((e) -> {// 可以捕获异常
            System.out.println(Thread.currentThread().getName() + " exceptionally , e = " + e);
            e.printStackTrace();
            return 0;
        }).handleAsync((v, e) -> {// 也可以捕获异常，但是被前面的Completable捕获处理之后，不会往后传
            System.out.println(Thread.currentThread().getName() + " result = " + v);
            System.out.println(Thread.currentThread().getName() + " handler , e = " + e);
            e.printStackTrace();
            return 0;
        });

        Integer result = 0;
        try {
            result = f.get();
        } catch (InterruptedException e) {
            System.out.println("main interrupt");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("main execute exception");
            e.printStackTrace();
        }
        System.out.println("result:" + result);
    }

}

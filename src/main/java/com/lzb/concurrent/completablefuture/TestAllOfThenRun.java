package com.lzb.concurrent.completablefuture;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

/**
 * <br/>
 * Created on : 2023-09-22 17:21
 * @author lizebin
 */
public class TestAllOfThenRun {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("start:" + LocalDateTime.now());
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }),
                CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
        ).thenRun(() -> {
            System.out.println("end:" + LocalDateTime.now());
        });

        System.out.println("开始sleep");

        Thread.sleep(1000000);
    }

}

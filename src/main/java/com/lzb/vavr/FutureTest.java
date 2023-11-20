package com.lzb.vavr;

import io.vavr.concurrent.Future;

/**
 * <br/>
 * Created on : 2023-11-20 20:50
 * @author lizebin
 */
public class FutureTest {

    public static void main(String[] args) {
        Future<Integer> getInt = Future.of(() -> {
            System.out.println(Thread.currentThread() + "1");
            return 88;
        });
        System.out.println(getInt.get());
    }

}

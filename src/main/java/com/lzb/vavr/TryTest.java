package com.lzb.vavr;

import io.vavr.control.Try;

/**
 * <br/>
 * Created on : 2023-01-13 19:44
 * @author lizebin
 */
public class TryTest {

    public static void main(String[] args) {

        //exception
        Try.of(() -> 1 / 0)
                .andThen(r -> System.out.println("and then 1" + r))
                .andThen(r -> System.out.println("and then 2" + r))
                .onFailure(error -> System.out.println("failure" + error.getMessage()))
                .andFinally(() -> {
                    System.out.println("finally");
                });

        // 正常调用
        Try.ofSupplier(() -> 1)
                .andThen(r -> System.out.println("and then 1 = " + (r + 1)))
                .andThen(r -> System.out.println("and then 2 = " + r));

    }

}

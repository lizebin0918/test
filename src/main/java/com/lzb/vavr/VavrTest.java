package com.lzb.vavr;

import io.vavr.control.Try;

/**
 * <br/>
 * Created on : 2023-01-13 19:44
 * @author lizebin
 */
public class VavrTest {

    public static void main(String[] args) {

        //try
        Try.of(() -> 1 / 0)
                .andThen(r -> System.out.println("and then " + r))
                .onFailure(error -> System.out.println("failure" + error.getMessage()))
                .andFinally(() -> {
                    System.out.println("finally");
                });

    }

}

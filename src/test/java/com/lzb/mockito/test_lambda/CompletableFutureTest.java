package com.lzb.mockito.test_lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

/**
 * <br/>
 * Created on : 2023-08-22 20:21
 * @author lizebin
 */
class CompletableFutureTest {

    @Test
    @DisplayName("test")
    void should_() {

        try (MockedStatic<CompletableFuture> completableFuture = Mockito.mockStatic(CompletableFuture.class)) {
            completableFuture.when(() -> CompletableFuture.runAsync(any(Runnable.class))).thenAnswer((invocation -> {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }));
            completableFuture.when(() -> CompletableFuture.supplyAsync(any(Supplier.class))).thenAnswer((invocation -> {
                //return CompletableFuture.supplyAsync(invocation.getArgument(0, Supplier.class), Runnable::run);
                System.out.println("int");
                return CompletableFuture.completedFuture(invocation.getArgument(0, Supplier.class).get());
            }));

            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " this is a");
            });

            int i = CompletableFuture.supplyAsync(() -> {
                System.out.println("execute supplier");
                return 1;
            }).join();
            System.out.println("this is a " + i);
        }
    }

    /*@Test
    @DisplayName("test")
    void should_1() {
        try (MockedStatic<CompletableFuture> completableFuture = Mockito.mockStatic(CompletableFuture.class)) {
            completableFuture.when(() -> CompletableFuture.runAsync(any(Runnable.class))).thenAnswer((invocation -> {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }));
            completableFuture.when(() -> CompletableFuture.supplyAsync(any(Supplier.class))).thenAnswer((invocation -> {
                return CompletableFuture.completedFuture(invocation.getArgument(0, Supplier.class).get());
            }));
            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " this is a");
            });
            int i = CompletableFuture.supplyAsync(() -> 1).join();
            System.out.println("this is a " + i);
        }
    }*/

}

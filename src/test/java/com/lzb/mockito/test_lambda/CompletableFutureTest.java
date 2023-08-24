package com.lzb.mockito.test_lambda;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

/**
 * <br/>
 * Created on : 2023-08-22 20:21
 * @author lizebin
 */
class CompletableFutureTest {

    @Test
    @DisplayName("mock CompletableFuture静态方法+实例方法")
    void should_() {

        try (MockedStatic<CompletableFuture> completableFuture = Mockito.mockStatic(CompletableFuture.class)) {

            completableFuture.when(() -> CompletableFuture.runAsync(any(Runnable.class))).thenAnswer((invocation -> {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }));
            completableFuture.when(() -> CompletableFuture.supplyAsync(any(Supplier.class))).thenAnswer((invocation -> {
                Object supplierValue = invocation.getArgument(0, Supplier.class)
                        .get();
                CompletableFuture<Object> f = mock(CompletableFuture.class);
                // 用spy无效
                //CompletableFuture<Object> f = spy(CompletableFuture.class);
                when(f.join()).thenReturn(supplierValue);
                return f;
            }));

            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " this is a");
            });

            int i = CompletableFuture.supplyAsync(() -> {
                System.out.println("execute supplier");
                return 1;
            }).join();
            Assertions.assertEquals(1, i);
        }
    }

    @Test
    @DisplayName("执行静态代码，如果需要执行实例方法，需要新建一个mock实例")
    void should_1() {
        long supplierValue1 = 1L;
        CompletableFuture<Long> f1 = new CompletableFuture<>();
        System.out.println("supplierValue1: " + supplierValue1);
        f1.complete(supplierValue1);
        Assertions.assertEquals(supplierValue1, f1.join());

        try (MockedStatic<CompletableFuture> completableFuture = Mockito.mockStatic(CompletableFuture.class)) {

            long supplierValue2 = 2L;
            CompletableFuture<Long> f2 = mock(CompletableFuture.class);
            when(f2.join()).thenReturn(supplierValue2);
            System.out.println("supplierValue2: " + supplierValue2);
            f2.complete(supplierValue2);
            Assertions.assertEquals(supplierValue2, f2.join());
        }
    }

    @Test
    @DisplayName("mock CompletableFuture静态方法+实例方法——2")
    void should_2() {

        try (MockedStatic<CompletableFuture> completableFuture = Mockito.mockStatic(CompletableFuture.class, withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS))) {

            completableFuture.when(() -> CompletableFuture.runAsync(any(Runnable.class))).thenAnswer((invocation -> {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }));
            completableFuture.when(() -> CompletableFuture.supplyAsync(any(Supplier.class))).thenAnswer((invocation -> {
                Object supplierValue = invocation.getArgument(0, Supplier.class)
                        .get();
                return CompletableFuture.completedFuture(supplierValue);
            }));

            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + " this is a");
            });

            int i = CompletableFuture.supplyAsync(() -> {
                System.out.println("execute supplier");
                return 1;
            }).join();
            Assertions.assertEquals(1, i);

            int r = CompletableFuture
                    .supplyAsync(() -> {
                        System.out.println(Thread.currentThread().getName() + " 1 ");
                        return 1;
                    })
                    .thenCombine(CompletableFuture.supplyAsync(() -> {
                                System.out.println(Thread.currentThread().getName() + " 2 ");
                                return 2;
                            }),
                            (j, k) -> j + k).join();
            Assertions.assertEquals(3, r);
        }
    }

}

package com.lzb.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.*;

import java.util.*;
import java.util.concurrent.*;

/**
 * 测试多任务进行，取消直接返回<br/>
 * Created on : 2021-10-30 11:40
 *
 * @author lizebin
 */
public class TestInOrderCompletion {

    /*public static void main(String[] args) {
        Random r = new Random();
        List<CompletableFuture<Integer>> taskList = Arrays.asList(task(3), task(1), task(5));

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(taskList.toArray(CompletableFuture[]::new));
        CompletableFuture<List<Integer>> allCompletableFuture = allFutures.thenApply(future -> {
            return taskList.stream().map(completableFuture -> completableFuture.join())
                    .collect(Collectors.toList());
        });

        System.out.println(allCompletableFuture);

    }*/

    public static void main(String[] args) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(ForkJoinPool.commonPool());
        ImmutableList<ListenableFuture<Integer>> listenableFutureList = Futures.inCompletionOrder(Lists.newArrayList(
                        listeningExecutorService.submit(() -> {
                            try {
                                Thread.sleep(4000);
                                System.out.println(Thread.currentThread().getName() + "@666");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }, 1),
                        listeningExecutorService.submit(() -> {
                            try {
                                Thread.sleep(2000);
                                System.out.println(Thread.currentThread().getName() + "@888");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }, 2),
                        listeningExecutorService.submit(() -> {
                            try {
                                Thread.sleep(1000);
                                System.out.println(Thread.currentThread().getName() + "@777");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }, 1)
                )
        );
        listenableFutureList.forEach(p -> {
            try {
                System.out.println(Thread.currentThread().getName() + p.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 多个 executor 异步查询 oid
        /*ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(ForkJoinPool.commonPool());
        List<ListenableFuture<Set<Integer>>> oidSetFutureList = new ArrayList<>();
        for (AbstractOidQueryExecutor oidQueryExecutor : oidQueryExecutorList) {
            if (oidQueryExecutor.inFilter(orderQuery)) {
                oidSetFutureList.add(listeningExecutorService.submit(() -> oidQueryExecutor.listOid(orderQuery)));
            }
        }

        for (var future : Futures.inCompletionOrder(oidSetFutureList)) {
            try {
                Set<Integer> oidSet = future.get();
                if (DataUtils.isEmpty(oidSet)) {
                    return PageResponse.of(Collections.emptyList(), 0, orderQuery.getPageSize());
                }
                Set<Integer> targetOidSet = Optional.ofNullable(orderQuery.getOidSet()).orElse(oidSet);
                Set<Integer> intersection = Sets.intersection(targetOidSet, oidSet);
                orderQuery.setOidSet(intersection);
            } catch (Exception e) {
                log.error("订单查询异常 参数 {}", JSON.toJSONString(orderQuery));
                return PageResponse.of(Collections.emptyList(), 0, orderQuery.getPageSize());
            }
        }*/
    }

    public static CompletableFuture<Integer> task(int i) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ":开始睡眠" + i + "秒");
            try {
                Thread.sleep(i * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ":结束睡眠" + i + "秒");
            return i;
        });
    }

}

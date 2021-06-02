package com.lzb.concurrent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 每个线程读一个队列，N个线程写。实现消息分组，单线程消费<br/>
 * Created on : 2021-06-02 23:36
 * @author lizebin
 */
public class TestSingleThreadReadGroupMessage {

    //模拟redis
    private static Map<String, ConcurrentLinkedQueue<String>> redisList =
        new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {

        //每个消费者消费一个队列，producer分别对应多个队列
        int consumerSize = 4, producerSize = 8;

        for (int i = 0; i < producerSize; i++) {
            CompletableFuture.runAsync(() -> {
                long threadId = Thread.currentThread().getId();
                String queue = Objects.toString(threadId % consumerSize);
                System.out.println("threadId=" + threadId);
                System.out.println("queue=" + queue);
                while (true) {
                    push(queue, queue);
                    System.out.println(Thread.currentThread().getName() +
                                           ":" + redisList.get(queue).size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        Thread.sleep(2500);
        System.out.println("队列个数:" + redisList.size());

        for (int j = 0; j < consumerSize; j++) {
            int q = j;
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String element = pop(Objects.toString(q));
                    if (Objects.isNull(element)) continue;
                    System.out.println("第" + q + "个队列:" + element);
                }
            }).start();
        }

        Thread.sleep(10000000);


    }

    public static void push(String queue, String element) {
        redisList.computeIfAbsent(queue, (key) -> {
            return new ConcurrentLinkedQueue<>();
        }).add(element);
    }

    public static String pop(String queue) {
        return redisList.getOrDefault(queue, new ConcurrentLinkedQueue<>()).poll();
    }


}

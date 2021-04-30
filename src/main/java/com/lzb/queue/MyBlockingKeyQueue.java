package com.lzb.queue;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * FIFO队列，按照Key合并，例如"key1"在对头，再来一个"key1"，key1对应的value被覆盖，pop() 返回 k1 的值<br/>
 * Created on : 2021-04-30 09:43
 * @author chenpi 
 */
public class MyBlockingKeyQueue<T> {

    private ConcurrentHashMap<String, Node<T>> map = new ConcurrentHashMap<>();

    private LinkedBlockingQueue<Node<T>> queue;

    public MyBlockingKeyQueue(int capacity) {
        queue = new LinkedBlockingQueue<>(capacity);
    }

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * 是否被覆盖
     * @param key
     * @param value
     * @return
     */
    public boolean push(String key, T value) {
        Node node = new Node(key, value);
        Node oldNode = map.put(key, node);
        if (Objects.isNull(oldNode)) {
            queue.offer(node);
        }
        return Objects.nonNull(oldNode);
    }

    public Optional<T> pop() {
        Node<T> node = queue.poll();
        if (Objects.nonNull(node)) {
            String key = node.key;
            return Optional.ofNullable(map.getOrDefault(key, new Node<T>()).value);
        }
        return Optional.empty();
    }

    public void forEach(Consumer<T> consumer) {
        queue.forEach(node -> consumer.accept(node.value));
    }

    private static class Node<T> {
        String key;
        T value;

        Node() {

        }

        Node(String key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        MyBlockingKeyQueue<String> queue = new MyBlockingKeyQueue<>(1000);
        for (int i=0; i<10; i++) {
            queue.push(Objects.toString(i), Objects.toString(i));
        }
        queue.forEach(System.out::println);

        queue.push("1", "1000");

        System.out.println("pop-------->");
        System.out.println(queue.pop().get());
        System.out.println(queue.pop().get());
    }

}

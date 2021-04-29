package com.lzb.concurrent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * 阻塞队列<br/>
 * Created on : 2021-04-29 17:59
 * @author chenpi 
 */
public class MyLinkedBlockingQueue<T> {

    private LongAdder size = new LongAdder();

    /**
     * 虚拟头节点
     */
    private volatile Node<T> head = new Node<>(null, null, null);
    private volatile Node<T> tail = head;

    private ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    private int capacity;

    public MyLinkedBlockingQueue() {
        this.capacity = Integer.MAX_VALUE;
    }

    public MyLinkedBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 遍历
     * @param consumer
     */
    public void forEach(Consumer<T> consumer) {
        if (getNotEmpty()) return;
        Node<T> next = head.next;
        while (Objects.nonNull(next)) {
            consumer.accept(next.value);
            next = next.next;
        }
    }

    /**
     * 队头出队
     * @return
     */
    public T pop() {
        lock.lock();
        Node<T> retNode = null;
        try {
            while (getNotEmpty()) notEmpty.await(1, TimeUnit.SECONDS);
            retNode = head.next;
            head.next = retNode.next;
            retNode.pre = head;
            size.add(-1);
            notFull.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return Objects.nonNull(retNode) ? retNode.value : null;
    }

    /**
     * 队尾入队
     * @param value
     */
    public void push(T value) {
        lock.lock();
        try {
            while (size.intValue() == capacity) notFull.await(1, TimeUnit.SECONDS);
            Node<T> pre = tail;
            Node<T> node = new Node<>(value, null, pre);
            pre.next = node;
            tail = node;
            size.add(1);
            notEmpty.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public boolean getNotEmpty() {
        return head == tail || size.intValue() == 0;
    }

    public int size() {
        return size.intValue();
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> pre;

        Node(T value, Node<T> next, Node<T> pre) {
            this.value = value;
            this.next = next;
            this.pre = pre;
        }

    }

    public static void main(String[] args) {

        MyLinkedBlockingQueue<String> queue = new MyLinkedBlockingQueue<>(2);

        new Thread(() -> {
            int i = 10;
            while (--i > 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("print queue.size() = " + queue.size());
                System.out.println(Thread.currentThread().getName() + ":print queue.element = " + queue.pop());
            }
        }).start();

        queue.push("1");
        queue.push("2");
        queue.push("3");
        queue.push("4");
        queue.push("5");
        queue.forEach(item -> System.out.println(item));
        System.out.println(queue.size());
    }


}

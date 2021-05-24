package com.lzb.interview;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A、B、C 3个线程分别打印100个数，A线程打印0,1,2，B线程打印3,4,5，C线程打印6,7,8....<br/>
 * Created on : 2021-05-24 21:24
 * @author lizebin
 */
public class ThreadPrintNumber5 {

    private static final ReentrantLock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    private static int[] array = new int[100];

    static {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }

    private static final int threadSize = 3;

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new A());
        Thread b = new Thread(new B());
        Thread c = new Thread(new C());

        a.start();
        b.start();
        c.start();

        a.join();
        b.join();
        c.join();

        System.out.println("done");
    }

    private static class A implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i=0; i<array.length; i = i + 2 * threadSize) {
                    int start = i, end = i + threadSize;
                    while (start < end && start < array.length) {
                        System.out.println(Thread.currentThread().getName() + "--------->" + array[start++]);
                    }
                    conditionB.signal();
                    conditionA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class B implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i=3; i<array.length; i = i + 2 * threadSize) {
                    int start = i, end = i + threadSize;
                    while (start < end && start < array.length) {
                        System.out.println(Thread.currentThread().getName() + "--------->" + array[start++]);
                    }
                    conditionC.signal();
                    conditionB.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private static class C implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                for (int i=6; i<array.length; i = i + 2 * threadSize) {
                    int start = i, end = i + threadSize;
                    while (start < end && start < array.length) {
                        System.out.println(Thread.currentThread().getName() + "--------->" + array[start++]);
                    }
                    conditionA.signal();
                    conditionC.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
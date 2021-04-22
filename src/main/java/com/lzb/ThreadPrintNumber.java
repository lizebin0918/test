package com.lzb;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 100个整数，三个线程顺序打印<br/>
 * Created on : 2021-04-22 16:49
 * @author chenpi 
 */
public class ThreadPrintNumber {

    public static final int SIZE = 100;
    private static final int[] ARRAY = new int[SIZE];
    static {
        for (int i=0; i<SIZE; i++) {
            ARRAY[i] = i;
        }
    }

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    private static Condition condition3 = lock.newCondition();

    public static void main(String[] args) {
        int threadSize = 3;
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                int i = 0;
                while (true) {
                    if (i > 99) {
                        breakLoop();
                        break;
                    }
                    print(i);
                    condition2.signal();
                    condition1.await();
                    i = i + threadSize;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "线程-1");
        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                int i = 1;
                while (true) {
                    if (i > 99) {
                        breakLoop();
                        break;
                    }
                    print(i);
                    condition3.signal();
                    condition2.await();
                    i = i + threadSize;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "线程-2");
        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                int i = 2;
                while (true) {
                    if (i > 99) {
                        breakLoop();
                        break;
                    }
                    print(i);
                    condition1.signal();
                    condition3.await();
                    i = i + threadSize;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "线程-3");
        t1.start();
        t2.start();
        t3.start();
    }


    private static void print(int i) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + ARRAY[i]);
    }

    private static void breakLoop() {
        System.out.println(Thread.currentThread().getName() + " 退出");
        condition1.signalAll();
        condition2.signalAll();
        condition3.signalAll();
    }
}

package com.lzb.concurrent;
/**
 * 测试：一共3个线程，线程1执行完，再执行线程2，如此类推<br/>
 * Created on : 2021-06-04 21:18
 * @author lizebin
 */
public class TestThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            String tn = Thread.currentThread().getName();
            System.out.println(tn + " start sleep ");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tn + " end sleep ");
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String tn = Thread.currentThread().getName();
            System.out.println(tn + " start sleep ");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tn + " end sleep ");
        }, "t2");

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String tn = Thread.currentThread().getName();
            System.out.println(tn + " start sleep ");
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(tn + " end sleep ");
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        t3.join();
    }

}

package com.lzb.concurrent.dead_lock;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 哲学家就餐问题死锁<br/>
 * Created on : 2021-05-25 22:19
 * @author lizebin
 */
public class DiningPhilosophers {

    static CountDownLatch countDownLatch = new CountDownLatch(5);

    public static class Philosopher implements Runnable {

        private Object left, right;

        private Philosopher(Object left, Object right) {
            this.left = left;
            this.right = right;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                doAction("thinking");
                synchronized (left) {
                    doAction("pick up left" + left);
                    /*
                    countDownLatch.countDown();
                    countDownLatch.await();
                    doAction("waiting is end");
                     */
                    synchronized (right) {
                        doAction("pick up right" + right);
                        doAction("eating");
                        doAction("pick down right" + right);
                    }
                    doAction("pick down left" + left);
                }
            }
        }

        private void doAction(String action) throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " " + action);
            Thread.sleep((long) (ThreadLocalRandom.current().nextInt(10) * 200L));
        }
    }

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[5];
        Object[] chopsticks = new Object[philosophers.length];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Object();
        }
        for (int i = 0; i < philosophers.length; i++) {
            //这样可以防止越界
            Object left = chopsticks[i], right =
                chopsticks[(i + 1) % chopsticks.length];
            philosophers[i] = new Philosopher(left, right);
            new Thread(philosophers[i], "哲学家-" + (i + 1)).start();
        }

    }

}

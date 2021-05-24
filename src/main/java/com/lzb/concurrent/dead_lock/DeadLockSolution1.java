package com.lzb.concurrent.dead_lock;

import com.alibaba.fastjson.JSON;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 死锁解决<br/>
 * Created on : 2021-05-24 22:55
 *
 * @author lizebin
 */
public class DeadLockSolution1 {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("a", 100);
        Account b = new Account("b", 100);
        Account c = new Account("c", 50);

        Thread a2b = new Thread(new DeadLockSolution1.Transfer(a, b, 10));
        Thread b2a = new Thread(new DeadLockSolution1.Transfer(b, a, 5));
        Thread c2a = new Thread(new DeadLockSolution1.Transfer(c, a, 10));
        Thread c2b = new Thread(new DeadLockSolution1.Transfer(c, b, 5));
        Thread a2c = new Thread(new DeadLockSolution1.Transfer(a, c, 10));
        Thread b2c = new Thread(new DeadLockSolution1.Transfer(b, c, 10));

        a2b.start();
        b2a.start();
        c2a.start();
        c2b.start();
        a2c.start();
        b2c.start();

        a2b.join();
        b2a.join();
        c2a.join();
        c2b.join();
        a2c.join();
        b2c.join();

        System.out.println("----------------");

        System.out.println("a账户余额:" + JSON.toJSONString(a));
        System.out.println("b账户余额:" + JSON.toJSONString(b));
        System.out.println("c账户余额:" + JSON.toJSONString(c));

    }

    private static class Transfer implements Runnable {

        private Account a, b;
        private int amount;

        /**
         * A账户向B账户转账，amount为转账金额
         *
         * @param a
         * @param b
         * @param amount
         */
        public Transfer(Account a, Account b, int amount) {
            this.a = a;
            this.b = b;
            this.amount = amount;
        }

        @Override
        public void run() {
            int ahc = System.identityHashCode(a), bhc = System.identityHashCode(b);
            //保证较大值在前，twins lock
            String lock = (ahc > bhc ? ahc + "_" + bhc : bhc + "_" + ahc).intern();
            synchronized (lock) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(5) * 500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a.sub(amount);
                b.add(amount);
            }
            System.out.println("转账完成:转账金额:" + amount);
            System.out.println("转出账户余额:" + JSON.toJSONString(a));
            System.out.println("转入账户余额:" + JSON.toJSONString(b));
        }
    }

}

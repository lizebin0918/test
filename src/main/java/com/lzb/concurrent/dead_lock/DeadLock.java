package com.lzb.concurrent.dead_lock;

import com.alibaba.fastjson.JSON;

/**
 * 死锁演示<br/>
 * Created on : 2021-05-24 22:45
 * @author lizebin
 */
public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("a", 100);
        Account b = new Account("b", 100);

        Thread a2b = new Thread(new Transfer(a, b, 10));
        Thread b2a = new Thread(new Transfer(b, a, 10));

        a2b.start();
        b2a.start();

        a2b.join();
        b2a.join();

    }

    private static class Transfer implements Runnable {

        private Account a, b;
        private int amount;

        /**
         * A账户向B账户转账，amount为转账金额
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
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + "获取锁:" + JSON.toJSONString(a));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    a.sub(amount);
                    b.add(amount);
                }
            }
            System.out.println("转账完成:转账金额:" + amount);
            System.out.println("转出账户余额:" + JSON.toJSONString(a));
            System.out.println("转入账户余额:" + JSON.toJSONString(b));
        }
    }

}

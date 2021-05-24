package com.lzb.concurrent.dead_lock;

import com.alibaba.fastjson.JSON;

/**
 * 死锁解决<br/>
 * Created on : 2021-05-24 22:55
 * @author lizebin
 */
public class DeadLockSolution {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account("a", 100);
        Account b = new Account("b", 100);

        Thread a2b = new Thread(new DeadLockSolution.Transfer(a, b, 10));
        Thread b2a = new Thread(new DeadLockSolution.Transfer(b, a, 10));

        a2b.start();
        b2a.start();

        a2b.join();
        b2a.join();

        System.out.println("----------------");

        System.out.println("a账户余额:" + JSON.toJSONString(a));
        System.out.println("b账户余额:" + JSON.toJSONString(b));

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
            int ahc = System.identityHashCode(a), bhc = System.identityHashCode(b);
            if (ahc > bhc) {
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
            } else if (ahc < bhc) {
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + "获取锁:" + JSON.toJSONString(a));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (a) {
                        a.sub(amount);
                        b.add(amount);
                    }
                }
            } else {
                //hash碰撞
                synchronized (this) {
                    synchronized (a) {
                        synchronized (b) {
                            a.sub(amount);
                            b.add(amount);
                        }
                    }
                }
            }
            System.out.println("转账完成:转账金额:" + amount);
            System.out.println("转出账户余额:" + JSON.toJSONString(a));
            System.out.println("转入账户余额:" + JSON.toJSONString(b));
        }
    }

}

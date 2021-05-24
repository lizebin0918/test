package com.lzb.concurrent.dead_lock;
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

}

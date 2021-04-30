package com.lzb.interview;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试中断<br/>
 * Created on : 2021-04-30 14:03
 * @author chenpi 
 */
public class TestLockSupportInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            int i = 10;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + ":running");
                //第一次中断状态为false，表示未被中断
                System.out.println("isInterrupt :" + Thread.currentThread().isInterrupted());
                //当调用interrupt方法时，会把中断状态设置为true，然后park方法会去判断中断状态，如果为true，就直接返回，然后往下继续执行，并不会抛出异常。注意，这里并不会清除中断标志。
                LockSupport.park(Thread.currentThread());
                //被中断，中断状态为true，表示已经被中断
                System.out.println("isInterrupt :" + Thread.currentThread().isInterrupted());
                //重置，对于LockSupport无效
                Thread.currentThread().interrupt();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("t2 interrupt t1");
                t1.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("done");
    }

}

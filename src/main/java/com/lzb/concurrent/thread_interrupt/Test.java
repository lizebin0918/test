package com.lzb.concurrent.thread_interrupt;
/**
 * sleep 被中断，抛异常，会自动重置中断标志位，isInterrupted 是 false
 * 如果要中断线程，可以调用：Thread.currentThread().interrupt()；
 * 如果要返回当前中断标志位，并复位：Thread.interrupted()
 * 如果线程休眠，关闭应用：Process finished with exit code 130 (interrupted by signal 2: SIGINT)
 *
 * Created on : 2021-05-17 10:49
 * @author chenpi 
 */
public class Test {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("线程被中断了，退出");
                    break;
                }
                System.out.println("1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    try {
                        Thread.sleep(100000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                    System.out.println("sleep 被中断");
                    //Thread.currentThread().interrupt();
                }
            }
        });
        t.start();
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //t.interrupt();
    }

}

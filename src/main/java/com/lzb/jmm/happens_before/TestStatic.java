package com.lzb.jmm.happens_before;

/**
 * 实验证明，static变量也会缓存副本<br/>
 * Created on : 2022-01-13 23:23
 *
 * @author lizebin
 */
public class TestStatic {

    static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            while(flag) {

            }
        }).start();

        // 保证第一个线程先启动
        long i = 100000L;
        while(--i > 0) {

        }

        new Thread(() -> {
            flag = false;
        }).start();

    }

}

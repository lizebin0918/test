package com.lzb.jdk.threadlocal;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 并发随机数测试类<br/>
 * Created on : 2022-02-09 22:27
 *
 * @author lizebin
 */
public class ThreadLocalRandomTest {

    /**
     * 错误示例
     *
     * @param args
     */
    public static void main(String[] args) {

        //ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
        // 多线程输出随机数程序
        for (int i = 0; i < 4; ++i) {
            new Thread(() -> {
                for (int j = 0; j < 5; ++j) {
                    System.out.println(Thread.currentThread().getName() + "第" + j + "个:" + ThreadLocalRandom.current()
                            .nextInt(1000));
                }
            }, "thread" + i).start();
        }


    }

}

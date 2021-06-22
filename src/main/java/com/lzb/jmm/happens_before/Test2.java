package com.lzb.jmm.happens_before;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个线程往list添加，一个线程从list读取，一旦list数量等于5就退出<br/>
 * Created on : 2021-03-28 22:41
 * @author lizebin
 */
public class Test2 {

    public static void main(String[] args) throws InterruptedException {
        Test2 test = new Test2();
        test.run();

    }

    volatile List<Object> list = new ArrayList<>();

    public void run() throws InterruptedException {
        //add
        Thread add = new Thread(() -> {
            for (int i=0; i<10; i++) {
                list.add(new Object());
                System.out.println("add " + list.size());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        add.start();

        //size
        Thread size = new Thread(() -> {
            while (true) {
                if (list.size() == 5) {
                    break;
                }
            }
            System.out.println("元素达到5个退出");
        });
        size.start();

        add.join();
        size.join();

        System.out.println("exit");
    }

}

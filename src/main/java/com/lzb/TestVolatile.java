package com.lzb;

import java.util.ArrayList;
import java.util.List;

/**
 * volitale除了修饰原生类型，如果是引用类型的成员变量也会保持对应语义？<br/>
 * Created on : 2021-03-29 14:17
 * @author chenpi 
 */
public class TestVolatile {
    private ArrayList myList = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        TestVolatile t = new TestVolatile();
        //添加元素
        Thread addThread = new Thread(() -> {
            for (int i=0; i<10; i++) {
                t.myList.add(new Object());
                System.out.println("myList.size() = " + t.myList.size());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        });
        addThread.start();
        //查询元素个数是否等于5
        Thread sizeThread = new Thread(() -> {
            while (true) {
                if (t.myList.size() == 5) {
                    break;
                }
            }
            System.out.println("size thread stop");
        });
        sizeThread.start();

        addThread.join();
        sizeThread.join();
    }
}

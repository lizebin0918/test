package com.lzb.happens_before;

import java.util.*;

/**
 * volitale除了修饰原生类型，如果是引用类型的成员变量也会保持对应语义？<br/>
 * Created on : 2021-03-29 14:17
 * @author chenpi 
 */
public class TestVolatile {
    //private volatile ArrayList<String> myList = new ArrayList<>();遵循happen-before原则
    private ArrayList<String> myList = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        TestVolatile t = new TestVolatile();
        //添加元素
        Thread addThread = new Thread(() -> {
            for (int i=0; i<10; i++) {
                t.myList.add("" + i);
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
                //因为System.out.println底层采用synchronized关键，遵循happens-before原则，所以能看到list的变化
                //System.out.println("size = " + t.myList.size());
                if (t.myList.size() == 5) {
                    System.out.println("size thread stop");
                    break;
                }
            }
        });
        sizeThread.start();

        addThread.join();
        sizeThread.join();
    }
}

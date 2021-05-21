package com.lzb.happens_before;

import java.util.*;

/**
 * volitale除了修饰原生类型，如果是引用类型的成员变量也会保持对应语义？<br/>
 * 1.sizeThread线程加上System.out.println()，遵循happens-before原则
 * 2.sizeThread读取一个volatile变量，也可以"看到"之前的变量变化
 * Created on : 2021-03-29 14:17
 * @author chenpi 
 */
public class TestVolatile {
    volatile int number = 0;
    //private volatile ArrayList<String> myList = new ArrayList<>();遵循happen-before原则
    private ArrayList<String> myList = new ArrayList<>();
    public static void main(String[] args) throws InterruptedException {
        TestVolatile t = new TestVolatile();
        //添加元素
        Thread addThread = new Thread(() -> {
            for (int i=0; i<10; i++) {
                t.myList.add("" + i);
                System.out.println("myList.size() = " + t.myList.size());
                System.out.println("number = " + t.number++);
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
                //如果number变量加上volatile修饰，t.myList的修改，对后续的线程可见
                int n = t.number;
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

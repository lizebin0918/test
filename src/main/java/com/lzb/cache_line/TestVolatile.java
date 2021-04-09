package com.lzb.cache_line;

import java.util.*;

/**
 * volitale除了修饰原生类型，如果是引用类型的成员变量也会保持对应语义？<br/>
 * Created on : 2021-03-29 14:17
 * @author chenpi 
 */
public class TestVolatile {
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
                if (t.myList.size() == 5) {
                    break;
                }
            }
            System.out.println("size thread stop");
        });
        sizeThread.start();

        //查看元素是否写到Object[]
        Thread lookThread = new Thread(() -> {
            while (true) {
                if (t.myList.toArray().length == 5) {
                    break;
                }
            }
            System.out.println(t.myList.size());
        });
        lookThread.start();

        addThread.join();
        sizeThread.join();
        lookThread.join();
    }
}

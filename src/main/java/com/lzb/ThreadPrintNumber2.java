package com.lzb;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 整形数组，多线程打印，每个线程打印10个数，直到打印完<br/>
 * Created on : 2021-04-26 11:27
 * @author chenpi 
 */
public class ThreadPrintNumber2 {

    public static final int SIZE = 1000;
    private static final List<Integer> LIST = new ArrayList<>();
    static {
        for (int i=0; i<SIZE; i++) {
            LIST.add(i);
        }
    }

    private static volatile int startIndex;

    private static volatile int length = 10;

    public static void main(String[] args) {
        int threadSize = 10;
        for (int i=0; i<threadSize; i++) {
            new Thread(() -> {
                int nextIndex = nextIndex();
                int startIndex = nextIndex - length;
                int endIndex = nextIndex;
                print(startIndex, endIndex);
            }, "线程-" + i).start();
        }
    }

    public static synchronized int nextIndex() {
        startIndex += length;
        return startIndex;
    }

    public static void print(int startIndex, int endIndex) {
        System.out.println(Thread.currentThread().getName() + ">>>>>" + JSON.toJSONString(LIST.subList(startIndex, endIndex)));
    }


}

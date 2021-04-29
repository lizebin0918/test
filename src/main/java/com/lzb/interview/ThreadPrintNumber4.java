package com.lzb.interview;

import com.alibaba.fastjson.JSON;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 整形数组，多线程打印，每个线程打印10个数，直到打印完<br/>
 * Created on : 2021-04-26 11:27
 * @author chenpi 
 */
public class ThreadPrintNumber4 {

    public static final int SIZE = 1000;
    private static final List<Integer> LIST = new ArrayList<>();
    static {
        for (int i=0; i<SIZE; i++) {
            LIST.add(i);
        }
    }

    private volatile int startIndex = 0;

    private static volatile int length = 10;

    public static void main(String[] args) {
        ThreadPrintNumber4 instance = new ThreadPrintNumber4();
        int threadSize = 10;
        for (int i=0; i<threadSize; i++) {
            new Thread(() -> {
                int nextIndex = instance.nextIndexWithCas();
                int startIndex = nextIndex - length;
                int endIndex = nextIndex;
                print(startIndex, endIndex);
            }, "线程-" + i).start();
        }

        int i = 2;
        System.out.println(5 & ~i);
    }

    public static void print(int startIndex, int endIndex) {
        System.out.println(Thread.currentThread().getName() + ">>>>>" + JSON.toJSONString(LIST.subList(startIndex, endIndex)));
    }

    public int nextIndexWithCas() {
        while (true) {
            int nextIndex = startIndex + length;
            if (unsafe.compareAndSwapInt(this, startIndexOffset, startIndex, nextIndex)) {
                return nextIndex;
            }
        }
    }

    private static Unsafe unsafe;
    private static long startIndexOffset = 0;

    static {
        try {
            // 使用反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可读取
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            Field startIndexField = ThreadPrintNumber4.class.getDeclaredField("startIndex");
            startIndexOffset = unsafe.objectFieldOffset(startIndexField);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

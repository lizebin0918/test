package com.lzb;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 整形数组，多线程打印，每个线程打印10个数，直到打印完<br/>
 * Created on : 2021-04-26 11:27
 * @author chenpi 
 */
public class ThreadPrintNumber3 {

    public static final int SIZE = 1000;
    private static final List<Integer> LIST = new ArrayList<>();
    static {
        for (int i=0; i<SIZE; i++) {
            LIST.add(i);
        }
    }

    private static AtomicInteger startIndex = new AtomicInteger(0);

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

    public static void print(int startIndex, int endIndex) {
        System.out.println(Thread.currentThread().getName() + ">>>>>" + JSON.toJSONString(LIST.subList(startIndex, endIndex)));
    }

    public static int nextIndex() {
        return startIndex.addAndGet(length);
    }



    /*private static AtomicReference<Integer> nextIndexReference = new AtomicReference<>(0);
    public static int nextIndexWithCas() {
        while (true) {
            System.out.println("startIndex=" + startIndex);
            System.out.println("nextIndexReference.get()=" + nextIndexReference.get());
            startIndex += length;
            if (nextIndexReference.compareAndSet(startIndex, )) {
                return startIndex;
            }
        }
    }*/

    /*
    public static int nextIndexWithCas() {
        while (true) {
            int nextIndex = startIndex + length;
            if (unsafe.compareAndSwapInt(ThreadPrintNumber2.class, startIndexOffset, startIndex, nextIndex)) {
                return nextIndex;
            }
        }
    }

    private static Unsafe unsafe;
    private static final long startIndexOffset;

    static {
        try {
            // 使用反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可读取
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            startIndexOffset = unsafe.objectFieldOffset
                (ThreadPrintNumber2.class.getDeclaredField("startIndex"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    */

}

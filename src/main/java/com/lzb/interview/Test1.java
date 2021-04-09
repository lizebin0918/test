package com.lzb.interview;

import java.util.ArrayList;
import java.util.List;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {

            int times = 100;
            while (--times > 0) {
                if (times == 2) {
                    System.gc();
                    System.out.println("execute gc");
                    try {
                        Thread.sleep(10000000);

                    } catch (Exception e) {
                    }
                }
                List<Object> list = new ArrayList<>(1000000);
                for (int i = 0; i < 100000; i++) {
                    list.add(new A());
                }
            }
        });
        t.start();
        t.join();
    }

    private static class A {
        int[] array = new int[100];

        @Override
        protected void finalize() throws Throwable {
            //            System.out.println("A recall");
        }
    }
}
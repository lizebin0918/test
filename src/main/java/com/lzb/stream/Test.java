package com.lzb.stream;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * List转Map，内存是否能回收<br/>
 * Created on : 2021-05-08 15:22
 * @author chenpi 
 */
public class Test {

    private static final AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Map<Integer, String> map = null;
        {
            int size = 500;
            List<A> list = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                A a = new A();
                a.id = i;
                a.name = Objects.toString(i);
                list.add(a);
            }
            map = list.stream().collect(Collectors.toMap(A::getId, A::getName));
            list.clear();
        }

        System.gc();
        Thread.sleep(100000);

       /* Test test = new Test();
        Map<Integer, String> map = test.getMap();
        System.gc();
        Thread.sleep(100000);*/
    }

    public Map<Integer, String> getMap() {
        int size = 500;
        List<A> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            A a = new A();
            a.id = i;
            a.name = Objects.toString(i);
            list.add(a);
        }
        return list.stream().collect(Collectors.toMap(A::getId, A::getName));
    }

    private static class A {
        private byte[] bytes = new byte[1024 * 1024];
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("被回收了:" + counter.addAndGet(1));
            super.finalize();
        }
    }

}

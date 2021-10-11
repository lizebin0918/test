package com.lzb.jdk.list;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 测试List.add<br/>
 * Created on : 2021-10-11 23:04
 *
 * @author lizebin
 */
public class TestAddList {

    public static void main(String[] args) throws InterruptedException {
        int size = 200000;
        /**
         * 头插法-效率高低：ArrayList < LinkedList
         */
        // test_ArrayList_addFirst(size);
        // test_LinkedList_addFirst(size);

        /**
         * 尾插法-效率高低：ArrayList > LinkedList，就算ArrayList扩容，效率比LinkedList更高......晕......
         */
        //test_ArrayList_addLast(size);
        //test_LinkedList_addLast(size);

        /**
         * 中间插入-效率高低：ArrayList > LinkedList，找元素实在耗时
         */
        /*test_ArrayList_addMiddle(size);
        System.gc();
        Thread.sleep(1000);
        test_LinkedList_addMiddle(size);*/



    }

    public static void test_ArrayList_addFirst(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(0, i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void test_LinkedList_addFirst(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.addFirst(i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void test_ArrayList_addLast(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void test_LinkedList_addLast(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void test_ArrayList_addMiddle(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(list.size() >> 1, i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }

    public static void test_LinkedList_addMiddle(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            list.add(list.size() >> 1, i);
        }
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));
    }
}

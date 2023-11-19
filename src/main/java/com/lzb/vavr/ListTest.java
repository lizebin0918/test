package com.lzb.vavr;

import io.vavr.collection.List;

/**
 * <br/>
 * Created on : 2023-11-19 16:51
 * @author mac
 */
public class ListTest {

    public static void main(String[] args) {

        // JDK的List.of()就是一个不可变数组 vavr 是一个LIFO链表
        List<Integer> list = List.of(1, 2, 3);
        System.out.println(list);

        List<Integer> list2 = list.tail().append(0);
        System.out.println(list2);

        // = HashMap((0, List(2, 4)), (1, List(1, 3)))
        System.out.println(List.of(1, 2, 3, 4).groupBy(i -> i % 2));

        // = List((a, 0), (b, 1), (c, 2))
        System.out.println(List.of('a', 'b', 'c').zipWithIndex());

    }

}

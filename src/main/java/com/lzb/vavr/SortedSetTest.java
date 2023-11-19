package com.lzb.vavr;

import java.util.Comparator;

import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeSet;

/**
 * <br/>
 * Created on : 2023-11-19 19:59
 * @author mac
 */
public class SortedSetTest {

    public static void main(String[] args) {
        SortedSet<Integer> xs = TreeSet.of(6, 1, 3, 2, 4, 7, 8);
        System.out.println(xs);
        xs.stdout();

        Comparator<Integer> c = (a, b) -> b - a;
        TreeSet<Integer> xs2 = TreeSet.of(c, 1, 2, 3, 4, 5, 6, 7, 8);
        xs2.stdout();
    }

}

package com.lzb.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TreeMultiset;

/**
 * <br/>
 * Created on : 2023-06-09 16:49
 * @author lizebin
 */
public class TestMultiset {

    public static void main(String[] args) {
        Multiset<String> names = HashMultiset.create();
        names.add("John");
        names.add("Adam", 3);
        names.add("John");
        System.out.println(names.size());
        System.out.println(names.count("John"));

        TreeMultiset<Integer> list = TreeMultiset.create();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(list.count(1));
        System.out.println(list.size());

        HashMultiset<Integer> list1 = HashMultiset.create();
        list1.add(1);
        list1.add(1);
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);
        System.out.println(list1.count(1));
        System.out.println(list1.size());

    }

}

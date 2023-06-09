package com.lzb.guava;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;

/**
 * <br/>
 * Created on : 2023-06-09 19:50
 * @author lizebin
 */
public class TestList {

    public static void main(String[] args) {
        Iterable<String> i = Iterables.concat(List.of("1"), List.of("2"));
        ImmutableList<String> list = ImmutableList.copyOf(i);
        System.out.println(list);
    }

}

package com.lzb.guava;

import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.Iterables;
import one.util.streamex.StreamEx;

/**
 * <br/>
 * Created on : 2023-06-09 17:05
 * @author lizebin
 */
public class TestIterables {

    public static void main(String[] args) {
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("a");
        strings1.add("b");

        ArrayList<String> strings2 = new ArrayList<>();
        strings1.add("c");
        strings1.add("d");


        Iterable<String> concat = Iterables.concat(strings1, strings2);
        concat.forEach(System.out::println);

        System.out.println(StreamEx.of(concat).toList());
        System.out.println(StreamSupport.stream(concat.spliterator(), false).collect(Collectors.toList()));
    }

}

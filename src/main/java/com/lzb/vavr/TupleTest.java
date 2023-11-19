package com.lzb.vavr;


import io.vavr.Tuple;
import io.vavr.Tuple2;

/**
 * <br/>
 * Created on : 2023-11-19 20:49
 * @author mac
 */
public class TupleTest {

    public static void main(String[] args) {
        var tuple5 = Tuple.of("1", 1, "2", "4", 5);
        System.out.println(tuple5);

        System.out.println(tuple5._3);

        // (Java, 8)
        Tuple2<String, Integer> java8 = Tuple.of("Java", 8);

        // "Java"
        String s = java8._1;

        // 8
        Integer i = java8._2;

        // 元组映射
        Tuple2<String, Integer> that = java8.map(
                s1 -> s1.substring(2) + "vr",
                i1 -> i1 / 8
        );
        System.out.println(that);

        // "vavr 1"
        String that1 = java8.apply(
                (s1, i1) -> s1.substring(2) + "vr " + i1 / 8
        );
        System.out.println(that1);
    }

}

package com.lzb.function;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * <br/>
 * Created on : 2024-01-06 10:10
 * @author lizebin
 */
public class Main {

    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
        System.out.println(add.apply(2).apply(3));

        IntFunction<IntFunction<Integer>> add2 = x -> y -> x + y;
        System.out.println(add2.apply(2).apply(3));

        System.out.println(BinaryOperator.<Integer>minBy(Comparator.naturalOrder()).apply(2, 3));

        System.out.println("higherCompose");
        System.out.println(higherCompose().apply(x -> x).apply(y -> y).apply(1));
    }

    public static <T, U, V> Function<Function<T, U>, Function<Function<V, T>, Function<V, U>>> higherCompose() {
        return f -> g -> x -> f.apply(g.apply(x));
    }

}

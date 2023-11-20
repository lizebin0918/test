package com.lzb.vavr;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurryingFibonacci {
    public static void main(String[] args) {
        // 定义斐波那契数列的递归函数
        Function<Function<Integer, Integer>, Function<Integer, Integer>> fib = f -> n -> n <= 1 ? n : f.apply(n - 1) + f.apply(n - 2);

        /*Function<Integer, Integer> fibonacci = i -> {
            Function<Integer, Function<Integer, Integer>> fib1 = j -> {
                if (j <= 1) {
                    return j;
                }
                return Function.identity();
            };
            return fib1.apply(i -1) + fib1.apply(i - 2);
        };*/

        Set<Object> collect = Stream.builder().add("1").add(2).build()
                .collect(Collectors.toSet());
        System.out.println(collect.size());


        // 输出斐波那契数列的前10个数
        /*for (int i = 0; i < 10; i++) {
            System.out.println("i = " + i);
            System.out.println(fibonacci.apply(i));
            System.out.println(f(i));
        }*/


    }

    public void t() {
        Function<Function<Integer, Integer>, Function<Integer, Integer>> fib =
                (Function<Integer, Integer> f) -> (Integer n) -> n <= 1 ? n : f.apply(n - 1) + f.apply(n - 2);

        // 柯里化后的斐波那契函数
        Function<Integer, Integer> fibonacci = fib.apply(null);

        // 输出斐波那契数列的前10个数
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci.apply(i));
        }
    }

    public static int f(int i) {
        if (i<=1) {
            return i;
        }
        return f(i - 1) + f(i -2);
    }

    /**
     * 尾递归函数接口
     * @author : martrix
     */
    @FunctionalInterface
    public interface TailRecursion<T> {
        /**
         * 用于递归栈帧之间的连接,惰性求值
         * @return 下一个递归栈帧
         */
        TailRecursion<T> apply();

        /**
         * 判断当前递归是否结束
         * @return 默认为false,因为正常的递归过程中都还未结束
         */
        default boolean isFinished(){
            return false;
        }

        /**
         * 获得递归结果,只有在递归结束才能调用,这里默认给出异常,通过工具类的重写来获得值
         * @return 递归最终结果
         */
        default T getResult()  {
            throw new Error("递归还没有结束,调用获得结果异常!");
        }

        /**
         * 及早求值,执行者一系列的递归,因为栈帧只有一个,所以使用findFirst获得最终的栈帧,接着调用getResult方法获得最终递归值
         * @return 及早求值,获得最终递归结果
         */
        default T invoke() {
            return Stream.iterate(this, TailRecursion::apply)
                    .filter(TailRecursion::isFinished)
                    .findFirst()
                    .get()
                    .getResult();
        }
    }

}
package com.lzb.tdd;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 编程练习<br/>
 *
 * 需求1：
 * 写一个程序，从1遍历到100
 * 3的倍数替换成"Fizz"
 * 5的倍数替换成"Buzz"
 * 既能被3和5整除的，替换成"FizzBuzz"
 *
 * 基于需求1，扩展成需求2：
 * 包含3的数字也要替换成"Fizz"
 * 包含5的数字也要替换成"Buzz"
 *
 * Created on : 2021-12-19 16:17
 *
 * @author lizebin
 */
public class Test2 {

    public static void main(String[] args) {

        testMod3();
        testMod5();
        PrintFizzBuzz.print();

    }

    public static void testMod3() {
        // 测试mod3
        Assert.isTrue(!PrintFizzBuzz.isMod3.test(1));
        Assert.isTrue(!PrintFizzBuzz.isMod3.test(2));
        Assert.isTrue(PrintFizzBuzz.isMod3.test(3));
        Assert.isTrue(!PrintFizzBuzz.isMod3.test(4));
        Assert.isTrue(!PrintFizzBuzz.isMod3.test(5));
        Assert.isTrue(PrintFizzBuzz.isMod3.test(6));
    }

    public static void testMod5() {
        // 测试mod5
        Assert.isTrue(!PrintFizzBuzz.isMod5.test(1));
        Assert.isTrue(!PrintFizzBuzz.isMod5.test(2));
        Assert.isTrue(!PrintFizzBuzz.isMod5.test(3));
        Assert.isTrue(!PrintFizzBuzz.isMod5.test(4));
        Assert.isTrue(PrintFizzBuzz.isMod5.test(5));
        Assert.isTrue(!PrintFizzBuzz.isMod5.test(6));
    }

    public static class PrintFizzBuzz {

        public static final String FIZZ = "Fizz";

        public static final String BUZZ = "Buzz";

        public static void print() {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= 100; i++) {
                sb.append(format(i));
            }
            System.out.println(sb);
        }

        private static final Predicate<Integer> isMod3 = (n) -> n % 3 == 0;
        private static final Predicate<Integer> isEnd3 = (n) -> String.valueOf(n).endsWith("3");
        private static final Function<Integer, String> end3 = n -> {
            if (isEnd3.or(isMod3).test(n)) return FIZZ;
            return "";
        };

        private static final Predicate<Integer> isMod5 = (n) -> n % 5 == 0;
        private static final Predicate<Integer> isEnd5 = (n) -> String.valueOf(n).endsWith("5");
        private static final Function<Integer, String> end5 = n -> {
            if (isMod5.or(isEnd5).test(n)) return BUZZ;
            return "";
        };

        private static final List<Function<Integer, String>> FUNCTION_LIST = List.of(end3, end5);

        private static String format(int number) {
            StringBuilder r = new StringBuilder();
            boolean isMatch = false;
            for (Function<Integer, String> f : FUNCTION_LIST) {
                String temp = f.apply(number);
                if (!Objects.equals(temp, "")) {
                    r.append(temp);
                    isMatch = true;
                }
            }
            if (!isMatch) {
                r.append(number);
            }
            return r.toString();
        }

    }

}

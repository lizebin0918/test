package com.lzb.vavr;

import io.vavr.collection.List;

/**
 * <br/>
 * Created on : 2023-11-19 20:05
 * @author mac
 */
public class SeqTest {

    public static void main(String[] args) {
        // Java的写法:s会变化
        for (String s : List.of("Java", "Advent")) {
            // side effects and mutation
        }

        System.out.println(join1("1", "2", "3", "4"));

        System.out.println(List.of("1", "2", "3", "4").mkString(", "));
    }

    /**
     * 这是有副作用的写法
     * @param words
     * @return
     */
    static String join(String... words) {
        StringBuilder builder = new StringBuilder();
        for(String s : words) {
            if (!builder.isEmpty()) {
                builder.append(", ");
            }
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * 这是无副作用写法
     * @param words
     * @return
     */
    static String join1(String... words) {
        return List.of(words)
                .intersperse(", ")
                //.foldLeft(new StringBuilder(), StringBuilder::append)
                // 从右往左
                .foldRight(new StringBuilder(), (s1, s2) -> {
                    System.out.println("s2 = " + s2);
                    return s2.append(s1);
                })
                .toString();
    }
}

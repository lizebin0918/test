package com.lzb.goto_test;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * break out;跳出out对应层的逻辑，继续执行后续逻辑<br/>
 * Created on : 2021-08-02 21:34
 *
 * @author lizebin
 */
public class TestGoto {

    public static void main(String[] args) {
        //多层循环，跳到指定的那一层循环
        /*for (int i = 0; i < 10; i++) {
            System.out.println(i);
            out:
            for (; ; ) {
                for (; ; ) {
                    System.out.println(1);
                    break out;
                }
            }
        }*/

        //if-else
        o:
        while (true) {
            System.out.println("1");
            if (args.length == 0) {
                //continue 同理
                break o;
            }
            System.out.println("2");
        }
        System.out.println("3");

        Person p = new Person();

        Predicate<Person> ageGreater14 = (item) -> item.age > 14;
        Stream.of(p).filter(ageGreater14).collect(Collectors.toCollection(() -> new ArrayList<>(1)));
    }

    private static class Person {
        int age;
    }

}

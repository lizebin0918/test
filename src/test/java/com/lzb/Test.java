package com.lzb;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br/>
 * Created on : 2021-07-04 10:54
 *
 * @author lizebin
 */
public class Test {

    private static final Person p = new Person("0", "0");

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            int j = i;
            System.out.println("i--------->" + i);
            stepOne();
            Stream.of(j).map(Test::stepTwo).collect(Collectors.toList());
            p.setAge(String.valueOf(j));
            p.setName(String.valueOf(j));
        }

        System.out.println("main done");

    }

    public static void stepOne() {
        System.out.println("---------------------");
    }

    public static int stepTwo(int i) {
        System.out.println("stepTwo=" + i);
        return i;
    }

    private static class Person {
        private String name, age;
        public Person(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public String getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}

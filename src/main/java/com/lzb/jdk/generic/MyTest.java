package com.lzb.jdk.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;

class MyTest {

    void should_() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("lizebin18", 18));
        persons.add(new Man("lizebin17", 17, 20));

        /**
         *
         * public static <T 它是Comparable子类 extends Comparable<? super T> 这个Comparable的泛型可以是父类实现的，比如这个例子> void sort(List<T> list 这是生产者) {
         *    list.sort(null);
         * }
         */
        Collections.sort(persons);

        // assertThat(persons.get(0).getAge()).isEqualTo(17);
    }



    @Getter
    static class Person implements Comparable<Person> {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(@NotNull MyTest.Person o) {
            return this.age - o.age;
        }
    }

    static class Man extends Person {

        private final int muscle;
        public Man(String name, int age, int muscle) {
            super(name, age);
            this.muscle = muscle;
        }
    }

}
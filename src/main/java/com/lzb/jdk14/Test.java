package com.lzb.jdk14;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新特性<br/>
 * Created on : 2021-07-22 16:15
 *
 * @author chenpi
 */
public class Test {

    public static void main(String[] args) {
        var i = 1;
        System.out.println(i);

        //list--------------------->
        var intList1 = List.of(i, 2, 3);
        var intList2 = new ArrayList<String>();
        intList2.add("1");
        intList2.add("2");
        intList2.add("3");
        System.out.println(intList1);
        String[] array = intList2.toArray(String[]::new);
        System.out.println(intList2);
        System.out.println(Arrays.toString(array));
        //list<---------------------

        //Predicate
        System.out.println(intList1.stream().filter(Predicate.not(item -> item == 2)).collect(Collectors.toList()));
        System.out.println(intList1.stream().filter(Predicate.not(Objects::isNull)).collect(Collectors.toList()));

        //String
        System.out.println(intList2.stream().filter(Predicate.not(String::isBlank)).collect(Collectors.toList()));

        //Stream
        Stream.of("a", "b", "c", "", "e", "f").takeWhile(s->!s.isEmpty())
            .forEach(System.out::print);
        System.out.println("---------------");
        Stream.of("a","b","c","","e","f").dropWhile(s-> !s.isEmpty())
            .forEach(System.out::print);

        Stream<Integer> unorderedList = Stream.of(1, 8, 31, 5, 7, 18, 12, 6, 2, 1, 16, 51);
        unorderedList.dropWhile(num -> num < 10).forEach(num -> System.out.println(num + " "));

        System.out.println("------------");

        //Set<Integer> numbers = Set.of(2, 4, 6, 3, 8);
        var numbers = List.of(2, 4, 6, 3, 8);
        numbers.stream()
            .takeWhile(n -> n % 2 == 0)
            .forEach(System.out::println);
    }

}

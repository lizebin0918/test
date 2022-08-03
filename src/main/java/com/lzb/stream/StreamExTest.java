package com.lzb.stream;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import one.util.streamex.StreamEx;

import java.util.Arrays;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-08-03 23:23
 *
 * @author mac
 */
public class StreamExTest {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(new Person(1), new Person(2), new Person(3));
        List<Person> personsIs2Age = StreamEx.of(persons).filterBy(Person::getAge, 2).toList();
        System.out.println(JSON.toJSONString(personsIs2Age));
    }

}

@Data
@AllArgsConstructor
class Person {
    private int age;
}

package com.lzb.stream;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import java.util.*;

/**
 * <br/>
 * Created on : 2022-08-03 23:23
 *
 * @author mac
 */
public class StreamExTest {

    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(new Person(1, "1"), new Person(2, "2"), new Person(3, "3"));
        List<Person> personsIs2Age = StreamEx.of(persons).filterBy(Person::getAge, 2).toList();
        System.out.println(JSON.toJSONString(personsIs2Age));

        // 直接转化成属性
        List<String> names = StreamEx.of(persons).map(Person::getName).toList();
        System.out.println(JSON.toJSONString(names));

        // group by
        Map<Integer, List<Person>> age2Persons = StreamEx.of(persons).groupingBy(Person::getAge);
        System.out.println(JSON.toJSONString(age2Persons));

        // 多个属性 join
        System.out.println(StreamEx.of(1, 2, 3).joining(","));

        // 类型过滤
        List<?> userPersons = Arrays.asList(new User(), new Person(1, "1"));
        List<Person> peoples = StreamEx.of(userPersons).select(Person.class).toList();
        System.out.println(JSON.toJSONString(peoples));

        // 头尾添加元素
        List<String> names1 = StreamEx.of(persons).map(Person::getName).prepend("first").append("last").toList();
        System.out.println(JSON.toJSONString(names1));

        // 过滤非空字段
        List<String> names2 = StreamEx.of(persons).map(Person::getName).nonNull().toList();
        System.out.println(JSON.toJSONString(names2));

        // map 过滤 value，只输出 key
        Map<String, Person> nameToPerson = new HashMap<>();
        nameToPerson.put("first", new Person(18, "18"));
        nameToPerson.put("second", null);
        Set<String> nonNullPersons = StreamEx.ofKeys(nameToPerson, Objects::nonNull).toSet();
        System.out.println(JSON.toJSONString(nonNullPersons));

        // 操作键值对，翻转
        Map<String, List<Person>> nameToPersons = new HashMap<>();
        nameToPersons.put("first", persons);
        List<Person> persons2 = Arrays.asList(new Person(18, "18"));
        nameToPersons.put("second", persons2);
        Map<Person, List<String>> users2Persons = EntryStream.of(nameToPersons).flatMapValues(List::stream).invert().grouping();

    }

}

@Data
@AllArgsConstructor
class Person {
    private int age;
    private String name;
}

class User {
    private long id;
    private String name;
    private String role;
}

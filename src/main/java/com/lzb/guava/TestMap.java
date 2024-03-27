package com.lzb.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Map测试<br/>
 * Created on : 2021-06-22 23:56
 *
 * @author lizebin
 */
public class TestMap {

    public static void main(String[] args) {
        Multimap<Integer,String> multimap = ArrayListMultimap.create();
        multimap.put(1, "狄仁杰");
        multimap.put(1, "阿离");
        multimap.put(1, "小卤蛋");
        multimap.put(2, "韩信");
        multimap.put(2, "李白");
        multimap.put(2, "猴哥");

        System.out.println(multimap);

        // <String>
        ImmutableSetMultimap<String, String> setMap = ImmutableSetMultimap.<String, String>builder()
                .put("1", "1")
                .put("2", "2")
                .put("1", "3")
                .build();
        System.out.println(setMap);
        System.out.println(setMap.get("1"));

        // <Person, Skill>
        Multimap<Person, Skill> personSkillMap = ImmutableSetMultimap.<Person, Skill>builder()
                .put(new Person("张三", 18), new Skill("java", 1))
                .put(new Person("张三", 18), new Skill("python", 2))
                .put(new Person("李四", 20), new Skill("java", 3))
                .build();
    }

    @Data
    @AllArgsConstructor
    private static class Person {
        private String name;
        private int age;
    }

    @Data
    @AllArgsConstructor
    private static class Skill {
        private String name;
        private int level;
    }

}

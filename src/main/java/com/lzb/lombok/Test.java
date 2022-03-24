package com.lzb.lombok;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2022-03-13 16:47
 *
 * @author lizebin
import lombok.*;

import java.util.Optional;

/**
 * <br/>
 * Created on : 2022-03-13 09:51
 *
 * @author cidervisioncase
 */
public class Test {

    public static void main(String[] args) {

        Student student = Student.builder().name("name").age(18).classId(1).grade("年级").id(1L).build();
        String studentString = JSON.toJSONString(student);
        System.out.println(studentString);

        Gson gson = new Gson();
        Student student1 = gson.fromJson(gson.toJson(student), Student.class);
        String student1String = JSON.toJSONString(student1);
        System.out.println(student1String);

        Person p = Person.builder().name("name").age(18).id(1L).build();

        String jsonString = JSON.toJSONString(p);
        System.out.println("原字符串:" + jsonString);

        // 部分字段没有反序列化
        Person p2 = JSON.parseObject(jsonString, Person.class);
        System.out.println(JSON.toJSONString(p2));

        assert p2 == null : "1234";

        System.out.println("123");

    }

    // 用于外部取值
    @Getter
    // 用于手动设值，配合 @NonNull
    @Builder
    // 用于JSON反序列化
    @AllArgsConstructor
    public static class Person {

        @NonNull
        private Long id;

        private String name;

        private Integer age;

    }

}

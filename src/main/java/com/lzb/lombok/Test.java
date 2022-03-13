package com.lzb.lombok;

import com.alibaba.fastjson.JSON;
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
        Person p = new Person(1L);
        p.setName("name");
        p.setAge(18);

        String jsonString = JSON.toJSONString(p);
        System.out.println("原字符串:" + jsonString);

        // 部分字段没有反序列化
        Person p2 = JSON.parseObject(jsonString, Person.class);
        System.out.println(JSON.toJSONString(p2));

    }

    /**
     * 无参构造：用于JSON序列化
     */
    //@NoArgsConstructor
    /**
     * 有参构造：传入必填字段
     */
    @Data
    public static class Person {

        @NonNull
        private Long id;

        private String name;

        private Integer age;


    }

}

package com.lzb.jdk.feature.jdk17;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.alibaba.fastjson.JSON;

/**
 * <br/>
 * Created on : 2022-04-23 11:07
 *
 * @author lizebin
 */
public class Main {

    /**
     * 成员变量
     * @param id
     * @param name
     * @param age
     */
    public record User(long id,

                       @NotBlank(message = "Your project needs a name.")
                       @Size(min = 3, max = 30)
                       String name,

                       int age) {

        User(long id) {
            this(id, null, 0);
        }

    };

    public static void main(String[] args) {
        PersonDto lizebin = new PersonDto("lizebin", 18);
        System.out.println(JSON.toJSONString(lizebin));
        System.out.println(lizebin.name());
        System.out.println(lizebin.age());

        Object liagnwanyi = new PersonDto("liagnwanyi", 18);
        instanceOf(liagnwanyi);

        User lizebin1 = new User(1, "lizebin", 18);
        System.out.println(lizebin1.age);

    }

    private static void instanceOf(Object personDto) {
        if (personDto instanceof PersonDto person) {
            System.out.println(person.name());
        }
    }

}

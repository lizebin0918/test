package com.lzb.easy_random;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Animal;
import com.github.javafaker.Faker;
import org.jeasy.random.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * https://github.com/j-easy/easy-random <br/>
 * Created on : 2022-06-22 19:26
 *
 * @author lizebin
 */
class EasyRandomTest {

    @Test
    void test() {
        Faker faker = new Faker();
        faker.address();

        EasyRandomParameters parameters = new EasyRandomParameters();
        parameters.seed(100L);
        parameters.stringLengthRange(3, 3);
        //parameters.excludeField(FieldPredicates.named("lastName").and(FieldPredicates.inClass(Employee.class)));
        //parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));
        parameters.randomize(Integer.class, new AgeRandomizer());
        // 指定字段返回值
        parameters.randomize(FieldPredicates.named("cards"), () -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < new Random().nextInt(10); i++) {
                list.add(faker.animal().name());
            }
            return list;
        });
        parameters.collectionSizeRange(1, 2);
        parameters.randomize(String.class, ()->{
            return "a";
        });

        EasyRandom generator = new EasyRandom(parameters);
        Person person = generator.nextObject(Person.class);
        System.out.println(JSON.toJSONString(person));
        System.out.println(JSON.toJSONString(parameters));
    }

}

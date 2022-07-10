package com.lzb.easy_random;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Animal;
import com.github.javafaker.Faker;
import org.jeasy.random.*;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

//        EasyRandomParameters parameters = new EasyRandomParameters()
//                .seed(123L)
//                .objectPoolSize(100)
//                .randomizationDepth(3)
//                .charset(forName("UTF-8"))
//                .timeRange(nine, five)
//                .dateRange(today, tomorrow)
//                .stringLengthRange(5, 50)
//                .collectionSizeRange(1, 10)
//                .scanClasspathForConcreteTypes(true)
//                .overrideDefaultInitialization(false)
//                .ignoreRandomizationErrors(true);

        EasyRandomParameters parameters = new EasyRandomParameters();
        long seed = 100L;
        parameters.seed(seed);
        parameters.stringLengthRange(3, 3);
        //parameters.excludeField(FieldPredicates.named("lastName").and(FieldPredicates.inClass(Employee.class)));
        //parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));
        parameters.randomize(Integer.class, new AgeRandomizer());
        // 指定字段返回值
        parameters.randomize(FieldPredicates.named("cards"), () -> {
            Random random = new Random(seed);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < random.nextInt(10); i++) {
                list.add(faker.animal().name());
            }
            return list;
        });

        // 字符串随机
        parameters.randomize(Address.class, ()->{
            return new Address(faker.country().name(), faker.address().streetAddress(), faker.address().buildingNumber());
        });

        // 基于集合随机
        parameters.randomize(FieldPredicates.named("firstName"), new FirstNameRandomizer());

        EasyRandom generator = new EasyRandom(parameters);
        Person person = generator.nextObject(Person.class);
        System.out.println(JSON.toJSONString(person));
        System.out.println(JSON.toJSONString(parameters));
        Person person1 = generator.nextObject(Person.class);
    }

}

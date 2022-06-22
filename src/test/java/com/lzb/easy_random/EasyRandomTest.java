package com.lzb.easy_random;

import com.alibaba.fastjson.JSON;
import com.github.javafaker.Faker;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.Test;

/**
 * <br/>
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
        //parameters.stringLengthRange(3, 3);
        //parameters.collectionSizeRange(5, 5);
        //parameters.excludeField(FieldPredicates.named("lastName").and(FieldPredicates.inClass(Employee.class)));
        //parameters.excludeType(TypePredicates.inPackage("not.existing.pkg"));
        //parameters.randomize(YearQuarter.class, new YearQuarterRandomizer());

        EasyRandom generator = new EasyRandom(parameters);
        Person person = generator.nextObject(Person.class);
        System.out.println(JSON.toJSONString(person));
    }

}

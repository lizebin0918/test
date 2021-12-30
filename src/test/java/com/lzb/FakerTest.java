package com.lzb;

import com.github.javafaker.Faker;
import org.junit.Test;

/**
 * <br/>
 * Created on : 2021-12-30 19:36
 *
 * @author lizebin
 */
public class FakerTest {

    @Test
    public void test() {
        Faker faker = new Faker();
        // 随机生成
        for (int i = 0; i < 100; i++) {
            System.out.println(faker.name().name());
            System.out.println(faker.artist().name());
        }
    }

}

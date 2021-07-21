package com.lzb.assert_test;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 断言测试<br/>
 * Created on : 2021-07-19 23:30
 *
 * @author lizebin
 */
public class MyAssertTest {

    public static void main(String[] args) {
        MyAssertTest t = new MyAssertTest();
        t.test(null);
    }

    public void test(Object object) {
        Objects.requireNonNull(object, "object is null");
        Assert.isTrue(true, "object is null");
        System.out.println("done");
    }

}

package com.lzb.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 整个class都会分享一个result
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 每个实例都会初始化
// @TestInstance(TestInstance.Lifecycle.PER_METHOD)
class MathUtilTest {

    private int result = 5;

    @Test
    void test_Add() {
        result = result + 5;
        System.out.println("test_Add(5,5) => " + result);
        assertEquals(10, result);
    }

    @Test
    void test_Multiply() {
        result = result * 5;
        System.out.println("test_Multiply(5,5) => " + result);
        assertEquals(25, result);
    }

    @Test
    void test_Devide() {
        result = result / 5;
        System.out.println("test_Devide(5,5) => " + result);
        assertEquals(1, result);
    }
}
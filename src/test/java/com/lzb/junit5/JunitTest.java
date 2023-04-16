package com.lzb.junit5;

import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 整个class都会分享一个result
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
// 每个实例都会初始化
// @TestInstance(TestInstance.Lifecycle.PER_METHOD)
class JunitTest {

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

    /**
     * 动态参数测试
     * @param input
     * @param expected
     */
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
        assertEquals(expected, Strings.isBlank(input));
    }

    private static Stream<Arguments> provideStringsForIsBlank() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)
        );
    }
}
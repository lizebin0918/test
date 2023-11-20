package com.lzb.vavr;

import java.util.function.Predicate;

import io.vavr.control.Option;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * <br/>
 * Created on : 2023-11-20 22:57
 * @author mac
 */
public class MatcherTest {

    public static void main(String[] args) {
        // 模式匹配
        int i = 100;
        String s = Match(i).of(
                Case($(1), "one"),
                Case($(2), "two"),
                Case($(), "?")
        );
        System.out.println(s);

        Option<String> zero = Match(i).option(Case($(0), "zero"));
        System.out.println(zero.isDefined());

        String s1 = Match(i).of(
                Case($(is(1)), "one"),
                Case($(is(2)), "two"),
                Case($(), "?")
        );
        System.out.println(s1);
    }

    private static Predicate<? super Integer> is(int i) {
        return Predicate.isEqual(i);
    }
}

package com.lzb.vavr;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import io.vavr.control.Option;
import lombok.Data;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.isIn;

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

        // 等值匹配
        String s1 = Match(i).of(
                Case($(Predicate.isEqual(1)), "one"),
                Case($(Predicate.isEqual(2)), "two"),
                Case($(), "?")
        );
        System.out.println(s1);

        // 断言匹配
        UnaryOperator<String> hasValue = (input) -> Match(input).of(
                Case($(isIn("h", "help")), "has value"),
                Case($(), "not value")
        );
        System.out.println("null = " + hasValue.apply(null));
        System.out.println("null = " + hasValue.apply("help"));

        Person p = new Person();
        p.setAge(30);
        String person = Match(p).of(
                Case($(p1 -> p1.getAge() > 18), "adult"),
                Case($(), "child"));
        System.out.println(person);

        Optional<String> number = Optional.ofNullable(get1()).or(() -> Optional.ofNullable(get()));
        System.out.println(number.isPresent());
        System.out.println(number.get());

    }

    private static String get1() {
        System.out.println("get1");
        return "1";
    }

    private static String get() {
        System.out.println("get");
        return null;
    }

    /*public Result allowChangeSku1(Long detailId, PackageVal detailPackage) {
        OrderVal orderVal = getOrderValOrThrow();
        return Match(orderVal).of(
                Case($(OrderVal::isCancel), Result.notSuccess("订单已取消，不能换码")),
                Case($(OrderVal::isShipped), Result.notSuccess("订单已发货，不能换码")),
                Case($(isCancel(detailId)), Result.notSuccess("订单明细已取消")),
                Case($(Predicate.not(isZhaoQingWarehouse(detailId))), Result.notSuccess("订单sku不在肇庆仓库，不能换码")),
                Case($(orderDetailIsShipped(detailPackage)), Result.notSuccess("订单sku已发货，不能换码")),
                Case($(), Result.DEFAULT_SUCCESS)
        );
    }*/

    public static Optional<String> checkForPerson(Person person) {
        return null;
    }

    @Data
    private static class Person {
        private int age;
        private String name;
    }
}

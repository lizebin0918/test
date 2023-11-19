package com.lzb.vavr;

import java.util.Optional;
import java.util.function.Function;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;

/**
 * <br/>
 * Created on : 2023-11-19 21:26
 * @author mac
 */
public class FunctionTest {

    public static void main(String[] args) {
        // 提供了8个参数的Function8
        var add_3 = Function3.of(FunctionTest::add);
        System.out.println(add_3.apply(1, 2, 3));

        // Function 的增强
        // java8 的写法，原来java8自带compose
        Function1<Integer, Integer> plusOne = a -> a + 1;
        Function1<Integer, Integer> multiplyByTwo = a -> a * 2;

        Function1<Integer, Integer> add1AndMultiplyBy2 = plusOne.andThen(multiplyByTwo);

        System.out.println((add1AndMultiplyBy2.apply(2)));

        // vavr 的写法 先算里面再算外面
        System.out.println(plusOne.compose(multiplyByTwo).apply(2));

        lift();

        curried();

        curried1();

        curriedFromChatGPT();
    }

    private static void lift() {
        /**
         * lift
         */
        /*
         * A lifted function returns None instead of throwing an exception, if the function is invoked with disallowed input values.
         * 如果使用不允许的输入值调用函数，则提升的函数将返回 None 而不是抛出异常。
         * A lifted function returns Some, if the function is invoked with allowed input values.
         * 如果使用允许的输入值调用函数，则提升的函数将返回 Some 。
         */
        Function2<Integer, Integer, Integer> divide = (a1, b1) -> a1 / b1;
        var liftDivide = Function2.lift(divide);
        System.out.println(liftDivide.apply(1, 0).isDefined());

        // 设置部分参数固定值，从多参数降阶函数
        Function1<Integer, Integer> divided100 = divide.apply(100);
        System.out.println(divided100.apply(10));

        /**
         * 总的来说，"lift" 是函数式编程中一种将普通函数提升为操作符或者函数的技术，使其能够在更高级的抽象类型上进行操作的方法。
         */
        Function<Integer, Integer> addOne = x -> x + 1;

        Function<Optional<Integer>, Optional<Integer>> liftedAddOne = lift(addOne);

        Optional<Integer> result = liftedAddOne.apply(Optional.of(5));
        System.out.println(result); // 输出 Optional[6]

    }

    public static <T, R> Function<Optional<T>, Optional<R>> lift(Function<T, R> function) {
        return optional -> optional.map(function);
    }

    public static void curried() {
        Function2<Integer, Integer, Integer> sum = (a, b) -> a + b;
        Function1<Integer, Integer> add2 = sum.curried().apply(2);

        System.out.println((add2.apply(4)));
    }

    public static void curried1() {
        Function3<Integer, Integer, Integer, Integer> sum = (a, b, c) -> a + b + c;

        // 非curried
        Function2<Integer, Integer, Integer> add1 = sum.apply(2);
        System.out.println(add1.apply(4).apply(3));

        // curried
        final Function1<Integer, Function1<Integer, Integer>> add2 = sum.curried().apply(2);
        System.out.println((add2.apply(4).apply(3)));
    }

    public static void curriedFromChatGPT() {
        // 定义一个接受两个参数的函数
        Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

        // 柯里化后的函数
        Function<Integer, Integer> add2 = add.apply(2);

        // 调用柯里化后的函数
        int result = add2.apply(3);
        System.out.println(result); // 输出 5
    }

    public static int add(int i, int j, int k) {
        return i + j + k;
    }

}

package com.lzb.vavr;

import io.vavr.control.Try;
import org.checkerframework.checker.units.qual.A;

import static com.google.common.base.Predicates.instanceOf;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * Try 是一个一元容器类型，它表示可能导致异常或返回成功计算值的计算。它与 Either 类似，但语义上不同。 Try 的实例是 Success 或 Failure 的实例。<br/>
 * Created on : 2023-01-13 19:44
 * @author lizebin
 */
public class TryTest {

    public static void main(String[] args) {

        //exception
        Try.of(() -> 1 / 0)
                .andThen(r -> System.out.println("and then 1" + r))
                .andThen(r -> System.out.println("and then 2" + r))
                .onFailure(error -> System.out.println("failure" + error.getMessage()))
                .andFinally(() -> {
                    System.out.println("finally");
                });

        // 正常调用
        Integer result = Try.ofSupplier(() -> 1)
                .andThen(r -> System.out.println("and then 1 = " + (r + 1)))
                .andThen(r -> System.out.println("and then 2 = " + r))
                .get();
        System.out.println(result);

        // 还能这样表达
        /*A result = Try.of(this::bunchOfWork)
                .recover(x -> Match(x).of(
                        Case($(instanceOf(Exception_1.class)), t -> somethingWithException(t)),
                        Case($(instanceOf(Exception_2.class)), t -> somethingWithException(t)),
                        Case($(instanceOf(Exception_n.class)), t -> somethingWithException(t))
                ))
                .getOrElse(other);*/

    }

    private static void bunchOfWork() {

    }

}

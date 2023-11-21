package com.lzb.vavr;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import org.apache.commons.lang3.StringUtils;

/**
 * left 作为 failure，right 作为 成功值<br/>
 * 在我们的例子中，如果我们有一个Right，我们真的不需要知道任何关于错误的信息，因为它不存在。另一方面，如果我们有一个Left，则需要关注处理错误，但对于正常值则不必考虑那么多。
 * 我们最终得到一种更好、更简洁的方式来表达我们的成功或错误的处理类型。功能用例尽管非常简单但富有表现力，限定Either<L, R>的返回。 任何一种输入都能得到一种输出。 并不是观察异常（try/catch 块）并根据它做出改变控制流的决策。
 * 这使得错误/异常处理能在框架层面上设计而不是在实现级别上得到注意。
 * Created on : 2023-11-20 14:15
 * @author lizebin
 */
public class EitherTest {

    public static void main(String[] args) {
        Either<Long, Void> left = Either.left(1L);
        System.out.println(left);
        System.out.println(left.isRight());
        System.out.println(left.isLeft());
        Either<Integer, Void> either = left.mapLeft(i -> i * 2).toEither(() -> 1);
        System.out.println(either.getLeft());

        System.out.println(left.swap());


        System.out.println("---------打印异常---------");
        List<String> lines = List.of("1", "", "2", "", "3");

        seq(lines);

        traverse(lines);

        traverseRight(lines);
    }

    private static void traverseRight(List<String> lines) {
        System.out.println("traverseRight");
        Either<Throwable, Seq<String>> traverseRight = Either.traverseRight(lines, EitherTest::readLine);
        if (traverseRight.isRight()) {
            System.out.println("right");
            traverseRight.get().forEach(System.out::println);
        }
        if (traverseRight.isLeft()) {
            System.out.println("left");
            System.out.println(traverseRight.getLeft());
        }
    }

    private static void traverse(List<String> lines) {
        System.out.println("----------traverse---------");

        System.out.println(lines);
        Either<Seq<Throwable>, Seq<String>> traverse = Either.traverse(lines, EitherTest::readLine);
        // 收集失败情况
        System.out.println(traverse);
        if (traverse.isLeft()) {
            traverse.getLeft().forEach(System.out::println);
        }
        // 转换成一个新的 Either
        /*Either<Integer, Seq<String>> seqs = traverse.mapLeft(list -> {
            int size = list.size();
            System.out.println(size);
            return size;
        });*/
        System.out.println(traverse.isRight());
        traverse.forEach(System.out::println);
        //traverse.map(list -> list.collect(Collectors.joining(","))).forEach(System.out::println);
        System.out.println("----------traverse---------");
    }

    private static void seq(List<String> lines) {
        System.out.println("----------seq---------");
        List<Either<Throwable, String>> eithers = lines.map(EitherTest::readLine);
        eithers.forEach(either -> {
            if (either.isLeft()) {
                System.out.println(either.getLeft());
            }
            if (either.isRight()) {
                System.out.println(either.get());
            }
        });
        System.out.println("----------seq---------");
    }

    public static Either<Throwable, String> readLine(String line) {
        if (StringUtils.isBlank(line)) {
            return Either.left(new IllegalArgumentException("line is empty"));
        }
        return Either.right(line);
    }

}

package com.lzb.vavr;

import io.vavr.control.Either;

/**
 * left 作为 failure，right 作为 成功值<br/>
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

    }

}

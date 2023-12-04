package com.lzb.fp.monad;

import java.util.function.Function;

import lombok.Getter;

public final class WrapperMonad<A> {

    public final A value;

    private WrapperMonad(A value) {
        this.value = value;
    }

    static <A> WrapperMonad<A> of(A value) {
        return new WrapperMonad<>(value);
    }

    <B> WrapperMonad<B> flatMap(Function<A, WrapperMonad<B>> f) {
        return f.apply(value);
    }

    // For sake of asserting in Example
    boolean valueEquals(A x) {
        return value.equals(x);
    }
}
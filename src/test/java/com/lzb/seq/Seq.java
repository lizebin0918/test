package com.lzb.seq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


/**
 * https://mp.weixin.qq.com/s/v-HMKBWxtz1iakxFL09PDw
 * A负责产出数据，B负责使用数据。A不关心B怎么处理数据，可能要先过滤一些，进行聚合后再做计算，
 * 也可能是写到某个本地或者远程的存储；B自然也不关心A的数据是怎么来的。这里边唯一的问题在于，
 * 数据条数实在是太多了，内存一次性放不下。在这种情况下，传统的做法是让A提供一个带回调函数consumer的接口，
 * B在调用A的时候传入一个具体的consumer
 *
 * // 这玩意就是一个Seq啊
 * public void produce(Consumer<String> callback) {
 *     // do something that produce strings
 *     // then use the callback consumer to eat them
 * }
 *
 * 换句话讲，生成器的callback机制，保证了哪怕Seq可以作为变量四处传递，但涉及到的任何副作用操作，
 * 都是包在同一个代码块里惰性执行的。它不需要像Monad那样，还得定义诸如IOMonad，StateＭonad等等花样众多的Monad
 *
 * Monad这种设计模式之所以被推崇备至，是因为它有几个重要特性，惰性求值、链式调用以及副作用隔离——在纯函数的世界里，后者甚至称得上是性命攸关的大事。
 * 但是对包括Java在内的大部分正常语言来说，实现惰性求值更直接的方式是面向接口而不是面向对象(实例)编程，
 * 接口由于没有成员变量，天生就是惰性的。链式操作则是流的天生特性，无须赘述。至于副作用隔离，这同样不是Monad的专利。生成器用闭包+callback的方式也能做到，前文都有介绍
 * @param <T>
 */
@FunctionalInterface
public interface Seq<T> {
    void consume(Consumer<T> consumer);

    static <T> Seq<T> unit(T t) {
        return c -> c.accept(t);
    }

    default <E> Seq<E> map(Function<T, E> function) {
        return c -> consume(t -> c.accept(function.apply(t)));
    }

    default <E> Seq<E> flatMap(Function<T, Seq<E>> function) {
        return c -> consume(t -> function.apply(t).consume(c));
    }

    default Seq<T> filter(Predicate<T> predicate) {
        return c -> consume(t -> {
            if (predicate.test(t)) {
                c.accept(t);
            }
        });
    }
    static <T> T stop() {
        throw StopException.INSTANCE;
    }

    default void consumeTillStop(Consumer<T> consumer) {
        try {
            consume(consumer);
        } catch (StopException ignore) {}
    }

    default Seq<T> take(int n) {
        return c -> {
            int[] i = {n};
            consumeTillStop(t -> {
                if (i[0]-- > 0) {
                    c.accept(t);
                } else {
                    stop();
                }
            });
        };
    }

    default Seq<T> drop(int n) {
        return c -> {
            int[] a = {n - 1};
            consume(t -> {
                if (a[0] < 0) {
                    c.accept(t);
                } else {
                    a[0]--;
                }
            });
        };
    }
    default Seq<T> peek(Consumer<T> consumer) {
        return c -> consume(consumer.andThen(c));
    }

    default <E, R> Seq<R> zip(Iterable<E> iterable, BiFunction<T, E, R> function) {
        return c -> {
            Iterator<E> iterator = iterable.iterator();
            consumeTillStop(t -> {
                if (iterator.hasNext()) {
                    c.accept(function.apply(t, iterator.next()));
                } else {
                    stop();
                }
            });
        };
    }

    default String join(String seprator) {
        StringJoiner joiner = new StringJoiner(seprator);
        consume(t -> joiner.add(t.toString()));
        return joiner.toString();
    }

    default List<T> toList() {
        List<T> list = new ArrayList<>();
        consume(list::add);
        return list;
    }


    public static <T> Stream<T> stream(Seq<T> seq) {
        Iterator<T> iterator = new Iterator<T>() {
            @Override
            public boolean hasNext() {
                throw new NoSuchElementException();
            }

            @Override
            public T next() {
                throw new NoSuchElementException();
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                seq.consume(action::accept);
            }
        };
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED),
                false);
    }


    /*default Seq<T> parallel() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return c -> map(t -> pool.submit(() -> c.accept(t))).cache().consume(ForkJoinTask::join);
    }*/
}
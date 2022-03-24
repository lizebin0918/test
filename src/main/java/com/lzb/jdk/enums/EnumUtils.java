package com.lzb.jdk.enums;

import java.util.Comparator;
import java.util.Optional;

public final class EnumUtils {

    /**
     * 根据枚举值获取对应的枚举对象
     * @param enums
     * @param value
     * @param <E>
     * @param <V>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> Optional<E> getByValue(E[] enums, V value) {
        for (E e : enums) {
            if (((ValueEnum<V>) e).getValue().equals(value)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据枚举值获取对应的枚举对象
     * @param enums 枚举集合
     * @param value 输入值
     * @param comparator 比较器
     * @param <E> 枚举类型
     * @param <V> 枚举值
     * @return 对应枚举
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<? extends ValueEnum<V>>, V> Optional<E> getByValue(E[] enums, V value, Comparator<V> comparator) {
        for (E e : enums) {
            if (comparator.compare(((ValueEnum<V>) e).getValue(), value) == 0) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * 根据枚举值获取对应的枚举对象
     *
     * @param enumClass 枚举class
     * @return 枚举对象
     */
    public static <E extends Enum<? extends ValueEnum<V>>, V> Optional<E> getByValue(Class<E> enumClass, V value) {
        return getByValue(enumClass.getEnumConstants(), value);
    }

    /**
     * 根据枚举值获取对应的枚举对象
     * @param enumClass
     * @param value
     * @param comparator
     * @param <E>
     * @param <V>
     * @return
     */
    public static <E extends Enum<? extends ValueEnum<V>>, V> Optional<E> getByValue(Class<E> enumClass, V value, Comparator<V> comparator) {
        return getByValue(enumClass.getEnumConstants(), value, comparator);
    }
}
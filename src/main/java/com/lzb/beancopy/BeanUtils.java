package com.lzb.beancopy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class BeanUtils {
    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 浅拷贝
     * @param source
     * @param target
     */
    public static void copy(final Object source, final Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = genKey(source.getClass(), target.getClass(), "copy");
        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                o -> BeanCopier.create(source.getClass(), target.getClass(), false));
        beanCopier.copy(source, target, null);
    }

    /**
     * 满足任一条件，则忽略【原对象】字段
     * 1.如果字段为空。判断非空：org.springframework.util.ObjectUtils.isEmpty()
     * 2.字段类型不一致
     * @param source
     * @param target
     */
    public static void copyNonEmpty(final Object source, final Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = genKey(source.getClass(), target.getClass(), "copyNonNull");
        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                o -> BeanCopier.create(source.getClass(), target.getClass(), true));
        beanCopier.copy(source, target, new Converter() {
            /**
             * @param value 源对象值
             * @param targetClass Class:目标对象属性类
             * @param setMethod String:目标对象setter方法名
             * @return
             */
            @Override
            public Object convert(Object o, Class targetClass, Object setMethod) {
                if (ObjectUtils.isEmpty(o) || !targetClass.isAssignableFrom(o.getClass())) {
                    String setMethodString = (String) setMethod;
                    String getMethodString = "g" + setMethodString.substring(1);
                    try {
                        return ReflectUtils.findDeclaredMethod(
                                target.getClass(),
                                getMethodString,
                                null).invoke(target, null);
                    } catch (Exception e) {
                        // ignore exception
                    }
                }
                return o;
            }
        });
    }

    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz, String suffix) {
        return srcClazz.getName() + "_" + tgtClazz.getName() + "_" + suffix;
    }

    /**
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copy(S source, Supplier<T> target) {
        T t = target.get();
        copy(source, t);
        return t;
    }

    /**
     * 复制列表
     * @param sources
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copyList(List<S> sources, Supplier<T> target) {
        Supplier<List<T>> listSupplier = null;
        if (sources instanceof RandomAccess) {
            listSupplier = () -> new ArrayList<>(sources.size());
        } else {
            listSupplier = () -> new LinkedList<>();
        }
        return sources.stream().map(obj -> {
            T t = target.get();
            copy(obj, t);
            return t;
        }).collect(Collectors.toCollection(listSupplier));
    }

}
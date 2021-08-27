package com.lzb.beancopy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;
import org.springframework.cglib.core.ReflectUtils;

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
    public static <T> void copy(final T source, final T target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = genKey(source.getClass(), target.getClass(), false);
        BeanCopier beanCopier = BEAN_COPIER_CACHE.computeIfAbsent(key,
                o -> BeanCopier.create(source.getClass(), target.getClass(), false));
        beanCopier.copy(source, target, null);
    }

    /**
     * 浅拷贝，过滤掉【原对象】的null字段，只复制非空字段
     * @param source
     * @param target
     */
    public static <T> void copyNonNull(final T source, final T target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = genKey(source.getClass(), target.getClass(), true);
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
                if (Objects.isNull(o)) {
                    String setMethodString = (String) setMethod;
                    String getMethod = "g" + setMethodString.substring(1, setMethodString.length());
                    try {
                        return ReflectUtils.findDeclaredMethod(target.getClass(), getMethod, null).invoke(target, null);
                    } catch (Exception e) {
                        // ignore exception
                    }
                }
                return o;
            }
        });
    }

    private static String genKey(Class<?> srcClazz, Class<?> tgtClazz, boolean useConverter) {
        return srcClazz.getName() + tgtClazz.getName() + useConverter;
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
package com.lzb.beancopy;

import com.alibaba.fastjson.JSON;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.time.LocalDateTime;

/**
 * <br/>
 * Created on : 2021-08-26 15:17
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        A a = new A();
        BeanCopier.create(A.class, A.class, true).copy(a, a, new Converter() {

            /**
             * @param value 源对象属性
             * @param targetClass 目标对象属性类
             * @param setMethod 目标对象setter方法名
             * @return
             */
            @Override
            public Object convert(Object value, Class targetClass, Object setMethod) {
                String setMethodName = (String) setMethod;
                System.out.println("------------------");
                System.out.println(value);
                System.out.println(targetClass);
                System.out.println(setMethod + ":" + setMethod);
                System.out.println("------------------");
                return null;
            }
        });
    }

    public static class A {

    }
}

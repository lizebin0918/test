package com.lzb.beancopy;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <br/>
 * Created on : 2021-08-26 15:17
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        User a = new User();
        a.setAge(18);
        a.setBirthday(LocalDate.now());
        User b = new User();
        b.setAge(1);
        b.setName("lizebin");
        BeanUtils.copyNonNull(a, b);
        System.out.println(JSON.toJSONString(b));

        a = new User();
        a.setBirthday(LocalDate.now());
        b = new User();
        b.setAge(1);
        b.setName("lizebin12344");
        BeanUtils.copyNonNull(a, b);
        System.out.println(JSON.toJSONString(b));

        BeanUtils.copy(a, b);
        System.out.println(JSON.toJSONString(b));

        List<Integer> a1 = Arrays.asList(1, 2);
        List<Integer> b1 = new ArrayList<>();
        BeanUtils.copyList(a1, () -> new Integer(3));
        System.out.println(b1);

        Integer i = new Integer(100);
        org.springframework.beans.BeanUtils.copyProperties(new Integer(1), i);
        System.out.println(i);

        // 给定一个值，对数组元素扣减，扣完为零
       /* int count = 3;
        List<Integer> list = Arrays.asList(1, 0, 0, 1, 1, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            int item = list.get(i);
            if (item <= count) {
                count = count - item;
                list.set(i, count);
            } else {
                list.set(i, item - count);
                count = 0;
            }
            if (count == 0) {
                break;
            }
        }
        System.out.println(list);
        System.out.println(count);*/

    }

    @Data
    public static class User {
        private String id;
        private Integer age;
        private String name;
        private LocalDate birthday;
    }

    public static class MyConvertor implements Converter {
        BeanCopier beanCopier;
        public MyConvertor(BeanCopier beanCopier) {
            this.beanCopier = beanCopier;
        }

        /**
         * @param value 源对象值
         * @param targetClass Class:目标对象属性类
         * @param setMethod String:目标对象setter方法名
         * @return
         */
        @Override
        public Object convert(Object value, Class paramClass, Object setMethod) {
            System.out.println("-------------");
            System.out.println(value);
            System.out.println(paramClass);
            System.out.println(setMethod);
            System.out.println("-------------");
            System.out.println(beanCopier.getClass());
            if (Objects.isNull(value)) {
            }
            return value;
        }
    }
}

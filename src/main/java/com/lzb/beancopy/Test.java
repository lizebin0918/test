package com.lzb.beancopy;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.time.LocalDate;
import java.util.*;

/**
 * <br/>
 * Created on : 2021-08-26 15:17
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        /*User a = new User();
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
        System.out.println(JSON.toJSONString(b))*/;

        User a = new User();
        a.setAge(18);
        a.setBirthday(LocalDate.now());
        a.setIntegers(Arrays.asList(1, 2, 3, 4));
        a.setId("1");
        a.setName("name");
        a.setArray(new Integer[]{1, 2});
        UserVo b = new UserVo();
        b.setIsDel(false);

        // BeanUtils.copy(a, b);
        // System.out.println(JSON.toJSONString(a));
        // System.out.println(JSON.toJSONString(b));

        BeanUtils.copyNonEmpty(a, b);
        System.out.println(JSON.toJSONString(b));

        System.out.println(new ArrayList<>() instanceof RandomAccess);

        System.out.println(ArrayList.class.isNestmateOf(List.class));
        System.out.println(List.class.isAssignableFrom(ArrayList.class));
        System.out.println(ArrayList.class.isAssignableFrom(ArrayList.class));

        /*var a1 = Arrays.asList(a, b);
        var newList = BeanUtils.copyList(a1, () -> new UserVo());
        System.out.println(JSON.toJSONString(newList));*/

        /*Integer x = 3;
        Integer y = 4;
        BeanUtils.copy(x, y);
        System.out.println(y);

        Integer i = new Integer(100);
        org.springframework.beans.BeanUtils.copyProperties(new Integer(1), i);
        System.out.println(i);*/

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
    public static class UserVo {
        private Integer id;
        private String name;
        private LocalDate birthday;
        private ArrayList<Integer> integers;
        private Boolean isDel;
        private Integer[] array;
    }

    @Data
    public static class User {
        private String id;
        private Integer age;
        private String name;
        private LocalDate birthday;
        private List<Integer> integers;
        private Boolean isDel;
        private Integer[] array;
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

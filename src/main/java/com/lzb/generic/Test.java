package com.lzb.generic;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型的设计：为了保证类型的读写安全<br/>
 * Created on : 2022-03-12 20:19
 *
 * @author cidervisioncase
 */
public class Test {

    public static void main(String[] args) {

        Test client = new Test();

        Apple apple = new Apple();
        apple.name = "apple";

        // Food
        List<Food> foods = new ArrayList<>();
        foods.add(apple);
        //client.extendsFruit(foods); 编译错误，类型非法，实物不是水果的子类
        client.superFruit(foods);

        // Fruit
        List<Fruit> fruits = new ArrayList<>();
        fruits.add(apple);
        client.superFruit(fruits);
        client.extendsFruit(fruits);

        // Apple
        List<Apple> apples = new ArrayList<>();
        apples.add(apple);
        // client.superFruit(apples);编译错误，类型非法，苹果不是水果的超类
        client.extendsFruit(apples);

    }

    @Data
    private static class Food {
        protected String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class Fruit extends Food {

    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    private static class Apple extends Fruit {

    }

    public void extendsFruit(List<? extends Fruit> extendsFruits) {

        System.out.println("水果子类列表:" + JSON.toJSONString(extendsFruits));

        //extendsFruits.add(XXXXX); 编译异常，这样就可以添加：苹果、香蕉、梨，违反了泛型的初衷

        // 编译通过：因为集合元素都是水果的子类
        Fruit apple = extendsFruits.get(0);
    }

    public void superFruit(List<? super Fruit> superFruits) {

        System.out.println("水果超类列表:" + JSON.toJSONString(superFruits));

        // 因为容器的类型是Fruit的父类，那就是说容器可以放：水果及水果的子类，这样也是类型安全的
        //superFruits.add(new Food());编译报错
        superFruits.add(new Fruit());
        superFruits.add(new Apple());

        // get 就会报错，因为超类也有很多，get的类型不安全
        //Fruit food = superFruits.get(0);

    }

}

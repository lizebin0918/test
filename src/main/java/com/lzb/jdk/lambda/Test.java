package com.lzb.jdk.lambda;

import com.alibaba.fastjson.JSON;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 测试lambda表达式<br/>
 * Created on : 2021-08-21 16:46
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        List<String> sampleList = Arrays.asList("Java", "Kotlin", null);
        String resultString = sampleList.stream()
                .map((@Nonnull var x) -> x.toUpperCase())
                .collect(Collectors.joining(", "));

        System.out.println(sampleList);

        a(new Object());
        a(null);

        Optional.ofNullable(1).orElseThrow(() -> new RuntimeException());
    }

    public static void a(@Nonnull Object o) {
        System.out.println(JSON.toJSONString(o));
    }

}

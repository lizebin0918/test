package com.lzb.util;

import java.util.LinkedHashSet;
import java.util.Set;

import lombok.experimental.UtilityClass;

/**
 * <br/>
 * Created on : 2023-06-17 23:09
 * @author mac
 */
@UtilityClass
public class MyStringUtils {

    /**
     * 提取占位符变量
     * 字符串：a ${b} ${c} d ${e}
     * 结果：["b", "c", "e"]
     *
     * @param sourceString
     * @param start
     * @param end
     * @return
     */
    public static Set<String> extractVariables(String sourceString, String start, String end) {
        LinkedHashSet<String> result = new LinkedHashSet<>();
        int startIdx = sourceString.indexOf(start);
        int stopIdx = sourceString.indexOf(end);
        while (startIdx != -1 && stopIdx != -1 && startIdx < stopIdx) {
            String variable = sourceString.substring(startIdx + 2, stopIdx);
            result.add(variable);
            startIdx = sourceString.indexOf(start, stopIdx + 1);
            stopIdx = sourceString.indexOf(end, stopIdx + 1);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(extractVariables("a ${b} ${c} d ${e}", "${", "}"));
    }

}

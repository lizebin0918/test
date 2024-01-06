package com.lzb.jdk.switch_;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSONObject;

/**
 * <br/>
 * Created on : 2023-12-15 13:32
 * @author lizebin
 */
public class TestSwitch {

    public static void main(String[] args) {
        System.out.println(asStringValue("1"));
    }

    static String asStringValue(Object anyValue) {

        return switch (anyValue) {
            case String str      -> "this is str:" + str;
            case BigDecimal bd   -> bd.toEngineeringString();
            case Integer i       -> Integer.toString(i);
            case LocalDate ld    -> ld.format(DateTimeFormatter.ISO_LOCAL_DATE);
            default              -> "n/a";
        };
    }

}

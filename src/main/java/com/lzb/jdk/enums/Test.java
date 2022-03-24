package com.lzb.jdk.enums;

/**
 * <br/>
 * Created on : 2022-03-13 18:23
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {
        boolean valueExist = EnumUtils.getByValue(TypeEnum.values(), 1).isPresent();
        System.out.println(valueExist);
        System.out.println(EnumUtils.getByValue(TypeEnum.values(), 4).isPresent());
        System.out.println(EnumUtils.getByValue(TypeEnum.class, 4).isPresent());

        System.out.println(EnumUtils.getByValue(StatusEnum.class, "all", String.CASE_INSENSITIVE_ORDER).isPresent());
        System.out.println(EnumUtils.getByValue(StatusEnum.class, "all1", String.CASE_INSENSITIVE_ORDER).isPresent());

    }

}

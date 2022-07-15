package com.lzb.jdk.feature.jdk17;

/**
 * 这是一个dto:这是一个不可变对象
 *
 * @param name
 * @param age
 */
public record PersonDto(String name, int age) {

    public PersonDto {
        isValidate(name, age);
    }

    /**
     * 校验数据合法性
     * @param name
     * @param age
     * @return
     */
    private boolean isValidate(String name, int age) {
        return false;
    }

}

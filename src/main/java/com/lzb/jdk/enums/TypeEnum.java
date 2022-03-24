package com.lzb.jdk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TypeEnum implements ValueEnum<Integer> {
    /**
     * 类型1
     */
    T1(1, "类型1"),

    T2(2, "类型2"),

    T3(3, "类型3");

    private final int type;
    private final String name;

    @Override
    public Integer getValue() {
        return this.type;
    }
}
package com.lzb.jdk.enums;

import lombok.AllArgsConstructor;

/**
 * <br/>
 * Created on : 2022-03-13 19:56
 *
 * @author lizebin
 */
@AllArgsConstructor
public enum StatusEnum implements ValueEnum<String> {

    /**
     * 全部
     */
    ALL("ALL", "全部", true),
    /**
     * 已下单
     */
    ORDERED("ORDERED", "已下单", true);

    private final String value;
    private final String text;
    private final Boolean showFlag;


    @Override
    public String getValue() {
        return value;
    }
}

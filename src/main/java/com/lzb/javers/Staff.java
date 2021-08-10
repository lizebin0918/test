package com.lzb.javers;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@ToString
@Builder
@Data
public class Staff {
    private String name;
    private int age;
    private Double height;
    private BigDecimal salary;
    private Staff manager;
    private List<String> hobbies;
    private Map<String, String> phones;
}
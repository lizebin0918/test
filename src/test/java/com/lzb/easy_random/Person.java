package com.lzb.easy_random;

import lombok.Data;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-06-22 20:33
 *
 * @author lizebin
 */
@Data
public class Person {

    private String fullName;
    private Integer age;
    private Gender gender;
    private List<String> cards;
    private Address address;
    private String firstName;

}

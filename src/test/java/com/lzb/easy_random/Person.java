package com.lzb.easy_random;

import lombok.Data;
import org.jeasy.random.annotation.Randomizer;

import java.util.List;

/**
 * <br/>
 * Created on : 2022-06-22 20:33
 *
 * @author lizebin
 */
@Data
public class Person {

    private String name;
    private Integer age;
    private Gender gender;
    private List<String> cards;


}

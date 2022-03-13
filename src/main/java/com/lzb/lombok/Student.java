package com.lzb.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <br/>
 * Created on : 2022-03-13 16:48
 *
 * @author lizebin
 */
@Getter
@SuperBuilder
public class Student extends Person {

    /**
     * 年级
     */
    private String grade;

    /**
     * 班级id
     */
    private Integer classId;




}

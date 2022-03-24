package com.lzb.lombok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * <br/>
 * Created on : 2022-03-13 16:47
 *
 * @author lizebin
 */
@Getter
@SuperBuilder
public class Person {

    @NonNull
    protected Long id;
    protected String name;
    protected Integer age;

}

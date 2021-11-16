package com.lzb.javers;

import lombok.Data;
import org.javers.core.metamodel.annotation.Id;

import java.util.List;

/**
 * <br/>
 * Created on : 2021-11-15 19:01
 *
 * @author lizebin
 */
@Data
public class Person {

    @Id
    private Integer id;
    private String name;
    private Integer age;
    private List<Address> addresseList;

}

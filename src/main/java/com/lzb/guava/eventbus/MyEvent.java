package com.lzb.guava.eventbus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-14 21:21
 * @author mac
 */
@Getter
@AllArgsConstructor
public class MyEvent {

    private final int id;
    private final String name;

}

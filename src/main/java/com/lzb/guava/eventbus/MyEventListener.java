package com.lzb.guava.eventbus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;

/**
 * <br/>
 * Created on : 2023-09-14 21:20
 * @author mac
 */
public class MyEventListener {

    @Subscribe
    public void subscribe(MyEvent event) {
        System.out.println(JSON.toJSONString(event));
    }

}

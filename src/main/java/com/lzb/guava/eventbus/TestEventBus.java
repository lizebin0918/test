package com.lzb.guava.eventbus;

import com.google.common.eventbus.EventBus;

/**
 * <br/>
 * Created on : 2023-09-14 21:16
 * @author mac
 */
public class TestEventBus {

    public static void main(String[] args) {
        EventBus eventBus = new EventBus();

        eventBus.register(new EventListener());
        eventBus.register(new MyEventListener());

        eventBus.post("hello world");
        eventBus.post(new MyEvent(1, "lzb"));
    }

}

package com.lzb.guava.eventbus;

import com.google.common.eventbus.Subscribe;

public class EventListener {

    private static int eventsHandled;

    @Subscribe
    public void stringEvent(String event) {
        System.out.println("event:" + event);
        eventsHandled++;
    }
}
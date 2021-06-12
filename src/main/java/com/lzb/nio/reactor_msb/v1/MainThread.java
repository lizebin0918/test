package com.lzb.nio.reactor_msb.v1;

/**
 * 主线程:不做IO和业务的操作<br/>
 * Created on : 2021-06-12 11:14
 *
 * @author lizebin
 */
public class MainThread {

    public static void main(String[] args) {

        SelectorThreadGroup group = new SelectorThreadGroup(3);
        group.bind(8888);

    }

}

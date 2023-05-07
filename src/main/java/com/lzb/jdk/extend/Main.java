package com.lzb.jdk.extend;

/**
 * 作者原话：不错的隔离服务封层的设计技巧，也可以在一些复杂的业务场景中使用<br/>
 * Created on : 2023-05-07 20:39
 * @author mac
 */
public class Main {

    public static void main(String[] args) {
        Order order = new Order();
        order.pay();
    }
}

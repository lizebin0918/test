package com.lzb.nio.reactor;

public class Server {
    public static void main(String[] args) {
        Reactor reactor = new Reactor(8080);
        reactor.run();
    }
}
package com.lzb.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端<br/>
 * Created on : 2021-06-07 20:25
 * @author lizebin
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        //channel 表示main Reactor的组件是NioServerSocketChannel，负责：连接、IO读、IO写操作
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

        // childHandler 表示sub Reactor组件的处理器，负责：解码、业务逻辑、编码
        // 默认是用sub Reactor执行
        // 如果需要优化，可以在业务逻辑中加入线程池
        server.childHandler(new ServerInitializer());

        ChannelFuture future = server.bind(8080).sync();
        System.out.println("Web socket server started at port " + 8080);
        future.channel().closeFuture().sync();
    }

}

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
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);

        server.childHandler(new ServerInitializer());

        ChannelFuture future = server.bind(8080).sync();
        System.out.println("Web socket server started at port " + 8080);
        future.channel().closeFuture().sync();
    }

}

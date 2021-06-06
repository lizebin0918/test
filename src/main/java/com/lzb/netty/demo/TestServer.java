package com.lzb.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * <br/>
 * Created on : 2021-06-06 21:35
 * @author lizebin
 */
public class TestServer {

    public static void main(String[] args) throws InterruptedException {
        //定义事件轮询线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        //定义初始化器
        server.childHandler(new TestServerInitializer());

        ChannelFuture sync = server.bind(8080).sync();
        sync.channel().closeFuture().sync();
    }

}

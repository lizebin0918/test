package com.lzb.netty.handler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 测试自定义消息和handler<br/>
 * Created on : 2021-06-17 16:35
 *
 * @author chenpi
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //定义事件轮询线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class);
        //定义初始化器
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new StringDecoder());
                for (int i = 0; i < 5; i++) {
                    //pipeline.addLast(String.valueOf(i), new MySimpleHandler(String.valueOf(i)));
                }
            }
        });

        ChannelFuture sync = server.bind(8080).sync();
        sync.channel().closeFuture().sync();
    }


}

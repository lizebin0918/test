package com.lzb.netty.sinoxk.server;

import com.alibaba.fastjson.JSON;
import com.lzb.netty.sinoxk.common.RequestMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import java.util.UUID;

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
                pipeline.addLast(new MessageEncoderHandler());
                pipeline.addLast(new MessageDecoderHandler());
                pipeline.addLast(new ResponseMessageHandler());
                pipeline.addLast(new ConnectHandler());
            }
        });

        ChannelFuture sync = server.bind(8080).sync();
        NioServerSocketChannel channel = (NioServerSocketChannel) sync.channel();
        channel.closeFuture().sync();
    }


}

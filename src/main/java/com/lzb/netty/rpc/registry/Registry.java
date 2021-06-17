package com.lzb.netty.rpc.registry;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.*;


/**
 * 注册中心<br/>
 * Created on : 2021-06-06 17:16
 * @author lizebin
 */
public class Registry {

    /**
     * 对外注册端口
     */
    private int port;

    public Registry(int port) {
        this.port = port;
    }

    public void start() {

        // 基于NIO实现-Selector
        // 事件循环组-线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 主线程、Work线程
        ServerBootstrap server = new ServerBootstrap();
        server.group(bossGroup, workerGroup);

        //channel注册到selector上，并监听各个事件
        server.channel(NioServerSocketChannel.class);

        //相当于ServerSocketChannel.accept()，返回对应客户端
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //所有业务逻辑处理全部归总到了一个队列中
                //这个队列中包含了各种各样的处理逻辑，对这些处理逻辑在Netty中有一个封装
                //封装成一个对象，无锁化串行任务队列
                //note:入栈->1->3->5,出栈->5->4-2
                ChannelPipeline p = socketChannel.pipeline();

                //还原 InvokerProtocol
                //对于自定义协议，需要对内容编解码
                p.addLast("1", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                                                           0, 4, 0, 4));
                //自定义编码
                p.addLast("2", new LengthFieldPrepender(4));

                //还原 InvokerProtocol 参数
                //实参处理
                p.addLast("3", new ObjectEncoder());
                p.addLast("4", new ObjectDecoder(Integer.MAX_VALUE,
                                                       ClassResolvers.cacheDisabled(null)));
                //执行业务逻辑
                //1.注册：给每一个对象起一个名字
                p.addLast("5", new RegistryHandler());
            }
        });

        server.option(ChannelOption.SO_BACKLOG, 1024);
        server.childOption(ChannelOption.SO_KEEPALIVE, true);


        ChannelFuture future = null;
        try {
            future = server.bind(this.port).sync();
            System.out.println(" server listen ");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Registry(8080).start();
    }
}

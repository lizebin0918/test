package com.lzb.netty;

import com.lzb.netty.demo.TestServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 测试pipeline顺序及传播机制<br/>
 * Created on : 2021-06-16 22:02
 *
 * @author lizebin
 */
public class TestPipeline {

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
                for (int i = 0; i < 5; i++) {
                    pipeline.addLast(String.valueOf(i), new MySimpleHandler(String.valueOf(i)));
                }
            }
        });

        ChannelFuture sync = server.bind(8080).sync();
        sync.channel().closeFuture().sync();
    }



    private static class MySimpleHandler extends SimpleChannelInboundHandler<String> {

        private final String no;
        public MySimpleHandler(String no) {
            System.out.println(no);
            this.no = no;
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            System.out.println("simpleHandler:" + no);
            System.out.println("msg:" + msg);
        }
    }

    private static class MyHandler extends ChannelInboundHandlerAdapter {

        private final String no;
        public MyHandler(String no) {
            this.no = no;
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("registed:" + no);
        }

        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            System.out.println("unregistered:" + no);
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("active:" + no);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("inactive:" + no);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("read:" + no);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("complete:" + no);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("triggered:" + no);
        }

        @Override
        public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
            System.out.println("writabilityChanged:" + no);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                throws Exception {
            System.out.println("exceptionCaught:" + no);
        }
    }

}

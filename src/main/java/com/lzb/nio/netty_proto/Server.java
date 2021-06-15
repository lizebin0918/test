package com.lzb.nio.netty_proto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * nio模拟netty服务端<br/>
 * Created on : 2021-06-15 21:20
 *
 * @author lizebin
 */
public class Server {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup selector = new NioEventLoopGroup(1);
        NioServerSocketChannel server = new NioServerSocketChannel();

        selector.register(server);

        //提前注册处理器，一旦有事件马上响应
        ChannelPipeline pipeline = server.pipeline();
        //第一个是Handler是接收SocketClient
        pipeline.addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                //客户端需要注册读事件
                SocketChannel client = (SocketChannel) msg;
                client.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        ByteBuf buf = (ByteBuf) msg;
                        System.out.println(buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8));
                    }
                });
                selector.register(client);
            }
        });

        ChannelFuture bind = server.bind(new InetSocketAddress("127.0.0.1", 8080));
        bind.sync().channel().closeFuture().sync();
    }

}

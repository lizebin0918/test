package com.lzb.nio.netty_proto;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * netty的客户端原型<br/>
 * Created on : 2021-06-15 07:43
 *
 * @author lizebin
 */
public class Client {

    public static void main(String[] args) throws Exception {
        //单线程只有一个selector
        NioEventLoopGroup selector = new NioEventLoopGroup(1);
        //声明一个channel
        NioSocketChannel client = new NioSocketChannel();
        //channel注册到selector
        selector.register(client);
        //异步特征
        ChannelFuture connect = client.connect(new InetSocketAddress(8080));
        ByteBuf buf = Unpooled.copiedBuffer("hello server".getBytes(StandardCharsets.UTF_8));
        client.writeAndFlush(buf);

        //读事件
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                //读
                ByteBuf buf = (ByteBuf) msg;
                System.out.println(buf.getCharSequence(0, buf.readableBytes(), CharsetUtil.UTF_8));

                //写
                ctx.writeAndFlush(msg);
            }
        });

        //客户端同步发送，并等待服务端断开
        connect.sync().channel().closeFuture().sync();
        System.out.println("client done");

    }
}

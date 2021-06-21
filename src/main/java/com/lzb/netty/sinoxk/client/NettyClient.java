package com.lzb.netty.sinoxk.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Optional;

public class NettyClient {
    private String host;
    private Integer port;
    private EventLoopGroup group;
    private Bootstrap boot;
    private Integer keepAliveSeconds;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
        group = new NioEventLoopGroup();
        boot = new Bootstrap();
        boot.group(group).channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO));
        this.keepAliveSeconds = Optional.ofNullable(keepAliveSeconds).orElse(60);
    }

    public NettyClient() {

    }

    public void connect() {
        NettyClient client = this;
        try {
            boot.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline()
                        .addLast(new MessageDecoderHandler())
                        .addLast(new MessageEncoderHandler())
                        .addLast(new RequestMessageHandler());
                }
            });
            ChannelFuture future = boot.connect(host, port);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 8080);
        client.connect();
    }
}

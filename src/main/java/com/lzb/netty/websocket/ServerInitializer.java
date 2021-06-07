package com.lzb.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * <br/>
 * Created on : 2021-06-07 20:27
 * @author lizebin
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();

        p.addLast(new HttpServerCodec());
        p.addLast(new ChunkedWriteHandler());
        //分成10个段，汇总成一个完整的请求或者响应
        p.addLast(new HttpObjectAggregator(8192));
        p.addLast(new WebSocketServerProtocolHandler("/"));
        p.addLast(new TextWebSocketFrameHandler());


    }
}

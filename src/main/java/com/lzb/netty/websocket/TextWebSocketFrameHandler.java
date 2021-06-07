package com.lzb.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.time.LocalDateTime;

/**
 * 文本处理<br/>
 * Created on : 2021-06-07 20:35
 * @author lizebin
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println("收到消息:" + request);
        }
        String r = "消息收到了-" + LocalDateTime.now();
        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(r));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------handlerAdd----------");
        System.out.println("handlerAdd:" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("------------handlerRemoved----------");
        System.out.println("handlerRemoved:" + ctx.channel().id().asLongText());
    }

}

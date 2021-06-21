package com.lzb.netty.sinoxk.server;

import com.alibaba.fastjson.JSON;
import com.lzb.netty.sinoxk.common.ResponseMessage;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class ResponseMessageHandler extends SimpleChannelInboundHandler<ResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage message) throws Exception {
        long msgId = message.getMsgId();
        System.out.println("server receive : " + JSON.toJSONString(message));
    }
}
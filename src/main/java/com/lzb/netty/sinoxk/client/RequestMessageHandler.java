package com.lzb.netty.sinoxk.client;

import com.alibaba.fastjson.JSON;
import com.lzb.netty.sinoxk.common.RequestMessage;
import com.lzb.netty.sinoxk.common.ResponseMessage;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class RequestMessageHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RequestMessage message) throws Exception {
        long msgId = message.getMsgId();
        System.out.println("client receive : " + JSON.toJSONString(message));

        ResponseMessage response = new ResponseMessage();
        response.setMsgId(message.getMsgId());
        response.setBody("this is client response");
        channelHandlerContext.writeAndFlush(response);
    }
}
package com.lzb.netty.sinoxk.client;

import com.lzb.netty.sinoxk.common.MessageEncoder;
import com.lzb.netty.sinoxk.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoderHandler extends MessageToByteEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ResponseMessage message, ByteBuf byteBuf) throws Exception {
        MessageEncoder.encode(byteBuf, message);
    }
}
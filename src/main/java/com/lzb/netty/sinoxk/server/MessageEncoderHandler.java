package com.lzb.netty.sinoxk.server;

import com.lzb.netty.sinoxk.common.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Collections;

public class MessageEncoderHandler extends MessageToByteEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RequestMessage message, ByteBuf byteBuf) throws Exception {
        MessageEncoder.encode(byteBuf, message);
    }
}
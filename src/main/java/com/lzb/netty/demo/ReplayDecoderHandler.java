package com.lzb.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * <br/>
 * Created on : 2021-06-23 21:52
 *
 * @author lizebin
 */
public class ReplayDecoderHandler extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //MessageToByte 需要判断可读字节数是否满足
        //ReplayingDecoder 无需检查
        out.add(in.readLong());
    }

}

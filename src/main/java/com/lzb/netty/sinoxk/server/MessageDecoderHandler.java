package com.lzb.netty.sinoxk.server;

import com.alibaba.fastjson.JSON;
import com.lzb.netty.sinoxk.common.MessageDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MessageDecoderHandler extends ByteToMessageDecoder {

    public static final Integer MIN_LENGTH = 20;

    /**
     * @param context
     * @param in
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> out) throws Exception {
        //可读取字节长度
        //小于最小包
        if(in.readableBytes()<MIN_LENGTH){
            return;
        }
        in.markReaderIndex();
        //随机数
        int magic = in.readInt();
        System.out.println("magic=" + magic);
        //版本
        int version = in.readInt();
        System.out.println("version=" + version);
        int timestamp = in.readInt();
        System.out.println("timestamp=" + timestamp);
        long msgId = in.readLong();
        System.out.println("msgId=" + msgId);

        //类名长度
        int classNameLength = in.readInt();

        //剩余可读小于类全名
        if(in.readableBytes()<classNameLength){
            in.resetReaderIndex();
            return;
        }
        byte[] classNameByte = new byte[classNameLength];
        //读取类全名
        in.readBytes(classNameByte);
        String className = new String(classNameByte);
        //读取类字节长度
        int length = in.readInt();
        if(length<0){
            context.close();
        }
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        Object o = MessageDecoder.decode(length, className, in);
        out.add(o);
    }
}

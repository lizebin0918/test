package com.lzb.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        Unpooled.buffer(10);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<String> f = Executors.newCachedThreadPool().submit(() -> {
            System.out.println("begin sleep ");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("end sleep");
            return "1";
        });

        System.out.println("main start sleep");
        Thread.sleep(2000);
        System.out.println("main end sleep");
        String result = f.get();
        System.out.println(result);
    }
}

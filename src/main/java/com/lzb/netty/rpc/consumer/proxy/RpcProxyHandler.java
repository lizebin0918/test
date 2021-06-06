package com.lzb.netty.rpc.consumer.proxy;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * <br/>
 * Created on : 2021-06-06 21:15
 * @author lizebin
 */
public class RpcProxyHandler extends ChannelInboundHandlerAdapter {

    private Object response;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client read:" + JSON.toJSONString(msg));
        response = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public Object getResult() {
        return response;
    }
}

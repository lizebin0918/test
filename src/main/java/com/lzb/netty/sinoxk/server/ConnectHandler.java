package com.lzb.netty.sinoxk.server;

import com.alibaba.fastjson.JSON;
import com.lzb.netty.sinoxk.common.RequestMessage;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 连接handler<br/>
 * Created on : 2021-06-21 16:53
 *
 * @author chenpi
 */
@ChannelHandler.Sharable
public class ConnectHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        RequestMessage request = new RequestMessage();
        request.setOperationType("operationType");
        request.setOperationParam("operationParam");
        ctx.writeAndFlush(request);

        System.out.println("server send:" + JSON.toJSONString(request));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        NioSocketChannel client = (NioSocketChannel)ctx.channel();
        System.out.println("client disconnect:" + client.remoteAddress());
    }
}

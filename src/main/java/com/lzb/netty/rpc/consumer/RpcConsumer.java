package com.lzb.netty.rpc.consumer;

import com.lzb.netty.rpc.api.IRpcHelloService;
import com.lzb.netty.rpc.api.IRpcService;
import com.lzb.netty.rpc.consumer.proxy.RpcProxy;
import com.lzb.netty.rpc.provider.RpcServiceImpl;

/**
 * 客户端 <br/>
 * Created on : 2021-06-06 20:58
 * @author lizebin
 */
public class RpcConsumer {


    public static void main(String[] args) {
        IRpcService service = RpcProxy.create(IRpcService.class);
        int v = service.add(3, 2);
        System.out.println("v = " + v);

        IRpcHelloService service1 = RpcProxy.create(IRpcHelloService.class);
        System.out.println(service1.hello("lizebin"));
    }

}

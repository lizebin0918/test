package com.lzb.netty.rpc.provider;

import com.lzb.netty.rpc.api.IRpcHelloService;

/**
 * <br/>
 * Created on : 2021-06-06 17:13
 * @author lizebin
 */
public class RpcHelloServiceImpl implements IRpcHelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";
    }
}

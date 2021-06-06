package com.lzb.netty.rpc.provider;

import com.lzb.netty.rpc.api.IRpcService;

/**
 * <br/>
 * Created on : 2021-06-06 17:14
 * @author lizebin
 */
public class RpcServiceImpl implements IRpcService {

    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

    @Override
    public int mul(int a, int b) {
        return a * b;
    }

    @Override
    public int div(int a, int b) {
        return a / b;
    }
}

package com.lzb.netty.rpc.api;
/**
 * <br/>
 * Created on : 2021-06-06 17:08
 * @author lizebin
 */
public interface IRpcService {

    int add(int a, int b);
    int sub(int a, int b);
    int mul(int a, int b);
    int div(int a, int b);

}

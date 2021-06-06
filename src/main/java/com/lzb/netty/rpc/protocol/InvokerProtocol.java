package com.lzb.netty.rpc.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * 序列化协议<br/>
 * Created on : 2021-06-06 17:11
 * @author lizebin
 */
@Data
public class InvokerProtocol implements Serializable {

    //服务名
    private String className;
    //方法名
    private String methodName;
    // 参数类型
    private Class<?>[] params;
    // 参数值
    private Object[] values;

}

package com.lzb.netty.sinoxk.common;

import lombok.Data;

@Data
public class RequestMessage extends BaseMessage {

    private String operationType;

    private String operationParam;

}

package com.lzb.netty.sinoxk.common;

import lombok.Data;

/**
 * 消息实体<br/>
 * Created on : 2021-06-17 17:33
 *
 * @author chenpi
 */
@Data
public class ResponseMessage extends BaseMessage {

    private String body;

}

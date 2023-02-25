package com.lzb.mockito;

import lombok.Data;

/**
 * <br/>
 * Created on : 2023-02-12 11:18
 * @author mac
 */
@Data
public class Order {

	private long oid = 1;
	private boolean cancelFlag;

	public void cancel() {
		cancelFlag = true;
		System.out.println("取消订单");
	}

}

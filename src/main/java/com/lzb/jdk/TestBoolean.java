package com.lzb.jdk;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/>
 * Created on : 2022-12-24 16:06
 * @author lizebin
 */
public class TestBoolean {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(-2);
		list.add(3);

		// 默认都是大于0，任意一个false都要返回false
		boolean result = true;
		for (Integer i : list) {
			result &= (i > 0);
		}
		System.out.println(result);

		// 等价于上面的判断
		boolean result1 = false;
		for (Integer i : list) {
			result |= (i > 0);
		}
		System.out.println(result1);
	}

}

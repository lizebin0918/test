package com.lzb.jdk;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 测试重载<br/>
 * Created on : 2022-12-19 10:03
 * @author mac
 */
public class TestOverload {

	public static void main(String[] args) {
		TreeSet<Integer> set = new TreeSet<>();
		ArrayList<Integer> list = new ArrayList<>();

		for (int i = -3; i < 3; i++) {
			set.add(i);
			list.add(i);
		}

		for (int i = 0; i < 3; i++) {
			set.remove(i);
			// 这个会导致重载....移除了错误的元素
			// list.remove(i);
			list.remove((Integer)i);
		}

		System.out.println("set:" + set);
		System.out.println("list:" + list);
	}

}

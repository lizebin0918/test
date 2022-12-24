package com.lzb.jdk.generic;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 可变参数的泛型测试<br/>
 * Created on : 2022-12-17 10:04
 * @author mac
 */
public class Test1 {

	/**
	 * 这里返回的是Object[]......
	 *
	 * @param args
	 * @return
	 * @param <T>
	 */
	static <T> T[] toArray(T... args) {
		return args;
	}

	static <T> T[] pickTwo(T a, T b, T c) {
		switch (ThreadLocalRandom.current().nextInt(3)) {
		case 0:
			return toArray(a, b);
		case 1:
			return toArray(a, c);
		case 2:
			return toArray(b, c);
		}
		// can't get here
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		String[] attributes = pickTwo("Good", "luck", "man");
		System.out.println(Arrays.toString(attributes));
	}

}

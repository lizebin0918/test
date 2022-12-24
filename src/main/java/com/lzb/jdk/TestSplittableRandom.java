package com.lzb.jdk;

import java.util.SplittableRandom;
import java.util.stream.IntStream;

/**
 * 随机数<br/>
 * Created on : 2022-12-24 11:39
 * @author lizebin
 */
public class TestSplittableRandom {

	public static void main(String[] args) {
		// SplittableRandom 非线程安全的
		SplittableRandom random = new SplittableRandom(System.nanoTime());
		int i = random.nextInt(10);
		System.out.println(i);

		IntStream.range(0, 10).parallel().forEach(item -> {
			SplittableRandom split = random.split();
			System.out.println(split.nextInt(10));
		});
	}

}

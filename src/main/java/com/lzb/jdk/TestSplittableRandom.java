package com.lzb.jdk;

import java.util.SplittableRandom;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数<br/>
 * Created on : 2022-12-24 11:39
 * @author lizebin
 */
public class TestSplittableRandom {

	/**
	 * jdk17 可以这样声明，不会造成伪随机？网上说会造成共享实例，导致伪随机，但是我本机重现不了啊...但是还是不建议这种用法
	 */
	public static final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

	public static void main(String[] args) {
		// SplittableRandom 非线程安全
		SplittableRandom random = new SplittableRandom(System.nanoTime());
		int i = random.nextInt(10);
		System.out.println(i);

		// 多线程输出随机数程序
		for (int k = 0; k < 4; ++k) {
			new Thread(() -> {
				for (int j = 0; j < 5; ++j) {
					System.out.println(Thread.currentThread().getName() + "splitablerandom 第" + j + "个:" + random.nextInt(1000));
				}
			}, "thread" + k).start();
		}

		for (int k = 0; k < 4; ++k) {
			new Thread(() -> {
				for (int j = 0; j < 5; ++j) {
					System.out.println(Thread.currentThread().getName() + "splitablerandom.split() 第" + j + "个:" + random.split().nextInt(1000));
				}
			}, "thread" + k).start();
		}

		for (int k = 0; k < 4; ++k) {
			new Thread(() -> {
				for (int j = 0; j < 5; ++j) {
					System.out.println(Thread.currentThread().getName() + "threadLocalRandom 第" + j + "个:" + threadLocalRandom.nextInt(1000));
				}
			}, "thread" + k).start();
		}

	}
}

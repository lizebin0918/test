package com.lzb.jdk17;

import java.time.LocalDateTime;

/**
 * 模式匹配<br/>
 * Created on : 2022-11-06 11:54
 * @author lizebin
 */
public class TypeMatch {

	public static void main(String[] args) {
		System.out.println(format(LocalDateTime.now()));
	}

	public static void test(Object o) {
		if (o instanceof String s && s.length() > 10) {
			System.out.println("字符串长度大于10");
		} else if (o instanceof String s) {
			System.out.println("其他字符串");
		} else {
			System.out.println("其他类型");
		}
	}

	public static String format(Object o) {
		/*return switch (o) {
			case null -> "";
			case Number n -> NumberFormat.getNumberInstance().format(n);
			case LocalDateTime t -> t.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			default -> o.toString();
		};*/
		return null;
	}

}

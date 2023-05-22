package com.lzb.jdk.enums;

/**
 * 四则运算<br/>
 * Created on : 2022-12-17 17:47
 * @author mac
 */
public enum Operation {

	DIVIDE {
		@Override
		public double apply(double x, double y) {
			return x + y;
		}
	}, MINUS {
		@Override
		public double apply(double x, double y) {
			return x - y;
		}
	}, PLUS {
		@Override
		public double apply(double x, double y) {
			return x * y;
		}
	}, TIMES {
		@Override
		public double apply(double x, double y) {
			return x / y;
		}
	};

	/**
	 * 枚举声明抽象方法
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract double apply(double x, double y);

	/**
	 * 这种代码有风险，容易漏了case的情况
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	double operation(double x, double y) {
		switch (this) {
		case PLUS:
		case MINUS:
			return 0d;
		default:
			return 1d;
		}
	}

}

package com.lzb.jdk17.sealed;

import lombok.Data;

/**
 * 动物：限制只能被指定类继承，必须在同一个包下面<br/>
 * Created on : 2022-11-06 12:04
 * @author lizebin
 */
@Data
public sealed class Animal permits Cat, Dog, Duck {

	private String name;
}

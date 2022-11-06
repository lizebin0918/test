package com.lzb.jdk17.sealed;

/**
 * 狗：可以变成密封类，继续限制子类继承
 * 这么说下来 sealed 和 permits 要成对出现<br/>
 * Created on : 2022-11-06 12:05
 * @author lizebin
 */
public sealed class Dog extends Animal permits HaShiQi {

}

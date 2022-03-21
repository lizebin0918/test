package com.lzb.mock;

/**
 * <br/>
 * Created on : 2022-03-21 23:02
 *
 * @author lizebin
 */
public class MyService implements IMyService {

    /**
     * 返回固定字符串
     * @return
     */
    public static String staticReturnString() {
        return "a";
    }

    /**
     * 返回字符串
     * @return
     */
    public String returnString() {
        return "b";
    }

    /**
     * method with common method invoke
     */
    public String commonFunc() {
        return "anything".trim() + "__" + "anything".substring(1, 2) + "__" + "abc".startsWith("ab");
    }

}

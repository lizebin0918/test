package com.lzb.jdk.feature.jdk10;

/**
 * 类型推断优化<br/>
 * Created on : 2022-04-23 10:35
 *
 * @author lizebin
 */
public class TestSwitch {

    public static void main(String[] args) {

        String input = "1";



        String output1 = null;
        switch (input) {
            case "1" :
                System.out.println("this is 1");
                output1 = "1" + input;
                break;
            case "2":
                output1 = "2" + input;
                break;
            default:
                output1 = "invalidate";
        }
        System.out.println(output1);

        String output = switch (input) {
            case "1" -> {
                System.out.println("this is 1");
                yield "1" + input;
            }
            case "2" -> {
                yield "2" + input;
            }
            default -> "invalidate";
        };

        System.out.println(output);

    }

}

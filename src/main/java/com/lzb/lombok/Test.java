package com.lzb.lombok;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

/**
 * <br/>
 * Created on : 2022-03-13 16:47
 *
 * @author lizebin
 */
public class Test {

    public static void main(String[] args) {

        Student student = Student.builder().name("name").age(18).classId(1).grade("年级").id(1L).build();
        String studentString = JSON.toJSONString(student);
        System.out.println(studentString);

        Gson gson = new Gson();
        Student student1 = gson.fromJson(gson.toJson(student), Student.class);
        String student1String = JSON.toJSONString(student1);
        System.out.println(student1String);

    }

}

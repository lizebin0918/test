package com.lzb.jdk.extend;

/**
 * <br/>
 * Created on : 2023-06-16 23:22
 * @author mac
 */
public class GenericExtendTest {

    public static class Person<T> {

        public T get() {
            System.out.println("person get");
            return null;
        }

        public Object getObject() {
            return null;
        }

    }

    public static class Child extends Person<String> {
        @Override
        public String get() {
            return "";
        }

        @Override
        public String getObject() {
            return "";
        }
    }

    public static void main(String[] args) {
        Child child = new Child();
        child.get();
    }

}

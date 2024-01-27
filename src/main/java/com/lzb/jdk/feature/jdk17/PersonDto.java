package com.lzb.jdk.feature.jdk17;

/**
 * 这是一个dto:这是一个不可变对象
 *
 * @param name
 * @param age
 */
public record PersonDto(String name, int age) {

    public PersonDto {
        isValidate(name, age);
    }

    /**
     * 校验数据合法性
     * @param name
     * @param age
     * @return
     */
    private boolean isValidate(String name, int age) {
        return false;
    }

}

record StudentRecordV2(String name, int rollNo, int marks, char grade) {
    public StudentRecordV2(String name, int rollNo, int marks) {
        this(name, rollNo, marks, calculateGrade(marks));
    }

    private static char calculateGrade(int marks) {
        if (marks >= 90) {
            return 'A';
        } else if (marks >= 80) {
            return 'B';
        } else if (marks >= 70) {
            return 'C';
        } else if (marks >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}

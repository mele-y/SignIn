package com.example.signin.utility;

import com.example.signin.memberClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class studentInfo {
    private static List<memberClass> stu = new ArrayList<>();
    private static String classID = "";

    public static String getClassID() {
        return classID;
    }

    public static void setClassID(String classID) {
        studentInfo.classID = classID;
    }

    public static List<memberClass> getStu() {
        return stu;
    }

    public static void setStu(List<memberClass> stu) {
        studentInfo.stu = stu;
    }
}

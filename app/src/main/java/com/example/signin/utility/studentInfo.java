package com.example.signin.utility;

import com.example.signin.memberClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class studentInfo {
    private static List<memberClass> stu = new ArrayList<>();

    public static List<memberClass> getStu() {
        return stu;
    }

    public static void setStu(List<memberClass> stu) {
        studentInfo.stu = stu;
    }
}

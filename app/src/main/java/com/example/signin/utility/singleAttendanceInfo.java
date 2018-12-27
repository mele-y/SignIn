package com.example.signin.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class singleAttendanceInfo {
    private static List<signinInfo> attendances = new ArrayList<>();
    private static String rate = "";
    private static String classID = "";
    private static String stuID = "";

    public static String getStuID() {
        return stuID;
    }

    public static void setStuID(String stuID) {
        singleAttendanceInfo.stuID = stuID;
    }

    public static void setClassID(String classID) {
        singleAttendanceInfo.classID = classID;
    }

    public static String getClassID() {
        return classID;
    }

    public static List<signinInfo> getAttendances() {
        return attendances;
    }

    public static void setAttendances(List<signinInfo> attendances) {
        Collections.sort(attendances);
        singleAttendanceInfo.attendances = attendances;
        calculate();
    }

    public static String getRate() {
        return rate;
    }

    public static void setRate(String rate) {
        singleAttendanceInfo.rate = rate;
    }

    private static void calculate(){
        rate = "";
        if(attendances.size() > 0){
            double nu,de;
            nu = de = 0.0;
            for(int i=0;i<attendances.size();++i){
                de += 1.0;
                if(attendances.get(i).getAttendance().equals("1")) {
                    nu += 1.0;
                }
            }
            String r = String.valueOf(Double.valueOf(nu/de*100).intValue());
            rate = r+"%";
        }
    }
}

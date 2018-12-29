package com.example.signin.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class allAttendanceInfo {
    private static List<signinInfo> attendances = new ArrayList<>();
    private static List<signinInfo> attendanceRate = new ArrayList<>();
    private static String rate = "";
    private static String classID = "";

    public static void setClassID(String classID) {
        allAttendanceInfo.classID = classID;
    }

    public static String getClassID() {
        return classID;
    }

    public static List<signinInfo> getAttendances() {
        return attendances;
    }

    public static void setAttendances(List<signinInfo> attendances) {
        Collections.sort(attendances);
        allAttendanceInfo.attendances = attendances;
        calculate();
    }

    public static String getRate() {
        return rate;
    }

    public static void setRate(String rate) {
        allAttendanceInfo.rate = rate;
    }

    public static List<signinInfo> getAttendanceRate() {
        return attendanceRate;
    }

    public static void setAttendanceRate(List<signinInfo> attendanceRate) {
        allAttendanceInfo.attendanceRate = attendanceRate;
    }

    private static void calculate(){
        attendanceRate = new ArrayList<>();
        rate = "";
        int sz = attendances.size();
        if(sz > 0){
            String preTime, r;
            double nu, de, tnu, tde;
            nu = de = tnu = tde = 0;
            for(int i=0;i<sz;++i){
                preTime = attendances.get(i).getTime();
                de += 1;
                if(attendances.get(i).getAttendance().equals("1"))
                    nu += 1;
                if(i == sz - 1 || !attendances.get(i+1).getTime().equals(preTime)){
                    tnu += nu;
                    tde += de;
                    r = String.valueOf(Double.valueOf(nu/de*100).intValue());
                    attendanceRate.add(new signinInfo(preTime, r+"%", attendances.get(i).getWeek()));
                    de = nu = 0;
                }
            }
            r = String.valueOf(Double.valueOf(tnu/tde*100).intValue());
            rate = r+"%";
        }
    }

}

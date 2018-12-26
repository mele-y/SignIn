package com.example.signin.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class singleAttendanceInfo {
    private static List<signinInfo> attendances = new ArrayList<>();
    private static String rate = "";

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
        if(attendances.size() > 0){
            double nu,de;
            nu = de = 0.0;
            for(int i=0;i<attendances.size();++i){
                de += 1.0;
                if(attendances.get(i).getAttendance().equals("1")) {
                    nu += 1.0;
                }
            }
            String r = String.valueOf(nu/de*100);
            rate = r.substring(0, Math.min(r.length(), 2))+"%";
        }
        else
            rate = "";
    }
}

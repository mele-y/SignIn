package com.example.signin.utility;

public class signinInfo implements Comparable{
    private String time = "";
    private String attendance = "";
    public signinInfo(String time, String attendance){
        this.time = time;
        this.attendance = attendance;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getTime() {
        return time;
    }

    public String getAttendance() {
        return attendance;
    }

    @Override
    public int compareTo(Object o) {
        signinInfo s = (signinInfo)o;
        return s.time.compareTo(this.time);
    }
}

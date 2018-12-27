package com.example.signin.utility;

public class signinInfo implements Comparable{
    private String time = "";
    private String attendance = "";
    private String week = "";
    private String sname = "";
    private String sid = "";

    public signinInfo(String time, String attendance, String week){
        this.time = time;
        this.attendance = attendance;
        this.week = week;
    }

    public String getSid() {
        return sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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

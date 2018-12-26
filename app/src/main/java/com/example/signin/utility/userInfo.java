package com.example.signin.utility;

public class userInfo {
    private static String phonenum = "";
    private static String password = "";
    private static String nickname = "";
    private static String college = "";
    private static String major = "";
    private static int sex = -1;
    private static String ID = "";
    private static String realname = "";
    private static String ident = "";
    private static String token = "";

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        userInfo.token = token;
    }

    public static int getSex() {
        return sex;
    }

    public static String getMajor() {
        return major;
    }

    public static String getNickname() {
        return nickname;
    }

    public static String getIdent() {
        return ident;
    }

    public static void setSex(int sex) {
        userInfo.sex = sex;
    }

    public static void setRealname(String realname) {
        userInfo.realname = realname;
    }

    public static void setPhonenum(String phonenum) {
        userInfo.phonenum = phonenum;
    }

    public static void setPassword(String password) {
        userInfo.password = password;
    }

    public static void setNickname(String nickname) {
        userInfo.nickname = nickname;
    }

    public static void setMajor(String major) {
        userInfo.major = major;
    }

    public static void setIdent(String ident) {
        userInfo.ident = ident;
    }

    public static void setID(String ID) {
        userInfo.ID = ID;
    }

    public static void setCollege(String college) {
        userInfo.college = college;
    }

    public static String getRealname() {
        return realname;
    }

    public static String getPhonenum() {
        return phonenum;
    }

    public static String getPassword() {
        return password;
    }

    public static String getCollege() {
        return college;
    }

    public static String getID() {
        return ID;
    }
}

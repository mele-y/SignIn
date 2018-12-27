package com.example.signin.utility;

import com.example.signin.noticeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class allNoticeInfo {
    private static List<noticeInfo> notices = new ArrayList<>();
    private static String classID  = "";

    public static String getClassID() {
        return classID;
    }

    public static void setClassID(String classID) {
        allNoticeInfo.classID = classID;
    }

    public static List<noticeInfo> getNotices() {
        return notices;
    }

    public static void setNotices(List<noticeInfo> notices) {
        Collections.sort(notices);
        allNoticeInfo.notices = notices;
    }
}

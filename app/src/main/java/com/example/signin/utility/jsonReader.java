package com.example.signin.utility;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParsePosition;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.example.signin.classInfo;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.signin.utility.userInfo;
import com.example.signin.utility.classesInfo;
import com.example.signin.classInfo;
import com.example.signin.memberClass;
import com.example.signin.utility.studentInfo;
import com.example.signin.noticeInfo;
import com.example.signin.messageInfo;
import com.example.signin.utility.allMessageInfo;
import java.util.regex.*;

/**
 * Last Update Time: 2018/12/27
 * Description: use gson to analyze json obeject
 */

public class jsonReader {

    private static JsonParser parser = new JsonParser();

    public static String recvLogIn(String json){
        json = unicodeToString(json);
        String message = "";
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            if(jsonObj.get("access_token") == null){
                message = jsonObj.get("message").getAsString();
            }
            else{
                if(jsonObj.get("jobID").getAsString().equals("None")){
                    userInfo.setID(jsonObj.get("stuID").getAsString());
                    userInfo.setIdent("student");
                    userInfo.setRealname(jsonObj.get("srealname").getAsString());
                }
                else{
                    userInfo.setID(jsonObj.get("jobID").getAsString());
                    userInfo.setIdent("teacher");
                    userInfo.setRealname(jsonObj.get("trealname").getAsString());
                }
                userInfo.setToken(jsonObj.get("access_token").getAsString());
                userInfo.setCollege(jsonObj.get("college").getAsString());
                userInfo.setMajor(jsonObj.get("major").getAsString());
                userInfo.setNickname(jsonObj.get("nickname").getAsString());
                userInfo.setPassword(jsonObj.get("password").getAsString());
                userInfo.setPhonenum(jsonObj.get("phonenum").getAsString());
                userInfo.setSex(jsonObj.get("sex").getAsString());
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String recvMsg(String json){
        json = unicodeToString(json);
        String message = "";
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            message = jsonObj.get("message").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String recvStatus(String json){
        json = unicodeToString(json);
        String message = "";
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            message = jsonObj.get("status").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static String recvGetAllStudent(String json, String cid){
        json = unicodeToString(json);
        String status = "";
        List<memberClass> stu = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    stu.add(new memberClass(cls.get("stuID").getAsString(), cls.get("srealname").getAsString()));
                }
                studentInfo.setStu(stu);
                studentInfo.setClassID(cid);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String recvGetAllClass(String json){
        json = unicodeToString(json);
        String status = "";
        List<classInfo> classes = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    classes.add(new classInfo(cls.get("classname").getAsString(), cls.get("classID").getAsString()));
                }
                classesInfo.setClasses(classes);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<classInfo> recvGetClass(String json){
        json = unicodeToString(json);
        List<classInfo> classes = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            int status = jsonObj.get("status").getAsInt();
            if(status == 200){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    classes.add(new classInfo(cls.get("classname").getAsString(), cls.get("classID").getAsString()));
                }
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static String recvGetSingleAttendance(String json, String cid, String sid){
        json = unicodeToString(json);
        String status = "";
        List<signinInfo> list = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    if(sid.equals(cls.get("stuID").getAsString())){
                        list.add(new signinInfo(cls.get("time").getAsString(), cls.get("result").getAsString(), getWeek("2018-12-27")));
                    }
                }
                singleAttendanceInfo.setAttendances(list);
                singleAttendanceInfo.setClassID(cid);
                singleAttendanceInfo.setStuID(sid);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    private static String getWeek(String sdate) {
        Date date = strToDate(sdate);
        return new SimpleDateFormat("EEEE", Locale.CHINA).format(date);
    }

    private static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    public static String recvGetAllAttendance(String json, String cid){
        json = unicodeToString(json);
        String status = "";
        List<signinInfo> list = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    list.add(new signinInfo(cls.get("time").getAsString(), cls.get("result").getAsString(), getWeek("2018-12-27")));
                    list.get(i).setSid(cls.get("stuID").getAsString());
                    for(int j=0;j<studentInfo.getStu().size();++j){
                        if(studentInfo.getStu().get(j).getStu_id().equals(list.get(i).getSid()))
                            list.get(i).setSname(studentInfo.getStu().get(j).getStu_name());
                    }
                }
                allAttendanceInfo.setAttendances(list);
                allAttendanceInfo.setClassID(cid);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String recvGetAllNotice(String json, String cid){
        json = unicodeToString(json);
        String status = "";
        List<noticeInfo> list = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    list.add(new noticeInfo(cls.get("bid").getAsString(), cls.get("content").getAsString(), cls.get("time").getAsString()));
                }
                allNoticeInfo.setNotices(list);
                allNoticeInfo.setClassID(cid);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    private static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            //group 6728
            String group = matcher.group(2);
            //ch:'木' 26408
            ch = (char) Integer.parseInt(group, 16);
            //group1 \u6728
            String group1 = matcher.group(1);
            str = str.replace(group1, ch + "");
        }
        return str;
    }

    public static String recvGetAllMessage(String json, String cid){
        json = unicodeToString(json);
        String status = "";
        List<messageInfo> list = new ArrayList<>();
        try {
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    list.add(new messageInfo(cls.get("stuID").getAsString(), cls.get("time").getAsString(), cls.get("sender").getAsString(), cls.get("content").getAsString(), cls.get("isleave").getAsString()));
                    for(int j=0;j<studentInfo.getStu().size();++j){
                        if(studentInfo.getStu().get(j).getStu_id().equals(list.get(i).getStu_id()))
                            list.get(i).setStu_name(studentInfo.getStu().get(j).getStu_name());
                    }
                }
                allMessageInfo.setMessages(list);
                allMessageInfo.setClassID(cid);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }
}

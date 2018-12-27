package com.example.signin.utility;

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
/**
 * Last Update Time: 2018/12/27
 * Description: use gson to analyze json obeject
 */

public class jsonReader {
    /**
     * parse json (login)
     * @param jsonData JSON received from server
     */
    public String recvLogIn(String jsonData){
        String message = "";
        JsonParser parser = new JsonParser();  //创建json解析器
        try {
            JsonObject json = (JsonObject) parser.parse(jsonData);
            if(json.get("access_token") == null){
                message = json.get("message").getAsString();
            }
            else{
                if(json.get("jobID").getAsString().equals("None")){
                    userInfo.setID(json.get("stuID").getAsString());
                    userInfo.setIdent("student");
                    userInfo.setRealname(json.get("srealname").getAsString());
                }
                else{
                    userInfo.setID(json.get("jobID").getAsString());
                    userInfo.setIdent("teacher");
                    userInfo.setRealname(json.get("trealname").getAsString());
                }
                userInfo.setToken(json.get("access_token").getAsString());
                userInfo.setCollege(json.get("college").getAsString());
                userInfo.setMajor(json.get("major").getAsString());
                userInfo.setNickname(json.get("nickname").getAsString());
                userInfo.setPassword(json.get("password").getAsString());
                userInfo.setPhonenum(json.get("phonenum").getAsString());
                userInfo.setSex(json.get("sex").getAsInt());
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String recvMsg(String jsonData){
        String message = "";
        JsonParser parser = new JsonParser();  //创建json解析器
        try {
            JsonObject json = (JsonObject) parser.parse(jsonData);
            message = json.get("message").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String recvStatus(String jsonData){
        String message = "";
        JsonParser parser = new JsonParser();  //创建json解析器
        try {
            JsonObject json = (JsonObject) parser.parse(jsonData);
            message = json.get("status").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return message;
    }

    public String recvGetAllStudent(String json, String cid){
        String status = "";
        List<memberClass> stu = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
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

    public String recvGetAllClass(String json){
        String status = "";
        List<classInfo> classes = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
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

    public List<classInfo> recvGetClass(String json){
        List<classInfo> classes = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
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

    public String recvGetSingleAttendance(String json, String cid, String sid){
        String status = "";
        List<signinInfo> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    if(sid.equals(cls.get("stuID").getAsString()))
                        list.add(new signinInfo(cls.get("time").getAsString(), cls.get("result").getAsString(), getWeek("2018-12-27")));
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

    private String getWeek(String sdate) {
        Date date = strToDate(sdate);
        return new SimpleDateFormat("EEEE", Locale.CHINA).format(date);
    }

    private Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    public String recvGetAllAttendance(String json, String cid){
        String status = "";
        List<signinInfo> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsString();
            if(status.equals("200")){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    list.add(new signinInfo(cls.get("time").getAsString(), cls.get("result").getAsString(), getWeek("2018-12-27")));
                    list.get(i).setSid(cls.get("stuID").getAsString());
                    for(int j=0;j<studentInfo.getStu().size();++j)
                        if(studentInfo.getStu().get(j).getStu_id().equals(cls.get("stuID").getAsString())){
                            list.get(i).setSname(studentInfo.getStu().get(j).getStu_name());
                            break;
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
}

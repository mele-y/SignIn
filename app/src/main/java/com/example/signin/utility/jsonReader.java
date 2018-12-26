package com.example.signin.utility;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.signin.utility.userInfo;
import com.example.signin.utility.classesInfo;
import com.example.signin.classInfo;
import com.example.signin.memberClass;
import com.example.signin.utility.studentInfo;
/**
 * Last Update Time: 2018/12/20
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

    public String recvSignUp(String jsonData){
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

    public String recvCreateClass(String jsonData){
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

    public int recvGetAllStudent(String json){
        int status = -1;
        List<memberClass> stu = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsInt();
            if(status == 200){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    stu.add(new memberClass(cls.get("stuID").getAsString(), cls.get("srealname").getAsString()));
                }
                studentInfo.setStu(stu);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }

    public int recvGetAllClass(String json){
        int status = -1;
        List<classInfo> classes = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsInt();
            if(status == 200){
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

    public int recvGetSingleAttendance(String json, String sid){
        int status = -1;
        List<signinInfo> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            status = jsonObj.get("status").getAsInt();
            if(status == 200){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    if(sid.equals(cls.get("stuID").getAsString()))
                        list.add(new signinInfo(cls.get("time").getAsString(), cls.get("result").getAsString()));
                }
                singleAttendanceInfo.setAttendances(list);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return status;
    }
}

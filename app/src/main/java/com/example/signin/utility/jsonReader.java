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
        String token = "";
        JsonParser parser = new JsonParser();  //创建json解析器
        try {
            JsonObject json = (JsonObject) parser.parse(jsonData);
            if(json.get("access_token") == null){
                return json.get("message").getAsString();
            }
            else
                token = json.get("access_token").getAsString();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return token;
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

    public List<Map<String,Object>> recvSearchClass(String json){
        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        json = testSearchClass();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonArray jsonArray = parser.parse(json).getAsJsonArray();
            Gson gson = new Gson();
            //加强for循环遍历JsonArray
            for (JsonElement cls : jsonArray) {
                //使用GSON，直接转成Bean对象
                classInfo bean = gson.fromJson(cls, classInfo.class);
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("name",bean.getName());
                map.put("classId",bean.getClassId());
                listItem.add(map);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return listItem;
    }

    public String testSearchClass(){
        List<classInfo> classes = new ArrayList<classInfo>();
        classes.add(new classInfo("中国近代史","001"));
        classes.add(new classInfo("软件项目管理","002"));
        classes.add(new classInfo("UML","003"));
        classes.add(new classInfo("编译原理","004"));
        Gson gson = new Gson();
        String json = gson.toJson(classes);
        return json;
    }

    public List<Map<String,Object>> recvGetAllClass(String json){
        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        try {
            JsonParser parser = new JsonParser();  //创建json解析器
            JsonObject jsonObj = parser.parse(json).getAsJsonObject();
            int status = jsonObj.get("status").getAsInt();
            if(status == 200){
                JsonArray jsonArray = jsonObj.get("message").getAsJsonArray();
                Gson gson = new Gson();
                //加强for循环遍历JsonArray
                for (int i=0;i<jsonArray.size();++i) {
                    JsonObject cls = jsonArray.get(i).getAsJsonObject();
                    //使用GSON，直接转成Bean对象
                    Map<String,Object> map=new HashMap<String,Object>();
                    map.put("name",cls.get("classID").getAsString());
                    map.put("classId",cls.get("classname").getAsString());
                    System.out.println("=========================");
                    System.out.println(cls.get("classID").getAsString());
                    System.out.println(cls.get("classname").getAsString());
                    System.out.println("=========================");
                    listItem.add(map);
                }
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return listItem;
    }
}

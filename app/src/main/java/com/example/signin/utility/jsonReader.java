package com.example.signin.utility;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Last Update Time: 2018/12/20
 * Description: use gson to analyze json obeject
 */

public class jsonReader {
    /**
     * parse json (login)
     * @param jsonData JSON received from server
     */
    public String recvLogin(String jsonData){
        String message = "No response.";
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
}

package com.example.signin.utility;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.GsonBuilder;

/**
 * Last Update Time: 2018/12/20
 * Description: use OkHttp3 to post json to Http Server
 */

public class OkHttp {
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * get method
     * @param url server address with port
     */
    public String get(String url) {
        String result = "No response.";
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * post method
     * @param url server address with port
     * @param map map to send
     */
    public String postJson(String url, Map<String, String> map) {
        String result = "";
        Gson gson = new Gson();
        String json = gson.toJson(map);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String postForm(String url, Map<String, String> map) {
        String result = "";
        FormBody.Builder build = new FormBody.Builder();
        for (Map.Entry<String, String> item : map.entrySet()) {
            build.add(item.getKey(), item.getValue());
        }
        RequestBody body = build.build();
        Request request = new Request.Builder().addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String postFormWithToken(String url, Map<String, String> map) {
        String result = "";
        FormBody.Builder build = new FormBody.Builder();
        for (Map.Entry<String, String> item : map.entrySet()) {
            build.add(item.getKey(), item.getValue());
        }
        RequestBody body = build.build();
        Request request = new Request.Builder().addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").addHeader("Authorization", "jwt " + userInfo.getToken()).url(url).post(body).build();
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}

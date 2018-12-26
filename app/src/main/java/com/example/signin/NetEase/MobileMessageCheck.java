package com.example.signin.NetEase;

import java.io.IOException;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * description : 检测短信验证码工具类
 */

public class MobileMessageCheck {
    private static final String SERVER_URL="https://api.netease.im/sms/verifycode.action";//校验验证码的请求路径URL
    private static final String APP_KEY="be89ce390fa787c2a765fd7bf128e8f5";//账号
    private static final String APP_SECRET="3ace615d0f51";//密钥
    private static final String NONCE="123456";//随机数

    public static String checkMsg(String phone,String sum) throws Exception{
        String curTime=String.valueOf((new Date().getTime()/1000L));
        String checkSum=CheckSumBuilder.getCheckSum(APP_SECRET,NONCE,curTime);
        int code = -1;
        FormBody.Builder build = new FormBody.Builder();
        build.add("mobile", phone);
        build.add("code", sum);
        RequestBody body = build.build();
        Request request = new Request.Builder().addHeader("AppKey", APP_KEY).addHeader("Nonce", NONCE).addHeader("CurTime", curTime).addHeader("CheckSum", checkSum).addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8").url(SERVER_URL).post(body).build();
        OkHttpClient client = new OkHttpClient();
        JsonParser parser = new JsonParser();  //创建json解析器
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            JsonObject json = (JsonObject) parser.parse(result);
            code = json.get("code").getAsInt();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if(code == 200) {
            return "success";
        }
        else {
            return "error";
        }

    }
}

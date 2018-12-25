package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;

import java.util.HashMap;
import java.util.Map;

public class editTeaInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tea_info);
    }

//    private void sendCreatclassRequest(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try{
//                    Map<String, String> map = new HashMap<>();
////                    map.put("teacherid", user_phone);
////                    map.put("password", password);
//                    OkHttp okhttp = new OkHttp();
//                    String result = okhttp.post(" ", map);
//                    jsonReader reader = new jsonReader();
//                    String recvMessage = reader.recvLogIn(result);
//                    if(recvMessage.equals("success"))
//                        goIntent();
//                    else
//                        showResponse(recvMessage);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    //Http测试用函数
//    private void showResponse(final String response){
//        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                //在这里进行UI操作，将结果显示到界面上
//                chromToast.showToast(logIn.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
//            }
//        });
//    }

    //跳转页面 未改，用于跳到创建班课成功后界面
//    private void goIntent(){
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                chromToast.showToast(logIn.this, "登录成功", false, 0xAA00FF7F, 0xFFFFFFFF);
//                Intent intent=new Intent(logIn.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//    }




}

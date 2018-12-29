package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;

import java.util.HashMap;
import java.util.Map;

public class createClass extends AppCompatActivity {
    private EditText class_id,lesson_name;
    private  String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_class);
        Toolbar toolbar1=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button btn_create=findViewById((R.id.btn_create));
        class_id=findViewById(R.id.class_id);
        lesson_name=findViewById(R.id.lesson_name);
        btn_create.setOnClickListener(new View.OnClickListener()//点击创建班课跳转主页面 查看所有班课
        {
            @Override
            public void onClick(View v)
            {
                id=class_id.getText().toString().trim();
                name=lesson_name.getText().toString().trim();
                sendCreateClassRequest();
            }
        });
    }

    private void sendCreateClassRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    map.put("classID", id);
                    map.put("classname", name);
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/addclass", map);
                    if(result.equals("")){
                        showResponse("网络连接异常");
                    }
                    else{
                        if(jsonReader.recvStatus(result).equals("200")){
                            goIntent();
                        }
                        else
                            showResponse(jsonReader.recvMsg(result));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(createClass.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            //返回上一层
            case android.R.id.home:
                this.finish();
                return super.onOptionsItemSelected(item);
            default:
        }
        return true;
    }

    //跳转页面
    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(createClass.this, "创建成功", false, 0xAA00FF7F, 0xFFFFFFFF);
                finish();
            }
        });
    }
}

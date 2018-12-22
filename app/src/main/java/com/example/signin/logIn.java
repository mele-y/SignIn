package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.signin.utility.OkHttp;

import java.util.HashMap;
import java.util.Map;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;

public class logIn extends AppCompatActivity {
    private Button btn_register;
    private Button btn_login;
    private EditText et_user_phone,et_psw;//编辑框 手机号和密码
    private String user_phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        btn_register=findViewById(R.id.btn_register);
        et_user_phone=findViewById(R.id.user_phone);
        et_psw=findViewById(R.id.password);
        btn_login=findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
          @Override
            public void onClick(View v)
          {
            Intent intent=new Intent(logIn.this,SignIn.class);
            startActivity(intent);
          }
        });

        //点击登录按钮,跳转主界面
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user_phone=et_user_phone.getText().toString().trim();//获取手机号
                password=et_psw.getText().toString().trim();//获取密码

                sendLoginRequest();
            }
        });
    }
    private void sendLoginRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", "18260071012");
                    map.put("password", "12345678");
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.post("http://98.142.138.123:5000/login", map);
                    jsonReader reader = new jsonReader();
                    String recvMessage = reader.recvLogin(result);
                    if(recvMessage.equals("success"))
                        goIntent();
                    else
                        showResponse(recvMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //Http测试用函数
    private void showResponse(final String response){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                chromToast.showToast(logIn.this, response, true, 0xAAFF6100, 0xAAFFFFFF);
            }
        });
    }

    //跳转页面
    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(logIn.this, "success", false, 0xAA00FF7F, 0xAAFFFFFF);
                Intent intent=new Intent(logIn.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

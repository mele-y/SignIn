package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
                //以下判断跳转主页面
                Intent intent=new Intent(logIn.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}

package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {
    private Button btn_register, btn_send,btn_gender;
    private EditText et_user_phone,et_psw,et_birthday,et_code,et_major,et_college,et_id_num,et_user_name;//编辑框 手机号和密码
    private String user_phone,password,birthday,code,major,college,id_num,user_name,gender;
    private RadioButton male,female;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);
        male=findViewById(R.id.male_radio);
        female=findViewById(R.id.female_radio);
        btn_register=findViewById(R.id.btn_register);
        btn_send=findViewById(R.id.btn_send);
        et_user_phone=findViewById(R.id.user_phone);
        et_psw=findViewById(R.id.password);
        et_birthday=findViewById(R.id.birthday);
        et_code=findViewById(R.id.code);
        et_college=findViewById(R.id.college);
        et_id_num=findViewById(R.id.id_num);
        et_major=findViewById(R.id.major);
        et_user_name=findViewById(R.id.user_name);
        btn_register.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
            @Override
            public void onClick(View v)
            {
                user_phone=et_user_phone.getText().toString().trim();//以下获取用户信息
                password=et_psw.getText().toString().trim();
                birthday=et_birthday.getText().toString().trim();
                code=et_code.getText().toString().trim();
                major=et_major.getText().toString().trim();
                college=et_major.getText().toString().trim();
                id_num=et_id_num.getText().toString().trim();
                user_name=et_user_name.getText().toString().trim();
                if(male.isChecked())
                {
                    gender="male";
                }
                else if(female.isChecked()){
                   gender="female";
                }
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SignIn.this,logIn.class);//跳转登录主界面
                startActivity(intent);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v)
        {
         //发送验证码给手机
        }
        }

        );

    }
}

package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;

import java.util.HashMap;
import java.util.Map;

import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class SignIn2 extends AppCompatActivity {
    private QMUIRoundButton btn_register;
    private EditText et_major,et_college,et_id_num,et_user_name,nickname;
    private String major,college,id_num,user_name,gender,identity,user_phone,password,nick_name;
    private RadioButton male,female,tea,stu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page2);
        Intent intent = getIntent();
        //succession
        user_phone = intent.getStringExtra("user_phone");
        password = intent.getStringExtra("password");
        Toolbar toolbar1 = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //back
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        male=findViewById(R.id.male_radio);
        female=findViewById(R.id.female_radio);
        tea=findViewById(R.id.tea_radio);
        stu=findViewById(R.id.stu_radio);
        btn_register=(QMUIRoundButton )findViewById(R.id.btn_register0);
        et_college=findViewById(R.id.college);
        nickname=findViewById(R.id.nick_name);
        et_id_num=findViewById(R.id.id_num);
        et_major=findViewById(R.id.major);
        et_user_name=findViewById(R.id.user_name);
        btn_register.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
            @Override
            public void onClick(View v)
            {
                nick_name=nickname.getText().toString().trim();
                major=et_major.getText().toString().trim();
                college=et_college.getText().toString().trim();
                id_num=et_id_num.getText().toString().trim();
                user_name=et_user_name.getText().toString().trim();
                if(male.isChecked())
                {
                    gender="1";
                }
                else if(female.isChecked()){
                    gender="0";
                }
                if(tea.isChecked())
                {
                    identity="teacher";
                }
                else if(stu.isChecked()){
                    identity="student";
                }
                if(!(id_num.length()==10))
                    showResponse("ID需为10位", false);
                else if(gender.isEmpty())
                    showResponse("请选择性别", false);
                else if(user_name.isEmpty())
                    showResponse("请输入姓名", false);
                else if(identity.isEmpty())
                    showResponse("请选择身份", false);
                else
                    sendSignUpRequest();
            }
        });
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(SignIn2.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(SignIn2.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void sendSignUpRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", user_phone);
                    map.put("password", password);
                    map.put("nickname", nick_name);
                    map.put("college", college);
                    map.put("major", major);
                    map.put("sex", gender);
                    map.put("ID", id_num);
                    map.put("realname", user_name);
                    map.put("ident", identity);
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postForm("http://98.142.138.123:12345/api/register", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        if(jsonReader.recvStatus(result).equals("200"))
                            goIntent();
                        else
                            showResponse(jsonReader.recvMsg(result), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //跳转页面
    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showResponse("注册成功", true);
                userInfo.setPhonenum(user_phone);
                Intent intent=new Intent(SignIn2.this,logIn.class);//跳转登录主界面
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                return super.onOptionsItemSelected(item);
            default:
        }
        return true;
    }

}


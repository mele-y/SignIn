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
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class logIn extends AppCompatActivity {
    private EditText et_user_phone,et_psw;//编辑框 手机号和密码
    private String user_phone,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        et_user_phone=findViewById(R.id.user_phone);
        et_user_phone.setText(userInfo.getPhonenum());
        et_psw=findViewById(R.id.password);
        et_psw.setText(userInfo.getPassword());
        QMUIRoundButton btn_login=( QMUIRoundButton)findViewById(R.id.btn_login);
        QMUIRoundButton btn_register=( QMUIRoundButton)findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
          @Override
            public void onClick(View v)
          {
            Intent intent = new Intent(logIn.this, SignIn.class);
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
                sendLogInRequest();
            }
        });
    }
    private void sendLogInRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    userInfo.setToken("");
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", user_phone);
                    map.put("password", password);
                    String result = OkHttp.postJson("http://98.142.138.123:12345/login", map);
                    if(result.equals("")){
                        showResponse("网络连接异常");
                    }
                    else{
                        String recvMessage = jsonReader.recvLogIn(result);
                        if(!userInfo.getToken().equals("")) {
                            goIntent();
                        }
                        else {
                            showResponse(recvMessage);
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(logIn.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    //跳转页面
    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(logIn.this, "登录成功", false, 0xAA00FF7F, 0xFFFFFFFF);
                if(userInfo.getIdent().equals("student")){
                    Intent intent =new Intent(logIn.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent=new Intent(logIn.this,tea_Main.class);
                    startActivity(intent);
                }
            }
        });
    }
}

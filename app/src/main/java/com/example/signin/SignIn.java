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
import java.util.regex.*;

import com.example.signin.utility.chromToast;
import com.example.signin.NetEase.SendMessage;
import com.example.signin.NetEase.MobileMessageCheck;

public class SignIn extends AppCompatActivity {
    private Button btn_next, btn_send;
    private EditText et_user_phone,et_psw,et_code,et_confirm;//编辑框 手机号和密码
    private String user_phone,password,code,confirm_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //back
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btn_next=findViewById(R.id.next);
        btn_send=findViewById(R.id.btn_send);
        et_user_phone=findViewById(R.id.user_phone);
        et_psw=findViewById(R.id.password);
        et_confirm=findViewById(R.id.paw_confirm);
        et_code=findViewById(R.id.code);

        btn_next.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
            @Override
            public void onClick(View v)
            {
                user_phone=et_user_phone.getText().toString().trim();//以下获取用户信息
                password=et_psw.getText().toString().trim();
                confirm_psw=et_confirm.getText().toString().trim();
                code=et_code.getText().toString().trim();
            //判断验证码 密码确认是否正确若正确 跳转
                if(user_phone.isEmpty())
                    showResponse("请输入手机号码", false);
                else if(!(user_phone.length() == 11 && isNumeric(user_phone)))
                    showResponse("手机号码不合法", false);
                else if(password.isEmpty())
                    showResponse("请输入密码", false);
                else if(confirm_psw.isEmpty())
                    showResponse("请确认密码", false);
                else if(code.isEmpty())
                    showResponse("请输入验证码", false);
                else if(!password.equals(confirm_psw))
                    showResponse("两次密码不一致", false);
                else if(!(password.length()>=8 && password.length()<=20))
                    showResponse("密码长度需为8到20位", false);
                else
                    checkCode(user_phone, code);
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                user_phone=et_user_phone.getText().toString().trim();
                //发送验证码给手机
                sendMessage(user_phone);
            }
        }
        );

    }

    private void sendMessage(final String phone){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if(phone.length() == 11 && isNumeric(phone)) {
                        if(SendMessage.sendMsg(phone).equals("success"))
                            showResponse("发送成功", true);
                        else
                            showResponse("操作失败", false);
                    }
                    else
                        showResponse("手机号码不合法", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkCode(final String phone, final String code){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    if(MobileMessageCheck.checkMsg(phone, code).equals("success")) {
//                         showResponse("校验成功", true);
                         goIntent();
//                    }
//                    else
//                        showResponse("校验失败", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(SignIn.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(SignIn.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
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

    //跳转页面
    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SignIn.this,SignIn2.class);//跳转下一页主界面
                intent.putExtra("user_phone", user_phone);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });
    }
}

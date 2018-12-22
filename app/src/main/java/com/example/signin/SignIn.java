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
                //Toast.makeText(getApplicationContext(),"验证成功",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(SignIn.this,SignIn2.class);//跳转下一页主界面
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

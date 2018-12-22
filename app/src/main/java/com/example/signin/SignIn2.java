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

public class SignIn2 extends AppCompatActivity {
    private Button btn_register,btn_identify,btn_gender;
    private EditText et_major,et_college,et_id_num,et_user_name;//
    private String major,college,id_num,user_name,gender,identify;
    private RadioButton male,female,tea,stu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_page2);
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar1);
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
        btn_register=findViewById(R.id.btn_register);
        et_college=findViewById(R.id.college);
        et_id_num=findViewById(R.id.id_num);
        et_major=findViewById(R.id.major);
        et_user_name=findViewById(R.id.user_name);
        btn_register.setOnClickListener(new View.OnClickListener()//点击注册按钮,跳转注册界面
        {
            @Override
            public void onClick(View v)
            {

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
                if(tea.isChecked())
                {
                    identify="tea";
                }
                else if(stu.isChecked()){
                    identify="stu";
                }
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_LONG).show();
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


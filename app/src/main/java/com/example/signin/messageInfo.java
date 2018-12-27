package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class messageInfo extends AppCompatActivity {
    private String stu_name;//
    private String context,type;//显示具体内容

    public messageInfo(String stu_name,String context,String type)
    {
        this.stu_name=stu_name;
        this.context=context;
        this.type=type;
    }

    public String getStu_name(){return stu_name;}
    public String getContext(){return context;}
    public String getType(){return type;}
    public void setType(String type){this.type=type;}
    public void setStu_name(String stu_name){this.stu_name=stu_name;}
    public void setContext(String context){this.context=context;}
}

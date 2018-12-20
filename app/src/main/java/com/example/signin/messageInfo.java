package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class messageInfo extends AppCompatActivity {
    private String _title;//显示留言or公告
    private String _context;//显示具体内容

    public messageInfo(String headline,String context)
    {
        this._title=headline;
        this._context=context;
    }

    public String getHeadline(){return _title;}
    public String getContext(){return _context;}
    public void setTitle(String headline){this._title=headline;}
    public void setContext(String context){this._context=context;}
}

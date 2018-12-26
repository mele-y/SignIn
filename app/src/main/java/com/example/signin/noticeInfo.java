package com.example.signin;
import android.support.v7.app.AppCompatActivity;
public class noticeInfo {
    private String title;//
    private String context;//显示具体内容

    public noticeInfo(String title,String context)
    {
        this.title=title;
        this.context=context;
    }

    public String getTitle(){return title;}
    public String getContext(){return context;}
    public void setTitle(String title){this.title=title;}
    public void setContext(String context){this.context=context;}
}

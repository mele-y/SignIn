package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class message_detail extends AppCompatActivity {
private String stu_name,context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        stu_name=(String)intent.getCharSequenceExtra("stu_name");//获取学生信息
        context=(String)intent.getCharSequenceExtra("context");//获取请加内容
        getSupportActionBar().setTitle("学生消息");
        toolbar.setSubtitle(stu_name);//设置标题与副标题


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return  true;

        }
        return super.onOptionsItemSelected(item);
    }
}

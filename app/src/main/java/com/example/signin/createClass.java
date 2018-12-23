package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createClass extends AppCompatActivity {
    private  Button btn_create;
    private EditText class_id,lesson_name;
    private  String id,name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_class);
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //back
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        btn_create=findViewById((R.id.btn_create));
        class_id=findViewById(R.id.class_id);
        lesson_name=findViewById(R.id.lesson_name);
        btn_create.setOnClickListener(new View.OnClickListener()//点击创建班课跳转主页面 查看所有班课
        {
            @Override
            public void onClick(View v)
            {
                id=class_id.getText().toString().trim();
                name=lesson_name.getText().toString().trim();
                //if true 判断是否创建成功 
                    Toast.makeText(getApplicationContext(),"创建成功",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(createClass.this,tea_Main.class);
                    startActivity(intent);






            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            //返回上一层
            case android.R.id.home:
                this.finish();
                return super.onOptionsItemSelected(item);
            default:
        }
        return true;
    }
}

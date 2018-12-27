package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class teasend_message extends AppCompatActivity {
    private EditText reply_title,reply_context;
    private String title,context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);
        Toolbar toolbar=(Toolbar)findViewById(R.id.reply_toolbar);//获取TOOLBAR实例
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reply_context=findViewById(R.id.reply_context);
        reply_title=findViewById(R.id.reply_title);
        QMUIRoundButton btn=(QMUIRoundButton)findViewById(R.id.reply_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击发布,逻辑层交接存储数据,内容存储到学生信息,title,context
                context=reply_context.getText().toString().trim();
                title=reply_title.getText().toString().trim();
                Toast.makeText(teasend_message.this ,"发送成功",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(teasend_message.this,tea_Enter_main.class);//跳转
                startActivity(intent);
            }
        });
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


package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class message_detail extends AppCompatActivity {
private String stu_name,context,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar=(Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        title=(String)intent.getCharSequenceExtra("title");//获取学生信息
        context=(String)intent.getCharSequenceExtra("context");//获取请加内容
        stu_name=(String)intent.getCharSequenceExtra("stu_name");//获取请加内容
        getSupportActionBar().setTitle("学生消息");
        toolbar.setSubtitle(stu_name);//设置标题与副标题
        TextView title0=findViewById(R.id.title0);
        TextView context0=findViewById(R.id.context0);
        title0.setText(title);//逻辑层获取数据
        context0.setText(context);
        QMUIRoundButton btn=(QMUIRoundButton)findViewById(R.id.ignore);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();//返回界面
            }
        });
        QMUIRoundButton btn0=(QMUIRoundButton)findViewById(R.id.reply);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击回复,逻辑层交接

                Intent intent =new Intent(message_detail.this,teasend_message.class);
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

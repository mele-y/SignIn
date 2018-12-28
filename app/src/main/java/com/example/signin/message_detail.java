package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class message_detail extends AppCompatActivity {
private String classID,stuID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String title=intent.getStringExtra("type").equals("1")?"请假":"留言";
        String content=intent.getStringExtra("content");
        String stu_name=intent.getStringExtra("stu_name");
        classID=intent.getStringExtra("classID");
        stuID=intent.getStringExtra("stuID");
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar=findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        getSupportActionBar().setTitle("学生留言");
        toolbar.setSubtitle(stu_name);//设置标题与副标题
        TextView title0=findViewById(R.id.title0);
        TextView context0=findViewById(R.id.context0);
        title0.setText(title);//逻辑层获取数据
        context0.setText(content);
        QMUIRoundButton btn=findViewById(R.id.ignore);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回界面
            }
        });
        QMUIRoundButton btn0=findViewById(R.id.reply);
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击回复,逻辑层交接
                Intent intent =new Intent(message_detail.this,teasend_message.class);
                intent.putExtra("classID", classID);
                intent.putExtra("stuID", stuID);
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

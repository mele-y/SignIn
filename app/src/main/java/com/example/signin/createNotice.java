
package com.example.signin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
public class createNotice extends AppCompatActivity {
    private EditText notice_title,notice_context;//编辑框 手机号和密码
    private String title,context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notice);
        Toolbar toolbar=(Toolbar)findViewById(R.id.create_notice_toolbar);//获取TOOLBAR实例
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notice_context=findViewById(R.id.notice_context);
        notice_title=findViewById(R.id.notice_title);
        QMUIRoundButton btn=(QMUIRoundButton)findViewById(R.id.release);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击发布,逻辑层交接存储数据
                context=notice_context.getText().toString().trim();
                title=notice_title.getText().toString().trim();
                Toast.makeText(createNotice.this ,"发布成功",Toast.LENGTH_SHORT).show();
            finish(); }
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

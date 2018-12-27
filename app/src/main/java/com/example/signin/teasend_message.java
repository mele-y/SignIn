package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class teasend_message extends AppCompatActivity {//逻辑层判断是学生发给老师还是老师发给学生
    private EditText reply_title,reply_content;
    private String title,content, classID, stuID, ident;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        classID = intent.getStringExtra("classID");
        stuID = intent.getStringExtra("stuID");
        ident = intent.getStringExtra("ident");
        setContentView(R.layout.sendmessage);
        Toolbar toolbar=(Toolbar)findViewById(R.id.reply_toolbar);//获取TOOLBAR实例
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        reply_content=findViewById(R.id.reply_context);
        reply_title=findViewById(R.id.reply_title);
        QMUIRoundButton btn=(QMUIRoundButton)findViewById(R.id.reply_confirm);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击发布,逻辑层交接存储数据,内容存储到学生信息,title,context
                content=reply_content.getText().toString().trim();
                title=reply_title.getText().toString().trim();
                sendMessageRequest();
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

    private void sendMessageRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", ident);
                    map.put("ID", stuID);
                    map.put("content", content);
                    map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(System.currentTimeMillis()));
                    map.put("isleave", (ident.equals("student")&&title.equals("请假条"))?"1":"0");
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/addmessage", map);
                    if(result.equals(""))
                        showResponse("网络连接异常", false);
                    else{
                        if(jsonReader.recvStatus(result).equals("200"))
                            goIntent();
                        else
                            showResponse(jsonReader.recvMsg(result),false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(teasend_message.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(teasend_message.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void goIntent(){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                chromToast.showToast(teasend_message.this, "发送成功", false, 0xAA00FF7F, 0xFFFFFFFF);
                if(ident.equals("teacher")){
                    Intent intent=new Intent(teasend_message.this,tea_Enter_main.class);//判断学生还是教师跳转
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(teasend_message.this,studentEnterClass.class);
                    startActivity(intent);
                }
            }
        });
    }
}


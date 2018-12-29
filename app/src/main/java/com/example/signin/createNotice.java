package com.example.signin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.HashMap;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.example.signin.utility.allNoticeInfo;

public class createNotice extends AppCompatActivity {
    private EditText notice_title,notice_content;//编辑框 手机号和密码
    private String title,content,classID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        classID = intent.getStringExtra("classID");
        setContentView(R.layout.activity_create_notice);
        Toolbar toolbar=findViewById(R.id.create_notice_toolbar);//获取TOOLBAR实例
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        notice_content=findViewById(R.id.notice_context);
        notice_title=findViewById(R.id.notice_title);
        QMUIRoundButton btn=findViewById(R.id.release);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击发布,逻辑层交接存储数据
                content=notice_content.getText().toString().trim();
                title=notice_title.getText().toString().trim();
                sendCreateNoticeRequest();
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

    private void sendCreateNoticeRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    map.put("title", title);
                    map.put("content", content);
                    map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(System.currentTimeMillis()));
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/addbulletin", map);
                    if(result.equals(""))
                        showResponse("网络连接异常");
                    else{
                        if(jsonReader.recvStatus(result).equals("200")){
                            goIntent();
                        }
                        else
                            showResponse(jsonReader.recvMsg(result));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(createNotice.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void goIntent(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(createNotice.this, "创建成功", false, 0xAA00FF7F, 0xFFFFFFFF);
                sendGetAllNoticeRequest();
                finish();
            }
        });
    }

    private void sendGetAllNoticeRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getbulletin", map);
                    jsonReader.recvGetAllNotice(result, classID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

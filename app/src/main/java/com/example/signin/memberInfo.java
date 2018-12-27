package com.example.signin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import com.example.signin.utility.singleAttendanceInfo;
import com.example.signin.utility.signinInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class memberInfo extends AppCompatActivity {
    private String name, classId, stu_id, stu_name;
    private List<signinInfo> data = new ArrayList<>();
    QMUIGroupListView day_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        classId = intent.getStringExtra("classId");//课程信息
        stu_id = intent.getStringExtra("stu_id");//学生信息
        stu_name = intent.getStringExtra("stu_name");
        if(!(singleAttendanceInfo.getClassID().equals(classId) && singleAttendanceInfo.getStuID().equals(stu_id)))
            sendGetSingleAttendanceRequest();
        data = singleAttendanceInfo.getAttendances();
        String title2 = singleAttendanceInfo.getRate();//逻辑层获取信息修改title2
        setContentView(R.layout.activity_member_info);
        Toolbar stu_toolbar = (Toolbar) findViewById(R.id.member_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(stu_name);
        stu_toolbar.setSubtitle(stu_id);//设置标题与副标题

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.send_message);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(memberInfo.this,teasend_message.class);
                startActivity(intent);
            }
        });//点击发送消息,传递学生信息给下一个activity

        day_list = findViewById(R.id.day_list);//获取列表
        day_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);

        QMUIGroupListView.Section section = QMUIGroupListView.newSection(memberInfo.this).setTitle("总出勤率                                                                                                  " + title2);
        for (int i = 0; i < data.size(); ++i) {
            QMUICommonListItemView msg = day_list.createItemView(data.get(i).getTime());
            msg.setDetailText(data.get(i).getAttendance());
            section.addItemView(msg, null);
        }
        section.addTo(day_list);//将section加入列表

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }//左上角返回按钮事件

    private void sendGetSingleAttendanceRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    map.put("classID", classId);
                    map.put("ID", userInfo.getID());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        jsonReader reader = new jsonReader();
                        reader.recvGetSingleAttendance(result, classId, stu_id);
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
                    chromToast.showToast(memberInfo.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(memberInfo.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

}
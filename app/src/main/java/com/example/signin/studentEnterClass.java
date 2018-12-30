package com.example.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;

import java.util.HashMap;
import java.util.Map;
import com.example.signin.utility.studentInfo;
import com.example.signin.utility.singleAttendanceInfo;
import com.example.signin.utility.allNoticeInfo;
import com.example.signin.utility.allMessageInfo;

public class studentEnterClass extends AppCompatActivity {

   private Fragment[] fragments;
   int lastfragment;
   private String name, classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        name = intent.getStringExtra("name");
        classId = intent.getStringExtra("classId");
        setContentView(R.layout.activity_student_enter_class);
        Toolbar stu_toolbar=findViewById(R.id.stu_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题

        stuClassMemberFragment f1=new stuClassMemberFragment();
        stuMessageFragment f2=new stuMessageFragment();
        stuSignInFragment f3=new stuSignInFragment();
        lastfragment=0;
        fragments=new Fragment[]{f1,f2,f3};
        getSupportFragmentManager().beginTransaction().replace(R.id.stuEnterClass_mainView,f1).commit();//设置默认碎片
        BottomNavigationView bottomNavigationView=findViewById(R.id.stu_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //导航栏点击事件
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.stu_class_member: {
                        if (lastfragment != 0) {
                            switchFragment(lastfragment, 0);
                            lastfragment = 0;
                        }
                        return true;
                    }
                    case R.id.stu_message: {
                        if (lastfragment != 1) {
                            switchFragment(lastfragment, 1);
                            lastfragment = 1;
                        }
                        return true;
                    }
                    case R.id.stu_sign_in: {
                        if (lastfragment != 2) {
                            switchFragment(lastfragment, 2);
                            lastfragment = 2;
                        }
                        return true;
                    }
                  }
                return false;
            }
        });
    }

    public String getName() { return name; }
    public  String getClassId() { return classId; }
      @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_message,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.send:
                Intent intent = new Intent(studentEnterClass.this, teasend_message.class);
                intent.putExtra("classID", classId);
                intent.putExtra("stuID", userInfo.getID());
                startActivity(intent);
                finish();
                break;
            default:
        }
        return true;
    }//左上角返回按钮事件

    public void switchFragment(int lastfragment, int index){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if(!fragments[index].isAdded())
        {
            transaction.add(R.id.stuEnterClass_mainView,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }//切换不同片段布局转换

    private void sendGetAllStudentRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classId);
                    map.put("ident", userInfo.getIdent());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getallstudent", map);
                    jsonReader.recvGetAllStudent(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

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
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader.recvGetSingleAttendance(result, classId, userInfo.getID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendGetAllMessageRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classId);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getmessage", map);
                    jsonReader.recvGetAllMessage(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendGetAllNoticeRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classId);
                    map.put("ident", userInfo.getIdent());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getbulletin", map);
                    jsonReader.recvGetAllNotice(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!studentInfo.getClassID().equals(classId))
            sendGetAllStudentRequest();
        if(!allNoticeInfo.getClassID().equals(classId))
            sendGetAllNoticeRequest();
        if(!allMessageInfo.getClassID().equals(classId))
            sendGetAllMessageRequest();
        if(!(singleAttendanceInfo.getClassID().equals(classId) && singleAttendanceInfo.getStuID().equals(userInfo.getID())))
            sendGetSingleAttendanceRequest();
    }
}

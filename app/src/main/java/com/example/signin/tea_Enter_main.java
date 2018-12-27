package com.example.signin;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.signin.utility.OkHttp;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.studentInfo;
import com.example.signin.utility.userInfo;
import com.example.signin.utility.allAttendanceInfo;
import java.util.HashMap;
import java.util.Map;
import com.example.signin.utility.allNoticeInfo;
import com.example.signin.utility.allMessageInfo;

public class tea_Enter_main extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private teaMemberFra f1;
    private  teaNoticeFra f2;
    private mainSignFra f3;
    private Fragment[] fragments;
    int lastfragment;
    private  String name,classId;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        name = intent.getStringExtra("name");
        classId = intent.getStringExtra("classId");
        if(!studentInfo.getClassID().equals(classId))
            sendGetAllStudentRequest();
        if(!allNoticeInfo.getClassID().equals(classId)) {
            sendGetAllNoticeRequest();
        }
        if(!allMessageInfo.getClassID().equals(classId))
            sendGetAllMessageRequest();
        if(!allAttendanceInfo.getClassID().equals(classId))
            sendGetAllAttendanceRequest();
        setContentView(R.layout.tea__enter_main);
        Toolbar stu_toolbar=(Toolbar)findViewById(R.id.tea_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题
        f1=new teaMemberFra();
        f2=new teaNoticeFra();
        f3=new mainSignFra();
        lastfragment=1;
        fragments=new Fragment[]{f1,f2,f3};
        getSupportFragmentManager().beginTransaction().replace(R.id.teaEnterClass_mainView,f2).commit();//设置默认碎片
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.tea_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.class_member: {
                        if (lastfragment != 0) {
                            switchFragment(lastfragment, 0);
                            lastfragment = 0;

                        }
                        return true;
                    }
                    case R.id.message_notice: {
                        if (lastfragment != 1) {
                            switchFragment(lastfragment, 1);
                            lastfragment = 1;
                        }
                        return true;
                    }
                    case R.id.create_sign: {
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
    public String getName()
    {
        return name;
    }
    public  String getClassId()
    {
        return classId;
    }
@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_notice,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            case R.id.notice ://创建公告
                Intent intent=new Intent(this,createNotice.class);
                intent.putExtra("classID", classId);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
    public void switchFragment(int lastfragment, int index){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if(!fragments[index].isAdded())
        {
            transaction.add(R.id.teaEnterClass_mainView,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }
    private void sendGetAllStudentRequest(){
        new Thread(new Runnable() {

            @Override

            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", getClassId());
                    map.put("ident", userInfo.getIdent());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getallstudent", map);
                    jsonReader.recvGetAllStudent(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void sendGetAllAttendanceRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classId);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader.recvGetAllAttendance(result, classId);
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
                    map.put("ID", "2333333333");
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getmessage", map);
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
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getbulletin", map);
                    jsonReader.recvGetAllNotice(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
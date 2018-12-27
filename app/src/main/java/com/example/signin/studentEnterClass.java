package com.example.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;

import java.util.HashMap;
import java.util.Map;
import com.example.signin.utility.studentInfo;
import com.example.signin.utility.singleAttendanceInfo;
public class studentEnterClass extends AppCompatActivity {



   private BottomNavigationView bottomNavigationView;
   private stuClassMemberFragment f1;
   private stuMessageFragment f2;
    private stuSignInFragment f3;
   private Fragment[] fragments;
   int lastfragment;
   private String name, classId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        name= (String) intent.getCharSequenceExtra("name");
        classId= (String) intent.getCharSequenceExtra("classId");
        if(!studentInfo.getClassID().equals(classId))
            sendGetAllStudentRequest();
        if(!(singleAttendanceInfo.getClassID().equals(classId) && singleAttendanceInfo.getStuID().equals(userInfo.getID())))
            sendGetSingleAttendanceRequest();
        setContentView(R.layout.activity_student_enter_class);
        Toolbar stu_toolbar=(Toolbar)findViewById(R.id.stu_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题

        f1=new stuClassMemberFragment();
        f2=new stuMessageFragment();
        f3=new stuSignInFragment();
        lastfragment=0;
        fragments=new Fragment[]{f1,f2,f3};
        getSupportFragmentManager().beginTransaction().replace(R.id.stuEnterClass_mainView,f1).commit();//设置默认碎片
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.stu_bottom_nav);
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

    public String getName()
    {
        return name;
    }
    public  String getClassId()
    {
        return classId;
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

    public void switchFragment(int lastfragment, int index){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if(fragments[index].isAdded()==false)
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
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getallstudent", map);
                    jsonReader reader = new jsonReader();
                    reader.recvGetAllStudent(result, classId);
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
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader reader = new jsonReader();
                    reader.recvGetSingleAttendance(result, classId, userInfo.getID());
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
                    chromToast.showToast(studentEnterClass.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(studentEnterClass.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }
}

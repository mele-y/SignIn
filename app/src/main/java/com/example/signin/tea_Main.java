package com.example.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tea_Main extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;  //定义滑动菜单布局
    TextView user_name;
    TextView nick_name;
    TextView id_no;
    TextView college;
    TextView major;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sendGetClassRequest();
        setContentView(R.layout.tea__main);
        //初始化课程
        listView=findViewById(R.id.class_view1);
        LayoutInflater inflater = LayoutInflater.from(this);
        View headView=inflater.inflate(R.layout.list_title2,null);
        listView.addHeaderView(headView);
        Toolbar toolbar1 = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar1);
        mDrawerLayout = findViewById(R.id.drawer_layout);//获取滑动菜单实例
        NavigationView navView = findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }//设置标题栏左上角图标
        View headerView = navView.getHeaderView(0);
        user_name = headerView.findViewById(R.id.user_name_show);
        nick_name = headerView.findViewById(R.id.nick_name_show);
        id_no = headerView.findViewById(R.id.id_no_show);
        college = headerView.findViewById(R.id.college_show);
        major = headerView.findViewById(R.id.major_show);
        user_name.setText(userInfo.getRealname());
        nick_name.setText(userInfo.getNickname());
        id_no.setText(userInfo.getID());
        major.setText(userInfo.getMajor());
        college.setText(userInfo.getCollege());
        //设置滑动菜单的显示内容

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_exit:
                        Intent intent = new Intent(tea_Main.this, logIn.class);
                        startActivity(intent);//点击退出跳转至登录页
                        break;
                    default:
                }
                return true;
            }

        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_class_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;//若左上角菜单被选中打开滑动菜单
            case R.id.create_class:
                Intent intent = new Intent(this, createClass.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private void sendGetClassRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getclass", map);
                    if(result.equals("")){
                        showResponse();
                    }
                    else{
                        List<classInfo> classes = jsonReader.recvGetClass(result);
                        fillList(classes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void fillList(final List<classInfo> classes){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                classAdapter adapter = new classAdapter(tea_Main.this, R.layout.class_item, classes);
                listView.setAdapter(adapter);//设置ListView显示的内容
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void  onItemClick(AdapterView<?>parent,View view,int position,long id)//获取学生点击了的课程传递参数
                    {
                        classInfo class_=classes.get((int)id);
                        sendGetAllNoticeRequest(class_.getClassId());
                        sendGetAllStudentRequest(class_.getClassId());
                        sendGetAllMessageRequest(class_.getClassId());
                        sendGetAllAttendanceRequest(class_.getClassId());
                        Intent intent=new Intent(tea_Main.this, tea_Enter_main.class);
                        intent.putExtra("name", class_.getName());
                        intent.putExtra("classId",class_.getClassId());
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void sendGetAllStudentRequest(final String classId){
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

    private void sendGetAllAttendanceRequest(final String classId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classId);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader.recvGetAllAttendance(result, classId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendGetAllNoticeRequest(final String classId){
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

    private void sendGetAllMessageRequest(final String classId){
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

    private void showResponse(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chromToast.showToast(tea_Main.this, "网络连接异常", true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sendGetClassRequest();
    }
}
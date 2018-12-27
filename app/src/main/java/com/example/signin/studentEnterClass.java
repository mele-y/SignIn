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
        setContentView(R.layout.activity_student_enter_class);
        Toolbar stu_toolbar = (Toolbar) findViewById(R.id.stu_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        name = (String) intent.getCharSequenceExtra("name");
        classId = (String) intent.getCharSequenceExtra("classId");
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题

        f1 = new stuClassMemberFragment();
        f2 = new stuMessageFragment();
        f3 = new stuSignInFragment();
        lastfragment = 0;
        fragments = new Fragment[]{f1, f2, f3};
        getSupportFragmentManager().beginTransaction().replace(R.id.stuEnterClass_mainView, f1).commit();//设置默认碎片
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.stu_bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            //导航栏点击事件
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
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


    public String getName() {
        return name;
    }

    public String getClassId() {
        return classId;
    }


    public void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.stuEnterClass_mainView, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }//切换不同片段布局转换

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_message, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.send:
                Intent intent = new Intent(studentEnterClass.this, teasend_message.class);
                startActivity(intent);
                break;
            default:
        }

        return true;
    }
}
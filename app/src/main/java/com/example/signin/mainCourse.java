package com.example.signin;

import android.content.Intent;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mainCourse extends AppCompatActivity {//学生留言界面
    private messageInfo[] data={ new messageInfo("留言","周五交作业吗?"),
            new messageInfo("留言","周四交报告吗?"),
            new messageInfo("公告","明天考试"),
            new messageInfo("公告","明天不上课")
    };//学生对应留言 老师对应公告 放在同一个显示栏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_course);
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar1);
        //back
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        for(int i=0;i<data.length;i++)
        { Map<String,Object> map=new HashMap<String,Object>();
            map.put("_title",data[i].getHeadline());
            map.put("_context",data[i].getContext());
            listItem.add(map);
        }
        ListView listView=(ListView)findViewById(R.id.list_message);
        SimpleAdapter adapter=new SimpleAdapter(this,listItem,R.layout.messge_item,new String[]{"_title","_context"},new int[]{R.id._title,R.id._context});
        listView.setAdapter(adapter);
//点击了某个listview 跳转到对应的班课
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            //跳转发公告页面和签到页面
            case R.id.create_notice:

                break;
            case R.id.create_sign:
//                Intent intent=new Intent(this, main_Sign.class);
//                startActivity(intent);
                break;
            case android.R.id.home:
                this.finish();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);

    }
}

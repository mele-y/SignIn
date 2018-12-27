package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.example.signin.utility.allAttendanceInfo;
import com.example.signin.utility.signinInfo;

import java.util.List;
import java.util.ArrayList;

public class absence_info extends AppCompatActivity {
    private String name,classId,time;
    private List<signinInfo> data = new ArrayList<>();
    QMUIGroupListView absence_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = allAttendanceInfo.getAttendances();
        setContentView(R.layout.activity_absence_info);
        Toolbar stu_toolbar=(Toolbar)findViewById(R.id.absence_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        name = (String) intent.getStringExtra("name");
        classId = (String) intent.getStringExtra("classId");//课程信息
        time = (String) intent.getStringExtra("time");
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题

        absence_list=findViewById(R.id.absence_list);//获取列表
        absence_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);
        int tot = 0;
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(absence_info.this);
        for(int i=0;i<data.size();++i){
            if(data.get(i).getTime().equals(time) && data.get(i).getAttendance().equals("0")){
                QMUICommonListItemView msg = absence_list.createItemView(data.get(i).getSname());
                msg.setDetailText(data.get(i).getSid());
                section.addItemView(msg,null);
                tot += 1;
            }
        }
        String title2 = String.valueOf(tot);//逻辑层获取信息修改title2
        section.setTitle("缺勤人数                                                                                                  "+ title2);
        section.addTo(absence_list);

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

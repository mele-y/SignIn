package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import com.example.signin.utility.singleAttendanceInfo;
import com.example.signin.utility.signinInfo;

import java.util.ArrayList;
import java.util.List;

public class memberInfo extends AppCompatActivity {
     private String name,classId,stu_id,stu_name;
     private List<signinInfo> data = new ArrayList<>();
    QMUIGroupListView  day_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = singleAttendanceInfo.getAttendances();
        String title2=singleAttendanceInfo.getRate();//逻辑层获取信息修改title2
        setContentView(R.layout.activity_member_info);
        Toolbar stu_toolbar=(Toolbar)findViewById(R.id.member_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        name= (String) intent.getCharSequenceExtra("name");
        classId= (String) intent.getCharSequenceExtra("classId");//课程信息
        stu_id=(String)intent.getCharSequenceExtra("stu_id");//学生信息
        stu_name=(String)intent.getCharSequenceExtra("stu_name");
        getSupportActionBar().setTitle(stu_name);
        stu_toolbar.setSubtitle(stu_id);//设置标题与副标题


        day_list=findViewById(R.id.day_list);//获取列表
        day_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);

        QMUIGroupListView.Section section=QMUIGroupListView.newSection(memberInfo.this).setTitle("总出勤率                                                                                                  "+ title2);
        if(data.size() > 0){
            List<QMUICommonListItemView> lst = new ArrayList<>();
            for(int i=0;i<data.size();++i){
                QMUICommonListItemView msg = day_list.createItemView(data.get(i).getTime());
                msg.setDetailText(data.get(i).getAttendance());
                lst.add(msg);
                section.addItemView(lst.get(i),null);
            }
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
    }
}

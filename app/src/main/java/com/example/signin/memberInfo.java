package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class memberInfo extends AppCompatActivity {
     private String name,classId,pre,ab,stu_id,stu_name;
    QMUIGroupListView  day_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        QMUICommonListItemView msg1=day_list.createItemView("2018-12-20");
        pre="出勤";
        ab="缺勤";
        msg1.setDetailText(pre);


        QMUICommonListItemView msg2=day_list.createItemView("2018-12-25");
        msg2.setDetailText(ab);


        String title2="50%";//逻辑层获取信息修改title2
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(memberInfo.this).setTitle("总出勤率                                                                                                  "+ title2);
        section.addItemView(msg1,null);
        section.addItemView(msg2,null);
        section.addTo(day_list);


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

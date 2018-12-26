package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class absence_info extends AppCompatActivity {
    private String name,classId,stu_id,stu_name;
    QMUIGroupListView absence_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence_info);
        Toolbar stu_toolbar=(Toolbar)findViewById(R.id.absence_toolbar);//获取TOOLBAR实例
        setSupportActionBar(stu_toolbar);//把TOOLBAR设为标题栏
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        name= (String) intent.getCharSequenceExtra("name");
        classId= (String) intent.getCharSequenceExtra("classId");//课程信息
        getSupportActionBar().setTitle(name);
        stu_toolbar.setSubtitle(classId);//设置标题与副标题


        absence_list=findViewById(R.id.absence_list);//获取列表
        absence_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);
        stu_id="001";stu_name="学生1";
        QMUICommonListItemView msg1=absence_list.createItemView(stu_name);
        msg1.setDetailText(stu_id);

        stu_id="002";stu_name="学生2";
        QMUICommonListItemView msg2=absence_list.createItemView(stu_name);
        msg2.setDetailText(stu_id);


        String title2="2";//逻辑层获取信息修改title2
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(absence_info.this).setTitle("缺勤人数                                                                                                  "+ title2);
        section.addItemView(msg1,null);
        section.addItemView(msg2,null);
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

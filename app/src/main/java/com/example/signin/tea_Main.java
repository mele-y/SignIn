package com.example.signin;

import android.app.TabActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class tea_Main extends AppCompatActivity {
    //学生留言界面
    private classInfo[] data={ new classInfo("media","1"),
            new classInfo("C++","2"),
            new classInfo("UML","3"),
            new classInfo("JAVA","4")
    };   //要显示在列表中的内容

    private DrawerLayout mDrawerLayout;  //定义滑动菜单布局
    TextView user_name;
    TextView nick_name;
    TextView id_no;
    TextView college;
    TextView major;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tea__main);
        Toolbar toolbar1=(Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar1);
        //back
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);//获取滑动菜单实例

        NavigationView navView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }//设置标题栏左上角图标
        View headerView=navView.getHeaderView(0);
        user_name = (TextView) headerView.findViewById(R.id.user_name_show);
        nick_name=(TextView) headerView.findViewById(R.id.nick_name_show);
        id_no=(TextView) headerView.findViewById(R.id.id_no_show);
        college=(TextView) headerView.findViewById(R.id.college_show);
        major=(TextView) headerView.findViewById(R.id.major_show);
        user_name.setText("who");
        nick_name.setText("老师");
        id_no.setText("0000");
        major.setText("软件工程");
        college.setText("南京理工大学");
        //设置滑动菜单的显示内容


        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        for(int i=0;i<data.length;i++)
        { Map<String,Object> map=new HashMap<String,Object>();
            map.put("name",data[i].getName());
            map.put("classId",data[i].getClassId());
            listItem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(this,listItem,R.layout.class_item,new String[]{"name","classId"},new int[]{R.id.class_name,R.id.class_id});
        ListView listView=(ListView)findViewById(R.id.class_view1);
        listView.setAdapter(adapter);//设置ListView显示的内容
//点击了某个listview 跳转到对应的班课

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case  R.id.nav_edit:
                        Intent intent1=new Intent(tea_Main.this,editTeaInfo.class);
                        startActivity(intent1);
                        break;//点击编辑跳转至编辑个人信息
                    case R.id.nav_exit:
                        Intent intent2=new Intent(tea_Main.this,logIn.class);
                        startActivity(intent2);//点击退出跳转至登录页
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
                Intent intent=new Intent(this,createClass.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

}
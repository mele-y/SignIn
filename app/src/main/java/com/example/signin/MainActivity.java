package com.example.signin;

import android.app.TabActivity;
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



public class MainActivity extends AppCompatActivity {
    private classInfo[] data={ new classInfo("中国近代史","001"),
            new classInfo("软件项目管理","002"),
            new classInfo("UML","003"),
            new classInfo("编译原理","004")
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);//获取TOOLBAR实例
        setSupportActionBar(toolbar);//把TOOLBAR设为标题栏
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
       user_name.setText("叶剑波");
        nick_name.setText("sai");
        id_no.setText("916106840344");
        major.setText("软件工程");
        college.setText("南京理工大学");
        //设置滑动菜单的显示内容

          TabHost tabHost=(TabHost)findViewById(android.R.id.tabhost);//获取选项卡实例
        tabHost.setup();
        LayoutInflater inflater=LayoutInflater.from(this);
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("我加入的课程").setContent(R.id.left));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我创建的课程").setContent(R.id.right)); //设置选项卡显示的布局

        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        for(int i=0;i<data.length;i++)
        { Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",data[i].getName());
        map.put("classId",data[i].getClassId());
         listItem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,listItem,R.layout.class_item,new String[]{"name","classId"},new int[]{R.id.class_name,R.id.class_id});
        ListView listView=(ListView)findViewById(R.id.class_view1);
        listView.setAdapter(adapter);//设置ListView显示的内容
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }//给标题栏加载右上角菜单

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;//若左上角菜单被选中打开滑动菜单

                default:
        }
        return true;
    }
}

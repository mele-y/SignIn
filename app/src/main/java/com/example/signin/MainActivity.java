package com.example.signin;

import android.app.TabActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;

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
    };
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
          mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
          TabHost tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();
        LayoutInflater inflater=LayoutInflater.from(this);
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("我加入的课程").setContent(R.id.left));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("我创建的课程").setContent(R.id.right));

        List<Map<String,Object>> listItem=new ArrayList<Map<String,Object>>();
        for(int i=0;i<data.length;i++)
        { Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",data[i].getName());
        map.put("classId",data[i].getClassId());
         listItem.add(map);
        }
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,listItem,R.layout.class_item,new String[]{"name","classId"},new int[]{R.id.class_name,R.id.class_id});
        ListView listView=(ListView)findViewById(R.id.class_view1);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

                default:
        }
        return true;
    }
}

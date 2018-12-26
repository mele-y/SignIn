package com.example.signin;

import android.app.TabActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.AdapterView;
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

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;



public class MainActivity extends AppCompatActivity {

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
        user_name.setText(userInfo.getRealname());
        nick_name.setText(userInfo.getNickname());
        id_no.setText(userInfo.getID());
        major.setText(userInfo.getMajor());
        college.setText(userInfo.getCollege());
        //设置滑动菜单的显示内容
        listView=findViewById(R.id.class_view);

        sendGetClassRequest();
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.join_class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SearchClass.class);
                startActivity(intent);
            }
        });//点击加号跳转到搜索班课界面

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case  R.id.nav_edit:
                        Intent intent1=new Intent(MainActivity.this,editStudentInfo.class);
                        startActivity(intent1);
                        break;//点击编辑跳转至编辑个人信息
                    case R.id.nav_exit:
                        Intent intent2=new Intent(MainActivity.this,logIn.class);
                        startActivity(intent2);//点击退出跳转至登录页
                        break;
                        default:
                }
                return true;
            }

        });
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
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getclass", map);
                    jsonReader reader = new jsonReader();
                    List<classInfo> classes = reader.recvGetClass(result);
                    fillList(classes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void fillList(final List<classInfo> classes){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                classAdapter adapter = new classAdapter(MainActivity.this, R.layout.class_item, classes);
                listView.setAdapter(adapter);//设置ListView显示的内容
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void  onItemClick(AdapterView<?>parent,View view,int position,long id)//获取学生点击了的课程传递参数
                    {
                        classInfo class_=classes.get(position);
                        chromToast.showToast(MainActivity.this, class_.getName(), false, 0xAA00FF7F, 0xFFFFFFFF);
                        Intent intent=new Intent(MainActivity.this,studentEnterClass.class);
                        intent.putExtra("name", class_.getName().toString());
                        intent.putExtra("classId",class_.getClassId().toString());
                        startActivity(intent);
                        //可以getId获得标识
                    }
                });
            }
        });
    }

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

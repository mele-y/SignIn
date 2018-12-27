package com.example.signin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.example.signin.utility.classesInfo;

public class SearchClass extends AppCompatActivity {
    private SearchView mSearchView;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);
        mSearchView = findViewById(R.id.search_class_view);
        mListView = findViewById(R.id.search_class_list_view);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //当搜索提交时触发该方法
            public boolean onQueryTextSubmit(String query) {
                if(classesInfo.getClasses().size() == 0)
                    sendGetAllClassRequest();
                List<classInfo> classes = classesInfo.searchClass(query);
                fillList(classes);
                return false;
            }
          //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                List<classInfo> classes = classesInfo.searchClass(newText);
                fillList(classes);
                return false;
            }
        });
    }

    private void sendGetAllClassRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getallclass", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        jsonReader.recvGetAllClass(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(SearchClass.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(SearchClass.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void fillList(final List<classInfo> classes){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                classAdapter adapter = new classAdapter(SearchClass.this, R.layout.class_item, classes);
                mListView.setAdapter(adapter);//设置ListView显示的内容
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void  onItemClick(AdapterView<?>parent, View view, int position, long id)//获取学生点击了的课程传递参数
                    {
                        classInfo class_=classes.get(position);
                        chromToast.showToast(SearchClass.this, class_.getName(), false, 0xAA00FF7F, 0xFFFFFFFF);
                        Intent intent=new Intent(SearchClass.this,studentEnterClass.class);
                        intent.putExtra("name", class_.getName().toString());
                        intent.putExtra("classId",class_.getClassId().toString());
                        startActivity(intent);
                        //可以getId获得标识
                    }
                });
            }
        });
    }
}

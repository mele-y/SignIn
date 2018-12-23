package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.jsonReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.signin.classInfo;

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
//                Map<String, String> mp = new HashMap<>();
//                mp.put("classId", query);
//                OkHttp okhttp = new OkHttp();
//                String result = okhttp.post("http://98.142.138.123:5000/searchClass", mp);
                jsonReader reader = new jsonReader();
                List<Map<String,Object>> listItem = reader.recvSearchClass("123");
                if(listItem.size() > 0){
                    SimpleAdapter adapter=new SimpleAdapter(SearchClass.this,listItem,R.layout.class_item,new String[]{"name","classId"},new int[]{R.id.class_name,R.id.class_id});
                    mListView.setAdapter(adapter);//设置ListView显示的内容
                    return true;
                }
                else return false;
            }
          //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}

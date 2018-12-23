package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.jsonReader;
import java.util.List;
import java.util.Map;

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
                sendSearchClassRequest();
                return true;
            }
          //当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void sendSearchClassRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
//                    Map<String, String> map = new HashMap<>();
//                    map.put("user_name", user_name.getText().toString());
//                    map.put("id_no", id_no.getText().toString());
//                    OkHttp okhttp = new OkHttp();
//                    String result = okhttp.post("http://98.142.138.123:5000/ViewEnrolledClasses", map);
                    jsonReader reader = new jsonReader();
                    List<Map<String,Object>> listItem = reader.recvSearchClass("test");
                    fillList(listItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void fillList(final List<Map<String,Object>> listItem){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                SimpleAdapter adapter=new SimpleAdapter(SearchClass.this,listItem,R.layout.class_item,new String[]{"name","classId"},new int[]{R.id.class_name,R.id.class_id});
                mListView.setAdapter(adapter);//设置ListView显示的内容
            }
        });
    }
}

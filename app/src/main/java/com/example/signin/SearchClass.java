package com.example.signin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import com.example.signin.utility.classesInfo;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

public class SearchClass extends AppCompatActivity {
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_class);
        SearchView mSearchView = findViewById(R.id.search_class_view);
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
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getallclass", map);
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(pos)
                    chromToast.showToast(SearchClass.this, response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(SearchClass.this, response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void fillList(final List<classInfo> classes){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                classAdapter adapter = new classAdapter(SearchClass.this, R.layout.class_item, classes);
                mListView.setAdapter(adapter);//设置ListView显示的内容
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void  onItemClick(AdapterView<?>parent, View view, int position, long id)//获取学生点击了的课程传递参数
                    {
                        classInfo class_=classes.get(position);
                        showdialog(class_.getClassId(), class_.getName());
                    }
                });
            }
        });
    }
    public void showdialog(final String classID, final String className){
         new QMUIDialog.MessageDialogBuilder(SearchClass.this).setTitle(className).setMessage("确定要加入该课程吗？").addAction("取消", new QMUIDialogAction.ActionListener() {
             @Override
             public void onClick(QMUIDialog dialog, int index) {
                 dialog.dismiss();
             }
         }).addAction("确定", new QMUIDialogAction.ActionListener() {
             @Override
             public void onClick(QMUIDialog dialog, int index) {
                 sendJoinClassRequest(classID);
                 dialog.dismiss();
             }
         }).show();
    }//显示对话框

    private void sendJoinClassRequest(final String classID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    map.put("classID", classID);
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/joinclass", map);
                    if(result.equals(""))
                        showResponse("网络连接异常", false);
                    else{
                        if(jsonReader.recvStatus(result).equals("200"))
                            showResponse("加入成功",true);
                        else
                            showResponse(jsonReader.recvMsg(result), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

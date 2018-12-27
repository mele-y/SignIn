package com.example.signin;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import com.example.signin.utility.studentInfo;

public class teaMemberFra extends Fragment {
    private List<memberClass> data = new ArrayList<>();
    private static String classID = "", className = "";
    QMUIGroupListView  member_list;
    public teaMemberFra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tea_Enter_main a = (tea_Enter_main)getActivity();
        classID = a.getClassId();
        className = a.getName();
        if(!studentInfo.getClassID().equals(classID))
            sendGetAllStudentRequest();
        data = studentInfo.getStu();
        View view=inflater.inflate(R.layout.fragment_stu_class_member, container, false);
        member_list=view.findViewById(R.id.member_list);//获取列表
        member_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("成员人数："+data.size());//新建section，设置标题
        for(int i=0;i<data.size();++i){
            QMUICommonListItemView listItemName = member_list.createItemView(data.get(i).getStu_name());//新建列表项，设置标题
            listItemName.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
            listItemName.setDetailText(data.get(i).getStu_id());//显示学号
            listItemName.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
            listItemName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
            listItemName.setTag(i);
            section.addItemView(listItemName,mOnClickListenerGroup);
        }
        section.addTo(member_list);//将section加入列表

        return view;

    }

    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {//点击事件,查看学生出勤情况
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            Intent intent=new Intent(getActivity(),memberInfo.class);
            sendGetSingleAttendanceRequest(data.get((int)viewList.getTag()).getStu_id());
            intent.putExtra("name", className);
            intent.putExtra("classId",classID);//传递课程参数
            intent.putExtra("stu_name",data.get((int)viewList.getTag()).getStu_name());//传递学生信息
            intent.putExtra("stu_id",data.get((int)viewList.getTag()).getStu_id());
            startActivity(intent);
        }

    };

    private void sendGetSingleAttendanceRequest(final String sID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("ident", userInfo.getIdent());
                    map.put("classID", classID);
                    map.put("ID", userInfo.getID());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader reader = new jsonReader();
                    reader.recvGetSingleAttendance(result, classID, sID);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendGetAllStudentRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getallstudent", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        jsonReader reader = new jsonReader();
                        reader.recvGetAllStudent(result, classID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(getActivity(), response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(getActivity(), response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

}

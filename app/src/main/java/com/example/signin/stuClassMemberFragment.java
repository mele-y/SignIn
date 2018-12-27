package com.example.signin;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.studentInfo;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class stuClassMemberFragment extends Fragment {
    private List<memberClass> data = new ArrayList<>();
    private String classID = "";
    QMUIGroupListView  member_list;
    public stuClassMemberFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        studentEnterClass a = (studentEnterClass) getActivity();
        classID = a.getClassId();
        if(!studentInfo.getClassID().equals(classID))
            sendGetAllStudentRequest();
        else
            data = studentInfo.getStu();
        // Inflate the layout for this fragment
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
            section.addItemView(listItemName,null);
        }
        section.addTo(member_list);//将section加入列表

        return view;
        //使用了腾讯的QMUI，一些数据操作可能要在fragment的其他生命周期中实现
        //数据加载至列表中也可通过适配器实现
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
                        jsonReader.recvGetAllStudent(result, classID);
                        data = studentInfo.getStu();
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

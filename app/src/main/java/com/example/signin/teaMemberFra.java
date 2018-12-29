package com.example.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private String classID = "";
    QMUIGroupListView  member_list;
    public teaMemberFra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            tea_Enter_main a = (tea_Enter_main)getActivity();
            classID = a.getClassId();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        if(!studentInfo.getClassID().equals(classID))
            sendGetAllStudentRequest();
        View view=inflater.inflate(R.layout.fragment_stu_class_member, container, false);
        member_list=view.findViewById(R.id.member_list);//获取列表
        member_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        data = studentInfo.getStu();
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
            int i = (int)viewList.getTag();
            sendGetSingleAttendanceRequest(data.get(i).getStu_id());
            Intent intent = new Intent(getActivity(), memberInfo.class);
            intent.putExtra("classId", classID);//传递课程参数
            intent.putExtra("stu_name", data.get(i).getStu_name());//传递学生信息
            intent.putExtra("stu_id", data.get(i).getStu_id());
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
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    jsonReader.recvGetSingleAttendance(result, classID, sID);
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
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getallstudent", map);
                    if(result.equals("")){
                        showResponse("网络连接异常");
                    }
                    else{
                        String recvMessage = jsonReader.recvGetAllStudent(result, classID);
                        if(!recvMessage.equals("200")){
                            showResponse(jsonReader.recvMsg(result));
                            data = studentInfo.getStu();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response){
        try{
            (getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chromToast.showToast(getActivity(), response, true, 0xAAFF6100, 0xFFFFFFFF);
                }
            });
        }catch(NullPointerException e){
            e.printStackTrace();
        }
    }

}

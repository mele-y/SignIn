package com.example.signin;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.signin.utility.OkHttp;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.userInfo;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.signin.utility.allNoticeInfo;
import com.example.signin.utility.allMessageInfo;

public class teaNoticeFra extends Fragment {
    QMUIGroupListView message_list, notice_list;
    private List<messageInfo> messages = new ArrayList<>();
    private List<noticeInfo> notices = new ArrayList<>();
    private String classID = "";

    public teaNoticeFra() {
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

        if(!allMessageInfo.getClassID().equals(classID))
            sendGetAllMessageRequest();

        if(!allNoticeInfo.getClassID().equals(classID))
            sendGetAllNoticeRequest();

        View view = inflater.inflate(R.layout.tea_notice_frag, container, false);
        TabHost tabHost = view.findViewById(android.R.id.tabhost);//获取选项卡实例
        tabHost.setup();
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("留言").setContent(R.id.right));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("公告").setContent(R.id.left)); //设置选项卡显示的布局

        message_list = view.findViewById(R.id.message_list);//获取列表
        message_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        messages = allMessageInfo.getMessages();
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext()).setTitle("留言："+messages.size()+"条");//新建section，设置标题
        for (int i = 0; i < messages.size(); ++i) {
            QMUICommonListItemView msg = message_list.createItemView(messages.get(i).getStu_name());
            msg.setDetailText(messages.get(i).getContent());
            msg.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
            msg.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
            if(messages.get(i).getSender().equals("student")){
                msg.setTag(i);
                TextView tx = new TextView(getContext());
                tx.setText(messages.get(i).getType().equals("1")?"请假":"留言");
                msg.addAccessoryCustomView(tx);
                section.addItemView(msg, mOnClickListenerGroup);
            }
            else{
                section.addItemView(msg, null);
            }
        }
        section.addTo(message_list);//将section加入列表

        notice_list = view.findViewById(R.id.notice_list);//获取列表
        notice_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        notices = allNoticeInfo.getNotices();
        QMUIGroupListView.Section section1 = QMUIGroupListView.newSection(getContext()).setTitle("公告："+notices.size()+"条");//新建section，设置标题
        for (int i = 0; i < notices.size(); ++i) {
            QMUICommonListItemView msg = notice_list.createItemView(notices.get(i).getTime());
            msg.setDetailText(notices.get(i).getContent());
            msg.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
            msg.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
            section1.addItemView(msg, null);
        }
        section1.addTo(notice_list);//将section加入列表

        return view;
    }

    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {//点击事件,查看学生出勤情况
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            int idx = (int)viewList.getTag();
            Intent intent = new Intent(getActivity(), message_detail.class);
            intent.putExtra("classID", classID);
            intent.putExtra("stuID", messages.get(idx).getStu_id());
            intent.putExtra("stu_name", messages.get(idx).getStu_name());
            intent.putExtra("type", messages.get(idx).getType());
            intent.putExtra("content", messages.get(idx).getContent());
            startActivityForResult(intent, 233);
        }

    };

    private void sendGetAllNoticeRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getbulletin", map);
                    if(result.equals("")){
                        showResponse("网络连接异常");
                    }
                    else{
                        String recvMessage = jsonReader.recvGetAllNotice(result, classID);
                        if(!recvMessage.equals("200")){
                            showResponse(jsonReader.recvMsg(result));
                            notices = allNoticeInfo.getNotices();
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
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    private void sendGetAllMessageRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    String result = OkHttp.postFormWithToken("http://98.142.138.123:12345/api/getmessage", map);
                    if(result.equals(""))
                        showResponse("网络连接异常");
                    else{
                        String recvMessage = jsonReader.recvGetAllMessage(result, classID);
                        if(!recvMessage.equals("200")){
                            showResponse(jsonReader.recvMsg(result));
                            messages = allMessageInfo.getMessages();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        //刷新页面
    }
}

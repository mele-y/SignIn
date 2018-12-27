package com.example.signin;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

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
    QMUIGroupListView message_list,notice_list;
    private List<messageInfo> messages = new ArrayList<>();
    private List<noticeInfo> notices = new ArrayList<>();
    private String classID = "";
    public teaNoticeFra() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tea_Enter_main a = (tea_Enter_main)getActivity();
        classID = a.getClassId();

        if(!allMessageInfo.getClassID().equals(classID))
            sendGetAllMessageRequest();
        messages = allMessageInfo.getMessages();

        if(!allNoticeInfo.getClassID().equals(classID))
            sendGetAllNoticeRequest();
        notices = allNoticeInfo.getNotices();

        View view=inflater.inflate(R.layout.tea_notice_frag, container, false);
        TabHost tabHost=(TabHost)view.findViewById(android.R.id.tabhost);//获取选项卡实例
        tabHost.setup();
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("消息").setContent(R.id.right));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("公告").setContent(R.id.left)); //设置选项卡显示的布局

        message_list=view.findViewById(R.id.message_list);//获取列表
        message_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("消息:"+messages.size()+"条");//新建section，设置标题
        for (int i = 0; i < messages.size(); ++i) {
            if(messages.get(i).getSender().equals("student")){
                QMUICommonListItemView msg = message_list.createItemView(messages.get(i).getStu_name()+"的"+(messages.get(i).getType().equals("1")?"请假":"留言"));
                msg.setDetailText(messages.get(i).getContent());
                msg.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
                msg.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
                msg.setTag(i);
                section.addItemView(msg, mOnClickListenerGroup);
            }
            else{
                QMUICommonListItemView msg = message_list.createItemView(messages.get(i).getStu_name());
                msg.setDetailText(messages.get(i).getContent());
                msg.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
                msg.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
                section.addItemView(msg, null);
            }
        }
        section.addTo(message_list);//将section加入列表

        notice_list=view.findViewById(R.id.notice_list);//获取列表
        notice_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线
        QMUIGroupListView.Section section1=QMUIGroupListView.newSection(getContext()).setTitle("公告:"+notices.size()+"条");//新建section，设置标题
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
            Intent intent = new Intent(getActivity(),message_detail.class);
            intent.putExtra("classID",classID);
            intent.putExtra("stuID",messages.get((int)viewList.getTag()).getStu_id());
            intent.putExtra("stu_name",messages.get((int)viewList.getTag()).getStu_name());
            intent.putExtra("type",messages.get((int)viewList.getTag()).getType());
            intent.putExtra("content",messages.get((int)viewList.getTag()).getContent());
            startActivity(intent);

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
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getbulletin", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        jsonReader.recvGetAllNotice(result, classID);
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

    private void sendGetAllMessageRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", "2333333333");
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getmessage", map);
                    if(result.equals(""))
                        showResponse("网络连接异常", false);
                    else{
                        jsonReader.recvGetAllMessage(result, classID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

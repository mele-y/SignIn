package com.example.signin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.view.Menu;
import android.view.MenuItem;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class stuMessageFragment extends Fragment {    QMUIGroupListView message_list,notice_list;
    private messageInfo[] data={
            new messageInfo("姓名1","周五交作业吗?","留言"),
            new messageInfo("姓名2","明天可以请假吗","请假")
    };//学生对应留言
    private noticeInfo[] data1={new noticeInfo("主题1","明天考试"),
            new noticeInfo("主题2","明天不上课")
    };// 老师对应公告
    public stuMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tea_notice_frag, container, false);
        TabHost tabHost=(TabHost)view.findViewById(android.R.id.tabhost);//获取选项卡实例
        tabHost.setup();
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("学生留言").setContent(R.id.right));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("教师消息").setContent(R.id.left)); //设置选项卡显示的布局

        message_list=view.findViewById(R.id.message_list);//获取列表
        message_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName1 = message_list.createItemView(data[0].getType());//新建列表项，设置标题
        listItemName1.setDetailText(data[0].getContext());
        listItemName1.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        listItemName1.setTag(0);

        QMUICommonListItemView listItemName2 = message_list.createItemView(data[1].getType());//新建列表项，设置标题
        listItemName2.setDetailText(data[1].getContext());//设置副标题
        listItemName2.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
        listItemName2.setTag(1);

        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("学生留言:"+data.length+"条");//新建section，设置标题
        section.addItemView(listItemName1,null);
        section.addItemView(listItemName2,null);//不设置点击
        section.addTo(message_list);//将section加入列表

        notice_list=view.findViewById(R.id.notice_list);//获取列表
        notice_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName3 = notice_list.createItemView(data1[0].getTitle());//新建列表项，设置标题
        listItemName3.setDetailText(data1[0].getContext());//显示学号
        listItemName3.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName3.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);

        QMUICommonListItemView listItemName4 = notice_list.createItemView(data1[1].getTitle());//新建列表项，设置标题
        listItemName4.setDetailText(data1[1].getContext());//设置副标题
        listItemName4.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName4.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件


        QMUIGroupListView.Section section1=QMUIGroupListView.newSection(getContext()).setTitle("教师消息:"+data1.length+"条");//新建section，设置标题
        section1.addItemView(listItemName3,null);
        section1.addItemView(listItemName4,null);//公告不设置点击事件
        section1.addTo(notice_list);//将section加入列表


        return view;
    }




    }





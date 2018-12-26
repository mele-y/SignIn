package com.example.signin;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class teaNoticeFra extends Fragment {
    QMUIGroupListView message_list,notice_list;
    private messageInfo[] data={
            new messageInfo("姓名1","周五交作业吗?"),
            new messageInfo("姓名2","周四交报告吗?")
    };//学生对应留言
    private noticeInfo[] data1={new noticeInfo("主题1","明天考试"),
            new noticeInfo("主题2","明天不上课")
    };// 老师对应公告
    public teaNoticeFra() {
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
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("留言").setContent(R.id.right));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("公告").setContent(R.id.left)); //设置选项卡显示的布局

        message_list=view.findViewById(R.id.message_list);//获取列表
        message_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName1 = message_list.createItemView(data[0].getHeadline());//新建列表项，设置标题
        //listItemName1.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName1.setDetailText(data[0].getContext());//显示学号
        listItemName1.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        listItemName1.setTag(0);

        QMUICommonListItemView listItemName2 = message_list.createItemView(data[1].getHeadline());//新建列表项，设置标题
        //listItemName2.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName2.setDetailText(data[1].getContext());//设置副标题
        listItemName2.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);//设置右侧控件
        listItemName2.setTag(1);

        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("留言:"+data.length+"条");//新建section，设置标题
        section.addItemView(listItemName1,null);
        section.addItemView(listItemName2,null);//把列表项加入section，第二个参数为点击监听器
        section.addTo(message_list);//将section加入列表

        notice_list=view.findViewById(R.id.notice_list);//获取列表
        notice_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName3 = notice_list.createItemView(data1[0].getTitle());//新建列表项，设置标题
        //listItemName1.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName3.setDetailText(data1[0].getContext());//显示学号
        listItemName3.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName3.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        listItemName3.setTag(4);

        QMUICommonListItemView listItemName4 = notice_list.createItemView(data1[1].getTitle());//新建列表项，设置标题
        listItemName4.setDetailText(data1[1].getContext());//设置副标题
        listItemName4.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName4.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件
        listItemName4.setTag(3);

        QMUIGroupListView.Section section1=QMUIGroupListView.newSection(getContext()).setTitle("公告:"+data1.length+"条");//新建section，设置标题
        section1.addItemView(listItemName3,null);
        section1.addItemView(listItemName4,null);//把列表项加入section，第二个参数为点击监听器
        section1.addTo(notice_list);//将section加入列表


        return view;
    }
    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {//点击事件,查看学生出勤情况
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            switch ((int)viewList.getTag()) {
                case 0:
                    break;
                case 1:
                    break;
            }
            //   Toast.makeText(getActivity(),"选项：" +  viewList.getTag()+ " 点击了",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(),message_detail.class);
            intent.putExtra("stu_name",data[(int)viewList.getTag()].getHeadline());//传递学生信息
            intent.putExtra("context",data[(int)viewList.getTag()].getContext());
            startActivity(intent);
        }

    };
}

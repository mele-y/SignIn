package com.example.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;




/**
 * A simple {@link Fragment} subclass.
 */
public class stuClassMemberFragment extends Fragment {

   QMUIGroupListView  member_list;
    public stuClassMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stu_class_member, container, false);
        member_list=view.findViewById(R.id.member_list);//获取列表
        member_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName1 = member_list.createItemView("sai");//新建列表项，设置标题
        listItemName1.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName1.setDetailText("叶剑波");//设置副标题
        listItemName1.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件自定义
        TextView tx=new TextView(getContext());
        tx.setText("916106840344");
        listItemName1.addAccessoryCustomView(tx);

        QMUICommonListItemView listItemName2 = member_list.createItemView("nick_name");//新建列表项，设置标题
        listItemName2.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName2.setDetailText("real_name");//设置副标题
        listItemName2.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);//设置右侧控件自定义
        TextView tx2=new TextView(getContext());
        tx2.setText("stu_id");
        listItemName2.addAccessoryCustomView(tx2);//自定义控件加入右侧

         QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("成员人数：2");//新建section，设置标题
                 section.addItemView(listItemName1,null);
                 section.addItemView(listItemName2,null);//把列表项加入section，第二个参数为点击监听器
                 section.addTo(member_list);//将section加入列表

        return view;
        //使用了腾讯的QMUI，一些数据操作可能要在fragment的其他生命周期中实现
    }

}

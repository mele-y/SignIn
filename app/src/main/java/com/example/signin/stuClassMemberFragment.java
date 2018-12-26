package com.example.signin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.signin.utility.studentInfo;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class stuClassMemberFragment extends Fragment {
    private List<memberClass> data = new ArrayList<>();
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
        data = studentInfo.getStu();
        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("成员人数："+data.size());//新建section，设置标题
        if(data.size() > 0){
            List<QMUICommonListItemView> lst = new ArrayList<>();
            for(int i=0;i<data.size();++i){
                QMUICommonListItemView listItemName = member_list.createItemView(data.get(i).getStu_name());//新建列表项，设置标题
                listItemName.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
                listItemName.setDetailText(data.get(i).getStu_id());//显示学号
                listItemName.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
                listItemName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
                listItemName.setTag(i);
                lst.add(listItemName);
                section.addItemView(lst.get(i),null);
            }
        }
        section.addTo(member_list);//将section加入列表

        return view;
        //使用了腾讯的QMUI，一些数据操作可能要在fragment的其他生命周期中实现
        //数据加载至列表中也可通过适配器实现
    }

}

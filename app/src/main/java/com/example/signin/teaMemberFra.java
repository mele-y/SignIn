package com.example.signin;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

public class teaMemberFra extends Fragment {
    QMUIGroupListView  member_list;
    public teaMemberFra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_stu_class_member, container, false);
        member_list=view.findViewById(R.id.member_list);//获取列表
        member_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName1 = member_list.createItemView("姓名");//新建列表项，设置标题
        listItemName1.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName1.setDetailText("学号001");//显示学号
        listItemName1.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);//设置右侧控件自定义
        TextView tx=new TextView(getContext());
        tx.setText("90%");//显示签到率
        listItemName1.addAccessoryCustomView(tx);
        listItemName1.setTag(1);

        QMUICommonListItemView listItemName2 = member_list.createItemView("姓名");//新建列表项，设置标题
        listItemName2.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName2.setDetailText("002");//设置副标题
        listItemName2.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);//设置右侧控件自定义
        TextView tx2=new TextView(getContext());
        tx2.setText("80%");
        listItemName2.addAccessoryCustomView(tx2);//自定义控件加入右侧
        listItemName2.setTag(2);

        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("成员人数：2");//新建section，设置标题
        section.addItemView(listItemName1,mOnClickListenerGroup);
        section.addItemView(listItemName2,mOnClickListenerGroup);//把列表项加入section，第二个参数为点击监听器
        section.addTo(member_list);//将section加入列表

        return view;

    }

    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {//点击事件,查看学生出勤情况
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            switch ((int)viewList.getTag()) {
                case 1:
                    break;
                case 2:
                    break;
            }//如果tag等于1,传递学号001给下一个界面显示签到情况
            Toast.makeText(getActivity(),"选项：" +  viewList.getTag()+ " 点击了",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(),memberInfo.class);
            tea_Enter_main activity=(tea_Enter_main)getActivity();//获取实例
            String name=activity.getName();
            String classId=activity.getClassId();
            intent.putExtra("name", name);
            intent.putExtra("classId",classId);
            startActivity(intent);
        }

    };

}

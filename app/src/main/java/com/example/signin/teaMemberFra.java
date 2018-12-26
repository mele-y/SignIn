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
    private memberClass[] data={ new memberClass("001","fongfong"),
            new memberClass("002","FANGFANG"),

    };//学生对应留言 老师对应公告 放在同一个显示栏
    QMUIGroupListView  member_list;
    private String stu_id,stu_name;
    public teaMemberFra() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_stu_class_member, container, false);
        member_list=view.findViewById(R.id.member_list);//获取列表
        member_list.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);//设置分割线

        QMUICommonListItemView listItemName1 = member_list.createItemView(data[0].getStu_name());//新建列表项，设置标题
        listItemName1.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName1.setDetailText(data[0].getStu_id());//显示学号
        listItemName1.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        listItemName1.setTag(0);

        QMUICommonListItemView listItemName2 = member_list.createItemView(data[1].getStu_name());//新建列表项，设置标题
        listItemName2.setImageDrawable(getResources().getDrawable(R.drawable.user_32px));//设置图标
        listItemName2.setDetailText(data[1].getStu_id());//设置副标题
        listItemName2.setOrientation(QMUICommonListItemView.VERTICAL);//垂直
        listItemName2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);//设置右侧控件
        listItemName2.setTag(1);

        QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("成员人数："+data.length);//新建section，设置标题
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
                case 0:
                    break;
                case 1:
                    break;
            }
         //   Toast.makeText(getActivity(),"选项：" +  viewList.getTag()+ " 点击了",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getActivity(),memberInfo.class);
            tea_Enter_main activity=(tea_Enter_main)getActivity();//获取实例
            String name=activity.getName();
            String classId=activity.getClassId();
            intent.putExtra("name", name);
            intent.putExtra("classId",classId);//传递课程参数
            intent.putExtra("stu_name",data[(int)viewList.getTag()].getStu_name());//传递学生信息
            intent.putExtra("stu_id",data[(int)viewList.getTag()].getStu_id());
            startActivity(intent);
        }

    };

}

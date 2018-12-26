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

import com.example.signin.utility.OkHttp;
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
                section.addItemView(lst.get(i),mOnClickListenerGroup);
            }
        }
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
            intent.putExtra("stu_name",data.get((int)viewList.getTag()).getStu_name());//传递学生信息
            intent.putExtra("stu_id",data.get((int)viewList.getTag()).getStu_id());
            startActivity(intent);
        }

    };

}

package com.example.signin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Calendar;

public class mainSignFra extends Fragment {
    QMUIGroupListView qmuiGroupListView;

    public mainSignFra() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view=inflater.inflate(R.layout.fragment_main_sign, container, false);
            TextView time_text=view.findViewById(R.id.time_);
            Calendar calendar= Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH)+1;
            int day=calendar.get(Calendar.DAY_OF_MONTH);
            time_text.setText(year+"年"+month+"月"+day+"日");
            qmuiGroupListView=view.findViewById(R.id.sign_in_list);
            qmuiGroupListView.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);
//显示每个日期的签到率
            QMUICommonListItemView msg1=qmuiGroupListView.createItemView("2018-12-20");
            msg1.setDetailText("星期四");
            msg1.setOrientation(QMUICommonListItemView.VERTICAL);
            msg1.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            TextView tx=new TextView(getContext());
            tx.setText("80%");
            msg1.addAccessoryCustomView(tx);

            QMUICommonListItemView msg2=qmuiGroupListView.createItemView("2018-12-25");
            msg2.setDetailText("星期二");
            msg2.setOrientation(QMUICommonListItemView.VERTICAL);
            msg2.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
            TextView tx2=new TextView(getContext());
            tx2.setText("80%");
            msg2.addAccessoryCustomView(tx2);

            String title2="80%";//逻辑层获取信息修改title2
            QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("总签到率                                                                                                  "+ title2);
            section.addItemView(msg1,null);
            section.addItemView(msg2,null);
            section.addTo(qmuiGroupListView);

            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            QMUIRoundButton btn=(QMUIRoundButton)getActivity().findViewById(R.id.sign_in_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(),"签到开始/结束",Toast.LENGTH_LONG).show();//点击单数为开始签到，双视为结束签到
                }
            });
        }
    }


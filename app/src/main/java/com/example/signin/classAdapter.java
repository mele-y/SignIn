package com.example.signin;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class classAdapter extends ArrayAdapter {
    private int resourced;
    public classAdapter( Context context, int textViewResourceId,List<classInfo>objects) {
        super(context, textViewResourceId, objects);
        resourced = textViewResourceId;
    }
        @Override
                public View getView(int position,View convertView,ViewGroup parent)
        {
         classInfo class_= (classInfo) getItem(position);//获取当前class实例
            View view =LayoutInflater.from(getContext()).inflate(resourced,parent,false);
            TextView id=(TextView)view.findViewById(R.id.class_id);
            id.setText(class_.getClassId());
            TextView name=(TextView)view.findViewById(R.id.class_name);
            name.setText(class_.getName());

          return view;
        }




    }


package com.example.signin;


import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class stuSignInFragment extends Fragment {


    public stuSignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stu_sign_in, container, false);
        TextView time_text=view.findViewById(R.id.time_text);
        Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        time_text.setText(year+"年"+month+"月"+day+"日");
        return view;
    }

}

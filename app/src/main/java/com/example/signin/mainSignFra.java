package com.example.signin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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
import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.example.signin.utility.mapUtils;

import com.example.signin.utility.userInfo;
import com.example.signin.utility.signinInfo;
import com.example.signin.utility.OkHttp;
import com.example.signin.utility.jsonReader;
import com.example.signin.utility.chromToast;
import com.example.signin.utility.allAttendanceInfo;

public class mainSignFra extends Fragment {
    private static final int CODE =233;
    private static boolean flag = false;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private static String longitude = "", latitude = "", time = "", classID = "";
    private static String[] permissions = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_PHONE_STATE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static List<signinInfo> data = new ArrayList<>();
    QMUIGroupListView qmuiGroupListView;

    public mainSignFra() {
        // Required empty public constructor
    }

    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getActivity());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(1000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(500);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {
                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    longitude = String.valueOf(location.getLongitude());
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    latitude = String.valueOf(location.getLatitude());
                    sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                    sb.append("提供者      : " + location.getProvider() + "\n");
                    sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省          : " + location.getProvince() + "\n");
                    sb.append("市          : " + location.getCity() + "\n");
                    sb.append("城市编码    : " + location.getCityCode() + "\n");
                    sb.append("区          : " + location.getDistrict() + "\n");
                    sb.append("区域 码     : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点      : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间    : " + mapUtils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                    time = mapUtils.formatUTC(location.getTime(), "yyyy-MM-dd");
                    showResponse("定位成功", true);
                    stopLocation();
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启":"关闭").append("\n");
                sb.append("* GPS状态：").append(getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("****************").append("\n");
                //定位之后的回调时间
                sb.append("回调时间: " + mapUtils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                System.out.println(result);
            } else {
                System.out.println("定位失败，loc is null");
            }
        }
    };

    private String getGPSStatusString(int statusCode){
        String str = "";
        switch (statusCode){
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                break;
        }
        return str;
    }

    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocation();
        destroyLocation();
    }

    public void getPermissions(int requestCode, final String[] permissions) {
//        ArrayList<String> list = new ArrayList<>();
//        for(int i=0;i<permissions.length;++i){
//            int status = ActivityCompat.checkSelfPermission(this.getActivity(), permissions[i]);
//            if(status == -1)
//                list.add(permissions[i]);
//        }
//        if(list.size() > 0)
//            ActivityCompat.requestPermissions(this.getActivity(), (String[])list.toArray(), requestCode);
        ActivityCompat.requestPermissions(this.getActivity(), permissions, requestCode);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            getPermissions(CODE, permissions);
            initLocation();
            startLocation();
            tea_Enter_main a = (tea_Enter_main)getActivity();
            classID = a.getClassId();
            if(!allAttendanceInfo.getClassID().equals(classID))
                sendGetAllAttendanceRequest();
            data = allAttendanceInfo.getAttendanceRate();
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
            String title2 = allAttendanceInfo.getRate();
            QMUIGroupListView.Section section=QMUIGroupListView.newSection(getContext()).setTitle("总签到率                                                                                                  "+ title2);
            for(int i=0;i<data.size();++i){
                QMUICommonListItemView msg = qmuiGroupListView.createItemView(data.get(i).getTime());
                msg.setDetailText(data.get(i).getWeek());
                msg.setOrientation(QMUICommonListItemView.VERTICAL);
                msg.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
                TextView tx=new TextView(getContext());
                tx.setText(data.get(i).getAttendance());
                msg.addAccessoryCustomView(tx);
                msg.setTag(data.get(i).getTime());
                section.addItemView(msg,mOnClickListenerGroup);
            }
            section.addTo(qmuiGroupListView);//将section加入列表

            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            QMUIRoundButton btn=(QMUIRoundButton)getActivity().findViewById(R.id.sign_in_btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!flag){
                        sendStartSignInRequest();
                    }
                    else{
                        sendEndSignInRequest();
                    }
                }
            });
        }

    //统一处理选项点击事件
    private View.OnClickListener mOnClickListenerGroup = new View.OnClickListener() {//点击事件,进入查看历史签到记录
        @Override
        public void onClick(View view) {
            QMUICommonListItemView viewList = (QMUICommonListItemView) view;
            String pickTime = (String)viewList.getTag();
            showResponse(pickTime, true);
            Intent intent=new Intent(getActivity(),absence_info.class);
            tea_Enter_main activity=(tea_Enter_main)getActivity();//获取实例
            String name=activity.getName();
            String classId=activity.getClassId();
            intent.putExtra("name", name);
            intent.putExtra("classId",classId);//传递课程参数
            intent.putExtra("time",pickTime);
            startActivity(intent);
        }

    };

    private void sendStartSignInRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    map.put("time", time);
                    map.put("location", longitude + " " + latitude);
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/startSignIn", map);
                    jsonReader reader = new jsonReader();
                    if(reader.recvStatus(result).equals("200")){
                        showResponse("签到开始", true);
                        flag = !flag;
                    }
                    else
                        showResponse(reader.recvMsg(result), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendEndSignInRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/closeSignIn", map);
                    System.out.println(result);
                    jsonReader reader = new jsonReader();
                    if(reader.recvMsg(result).equals("Signin successfully closed!")) {
                        showResponse("签到结束", true);
                        flag = !flag;
                    }
                    else
                        showResponse(reader.recvMsg(result), false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String response, final boolean pos){
        //Android不允许在子线程中进行UI操作，需通过此方法将线程切换到主线程，再更新UI元素
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                if(pos)
                    chromToast.showToast(getActivity(), response, false, 0xAA00FF7F, 0xFFFFFFFF);
                else
                    chromToast.showToast(getActivity(), response, true, 0xAAFF6100, 0xFFFFFFFF);
            }
        });
    }

    private void sendGetAllAttendanceRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> map = new HashMap<>();
                    map.put("phonenum", userInfo.getPhonenum());
                    map.put("classID", classID);
                    map.put("ident", userInfo.getIdent());
                    map.put("ID", userInfo.getID());
                    OkHttp okhttp = new OkHttp();
                    String result = okhttp.postFormWithToken("http://98.142.138.123:12345/api/getSignIn", map);
                    if(result.equals("")){
                        showResponse("网络连接异常", false);
                    }
                    else{
                        jsonReader reader = new jsonReader();
                        reader.recvGetAllAttendance(result, classID);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    }



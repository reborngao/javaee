package com.visabao.machine.util.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.internal.StringMap;
import com.visabao.machine.global.CacheUtil;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.SharedUtil;

import java.util.List;
import java.util.Map;


/**
 *
 * @Description 是一个检测网络状态改变的，需要配置
 * <uses-permission
 *              android:name="android.permission.CHANGE_NETWORK_STATE" />
 *              <uses-permission
 *              android:name="android.permission.CHANGE_WIFI_STATE" />
 *              <uses-permission
 *              android:name="android.permission.ACCESS_NETWORK_STATE" />
 *              <uses-permission
 *              android:name="android.permission.ACCESS_WIFI_STATE" />
 */


public class NetworkStateReceiver extends BroadcastReceiver {


    public final static String DBTO_ANDROID_NET_CHANGE_ACTION = "dbto.android.net.conn.CONNECTIVITY_CHANGE";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            ConnectivityManager   mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
            if(netInfo != null && netInfo.isAvailable()) {
                /////////////网络连接
                String name = netInfo.getTypeName();
                if(netInfo.getType()==ConnectivityManager.TYPE_WIFI){
                    Log.e("WiFi网络");
                }else if(netInfo.getType()==ConnectivityManager.TYPE_ETHERNET){
                    Log.e("有线网络");
                }else if(netInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                    Log.e("3g网络");
                }
                try {
                    String order= CacheUtil.read(context);
                    List<Map<String ,Object>> mapList= CacheUtil.getList(order);
                    if (mapList.size()>0){
                        requestData(context,mapList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("网络断开");
            }
        }
    }

    private  void requestData(final  Context context,final  List<Map<String ,Object>> mapList){
        final     Map<String ,Object>  map=  mapList.get(0);
        HttpClientHelper.getInstance(context).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()){
                        mapList.remove(map);
                        CacheUtil.saveOrderList(context, mapList);
                        requestData(context,mapList);
                }
            }
            @Override
            public void onFailure() {

            }
        });
    }

}

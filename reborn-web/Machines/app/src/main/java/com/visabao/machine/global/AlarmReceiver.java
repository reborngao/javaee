package com.visabao.machine.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;

import java.util.List;
import java.util.Map;


public class AlarmReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String order=CacheUtil.read(context);
              List<Map<String ,Object>> mapList= CacheUtil.getList(order);
            if (mapList!=null&&mapList.size()>0){
               requestData(context,mapList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private  void  updateLog(Context context){
        //HttpClientHelper.getInstance(context).post(URL.PINGAN);
    }


    private  void requestData(final  Context context,final List<Map<String ,Object>> mapList){
        final     Map<String ,Object>  map=  mapList.get(0);
        HttpClientHelper.getInstance(context).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()){
                        mapList.remove(map);
                        CacheUtil.saveOrderList(context, mapList);
                        if(mapList.size()>0){
                            requestData(context,mapList);
                    }
                }

            }
            @Override
            public void onFailure() {

            }
        });
    }
}

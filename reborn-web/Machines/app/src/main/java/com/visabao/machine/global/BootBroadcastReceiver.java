package com.visabao.machine.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.visabao.machine.LoginAcitivity;


public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
        Intent i=new Intent(context, LoginAcitivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

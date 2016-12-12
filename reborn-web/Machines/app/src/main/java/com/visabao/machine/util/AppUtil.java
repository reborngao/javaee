package com.visabao.machine.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

/**
 * 系统属性工具类
 * 权限 android.permission.READ_PHONE_STATE
 */
public class AppUtil {

    /***
     * 　　检查app 是不否安装
     * @param packageName
     * @return
     */
    public static boolean isInstallByread(String packageName)
    {
        return new File("/data/data/" + packageName).exists();
    }

    /***
     *   设备型号
     * @return
     */
    public static String getAndroidModel() {
       return android.os.Build.MODEL;

    }

    public static String getAndroidSDKVersion() {
        return android.os.Build.VERSION.RELEASE;

    }


    /**
     * 获得cpu名称
     *
     * @return
     */
    public static String getCpuName() {
        FileReader fr = null;
        BufferedReader br = null;
        String text;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return null;
    }



    /**
     * 获取应用程序的版本号
     *
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
            String versionName = info.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
    /**
     * 获取包信息.
     *
     * @param context  上下文
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return info;
    }

    /****
     *  获取系统UUID
     * @param context
     * @return
     */
    public static  String  getUUID(Context context){
        TelephonyManager telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice=telephonyManager.getDeviceId();
        String tmSerial =telephonyManager.getSimSerialNumber();
        String androidId=""+android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid=new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        Log.d("uuid:"+uniqueId);
        return uniqueId;
    }
}

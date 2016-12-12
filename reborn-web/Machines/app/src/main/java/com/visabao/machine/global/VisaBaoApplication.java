package com.visabao.machine.global;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.visabao.machine.util.AppUtil;
import com.visabao.machine.util.DateUitl;
import com.visabao.machine.util.FileUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.SharedUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Stack;


public class    VisaBaoApplication  extends Application {



    MyUncaughtExceptionHandler  mDefaultHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initImageLoader(getApplicationContext());
        initAlarm();
        Log.setDebug(false);
    }
   private static final int INTERVAL = 1000*60*60*2;// 24h
    private  void  initAlarm(){
       AlarmManager  alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0, intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),INTERVAL,pendingIntent);
    }

    private void init(){
        mDefaultHandler=new MyUncaughtExceptionHandler();
      //  Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }



    private  class MyUncaughtExceptionHandler  implements Thread.UncaughtExceptionHandler {

        private void sendReport(final Throwable ex) {
            try {
                File temp = File.createTempFile(DateUitl.format(System.currentTimeMillis(), DateUitl.DATE_TIME_MILLISECOND_PATTERN), FileUtil.END_TEMP);
                PrintWriter writer = new PrintWriter(temp);
//		                TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                writer.write(String.format("错误发生时间%s：\r\n名称：%s;机型：%s;版本号：%s;核心版本号：%s；厂商：%s;产品：%s;APP版本号：%s\r\n",
                        DateUitl.formartCurrentDateTime(),
                        Build.DEVICE,
                        Build.MODEL,
                        Build.VERSION.SDK,
                        Build.VERSION.RELEASE,
                        Build.MANUFACTURER,
                        Build.PRODUCT,
                        AppUtil.getVersionName(getApplicationContext())));
                ex.printStackTrace(writer);
                writer.flush();
                writer.close();
                Log.e(temp.getPath());
           //     SharedPreferencesProxy.getInstance(getApplicationContext()).putString(SharedPreferencesConstants.ERROR_REPORT, temp.getPath()).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            Log.e("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            int version = Build.VERSION.SDK_INT;
            if (!handleException(ex) && mDefaultHandler != null) {

                Log.e("????????????????????????????????????");
                Log.e("????????????????????????????????????");
                Log.e("????????????????????????????????????");
                Log.e("????????????????????????????????????");
                Log.e(ex.toString());
                // 如果用户没有处理则让系统默认的异常处理器来处理
                mDefaultHandler.uncaughtException(thread, ex);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("捕获全局异常信息:" + e.getMessage());
                }
                Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.e("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if (Log.isDebug()) sendReport(ex);
                TAAppManager.getAppManager().appExit(VisaBaoApplication.this);
                version = Build.VERSION.SDK_INT;
                ActivityManager activityMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                if (version <= 7) {
                    try {
                        activityMgr.restartPackage(getPackageName());
                    } catch (Exception e) {
                    }
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                System.exit(0);
            }

        }
      /* @param
        * @param ex
        * @return true:如果处理了该异常信息;否则返回false.
        */
        private boolean handleException(final Throwable ex) {
            if (ex == null) {
                return false;
            }
            // 使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(VisaBaoApplication.this, "sorry，程序出现异常，即将退出应用！",
                            Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            return true;
        }
    }
        private void initImageLoader(Context context) {
            com.nostra13.universalimageloader.utils.L.disableLogging();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                    context).threadPoolSize(5)
                    .threadPriority(Thread.NORM_PRIORITY - 1)
                    .denyCacheImageMultipleSizesInMemory()
                    .discCache(new UnlimitedDiscCache(new File(FileUtil.getImageDownloadDir(context))))//自定义缓存路径
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                            // 不加载相同的图片
                    .denyCacheImageMultipleSizesInMemory().writeDebugLogs() // Remove
                    .build();
            ImageLoader.getInstance().init(config);
        }

}

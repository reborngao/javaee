package com.visabao.machine.autoUpdate;

import java.io.File;
import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.visabao.machine.R;
import com.visabao.machine.util.Log;


/**
 * 在通知栏中显示进度条，当点击或者清除通知栏时会分别发送以下广播
 * ACTION_NOTIFICATION_CLICKED
 * ACTION_NOTIFICATION_CLEARED
 * 另外可以通过setAutoInstall方法来设置当下载完成后是否进行安装，默认不进行安装。
 * 下载完成时若不自动进行安装，则会发送
 * ACTION_DOWNLOAD_COMPLETED广播，可以监听该广播进行下载完成后的处理

 */
final public class NotificationDownLoader implements DownLoadAsynTask.OnDownLoadListener{
    public static final String ACTION_NOTIFICATION_CLEARED = "cn.com.do1.component.NOTIFICATION_CLEARED";
    public static final String ACTION_NOTIFICATION_CLICKED = "cn.com.do1.component.NOTIFICATION_CLICKED";
    public static final String ACTION_DOWNLOAD_COMPLETED= "cn.com.do1.component.DOWNLOAD_COMPLETED";
    
    public static final String FILE = "file";
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    private static final Random random = new Random(System.currentTimeMillis());
	private NotificationManager nm;
	private Notification notification;
	private RemoteViews mRemoteViews;
	private int notificationId = 0;
	private Context mContext;
	private boolean mAutoInstall = false;//下载完成后是否自动安装
	
	public NotificationDownLoader(Context context){
		mContext = context;
		nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.update_notification);
		notification.contentView = mRemoteViews;
		notificationId = random.nextInt();
	}
	
	/**
	 * 设置下载完成后是否自动安装
	 * @param autoInstall  true:自动安装，false不自动安装，此时会发送广播ACTION_AUTO_INSTALL
	 */
	public void setAutoInstall(boolean autoInstall) {
		this.mAutoInstall = autoInstall;
	}


	@Override
	public void onBegin() {
		//一定要设置icon，否则不显示出来
		notification.icon = android.R.drawable.stat_sys_download;
		notification.tickerText = mContext.getString(R.string.app_name) + "更新";
		notification.when = System.currentTimeMillis();
		notification.defaults = Notification.DEFAULT_SOUND;
		
		  //单击时发送广播
		Intent clickIntent = new Intent(
				ACTION_NOTIFICATION_CLICKED);
		clickIntent.putExtra(NOTIFICATION_ID, notificationId);
		PendingIntent clickPendingIntent = PendingIntent.getBroadcast(
				mContext, 0, clickIntent, 0);
		notification.contentIntent = clickPendingIntent;

		 //清除时也发送广播
		Intent clearIntent = new Intent(
				ACTION_NOTIFICATION_CLEARED);
		clearIntent.putExtra(NOTIFICATION_ID, notificationId);
		PendingIntent clearPendingIntent = PendingIntent.getBroadcast(
				mContext, 0, clearIntent, 0);
		notification.deleteIntent = clearPendingIntent;

		// 将下载任务添加到任务栏中
		nm.notify(notificationId, notification);

	}

	@Override
	public void onUpdateProgress(int progress) {
		notification.defaults = Notification.DEFAULT_LIGHTS;
		Log.d("已下载" + progress + "%");
		// 更新状态栏上的下载进度信息
		mRemoteViews.setTextViewText(R.id.update_id_tvProcess, "已下载"
				+ progress + "%");
		mRemoteViews.setProgressBar(R.id.update_id_progressbar, 100,
				progress, false);
		notification.contentView = mRemoteViews;
		nm.notify(notificationId, notification);
	}

	@Override
	public void onEnd(boolean result, File apkFile) {
		Log.d("result:"+ result + ",apkFile:"+apkFile);
		notification.defaults = Notification.DEFAULT_SOUND;
		nm.cancel(notificationId);
		if (result){
			if(mAutoInstall){//自动安装
				installApk(apkFile, mContext);
			}else{//发广播
				Intent intent = new Intent(ACTION_DOWNLOAD_COMPLETED);
				intent.putExtra(FILE, apkFile);
				mContext.sendBroadcast(intent);
			}
		}else{
			Toast.makeText(mContext, "获取apk包异常", Toast.LENGTH_LONG).show();
		}
	}

	// 安装下载后的apk文件
	public static void installApk(File file, Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
     /*   Constants.sharedProxy.putString("newVersion","false");
        Constants.sharedProxy.commit();*/
	}
}

package com.visabao.machine.autoUpdate;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import  com.visabao.machine.autoUpdate.DownLoadAsynTask;
import com.visabao.machine.util.FileUtil;

import java.io.File;


/**
 * 启动下载服务


 */
final public class DownLoadService extends Service{
	 public static final String AUTO_INSTALL= "auto_install";
	 public static final String URL= "url";
	@Override
	public IBinder onBind(Intent intent) { 
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent == null){
			return START_NOT_STICKY;
		}
		//SDCardUtil.getPackInSdcard();
		String url = intent.getStringExtra("url");
		boolean autoInstall = intent.getBooleanExtra(AUTO_INSTALL, false);
		if (TextUtils.isEmpty(url)){
			return START_NOT_STICKY;
		}
		executeDownLoadAsynTask(url,autoInstall);
		return START_NOT_STICKY;//不重启
	}
	
	private void executeDownLoadAsynTask(String url,boolean autoInstall){
		NotificationDownLoader downLoadListener = new NotificationDownLoader(this);
		downLoadListener.setAutoInstall(autoInstall);
//		File file = new File(AQUtility.getTempDir(), getPackageName()+".apk");
		File file = new File(FileUtil.getFileDownloadDir(getApplicationContext()), getPackageName()+".apk");
		DownLoadAsynTask downLoadAsynTask = new DownLoadAsynTask(new DownLoadListenerWrapper(downLoadListener),file);
		downLoadAsynTask.execute(url);
	}
	
	
	//包装类，实现下载完后关掉服务
	private class DownLoadListenerWrapper implements DownLoadAsynTask.OnDownLoadListener{
		private DownLoadAsynTask.OnDownLoadListener downLoadListener;
		public DownLoadListenerWrapper(DownLoadAsynTask.OnDownLoadListener downLoadListener){
			this.downLoadListener = downLoadListener;
		}
		@Override
		public void onBegin() {
			downLoadListener.onBegin();
		}

		@Override
		public void onUpdateProgress(int progress) {
			downLoadListener.onUpdateProgress(progress);
		}

		@Override
		public void onEnd(boolean result, File apkFile) {
			downLoadListener.onEnd(result,apkFile);
			stopSelf();
		}
		
	}
}

package com.visabao.machine.autoUpdate;

import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.visabao.machine.LoginAcitivity;
import com.visabao.machine.R;
import com.visabao.machine.global.TAAppManager;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.SharedUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;

/**
 * 自动更新检测入口类
 */
public class VersionUpdateCusService {
	private Activity act;
	private boolean fileCache;
	private int progress;
	private long expire = 12 * 60 * 1000;
	public String updateUrl;//
	private boolean force;
	private static VersionUpdateCusService sInstance;
	private boolean autoInstall = true;//
	CustomerDialog dialog;
	public VersionInfo info = new VersionInfo();

	/**
	 * Instantiates a new MarketService.
	 * 
	 * @param act
	 *            Current activity.
	 */

	private VersionUpdateCusService(Activity act) {
		this.act = act;
	}

	public static VersionUpdateCusService getInstance(Activity act) {
//		if (sInstance == null) {
			sInstance = new VersionUpdateCusService(act);
//		}
		return sInstance;
	}

	/**
	 * 
	 * @param autoInstall
	 *            url
	 * @return self
	 */
	public VersionUpdateCusService autoInstall(boolean autoInstall) {
		this.autoInstall = autoInstall;
		return this;
	}

	/**
	 * 
	 * @return self
	 */
	public VersionUpdateCusService force(boolean force) {
		this.force = force;
		return this;
	}

	/**
	 * Display a progress view during version check.
	 * 
	 * @param id
	 *            view id
	 * @return self
	 */
	public VersionUpdateCusService progress(int id) {
		this.progress = id;
		return this;
	}
	/**
	 * Set ajax request to be file cached.
	 * 
	 * @param cache
	 *            the cache
	 * @return self
	 */
	public VersionUpdateCusService fileCache(boolean cache) {
		this.fileCache = cache;
		return this;
	}

	/**
	 * The time duration which last version check expires. Default is 10 hours.
	 * 
	 * @param expire
	 *            expire time in milliseconds
	 * @return self
	 */

	public VersionUpdateCusService expire(long expire) {
		this.expire = expire;
		return this;
	}

	private static ApplicationInfo ai;

	private ApplicationInfo getApplicationInfo() {

		if (ai == null) {
			ai = act.getApplicationInfo();
		}

		return ai;
	}

	private static PackageInfo pi;

	private PackageInfo getPackageInfo() {

		if (pi == null) {
			try {
				pi = act.getPackageManager().getPackageInfo(getAppId(), 0);

			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
		}
		return pi;
	}

	private String getAppId() {

		return getApplicationInfo().packageName;
	}

	private Drawable getAppIcon() {
		Drawable d = getApplicationInfo().loadIcon(act.getPackageManager());
		return d;
	}

	public String getVersion() {
		return getPackageInfo().versionName;
	}

	public int getVersionCode() {
		return getPackageInfo().versionCode;
	}


	/**
	 * @param desc
	 */
	public void showUpdateDialog(String desc) {
		if (!isActive())
			return;

		if (TextUtils.isEmpty(desc)) {
			desc = "";
		}
		Context context = act;
		//dialog = new CustomerDialog(context, Html.fromHtml(patchBody(desc)).toString(),info.app_version);
		final AlertDialog dialog = new AlertDialog.Builder(context).
				setTitle("版本更新")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (act instanceof LoginAcitivity)
							((LoginAcitivity) act).startAnim();
						dialog.dismiss();
//				updateUrl = "http://172.20.15.29:8080/testweb/upload/thzhd.apk";
//				updateUrl = "http://www.ith71.cn/phone/ith71_android.apk";

						//updateUrl = act.getString(R.string.server_url) + updateUrl;
						Log.e("下载安装包的URL：" + updateUrl);
						handlePositiveClick(updateUrl);
					}
				})
				// .setNeutralButton(skip, mOnRequestListener)
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(act instanceof LoginAcitivity){
							if("0".equals(info.getApp_code())){
								ToastUtil.showToast(act, "对不起，您当前版本过低，请先升级");
								dialog.dismiss();
								versionHandler.sendEmptyMessageDelayed(0, 3000);
							}else{
								((LoginAcitivity)act).startAnim();
								dialog.dismiss();
							}
						}else{
							dialog.dismiss();
						}
					}
				}).create();
		dialog.setMessage(Html.fromHtml(patchBody(desc)));
		/*dialog.setBtnCancelListener(queryListener);
		dialog.setBtnOkListener(queryListener);
		dialog.setCanceledOnTouchOutside(false);*/
		dialog.show();
	}
	
	Handler versionHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 0){
				TAAppManager.getAppManager().finishAllActivity();
			}
		};
	};






/*	private OnClickListener queryListener = new OnClickListener() {
		@Override
		public void onClick(View paramView) {
			switch (paramView.getId()) {
			case R.id.later_btn:
				if(act instanceof LoginAcitivity){
					if("0".equals(info.app_code)){
						ToastUtil.showToast(act, "对不起，您当前版本过低，请先升级");
						dialog.dismiss();
						versionHandler.sendEmptyMessageDelayed(0, 3000);
					}else{
						((LoginAcitivity)act).startAnim();
						dialog.dismiss();
					}
				}else{
					dialog.dismiss();
				}
				break;
			case R.id.now_btn:
				if(act instanceof LoginAcitivity)
					((LoginAcitivity)act).startAnim();
				dialog.dismiss();
//				updateUrl = "http://172.20.15.29:8080/testweb/upload/thzhd.apk";
//				updateUrl = "http://www.ith71.cn/phone/ith71_android.apk";
				
				//updateUrl = act.getString(R.string.server_url) + updateUrl;
				Log.e("下载安装包的URL：" + updateUrl);
				handlePositiveClick(updateUrl);
				break;
				
			case R.id.close_dialog:
				if(act instanceof LoginAcitivity){
					if("0".equals(info.getApp_code())){
						ToastUtil.showToast(act, "对不起，您当前版本过低，请先升级");
						dialog.dismiss();
						versionHandler.sendEmptyMessageDelayed(0, 3000);
					}else{
						((LoginAcitivity)act).startAnim();
						dialog.dismiss();
					}
				}else{
					dialog.dismiss();
				}
				break;
			default:
				break;
			}
		}
	};*/
	
	private static String patchBody(String body) {
		return "<small>" + body + "</small>";
	}

	private boolean isActive() {
		if (act.isFinishing())
			return false;
		return true;
	}

	/**
	 */
	protected void noNeedUpdate() {
		if (force) {
			if(!(act instanceof LoginAcitivity)){
				Toast.makeText(act, "您的客户端是最新版本，不需要更新", Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * @param url
	 */
	protected void handlePositiveClick(String url) {
		Intent service = new Intent(act, DownLoadService.class);
		service.putExtra(DownLoadService.URL, updateUrl);
		service.putExtra(DownLoadService.AUTO_INSTALL, autoInstall);
		act.startService(service);
	}



	/**
	 * 匹配版本
	 * @param curV	当前版本
	 * @param newV  最新版本
	 * @return
	 */
	public  boolean compareVersion(String curV,String newV){
		String[] curVs = curV.split("[.]");
		String[] netVs = newV.split("[.]");
		int cSize = curVs.length;
		int nSize = netVs.length;
		
		int size = (cSize > nSize)?nSize:cSize;
		
		for(int i=0;i<size;i++){
			if(!StringUtil.isNumber(curVs[i]) || !StringUtil.isNumber(netVs[i])){
				return false;
			}
			int c = Integer.valueOf(curVs[i]);
			int n = Integer.valueOf(netVs[i]);
			System.out.println(c+":"+n);
			if(c < n)
				return true;
			else if( c > n)
				return false;
		}
		if(nSize > cSize)
        {
            SharedUtil.putBoolean(act, "newVersion", true);
            return true;
        }
		else{
			SharedUtil.putBoolean(act, "newVersion", false);
			return false;
        }
	}

	public void checkVersion(VersionInfo info) {
	   this.info=info;
		if(compareVersion(getVersion(), info.getApp_version())){
			updateUrl = info.getApp_url();
			showUpdateDialog(info.getApp_content());
		}else{
			noNeedUpdate();
		}
	}

	public static class VersionInfo {
		private  String app_type;
		private  String app_url;
		private  String app_version;
		private  String app_date;
		private  String app_code;
		private  String app_update;
		private  String app_content;

		public String getApp_content() {
			return app_content;
		}

		public void setApp_content(String app_content) {
			this.app_content = app_content;
		}

		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public String getApp_url() {
			return app_url;
		}

		public void setApp_url(String app_url) {
			this.app_url = app_url;
		}

		public String getApp_version() {
			return app_version;
		}

		public void setApp_version(String app_version) {
			this.app_version = app_version;
		}

		public String getApp_date() {
			return app_date;
		}

		public void setApp_date(String app_date) {
			this.app_date = app_date;
		}

		public String getApp_code() {
			return app_code;
		}

		public void setApp_code(String app_code) {
			this.app_code = app_code;
		}

		public String getApp_update() {
			return app_update;
		}

		public void setApp_update(String app_update) {
			this.app_update = app_update;
		}
	}
}

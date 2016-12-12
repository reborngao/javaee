package com.visabao.machine.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;


/***
 *   事件监听 工具类
 * @author liugaoqiao
 *
 */
public class ListenerUtil {
	
	/***
	 *  绑定单击事件
	 * @param activity
	 * @param listener
	 * @param resIds
	 */
	public static  void bindClick(Activity activity,OnClickListener listener,int ...resIds){
		for(int resId:  resIds){
			activity.findViewById(resId).setOnClickListener(listener);
		}
	}
	/***
	 *  绑定单击事件
	 * @param activity
	 * @param listener
	 * @param resIds
	 */
	public static  void bindClick(View view,OnClickListener listener,int ...resIds){
		for(int resId:  resIds){
			view.findViewById(resId).setOnClickListener(listener);
		}
	}
	/***
	 * 绑定单击事件
	 * @param listener
	 * @param views
	 */
	public  static  void bindClick(OnClickListener listener,View ...views){
		for(View v:views){
			 v.setOnClickListener(listener);
		}
	}
	
	/***
	 * 绑定文本改变事件
	 * @param listener
	 * @param views
	 */
	public  static  void bindWatcher(TextWatcher  watcher,EditText  ...views){
		for(EditText  editText:views){
			editText.addTextChangedListener(watcher);
		}
	}
}

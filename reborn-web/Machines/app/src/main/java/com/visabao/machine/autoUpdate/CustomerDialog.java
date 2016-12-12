package com.visabao.machine.autoUpdate;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.visabao.machine.R;

/**
 * 自动更新弹出的dialog
 */
public class CustomerDialog extends Dialog{

	private Button mBtnCancel;
	private Button mBtnOk;
	private ImageView mClose;
	private TextView mMsgText;

	public CustomerDialog(Context context, String message,String version) {
		super(context, R.style.myDialogTheme);
		View view = View.inflate(context, R.layout.version_update_dialog, null);
		setContentView(view);
		mMsgText = (TextView) findViewById(R.id.title);
		mMsgText.setText("有新版本" + version + "\r\n更新内容:\r\n" + message);
		mBtnCancel = (Button) findViewById(R.id.later_btn);
		mBtnOk = (Button) findViewById(R.id.now_btn);
		mClose = (ImageView) findViewById(R.id.close_dialog);
		mClose.setClickable(true);
	}

	
	public CustomerDialog setMessage(String msg){
		mMsgText.setText(msg);
		return this;
	}
	/**
	 * 确定事件
	 * 
	 * @param btn_ok_listener
	 */
	public CustomerDialog setBtnOkListener(
			View.OnClickListener btn_ok_listener) {
		mBtnOk.setOnClickListener(btn_ok_listener);
		return this;
	}

	/**
	 * 取消事件
	 * 
	 * @param btn_cancel_listener
	 */
	public CustomerDialog setBtnCancelListener(
			View.OnClickListener btn_cancel_listener) {
		mBtnCancel.setOnClickListener(btn_cancel_listener);
		mClose.setOnClickListener(btn_cancel_listener);
		return this;
	}
	
	public CustomerDialog setBtnOkIcon(int resid){
		mBtnOk.setBackgroundResource(resid);
		return this;
	}
	
	public CustomerDialog setBtnCancelIcon(int resid){
		mBtnCancel.setBackgroundResource(resid);
		return this;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//屏蔽返回键
            return true;
        }
		return super.onKeyDown(keyCode, event);
	}
	public void hideBtnCancel(){
		mBtnCancel.setVisibility(View.GONE);
	}
	
}

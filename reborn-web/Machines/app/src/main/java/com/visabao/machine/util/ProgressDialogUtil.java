package com.visabao.machine.util;

import android.app.*;
import android.content.Context;
import android.widget.TextView;

import com.visabao.machine.R;

/**
 * Dialog 工具类
 */
public class ProgressDialogUtil {
        public static  Dialog progressDialog=null;
    public  static  void  show(Context context){
        if (progressDialog!=null&&progressDialog.isShowing()){
            if(context instanceof  Activity){
                if (!((Activity)context).isFinishing()){
                    progressDialog.dismiss();
                }
            }
        }
        progressDialog=new Dialog(context,R.style.myDialogTheme);
        progressDialog.setContentView(R.layout.progress);
        progressDialog.setCancelable(false);
        TextView tvHint= (TextView) progressDialog.findViewById(R.id.progress_tv_message);
        tvHint.setText(context.getResources().getString(R.string.progress_hint));
        progressDialog.show();
    }
    public  static  void dismiss(){
        if (progressDialog!=null&&progressDialog.isShowing())
        {
            progressDialog.dismiss();

        }
    }
}

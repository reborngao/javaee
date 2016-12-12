package com.visabao.machine.util;

import android.app.Dialog;
import android.content.Context;


public class ProgressDialog  extends Dialog  {
    public ProgressDialog(Context context) {
        super(context);
        init();
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected ProgressDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private  void  init(){

    }
}

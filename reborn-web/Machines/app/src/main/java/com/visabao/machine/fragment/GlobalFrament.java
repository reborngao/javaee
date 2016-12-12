package com.visabao.machine.fragment;

import android.os.Bundle;
import android.view.View;

import com.visabao.machine.R;
import com.visabao.machine.fragment.message.WriteMessageFrament;
import com.visabao.machine.global.BaseFrament;

/**
 *  全球签.
 */
public class GlobalFrament  extends BaseFrament {
    @Override
    public int getContentLayoutId() {
        return R.layout.global;
    }
     public  static  final  String TYPE="TYPE";
    public  static  final  String TYPE_VALUE="1";
    @Override
    public void initData(View view) {
        view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteMessageFrament messageFrament=new WriteMessageFrament();
                 Bundle bundle= getArguments();
                bundle.putString(TYPE,TYPE_VALUE);
                messageFrament.setArguments(bundle);
                startFrament( GlobalFrament.this, messageFrament, true);
                getFragmentManager().beginTransaction().remove(GlobalFrament.this).commit();
            }
        });
    }
}

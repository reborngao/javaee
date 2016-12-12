package com.visabao.machine.fragment;

import android.app.Fragment;
import android.view.View;

import com.visabao.machine.R;
import com.visabao.machine.fragment.message.VisaMessageFrament;
import com.visabao.machine.global.BaseFrament;

/**
 * 温馨提示
 */
public class ReminderFragment   extends BaseFrament {
    @Override
    public int getContentLayoutId() {
        return R.layout.reminder;
    }

    @Override
    public void initData(View view) {
       view.findViewById(R.id.btn_sign).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               VisaMessageFrament fragment = new VisaMessageFrament();
               fragment.setArguments(getArguments());
               startFrament(ReminderFragment.this, fragment, true, VisaMessageFrament.class.getName());
               removeFrament(ReminderFragment.this);
           }

       });
    }
}

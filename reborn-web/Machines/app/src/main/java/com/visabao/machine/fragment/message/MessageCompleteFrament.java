package com.visabao.machine.fragment.message;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.visabao.machine.R;
import com.visabao.machine.fragment.GlobalFrament;

/**
 *完成订单
 */
public class MessageCompleteFrament  extends  MessageBaseFrament{
    @Override
    public Fragment getTabNextIndex() {
        return null;
    }

    @Override
    public int getTabUpIndex() {
        return R.id.message_rb_conmit;
    }

    @Override
    public void initData(View view) {
        super.initData(view);
         ImageView imageView= (ImageView) view.findViewById(R.id.mc_iv_status);
        if (getArguments().getBoolean(MessageConmitFrament.SAVEFLAG)){
                    imageView.setImageResource(R.mipmap.success);
        }
        else{
            imageView.setImageResource(R.mipmap.fail);
        }
        String   type = getArguments().getString(GlobalFrament.TYPE);
        if (GlobalFrament.TYPE_VALUE.equals(type)) {
            TextView tvTitle = (TextView) view.findViewById(R.id.visa_wirte_tv_title);
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText("全球签");
        }
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.message_complete;
    }
}

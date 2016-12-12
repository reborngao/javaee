package com.visabao.machine.fragment.refund;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.visabao.machine.R;
import com.visabao.machine.fragment.ReminderFragment;
import com.visabao.machine.global.BaseFrament;

/**
 * 退款状态
 */
public class RefundStatusFrament extends BaseFrament {
    @Override
    public int getContentLayoutId() {
        return R.layout.refund_status;
    }

    @Override
    public void initData(View view) {

        Fragment fragment=  getFragmentManager().findFragmentByTag(RefundInputFrament.class.getName());
        if (fragment!=null){
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }

        Fragment selectRefund=  getFragmentManager().findFragmentByTag(SelectRefundPersonFrament.class.getName());
        if (selectRefund!=null){
            getFragmentManager().beginTransaction().remove(selectRefund).commit();
        }
        Button nextBtn= (Button) view.findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fra=  getFragmentManager().findFragmentByTag(RefundStatusFrament.class.getName());
                if (fra!=null){
                    removeFramentLit(RefundStatusFrament.this);
                    getFragmentManager().beginTransaction().remove(fra).commit();

                }
                Fragment fraInput=  getFragmentManager().findFragmentByTag(RefundInputFrament.class.getName());
                if (fraInput!=null){
                    removeFramentLit(fraInput);
                    getFragmentManager().beginTransaction().remove(fra).commit();

                }
                RefundInputFrament refundInputFrament = new RefundInputFrament();
                startFrament(RefundStatusFrament.this, refundInputFrament, true,RefundInputFrament.class.getName());
            }
        });
    }
}

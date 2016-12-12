package com.visabao.machine.fragment.refund;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.visabao.machine.R;
import com.visabao.machine.global.BaseFrament;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;

/**
 * 申请退款
 */
public class RefundInputFrament extends BaseFrament {
    private EditText etRecommendedText;

    @Override
    public int getContentLayoutId() {
        return R.layout.refund_input;
    }

    public static final String RECOMMENDED_BANK = "RECOMMENDED_BANK";


    @Override
    public void initData(View view) {
        Button btnUp = (Button) view.findViewById(R.id.btn_up);
        btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                removeFramentLit(RefundInputFrament.this);
            }
        });
        Button btnNext = (Button) view.findViewById(R.id.btn_next);
         etRecommendedText = (EditText) view.findViewById(R.id.wm_et_recommended_bank);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isEmpty(etRecommendedText.getText().toString())){
                    SelectRefundPersonFrament selectRefundPersonFrament = new SelectRefundPersonFrament();
                    Bundle bundle = new Bundle();
                    bundle.putString(RECOMMENDED_BANK, etRecommendedText.getText().toString());
                    selectRefundPersonFrament.setArguments(bundle);
                    startFrament(RefundInputFrament.this, selectRefundPersonFrament, true,SelectRefundPersonFrament.class.getName());
                }
                else {
                    ToastUtil.showToast(getActivity(),"请输入交易流水号");
                }

            }
        });
    }
}

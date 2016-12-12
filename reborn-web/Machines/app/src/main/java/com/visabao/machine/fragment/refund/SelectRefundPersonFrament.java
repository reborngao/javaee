package com.visabao.machine.fragment.refund;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ActionProvider;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.visabao.machine.R;
import com.visabao.machine.entity.Pay;
import com.visabao.machine.entity.Refund;
import com.visabao.machine.entity.RefundPerson;
import com.visabao.machine.global.BaseFrament;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.global.BaseItemAdpter.BaseItemViewListenter;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.DateUitl;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialog;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SkuUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;
import com.visabao.machine.widget.MeasureListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 选择退款签证人
 */
public class SelectRefundPersonFrament extends BaseFrament implements BaseItemViewListenter {
    private List<RefundPerson> dataList;
    private BaseItemAdpter baseItemAdpter;
    private List<String> soList;

    private  Button btnNext;

    @Override
    public int getContentLayoutId() {
        return R.layout.select_refund_person;
    }
    private  View llNotData;
    @Override
    public void initData(View view) {
        ListView listView = (ListView) view.findViewById(R.id.srp_listview);
        dataList = new ArrayList<RefundPerson>();
         baseItemAdpter = new BaseItemAdpter(getActivity(), R.layout.item_select_refund, dataList, this,R.id.item_tv_name,R.id.item_rb_check,R.id.measurelistview);
        listView.setAdapter(baseItemAdpter);
        btnNext= (Button) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefundStatusFrament refundStatusFrament = new RefundStatusFrament();
                startFrament(SelectRefundPersonFrament.this, refundStatusFrament, true);
            }
        });
        llNotData=view.findViewById(R.id.ll_not_data);
        requestData();
         Button nextBtn= (Button) view.findViewById(R.id.btn_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    soList= new ArrayList<String>();
                    double sunPrice=0.00;
                    for (RefundPerson rp:dataList){
                        if (rp.isCheck()){
                            for (Refund r:rp.getRefundList()){
                                soList.add(r.getSo_code());
                            }
                        }
                    }
                    if (soList.size()>0){
                        requestRefund();
                    }
                   else {
                        ToastUtil.showToast(getActivity(),"请选择退款订单");
                    }
                }
                catch (Exception e){
                    ProgressDialogUtil.dismiss();
                    ToastUtil.showToast(getActivity(), "请安装中国银联客户端");
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 11) {
                if (resultCode == Activity.RESULT_CANCELED) {
                    String reason = data.getStringExtra("reason");
                    ProgressDialogUtil.dismiss();
                    ToastUtil.showToast(getActivity(), reason);
                } else if (resultCode == Activity.RESULT_OK) {
                    String info = data.getStringExtra("info");
                    Map<String ,Map<String,Object>> infoMap=     JsonUtil.fromJson(info, new TypeToken<Map<String, Map<String, Object>>>() {
                    });
                    Log.e(info + "");
                    Map<String ,Object> map=  infoMap.get("info");
                    Pay pay = new Pay();
                    pay.setAmount(map.get("TRANS_AMT").toString());
                    pay.setTrans_date(map.get("TRANS_DATE").toString());
                    pay.setReference_no(map.get("REF_NO").toString());
                    pay.setTrace_no(map.get("TRACE_NO").toString());
                    pay.setCard_no(map.get("CARD_NO").toString());
                    pay.setType("1");
                    pay.setIssue("");
                    pay.setBatch_no(map.get("BATCH_NO").toString());
                    pay.setTerminal_id(map.get("TERM_ID").toString());
                    pay.setMerchant_id(map.get("MERCH_NAME").toString());
                    pay.setMerchant_name(map.get("MERCH_NAME").toString());
                    // pay.setTrans_date(DateUitl.format(System.currentTimeMillis(), DateUitl.DATE_TIME_MILLISECOND_PATTERN));
                    requestRefund();

                }
            }
        }
    }



    public void requestRefund() {
        if (soList.size()>0){
            ProgressDialogUtil.show(getActivity());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(Constant.TYPE, Constant.Type.REFUND_RESULT);
            map.put("so_code",soList);
            HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
                @Override
                public void onSuccess(String data) {
                    ProgressDialogUtil.dismiss();
                    MessageResults messageResults=  JsonUtil.fromJson(data, MessageResults.class);
                    if (messageResults.isSuccess()){
                            removeFrament(SelectRefundPersonFrament.this);
                            startFrament(SelectRefundPersonFrament.this,new RefundStatusFrament(),true,RefundStatusFrament.class.getName());
                    }
                }

                @Override
                public void onFailure() {
                    ProgressDialogUtil.dismiss();
                }
            }, true);
        }
        else{
            ToastUtil.showToast(getActivity(), "请选择退款签证人");
        }
    }




    public void requestData() {
        ProgressDialogUtil.show(getActivity());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.REFUND);
        map.put("trace_no", getArguments().getString(RefundInputFrament.RECOMMENDED_BANK));
        map.put("so", new ArrayList());
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults<List<Refund>> messageResults = JsonUtil.fromJson(data, new TypeToken<MessageResults<List<Refund>>>() {
                });
                boolean showBtn = true;
                if (messageResults.isSuccess()) {
                    List<Refund> refundList = messageResults.getData();
                    Log.e("================>" + refundList.size());
                    for (Refund refund : refundList) {  //判断是否全部退款  如果全部退完false  没有为true
                        showBtn = refund.isRefund();
                        if (showBtn) {
                            break;
                        }
                    }
                    for (Refund refund : refundList) {  //处理服务器数据并显示
                        if ( refund.getMobile()!=null&& refund.getVisaUser()!=null){
                            boolean isExeis = true;
                            for (int i = 0; i < dataList.size(); i++) {
                                if (refund.getVisaUser().equals(dataList.get(i).getName()) && refund.getMobile().equals(dataList.get(i).getMobile())) {
                                    isExeis = false;
                                    dataList.get(i).getRefundList().add(refund);
                                }
                            }
                            if (isExeis) {
                                RefundPerson refundPerson = new RefundPerson();
                                List<Refund> refunds = new ArrayList<Refund>();
                                refunds.add(refund);
                                refundPerson.setVisaUserId(refund.getVisaUserId());
                                refundPerson.setName(refund.getVisaUser());
                                refundPerson.setMobile(refund.getMobile());
                                refundPerson.setRefundList(refunds);
                                dataList.add(refundPerson);
                            }
                        }

                    }
                    Log.e("================>" + dataList.size());
                    Log.e("==============>" + JsonUtil.toJson(dataList));
                    baseItemAdpter.notifyDataSetChanged();

                } else {

                }

                if (dataList.size()==0){
                    btnNext.setVisibility(View.GONE);
                    llNotData.setVisibility(View.VISIBLE);
                }
                else if (!showBtn&&dataList.size()>0){
                    btnNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
                btnNext.setVisibility(View.GONE);
            }
        }, true);
    }


    private void noRefund(){

    }

    @Override
    public void handerItemView(SparseArray<View> viewHolder, final int position, View convertView, ViewGroup parent) {
       TextView tvName= (TextView) viewHolder.get(R.id.item_tv_name);
        final RefundPerson  refund=  dataList.get(position);
        tvName.setText(refund.getName());
        final  RadioButton radioButton= (RadioButton) viewHolder.get(R.id.item_rb_check);
        MeasureListView measureListView= (MeasureListView) viewHolder.get(R.id.measurelistview);
         BaseItemViewListenter  baseItemViewListenter=new BaseItemViewListenter() {
             @Override
             public void handerItemView(SparseArray<View> viewHolder, int position, View convertView, ViewGroup parent) {
                  Refund ref=  refund.getRefundList().get(position);
                    TextView tvTitle= (TextView) viewHolder.get(R.id.item_tv_title);
                 tvTitle.setText(SkuUtil.getPruductName(ref.getSpu()));
                 TextView tvPrice= (TextView) viewHolder.get(R.id.item_tv_price);
                 tvPrice.setText("￥" + SkuUtil.getPruductPrice(ref.getSku()));
                 if (!ref.isRefund()){
                     tvTitle.setTextColor(Color.parseColor("#808080"));
                     tvPrice.setTextColor(Color.parseColor("#808080"));
                 }
                 else {
                     tvTitle.setTextColor(Color.BLACK);
                     tvPrice.setTextColor(Color.BLACK);
                 }
             }
         };
     final    boolean  isRefund=refund.isRefund();
      final BaseItemAdpter baseItemAdpter=  new BaseItemAdpter(getActivity(),R.layout.item_select_refund_chlid,refund.getRefundList(),baseItemViewListenter
                ,R.id.item_tv_title,R.id.item_tv_price);
        measureListView.setAdapter(baseItemAdpter);
        radioButton.setChecked(dataList.get(position).isCheck());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRefund) {
                    radioButton.setChecked(!radioButton.isChecked());
                    dataList.get(position).setCheck(radioButton.isChecked());
                    baseItemAdpter.notifyDataSetChanged();
                }

            }
        });
      if (!isRefund){
            tvName.setTextColor(Color.parseColor("#808080"));
            radioButton.setVisibility(View.GONE);
        }
        else {
            tvName.setTextColor(Color.BLACK);
        }

    }
}

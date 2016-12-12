package com.visabao.machine.fragment.message;

import android.app.Fragment;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.visabao.machine.R;
import com.visabao.machine.entity.CsServices;
import com.visabao.machine.entity.InServices;
import com.visabao.machine.entity.OrderService;
import com.visabao.machine.entity.Person;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SkuUtil;
import com.visabao.machine.util.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 *  选择服务
 */

public class MessageSelectFrament  extends  MessageBaseFrament  implements RadioGroup.OnCheckedChangeListener{
    private List<Person> personList;

    @Override
    public Fragment getTabNextIndex() {
        MessageConmitFrament messageConmitFrament=    new MessageConmitFrament();
        List<InServices> newInServices=new ArrayList<InServices>();
    for (InServices ins :inServices){   //  保险服务
        if (ins.isCheck()){
            newInServices.add(ins);
        }
    }

        List<CsServices> newCsServices=new ArrayList<CsServices>();
        for (CsServices csSe :csServices){   //  保险服务
            if (csSe.isCheck()){
                newCsServices.add(csSe);
            }
        }
        Bundle  bundle= getArguments();
        bundle.putSerializable(CS_SERVICES_LIST, (Serializable) newCsServices);
        bundle.putSerializable(IN_SERVICES_LIST, (Serializable) newInServices);
        messageConmitFrament.setArguments(bundle);
        return messageConmitFrament ;
    }
    public  static  final  String  CS_SERVICES_LIST="CS_SERVICES_LIST";
    public  static  final  String  IN_SERVICES_LIST="IN_SERVICES_LIST";
    @Override
    public int getTabUpIndex() {
        return R.id.message_rb_add;
    }

    private  ListView listView;
   private RadioGroup radioGroup;
    @Override
    public void initData(View view) {
        super.initData(view);
        radioGroup= (RadioGroup) view.findViewById(R.id.ms_rg);
        radioGroup.setOnCheckedChangeListener(this);
        listView= (ListView) view.findViewById(R.id.select_listview);
        csServices=new ArrayList<CsServices>();
        inServices=new ArrayList<InServices>();
        setInServieAdpter();

       personList= (List<Person>) getArguments().get(MessageAddFrament.PERSON);
        requestData(R.id.ms_rb_insurance);
    }
    private  List<CsServices> csServices;
    private  List<InServices> inServices;
    private  void  setCsServieAdpter(){  //附加服务
        BaseItemAdpter baseItemAdpter=new BaseItemAdpter(getActivity(), R.layout.item_select
                , csServices, new BaseItemAdpter.BaseItemViewListenter() {
            @Override
            public void handerItemView(SparseArray<View> viewHolder,final int position, View convertView, ViewGroup parent) {
                CsServices  cs=csServices.get(position);
                TextView tvTitleView= (TextView) viewHolder.get(R.id.item_tv_title);
                String[]  iput=   cs.getSkukvdescription().split("\\|\\*\\|");
                if (iput.length>0){
                    tvTitleView.setText(SkuUtil.getPruductName(iput[0]));// 服务名称
                }
                TextView tvPrice= (TextView) viewHolder.get(R.id.item_tv_price);
                if (iput.length>1){
                    String [] sku= iput[1].split("-");
                    if (sku.length>7){
                        if(!StringUtil.isEmpty(sku[8])){
                            tvPrice.setText(StringUtil.formatTowDouble(sku[8]));
                        }
                    }
                }

                /*String[]  skukvdescription=   cs.getSkukvdescription().split("-");
                String title="";
                if (skukvdescription.length>25){
                    title+=skukvdescription[25];
                }
                tvTitleView.setText(title);//  签证类型
                TextView tvPrice= (TextView) viewHolder.get(R.id.item_tv_price);
                if (skukvdescription.length>46){
                    if(!StringUtil.isEmpty(skukvdescription[46])){
                        tvPrice.setText(StringUtil.formatTowDouble(skukvdescription[46]));
                    }

                }*/
                TextView  tvNumber= (TextView) viewHolder.get(R.id.item_tv_number);
                tvNumber.setText(personList.size()+"");
                final   RadioButton radioButton= (RadioButton) viewHolder.get(R.id.item_rb_check);
                radioButton.setChecked(cs.isCheck());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioButton.setChecked(!radioButton.isChecked());
                        csServices.get(position).setCheck(radioButton.isChecked());
                    }
                });
            }
        },R.id.item_btn_select,R.id.item_tv_title,R.id.item_tv_price,R.id.item_tv_number,R.id.item_rb_check);
        listView.setAdapter(baseItemAdpter);
    }
    private  void  setInServieAdpter(){   //保险服务
        BaseItemAdpter baseItemAdpter=new BaseItemAdpter(getActivity(), R.layout.item_select
                , inServices, new BaseItemAdpter.BaseItemViewListenter() {
            @Override
            public void handerItemView(SparseArray<View> viewHolder, final int position, View convertView, ViewGroup parent) {
                InServices  inSe=inServices.get(position);
                TextView tvTitleView= (TextView) viewHolder.get(R.id.item_tv_title);
                String [] iput= inSe.getSkukvdescription().split("\\|\\*\\|");

                if (iput.length>0){
                    String [] spu= iput[0].split("-");
                    if (spu.length>15){
                        tvTitleView.setText(spu[16]);// 服务名称
                    }
                }

                TextView tvPrice= (TextView) viewHolder.get(R.id.item_tv_price);
                if (iput.length>1){
                    String [] sku= iput[1].split("-");
                    if (sku.length>7){
                        if(!StringUtil.isEmpty(sku[8])){
                            tvPrice.setText(StringUtil.formatTowDouble(sku[8]));
                        }
                    }
                }







                TextView  tvNumber= (TextView) viewHolder.get(R.id.item_tv_number);
                tvNumber.setText(personList.size() + "");

                final   RadioButton radioButton= (RadioButton) viewHolder.get(R.id.item_rb_check);
                radioButton.setChecked(inSe.isCheck());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        radioButton.setChecked(!radioButton.isChecked());
                        inServices.get(position).setCheck(radioButton.isChecked());
                    }
                });

            }
        }, R.id.item_btn_select,R.id.item_tv_title,R.id.item_tv_price,R.id.item_tv_number,R.id.item_rb_check);
        listView.setAdapter(baseItemAdpter);
    }



    private  void  requestData(final  int id){
        ProgressDialogUtil.show(getActivity());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.GETSERVICE);
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                  ProgressDialogUtil.dismiss();
                MessageResults<OrderService> mapMessageResults = JsonUtil.fromJson(data, new TypeToken<MessageResults<OrderService>>(){});
                if (mapMessageResults.isSuccess()){
                    csServices=mapMessageResults.getData().getCsService();
                    inServices=  mapMessageResults.getData().getInService();
                    if (id==R.id.ms_rb_additional){ //附加服务
                        setCsServieAdpter();
                    }
                    else{

                        setInServieAdpter();

                    }
                }else{
                    Log.e(mapMessageResults.getMsg());
                }

            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
            }
        });

    }


    @Override
    public int getContentLayoutId() {
        return R.layout.message_select;
    }



    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
          if (checkedId==R.id.ms_rb_insurance){  // 保险服务
              setInServieAdpter();
          }
        if(checkedId==R.id.ms_rb_additional){ // 附加服务

            setCsServieAdpter();
        }

    }
}

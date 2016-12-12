package com.visabao.machine.fragment.message;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.visabao.machine.R;
import com.visabao.machine.entity.CsServices;
import com.visabao.machine.entity.InServices;
import com.visabao.machine.entity.Notes;
import com.visabao.machine.entity.Pay;
import com.visabao.machine.entity.Person;
import com.visabao.machine.entity.Sku;
import com.visabao.machine.entity.SoAdd;
import com.visabao.machine.entity.SoOrder;
import com.visabao.machine.entity.Visauser;
import com.visabao.machine.fragment.AreaVisaListFragment;
import com.visabao.machine.global.BaseItemAdpter;
import com.visabao.machine.global.CacheUtil;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.AppUtil;
import com.visabao.machine.util.DateUitl;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SharedUtil;
import com.visabao.machine.util.SkuUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 确定订单
 */
public class MessageConmitFrament extends MessageBaseFrament implements BaseItemAdpter.BaseItemViewListenter {
    private Bundle bundle;
    private boolean saveFlag = true;
    public static final String SAVEFLAG = "SAVEFLAG";
    private List<Person> persons;
    private List<Map<String, String>> data;
    private List<CsServices> csServicesList;
    private List<InServices> inServicesList;
    private List<Object> skus;
    private int number;

    @Override
    public Fragment getTabNextIndex() {
        MessageCompleteFrament messageCompleteFrament = new MessageCompleteFrament();
        Bundle bundle = new Bundle();
        bundle.putBoolean(SAVEFLAG, saveFlag);
        messageCompleteFrament.setArguments(bundle);
        return messageCompleteFrament;
    }

    @Override
    public int getTabUpIndex() {
        return R.id.message_rb_select;
    }

    public static final String ITEM_PRICE = "ITEM_PRICE";
    public static final String ITEM_TITLE = "ITEM_TITLE";
    public static final String ITEM_COUNT_PRICE = "ITEM_COUNT_PRICE";
    public static final String ITEM_NUMBER = "ITEM_NUMBER";
    double sunPrice = 0;   //总价格

    @Override
    public void initData(View view) {
        super.initData(view);
        TextView tvTrabelDate = (TextView) view.findViewById(R.id.mc_travel_date);
        bundle = getArguments();
        tvTrabelDate.setText(bundle.getString(MessageDateFrament.TIMER));
        List<Person> personList = (List<Person>) bundle.getSerializable(MessageAddFrament.PERSON);

        csServicesList = (List<CsServices>) bundle.getSerializable(MessageSelectFrament.CS_SERVICES_LIST);
        inServicesList = (List<InServices>) bundle.getSerializable(MessageSelectFrament.IN_SERVICES_LIST);
        ListView listView = (ListView) view.findViewById(R.id.conmit_listview);
        data = new ArrayList<Map<String, String>>();
         skus = new ArrayList<Object>();
        Map<String, String>  productMap = new HashMap<String, String>();
        productMap.put(ITEM_TITLE, bundle.getString(AreaVisaListFragment.TITLE));
        productMap.put(ITEM_PRICE, StringUtil.formatTowDouble(bundle.getString(AreaVisaListFragment.PRICE)));
        productMap.put(ITEM_NUMBER, personList.size() + "");
        productMap.put(ITEM_COUNT_PRICE, (StringUtil.formatTowDouble(personList.size() * Double.parseDouble(bundle.getString(AreaVisaListFragment.PRICE)))) + "元");
        data.add(productMap);
        for (CsServices csS : csServicesList) {
            Map<String, String> map = new HashMap<String, String>();
            String[]  iput=   csS.getSkukvdescription().split("\\|\\*\\|");
            String title="";
            if (iput.length>0){
                 title= SkuUtil.getPruductName(iput[0]);
            }
            String price = "0.00";
            if (iput.length>1){
                    price= SkuUtil.getPruductPrice(iput[1]);
            }
            map.put(ITEM_TITLE, title);
            map.put(ITEM_PRICE, StringUtil.formatTowDouble(Double.parseDouble(price)));
            map.put(ITEM_NUMBER, personList.size() + "");
            map.put(ITEM_COUNT_PRICE, (StringUtil.formatTowDouble(personList.size() * Double.parseDouble(price))) + "元");
            sunPrice += (personList.size() * Double.parseDouble(price));
            data.add(map);
            Sku sku=  new Sku();
            sku.setSku_num("1");
            sku.setSku_name(csS.getSkukvdescription());
            sku.setSo_price(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setPrice(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setSku_code(csS.getSkushortcode());
            skus.add(sku);
        }

        for (InServices ins : inServicesList) {
            Map<String, String> map = new HashMap<String, String>();
            String[]  iput=   ins.getSkukvdescription().split("\\|\\*\\|");
            String title="";
            if (iput.length>0){
                String [] spu= iput[0].split("-");
                if (spu.length>15){
                    title=spu[16];// 服务名称
                }
            }
            String price = "0.00";
            if (iput.length>1){
                String [] sku= iput[1].split("-");
                if (sku.length>7){
                    if(!StringUtil.isEmpty(sku[8])){
                        price=StringUtil.formatTowDouble(sku[8]);
                    }
                }
            }
            map.put(ITEM_TITLE, title);
            map.put(ITEM_PRICE, StringUtil.formatTowDouble(Double.parseDouble(price)));
            map.put(ITEM_NUMBER, personList.size() + "");
            map.put(ITEM_COUNT_PRICE, (StringUtil.formatTowDouble(personList.size() * Double.parseDouble(price))) + "元");
            sunPrice += (personList.size() * Double.parseDouble(price));
            data.add(map);
          Sku sku=  new Sku();
            sku.setSku_num("1");
            sku.setSku_name(ins.getSkukvdescription());
            sku.setSo_price(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setPrice(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setSku_code(ins.getSkushortcode());
            skus.add(sku);
        }
        sunPrice+=((Double.parseDouble(getArguments().getString(AreaVisaListFragment.PRICE)))*personList.size());
       // skus.add(getSku());
        BaseItemAdpter baseItemAdpter = new BaseItemAdpter(getActivity(), R.layout.item_select
                , data, this, R.id.item_subtotal, R.id.item_btn_select, R.id.item_rb_check, R.id.item_tv_title, R.id.item_tv_price, R.id.item_tv_number);
        getNextBtn().setText("确定订单 ("+StringUtil.formatTowDouble(sunPrice)+"元)");
        listView.setAdapter(baseItemAdpter);
        persons = (List<Person>) bundle.get(MessageAddFrament.PERSON);
        getNextBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    requestCrad();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ///
            }
        });

    }


    private void pay(){
        try {
            Intent intent = new Intent("com.pax.CALL_PAYMENT");
            intent.putExtra("TRANS_TYPE", "001");//
            intent.putExtra("TRANS_AMT",StringUtil.formatTowDouble(sunPrice)+"");
            startActivityForResult(intent, SEND_REQUEST);
        }
        catch (Exception e){
            ProgressDialogUtil.dismiss();
            ToastUtil.showToast(getActivity(), "请安装中国银联客户端");
        }
    }

    private final static int SEND_REQUEST = 11;

   private  String cradId;

    /***
     * 请求订单和购物车ＩＤ
     */
    private void requestCrad() {
        ProgressDialogUtil.show(getActivity());
        Map<String,Object> map=new HashMap<String, Object>();
        number = ((inServicesList.size()+csServicesList.size())*persons.size())+persons.size();
        map.put("number", number +"");
        HttpClientHelper.getInstance(getActivity()).post(URL.ORDERCODE, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults messageResults = JsonUtil.fromJson(data,MessageResults.class);
                if (messageResults.isSuccess()) {
                    if (!StringUtil.isEmpty(messageResults.getMsg())) {
                      StringMap stringMap= (StringMap) messageResults.getData();
                        requestOrderNumber(stringMap.get("car_code").toString(), stringMap.get("so_code"));
                    } else {
                        ProgressDialogUtil.dismiss();
                        ToastUtil.showToast(getActivity(), getResources().getString(R.string.order_error));
                    }
                } else {
                    Log.e(messageResults.getMsg());
                    ProgressDialogUtil.dismiss();
                    ToastUtil.showToast(getActivity(), getResources().getString(R.string.order_error));
                }
            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
                ToastUtil.showToast(getActivity(), getResources().getString(R.string.order_error));
            }
        });
    }

    /**
     *   下单接口
     * @param cradId
     * @param soCodeList
     */
 // private  List<String> orderIdList;
    public void requestOrderNumber(String cradId, Object soCodeList) {
       this.cradId= cradId;
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, getMap(soCodeList), new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults messageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (messageResults.isSuccess()) {
                    //  orderIdList= messageResults.getData();
                    pay();
                } else {
                    ProgressDialogUtil.dismiss();
                }
            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
        if (requestCode == SEND_REQUEST) {
            if (resultCode == Activity.RESULT_CANCELED) {
                String reason = data.getStringExtra("reason");
                ProgressDialogUtil.dismiss();
                ToastUtil.showToast(getActivity(), reason);
            } else if (resultCode == Activity.RESULT_OK) {
                String info = data.getStringExtra("info");
                Map<String ,Map<String,Object>> infoMap=     JsonUtil.fromJson(info, new TypeToken<Map<String, Map<String, Object>>>() {
                });
                Log.e(info + "");
                perClick(true);
                ProgressDialogUtil.dismiss();
                Map<String ,Object> map=  infoMap.get("info");
               // pay.setTrans_date(DateUitl.format(System.currentTimeMillis(), DateUitl.DATE_TIME_MILLISECOND_PATTERN));
                requestData(Pay.getPay(map));
            }
        }
    }
    }

    /***
     * 组装订单数据
     *
     * @param data
     * @param pay
     */
   /* private Map<String, Object> getMap(List<String> data, Pay pay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.SAVESO);
        map.put(Constant.SHOPCART_CODE,cradId);    // 购物车id
        map.put(Constant.SO_CONUT, persons.size()+"");    // 订单条数
        map.put(Constant.USER, bundle.getSerializable(WriteMessageFrament.USER));    //制单人信息
        map.put(Constant.SO, getSoOrder(data, pay));  //订单信息
        map.put(Constant.SO_TOTAL, sunPrice + "");  //总金额
        map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_VALUE);  //总金额
        return map;
    }*/


     private Map<String, Object> getMap(Object soCodeList) {
         Map<String, Object> map = new HashMap<String, Object>();
         map.put(Constant.SHOPCART_CODE, cradId);
         map.put(Constant.SO_CONUT, number + "");
         map.put(Constant.TYPE, Constant.Type.SAVESOINFO);
         map.put(Constant.USER, bundle.getSerializable(WriteMessageFrament.USER));    //制单人信息
         map.put(Constant.SO, getSoOrder(soCodeList));  //订单信息
         map.put(Constant.SO_TOTAL, StringUtil.formatTowDouble(sunPrice));  //总金额
         map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_VALUE);  //签证类型
      /*  Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.SO_CONUT, persons.size()+"");    // 订单条数
        map.put(Constant.USER, bundle.getSerializable(WriteMessageFrament.USER));    //制单人信息
        map.put(Constant.SO, getSoOrder(data, pay));  //订单信息
        map.put(Constant.SO_TOTAL, sunPrice + "");  //总金额
        map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_VALUE);  //总金额*/
        return map;
    }


    private Map<String, Object> getMap(Pay pay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.SAVEPAY);
        map.put(Constant.SHOPCART_CODE, cradId);  //购物车id
        map.put(Constant.PAY, pay);  //支付信息
        return map;
    }

    private List<SoOrder> getSoOrder(Object soCodeList) {
        List<SoOrder> soOrders = new ArrayList<SoOrder>();
        SoAdd soAdd = (SoAdd) getArguments().getSerializable(WriteMessageFrament.SOADD);
        Notes notes = (Notes) getArguments().getSerializable(WriteMessageFrament.NOTES);
        String recomentBank = getArguments().getString(WriteMessageFrament.RECOMENTDEBANK);
        int j=0;
        if (soCodeList instanceof ArrayList){
            List soList= (List) soCodeList;
            for (int i = 0; i < persons.size(); i++) {  //商品
                Person p = persons.get(i);
                soOrders.add(getOrder(getSku(),p,soList.get(j).toString(),recomentBank,notes,soAdd));
                j++;
                for (Object s:skus){
                    if (s instanceof  Sku){ //保险服务
                        soOrders.add(getOrder((Sku)s,p,soList.get(j).toString(),recomentBank,notes,soAdd));
                        j++;
                    }
                }
            }
        }
        Log.e("===========>"+soOrders.size());
        return soOrders;
    }


    private  SoOrder getOrder(Sku sku,Person p,String soCode,String recomentBank, Notes notes, SoAdd soAdd ){
        SoOrder so = new SoOrder();
        so.setSo_code(soCode);
        so.setStatus("1");
        so.setCreate_date(DateUitl.formartCurrentDate());
        so.setConsulate_aire(SharedUtil.getString(getActivity(), Constant.CONSULATE_AIRE));
        so.setBranch_firm(SharedUtil.getString(getActivity(), Constant.CONSULATE_AIRE));
        so.setGoouttime(bundle.getString(MessageDateFrament.TIMER));
        so.setRecommended_bank(recomentBank);
        List<Sku> list=new ArrayList<Sku>();
        list.add(sku);
        so.setSkus(list);
        so.setVisauser(getVisauser(p));
        so.setPay(Pay.getPay());
        so.setNotes(notes);
        so.setSo_add(soAdd);
        return  so;
    }


    private List<?> getSkus() {
        return skus;
    }



  /**
     * 签证sku信息
     * @return
   **/

    private Sku getSku(){
        Sku  sku=new Sku();
        sku.setSku_num("1");
        sku.setSku_name(bundle.getString(AreaVisaListFragment.SKUKVDESCRIPTION));
        sku.setPrice(bundle.getString(AreaVisaListFragment.PRICE));
        sku.setSku_code(bundle.getString(AreaVisaListFragment.SKUSHORTCODE));
        sku.setSo_price(bundle.getString(AreaVisaListFragment.PRICE));
        return  sku;

    }

    /**
     * 保险信息
     *
     * @param person
     * @return
     *//*
    private InServices getInServices(Person person) {
        InServices inServices = new InServices();
        inServices.setSku_num("1");
        inServices.setSku_name("美国-上海领区-个人旅游-10年-保险-18-180天-全国-五颗星-100.00-9.99-100.23");
        inServices.setPrice("100");
        inServices.setSku_code("sku-4");

        return inServices;
    }

    *//**
     * 附加服务信息
     *
     * @param person
     * @return
     *//*
    private CsServices getCsServices(Person person) {
        CsServices csServices = new CsServices();
        csServices.setSku_code("sku-5");
        csServices.setSo_price("100");
        csServices.setSku_name("美国-上海领区-个人旅游-10年-特色服务-18-180天-全国-五颗星-100.00-9.99-100.23");
        csServices.setSku_num("1");
        csServices.setPrice("100");
        return csServices;
    }
*/
    /**
     * 签证人信息
     *
     * @param person
     * @return
     */
    private Visauser getVisauser(Person person) {
        Visauser visauser = new Visauser();
        visauser.setSur_name(person.getName().substring(0, 1));
        visauser.setName(person.getName().substring(1));
        visauser.setMobile(person.getMobile());
        visauser.setGender(person.getSexString());
        visauser.setAssetsCode(person.getPassportNumber());
        return visauser;
    }



    /**
     * 发票信息
     * @return
     *//*
    private Notes  getNotes(Person p){
        Notes notes=new Notes();
        notes.setIs_notes(true);
        notes.setRise("签证宝");
        notes.setContent("内容");
        notes.setNotestype("个人");
        return  notes;
    }

    */

    /**
     * 快递信息
     *
     * @param data
     * @param pay
     * @return
     *//*
    private SoAdd getSoAdd(Person p){
        SoAdd soAdd=new SoAdd();
        soAdd.setAdd_post_code("200000");
        soAdd.setSo_add("上海市静安区北京西路");
        soAdd.setSur_name("彭");
        soAdd.setName("国进");
        soAdd.setMobile("13651666456");
        return  soAdd;
    }*/

    public void requestData(Pay pay) {
        final  Map<String, Object> map=  getMap(pay);
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()) {
                    //StringMap strMap = (StringMap) mapMessageResults.getData();
                  /*  saveFlag = Boolean.parseBoolean(strMap.get("saveFlag").toString());
                    if (saveFlag) {*/

                   // }
                } else {
                    com.visabao.machine.util.Log.e(mapMessageResults.getMsg());
                    CacheUtil.saveOrderCache(getActivity(), map);
                }

            }
            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
                CacheUtil.saveOrderCache(getActivity(), map);
            }
        });
    }


    @Override
    public int getContentLayoutId() {
        return R.layout.message_conmit;
    }

    @Override
    public void handerItemView(SparseArray<View> viewHolder, int position, View convertView, ViewGroup parent) {
        viewHolder.get(R.id.item_btn_select).setVisibility(View.GONE);
        viewHolder.get(R.id.item_rb_check).setVisibility(View.GONE);
        Map<String, String> map = data.get(position);
        TextView tvTitleView = (TextView) viewHolder.get(R.id.item_tv_title);
        tvTitleView.setText(map.get(ITEM_TITLE));
        TextView tvPrice = (TextView) viewHolder.get(R.id.item_tv_price);
        tvPrice.setText(map.get(ITEM_PRICE));
        TextView tvNumber = (TextView) viewHolder.get(R.id.item_tv_number);
        tvNumber.setText(map.get(ITEM_NUMBER));
        TextView tvSubtotal = (TextView) viewHolder.get(R.id.item_subtotal);
        tvSubtotal.setVisibility(View.VISIBLE);
        tvSubtotal.setText(map.get(ITEM_COUNT_PRICE));
    }
}

package com.visabao.machine.fragment.message;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;
import com.visabao.machine.R;
import com.visabao.machine.entity.Notes;
import com.visabao.machine.entity.Pay;
import com.visabao.machine.entity.Sku;
import com.visabao.machine.entity.SoAdd;
import com.visabao.machine.entity.SoOrder;
import com.visabao.machine.entity.User;
import com.visabao.machine.entity.Visauser;
import com.visabao.machine.fragment.AreaVisaListFragment;
import com.visabao.machine.fragment.GlobalFrament;
import com.visabao.machine.fragment.HomeFragment;
import com.visabao.machine.global.AppConfig;
import com.visabao.machine.global.CacheUtil;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.DateUitl;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SharedUtil;
import com.visabao.machine.util.SkuUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;

import android.widget.RadioGroup;
import android.widget.Toast;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * 基本信息
 */
public class WriteMessageFrament extends MessageBaseFrament {
    private EditText tvName;
    private EditText tvMobile;
    private EditText tvPassportNumber;
    private EditText tvEmail;
    private TextView tvProvince, tvCity, tvArea;
    private List<StringMap> provinceList;

    private List<StringMap> citysList;

    private List<StringMap> areaList;
    private Button sendCodeBtn;
    private EditText etAddress;
    private EditText etRecommendedBank;
    private RadioGroup rgMessage;
    private RadioGroup rgType;
    private TextView tvRise;
    private TextView tvContent;
    private EditText etCode;
    private String price;
    private String type;
    private String skuCode;
    private boolean saveFlag=true;
    private VisaMessageFrament messageFrament;
    private ScrollView scrollView;

    @Override
    public int getContentLayoutId() {
        return R.layout.write_message;
    }

    boolean isShowCity, isShowArea;

    private TimeCount time;
    private TimeEffectiveCount timeEffective;

    private final static int SEND_REQUEST = 11;

    private void pay() {
        try {
            Intent intent = new Intent("com.pax.CALL_PAYMENT");
            intent.putExtra("TRANS_TYPE", "001");//
            intent.putExtra("TRANS_AMT", StringUtil.formatTowDouble(price) + "");
            startActivityForResult(intent, SEND_REQUEST);
        } catch (Exception e) {
            ProgressDialogUtil.dismiss();
            ToastUtil.showToast(getActivity(), "请安装中国银联客户端");
        }
    }

    private String cradId;

    private void requestCrad() {
        ProgressDialogUtil.show(getActivity());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("number", "1");
        HttpClientHelper.getInstance(getActivity()).post(URL.ORDERCODE, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults messageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (messageResults.isSuccess()) {
                    if (!StringUtil.isEmpty(messageResults.getMsg())) {
                        StringMap stringMap = (StringMap) messageResults.getData();
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

    private List<String> orderIdList;

    public void requestOrderNumber(String cradId, Object soCodeList) {
        this.cradId = cradId;
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, getMap(soCodeList), new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults messageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (messageResults.isSuccess()) {
                    //  orderIdList= messageResults.getData();
                    pay();
                } else {
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
                    Map<String, Map<String, Object>> infoMap = JsonUtil.fromJson(info, new TypeToken<Map<String, Map<String, Object>>>() {
                    });
                    Log.e(info + "");
                    perClick(true);
                    Map<String, Object> map = infoMap.get("info");
                    // pay.setTrans_date(DateUitl.format(System.currentTimeMillis(), DateUitl.DATE_TIME_MILLISECOND_PATTERN));
                    requestData(Pay.getPay(map));

                }
            }
        }
    }


  /*  */

    /***
     * 组装订单数据
     *
     * @param data
     * @param pay
     *//*
    private Map<String, Object> getMap(List<String> data, Pay pay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.SAVESO);
        map.put(Constant.SHOPCART_CODE, cradId);    // 购物车id
        map.put(Constant.SO_CONUT, "1");    // 订单条数
        map.put(Constant.VISA_TYPE,Constant.VISA_TYPE_GLOBAL);
        User user = new User();
        String name = tvName.getText().toString();
        user.setSur_name(name.substring(0, 1));
        user.setName(name.substring(1));
        user.setMobile(tvMobile.getText().toString());
        user.setMail(tvEmail.getText().toString());
      *//*  bundle.putSerializable(USER, user);
        bundle.putSerializable(SOADD, getSoAdd());
        bundle.putSerializable(NOTES, getNotes());*//*

        map.put(Constant.USER, user);    //制单人信息
        map.put(Constant.SO, getSoOrder(data, pay));  //订单信息
        map.put(Constant.SO_TOTAL, price);  //总金额
        map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_GLOBAL);
        return map;
    }*/
    private Map<String, Object> getMap(Object soCodeList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.SHOPCART_CODE, cradId);
        map.put(Constant.SO_CONUT, "1");
        map.put(Constant.TYPE, Constant.Type.SAVESOINFO);
        User user = new User();
        String name = tvName.getText().toString();
        user.setSur_name(name.substring(0, 1));
        user.setName(name.substring(1));
        user.setMobile(tvMobile.getText().toString());
        user.setMail(tvEmail.getText().toString());
        map.put(Constant.USER, user);    //制单人信息
        map.put(Constant.SO, getSoOrder(soCodeList, user));  //订单信息
        map.put(Constant.SO_TOTAL, StringUtil.formatTowDouble(price));  //总金额
        map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_GLOBAL);  //签证类型
      /*  Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.SO_CONUT, persons.size()+"");    // 订单条数
        map.put(Constant.USER, bundle.getSerializable(WriteMessageFrament.USER));    //制单人信息
        map.put(Constant.SO, getSoOrder(data, pay));  //订单信息
        map.put(Constant.SO_TOTAL, sunPrice + "");  //总金额
            map.put(Constant.VISA_TYPE, Constant.VISA_TYPE_VALUE);  //总金额*/
        return map;
    }


    private List<SoOrder> getSoOrder(Object soCodeList, User user) {
        if (soCodeList instanceof ArrayList) {
            List soList = (List) soCodeList;
            List<SoOrder> soOrders = new ArrayList<SoOrder>();
            SoAdd soAdd = getSoAdd();
            Notes notes = getNotes();
            String recomentBank = etRecommendedBank.getText().toString();
            SoOrder so = new SoOrder();
            so.setSo_code(soList.get(0).toString());
            so.setStatus("1");
            so.setCreate_date(DateUitl.formartCurrentDate());
            so.setConsulate_aire(SharedUtil.getString(getActivity(), Constant.CONSULATE_AIRE));
            so.setBranch_firm(SharedUtil.getString(getActivity(), Constant.CONSULATE_AIRE));
            so.setGoouttime("");
            so.setRecommended_bank(recomentBank);
            Visauser visauser = new Visauser();
            visauser.setMobile(user.getMobile());
            visauser.setAssetsCode(tvPassportNumber.getText().toString());
            visauser.setName(user.getName());
            visauser.setGender(null);
            visauser.setSur_name(user.getSur_name());
            visauser.setVisaoccupation(null);
            visauser.setName_en(null);
            visauser.setVisa_user_type(null);
            visauser.setVisa_add(null);
            visauser.setSur_name_en(null);
            so.setVisauser(visauser);
            Sku sku = new Sku();
            sku.setSku_num("1");
            sku.setSku_name("");
            sku.setSo_price(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setPrice(StringUtil.formatTowDouble(Double.parseDouble(price)));
            sku.setSku_code(getArguments().getString(HomeFragment.F_CODE));
            sku.setSku_name(getArguments().getString(AreaVisaListFragment.SKUKVDESCRIPTION));
            List<Sku> skus = new ArrayList<Sku>();
            skus.add(sku);
            so.setSkus(skus);
            so.setPay(Pay.getPay());
            so.setNotes(notes);
            so.setSo_add(soAdd);
            soOrders.add(so);
            return soOrders;
        }
        return null;
    }

    private Map<String, Object> getMap(Pay pay) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.SAVEPAY);
        map.put(Constant.SHOPCART_CODE, cradId);  //购物车id
        map.put(Constant.PAY, pay);  //支付信息
        return map;
    }


    public void requestData(Pay pay) {
        final Map<String, Object> map = getMap(pay);
        ProgressDialogUtil.dismiss();
   /*     CacheUtil.saveOrderCache(getActivity(), map);*/

        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()) {
                } else {
                    com.visabao.machine.util.Log.e(mapMessageResults.getMsg());
                    CacheUtil.saveOrderCache(getActivity(), map);
                }

            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
                perClick(true);
                CacheUtil.saveOrderCache(getActivity(), map);
            }
        });
    }
    boolean frishShow=false;

    @Override
    public void onResume() {
        super.onResume();

    }
    android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.input_scroll_height)));
            }
            else if (msg.what==2){
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.scroll_height)));
            }
            else  if (msg.what==3){
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.global_input_scroll_height)));
            }
            else  if (msg.what==4){
                scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.global_scroll_height)));
            }

        }
    };


    //Activity最外层的Layout视图
    private View framentRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    /***
     * 初始化数据
     *
     * @param view
     */
    @Override
    public void initData(View view) {
        super.initData(view);
        find(view);
        type = getArguments().getString(GlobalFrament.TYPE);

        framentRootView = view.findViewById(R.id.rl_write);
        //获取屏幕高度
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 3;
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
        if (GlobalFrament.TYPE_VALUE.equals(type)) {  //全球签
            view.findViewById(R.id.ll_message_title).setVisibility(View.VISIBLE);
            getNextBtn().setText("去支付");
            TextView tvTitle = (TextView) view.findViewById(R.id.visa_wirte_tv_title);
            tvTitle.setText(getResources().getString(R.string.glabal_visabao));
            View llPassport = view.findViewById(R.id.wm_et_passport_number);
            llPassport.setVisibility(View.VISIBLE);
            EditText etPassportNo = (EditText) view.findViewById(R.id.wm_et_passport_no);
            getUpView().setVisibility(View.INVISIBLE);
             scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.global_scroll_height)));
            skuCode = getArguments().getString(HomeFragment.SKU_CODE);
            price = SkuUtil.getPruductPrice(skuCode);
            getNextBtn().setText("去支付 (" + price + "元)");
            getNextBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkMsg()) {
                        requestCrad();
                    }
                }
            });
        } else {   //普通标签
            Fragment fragment = getParentFragment();
            if (fragment instanceof VisaMessageFrament) {
                messageFrament = ((VisaMessageFrament) fragment);
                User user = messageFrament.getUser();
                //初始化当前填过的数据
                if (user != null) {
                    tvName.setText(user.getSur_name() + user.getName());
                    tvEmail.setText(user.getMail());
                }
                //   tvProvince.setText();
                Map<String, String> messageMap = messageFrament.getMessageMap();
                if (messageFrament.getMessageMap() != null) {
                    String pcode = messageMap.get(provinceCode);
                    if (!StringUtil.isEmpty(pcode)) {
                        Log.e("" + pcode);
                        tvProvince.setTag(pcode);
                        tvProvince.setText(messageMap.get(provinceStr));
                    }
                    String acode = messageMap.get(areaCode);
                    if (!StringUtil.isEmpty(acode)) {
                        tvArea.setTag(acode);
                        Log.e("" + acode);
                        tvArea.setText(messageMap.get(areaStr));
                    }
                    String ccode = messageMap.get(cityCode);
                    if (!StringUtil.isEmpty(ccode)) {
                        tvCity.setTag(ccode);
                        Log.e("" + ccode);
                        tvCity.setText(messageMap.get(cityStr));
                    }
                    etAddress.setText(messageMap.get(address));
                }


                Notes notes = messageFrament.getNotes();
                if (notes != null) {
                    rgMessage.check(notes.is_notes() ? R.id.rb_invoice_message_yes : R.id.rb_invoice_message_no);
                    tvRise.setText(notes.getRise());
                    tvContent.setText(notes.getContent());
                    if (notes.getNotestype().equals("个人")) {
                        rgType.check(R.id.rb_invoice_type_yes);
                    } else {
                        rgType.check(R.id.rb_invoice_type_no);
                    }
                    SoAdd soAdd = messageFrament.getSoAdd();
                    tvMobile.setText(soAdd.getMobile() + "");
                }

            }
        };
        framentRootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                 frishShow=true;
                    if (GlobalFrament.TYPE_VALUE.equals(type)) {
                        Log.e("=====================GlobalFrament===================");
                        handler.sendEmptyMessage(3);
                    }
                    else {
                        if (!GlobalFrament.TYPE_VALUE.equals(type))
                            handler.sendEmptyMessage(1);
                        Log.e("=====================framentRootView===================");
                        //   }
                    }

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                   Log.e("=====================1111===================" + getResources().getDimensionPixelSize(GlobalFrament.TYPE_VALUE.equals(type) ? R.dimen.global_scroll_height : R.dimen.input_scroll_height));
                    if (!GlobalFrament.TYPE_VALUE.equals(type))
                        handler.sendEmptyMessage(2);
                    else
                        handler.sendEmptyMessage(4);
                    //  scrollView.setLayoutPa*//*rams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,getResources().getDimensionPixelSize(R.dimen.input_scroll_height)));*/
                }
            }
        });
        time = new TimeCount(60000, 1000);
        timeEffective = new TimeEffectiveCount(AppConfig.CODE_EFFECTIVE_TIMER, 1000);
        requestProvince(null);
        tvProvince.setOnClickListener(new View.OnClickListener() {  //选择省
            @Override
            public void onClick(View v) {
                if (provinceList == null) {
                    requestProvince(null);
                } else {
                    showDialog(provinceList, v);
                }
            }
        });
        tvCity.setOnClickListener(new View.OnClickListener() {  //市
            @Override
            public void onClick(View v) {
                if (tvProvince.getTag() != null) {
                    if (citysList == null) {
                        isShowCity = true;
                        requestProvince(tvProvince.getTag().toString());
                    } else {
                        showDialog(citysList, v);
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "请选择省");
                }
            }
        });
        tvArea.setOnClickListener(new View.OnClickListener() {//区
            @Override
            public void onClick(View v) {
                if (tvCity.getTag() != null) {
                    if (areaList == null) {
                        isShowArea = true;

                        requestProvince(tvCity.getTag().toString());
                    } else {
                        showDialog(areaList, v);
                    }
                } else {
                    ToastUtil.showToast(getActivity(), "请选择市");
                }


            }
        });
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtil.showToast(getActivity(),"发送验证码成功");
                sendCode();

            }
        });
        rgMessage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_invoice_message_no) {
                    tvRise.setEnabled(false);
                } else {
                    tvRise.setEnabled(true);
                }
            }
        });
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            sendCodeBtn.setText("获取验证码");
            sendCodeBtn.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            sendCodeBtn.setClickable(false);//防止重复点击
            sendCodeBtn.setText(millisUntilFinished / 1000 + "s");
        }
    }


    class TimeEffectiveCount extends CountDownTimer {
        public TimeEffectiveCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕

            code = "";
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
        }
    }


    String code = "";

    private void sendCode() {
        Map<String, Object> map = new HashMap<String, Object>();
        String mobile = tvMobile.getText().toString();
        if (StringUtil.isEmpty(mobile)) {
            ToastUtil.showToast(getActivity(), "请输入手机号码");
            return;
        } else if (!StringUtil.isMobileNo(mobile)) {
            ToastUtil.showToast(getActivity(), "请输入正确的手机号码");
            return;
        }

        map.put("mobile", mobile);
        map.put(Constant.TYPE, Constant.Type.SMS);
        code = "";
        timeEffective.cancel();
        time.start();
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                MessageResults<Map<String, String>> messageResults = JsonUtil.fromJson(data, new TypeToken<MessageResults<Map<String, String>>>() {
                });
                if (messageResults.isSuccess()) {
                    code=messageResults.getData().get("smsCode");
                    timeEffective.start();
                    //code = "123456";

                } else {
                    ToastUtil.showToast(getActivity(), "获取验证码");
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void requestProvince(String cityId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.GETCITY);
        if (cityId != null) {
            map.put("parentId", cityId);
        }
        HttpClientHelper.getInstance(getActivity()).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()) {
                    StringMap stringMap = (StringMap) mapMessageResults.getData();

                    if (stringMap.get("citys") != null) {
                        if (provinceList == null) {  //省
                            provinceList = (List<StringMap>) stringMap.get("citys");
                        } else if (citysList == null) {
                            citysList = (List<StringMap>) stringMap.get("citys");   //市
                        } else if (areaList == null) {
                            areaList = (List<StringMap>) stringMap.get("citys");   //区
                        }
                        if (isShowCity) {
                            isShowCity = false;
                            showDialog(citysList, tvCity);
                        }
                        if (isShowArea) {
                            isShowArea = false;
                            showDialog(areaList, tvArea);
                        }
                    }
                } else {
                    com.visabao.machine.util.Log.e(mapMessageResults.getMsg());
                }
            }

            @Override
            public void onFailure() {
                ProgressDialogUtil.dismiss();
            }
        });
    }

    ;

    private void showDialog(final List<StringMap> stringMapList, final View v) {
        if (stringMapList == null) return;
        final String[] cities = new String[stringMapList.size()];
        for (int i = 0; i < stringMapList.size(); i++) {
            StringMap map = stringMapList.get(i);
            if (v == tvProvince) {
                cities[i] = (String) map.get("province");
            } else if (v == tvCity) {
                cities[i] = (String) map.get("cityName");
            } else if (v == tvArea) {
                cities[i] = (String) map.get("county");
            }

        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.myDialogTheme);
        builder.setTitle("");
        //    设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((TextView) v).setText(cities[which]);
                String cityId = StringUtil.subStringIndex(stringMapList.get(which).get("cityId").toString(), ".");
                if (v == tvProvince) {
                    if (!cityId.equals(tvProvince.getText().toString())) {
                        tvCity.setTag(null);
                        tvCity.setText("市");
                        tvArea.setTag(null);
                        tvArea.setText("区");
                        citysList = null;
                        areaList = null;
                    }
                }
                if (v == tvCity) {
                    if (!cityId.equals(tvCity.getText().toString())) {
                        tvArea.setTag(null);
                        tvArea.setText("区");
                        areaList = null;

                    }
                }
                ((TextView) v).setTag(cityId);
                requestProvince(cityId);
            }
        });
        builder.show();
    }

    private void find(View view) {
        tvName = (EditText) view.findViewById(R.id.wm_et_name);  //姓名
        tvMobile = (EditText) view.findViewById(R.id.wm_et_mobile);  //手机号码
        tvPassportNumber = (EditText) view.findViewById(R.id.wm_et_passport_no);  //护照号码
        tvEmail = (EditText) view.findViewById(R.id.wm_et_passport_emial);  //电子邮箱;
        tvProvince = (TextView) view.findViewById(R.id.wm_tv_province);  //省
        tvCity = (TextView) view.findViewById(R.id.wm_tv_city);  //市
        tvArea = (TextView) view.findViewById(R.id.wm_tv_area);  //区
        sendCodeBtn = (Button) view.findViewById(R.id.btn_send_code); // 发送验证码Btn
        etCode = (EditText) view.findViewById(R.id.message_et_code);
        etAddress = (EditText) view.findViewById(R.id.wm_et_address);  //详细地址
        etRecommendedBank = (EditText) view.findViewById(R.id.wm_et_recommended_bank);  //推荐行编码
        rgMessage = (RadioGroup) view.findViewById(R.id.wm_rg_invoice_message); // 发票信息
        rgType = (RadioGroup) view.findViewById(R.id.wm_rg_invoice_type);  //发票类型
        tvRise = (TextView) view.findViewById(R.id.wm_et_rise); // 发票抬头
        tvContent = (TextView) view.findViewById(R.id.wm_tv_content);

    }


    private User getUser() {
        User user = new User();
        String name = tvName.getText().toString();
        if (name.length() > 0) {
            user.setSur_name(name.substring(0, 1));
        }

        if (name.length() > 1) {
            user.setName(name.substring(1));
        }
        user.setMobile(tvMobile.getText().toString());
        user.setMail(tvEmail.getText().toString());
        return user;
    }

    private Bundle getBundle() {
        Bundle bundle = getArguments();
        bundle.putSerializable(USER, getUser());
        bundle.putSerializable(SOADD, getSoAdd());
        bundle.putSerializable(NOTES, getNotes());
        bundle.putSerializable(RECOMENTDEBANK, etRecommendedBank.getText().toString());
        return bundle;
    }

    private Notes getNotes() {
        Notes notes = new Notes();
        notes.setIs_notes(rgMessage.getCheckedRadioButtonId() == R.id.rb_invoice_message_yes);
        notes.setRise(tvRise.getText().toString());
        notes.setContent(tvContent.getText().toString());
        notes.setNotestype(rgType.getCheckedRadioButtonId() == R.id.rb_invoice_type_yes ? "个人" : "单位");
        return notes;
    }

    private SoAdd getSoAdd() {  //快递信息
        SoAdd soAdd = new SoAdd();
        soAdd.setMobile(tvMobile.getText().toString());
        String name = tvName.getText().toString();
        soAdd.setSo_add(tvProvince.getText().toString() + tvCity.getText().toString() + tvArea.getText().toString() + etAddress.getText().toString());
        if (name.length() > 0) {
            soAdd.setSur_name(name.substring(0, 1));
        }

        if (name.length() > 1) {
            soAdd.setName(name.substring(1));
        }
        return soAdd;
    }

    public final static String USER = "USER";
    public final static String SOADD = "SOADD";
    public final static String NOTES = "NOTES";
    public final static String RECOMENTDEBANK = "RECOMENTDEBANK";

    private boolean checkMsg() {
        if (StringUtil.isEmpty(tvName.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入中文姓名");
            return false;
        }
        if (StringUtil.isEmpty(tvMobile.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入联系电话");
            return false;
        }
        if (GlobalFrament.TYPE_VALUE.equals(type) && StringUtil.isEmpty(tvPassportNumber.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入护照号");
            return false;
        }
        if (!StringUtil.isMobileNo(tvMobile.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入正确的手机号码");
            return false;
        }
        if (StringUtil.isEmpty(etCode.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入验证码");
        }
        if (code.equals("")) {
            ToastUtil.showToast(getActivity(), "请获取验证码");
            return false;
        }
        if (!code.equals(etCode.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入正确的验证码");
            return false;
        }
        if (StringUtil.isEmpty(tvEmail.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入的电子邮箱");
            return false;
        }
        if (!StringUtil.isEmail(tvEmail.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入正确的电子邮箱");
            return false;
        }
        if (tvProvince.getTag() == null || tvCity.getTag() == null || tvArea.getTag() == null || StringUtil.isEmpty(etAddress.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入填写收件地址");
            return false;
        }
      /*  if (StringUtil.isEmpty(etRecommendedBank.getText().toString())) {
            ToastUtil.showToast(getActivity(), "请输入推荐行编码");
            return false;
        }*/

        if (rgMessage.getCheckedRadioButtonId() == R.id.rb_invoice_message_yes) {
            if (StringUtil.isEmpty(tvRise.getText().toString())) {
                ToastUtil.showToast(getActivity(), "请输入发票抬头");
                return false;
            }
        }
        return true;
    }

    @Override
    public Fragment getTabNextIndex() {
        if (GlobalFrament.TYPE_VALUE.equals(type)) {
            MessageCompleteFrament messageCompleteFrament = new MessageCompleteFrament();
            Bundle bundle = getArguments();
            bundle.putBoolean(MessageConmitFrament.SAVEFLAG, saveFlag);
            messageCompleteFrament.setArguments(bundle);
            return messageCompleteFrament;
        } else {
            if (checkMsg()) {
                MessageAddFrament messageAddFrament = new MessageAddFrament();
                messageAddFrament.setArguments(getBundle());
                return messageAddFrament;
            }
        }
        return null;
    }

    private String recommendedBank = "recommendedBank";
    private String provinceStr = "provinceStr";
    private String cityStr = "cityStr";
    private String areaStr = "areaStr";

    private String provinceCode = "provinceCode";
    private String cityCode = "cityCode";
    private String areaCode = "areaCode";
    private String address = "address";


    @Override
    public int getTabUpIndex() {
        if (!GlobalFrament.TYPE_VALUE.equals(type)) {
            messageFrament.setUser(getUser());
            messageFrament.setSoAdd(getSoAdd());
            messageFrament.setNotes(getNotes());
            Map<String, String> map = new HashMap<String, String>();
            map.put(recommendedBank, etRecommendedBank.getText().toString());
            map.put(provinceStr, tvProvince.getText().toString());
            map.put(provinceCode, tvProvince.getTag() == null ? null : tvProvince.getTag().toString());
            map.put(cityStr, tvCity.getText().toString());
            map.put(cityCode, tvCity.getTag() == null ? null : tvCity.getTag().toString());
            map.put(areaStr, tvArea.getText().toString());
            map.put(areaCode, tvArea.getTag() == null ? null : tvArea.getTag().toString());
            map.put(address, etAddress.getText().toString());
            messageFrament.setMeeageMap(map);
        }
        return R.id.message_rb_date;

    }
}

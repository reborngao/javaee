package com.visabao.machine;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.reflect.TypeToken;
import com.visabao.machine.autoUpdate.VersionUpdateCusService;
import com.visabao.machine.global.AlarmReceiver;
import com.visabao.machine.global.BaseActivity;
import com.visabao.machine.global.CacheUtil;
import com.visabao.machine.global.Constant;
import com.visabao.machine.global.MessageResults;
import com.visabao.machine.global.TAAppManager;
import com.visabao.machine.global.URL;
import com.visabao.machine.util.AppUtil;
import com.visabao.machine.util.DESCoder;
import com.visabao.machine.util.HttpClientHelper;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.ProgressDialogUtil;
import com.visabao.machine.util.SharedUtil;
import com.visabao.machine.util.StringUtil;
import com.visabao.machine.util.ToastUtil;
import com.visabao.machine.widget.RadioGroup;

import org.apache.http.util.VersionInfo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 操作员签到
 */
public class LoginAcitivity extends BaseActivity{
    RadioGroup radioGroup;

    EditText  etBankCode;

    private   int mCheckedId;
    private VersionUpdateCusService versionUpdateCusService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        click(R.id.login_btn);



        radioGroup=find(R.id.login_radiogroup);
        etBankCode=find(R.id.login_et_bank_code);
        radioGroup.check(R.id.login_rb_sh);
        mCheckedId=R.id.login_rb_sh;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCheckedId = checkedId;
            }
        });
       versionUpate();
         versionUpdateCusService= VersionUpdateCusService.getInstance(this);
        versionUpdateCusService.autoInstall(true);
    }
    public void versionUpate() {
        Map<String,Object> map=new HashMap<String, Object>();
        map.put(Constant.TYPE,Constant.Type.VERSION);
        map.put("app_type","1");
        map.put("uuid", AppUtil.getUUID(this));
         HttpClientHelper.getInstance(this).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
             @Override
             public void onSuccess(String data) {
                 MessageResults<Map<String,VersionUpdateCusService.VersionInfo>> messageResults=    JsonUtil.fromJson(data,new TypeToken< MessageResults<Map<String,VersionUpdateCusService.VersionInfo>>>(){});
                 if (messageResults.isSuccess()){
                      VersionUpdateCusService.VersionInfo versionInfo= messageResults.getData().get("app_version");
                  /*   versionInfo.setApp_version("1.0.2");
                     versionInfo.setApp_url("http://www.immomo.com/download/momo_apk?d=&v=&mark=&market=&b=android_apk");*/
                     versionUpdateCusService.checkVersion(versionInfo);
                 }
                 else {
                     Log.d(messageResults.getMsg());
                 }
             }

             @Override
             public void onFailure() {
                 Log.i("网络异常，没有获取到检测版本更新数据");
             }
         });
    }





    public  void request(){

        if (radioGroup.getCheckedRadioButtonId()==-1){
            ToastUtil.showToast(this,"请选择领区");
            return;
        }
        if(StringUtil.isEmpty(etBankCode.getText().toString())){
            ToastUtil.showToast(this,"请输入银行编码");
            return;
        }

        Map<String ,Object> map=new HashMap<String, Object>();
        map.put(Constant.TYPE, Constant.Type.SIGN);
        map.put("d_code", AppUtil.getUUID(getApplicationContext()));
        map.put("d_cpu", AppUtil.getCpuName());
        map.put("d_model", AppUtil.getAndroidModel());
        map.put("d_system_version", AppUtil.getAndroidSDKVersion());
        map.put("app_version", AppUtil.getVersionName(getApplicationContext()));
        map.put("bankCode", etBankCode.getText().toString());
        ProgressDialogUtil.show(this);
        HttpClientHelper.getInstance(this).post(URL.PINGAN, map, new HttpClientHelper.HttpResponseHandler() {
            @Override
            public void onSuccess(String data) {
                ProgressDialogUtil.dismiss();
                MessageResults mapMessageResults = JsonUtil.fromJson(data, MessageResults.class);
                if (mapMessageResults.isSuccess()) {
                    Map<String, String> map = JsonUtil.fromJson(mapMessageResults.getData().toString(), new TypeToken<Map<String, String>>() {
                    });
                    if (!Boolean.parseBoolean(map.get("isBank"))) {
                        ToastUtil.showToast(LoginAcitivity.this, "银行编码不存在");
                    } else {
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(mCheckedId);
                        SharedUtil.putString(LoginAcitivity.this, Constant.CONSULATE_AIRE, radioButton.getText().toString());
                        startIntent(MainActivity.class);
                        finish();
                    }
                } else {
                    ToastUtil.showToast(LoginAcitivity.this,mapMessageResults.getMsg());
                    Log.e("====>" + mapMessageResults.getMsg());
                }
            }

            @Override
            public void onFailure() {

                ProgressDialogUtil.dismiss();
            }
        },true);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定退出签证宝?");
        builder.setTitle("");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TAAppManager.getAppManager().finishAllActivity();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.login_btn:
               request();
               break;
       }
    }

    public void startAnim() {
    }
}

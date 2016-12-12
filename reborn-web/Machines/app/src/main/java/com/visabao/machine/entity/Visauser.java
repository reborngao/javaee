package com.visabao.machine.entity;

import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;
//签证人信息
public class Visauser implements Serializable {
    private  String  sur_name=""; //中文姓
    private  String  name="";  //中文名
    private  String  sur_name_en=""; //英文姓
    private  String  name_en=""; //英文名
    private  String  mobile="";  //签证人手机号
    private  String  visa_add="";  //签证人地址
    private  String  gender="";  //签证人性别
    private  String  assetsCode="";  //护照号
    private  String  visa_user_type="";  //签证人类型
    private  String  visaoccupation="";  //签证人职业

    public String getSur_name() {
        return sur_name;
    }

    public void setSur_name(String sur_name) {
        this.sur_name = sur_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSur_name_en() {
        return sur_name_en;
    }

    public void setSur_name_en(String sur_name_en) {
        this.sur_name_en = sur_name_en;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVisa_add() {
        return visa_add;
    }

    public void setVisa_add(String visa_add) {
        this.visa_add = visa_add;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAssetsCode() {
        return assetsCode;
    }

    public void setAssetsCode(String assetsCode) {
        this.assetsCode = assetsCode;
    }

    public String getVisa_user_type() {
        return visa_user_type;
    }

    public void setVisa_user_type(String visa_user_type) {
        this.visa_user_type = visa_user_type;
    }

    public String getVisaoccupation() {
        return visaoccupation;
    }

    public void setVisaoccupation(String visaoccupation) {
        this.visaoccupation = visaoccupation;
    }


}

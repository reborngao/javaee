package com.visabao.machine.entity;

import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;


public class User  implements Serializable {



    private String sur_name =""; //中文姓
    private String name=""; ////中文名
    private String sur_name_en=""; //英文姓
    private String name_en=""; //英文名
    private String mail=""; //邮箱
    private String mobile=""; //手机号码


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}

package com.visabao.machine.entity;

import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;

/**
 * //快递信息
 */
public class SoAdd implements Serializable{
    private  String  add_post_code ="";//邮编
    private  String  so_add ="";  //地址
    private  String  sur_name =""; //姓
    private  String  name =""; //名
    private  String  mobile =""; //手机号

    public String getAdd_post_code() {
        return add_post_code;
    }

    public void setAdd_post_code(String add_post_code) {
        this.add_post_code = add_post_code;
    }

    public String getSo_add() {
        return so_add;
    }

    public void setSo_add(String so_add) {
        this.so_add = so_add;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}

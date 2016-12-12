package com.visabao.machine.entity;


import java.io.Serializable;

public class Refund implements Serializable {
    private  boolean  isRefund;
    private  String  visaUser;
    private  String  spu;
    private  String  sku;
    private  String  so_code;

    private  String  mobile;
    private  String  ref_no;
    private  String  trans_date;
    private  int  visaUserId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getVisaUserId() {
        return visaUserId;
    }

    public void setVisaUserId(int visaUserId) {
        this.visaUserId = visaUserId;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public String getRef_no() {
        return ref_no;
    }



    public boolean isRefund() {
        return isRefund;
    }

    public String getVisaUser() {
        return visaUser;
    }

    public String getSpu() {
        return spu;
    }

    public String getSku() {
        return sku;
    }

    public String getSo_code() {
        return so_code;
    }
}

package com.visabao.machine.entity;

import java.io.Serializable;
import java.util.List;


public class RefundPerson  implements Serializable {
    private  String name;
    private  int visaUserId;
    private List<Refund> refundList;
    private  boolean check;
    private  String  mobile;
    public boolean isRefund() {
        boolean flag=true;
        for (Refund ref:refundList){
            if (!ref.isRefund()){
                flag=false;
                break;
            }
        }

        return flag;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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

    public int getVisaUserId() {
        return visaUserId;
    }

    public void setVisaUserId(int visaUserId) {
        this.visaUserId = visaUserId;
    }

    public List<Refund> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<Refund> refundList) {
        this.refundList = refundList;
    }
}

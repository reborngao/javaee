package com.visabao.machine.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/28.
 */
public class Person  implements Serializable{
    public   static final  int FEMABLE=1;
    public  static   final  int MALE=2;
    private String name;
    private int sex;
    private String passportNumber;
    private String mobile;
     public  String getSexString(){
         if (sex==MALE){
             return "男";
         }
         else  if (sex==FEMABLE){
             return "女";
         }
         return "";
     }

    public String getName() {
        return name;
    }

    public int getSex() {
        return sex;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

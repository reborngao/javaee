package com.visabao.machine.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ImageList implements Serializable {
    private   String f_type;
    private   String f_code;
    private  String skucode;
    private   String f_path;
    private   String spucode;
    private   String skukvdescription;

    public String getSkukvdescription() {
        return skukvdescription;
    }

    public void setSkukvdescription(String skukvdescription) {
        this.skukvdescription = skukvdescription;
    }

    public String getF_path() {
        return f_path;
    }

    public void setF_path(String f_path) {
        this.f_path = f_path;
    }

    public String getF_code() {
        return f_code;
    }

    public void setF_code(String f_code) {
        this.f_code = f_code;
    }

    public String getF_type() {
        return f_type;
    }

    public void setF_type(String f_type) {
        this.f_type = f_type;
    }

    public String getSkucode() {
        return skucode;
    }

    public String getSpucode() {
        return spucode;
    }

    public void setSpucode(String spucode) {
        this.spucode = spucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }
}

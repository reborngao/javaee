package com.visabao.machine.entity;


import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;

public class InServices implements Serializable{
    private String sku_code ="";   //保险信息
    private String so_price ="";
    private String sku_name ="";
    private String sku_num ="";
    private String price ="";
    private String suggested_price ="";
    private String act_price ="";
    private String consulate_pay ="";
    private String product_cost ="";
    private String gross_profit ="";
    private String gross_profit_ratr ="";
    private String pure_profit ="";
    private String pure_profit_rate ="";
    private String express_cost ="";

    private  String skukvdescription;
    private  String skushortcode;
    private  String skucode;
    private  String skucodeid;

    private  boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSkukvdescription() {
        return skukvdescription;
    }

    public String getSkushortcode() {
        return skushortcode;
    }

    public void setSkushortcode(String skushortcode) {
        this.skushortcode = skushortcode;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getSkucodeid() {
        return skucodeid;
    }

    public void setSkucodeid(String skucodeid) {
        this.skucodeid = skucodeid;
    }

    private  String skukvdescriptionArray[];
    public void setSkukvdescriptionArray(String skukvdescription){
        skukvdescriptionArray= skukvdescription.split("-");
    }

    public String[] getSkukvdescriptionArray() {
        return skukvdescriptionArray;
    }

    public void setSkukvdescriptionArray(String[] skukvdescriptionArray) {
        this.skukvdescriptionArray = skukvdescriptionArray;
    }

    public void setSkukvdescription(String skukvdescription) {
        this.skukvdescription = skukvdescription;

        setSkukvdescriptionArray(skukvdescription);
    }

    public String getSku_code() {
        return sku_code;
    }

    public void setSku_code(String sku_code) {
        this.sku_code = sku_code;
    }

    public String getSo_price() {
        return so_price;
    }

    public void setSo_price(String so_price) {
        this.so_price = so_price;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public String getSku_num() {
        return sku_num;
    }

    public void setSku_num(String sku_num) {
        this.sku_num = sku_num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSuggested_price() {
        return suggested_price;
    }

    public void setSuggested_price(String suggested_price) {
        this.suggested_price = suggested_price;
    }


    public String getAct_price() {
        return act_price;
    }

    public void setAct_price(String act_price) {
        this.act_price = act_price;
    }

    public String getConsulate_pay() {
        return consulate_pay;
    }

    public void setConsulate_pay(String consulate_pay) {
        this.consulate_pay = consulate_pay;
    }

    public String getProduct_cost() {
        return product_cost;
    }

    public void setProduct_cost(String product_cost) {
        this.product_cost = product_cost;
    }

    public String getGross_profit() {
        return gross_profit;
    }

    public void setGross_profit(String gross_profit) {
        this.gross_profit = gross_profit;
    }

    public String getGross_profit_ratr() {
        return gross_profit_ratr;
    }

    public void setGross_profit_ratr(String gross_profit_ratr) {
        this.gross_profit_ratr = gross_profit_ratr;
    }

    public String getPure_profit() {
        return pure_profit;
    }

    public void setPure_profit(String pure_profit) {
        this.pure_profit = pure_profit;
    }

    public String getPure_profit_rate() {
        return pure_profit_rate;
    }

    public void setPure_profit_rate(String pure_profit_rate) {
        this.pure_profit_rate = pure_profit_rate;
    }

    public String getExpress_cost() {
        return express_cost;
    }

    public void setExpress_cost(String express_cost) {
        this.express_cost = express_cost;
    }
}

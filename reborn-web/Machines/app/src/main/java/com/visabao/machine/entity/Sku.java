package com.visabao.machine.entity;


import com.visabao.machine.util.JsonUtil;

import java.io.Serializable;

public class Sku  implements Serializable {
    private String sku_code ="";  //sku短码
    private String so_price ="";  //总价格
    private String sku_name ="";  //sku名称
    private String sku_num ="";  //数量
    private String price ="";  //单价
    private String suggested_price ="";//建议售价
    private String act_price =""; 	//实际售价
    private String consulate_pay =""; //领馆代收代付金额
    private String product_cost =""; //制作成本
    private String gross_profit =""; 	//毛利润
    private String gross_profit_ratr; 	//毛利率
    private String pure_profit =""; 	//净利润
    private String pure_profit_rate =""; //净利率
    private String express_cost =""; 	//快递费用

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

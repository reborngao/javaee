package com.visabao.machine.entity;

import java.io.Serializable;
import java.util.Map;

/**
 *   //支付信息
 */
public class Pay implements Serializable {
    private  String pay_type =""; //支付方式
    private  String trace_no ="";         //支付流水号
    private  String issue ="";        //支付银行(发卡行)
    private  String card_no =""; //卡号
    private  String trans_date ="";        //交易日期
    private  String amount ="";  //金额
    private  String reference_no ="";   //参考号
    private  String merchant_id ="";  //商户号
    private  String terminal_id ="";        //终端号
    private  String batch_no ="";  //批次号
    private  String merchant_name ="";      //商户名称
    private  String type ="";      //卡类型

    public String getPay_type() {
        return pay_type;
    }

    public String getTrace_no() {
        return trace_no;
    }

    public String getIssue() {
        return issue;
    }

    public String getCard_no() {
        return card_no;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public String getAmount() {
        return amount;
    }

    public String getReference_no() {
        return reference_no;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public String getType() {
        return type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public void setTrace_no(String trace_no) {
        this.trace_no = trace_no;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static  Pay getPay(Map<String,Object> map){
        Pay pay=new Pay();
        pay.setAmount(map.get("TRANS_AMT").toString());
        pay.setTrans_date((map.get("TRANS_DATE").toString().replace("-","")) + (map.get("TRANS_TIME").toString().replace(":","").replace(" ","")));
        pay.setReference_no(map.get("REF_NO").toString());
        pay.setTrace_no(map.get("TRACE_NO").toString());
        pay.setCard_no(map.get("CARD_NO").toString());
        pay.setType("1");
        pay.setIssue("");
        pay.setBatch_no(map.get("BATCH_NO").toString());
        pay.setTerminal_id(map.get("TERM_ID").toString());
        pay.setMerchant_id(map.get("MERCH_ID").toString());
        pay.setMerchant_name(map.get("MERCH_NAME").toString());
        return  pay;
    }
    public static  Pay getPay(){
        Pay pay=new Pay();
        pay.setPay_type(null);
        pay.setTrace_no(null);
        pay.setIssue(null);
        pay.setCard_no(null);
        pay.setAmount(null);
        pay.setReference_no(null);
        pay.setTrans_date(null);
        pay.setMerchant_name(null);
        pay.setMerchant_id(null);
        pay.setType(null);
        pay.setBatch_no(null);
        pay.setTerminal_id(null);
        return  pay;
    }
}

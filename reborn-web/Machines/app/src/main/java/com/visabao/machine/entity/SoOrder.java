package com.visabao.machine.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 订单信息
 */
public class SoOrder implements Serializable {
        private String so_code=""; //订单号
        private String status="";//订单状态
        private String create_date="";//创建时间
        private String consulate_aire ="";//领区
        private String branch_firm=""; //分公司
        private String goouttime="";//出行日期
        private String recommended_bank="";//推荐银行号or推荐人
        private String bank="";    //开户银行
        private InServices in_services;     //保险信息
        private CsServices cs_services;     //附加服务信息
        private Visauser visauser;     //签证人信息
        private Pay pay;    //支付信息
        private Notes notes;  	//发票信息
        private SoAdd so_add;    //快递信息


        private List<?> skus;


        public List<?> getSkus() {
                return skus;
        }

        public void setSkus(List<?> skus) {
                this.skus = skus;
        }

        public String getSo_code() {
                return so_code;
        }

        public void setSo_code(String so_code) {
                this.so_code = so_code;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public String getCreate_date() {
                return create_date;
        }

        public void setCreate_date(String create_date) {
                this.create_date = create_date;
        }

        public String getConsulate_aire() {
                return consulate_aire;
        }

        public void setConsulate_aire(String consulate_aire) {
                this.consulate_aire = consulate_aire;
        }

        public String getBranch_firm() {
                return branch_firm;
        }

        public void setBranch_firm(String branch_firm) {
                this.branch_firm = branch_firm;
        }

        public String getGoouttime() {
                return goouttime;
        }

        public void setGoouttime(String goouttime) {
                this.goouttime = goouttime;
        }

        public String getRecommended_bank() {
                return recommended_bank;
        }

        public void setRecommended_bank(String recommended_bank) {
                this.recommended_bank = recommended_bank;
        }

        public String getBank() {
                return bank;
        }

        public void setBank(String bank) {
                this.bank = bank;
        }



        public InServices getIn_services() {
                return in_services;
        }

        public void setIn_services(InServices in_services) {
                this.in_services = in_services;
        }

        public CsServices getCs_services() {
                return cs_services;
        }

        public void setCs_services(CsServices cs_services) {
                this.cs_services = cs_services;
        }

        public Visauser getVisauser() {
                return visauser;
        }

        public void setVisauser(Visauser visauser) {
                this.visauser = visauser;
        }

        public Pay getPay() {
                return pay;
        }

        public void setPay(Pay pay) {
                this.pay = pay;
        }

        public Notes getNotes() {
                return notes;
        }

        public void setNotes(Notes notes) {
                this.notes = notes;
        }

        public SoAdd getSo_add() {
                return so_add;
        }

        public void setSo_add(SoAdd so_add) {
                this.so_add = so_add;
        }
}

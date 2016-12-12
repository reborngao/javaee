package com.visabao.machine.util;

/**
 * Created by Administrator on 2016/6/1.
 */
public class SkuUtil {
    public  static    String getPruductName(String spu){
        String title="";
        if (!StringUtil.isEmpty(spu)){
            String []  spukvdescription=   spu.split("-");
            if (spukvdescription.length>15){
                title=spukvdescription[16];// 服务名称
            }
        }

        return title;
    }

    public  static    String getPruductPrice(String sku){
        String []  skukvdescription= sku.split("-");
        if (skukvdescription.length>7){
            if(!StringUtil.isEmpty(skukvdescription[8])){
                return  StringUtil.formatTowDouble(skukvdescription[8]);
            }
        }
        return "0.00";
    }

}

package com.visabao.machine.global;


public class Constant{


    public  static  final  String USER="user";

    public  static  final  String SHOPCART_CODE="shopcart_code";
    public  static  final  String PAY="pay"; //支付信息

    public  static  final  String SO_CONUT="so_count";
    public  static  final  String VISA_TYPE="visa_type";  // 0全球签  1 普通签

    public  static  final  String VISA_TYPE_GLOBAL="0";  // 0全球签
    public  static  final  String VISA_TYPE_VALUE="1";  //  1 普通签

    public  static  final  String SO="so";
    public  static  final  String SO_TOTAL="so_total";

    /***
     *  保存订单信息
     */
    public  static  final  String SHARE_SAVE_ORDER="SHARE_SAVE_ORDER";


    public  static  final  String KEY="pPuruYA3L3kMIICXQIBAAKBgQC8yPzirsHq0D3fXeTwEYizpag+EW2ncctqntldXpzPd";

    public  static  final  String CONSULATE_AIRE="CONSULATE_AIRE";
    public static final String TYPE="type";
    public    enum Type{
        SIGN("SIGN"),              //签到
        GETSKU("GETSKU"),          // 获取国家所有的商品
        SAVESO("SAVESO"),          // 支付完成保存订单信息
        GETCITY("GETCITY"),        // 获取省市区
        CAROUSEL("CAROUSEL"),      // 首页轮播图
        GETSERVICE("GETSERVICE"),  // 获取服务
        VERSION("VERSION"),            //  版本更新
        SMS("SMS"),                // 验证码
        SAVESOINFO("SAVESOINFO"),                //保存订单
        SAVEPAY("SAVEPAY"),                //保存支付信息
        REFUND("REFUND"),                // 根据交 易流水号查询订单列表 退款
        REFUND_RESULT("REFUND_RESULT"),                //  退款
        GETCOUTRY("GETCOUTRY");   // 获取洲和国家

        private final String value;
        Type(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }





    }
}

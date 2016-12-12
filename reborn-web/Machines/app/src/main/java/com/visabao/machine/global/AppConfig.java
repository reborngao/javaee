package com.visabao.machine.global;


public class AppConfig {


    /***
     * 验证码有效期
     */
    public static long CODE_EFFECTIVE_TIMER= 10*60*1000;


    /** 默认下载文件地址. */
    public static String DOWNLOAD_ROOT_DIR = "download";
    /** 默认下载图片文件地址. */
    public static String DOWNLOAD_IMAGE_DIR = "images";

    /** 默认下载文件地址. */
    public static String DOWNLOAD_FILE_DIR = "files";
    /** APP缓存目录. */
    public static String CACHE_DIR = "cache";

    /** 默认 SharePreferences文件名. */
    public static String SHARED_PATH = "visabao_app_share";

    /**  服务器BASEURL. */
  //  public  static  String BASE_URL="http://192.168.1.119:8080/visabao/";   //测试环境
  //public  static  String BASE_URL="http://139.196.232.181/visabao/";   //外网环境

    public  static  String BASE_URL="http://pajj.visabao.com/visabao/";   //外网环境
 // public  static  String BASE_URL="http://192.168.1.215:8080/visabao/";
  //public  static  String BASE_URL="http://192.168.1.154:8080/visabao/";

}

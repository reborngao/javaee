package com.visabao.machine.util;

import android.app.*;
import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.visabao.machine.global.AppConfig;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by Administrator on 2016/4/11.
 */
public class HttpClientHelper {
    /** HTTP请求的Header里设置User-Agent字段，表示请求者的身份信息. */
    private String userAgent="Mozilla/5.0 (Linux; U; Android 1.0; en-us; generic) AppleWebKit/525.10+ (KHTML, like Gecko) Version/3.0.4 Mobile Safari/523.12.2";

    /** 最大连接数. */
    private static final int DEFAULT_MAX_CONNECTIONS = 10;

    /** 超时时间. */
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;

    /** 重试次数. */
    private static final int DEFAULT_MAX_RETRIES = 3;

    /** 超时时间. */
    private int mTimeout = DEFAULT_SOCKET_TIMEOUT;

    /** httpPort 端口**/
    private int HTTP_PORT=80;

    /** httpPort 端口**/
    private int HTTPS_PORT=443;

    private static final int SERVER_PORT = 50030;//端口号
    private static final String SERVER_IP = "218.206.176.146";//连接IP
    private static final String CLIENT_KET_PASSWORD = "123456";//私钥密码
    private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
    private static final String CLIENT_TRUST_MANAGER = "X509";//
    private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";//



    private final static String FORMAT_JSON = "application/json";
    private static final String CHARSET = "UTF-8";
   private  AsyncHttpClient asyncHttpClient;





    private  static  HttpClientHelper  httpClientHelper=null;
    private  Context context;
     private   HttpClientHelper(Context context){
         this.context=context;
         asyncHttpClient=new AsyncHttpClient(true,HTTP_PORT,HTTPS_PORT);
         asyncHttpClient.setUserAgent(userAgent);
         asyncHttpClient.setTimeout(mTimeout);
          new DefaultHttpClient();
     }


    public interface  HttpResponseHandler{
        void  onSuccess(String data);
        void  onFailure();
    }

    public  void post( String url,Map<String ,Object> map , HttpResponseHandler responseHandler) {
        post(url,map,responseHandler,false);
    }

    public  void post(final String url,Map<String ,Object> map ,final HttpResponseHandler responseHandler,final  boolean isShowError) {
        Log.e("URL:" + AppConfig.BASE_URL + url);
        try {
            String json= JsonUtil.toJson(map);
            Log.e("参数:"+json);
            StringEntity stringEntity=new StringEntity(json, CHARSET);
            asyncHttpClient.post(this.context, AppConfig.BASE_URL+url,stringEntity, FORMAT_JSON, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String data, Throwable throwable) {
                    Log.e("请求失败:" + data + "异常信息:" + throwable.getLocalizedMessage());
                    responseHandler.onFailure();
                    if (isShowError){
                        ToastUtil.showToast(HttpClientHelper.this.context, "连接服务器失败!");
                    }

                }

                @Override
                public void onSuccess(int i, Header[] headers, String data) {

                    Log.e(AppConfig.BASE_URL + url + "返回参数:" + data);
                    responseHandler.onSuccess(data);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

   /* public  void post(final String url,RequestParams requestParams,final HttpResponseHandler responseHandler) {
        Log.e("URL:"+ AppConfig.BASE_URL+url);
            asyncHttpClient.post(this.context, AppConfig.BASE_URL+url,requestParams, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String data, Throwable throwable) {
                    Log.e("请求失败:" + data + "异常信息:" + throwable.getLocalizedMessage());
                    responseHandler.onFailure();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String data) {

                    Log.e(AppConfig.BASE_URL + url + "返回参数:" + data);
                    responseHandler.onSuccess(data);
                }
            });
    }*/




    public  void get(final String url,final HttpResponseHandler responseHandler) {
        Log.e("URL:"+ AppConfig.BASE_URL+url);
            asyncHttpClient.get(this.context, AppConfig.BASE_URL+url, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String data, Throwable throwable) {
                    Log.e("请求失败:"+data+"异常信息:"+throwable.getLocalizedMessage());
                    responseHandler.onFailure();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String data) {
                    Log.e(AppConfig.BASE_URL+url+"返回参数:"+data);
                    responseHandler.onSuccess(data);
                }
            });
    }


    public  static  HttpClientHelper  getInstance(Context context){
         if(httpClientHelper==null){
             httpClientHelper=new HttpClientHelper(context);
         }
        return  httpClientHelper;
    }

    public void  requestData() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        //取得SSL的SSLContext实例
        SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
        //取得KeyManagerFactory和TrustManagerFactory的X509密钥管理器实例
        KeyManagerFactory keyManagerFactory= KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);
        TrustManagerFactory trustManager=   TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
        //取得BKS密库实例
        KeyStore kks= KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
        kks.load(new FileInputStream(""),CLIENT_KET_PASSWORD.toCharArray());
        //初始化密钥管理器
        keyManagerFactory.init(kks, CLIENT_KET_PASSWORD.toCharArray());

        KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
        tks.load(new FileInputStream(""), CLIENT_TRUST_PASSWORD.toCharArray());
        trustManager.init(tks);
        sslContext.init(keyManagerFactory.getKeyManagers(),trustManager.getTrustManagers(),null);
    }


}

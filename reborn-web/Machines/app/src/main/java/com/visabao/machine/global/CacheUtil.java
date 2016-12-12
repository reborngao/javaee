package com.visabao.machine.global;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.visabao.machine.util.DESCoder;
import com.visabao.machine.util.FileUtil;
import com.visabao.machine.util.JsonUtil;
import com.visabao.machine.util.Log;
import com.visabao.machine.util.SharedUtil;
import com.visabao.machine.util.StringUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单缓存 防止网络连接断开 订单数据无法送到后台  网络正常情况下Service 上传订单数据
 */
public class CacheUtil {



    public static  File  createFile(Context context) throws IOException {
         File file= new File( FileUtil.getCacheDownloadDir(context)+File.separator+".tmp");
        if (!file.exists()){
            file.createNewFile();
        }
        return file;
    }

    public static void write(Context context, String content) throws Exception{
        FileOutputStream osw =new FileOutputStream(createFile(context));
        osw.write(content.getBytes(), 0, content.length());
        if (osw!=null){
            osw.flush();
            osw.close();
        }
    }


    public static  String read(Context context) throws Exception{
        byte[] b=new byte[1024];//新建一个字节数组
        int hasRead = 0;
        FileInputStream fileInputStream =new FileInputStream(createFile(context));
        StringBuffer stringBuffer=new StringBuffer("");
        while ((hasRead=fileInputStream.read(b))!=-1){
            stringBuffer.append(new String(b,0,hasRead));
        }
        if (fileInputStream!=null){
            fileInputStream.close();
        }
        return  stringBuffer.toString();
    }



    public static  void saveOrderCache(Context context,Map<String ,Object> map){
        try {


         //   String saveOrderDes=new String(DESCoder.decryptBASE64(SharedUtil.getString(context, Constant.SHARE_SAVE_ORDER)),"UTF-8");
            List<Map<String ,Object>> list=getList(read(context));
            Log.e("===========================================>"+list);
            if (list.size()==0){
                list.add(map);
                byte[] outputData= DESCoder.encrypt(JsonUtil.toJson(list).getBytes(),Constant.KEY);
                  write(context, DESCoder.encryptBASE64(outputData));
                Log.e("====>"+list);
            }
            else{
                Log.e("===========================================1>");

                boolean isExist=false;
               for (Map<String,Object> m:list){
                   if (map.get(Constant.SHOPCART_CODE).equals(m.get(Constant.SHOPCART_CODE).toString())){
                       isExist=true;
                   }
               }
                if (!isExist){
                    list.add(map);
                    byte[] outputData= DESCoder.encrypt(JsonUtil.toJson(list).getBytes(),Constant.KEY);
                    write(context,  DESCoder.encryptBASE64(outputData));
                }
            }
        }catch (Exception e){
                e.printStackTrace();
        }
    }

    public  static  void  saveOrderList(Context context,List<Map<String ,Object>> list){
        byte[] outputData= new byte[0];
        try {
            outputData = DESCoder.encrypt(JsonUtil.toJson(list).getBytes(), Constant.KEY);
            write(context, DESCoder.encryptBASE64(outputData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  static   List<Map<String ,Object>> getList(String saveOrderDes) throws Exception {
        if (!StringUtil.isEmpty(saveOrderDes)){
            byte[] inputData= DESCoder.decrypt(DESCoder.decryptBASE64(saveOrderDes), Constant.KEY);
            String inputStr = new String(inputData,"UTF-8");
            return  JsonUtil.fromJson(inputStr,new TypeToken<ArrayList<Map<String ,Object>>>(){});
        }
        return new ArrayList<Map<String, Object>>();
    }

    public static  void removeOrderCache(Context context,String cardid){

        try {
            String saveOrderDes= read(context);
            List<Map<String ,Object>>  list=getList(saveOrderDes);
            if (list!=null){
                List<Map<String ,Object>>  newList=getList(saveOrderDes);
                for (Map<String,Object> m:list){
                    if (!cardid.equals(m.get(Constant.SHOPCART_CODE))){
                        newList.add(m);
                    }
                }
                if (newList.size()>0){
                    byte[] outputData= DESCoder.encrypt(JsonUtil.toJson(newList).getBytes(),Constant.KEY);
                    write(context,  DESCoder.encryptBASE64(outputData));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

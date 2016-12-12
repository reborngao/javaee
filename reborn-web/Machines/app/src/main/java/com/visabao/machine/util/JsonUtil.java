package com.visabao.machine.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 描述：json处理类.
 */
public class JsonUtil {

    /**
     * 描述：将对象转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(Object src) {
        String json = null;
        try {
            json = getGson().toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 描述：将列表转化为json.
     *
     * @param list
     * @return
     */
    public static String toJson(List<?> list) {
        String json = null;
        try {
            json = getGson().toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Gson getGson() {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        return gson;
    }


    /**
     * 描述：将json转化为列表.
     *
     * @param json
     * @param typeToken new TypeToken<ArrayList<?>>() {}.getType();
     * @return
     */
    public static <T> T fromJson(String json, TypeToken typeToken) {

        try {
             Type type = typeToken.getType();
          return  getGson().fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 描述：将json转化为对象.
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            return (T) gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

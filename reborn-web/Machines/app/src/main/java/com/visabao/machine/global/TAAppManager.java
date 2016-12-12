package com.visabao.machine.global;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
     * activity 堆栈管理类
     * @author liugaoqiao
     *
     */
    public class TAAppManager {
        	private static Stack<Activity> activityStack;
        private static TAAppManager instance;
        private static Activity lastActivity;
        private static Map<String, SoftReference<Activity>> activityMap = new ConcurrentHashMap<String, SoftReference<Activity>>(50);

        private TAAppManager() {

        }

        /**
         * 单一实例
         */
        public static TAAppManager getAppManager() {
            if (instance == null) {
                instance = new TAAppManager();
            }
            return instance;
        }

        /**
         * 添加Activity到堆栈
         */
        public void addActivity(Activity activity) {
            lastActivity = activity;
            SoftReference softReference = new SoftReference(activity);
            activityMap.put(activity.getClass().getName(), softReference);
        }


        public Activity getActivity(String clzName) {
            SoftReference<Activity> softReference = activityMap.get(clzName);
            return softReference == null ? null : softReference.get();
        }

        public <T> T getActivity(Class<T> clz) {
            SoftReference<Activity> softReference = activityMap.get(clz.getName());
            return softReference == null ? null : (T) softReference.get();
        }

        public Activity getLastActivity() {
            return lastActivity;
        }

        /**
         * 结束当前Activity（堆栈中最后一个压入的）
         */
        public void finishActivity() {
            finishActivity(lastActivity);
        }

        /**
         * 结束指定的Activity
         */
        public void finishActivity(Activity activity) {
            if (activity != null) {
                activityMap.remove(activity.getClass().getName());
                activity.finish();
                activity = null;
            }
        }

        /**
         * 移除指定的Activity
         */
        public void removeActivity(Activity activity) {
            if (activity != null) {
                activityMap.remove(activity.getClass().getName());
            }
        }

        /**
         * 结束指定类名的Activity
         */
        public void finishActivity(Class<?> cls) {
            for (SoftReference<Activity> softReference : activityMap.values()) {
                if (softReference.get().getClass().equals(cls)) {
                    finishActivity(softReference.get());
                }
            }
        }

        /**
         * 结束所有Activity
         */
        public void finishAllActivity() {
            for (SoftReference<Activity> softReference : activityMap.values()) {
                finishActivity(softReference.get());
            }
            activityMap.clear();
            lastActivity = null;
        }

        /**
         * 退出应用程序
         *
         * @param context      上下文
         */
        public void appExit(Context context) {
            try {

                finishAllActivity();
                ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                activityMgr.restartPackage(context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }



    }

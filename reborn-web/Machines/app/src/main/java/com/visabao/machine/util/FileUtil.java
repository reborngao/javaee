package com.visabao.machine.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Environment;


import com.visabao.machine.global.AppConfig;

import java.io.File;

/**
 * Sd卡工具类
 * @author liugaoqiao
 *
 */
public class FileUtil {
	
	  public static final String END_TEMP="END_TEMP";
	
	  public static final String SAFY_ANDROID="visabao";

		/** 默认APP根目录. */
		private static String downloadRootDir = null;

		/** 默认下载图片文件目录. */
		private static String imageDownloadDir = null;

		/** 默认下载文件目录. */
		private static String fileDownloadDir = null;

		/** 默认缓存目录. */
		private static String cacheDownloadDir = null;


	/**
	 * 描述：SD卡是否能用.
	 *
	 * @return true 可用,false不可用
	 */
	public static boolean isCanUseSD() {
		try {
			return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	/**
	 *   下载图片文件目录
	 * @param context
	 * @return
	 */
	public static String getImageDownloadDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return imageDownloadDir;
	}

	/**
	 * 　　获取　默认下载目录
	 * @param context
	 * @return
	 */
	public static String getDownloadRootDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return downloadRootDir;
	}

	/**
	 *   获取文件下载目录
	 * @param context
	 * @return
	 */
	public static String getFileDownloadDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return fileDownloadDir;
	}

	/**
	 * 获取默认缓存目录
	 * @param context
	 * @return
	 */
	public static String getCacheDownloadDir(Context context) {
		if(downloadRootDir == null){
			initFileDir(context);
		}
		return cacheDownloadDir;
	}

		/***
		 * 删除所有缓存文件.
		 * @return   true  是成功
		 */
		public static boolean clearDownloadFile(){
			try {
				File fileDirectory = new File(downloadRootDir);
				deleteFile(fileDirectory);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	/**
	 * 删除文件.
	 * @param file
	 * @return  true  是成功
	 */
		public static boolean deleteFile(File file) {

			try {
				if(!isCanUseSD()){
					return false;
				}
				if (file == null) {
					return true;
				}
				if(file.isDirectory()){
					File[] files = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFile(files[i]);
					}
				}else{
					file.delete();
				}

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}





	/**
	 * 描述：初始化存储目录.
	 *
	 * @param context     上下文
	 */
	public static void initFileDir(Context context){
		PackageInfo info = AppUtil.getPackageInfo(context);
		//默认下载文件根目录.
		String downloadRootPath = File.separator + AppConfig.DOWNLOAD_ROOT_DIR + File.separator + info.packageName + File.separator;

		//默认下载图片文件目录.
		String imageDownloadPath = downloadRootPath + AppConfig.DOWNLOAD_IMAGE_DIR + File.separator;

		//默认下载文件目录.
		String fileDownloadPath = downloadRootPath + AppConfig.DOWNLOAD_FILE_DIR + File.separator;

		//默认缓存目录.
		String cacheDownloadPath = downloadRootPath + AppConfig.CACHE_DIR + File.separator;

		try {
			if (!isCanUseSD()){
				return;
			}
			else {
				File root = Environment.getExternalStorageDirectory();
				File downloadDir = new File(root.getAbsolutePath() + downloadRootPath);
				if(!downloadDir.exists()){
					downloadDir.mkdirs();
				}
				downloadRootDir = downloadDir.getPath();

				File cacheDownloadDirFile = new File(root.getAbsolutePath() + cacheDownloadPath);
				if(!cacheDownloadDirFile.exists()){
					cacheDownloadDirFile.mkdirs();
				}
				cacheDownloadDir = cacheDownloadDirFile.getPath();

				File imageDownloadDirFile = new File(root.getAbsolutePath() + imageDownloadPath);
				if(!imageDownloadDirFile.exists()){
					imageDownloadDirFile.mkdirs();
				}
				imageDownloadDir = imageDownloadDirFile.getPath();

				File fileDownloadDirFile = new File(root.getAbsolutePath() + fileDownloadPath);
				if(!fileDownloadDirFile.exists()){
					fileDownloadDirFile.mkdirs();
				}
				fileDownloadDir = fileDownloadDirFile.getPath();
			}

		}catch (Exception e){
			e.printStackTrace();
		}
	}

}

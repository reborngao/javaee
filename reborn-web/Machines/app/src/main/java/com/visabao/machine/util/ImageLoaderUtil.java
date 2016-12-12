package com.visabao.machine.util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


/***
 *   ImageLoader 工具类
 * @author liugaoqiao
 *
 */
public class ImageLoaderUtil {

	public static DisplayImageOptions getDisplayImageOptions (){
		 return  new DisplayImageOptions.Builder()
		// 设置图片加载/解码过程中错误时候显示的图片
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true)
		// 设置下载的图片是否缓存在SD卡中
		.considerExifParams(false)
		// 是否考虑JPEG图像EXIF参数（旋转，翻转）
		.imageScaleType(ImageScaleType.EXACTLY)
		// 设置图片以如何的编码方式显示
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	
	
	
	/**
	 *  方形
	 */
	public static DisplayImageOptions getDisplayFilletImageOptionsDisplayer(int defalutHeight){
		return new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(50)).build();
	}
	
	
	
	
	
	
	/***
	 * 
	 *    获取 DisplayImageOptions  实例    
	 * @param defalutResId    默认图片的资源id
	 * @return
	 */
	public static DisplayImageOptions getDisplayImageOptions (int defalutResId){
		 return  new DisplayImageOptions.Builder()
		.showImageOnLoading(defalutResId)// 设置图片在下载期间显示的图片
		.showImageForEmptyUri(defalutResId)// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(defalutResId)
		// 设置图片加载/解码过程中错误时候显示的图片
		.cacheInMemory(true)
		// 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true)
		// 设置下载的图片是否缓存在SD卡中
		.considerExifParams(true)
		// 是否考虑JPEG图像EXIF参数（旋转，翻转）
		.imageScaleType(ImageScaleType.EXACTLY)
		// 设置图片以如何的编码方式显示
		.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	/**
	 *  方形
	 */
	public static DisplayImageOptions getDisplayFilletImageOptions(int defalutResId){
		return new DisplayImageOptions.Builder()
		.showImageOnLoading(defalutResId)// 设置图片在下载期间显示的图片
		.showImageForEmptyUri(defalutResId)// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(defalutResId)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(6)).build();
	}
	public static DisplayImageOptions getDisplayRoundedBitmapOptions(int rounde,int defalutResId){
		return new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.showImageOnLoading(defalutResId)// 设置图片在下载期间显示的图片
		.showImageForEmptyUri(defalutResId)// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(defalutResId)
		.considerExifParams(true)
		.imageScaleType(ImageScaleType.EXACTLY)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(rounde)).build();
	}
	
}

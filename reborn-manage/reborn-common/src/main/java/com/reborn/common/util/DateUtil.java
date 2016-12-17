package com.reborn.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 
* @Title 

* @Description: 日期处理

* @author reborngao  Email 460600117@qq.com

* @CreateTime:2016年12月17日 下午4:26:41
 
* @version V1.0
 */
public class DateUtil {
	/**
	 *  yyyy  如:2016
	 */
	private final static String DATE_YEAR_PATTERN = "yyyy";
	/**
	 * 时间格式 yyyy-MM-dd 时间日期格式化到年月日
	 */
	private final static String DATE_PATTERN ="yyyy-MM-dd";
	/**
	 * 时间格式yyyyMMdd
	 */
	private final static String DATE_DAY_PATTERN = "yyyyMMdd";
	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss  时间日期格式化到年月日时分秒
	 */
	private final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 时间格式yyyyMMddHHmmss  时间日期格式化到年月日时分秒
	 */
	private final static String DATE_TIMES_PATTERN ="yyyyMMddHHmmss";
	/**
	 * 时间格式 HH:mm:ss 时分秒
	 */
	public static final String TIME_PATTERN = "HH:mm:ss";
	/**
	 * 时间格式 yyyy-MM-dd EEEE
	 */
	public static final String DATE_PATTERN_WEEK = "yyyy-MM-dd EEEE";
	
	/**
	 * 
	 * @Title: format   
	 * @Description: 格式化日期  
	 * @param: @param date   时间
	 * @param: @param pattern  格式
	 * @param: @return      
	 * @return: String
	 */
	public static String format(Date date, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}
	/**
	 * 
	 * @Title: formartDate   
	 * @Description: 日期格式: yyyy-MM-dd 
	 * @param: @param date
	 * @param: @return      
	 * @return: String      
	 */
	public static String formartDate(Date date) {
		return format(date, DATE_PATTERN);
	}
	/**
	 * 
	 * @Title: formartTime   
	 * @Description: 时间格式 HH:mm:ss 时分秒  
	 * @param: @param date
	 * @param: @return      
	 * @return: String      
	 */
	public static String formartTime(Date date) {
		return format(date, TIME_PATTERN);
	}
	/**
	 * 
	 * @Title: formartDateTime   
	 * @Description: 时间格式yyyy-MM-dd HH:mm:ss 时间日期格式化到年月日时分秒    
	 * @param: @param date
	 * @param: @return      
	 * @return: String
	 */
	public static String formartDateTime(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}
	/**
	 * 
	 * @Title: formatCurrent   
	 * @Description: 格式化当前时间   
	 * @param: @param pattern   日期格式
	 * @param: @return      
	 * @return: String      
	 */
	public static String formatCurrent(String pattern) {
		return format(new Date(), pattern);
	}
	/**
	 * 
	 * @Title: formartCurrentDate   
	 * @Description:  格式化当前时间     
	 * @param: @return      
	 * @return: String      yyyy-MM-dd
	 */
	public static String formartCurrentDate() {
		return format(new Date(), DATE_PATTERN);
	}
	/**
	 * 
	 * @Title: formartCurrentTime   
	 * @Description: 格式化当前时间  
	 * @param: @return      
	 * @return: String  字符串格式  HH:mm:ss
	 */
	public static String formartCurrentTime() {
		return format(new Date(), TIME_PATTERN);
	}
	/**
	 * 
	 * @Title: formartCurrentDateTime   
	 * @Description: 格式化当前时间 
	 * @param: @return         
	 * @return: String 时间格式yyyy-MM-dd HH:mm:ss 时间日期格式化到年月日时分秒
	 */
	public static String formartCurrentDateTime() {
		return format(new Date(), DATE_TIME_PATTERN);
	}
}

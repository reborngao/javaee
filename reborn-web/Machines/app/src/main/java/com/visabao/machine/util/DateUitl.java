package com.visabao.machine.util;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUitl {

	/**
	 * 时间格式 yyyy-MM-dd 时间日期格式化到年月日
	 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss  时间日期格式化到年月日时分秒
	 */
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss SSS
	 */
	public static final String DATE_TIME_MILLISECOND_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	

	/**
	 * 时间格式 HH:mm:ss 时分秒
	 */
	public static final String TIME_PATTERN = "HH:mm:ss";

	/**
	 * 时间格式 yyyy-MM-dd EEEE
	 */
	public static final String DATE_PATTERN_WEEK = "yyyy-MM-dd EEEE";

	/**
	 * 获取时间 小时和分钟 12:00
	 * 
	 * @return
	 */
	public static String getHourAndMinute() {
		Calendar calendar = Calendar.getInstance();
		return (calendar.get(Calendar.HOUR) < 9 ? ("0" + calendar
				.get(Calendar.HOUR)) : +calendar.get(Calendar.HOUR))
				+ ":"
				+ (calendar.get(Calendar.MINUTE) < 9 ? ("0" + calendar
						.get(Calendar.MINUTE)) : +calendar.get(Calendar.MINUTE));
	}

	
	
	public static String getMonthAndDay(long l) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(l);
		return dateFormat.format(calendar.getTime());
	}

	public static String format(long millis, String pattern) {
		return format(new Date(millis), pattern);
	}

	public static String format(Date date, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(date);
	}

	public static String formartDate(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	public static String formartTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String formartDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	public static String formatCurrent(String pattern) {
		return format(new Date(), pattern);
	}

	public static String formartCurrentDate() {
		return format(new Date(), "yyyy-MM-dd");
	}

	public static String formartCurrentTime() {
		return format(new Date(), "HH:mm:ss");
	}

	public static String formartCurrentDateTime() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	

	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static Date getTheDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);
		return cal.getTime();
	}

	public static int compareDate(Date start, Date end) {
		if ((start == null) && (end == null))
			return 0;
		if (end == null)
			return 1;
		if (start == null) {
			start = new Date();
		}
		start = getTheDate(start);
		end = getTheDate(end);
		return start.compareTo(end);
	}

	public static Date parse(String dateString, String pattern) {
		if (pattern.contains(":start")) {
			pattern = pattern.replace(":start", "HH:mm:ss");
			dateString = dateString + " 00:00:00";
		}
		if (pattern.contains(":end")) {
			pattern = pattern.replace(":end", "HH:mm:ss");
			dateString = dateString + " 23:59:59";
		}
		DateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date addYears(Date date, int amount) {
		return add(date, 1, amount);
	}

	public static Date addMonths(Date date, int amount) {
		return add(date, 2, amount);
	}

	public static Date addWeeks(Date date, int amount) {
		return add(date, 3, amount);
	}

	public static Date addDays(Date date, int amount) {
		return add(date, 5, amount);
	}

	public static Date addHours(Date date, int amount) {
		return add(date, 11, amount);
	}

	public static Date addMinutes(Date date, int amount) {
		return add(date, 12, amount);
	}

	public static Date addSeconds(Date date, int amount) {
		return add(date, 13, amount);
	}

	public static Date addMilliseconds(Date date, int amount) {
		return add(date, 14, amount);
	}

	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("日期对象不允许为null!");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarField, amount);
		return c.getTime();
	}

	/*****
	 * 获取当月天总数
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return
	 */
	public static int getMonthMaxDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);
		return calendar.get(Calendar.DATE);// //日期回滚一天，也就是最后一天
	}

	/****
	 * 获取当前月份1号星期几
	 * 
	 * @param year
	 *            年份
	 * @param month
	 *            月份
	 * @return
	 */
	public static int getWeek(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	
	
	
    /** 
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前 
     *  
     * @param timeStamp 
     * @return 
     */ 
    public static String convertTimeToFormat(long timeStamp) {
            long curTime = System.currentTimeMillis() / (long) 1000 ;
            long time = curTime - timeStamp;  

            if (time < 60 && time >= 0) {  
                    return "刚刚";  
            } else if (time >= 60 && time < 3600) {  
                    return time / 60 + "分钟前";  
            } else if (time >= 3600 && time < 3600 * 24) {  
                    return time / 3600 + "小时前";  
            } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {  
                    return time / 3600 / 24 + "天前";  
            } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {  
                    return time / 3600 / 24 / 30 + "个月前";  
            } else if (time >= 3600 * 24 * 30 * 12) {  
                    return time / 3600 / 24 / 30 / 12 + "年前";  
            } else {  
                    return "刚刚";  
            }  
    } 
	

	/**
	 * 获取文本星期几  如: 星期日
	 * @param week
	 * @return
	 */
	private static String getWeekStr(int week) {
		String weekStr = "";
		switch (week) {
		case 0:
			weekStr = "星期日";
			break;
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
			break;
		}
		return weekStr;
	}

}

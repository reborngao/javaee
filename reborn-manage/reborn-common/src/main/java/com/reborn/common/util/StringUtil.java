package com.reborn.common.util;

/**
 * 常用工具
 *
 */
public class StringUtil {
	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}
	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}
	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * @Title: str2StrArray   
	 * @Description: 
	 * @param: @param 字符串 \\s*
	 * @param: @return      
	 * @return: String[]
	 */
	public static String[] str2StrArray(String str){
		return str2StrArray(str,",\\s*");
	}
	/**
	 * 字符串转换为字符串数组
	 * @Title: str2StrArray   
	 * @Description:    
	 * @param: @param str 字符串
	 * @param: @param splitRegex 分隔符
	 * @param: @return      
	 * @return: String[]
	 */
	public static String[] str2StrArray(String str,String splitRegex){
		if(isEmpty(str)){
			return null;
		}
		return str.split(splitRegex);
	}
	
}

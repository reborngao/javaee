package com.reborn.common.util;

import java.util.Map;

/**
 * 
* @Title AppUtil

* @Description: 接口参数校验

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月17日 下午5:04:32
 
* @version V1.0
 */
public class AppUtil {
	protected static Logger logger = Logger.getLogger(AppUtil.class);
	
	/**
	 * 
	 * @Title: returnObject   
	 * @Description: 接口返回对象  
	 * @param: @param pd
	 * @param: @param map
	 * @param: @return      
	 * @return: Object
	 */
	public static Object returnObject(PageData pd,Map map){
		return map;
	}
}

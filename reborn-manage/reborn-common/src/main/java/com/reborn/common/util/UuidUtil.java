package com.reborn.common.util;

import java.util.UUID;


/**
 * UUID生成类
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月28日 下午9:05:32
 
* @version V1.0
 */
public class UuidUtil {
	
	/**
	 *  获取UUID
	 * @Title: get32UUID   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: String
	 */
	public static String get32UUID(){
		String uuid=UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}

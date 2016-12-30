package com.reborn.common.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


public class Jurisdiction {
	/**shiro管理的session
	 * @return
	 */
	public static Session getSession(){
		//Subject currentUser = SecurityUtils.getSubject();  
		return SecurityUtils.getSubject().getSession();
	}
	/**
	 * 获取当前登录的用户名
	 * @Title: getUserName   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: String
	 */
	public static String getUserName(){
		return getSession().getAttribute(Const.SESSION_USER).toString();
	}
}

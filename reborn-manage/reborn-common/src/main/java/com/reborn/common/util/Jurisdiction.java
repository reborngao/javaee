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
}

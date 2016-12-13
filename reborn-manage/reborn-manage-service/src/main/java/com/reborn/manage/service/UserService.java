package com.reborn.manage.service;

import java.util.Map;

import com.reborn.common.util.PageData;



public interface UserService {
	
	/**
	 * 
	 * @Title: getUserByNameAndPwd   
	 * @Description: 登录判断
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	public Map<String, Object> getUserByNameAndPwd(PageData pd)throws Exception;
}

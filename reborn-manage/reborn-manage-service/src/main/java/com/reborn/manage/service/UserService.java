package com.reborn.manage.service;

import java.util.Map;

import com.reborn.common.util.PageData;



public interface UserService {

	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserByNameAndPwd(PageData pd)throws Exception;
}

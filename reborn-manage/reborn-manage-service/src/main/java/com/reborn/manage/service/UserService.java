package com.reborn.manage.service;

import java.util.Map;



public interface UserService {

	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserByNameAndPwd(Map<String, Object> pd)throws Exception;
}

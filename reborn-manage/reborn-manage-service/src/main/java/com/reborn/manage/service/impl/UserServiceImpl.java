package com.reborn.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reborn.common.util.PageData;
import com.reborn.dao.DaoSupport;
import com.reborn.manage.service.UserService;


/**
 * 
* @Title 系统用户

* @Description: 

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月13日 下午10:14:42
 
* @version V1.0
 */
@Service
public class UserServiceImpl  implements  UserService{

	
	@Autowired
	DaoSupport  daoSupport;
	
	/**
	 * 
	 * <p>Title: getUserByNameAndPwd</p>   
	 * <p>Description:登录判断 </p>   
	 * @param pd
	 * @return
	 * @throws Exception   
	 * @see com.reborn.manage.service.UserService#getUserByNameAndPwd(com.reborn.common.util.PageData)
	 */
	public Map<String, Object> getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("UserMapper.getUserInfo", pd);
	}
}

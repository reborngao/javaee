package com.reborn.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reborn.common.util.PageData;
import com.reborn.dao.DaoSupport;
import com.reborn.manage.service.UserService;
import com.reborn.pojo.User;


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
	public User getUserByNameAndPwd(PageData pd) throws Exception {
		User user=  daoSupport.findForObject("UserMapper.getUserInfo", pd);
		if(user!=null){
			return user;
		}
		return null;
	}

	/**
	 * 
	 * <p>Title: updateLastLogin</p>   
	 * <p>Description:更新登录时间 </p>   
	 * @param data   
	 * @see com.reborn.manage.service.UserService#updateLastLogin(com.reborn.common.util.PageData)
	 */
	public void updateLastLogin(PageData pd) {
		daoSupport.update("UserMapper.updateLastLogin", pd);
		
	}
	/**
	 * 
	 * <p>Title: getUserAndRoleById</p>   
	 * <p>Description: 通过用户ID获取用户信息和角色信息 </p>   
	 * @param userId
	 * @return   
	 * @throws Exception 
	 * @see com.reborn.manage.service.UserService#getUserAndRoleById(java.lang.String)
	 */
	public User getUserAndRoleById(String userId) throws Exception {
		return (User) daoSupport.findForObject("UserMapper.getUserAndRoleById", userId);
	}
}

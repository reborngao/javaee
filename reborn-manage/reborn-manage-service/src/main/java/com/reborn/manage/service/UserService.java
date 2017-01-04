package com.reborn.manage.service;

import java.util.List;

import com.reborn.common.util.PageData;
import com.reborn.pojo.User;


/**
 * 用户接口类
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2017年1月4日 下午4:50:18
 
* @version V1.0
 */
public interface UserService {
	
	/**
	 * 
	 * @Title: getUserByNameAndPwd   
	 * @Description: 登录判断 
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: User      
	 */
	public User getUserByNameAndPwd(PageData user)throws Exception;
	
	/**
	 * 
	 * @Title: updateLastLogin   
	 * @Description: 更新登录时间  
	 * @param: @param pd      
	 * @return: void      
	 */
	public void updateLastLogin(PageData pd);
	/**
	 * 
	 * @Title: getUserAndRoleById   
	 * @Description: 通过用户ID获取用户信息和角色信息 
	 * @param: @param userId
	 * @param: @return      
	 * @return: User
	 */
	public User getUserAndRoleById(String userId)throws Exception;
	/**
	 * 
	 * @Title: listUsers   
	 * @Description: 用户列表
	 * @param: @return      
	 * @return: List<User>      
	 */
	public List<User> listUsers();
}

package com.reborn.manage.service;

import java.util.List;

import com.reborn.common.util.PageData;
import com.reborn.pojo.User;



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
	 * 用户列表
	 * @Title: listUsers   
	 * @Description: 
	 * @param: @return      
	 * @return: List<User>
	 */
	public List<User>  listUsers();
}

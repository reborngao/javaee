package com.reborn.manage.service;

import java.util.List;

import com.reborn.pojo.Role;


/**
 * 角色接口类
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月29日 下午2:20:57
 
* @version V1.0
 */
public interface RoleService {
	
	/**
	 * 添加
	 * @Title: add   
	 * @Description: 
	 * @param: @param role      
	 * @return: void
	 */
	public void add(Role role);
	
	
	/**
	 * 列出此组下级角色
	 * @Title: listAllRolesByPId   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param parentId
	 * @param: @return      
	 * @return: List<Role>
	 */
	public List<Role> listAllRolesByPId(String parentId);

	/**
	 * 通过id查找
	 * @Title: getRoleById   
	 * @Description:   
	 * @param: @param roleId
	 * @param: @return      
	 * @return: Role
	 */
	public Role getRoleById(String roleId);

	/**
	 * 给当前角色附加菜单权限
	 * @Title: updateRoleRights   
	 * @Description:  
	 * @param: @param role      
	 * @return: void
	 */
	public void updateRoleRights(Role role);
}

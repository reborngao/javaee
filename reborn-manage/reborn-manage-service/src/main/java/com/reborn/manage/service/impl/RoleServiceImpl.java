package com.reborn.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reborn.common.util.StringUtil;
import com.reborn.common.util.UuidUtil;
import com.reborn.dao.DaoSupport;
import com.reborn.manage.service.RoleService;
import com.reborn.pojo.Role;

/****
 * 角色接口类
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月28日 下午9:20:46
 
* @version V1.0
 */
@Service
public class RoleServiceImpl  implements RoleService{

	@Autowired
	DaoSupport daoSupport;
	public void add(Role role) {
		role.setRoleId(UuidUtil.get32UUID());
		if(StringUtil.isEmpty(role.getParentId())){
			role.setParentId("0");
		}
		daoSupport.save("RoleMapper.insert", role);
	}
	/**
	 * 列出此组下级角色
	 * <p>Title: listAllRolesByPId</p>   
	 * <p>Description: </p>   
	 * @param parentId
	 * @return   
	 * @see com.reborn.manage.service.RoleService#listAllRolesByPId(java.lang.String)
	 */
	public List<Role> listAllRolesByPId(String parentId) {
		// TODO Auto-generated method stub
		return daoSupport.findForList("RoleMapper.listAllRolesByPId", parentId);
	}
	
	/**
	 * 通过id查找
	 * <p>Title: getRoleById</p>   
	 * <p>Description: </p>   
	 * @param roleId
	 * @return   
	 * @see com.reborn.manage.service.RoleService#getRoleById(java.lang.String)
	 */
	public Role getRoleById(String roleId) {
		return daoSupport.findForObject("RoleMapper.getRoleById", roleId);
	}
	
	/**
	 * *给全部子角色加菜单权限
	 * <p>Title: updateRoleRights</p>   
	 * <p>Description: </p>   
	 * @param role   
	 * @see com.reborn.manage.service.RoleService#updateRoleRights(com.reborn.pojo.Role)
	 */
	public void updateRoleRights(Role role) {
		daoSupport.update("RoleMapper.updateRoleRights", role);
		
	}
}

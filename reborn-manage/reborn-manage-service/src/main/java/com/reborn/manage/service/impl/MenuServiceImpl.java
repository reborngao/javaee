package com.reborn.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.reborn.dao.DaoSupport;
import com.reborn.manage.service.MenuService;
import com.reborn.pojo.Menu;

/**
 * 
* @Title 

* @Description: 类名称：MenuService 菜单处理

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月15日 下午2:11:08
 
* @version V1.0
 */
public class MenuServiceImpl implements MenuService {

	@Autowired
	DaoSupport daoSupport;
	/**
	 * 
	 * <p>Title: listSubMenuByParentId</p>   
	 * <p>Description: </p>   
	 * @param parentId
	 * @return   
	 * @see com.reborn.manage.service.MenuService#listSubMenuByParentId(java.lang.String)
	 */
	public List<Menu> listAllMenuQx(String parentId) {
		return daoSupport.findForList("listSubMenuByParentId", parentId);
	}

}

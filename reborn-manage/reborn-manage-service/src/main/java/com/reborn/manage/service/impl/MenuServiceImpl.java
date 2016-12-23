package com.reborn.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	DaoSupport daoSupport;
	/**
	 * 获取所有菜单并填充每个菜单的子菜单列表(菜单管理)(递归处理)
	 * <p>Title: listSubMenuByParentId</p>   
	 * <p>Description: </p>   
	 * @param parentId
	 * @return   
	 * @see com.reborn.manage.service.MenuService#listSubMenuByParentId(java.lang.String)
	 */
	public List<Menu> listAllMenuQx(String parentId) {
		List<Menu> menuList= this.listSubMenuByParentId(parentId);
		for(Menu menu : menuList){
			menu.setMenuUrl("menu/toEidt?menuId="+menu.getMenuId());
			menu.setSubMenu(this.listAllMenuQx(menu.getMenuId()));
			menu.setTarget("treeFrame");
		}
		
		return menuList;
	}
	/**
	 * 通过ID获取其子一级菜单
	 * <p>Title: listSubMenuByParentId</p>   
	 * <p>Description: </p>   
	 * @param parentId
	 * @return   
	 * @see com.reborn.manage.service.MenuService#listSubMenuByParentId(java.lang.String)
	 */
	public List<Menu> listSubMenuByParentId(String parentId) {
		return daoSupport.findForList("MenuMapper.listSubMenuByParentId", parentId);
	}
	
	/**
	 * 新增菜单
	 * <p>Title: saveMenu</p>   
	 * <p>Description: </p>   
	 * @param menu   
	 * @see com.reborn.manage.service.MenuService#saveMenu(com.reborn.pojo.Menu)
	 */
	public void saveMenu(Menu menu) {
		// TODO Auto-generated method stub
		daoSupport.save("MenuMapper.insertMenu",menu);	
	}
	public Menu getMenuById(String menuId) {
		return daoSupport.findForObject("MenuMapper.getMenuById", menuId);
	}
}

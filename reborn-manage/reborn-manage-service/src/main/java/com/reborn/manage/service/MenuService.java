package com.reborn.manage.service;

import java.util.List;

import org.omg.CORBA.Object;

import com.reborn.pojo.Menu;



/**
 * 
* @Title 

* @Description: 菜单处理接口

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月15日 下午2:09:41
 
* @version V1.0
 */
public interface MenuService  {
	public List<Menu> listAllMenuQx(String parentId) ; 
	
	public List<Menu> listSubMenuByParentId(String parentId);

	public void saveMenu(Menu menu);

	public Menu getMenuById(String menuId);
}

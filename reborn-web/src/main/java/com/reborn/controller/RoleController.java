package com.reborn.controller;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.base.BaseController;
import com.reborn.common.util.JsonUtil;
import com.reborn.common.util.Jurisdiction;
import com.reborn.common.util.PageData;
import com.reborn.common.util.RightsHelper;
import com.reborn.common.util.StringUtil;
import com.reborn.manage.service.MenuService;
import com.reborn.manage.service.RoleService;
import com.reborn.pojo.Menu;
import com.reborn.pojo.Role;


/***
 * 角色权限管理
* @Title   

* @Description: 

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月23日 下午11:16:22
 
* @version V1.0
 */

@Controller
@RequestMapping("/role")
public class RoleController  extends BaseController{
	
	
	@Autowired
	RoleService roleService;
	
	
	@Autowired
	MenuService menuService;
	
	/**
	 * 进入权限首页
	 * @Title: list   
	 * @Description:  
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping
	public ModelAndView list(@RequestParam(defaultValue="1")String roleId){
		ModelAndView modelAndView=getModelAndView();
		List<Role> roles= roleService.listAllRolesByPId("0"); //列出组(页面横向排列的一级组)
		List<Role> roleListZ =roleService.listAllRolesByPId(roleId);  ////列出此组下架角色
		modelAndView.addObject("roles", roles);
		modelAndView.addObject("roleId", roleId);
		modelAndView.addObject("roleListZ", roleListZ);
		modelAndView.setViewName("system/role/role_list");
		return modelAndView;
	}
	
	/**
	 * 去新增页面
	 * @Title: toAdd   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ModelAndView
	 */
	
	@RequestMapping("toAdd")
	public ModelAndView toAdd(Role role){
		ModelAndView mv = this.getModelAndView();
		 mv.addObject("parentId", role.getParentId());
		mv.setViewName("system/role/role_edit");
		return mv;
	}
	
	
	/**
	 * 保存新增角色
	 * @Title: add   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add(Role role){
		 ModelAndView mv=  getModelAndView();
		 logBefore(logger, Jurisdiction.getUserName()+"新增角色");
		 roleService.add(role);
		 mv.setViewName("save_result");
		 return mv;
	}
	
	
	/**
	 * 显示菜单列表ztree(菜单授权菜单)
	 * @Title: listAllMenu   
	 * @Description:    
	 * @param: @param roleId
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping("menuqx")
	public ModelAndView listAllMenu(String roleId){
		ModelAndView mv=  getModelAndView();
		List<Menu> menus=	  menuService.listAllMenuQx("0");
		Role role = roleService.getRoleById(roleId);			//根据角色ID获取角色对象
		this.readMenu(menus, role.getRights());	
		 String json=JsonUtil.objectToJson(menus);
			logger.info(json);
			json=json.replaceAll("menuName", "name").replaceAll("menuId", "id")
					.replaceAll("menuUrl", "url").replaceAll("parentId", "pId")
					.replaceAll("subMenu", "children").replaceAll("hasMenu", "checked");
			logger.info(json);
		mv.addObject("zTreeNodes", json);
		mv.addObject("roleId", roleId);
		mv.setViewName("system/role/menuqx");
		 return mv;
	}
	
  @RequestMapping(value="saveMenuqx",method=RequestMethod.POST)
  public void saveMenuqx(@RequestParam String roleId,@RequestParam String menuIds,PrintWriter out){
	  try{
		  logBefore(logger, Jurisdiction.getUserName()+"修改菜单权限");
		  if(StringUtil.notEmpty(menuIds)){
			  BigInteger  bigInteger=  RightsHelper.sumRights(StringUtil.str2StrArray(menuIds));
			  Role role = roleService.getRoleById(roleId);	//通过id获取角色对象
			  role.setRights(bigInteger.toString());
			  roleService.updateRoleRights(role);
		  }
		  else{
			  	Role role = new Role();
				role.setRights("");
				role.setRoleId(roleId);
				roleService.updateRoleRights(role);				//更新当前角色菜单权限(没有任何勾选)
				
		  }
		  out.write("success");
		  out.close();
	  }catch(Exception e){
			logger.error(e.toString(), e);
		}
  }
	
  /***
   * 根据角色权限处理权限状态(递归处理)
   * @Title: readMenu   
   * @Description: 
   * @param: @param menuList 传入的总菜单
   * @param: @param roleRights 加密的权限字符串
   * @param: @return      
   * @return: List<Menu>
   */
  public List<Menu> readMenu(List<Menu> menuList,String roleRights){
	  for(Menu m :menuList){
		  m.setHasMenu(RightsHelper.testRights(roleRights,m.getMenuId()));
		  this.readMenu(m.getSubMenu(), roleRights);
	  }
	  return menuList;
  }
}

package com.reborn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.reborn.common.base.BaseController;
import com.reborn.common.util.JsonUtil;
import com.reborn.common.util.PageData;
import com.reborn.manage.service.MenuService;
import com.reborn.pojo.Menu;

@Controller
@RequestMapping(value="/menu")
public class MenuController extends  BaseController{
	
	@Autowired
	MenuService  menuService;
	
	
	
	private  String getMenuId(PageData pd){
		return (null==pd.get("menuId")||"".equals(pd.get("menuId").toString()))
		?"0":pd.get("menuId").toString();
	}
	
	
	/**
	 * 显示菜单列表
	 * @Title: list   
	 * @Description: 
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping
	public  ModelAndView list(){
		ModelAndView mv=this.getModelAndView();
		PageData  pd=getPageData();
		String menuId=getMenuId(pd);
		try{
			List<Menu> menuList = menuService.listSubMenuByParentId(menuId);
			mv.addObject("menuList", menuList);
			mv.setViewName("system/menu/menu_list");
		}catch(Exception e){
			logger.error(e.toString(),e);
		}
	
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @Title: toAdd   
	 * @Description:   
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(String menuName){
		ModelAndView mv = this.getModelAndView();
		String menuId=getMenuId(this.getPageData());
		mv.addObject("menuId", menuId);
		mv.addObject("parentMenuName",null==menuName?null:menuName);
		mv.addObject("action","add");
		mv.setViewName("system/menu/menu_edit");
		return mv;
	}
	
	/**
	 * 请求编辑菜单页面
	 * @Title: toEidt   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param:       
	 * @return: void
	 */
	
	@RequestMapping("/toEidt")
	public ModelAndView toEidt( String id){
		ModelAndView modelAndView=getModelAndView();
		try{
			Menu  menu=  menuService.getMenuById(id);
			 Menu parentMenu=  menuService.getMenuById(menu.getParentId());
			 modelAndView.addObject("parentMenuName",null==parentMenu?null:parentMenu.getMenuName());
			modelAndView.addObject("menu",menu);
			modelAndView.addObject("action","edit");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		modelAndView.setViewName("system/menu/menu_edit");
		return modelAndView;
	}
	
	
	/**
	 * 保存菜单信
	 * @Title: add   
	 * @Description: 
	 * @param: @param menu      
	 * @return: void
	 */
	@RequestMapping("/add")
	public ModelAndView add(Menu menu){
		ModelAndView mv = this.getModelAndView();
		try{
			menuService.saveMenu(menu); //保存菜单
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		mv.setViewName("redirect:/menu?MSG='change'&MENU_ID="+menu.getParentId());
		return mv;
	}
	
	

	/**
	 * 显示菜单列表ztree(菜单管理)
	 * @Title: listAllMenu   
	 * @Description: 
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping("/listAllMenu")
	public ModelAndView listAllMenu(Model model){
		ModelAndView modelAndView=getModelAndView();
		try{
			String json=JsonUtil.objectToJson(menuService.listAllMenuQx("0"));
			logger.info(json);
			json=json.replaceAll("menuName", "name").replaceAll("menuId", "id")
					.replaceAll("menuUrl", "url").replaceAll("parentId", "pId")
					.replaceAll("subMenu", "children").replaceAll("hasMenu", "open");
			logger.info(json);
			model.addAttribute("zTreeNodes", json);
		}catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		modelAndView.setViewName("system/menu/menu_ztree");
		return modelAndView;
	}
	
}

package com.reborn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.base.BaseController;


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
	
	@RequestMapping
	public ModelAndView list(){
		ModelAndView modelAndView=getModelAndView();
		modelAndView.setViewName("system/role/role_list");
		return modelAndView;
	}
}

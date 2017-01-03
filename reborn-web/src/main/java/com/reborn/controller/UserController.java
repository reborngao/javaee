package com.reborn.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.base.BaseController;
import com.reborn.manage.service.UserService;
import com.reborn.pojo.User;


/**
 * 用户控制器
* @Title 

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月30日 上午11:55:45
 
* @version V1.0
 */
@Controller
@RequestMapping("/user")
public class UserController  extends BaseController{
	
	@Autowired
	UserService  userService;
	
	/**
	 * 显示用户列表
	 * @Title: listUsers   
	 * @Description: 
	 * @param:       
	 * @return: void
	 */
	@RequestMapping("/listUsers")
	public void listUsers(){
		  List<User> users= userService.listUsers();
		  ModelAndView mv = this.getModelAndView();
		  mv.addObject("users", users);
		  mv.setViewName("system/");
	}
}

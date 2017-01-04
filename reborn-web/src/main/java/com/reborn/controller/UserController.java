package com.reborn.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.base.BaseController;
import com.reborn.manage.service.UserService;
import com.reborn.pojo.User;


/***
 *   
 * 用户控制器
 *
 */
@Controller
public class UserController extends  BaseController{
	@Autowired
	UserService  userService;
	
	
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(){
		ModelAndView  mv=getModelAndView();
		List<User> users=userService.listUsers();  //列出用户列表
		mv.addObject("users", users);
 		return mv;
	}
}

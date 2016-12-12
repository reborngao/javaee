package com.reborn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.controller.base.BaseController;

/**
 * 
* @Title   总入口

* @Description: TODO

* @author reborngao  

* @date 2016年11月14日 
 
* @version V1.0
 */
@Controller
public class LoginController  extends BaseController{
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping("/login_toLogin")
	public ModelAndView toLogin(){
		ModelAndView  mv=getModeAndView();
		mv.setViewName("login");
		return  mv;
	}
}

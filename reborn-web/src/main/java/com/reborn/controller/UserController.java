package com.reborn.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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
public class UserController {
	/**
	 * 显示用户列表
	 * @Title: listUsers   
	 * @Description: 
	 * @param:       
	 * @return: void
	 */
	@RequestMapping("/listUsers")
	public void listUsers(){
		
	}
}

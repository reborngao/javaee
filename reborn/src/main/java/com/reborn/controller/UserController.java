package com.reborn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.reborn.dto.BaseResult;
import com.reborn.eu.EUMenu;
import com.reborn.pojo.Menu;
import com.reborn.pojo.User;
import com.reborn.service.UserService;


/***
 *   
 * 用户控制器
 *
 */
@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	/***
	 *  登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public BaseResult<User> login(String username,String password){
		User  user= userService.login(username, password);
		if(user!=null)
			return new BaseResult<User>(BaseResult.STATUS_SUCCESS, user);
		else
			return new BaseResult<User>(BaseResult.STATUS_ERROR, "用户名和密码不正确！");
	}
	

	
	/***
	 * {
	basic : [ {
		"menuid" : "10",
		"icon" : "icon-sys",
		"menuname" : "基础数据",
		"menus" : [ {
			"menuid" : "111",
			"menuname" : "基础数据1",
			"icon" : "icon-nav",
			"url" : "#"
		}]
	}, {
		"menuid" : "20",
		"icon" : "icon-sys",
		"menuname" : "测试一",
		"menus" : [ {
			"menuid" : "211",
			"menuname" : "测试一11",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "213",
			"menuname" : "测试一22",
			"icon" : "icon-nav",
			"url" : "#"
		} ]
	} ],
	point : [{
		"menuid" : "20",
		"icon" : "icon-sys",
		"menuname" : "积分管理",
		"menus" : [ {
			"menuid" : "211",
			"menuname" : "积分用途",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "213",
			"menuname" : "积分调整",
			"icon" : "icon-nav",
			"url" : "#"
		} ]

	}]
}
	 *  获取功能菜单
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/getMeunList",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public BaseResult<List<EUMenu>> getMeunList(@RequestParam( defaultValue="0" ) int parentId){
		return new  BaseResult<List<EUMenu>>(BaseResult.STATUS_SUCCESS,userService.getMeunList(parentId));
	}
	@RequestMapping(value="/getMeunAll",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Menu> getMeunAll(){
		return userService.getMeunAll();
	}
}

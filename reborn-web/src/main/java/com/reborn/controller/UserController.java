package com.reborn.controller;


import org.springframework.stereotype.Controller;



/***
 *   
 * 用户控制器
 *
 */
@Controller
public class UserController {
/*	@Autowired
	UserService userService;*/
	
	/***
	 *  登录
	 * @param username
	 * @param password
	 * @return
	 *//*
	@RequestMapping(value="/login",method=RequestMethod.POST,produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public BaseResult<User> login(String username,String password){
		User  user= null;
		if(user!=null)
			return new BaseResult<User>(BaseResult.STATUS_SUCCESS, user);
		else
			return new BaseResult<User>(BaseResult.STATUS_ERROR, "用户名和密码不正确！");
	}
	
*/
	
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
	 *//*
	@RequestMapping(value="/getMeunList",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public BaseResult<List<EUMenu>> getMeunList(@RequestParam( defaultValue="0" ) int parentId){
		return new  BaseResult<List<EUMenu>>(BaseResult.STATUS_SUCCESS,"");
	}
	@RequestMapping(value="/getMeunAll",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public List<Menu> getMeunAll(){
		return null;
	}*/
}

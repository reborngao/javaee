package com.reborn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.common.base.BaseController;
import com.reborn.common.util.AppUtil;
import com.reborn.common.util.Const;
import com.reborn.common.util.DateUtil;
import com.reborn.common.util.Jurisdiction;
import com.reborn.common.util.PageData;
import com.reborn.common.util.RightsHelper;
import com.reborn.common.util.Tools;
import com.reborn.manage.service.UserService;
import com.reborn.pojo.Menu;
import com.reborn.pojo.User;

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
	
	 @Autowired
	UserService userService;
	 
	 
	
	/**
	 * 访问登录页
	 * @return
	 */
	@RequestMapping("/login_toLogin")
	public ModelAndView toLogin(){
		ModelAndView  mv=getModelAndView();
		mv.setViewName("login");
		return  mv;
	}
	
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value="/login_login",produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login() throws Exception{
		PageData pd=new PageData();
		Map<String, String> map=new HashMap<String, String>();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString(PageData.KEYDATA).split(",");
		if(null!=KEYDATA&&KEYDATA.length==3){
			String code=KEYDATA[2];
 			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			//判断效验码
			if(null==code||"".equals(code)){
				errInfo = "nullcode"; 			//效验码为空
			}
			else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				if(Tools.notEmpty(sessionCode)&&sessionCode.equalsIgnoreCase(code)){///判断登录验证码
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("USER_NAME", USERNAME);
					pd.put("PASSWORD", passwd);
					  User user=userService.getUserByNameAndPwd(pd);
					  if(user!=null){
						  user.setLAST_LOGIN(DateUtil.formartCurrentDateTime());
						  pd.put("USER_ID", user.getUSER_ID());
						  pd.put("LAST_LOGIN", user.getLAST_LOGIN());
						  userService.updateLastLogin(pd);  //更新登录时间 
						  session.setAttribute(Const.SESSION_USER, user);
						  session.removeAttribute(Const.SESSION_SECURITY_CODE);
						//shiro加入身份验证
						  Subject  subject= 	SecurityUtils.getSubject();
						   UsernamePasswordToken  token=new UsernamePasswordToken(USERNAME, PASSWORD);
						   try { 
							   subject.login(token);
						   }catch(Exception e){
							   e.printStackTrace();
							   errInfo = "身份验证失败！";
						   }
					  }
					  else{
						  errInfo = "usererror"; 				//用户名或密码有误
						  logBefore(logger, USERNAME+"登录系统密码或用户名错误");
					  }
				}
				else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(Tools.isEmpty(errInfo)) {
					errInfo="success";           ////验证成功
					logBefore(logger, USERNAME+"登录系统");
				}
			}
		}
		else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(pd, map);
	}
	
	/**
	 * 
	 * @Title: loginIndex   
	 * @Description: 访问系统首页  
	 * @param: @param changeMenu 切换菜单参数
	 * @param: @return      
	 * @return: ModelAndView
	 */
	@RequestMapping("/main/{changeMenu}")
	public ModelAndView loginIndex(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session=Jurisdiction.getSession();
			User  user=(User) session.getAttribute(Const.SESSION_USER);              		//读取session中的用户信息(单独用户信息)
			if(user!=null){
				User userr=(User) session.getAttribute(Const.SESSION_USERROL);
				if(null==userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}	
				mv.setViewName("system/index/main");
			}
			else{
				mv.setViewName("system/index/login");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return mv;
		
	}
	/**
	 * 进入tab标签
	 * @Title: tab   
	 * @Description:   
	 * @param: @return      
	 * @return: String
	 */
	@RequestMapping("/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	
	
	/**
	 * 菜单缓存
	 * @Title: getAttributeMenu   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return      
	 * @return: List<Menu>
	 */
	public List<Menu> getAttributeMenu(Session session,String userName){
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null==session.getAttribute(userName+Const.SESSION_allmenuList)){//获取所有菜单
			
		}
		else{
			
		}
		return allmenuList;
	}
	
	public List<Menu> readMenu(List<Menu> menuList ,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights,menuList.get(i).getMenuId()));
		}
		return null;
	}
}

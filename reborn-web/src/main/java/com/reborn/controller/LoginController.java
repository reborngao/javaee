package com.reborn.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.reborn.controller.base.BaseController;
import com.reborn.service.UserService;
import com.reborn.util.Const;
import com.reborn.util.Jurisdiction;
import com.reborn.util.PageData;
import com.reborn.util.Tools;

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
		ModelAndView  mv=getModeAndView();
		mv.setViewName("login");
		
		return  mv;
	}
	
	
	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping("/login_login")
	public Object login(){
		System.out.println("================");
		PageData pd=new PageData();
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
				if(Tools.notEmpty(sessionCode)&&sessionCode.equals(code)){///判断登录验证码
					//String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					userService.login(USERNAME, PASSWORD);
					//shiro加入身份验证
				   Subject  subject= 	SecurityUtils.getSubject();
				   UsernamePasswordToken  token=new UsernamePasswordToken(USERNAME, PASSWORD);
				  
				   try { 
					   subject.login(token);
				   }catch(Exception e){
					   errInfo = "身份验证失败！";
				   }
				}
				else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
			}
		}
		else{
			errInfo = "error";	//缺少参数
		}
		return "";
	}
	
}

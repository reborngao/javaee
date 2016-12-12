package com.reborn.interceptor.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.reborn.util.Logger;


/**
 * 1:  doGetAuthorizationInfo()方法可以理解为是权限验证，
    2: doGetAuthenticationInfo(  AuthenticationToken token)  理解为登陆验证
 *
 */
public class ShiroRealm  extends AuthorizingRealm {
	 private Logger logger=  Logger.getLogger(getClass());
	/**
	 * 登录信息和用户验证信息验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		
		System.out.println("========doGetAuthorizationInfo==========");
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String)token.getPrincipal();  				//得到用户名 
	     String password = new String((char[])token.getCredentials()); 	//得到密码
	     if(null!=username&&null!=password){
	    	 logger.info("shiro登录认证:username:"+username+",password:"+password+ ",Name:"+getName());
	    	 return new SimpleAuthenticationInfo(username, password, getName());
	     }
	     else{
	    	 return null;
	     }
	}

}

package com.reborn.manage.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reborn.common.util.PageData;
import com.reborn.dao.DaoSupport;
import com.reborn.manage.service.UserService;


/**
 * 系统用户
 *
 */

@Service
public class UserServiceImpl  implements  UserService{

	
	@Autowired
	DaoSupport  daoSupport;
	
	
	public Map<String, Object> getUserByNameAndPwd(PageData pd) throws Exception {
		return (PageData)daoSupport.findForObject("UserMapper.getUserInfo", pd);
	}
}

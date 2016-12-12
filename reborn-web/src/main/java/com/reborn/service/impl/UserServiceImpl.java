package com.reborn.service.impl;

import java.util.List;


import org.springframework.stereotype.Service;

import com.reborn.eu.EUMenu;
import com.reborn.pojo.Menu;
import com.reborn.pojo.User;
import com.reborn.service.UserService;


public class UserServiceImpl implements UserService {

	/*@Autowired
	UserMapper userMapper;

	@Autowired
	MenuMapper menuMapper;*/
	

	public User login(String userName, String password) {
		return null;
	}

	public List<EUMenu> getMeunList(int parentId) {
		return null;
	}

	@Override
	public List<Menu> getMeunAll() {
	/*	MenuExample example = new MenuExample();
		return menuMapper.selectByExample(example);*/
		return null;
	}
}

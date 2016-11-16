package com.reborn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reborn.eu.EUMenu;
import com.reborn.mapper.MenuMapper;
import com.reborn.mapper.UserMapper;
import com.reborn.pojo.Menu;
import com.reborn.pojo.MenuExample;
import com.reborn.pojo.User;
import com.reborn.pojo.UserExample;
import com.reborn.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;

	@Autowired
	MenuMapper menuMapper;

	public User login(String userName, String password) {
		UserExample example = new UserExample();
		example.createCriteria().andUserEqualTo(userName)
				.andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public List<EUMenu> getMeunList(int parentId) {
		MenuExample example = new MenuExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		List<Menu> menms = menuMapper.selectByExample(example);
		List<EUMenu> reultMenus = new ArrayList<EUMenu>();
		for (Menu m : menms) {
			EUMenu euMenu = new EUMenu();
			euMenu.setMenuId(m.getMenuId());
			euMenu.setMenuName(m.getMenuName());
			euMenu.setParentId(m.getParentId());
			euMenu.setUrl(m.getUrl());
			euMenu.setMenus(getMeunList(m.getMenuId()));
			reultMenus.add(euMenu);
		}

		return reultMenus;
	}

	@Override
	public List<Menu> getMeunAll() {
		MenuExample example = new MenuExample();
		return menuMapper.selectByExample(example);
	}
}

package com.reborn.service;

import java.util.List;

import com.reborn.eu.EUMenu;
import com.reborn.pojo.Menu;
import com.reborn.pojo.User;

public interface UserService {
	public User login(String userName,String password);
	
	
	public List<EUMenu> getMeunList(int parentId);


	public List<Menu> getMeunAll();
}

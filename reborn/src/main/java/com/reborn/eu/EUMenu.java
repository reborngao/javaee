package com.reborn.eu;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;


public class EUMenu implements Serializable{
	private Integer menuId;
	private Integer parentId;
    private String menuName;
    private String url;
    private List<EUMenu> menus;
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public List<EUMenu> getMenus() {
		return menus;
	}
	public void setMenus(List<EUMenu> menus) {
		this.menus = menus;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}

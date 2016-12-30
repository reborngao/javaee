package com.reborn.pojo;

public class Role {
	private String roleId;
	private String roleName;  
	private String rights="";
	private String parentId="0";
	private String addQX="0";  //初始新增权限为否
	private String eidtQX="0";  //修改权限
	private String delQX="0"; //删除权限
	private String queryQX="0";  //查看权限
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getAddQX() {
		return addQX;
	}
	public void setAddQX(String addQX) {
		this.addQX = addQX;
	}
	
	public String getEidtQX() {
		return eidtQX;
	}
	public void setEidtQX(String eidtQX) {
		this.eidtQX = eidtQX;
	}
	public String getDelQX() {
		return delQX;
	}
	public void setDelQX(String delQX) {
		this.delQX = delQX;
	}
	public String getQueryQX() {
		return queryQX;
	}
	public void setQueryQX(String queryQX) {
		this.queryQX = queryQX;
	}
	
	
}

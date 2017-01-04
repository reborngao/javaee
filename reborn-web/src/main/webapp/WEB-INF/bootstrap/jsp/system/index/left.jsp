<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="sidebar  responsive">
	<div class="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large"></div>
		<ul class="nav nav-list">
			<li class=""><a href="main/index"> <i
					class="menu-icon fa fa-tachometer"></i> <span class="menu-text">后台首页</span>
			</a></li>
			<li class=""> <!-- 一级菜单 -->
				<a style="cursor: pointer;" class="dropdown-toggle"> 
					<i class="menu-icon fa fa-leaf black"></i>
					<span class="menu-text"> 系统管理 </span> 
					<b class="arrow fa fa-angle-down"></b>
				</a>
				<b class="arrow"></b>
				<ul class="submenu">  <!-- 二级菜单 -->
					<li class="">
						<a style="cursor: pointer;" class="dropdown-toggle"> 
							<i class="menu-icon fa fa-leaf black"></i>
							 权限管理
							<b class="arrow fa fa-angle-down"></b>
						</a> 
						<b class="arrow"></b>
						<ul class="submenu"><!-- 三级菜单 -->
							<li class="">
								<a style="cursor: pointer;"  onclick="siMenu('m36','z2','角色(基础权限)','role')">
									<i class="menu-icon fa fa-leaf black"></i>
									角色(基础权限)
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
				</ul>
				<a style="cursor: pointer;"  onclick="siMenu('m37','z22','菜单管理','menu/listAllMenu')">
					<i class="menu-icon fa fa-leaf black"></i>
					<span class="menu-text">菜单管理</span>
				</a>
				<b class="arrow fa fa-angle-down"></b>
				
				<a style="cursor: pointer;"  onclick="siMenu('m37','z22','用户管理','menu/listAllMenu')">
					<i class="menu-icon fa fa-leaf black"></i>
					<span class="menu-text">用户管理</span>
				</a>
				<b class="arrow fa fa-angle-down"></b>
			</li>
		</ul>
	</div>
</div>
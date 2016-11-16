<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script type="text/javascript"
	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/outlook.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript">
	var _menus = {
		basic : [ {
			"menuid" : "10",
			"icon" : "icon-sys",
			"menuname" : "基础数据",
			"menus" : [ {
				"menuid" : "111",
				"menuname" : "基础数据1",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "113",
				"menuname" : "基础数据12",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "115",
				"menuname" : "基础数据13",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "117",
				"menuname" : "基础数据14",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "119",
				"menuname" : "基础数据15",
				"icon" : "icon-nav",
				"url" : "em/enterpriseChannelObtend.action"
			} ]
		}, {
			"menuid" : "20",
			"icon" : "icon-sys",
			"menuname" : "测试一",
			"menus" : [ {
				"menuid" : "211",
				"menuname" : "测试一11",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "213",
				"menuname" : "测试一22",
				"icon" : "icon-nav",
				"url" : "#"
			} ]
		} ],
		point : [ {
			"menuid" : "20",
			"icon" : "icon-sys",
			"menuname" : "积分管理",
			"menus" : [ {
				"menuid" : "211",
				"menuname" : "积分用途",
				"icon" : "icon-nav",
				"url" : "#"
			}, {
				"menuid" : "213",
				"menuname" : "积分调整",
				"icon" : "icon-nav",
				"url" : "#"
			} ]

		} ]
	};
</script>
<title>首页</title>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<div region="north" split="true" border="false"
		style="height: 40px; background: url('images/layout-browser-hd-bg.gif') #7f99be repeat-x center 50%; color: #fff; font-family: Verdana, 微软雅黑, 黑体;">
		<span
			style="padding-left: 10px; font-size: 16px; float: left; line-height: 32px;"><img
			src="images/blocks.gif" width="20" height="20" align="absmiddle"
			style="margin-right: 5px;" />管理平台</span> <span
			style="float: right; padding-right: 20px; font-size: 15px; line-height: 32px;"
			class="head"> <a href="#" id="editpass">修改密码</a> <a href="#"
			id="loginOut">安全退出</a></span>
	</div>

	<div data-options="region:'west',title:'导航菜单',split:true"
		style="width: 180px;" hide="true">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->
		</div>
	</div>
	<div region="south" split="true"
		style="height: 30px; background: #D2E0F2;">
		<div class="footer">reborn Email:460600117@qq.com</div>
	</div>
	<!-- 内容 -->
	<div data-options="region:'center',title:''"
		style="background: #eee; overflow-y: hidden;">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding: 20px; verflow: hidden;" id="home">
				<h1>欢迎你使用管理平台</h1>
			</div>
		</div>
	</div>
</body>
</html>
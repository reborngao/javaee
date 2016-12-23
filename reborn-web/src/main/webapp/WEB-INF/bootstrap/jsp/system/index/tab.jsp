<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html >
<html>
<head lang="en">
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="plugins/tab/js/framework.js"></script>
	<link href="plugins/tab/css/import_basic.css" rel="stylesheet" type="text/css"/>
	<link  rel="stylesheet" type="text/css"  id="skin" prePath="plugins/tab/" /><!--默认相对于根目录路径为../，可添加prePath属性自定义相对路径，如prePath="<%=request.getContextPath()%>"-->
	<script type="text/javascript" charset="utf-8" src="plugins/tab/js/tab.js"></script>
</head>
<body>

<div id="tab_menu"></div>
<div style="width:100%;">
	<div id="page" style="width:100%;height:100%;"></div>
</div>
</body>
<script type="text/javascript">



function tabAddHandler(mid,mtitle,murl){
	tab.add({
		id:mid,
		title:mtitle,
		url:murl,
		isClosed :true});
}

var tab;
	$(function (){
		tab=new TabView({
			containerId:'tab_menu',
			pageid:'page',
			cid :'tab1',
			position:'top'});
		    tab.add({
		    	id: "tab1_index1",
		    	url:'http://www.baidu.com',
		    	title:"主页",
		    	isClosed:false
		    });   
		    
	});
	cmainFrameT();
	function cmainFrameT(){
		var hmainT=document.getElementById("page");
		var bheightT= document.documentElement.clientHeight;
		hmainT.style.height=(bheightT-41)+"px";
		hmainT.style.width='100%';
	}
	
</script>
</html>
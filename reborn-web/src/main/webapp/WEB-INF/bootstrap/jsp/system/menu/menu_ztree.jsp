<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
  <%
  	String path=request.getContextPath();
    String basePath=request.getScheme()+"://"
    		+request.getServerName()+":"+request.getServerPort()
    		+path+"/";
  %> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath %>">
<%@ include file="../index/top.jsp"%>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
</head>
<body>

<table style="width: 100%;border: 0">
	<tr>
		<td style="width:15%;" valign="top" bgcolor="#F9F9F9">
			<div  >
				<ul id="leftTree" class="tree"  ></ul>
			</div>
		</td>
		<td style="width:85%;">
			<iframe   name="treeFrame"  id="treeFrame" frameborder="0" src="<%=basePath%>/menu.do?MENU_ID=${MENU_ID}" ></iframe>		
		</td>
	</tr>
	
</table>
</body>

<script type="text/javascript">
var zTree;
	$(document).ready(function (){
		var zNodes='${zTreeNodes}';
		var setting={
				 showLine: true,
				 checkable: false
		}
		var zTreeNodes=eval(zNodes);
		zTree=$("#leftTree").zTree(setting,zTreeNodes);
	});

	function treeFrameT(){
		 var treeFrame= document.getElementById("treeFrame")
	   var bHeightT=	document.documentElement.clientHeight;
		 treeFrame.style.height=bHeightT-26+'px';
			 treeFrame.style.width='100%';
	}
	treeFrameT();
</script>
</html>
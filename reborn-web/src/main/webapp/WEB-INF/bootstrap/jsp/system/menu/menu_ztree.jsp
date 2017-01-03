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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="plugins/zTree/3.5/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.exedit.js"></script>
</head>
<style type="text/css">
.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
	</style>
<body>
<div class="content_wrap">
<table style="width: 100%;border: 0">
	<tr>
		<td style="width:15%;" valign="top" bgcolor="#F9F9F9">
			<div  >
				<ul id="leftTree" class="ztree"  ></ul>
			</div>
		</td>
		<td style="width:85%;">
			<iframe   name="treeFrame"  id="treeFrame" frameborder="0" src="<%=basePath%>/menu.do?MENU_ID=${MENU_ID}" ></iframe>		
		</td>
	</tr>
	
</table>
</div>
</body>

<script type="text/javascript">
var setting = {
		view: {
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRenameBtn: false,
			showRemoveBtn: true
		},
		data: {
			simpleData: {
				enable: true,
				
			}
		},
		callback: {
			onClick: onClick,
			
		}
	};
	
	
	 
	
	function onClick(event, treeId, treeNode){
		$("#"+treeNode.target).attr("src",treeNode.url);
	}
	function addHoverDom(treeId, treeNode) {
	
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
		var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.tId);
		if (btn) btn.bind("click", function(){
			$("#"+treeNode.target).attr("src","menu/toAdd?menuId="+treeNode.id+"&menuName="+treeNode.name);
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	$(document).ready(function(){
		var zTreeNodes='${zTreeNodes}';
		var zNodes=eval(zTreeNodes);
		$.fn.zTree.init($("#leftTree"), setting, zNodes);
		
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
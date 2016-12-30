<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	%>
<!DOCTYPE html>
<html lang="en">
<base href="<%=basePath%>">
<%@ include file="../index/top.jsp"%>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="plugins/zTree/3.5/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.excheck.js"></script>
	<script type="text/javascript" src="plugins/zTree/3.5/js/jquery.ztree.exedit.js"></script>
<head>


</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<div id="zhongxin">
								<div style="overflow: scroll; scrolling: yes;height:415px;width: 309px;">
								<ul id="tree" class="ztree" style="overflow:auto;"></ul>
								</div>
							</div>
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">正在保存...</h4></div>
							</div>
						<!-- /.col -->
						</div>
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
		<div style="width: 100%;padding-top: 5px;" class="center">
			<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
			<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
		</div>
<script type="text/javascript">
	$(document).ready(function(){
		
		var setting = {
				view: {
					selectedMulti: false
				},
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
		};
		var zn = '${zTreeNodes}';
		var zTreeNodes = eval(zn);
		$.fn.zTree.init($("#tree"), setting, zTreeNodes);
	});
	
	function save(){
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		var tmpNode;
		var ids = "";
		for(var i=0; i<nodes.length; i++){
			tmpNode = nodes[i];
			if(i!=nodes.length-1){
				ids += tmpNode.id+",";
			}else{
				ids += tmpNode.id;
			}
		}
		var roleId = "${roleId}";
		var url = "<%=basePath%>role/saveMenuqx";
		var postData;
		postData = {"roleId":roleId,"menuIds":ids};
		$("#zhongxin").hide();
		$("#zhongxin2").show();
		$.post(url,postData,function(data){
			top.Dialog.close();
		});
	}
</script>
</body>
</html>
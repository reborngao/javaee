<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>>" />
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>


</head>
<body class="no-skin">
	<div class="main-container">
		<div class="pag-content">
			<div class="page-header">
				<h1>
					<small> <i class="ace-icon fa fa-angle-double-right"></i>
						编辑菜单
					</small>
				</h1>
			</div>
			<!-- /.page-header -->
			<div class="row">
				<div class="col-xs-12">
					<form action="menu/${action}" class="form-horizontal"  id="menuForm" method="post">
					
						<input  name="parentId"  type="hidden" value="${null==menu.parentId?menuId:menu.parentId}"/>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								> 上级 :</label>
							<div class="col-sm-9">
								<div style="padding-top: 5px;">
									<div
										class="col-xs-4 label label-lg label-light arrowed-in arrowed-right">
										<b>${null == parentMenuName ?'(无) 此项为顶级菜单':parentMenuName}</b>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right"
								> 名称 :</label>
							<div class="col-sm-9">
								<input type="text" name="menuName" placeholder="这里输入菜单名称"
									class="col-xs-10 col-sm-5"  value="${menu.menuName }" />
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right" >链接  :</label>
							<div class="col-sm-9">
								<input type="text" name="menuUrl"  value="${menu.menuUrl }"  placeholder="这里输入菜单链接" class="col-xs-10 col-sm-5"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">序号 :</label>
							<div class="col-sm-9">
								<input type="number" placeholder="这里输入菜单序号" class="col-xs-10 col-sm-5">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">类型 :</label>
							<div  class="col-sm-9" >
								<label style="padding-left: 8px;padding-top: 7px;" >
										<input  type="radio" class="ace"  />
										<span class="lbl" >系统菜单</span>
									</label>	
							<label  style="padding-left: 5px;padding-top:7px;">
								<input type="radio"  class="ace"/> 
								<span class="lbl" >业务菜单</span>
							</label>
							</div>
						</div>		
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">状态 :</label>
							<div class="col-sm-9">
								<label style="padding-left: 8px;padding-top: 7px;">
									<input type="radio"  class="ace"/>
									<span class="lbl">显示</span>
								</label>
								<label  style="padding-left: 5px;padding-top:7px;">
									<input type="radio" class="ace"/>
									<span class="lbl">隐藏</span>
								</label>
							</div>
						</div>
						
						<div class="form-actions clearfix">
						<div class="col-md-offset-3 col-md-9">
							<a class="btn btn-mini btn-primary"  onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger">取消</a>
						</div>
						</div>	
						<div class="hr hr-18 dotted hr-double"></div>			
					</form>
				</div>
			</div>
		</div>
	</div>
	
</body>
<%@ include file="../index/foot.jsp" %>
<script type="text/javascript">
	 function save(){
		 $("#menuForm").submit();
		 
	 }
</script>
</html>
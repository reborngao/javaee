<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<%@ include file="../index/top.jsp"%>

</head>
<body class="no-skin">
	<div class="main-container">
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<table style="margin-top: 8px;">
								<tr>

									<td style="width: 69px;" onclick="javascript:addRole(0);"><a
										class="btn btn-sm btn-success">新增组</a></td>
									<c:choose>
										<c:when test="${not empty roles }">
											<c:forEach items="${roles }" var="r">
												<td style="width: 100px;" class="center"
													<c:choose>
												<c:when test="${roleId == r.roleId}">
												bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"
												</c:when>
												<c:otherwise>
												bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"
												</c:otherwise>
											</c:choose>
													onMouseMove="javascript:this.bgColor='#FFC926';"><a
													href="role?roleId=${r.roleId }"
													style="text-decoration: none; display: block;"> <i
														class="menu-icon fa fa-users"></i> <font color="#666666">${r.roleName }</font>
												</a></td>
												<td style="width: 5px;"></td>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="100">没有相关数据</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tr>
							</table>
							<table>
								<tr height="7px;">
									<td colspan="100"></td>
								</tr>
								<tr>
									<td><font color="#808080">本组：</font></td>
									<td>
							</table>
							<table  class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center">序号</th>
										<th class="center">角色</th>
										<th class="center">增</th>
										<th class="center">删</th>
										<th class="center">改</th>
										<th class="center">查</th>
										<th class="center" style="width:155px;">操作</th>
									</tr>
								</thead>
								<c:choose>
									<c:when test="${not empty roleListZ }">
										<c:forEach var="rz" items="${roleListZ }" varStatus="vs">
											<tr>
												<td>${vs.index+1}</td>
												<td>${rz.roleName}</td>
												<td  style="width: 30px;"  title="分配新增权限" >
													<a class="btn btn-warning btn-mini">
														<i class="ace-icon fa fa-wrench bigger-110 icon-only"></i>
													</a>
												</td>
												<td style="width: 30px;" title="分配新增权限" >
													<a class="btn btn-warning btn-mini">
														<i class="ace-icon fa fa-wrench bigger-110 icon-only"></i>
													</a>
												</td>
												<td  style="width: 30px;" title="分配新增权限">
													<a class="btn btn-warning btn-mini">
														<i class="ace-icon fa fa-wrench bigger-110 icon-only"></i>
													</a>
												</td>
												<td  style="width: 30px;" title="分配新增权限" >
													<a class="btn btn-warning btn-mini">
														<i class="ace-icon fa fa-wrench bigger-110 icon-only"></i>
													</a>
												</td>
												<td style="width:155px;">
													<a class="btn btn-mini btn-purple" onclick="editRights('${rz.roleId }');">
														<i class="icon-pencil"></i>
														菜单权限
													</a>
													<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${rz.roleId }');">
														<i class='ace-icon fa fa-pencil-square-o bigger-130'></i>
													</a>
													 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${rz.roleId }','c','${rz.roleName }');">
													 	<i class='ace-icon fa fa-trash-o bigger-130'></i>
													 </a>
													
												</td>
												
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="100">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
							<div>
								<a class="btn btn-sm btn-success" onclick="addRole('${roleId }');">新增角色</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="../index/foot.jsp"%>
	<script type="text/javascript">
	function addRole(pid){
		var diag=new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增";
		 diag.URL = '<%=basePath%>role/toAdd?parentId=' + pid;
			diag.Width = 222;
			diag.Height = 100;
			diag.CancelEvent = function() {
				if (diag.innerFrame.contentWindow.document
						.getElementById('zhongxin').style.display == 'none') {
					setTimeout('self.location.reload()', 100)
				}
				diag.close();
			};
			diag.show();
		}
	// 菜单权限
	function editRights(roleId){
		var diag=new top.Dialog();
		diag.Drag=true;
		diag.title="菜单权限";
		diag.Title = "菜单权限";
		 diag.URL = '<%=basePath%>role/menuqx?roleId='+roleId;
		 diag.Width = 320;
		 diag.Height = 450;
		 diag.CancelEvent = function(){ //关闭事件
			diag.close();
		 };
		 diag.show();
	}
	</script>
</html>
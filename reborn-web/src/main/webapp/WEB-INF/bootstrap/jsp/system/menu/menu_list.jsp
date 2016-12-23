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
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<%@ include file="../index/top.jsp"%>
</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="center" style="width: 50px;">序号</th>
								<th class='center'>名称</th>
								<th class='center'>资源路径</th>
								<th class='center' style="width: 50px;">状态</th>
								<th class='center' style="width: 120px;">操作</th>
							</tr>
						</thead>
						<tbody>


							<c:choose>
								<c:when test="${not empty menuList}">
									<c:forEach items="${menuList }" var="m">
										<tr>
											<td></td>
											<td>${m.menuName}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
					<div>
						&nbsp;&nbsp; <a class="btn btn-sm btn-success" onclick="addmenu();">新增</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function addmenu(){
		window.location.href="<%=basePath%>menu/toAdd"
	}
</script>
</html>
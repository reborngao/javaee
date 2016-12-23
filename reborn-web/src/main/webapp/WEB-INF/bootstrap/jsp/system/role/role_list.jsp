<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
									<td><a class="btn btn-sm btn-success">新增组</a></td>
								</tr>
							</table>
						<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th class="center">序号</th>
										<th class="center">角色</th>
										<th class="center">增</th>
										<th class="center">删</th>
										<th class="center">改</th>
										<th class="center">查</th>
										<th class="center">操作</th>
									</tr>
								</thead>
						</table>

						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
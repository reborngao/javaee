<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE >
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
</head>
<body class="no-skin">

	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
							<form action="role/add.do" method="post">
								<div id="zhongxin" style="padding-top: 13px; ">
									<input name="parentId"  value="${parentId}" type="hidden">
									<table style="width: 100%;" class="center">
										<tr>
											<td><input type="text" placeholder="这里输入名称"  name="roleName"/></td>
										</tr>
										<tr>
											<td style="text-align: center; padding-top: 13px;"><a
												class="btn btn-mini btn-primary" onclick="save();">保存</a> <a
												class="btn btn-mini btn-danger"
												onclick="top.Dialog.close();">取消</a></td>
										</tr>
									</table>
								</div>
							</form>
							<div id="zhongxin2" class="center" style="display: none;">
								<img src="static/images/jzx.gif" style="width: 50px;" /><br />
								<h4 class="lighter block green"></h4>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@ include file="../index/foot.jsp" %>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
	function save(){
		if($("input[name='roleName']").val()==''){
			$("input[name='roleName']").tips({
				side:3,
				msg:'请输入',
				bg:'#AE81FF',
				time:2
			});
			$("input[name='roleName']").focus();
			return false;
		}
		$("zhongxin").hide();
		$("zhongxin2").show();
		$.find("form")[0].submit();
	}
	
</script>
</html>
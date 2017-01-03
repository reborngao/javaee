<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html >
<html lang="en"> <!--  lang="en" 这里的lang="en"可以删除，如果不删除的，用谷歌之类打开，它会认为是英文的，会自动给翻译（如果设置了自动翻译的话） -->
<head >
<base href="<%=basePath%>">
<!-- jsp文件头和头部 -->
<%@include file="top.jsp" %>
</head>
<body class="no-skin">
<%@ include file="head.jsp"%> 


<!-- main-container start -->
	<div class="main-container" id="main-container">
	<!-- sidebar start -->
	<%@ include file="left.jsp"%>
	<!-- sidebar end -->
	<!-- main-content start -->
	<div class="main-content">
		 <div class="main-content-inner">
			<div class="page-content">
			<div class="row" >
				<div >
						<iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab" style="margin:0 auto;width:100%;height:100%;"></iframe>
				</div>
			</div>
				
			</div>
		</div>  
		
	</div>
	<!-- main-content end -->
</div>

<!-- main-container end -->
<%@ include file="foot.jsp"%>
	<script src="static/ace/js/bootstrap.js"></script>
	<!-- ace start -->
	<!-- ace root -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!--  sidebar  单击Script-->
	<script src="static/ace/js/ace/ace.sidebar.js"></script>
	<!--引入属于此页面的js -->
	<script  src="static/js/myjs/head.js"></script>
	<script  src="static/js/myjs/index.js"></script>
	
	<!--引入弹窗组件2start-->
		<script type="text/javascript" src="plugins/attention/drag/drag.js"></script>
		<script type="text/javascript" src="plugins/attention/drag/dialog.js"></script>
		<link type="text/css" rel="stylesheet" href="plugins/attention/drag/style.css"  />
	<!--引入弹窗组件2end-->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<title>管理员登录</title>
</head>
<body  style="background-color: #F3F3F3">
	<div class="easyui-dialog" title="管理员登录" data-options="closable:false,draggable:false"  style="width:400px;height:300px;padding: 10px;">
       	<div style="margin-left: 50px;margin-top: 50px;">
	       	<div style="margin-bottom:20px;">
		       	<div>
		       			用户名：<input name="username" class="easyui-textbox" data-options="required:true" style="width:200px;height:32px" />
		       	</div>
	       	</div>
	       	<div style="margin-bottom:20px;">
		       		<div>
			            	密&nbsp;码: <input name="password" class="easyui-textbox" type="password" style="width:200px;height:32px" data-options="" />
			            </div>
	       	</div>
       		<div>
       			<a id="login" class="easyui-linkbutton" iconCls="icon-ok" style="width:100px;height:32px;margin-left: 50px">登录</a>
       		</div>
       	</div>
    </div>
    <script type="text/javascript">
    	$("#login").click(function (){
    		var username= $("[name=username]").val();
    		var password=$("[name=password]").val();
    		var params={"username":username,"password":password};
    		$.post("/login",params,function (data){
    			if(data.status==200){
    				window.location.href="/index"
    			}
    			else{
    				$.messager.alert("错误",data.message);
    			}
    		})
    	});
    </script>
    
</body>
</html>
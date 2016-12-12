<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="static/bootstrap/css/bootstrap.min.css" />
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<title>Insert title here</title>

<style type="text/css">
#loginbox {
	z-index: 100;
	width: 32%;
	text-align: center;
	margin-left: auto;
	margin-right: auto;
	margin-top: 10%;
	position: relative;
	width: 32%;
	overflow: hidden !important;
}

#loginbox form {
	width: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	position: relative;
	top: 0;
	left: 0;
}

body {
	padding: 0;
	margin-top: 0px;
	margin: 0px;
}

#loginbox  .control-group {
	padding: 20px 0px;
}

#loginbox .main_input_box {
	margin: 0 auto;
}

input[type='text'], input[type='password'] {
	-webkit-border-radius: 0px;
	border-radius: 0px;
}

#loginbox .main_input_box input {
	height: 30px;
	border: 0px;
	display: inline-block;
	width: 75%;
	line-height: 28px;
	margin-bottom: 0px;
}

#loginbox .normal_text {
	padding: 15px 10px;
}

#loginbox .main_input_box .add-on {
	display: inline-block;
	color: #fff;
	*line-height: 30px;
	width: 40px;
}

.bg_lg {
	background: #28b779;
}

.bg_ly {
	background: #ffb848;
}

.form-actions {
	background: none;
	border-top: 1px solid #3f4954;
	margin-top: 40px;
}

#loginbox  .i_user {
	padding-top: 10.5px;
	padding-bottom: 10.5px;
}

#loginbox  .i_passwd {
	padding-top: 12px;
	padding-bottom: 11px;
}

.login_main {
	width: 100%;
	position: absolute;
	margin: 0 auto;
	text-align: center;
}

#loginbox .form-actions {
	padding: 14px 20px 15px;
}

@media ( max-width :800px) {
	#logo {
		width: 60%;
	}
	#loginbox {
		width: 80%
	}
}

@media ( max-width : 480px) {
	#logo {
		width: 40%;
	}
	#loginbox {
		width: 90%
	}
	#loginbox .control-group {
		padding: 8px 0;
		margin-bottom: 0px;
	}
}

.login_code {
	width: 40px;
}
</style>
</head>
<body>
	<div class="login_main">
		<div id="loginbox">

			<form action="" method="post">
				<div class="control-group normal_text">
					<h3>
						<img src="static/login/images/logo.png" />
					</h3>
				</div>
				<div class="control-group">
					<div class="main_input_box">
						<span class="add-on bg_lg i_user"> <i><img
								height="37" src="static/login/images/user.png" /></i>
						</span><input type="text" name="loginName" id="loginName" value=""
							placeholder="请输入用户名" />
					</div>
				</div>
				<div class="control-group">
					<div class="main_input_box">
						<span class="add-on bg_ly i_passwd"> <i><img
								height="37" src="static/login/images/suo.png" /></i>
						</span><input type="password" name="password" id="password"
							placeholder="请输入密码" value="" />
					</div>
				</div>
				<div style="float: right; padding-right: 10%;">
					<div style="float: left; margin-top: 0px; margin-right: 2px;">
						<font color="white">记住密码</font>
					</div>
					<div style="float: left;">
						<input name="form-field-checkbox" id="saveid" type="checkbox"
							style="padding-top: 0px;" />
					</div>
				</div>
				<div class="form-actions">
					<div style="width: 86%; padding-left: 3%;">
						<div style="float: left; margin-top:">
							<i> <img src="static/login/images/yan.png" style="height: 30px;" /></i>
						</div>
						<div style="float: left; margin-left: 5px;">
							<input type="text" name="code" id="code" class="login_code" />
						</div>
						<div style="float: left; margin-left: 5px;">
							<i><img style="height: 30px;" id="codeImg" alt="点击更换"
								title="点击更换" src="code" /></i>
						</div>
					</div>
					<span style="float: right; padding-right: 3%; margin-left: 5px;"><a
						class="btn btn-success">取消</a></span> <span style="float: right;"
						onclick="severCheck()"><a class="btn btn-info">登录</a></span>
				</div>

			</form>
		</div>
	</div>
	<div>
		<img src="static/login/images/banner_slide_02.jpg">
	</div>

	<script type="text/javascript">
		function severCheck() {
			if (check()) {
				var loginName=$("#loginName").val();
				var password=$("#password").val();
				var code = (loginName+","+password+","+$("#code").val());
				$.ajax({
					tpye:'POST',
					url:'login_login',
					dataType:'json',
					data:{KEYDATA:code,tm:new Date().getTime()},
					cache:false,
					success:function (data){
						alert("0000");
					}
				});
				
			}
		}

		//客户端校验

		function check() {
			if ($("#loginName").val() == "") {
				$("#loginName").tips({
					side : 2,
					msg : '用户名不能为空!',
					bg : '#AE81FF',
					time : 3
				});
				$("#loginName").focus();
				return false;
			} else {
				$("#loginName").val(jQuery.trim($("#loginName").val()));
			}

			if ($("#password").val() == "") {
				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				$("#password").focus();
				return false;
			}
			if ($("#code").val() == "") {
				$("#code").tips({
					side : 2,
					msg : '验证码不得为空',
					bg : '#AE81FF',
					time : 3
				});
				$("#code").focus();
				return false;
			} 

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 10
			});
			return true;
		}
		
		$(document).ready(function() {
			$("#codeImg").bind("click", function() {
				changeCode();
			});
		});

		function getTimesamp() {
			var times = new Date();
			return times.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code?t=" + getTimesamp());
		}
	</script>
</body>
</html>
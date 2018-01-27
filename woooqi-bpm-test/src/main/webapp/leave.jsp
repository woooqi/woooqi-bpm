<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户注册</title>
<style type="text/css">
div#main_right_register label {
	width: 80px;
	font-size: 15px;
	display: inline-block;
	text-align: right;
	margin-left: 20px;
}

div#main_right_register input {
	outline: none;
}

#main_right_register span {
    display:inline-block;
	margin: 0 0 0 10px;
	text-align: left;
	width: 200px;
}

div#main_register h4 {
	text-align: center;
	padding: 0 0 0 30px;
}
/*登录按钮*/
#reg {
	cursor: pointer;
	background: none;
	border: burlywood 1px solid;
	font-size: 14px;
	width: 80px;
	height: 27px;
	display: inline-block;
}
</style>
<script type="text/javascript">
	function pre(){
		return "{\"pre\":\"1\"}";
	}
	function nex(str){
		//alert(str);
		return "{\"next\":\"nex\"}";
	}
</script>
</head>
<body style="text-align: center">
	<div id="main_register"
		style="margin: 0 auto; border: 1px solid #666; width: 800px; height: 400px;">
		<h4>请假申请</h4>
		<hr />
		<div id="main_right_register">
			<label>请假理由:</label><input type="text" name="loginName"
				id="reason" maxlength="30"
				style="height: 20px; width: 300px;margin-top: 10px;"> <br />
			<label>请假天数:</label> <input type="text" name="days" id="days"
				maxlength="5" 
				style="height: 20px; width: 300px;margin-top: 10px;"> <br />
			<label>请假时间:</label> <input type="text" name="time"
				id="time" maxlength="20" style="height: 20px; width: 300px;margin-top: 10px;">
			 <br />
			 
		</div>
	</div>




</body>
</html>
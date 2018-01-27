<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title>Titan BPM - <sitemesh:write property="title" /></title>
	
	<script src="${ctx}/static/js/jquery-1.9.1.js"></script>
        
    <script src="${ctx}/static/bootstrap/vendors/bootstrap/js/bootstrap.js"></script>
    <link href="${ctx}/static/bootstrap/vendors/bootstrap/css/bootstrap.css" rel="stylesheet" media="screen"></link>
    
    <link href="${ctx}/static/bootstrap/vendors/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    
    <script src="${ctx}/static/bootstrap/vendors/dialog/dist/dialog.js"></script>
    <script src="${ctx}/static/bootstrap/vendors/dialog/dist/dialog-plus.js"></script>
    
    <script src="${ctx}/static/bootstrap/bootstrap-table/dist/bootstrap-table.js"></script>
	<link href="${ctx}/static/bootstrap/bootstrap-table/dist/bootstrap-table.css" rel="stylesheet" type="text/css">
      
	<script src="${ctx}/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<link href="${ctx}/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css">
    
    <style type="text/css">  
		body {font-family: Microsoft YaHei;}
		.panel-body{padding-left: 10px;}
		.modal-footer{background-color: white;}
		table {width: 100%;}
		.form-control{margin-bottom: 10px;}
		.modal-body{padding: 0px;}
    </style>  
     <script type="text/javascript">
   		$(document).ready(function() {
			$(".datepicker").datetimepicker({
				format : 'yyyy-mm-dd',
		        todayBtn:  1,
				autoclose: 1,
				minView: 2,
				forceParse: 0
			})
			
			 $(".timepicker").datetimepicker({
				format : 'yyyy-mm-dd HH:ii:ss',
		        todayBtn:  1,
				autoclose: 1,
				forceParse: 0,
		        showMeridian: 1,
		        minuteStep:1
			})
		});
   		
   		Date.prototype.Format = function (formate) { 
   		    var o = {
   		        "M+": this.getMonth() + 1,
   		        "d+": this.getDate(),
   		        "h+": this.getHours(),
   		        "m+": this.getMinutes(),
   		        "s+": this.getSeconds(),
   		        "q+": Math.floor((this.getMonth() + 3) / 3),
   		        "S": this.getMilliseconds()
   		    };
   		    if (/(y+)/.test(formate)) formate = formate.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
   		    for (var k in o)
   		    if (new RegExp("(" + k + ")").test(formate)) formate = formate.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
   		    return formate;
   		}
    </script>
	
	<sitemesh:write property="head" />
</head>

<body>
	<sitemesh:write property="body" />
</body>

</html>
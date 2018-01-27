<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		
		<meta charset="utf-8">
    	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
    	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    	<meta name="viewport" content="width=device-width, initial-scale=1">
    	
		<title>Titan BPM - <sitemesh:write property="title"/></title>
		
		<script src="${ctx}/static/js/jquery-1.9.1.js"></script>
        
        <script src="${ctx}/static/bootstrap/vendors/bootstrap/js/bootstrap.js"></script>
        <link href="${ctx}/static/bootstrap/vendors/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"></link>
        
        <script src="${ctx}/static/bootstrap/vendors/dialog/dist/dialog.js"></script>
        <script src="${ctx}/static/bootstrap/vendors/dialog/dist/dialog-plus.js"></script>
        
        <link href="${ctx}/static/bootstrap/vendors/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
        	    
        <script src="${ctx}/static/bootstrap/vendors/metisMenu/metisMenu.js"></script>
        <link href="${ctx}/static/bootstrap/vendors/metisMenu/metisMenu.css" rel="stylesheet" type="text/css">
        
        <script src="${ctx}/static/bootstrap/bootstrap-table/dist/bootstrap-table.js"></script>
        <link href="${ctx}/static/bootstrap/bootstrap-table/dist/bootstrap-table.css" rel="stylesheet" type="text/css">
        
		<script src="${ctx}/static/bootstrap/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
        <link href="${ctx}/static/bootstrap/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css">
        
		<style type="text/css">
			.dropdown-submenu {position: relative;} 
			.dropdown-submenu:hover>.dropdown-menu {display: block;  }   
	        .dropdown-submenu > .dropdown-menu {top: 0;left: 100%;margin-top: -6px;margin-left: 6px;-webkit-border-radius: 6px 6px 6px 6px;-moz-border-radius: 6px 6px 6px 6px;border-radius: 6px 6px 6px 6px;}  
			.dropdown-submenu > .dropdown-menu:after {position: absolute;top: 10px;left: -6px;display: inline-block;border-bottom: 6px solid transparent;border-right: 6px solid #ffffff;border-top: 6px solid transparent;content: '';}
			body {font-family: Microsoft YaHei;}
			footer{margin-bottom: 0px;height: 20px;width: 100%;}
			footer span{text-align: center;height: 20px;}
			
			.menu{z-index:500; float: left;position: relative;width: 240px;-webkit-border-radius: 6px 6px 6px 6px;-moz-border-radius: 6px 6px 6px 6px;border-radius: 6px 6px 6px 6px;}
	    	.menu a{color: #777777}
	    	.menu a:hover, a:focus{color: #333333}
	    	#wrapper{float: left;width:calc(100% - 240px);min-height: 400px;padding-left: 10px;}
	    	
	    	#side-menu ul li a.active {background-color: #eeeeee;}
	    </style>  
	    
	    <%@ include file="main_header.jsp" %> 
	    
		<sitemesh:write property="head"/>
		
	</head>
	
	<body>
	    <%@ include file="main_body.jsp" %> 

	    <div class="container-fluid">
	    	<div class="menu" >
				<nav class="navbar navbar-default navbar-static-top" role="navigation" >
		            <div class="navbar-default sidebar" role="navigation">
		                <div class="sidebar-nav navbar-collapse" >
		                
		                    <ul class="nav" id="side-menu" >
		                        		                        
		                     </ul>
		                      
		                </div>
		            </div>
		        </nav>
			</div>    
			<sitemesh:write property="body"/>
		</div>
		
		<footer>
            <span><p>江苏富深协通科技股份有限公司 &copy; 2016-2018</p></span>
        </footer>

	</body>

</html>
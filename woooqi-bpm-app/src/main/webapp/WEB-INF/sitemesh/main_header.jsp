<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		 
 		 $.ajax({
			type: "POST",
			url: "${ctx}/menu/getAllRoleMenu",
			dataType: 'json',
			data:{roleIds:"${_ids}"},
			success: function(datas){
				//alert(JSON.stringify(datas));
				var html = "";
				if(datas.child && datas.child.length > 0){
					html = html + "<ul class='nav navbar-nav'>";
					for(var i = 0;i<datas.child.length;i++){
						var data = datas.child[i];
						html = html + "<li class='dropdown'><a  role='button' class='dropdown-toggle' data-toggle='dropdown'>" + data.name + "<i class='caret'></i></a>";
						if(data.child.length > 0){
							html = html + "<ul class='dropdown-menu'>";
							for(var j = 0;j<data.child.length;j++){
								var da = data.child[j];
								html = html + "<li><a href='javascript:void(0)' onclick=subMenus('"+ data.id +"',"+j+")>" + da.name + "</a></li>";
								//alert(html)
							}
							html = html + "</ul>";
						}
	                    html = html + "</li>";
					}
					html = html + "</ul>";
				}
				
				html = html
					+ "<ul class='nav navbar-nav navbar-right'>"
			        	+ "<li class='dropdown'>"
				    		+ "<a href='#' class='dropdown-toggle' data-toggle='dropdown' role='button' aria-haspopup='true' aria-expanded='false'>${user.name} <span class='caret'></span></a>"
				    		+ "<ul class='dropdown-menu'>"
								+ "<li><a href='#'>信息修改</a></li>"
		            			+ "<li class='divider'></li>"
		            			+ "<li><a href='${ctx}/logout'>登出</a></li>"
				    		+ "</ul>"
				    	+ "</li>"
			      	+ "</ul>"
				
				$("#menu_nav").html(html);
			}
		}); 
		 
	});
	
	function subMenus(id,i){
		subMenu("${_ids}",id,function(){
			$("#side-menu").children("li:eq("+i+")").addClass("active");
			$('#side-menu').metisMenu();
		},i);
	}
	
	function _initHtml(callback,index){
		if(index){
			subMenu("${_ids}","${_id}",callback,index);
		}else{
			subMenu("${_ids}","${_id}",callback);
		}
	}
	
	function subMenu(ids,id,callback,index){
		$.ajax({
			type: "POST",
			url: "${ctx}/menu/getSubRoleMenu",
			dataType: 'json',
			data:{roleIds:"${_ids}",id:id},
			success: function(datas){
				var sub = $("#side-menu").html();
				if(!sub){
					window.location.href = "${ctx}/main_index?path=index_blank&_ids=${ids}&_id="+id+"&index="+index;
				}else{
					var html = "";
					if(datas.child && datas.child.length > 0){
						for(var i = 0;i<datas.child.length;i++){
							var data = datas.child[i];
							html = html + "<li id='"+data.stamp+"'>";
							html = html + "<a ><i class='fa " + (!data.image?'fa-tasks':data.image) + "'></i>&nbsp;"+data.name+"<span class='fa arrow'></span></a>";
							if(data.child.length > 0){
								html = html + "<ul class='nav nav-second-level'>";
								for(var j = 0;j<data.child.length;j++){
									var da = data.child[j];
									if(da.child.length>0){
										html = html + "<li id='"+da.stamp+"'>";
										html = html + "<a >&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa " + (!da.image?'fa-tasks':da.image) + "'></i>&nbsp;"+da.name+"<span class='fa arrow'></span></a>";
										html = html + "<ul class='nav nav-third-level'>";
										for(var k = 0;k<da.child.length;k++){
											var d = da.child[k];
											html = html + "<li><a id='"+d.stamp+"' href='"+d.url+"&_id="+datas.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa " + (!d.image?'fa-tasks':d.image) + "'></i>&nbsp;"+d.name+"</a></li>";
										}
										html = html + "</ul>";
									}else{
										html = html + "<li>";
										html = html + "<a id='"+da.stamp+"' href='"+da.url+"&_id="+datas.id+"'>&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa " + (!da.image?'fa-tasks':da.image) + "'></i>&nbsp;"+da.name+"</a>";
									}
									html = html + "</li>";
								}
								html = html + "</ul>";
							}
							html = html + "</li>";
						}
					}
					$("#side-menu").html(html);
					callback();
				}
			}
		});
	}
</script>

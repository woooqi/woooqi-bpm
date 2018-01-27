<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
    var d = null;
	function addModel(){
		d = dialog({
				title: '新增模型',
				url : '${ctx}/edit?path=bpm/bpm/model/model_manage_add',
				width : 600,
				height : 260,
				close : false
			});
		d.showModal();
    }
	$(document).ready(function() {
		$(".datepicker").datetimepicker({
			format : 'yyyy-mm-dd',
	        todayBtn:  1,
			autoclose: 1,
			minView: 2,
			forceParse: 0
		});
		
		 $(".timepicker").datetimepicker({
			format : 'yyyy-mm-dd HH:ii:ss',
	        todayBtn:  1,
			autoclose: 1,
			forceParse: 0,
	        showMeridian: 1,
	        minuteStep:1
		});
		 
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
		
	<nav class="navbar navbar-default">
		<div class="container-fluid" >
		    <div class="navbar-header">
				<a class="brand" href="${ctx}/"><img src="${ctx}/static/image/logo.png" style="padding-top: 5px;padding-left: 20px;padding-right: 20px;"></a>
		    </div>
		
		    <div class="collapse navbar-collapse" id="menu_nav">

			</div>
		</div>
	</nav>

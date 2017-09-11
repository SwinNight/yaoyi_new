<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="java.util.ArrayList"%>
<%@page  import="java.util.List"%>
<% 
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配送中心平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
<meta content="yes" name="apple-mobile-web-app-capable" />

<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/platform.css"/>"/>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/jquery-1.8.3.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/bootstrap.min.js"/>"></script>
<script type="text/javascript">
	
	function ptDelivertyToDatail (DelivertyId) {
		window.location.href ='<%=path%>/mobilePtDelivertyToDatail?DelivertyId='+DelivertyId;
	}
</script>
</head>
<body>
	<div class="container">
		<div class="header">
			<a href="javascript:history.go(-1);" class="home">
		            <span class="header-icon header-icon-return"></span>
		            <span class="header-name">返回</span>
			</a>
		    <a onclick="refreshTab()"class="home1">
		            <span class="header-icon header-icon-shuaxin"></span>
		            <span class="header-name">刷新</span>
			 </a>
			<div class="title" id="titleString">全部配送</div>
		
		</div>
		<div class="alllist">
			<ul class="nav nav-tabs">
				<li class="active"><a id ="allTab"  onclick="showTab('all');" data-toggle="tab">全部</a></li>
				<li><a id ="okTab" onclick="findPtCfFirst('ok');"data-toggle="tab">已完成</a></li>
				<li><a id ="cancelTab" onclick="findPtCfFirst('cancel');"data-toggle="tab">已撤销</a></li>
			</ul>
			<div id="tab-content" class="tab-content">
			
				<div id="all" class="tab-pane active">
				<%--       <span class='no_orders' >所有</span> --%>
				</div>
				<div id="ok" class="tab-pane">
				<%-- <span class='no_orders' >成功</span> --%>
				</div>
				<div id="cancel" class="tab-pane">
				<%-- 	<span class='no_orders' >取消订单</span> --%>
				</div>
			</div>
		</div>
		<div class="footerbtn">
			<div style="display: block;border-bottom: solid 1px #FFF;border-top: solid 1px #cacaca;text-indent: -9999px;height: 0px;">line</div>
			<ul class="unstyled">
				<li><a href="<%=path%>/mobileNew"><span class="icon icon-new"></span><span class="name">新配送</span></a></li>
				<li><a href= "<%=path%>/mobileAll" class="active"><span class="icon icon-all"></span><span class="name">全部配送</span></a></li>
				<li><a href="<%=path%>/mobileUser"><span class="icon icon-user"></span><span class="name">个人中心</span></a></li>
			</ul>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
//将每次刷新的数据id存一起，下一次刷新的数据不包括当前已有的数据
		var allList = new Array();
		var okList = new Array();
		var cancelList = new Array();
         //初始显示页面
        findPtCf("all");  
       //重置当前tab页面
        function refreshTab() {
        	var id = $('#tab-content').children('.active').attr('id');
        	$('#'+id).empty();//先清空当前Tab页面
        	//并清空当前对应的存放id的数组
        	if(id=="all") {allList = [];} 
        	if(id=="ok") {okList = [];}
        	if(id=="cancel") {cancelList = [];}
        	$('#tab-content').scrollTop(0);
        	findPtCf(id);//添加当前页面
        }
      //点击事件只执行一次，通过滚动条刷新数据
       function findPtCfFirst(status) {
        	findPtCf(status);
            $('#' +status+"Tab").removeAttr("onclick");
            $('#' +status+"Tab").attr("onclick","showTab('"+status+"')");
      }
    	$('#tab-content').scroll(function(event){//当滚动条到到底部时刷新
    		var scrTop = $('#tab-content').scrollTop();
    		var boxHeight = $('#tab-content').height();
    		var allHeight =  $('#tab-content').children('.active').height();
    		if(scrTop + boxHeight == allHeight){
    			var id = $('#tab-content').children('.active').attr('id');
    			findPtCf(id);//滚动刷新下5条数据
    		}
    	})

    	function showTab(status) {
    		$("#"+status).addClass("active").siblings().removeClass("active");
    	}
 
		function 	findPtCf(status) {
			var old = new Array();
			var oldList ="";
		    if(status =="all") { old = allList; }
		    if(status =="ok") { old = okList; }
		    if(status =="cancel") { old = cancelList; }
		    //下次刷新时过滤掉已经显示的订单
		    for(var i=0;i<old.length;i++) {
		    	if(i ==0) { 
		    		oldList +="'"+old[i]+"'";
		    	} else {
		    		oldList +=",'"+old[i]+"'";
		    	}
		    }
		$("#"+status).addClass("active").siblings().removeClass("active");
		$.ajax({
			type : "post",
			url : '<%=path%>/mobileAllDetail',
			dataType : 'json',
			data :  {
				'status':status,
				'size':5,
				'oldList':oldList,
				'currentPage':1
			},
			async : false,
			success : function(data) {
		        var  deliveryList = data.ptDeliveryList;
			    var allDeli="";
			    var okDeli="";
			    var cancelDeli=""
			   for(var i =0;i<deliveryList.length;i++){
			    var  delivery = deliveryList[i];
			    var temp ="";
			    if(delivery.status=="13")  {temp ="(派送中)";  }
			    if(delivery.status=="16")  {temp ="(失败)";  }
			    if(delivery.status=="17")  {temp ="(取消)";  }
			    var start = '<div class="item" onclick=ptDelivertyToDatail('+delivery.id+')>';
				var end =	'<div><span>处方编号: </span><span>'+delivery.cf_id+temp+'</span></div>'+
					'<div><span>处方时间: </span><span>'+delivery.pres_date_time+'</span></div>'+
					'<div><span>联系人: </span><span>'+delivery.name+'</span></div>'+
					'<div><span>联系电话: </span><span>'+delivery.phone_no+'</span></div>'+
					'<div><span>配送地址: </span><span>'+delivery.address+'</span></div>'+
				    '</div>';
				      if(delivery.status == '15') {
				    	  allDeli += start +'<div class="img imgok"></div>' + end;
				    	  okDeli += start +'<div class="img imgok"></div>' + end;
				      }
				     if(delivery.status == '16'||delivery.status == '17') {  
				    	 allDeli += start +'<div class="img imgcancel"></div>' + end;
				    	 cancelDeli += start +'<div class="img imgcancel"></div>' + end;
				      }
				     if(delivery.status == '13'||delivery.status == '11') {
				    	  allDeli += start +'<div class="img imgnew"></div>' + end;
				      }
				    if(status =="all") {  allList.push(delivery.id);	 }
				    if(status =="ok") { okList.push(delivery.id); }
				    if(status =="cancel") { cancelList.push(delivery.id);}
			  }
			    if(status =="all") { $("#all").append(allDeli);}
			    if(status =="ok") { $("#ok").append(okDeli);}
			    if(status =="cancel") { $("#cancel").append(cancelDeli);}
              // console.log("allList:" +allList.toString());
               //console.log("okList:" +okList.toString());
               //console.log("cancelList:" +cancelList.toString());  
			},
			error: function(data) {
				alert("error");
			}
		});
		}
</script>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<% 
String path = request.getContextPath();
EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
List<EngPtDelivery>  ptDeliveryList = (List<EngPtDelivery>)request.getAttribute("ptDeliveryList");
if(ptDeliveryList ==null) {
	ptDeliveryList = new ArrayList<EngPtDelivery>();
}

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
			<div class="title" id="titleString">新配送</div>
		</div>
		 <div class="alllist">
			<div id="tab-content-new" class="tab-content">
			    <div  id="new"  class="tab-pane active"></div>
		  </div>
		 </div>
		<div class="footerbtn">
			<div style="display: block;border-bottom: solid 1px #FFF;border-top: solid 1px #cacaca;text-indent: -9999px;height: 0px;">line</div>
			<ul class="unstyled">
				<li><a href="<%=path%>/mobileNew" class="active"><span class="icon icon-new"></span><span class="name">新配送</span></a></li>
				<li><a href= "<%=path%>/mobileAll" ><span class="icon icon-all"></span><span class="name">全部配送</span></a></li>
				<li><a href="<%=path%>/mobileUser"><span class="icon icon-user"></span><span class="name">个人中心</span></a></li>
			</ul>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
		var newListCf = new Array();
		   //重置当前tab页面
        function refreshTab() {
        	$('#new').empty();//先清空当前Tab页面
        	//并清空当前对应的存放id的数组
            newListCf = [];
        	findPtCf();//添加当前页面
        }
    	$('#tab-content-new').scroll(function(event){
    		var scrTop = $('#tab-content-new').scrollTop();
    		var boxHeight = $('#tab-content-new').height();
    		var allHeight =  $('#tab-content-new').children('.active').height();
    		if(scrTop + boxHeight == allHeight){
    			findPtCf();
    		}
    	});
         findPtCf();//初始加载
		  function findPtCf() {	 
				var oldList="";
				    //下次刷新时过滤掉已经显示的订单
			    for(var i=0;i<newListCf.length;i++) {
				    	if(i ==0) { 
				    		oldList +="'"+newListCf[i]+"'";
				    	} else {
				    		oldList +=",'"+newListCf[i]+"'";
				    	}
				 } 
				$.ajax({
					type : "post",
					url : '<%=path%>/mobileNewDetail',
					dataType : 'json',
					data :  {
						'size':5,
						'oldList':oldList,
						'currentPage':1
					},
					async : false,
					success : function(data) {
				        var  deliveryList = data.ptDeliveryList;
				        var count =data.count;
					    var newDeli="";
					   for(var i =0;i<deliveryList.length;i++){
					    var  delivery = deliveryList[i];
					    var temp ="";
					    if(delivery.status=="13")  {temp ="(派送中)";  }
					    var start = '<div class="item" onclick=ptDelivertyToDatail('+delivery.id+')>';
						var end =	'<div><span>处方编号: </span><span>'+delivery.cf_id+temp+'</span></div>'+
							'<div><span>处方时间: </span><span>'+delivery.pres_date_time+'</span></div>'+
							'<div><span>联系人: </span><span>'+delivery.name+'</span></div>'+
							'<div><span>联系电话: </span><span>'+delivery.phone_no+'</span></div>'+
							'<div><span>配送地址: </span><span>'+delivery.address+'</span></div>'+
						    '</div>';
						    if(delivery.status == '13'||delivery.status == '11') {
						    	  newDeli += start +'<div class="img imgnew"></div>' + end;
						      }
						     newListCf.push(delivery.id);
					  }
					  $('#titleString').html("新配送("+count+")");
					  $("#new").append(newDeli)
		              //console.log("allnewListList:" +newListCf.toString());
					},
					error: function(data) {
						alert("error");
					}
				});
				} 
	
	
   </script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtPharmacy"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%
String path = request.getContextPath();
EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
EngPtCf cf = (EngPtCf)request.getAttribute("ptCf");
List<EngPtDrug> drugs = (List<EngPtDrug>)request.getAttribute("drugs");
EngPtDelivery  ptDelivery = (EngPtDelivery)request.getAttribute("ptDelivery");
if(drugs==null) {drugs= new ArrayList<EngPtDrug>();}
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>详情页面</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
	<meta content="yes" name="apple-mobile-web-app-capable" /> -->
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/detail.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/platform.css"/>"/>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/jquery-1.8.3.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/bootstrap.min.js"/>"></script>
</head>
<body>
	<div id="box">
		<div class="header">
			<a href="javascript:history.go(-1);" class="home">
		         <span class="header-icon header-icon-return"></span>
		         <span class="header-name">返回</span>
			</a>
			 <a onclick="home()"class="home1">
		            <span class="header-icon header-icon-home"></span>
		            <span class="header-name">主页</span>
			 </a>
			<div class="title statusName" ><%=cf.getStatusName()%></div>
		</div>
		<div class="content">
			<div class="content_top">
				<div class="recipe">
					<span>处方：</span>
					<span class="user_recipe"><%= cf.getId()%></span>
				</div>
				<div class="name">
					<span>姓名：</span>
					<span class="user_namme"><%= ptDelivery.getName()%></span>
				</div>
				<div class="tel">
					<span>电话：</span>
					<span class="user_tel"><%= ptDelivery.getPhoneNo()%></span>
				</div>
				<div class="address">
					<span>地址：</span>
					<span clas='user_address'><%= ptDelivery.getAddress()%></span>
				</div>
				<%if(ptDelivery.getCourierId()>0){%>
				<div class="logistics">
					<span>派送员：</span><span> <%=ptDelivery.getCourierName() %></span>
				</div>
				<% }%>
				<div class="logistics">
					<%if(cf.getStatus().equals("13") || cf.getStatus().equals("11")){%>
					<span>备注：</span><span> <input id="cancelRemarkWords" ></span>
					<%} %>
				   <%if(cf.getStatus().equals("16") || cf.getStatus().equals("17")){%>
					<span>备注：</span><span> <%=cf.getCancelRemark() %></span>
					<%} %>
				</div>
			</div>
			<div class="content_footer">
			     <%if(user.getRole().equals("1002")) { //店长%>
				    <%if(cf.getStatus().equals("11")){ %><div class="rt_btn sendEnd"><a onclick="sendEndFail('<%=cf.getId()%>','17')">配送取消</a></div><%}%>
				 <%} %>
				 <%if(user.getRole().equals("1003")) { //派送员%>
                     <%if(cf.getStatus().equals("13")){ %><div class="rt_btn sendEndFail"><a onclick="sendEndFail('<%=cf.getId()%>','16')">配送失败</a></div><%}%>
				 <%}%>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
  function home() {
	  window.location.href ='<%=path%>/mobileAll';
  }
  function sendEndFail(id,status) {
	  var cancelRemark= $("#cancelRemarkWords").val();
	  if(cancelRemark == null || cancelRemark.trim() =="") {
		  alert("请填写备注");
		  return false;
	  }
	  $.ajax({
			type : "post",
			url : '<%=path%>/mobileChangePtCfStatus',
			dataType : 'json',
			data:{
				'cfId':id,
				'status':status,
				'cancelRemark':cancelRemark
				},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
					$("#cancelRemarkWords").attr("disabled","disabled");
					$('.sendEnd').hide();
					$('.sendEndFail').hide();
					$('.statusName').html(message);
				}
			}
		}); 
  }
</script>
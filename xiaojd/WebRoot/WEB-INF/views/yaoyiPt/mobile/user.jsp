<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script type="text/javascript" src="<c:url value="/scripts/mobile/query-1.8.3.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/scripts/mobile/bootstrap.min.js"/>"></script>

</head>
<body style="background:#f6f6f6;">
	<div class="container">
		<div class="header">
			<a href="javascript:history.go(-1);" class="home">
		            <span class="header-icon header-icon-return"></span>
		            <span class="header-name">返回</span>
			</a>
			<div class="title" id="titleString">个人中心</div>
		</div>
		<div class="usercenter">
			<img src="images/user_bar.jpg" style="width:100%;height:180px;" />
			<div style="margin-top:10px;display: block;border-bottom: solid 1px #FFF;border-top: solid 1px #cacaca;text-indent: -9999px;height: 0px;">line</div>
			<ul class="unstyled">
				<li><a href="orderscount.html"><span class="icon_chart"></span><span>订单统计</span></a></li>
				<li><a href="userinfo.html"><span class="icon_face"></span><span>我的资料</span></a></li>
				<li><a href="changepwd.html"><span class="icon_pwd"></span><span>修改密码</span></a></li>
				<li><a href="advice.html"><span class="icon_advice"></span><span>意见反馈</span></a></li>
				<li><a href="adduser.html"><span class="icon_adduser"></span><span>用户管理</span></a></li>
				<li><a href="about.html"><span class="icon_about"></span><span>关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;于</span></a></li>
			</ul>
			<div class="loginout"><a href="login.html">退出登录</a></div>
		</div>
		<div class="footerbtn">
			<div style="display: block;border-bottom: solid 1px #FFF;border-top: solid 1px #cacaca;text-indent: -9999px;height: 0px;">line</div>
			<ul class="unstyled">
				<li><a href="<%=path%>/mobileNew"><span class="icon icon-new"></span><span class="name">新配送</span></a></li>
				<li><a href= "<%=path%>/mobileAll" ><span class="icon icon-all"></span><span class="name">全部配送</span></a></li>
				<li><a href="<%=path%>/mobileUser" class="active"><span class="icon icon-user"></span><span class="name">个人中心</span></a></li>
			</ul>
		</div>
	</div>
</body>
</html>

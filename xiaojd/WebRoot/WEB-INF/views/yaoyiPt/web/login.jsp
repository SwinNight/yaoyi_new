<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>医配通</title>
	<style>
		*{
			padding: 0;
			margin: 0;
			list-style: none;
		}
		body,html{
			width:100%;
			height:100%;
			overflow: hidden;
		}
		h1{
			position: absolute;
			left:50%;
			top:40%;
			color:#fff;
			font-size: 40px;
			margin-left: -95px;
			font-style:italic;
			text-align: center;
		}
		.inner{
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		.inner .top{
			width:100%;
			height:40%;
			position: relative;
			background-color: #67b7b5;
		}
		.inner .top span{
			position: absolute;
			top:60%;
			left:50%;
			margin-left: -150px;
			color:#fff;
			width:300px;
			height:50px;
			font-size: 26px;
			text-align: center;
			line-height: 50px;
			font-style:normal;
		}
		.inner .content {
			width: 100%;
			height: 60%;
			background-color: #fff;
		}
		.inner .content form{
			width:290px;
			height:100px;
			padding-top: 20px;
			display: block;
			margin: 0 auto;
		}
		.inner .content form span{
			float: left;
			width:30px;
			height:30px;
			background-color: #67b7b5;
		}
		.inner .content form .pwd{
			margin-top: 20px;
		}
		.inner .content form input{
			display: block;
			float: left;
			width:250px;
			height:26px;
		}
		.btn_box{
			width:290px;
			height:80px;
			padding-top: 20px;
			margin: 0 auto;
			position: relative;
		}
		.btn_box div{
			float: left;
			color:#fff;
			width:100px;
			height:30px;
			cursor: pointer;
			margin-left: 20px;
			line-height: 30px;
			text-align: center;
			background-color: #67b7b5;
		}
		.btn_box div.cancel{
			margin-left: 50px;
		}
		a{
			position: absolute;
			right:15px;
			bottom:20px;
			color:#333;
		}
	</style>
</head>
<body>
	<div class="inner">
		<div class="top">
			<h1>Welcome</h1>
			<span>医配通药物配送系统</span>
		</div>
		<div class="content">
		
			<form id="loginInfo" action="<%=path%>/login" method="post">
				<span></span><input id="code" name="code" type="text" placeholder=" 请输入您的账号">
				<span class='pwd'></span><input id="pwd" name="pwd" type="password" class='pwd' placeholder=" 请输入您的密码">
			</form>
			
			<div class="btn_box">
				<div class="save_btn"  onclick="login()">登录</div>
				<div class="cancel"  href="<%=path%>/ptChangePassword">重置</div>
				<a href="javascript:void(0)">忘记密码?</a>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
	<script type="text/javascript">
	function login(){		
	 	if(validateUser($('#code')) && validatePwd($('#pwd'))) { 
            $('#loginInfo').attr('action', "<%=path%>/loginPt").submit();
		 } 
		return false;
	}
	
	function validateUser($p){
		if($p.val() == ''){
			$('#result').html('用户名不能为空!');
			$p.focus();
			return false;
		} else if($p.length > 20) {
			$('#result').html('用户名长度不能大于20!');
			$p.focus();
			return false;
		}
		return true;
	}
	
	function validatePwd($p){
		if($('#code').val() != '') {
			if($p.val() == ''){
				$('#result').html('密码不能为空!');
				$p.focus();
				return false;
			} else if($p.length > 30) {
				$('#result').html('密码长度不能大于30!');
				$p.focus();
				return false;
			}
			return true;
		}
		return false;
	}
	</script>
</body>
</html>
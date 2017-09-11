<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" />
<meta content="yes" name="apple-mobile-web-app-capable" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/bootstrap.min.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/platform.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/login.css"/>"/>
    <script type="text/javascript" src="<c:url value="/scripts/mobile/zepto.js"/>"></script>
   <script type="text/javascript" src="<c:url value="/scripts/mobile/jquery-1.8.3.min.js"/>"></script>
</head>
<body>
<div class="header">
	<div class="title" id="titleString">登录</div>
</div>
<img src="./images/mobile/user_bar.jpg" style="width:100%;height:160px;" />
<form id="loginInfo"  method="post">
<div class="container" >
	<form name="aspnetForm"  id="aspnetForm" class="form-horizontal">
		<div class="control-group">
			<input name="code" type="text" id="code" class="input width100 " style="background: none repeat scroll 0 0 #F9F9F9;padding: 8px 0px 8px 4px" placeholder="请输入用户名" />
		</div>
		<div class="control-group">
			<input name="pwd" type="password" id="pwd" class="width100 input" style="background: none repeat scroll 0 0 #F9F9F9;padding: 8px 0px 8px 4px" placeholder="请输入密码" />
		</div>
		<div class="control-group">
		    <label class="checkbox fl">
		        <input name="cbSaveCookie" type="checkbox" id="cbSaveCookie" style="float: none;margin-left: 0px;" /> <span>记住账号</span>
		    </label>
			<a class="fr" href="forgetPwd.html">忘记密码？</a>
		</div>
		<div class="control-group">
			<button onclick="login()" id="btnOK" class="btn-large blue button width100">立即登录</button>
		</div>
	</form>
</div>
</form>
<div class="footer">
	<div style="margin-top:15px;display: block;border-bottom: solid 1px #FFF;border-top: solid 1px #cacaca;text-indent: -9999px;height: 0px;">line</div>
	<div class="gezifooter">
		<p style="color:#bbb;">上海海据医疗 &copy; 版权所有 2017 higndata.cn</p>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
		function login(){			
		 	if(validateUser($('#code')) && validatePwd($('#pwd'))) { 
	            $('#loginInfo').attr('action', '<%=path%>/mobileLoginPt').submit();
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

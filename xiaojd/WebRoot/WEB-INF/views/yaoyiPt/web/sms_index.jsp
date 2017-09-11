<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% String path = request.getContextPath();
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");

%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Index</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/index.css"/>"/>
		<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
</head>
<body>
	<%-- 头部 Start --%>
	<div class="header">
		<h2>医配通</h2>
		<div class="login"><a href='<%=path%>/logoutPt''>注销</a></div>
		<div class="set" onclick='TgLayer("design_admin")'><a href="javascript:void(0)">设置</a></div>
		<div class="user_box" onclick='TgLayer("layer_user_center")'>
			<a href="javascript:void(0)" >
				<span><img src="./images/web/timg.jpg"  alt="头像"></span>
				<span class="user_msg">白求恩</span>
			</a>
		</div>
	</div>
	<%-- 头部 End --%>
	<%-- 中心 Start --%>
	<div class="content">
		<div class="inner">
			<%-- 搜索框 --%>
			<div class="search_box">
				<!-- <select name="" id="">
					<option value="idCard">身份证</option>
					<option value="cardNo">医保卡</option>
					<option value="phoneNo">电话</option>
					<option value="name">姓名</option>
				</select>
				<input type="text" placeholder="请输入有效信息" id='search'> -->
			<input type="button" value='短信发送' class='sendSms' style='width:120px' onclick="sendPtSms();">
				<input type="button" value='短信查询' class='serach' style='width:120px' onclick="searchPtSms();"> -->
			</div>
			<%-- 展示信息 --%>
			<div class="user_msg_a">
			<table class='msg'>
					<tr class="tit">
						<th class="w150">发送人</th>
						<th class="w200">发送时间</th>
						<th class="w200">发送单位</th>
						<th class="w200">接收人</th>
						<th class="w150">接收人手机号码</th>
						<th class="w150">短信内容</th>
						<th class="w150">短信发送状态</th>
					</tr>
				</table>
			</div>
		</div>
	</div> 
	<%-- 中心 End --%>
	<%-- 个人中心 STart Layer --%>
	
	<%-- 个人中心 End Layer --%>

<script type="text/javascript">
	var dta, newary;
	searchPtSms();
	 function searchPtSms() {
			var type = $('select').val(); //获取查询类型
			var value = $('#search').val(); //获取查询值
			$.ajax({
				type : "post",
				url : '<%=path%>/loadSms',
				dataType : 'json',
				data :  {
					'type':type,
					'value':value
				},
				async : false,
				success : function(data) {
					setInnerHtml(data);
				}
			});
	 }

	 function setInnerHtml(data){
		 $('.tit').siblings('tr').remove();
		 var dta = data.data;
		 for(var i =0;i<dta.length;i++){
				    var item = $( '<tr onclick=loadPtSmsById("'+dta[i].id+'")></tr>');
					var msg = '<td class="name w150">'+dta[i].name+'</td>'+
					 '<td class="senderTime w200">'+dta[i].sender_time+'</td>'+
					 '<td class="pharmacy w200">'+dta[i].pharmacy+'</td>'+
					 '<td class="receiver">'+dta[i].receiver+'</td>'+
					 '<td class="phoneNo w150">'+dta[i].phone_no+'</td>'+
					 '<td class="messageContent w150">'+dta[i].message_content+'</td>'+
					 '<td class="backStatus w150">'+dta[i].back_status_name+'</td>';
					$(msg).appendTo(item);
			     	$(item).appendTo('.msg');
			}
	  }
	 
	 function sendPtSms(usrId) {//当前用户
			$.ajax({
				type : "post",
				url : '<%=path%>/savePtSms',
				dataType : 'json',
				data :  {
					'userId':usrId,
				},
				async : false,
				success : function(data) {
					var  success = data.success;
					var  message =  data.message;
					if(success =="false") {
						alert(message);
					} else {
	                   alert(message);
					}
				},
				error: function(data) {
					alert("发送失败！")
				}
			});
		}
	</script>
</body>
</html>
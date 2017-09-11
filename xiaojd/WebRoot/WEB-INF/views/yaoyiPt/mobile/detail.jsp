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
List<EngPtUser>  userList = (List<EngPtUser>)request.getAttribute("ptUserList");
if(drugs==null) {drugs= new ArrayList<EngPtDrug>();}
if(userList == null) {userList= new ArrayList<EngPtUser>();}
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
				<%if(cf.getStatus().equals("16") || cf.getStatus().equals("17")){%>
					<div class="logistics">
					<span>备注：</span><span> <%=cf.getCancelRemark() %></span>
				    </div>
				<%} %>
				<%if(cf.getStatus().equals("15")){%>
					<div class="logistics">   
					<span>取药人：</span><span> <%=ptDelivery.getTakeDrugPerson() %></span>
				   </div>
				<%} %>
				<%if(ptDelivery.getCourierId()>0){ %>
				<div class="logistics">
					<span>派送员：</span><span> <%=ptDelivery.getCourierName() %></span>
				</div>
				<% } else {%>
				     <div class="logistics">
					<span>派送员：</span>
					<% if(userList.size()>0) {%>
					<select id="courier" >
                         <% for(int i=0;i<userList.size();i++) {%>
						  <option value="<%=userList.get(i).getId()%>"><%=userList.get(i).getName()%></option>
						<%} %>
					</select>
					<%}%>
				</div>
				<%} %>
			
			</div>
			<div class="content_inner">
			  <c:forEach var="drug"  items="${drugs}" >
			   <c:if test="${drug.realityQuantity > 0}">
					<div class="inner_box">
						<div class="inner_name">
							<span>药物名称：</span>
								<span>${drug.drugName}</span>
						</div>
						<div class="size">
							<span>规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：</span>
							<span>${drug.spec}</span></div>
						<div class="way">
							<span>服用方法：</span>
							<span>${drug.adminRoute}/${drug.adminFrequency}//${drug.adminDose}</span>
						</div>
					  <div class="price">
							<span>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</span>
							<span>${drug.realityQuantity}/${drug.dispenseUnit}</span>
						</div>
						<div class="price">
							<span>金&nbsp;额(元)：</span>
							<span>${drug.money}</span>
						</div>
					</div>
					</c:if>
				</c:forEach>
			</div>
			<div class="content_footer">
			     <%if(user.getRole().equals("1002")) { //店长%>
				    <%if(cf.getStatus().equals("11")){ %><div class="rt_btn startSend"><a onclick="startSend('<%=cf.getId()%>')">开始配送</a></div><%}%>
				    <%if(cf.getStatus().equals("11")){ %><div class="rt_btn startSend"><a onclick="sendEndFail('<%=ptDelivery.getId()%>','17')">配送取消</a></div><%}%>
				 <%} %>
				 <%if(user.getRole().equals("1003")) { //派送员%>
                     <%if(cf.getStatus().equals("13")){ %><div class="rt_btn sendEndFail"><a onclick="sendEndFail('<%=ptDelivery.getId()%>','16')">配送失败</a></div><%}%>
				     <%if(cf.getStatus().equals("13")){ %><div class="status suceess sendEnd" style='text-align: center' onclick="sendEndSuccess('<%=ptDelivery.getId()%>')">配送完成</div><%}%>
				  <%}%>
				     <%if(cf.getStatus().equals("15")){ %><div class="status suceess sendEnd" style='text-align: center' onclick="sendEndSuccess('<%=ptDelivery.getId()%>')">查看图片</div><%}%>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
  function startSend(id) {
	  var courier = $('#courier').val();
	  $.ajax({
			type : "post",
			url : '<%=path%>/mobilePtDelivertyToCourier',
			dataType : 'json',
			data:{'cfId':id,'courierId':courier},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
					$("#courier").attr("disabled","disabled");
					$('.startSend').hide();
					$('.statusName').html(message);
				}
			}
		}); 
  }
  
  function sendEndSuccess(DelivertyId) {
		window.location.href ='<%=path%>/mobileDatailToEndSuccess?DelivertyId='+DelivertyId;
  }
  //配送失败 ，配送取消，要填写备注
  function sendEndFail(DelivertyId,status) {
		window.location.href ='<%=path%>/mobileDatailToEndFailed?DelivertyId='+DelivertyId+'&status=' +status;
  }
</script>
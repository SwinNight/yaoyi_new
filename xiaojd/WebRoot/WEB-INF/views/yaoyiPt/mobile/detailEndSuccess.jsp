<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtPharmacy"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtSuccessImg"%>
<%@page  import="java.util.List"%>
<%@page  import="java.util.ArrayList"%>
<%
String path = request.getContextPath();
EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
EngPtCf cf = (EngPtCf)request.getAttribute("ptCf");
List<EngPtDrug> drugs = (List<EngPtDrug>)request.getAttribute("drugs");
EngPtDelivery  ptDelivery = (EngPtDelivery)request.getAttribute("ptDelivery");
EngPtSuccessImg img = (EngPtSuccessImg) request.getAttribute("img");
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
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/styles/mobile/logistics_detail.css"/>"/> --%>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/jquery-1.8.3.min.js"/>"></script>
 <script type="text/javascript" src="<c:url value="/scripts/mobile/bootstrap.min.js"/>"></script>
</head>
 <style type="text/css">
	 #imgInp{
	font-size: 100px;
	right: 0;
	top: 0;
	opacity: 0;
	width: 70px;
	height: 40px;
	position: absolute;
	display:block;
	}
</style>
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
					<span clas='user_address'  title="<%= ptDelivery.getAddress()%>"><%= ptDelivery.getAddress()%></span>
				</div>
				<%if(ptDelivery.getCourierId()>0){%>
				<div class="logistics">
					<span>派送员：</span><span> <%=ptDelivery.getCourierName() %></span>
				</div>
				<% }%>
				<div class="logistics">
					<%if(cf.getStatus().equals("13")){%>
					<span>取药人：</span><span> <input  id="takeDrugPerson"  value ="<%=ptDelivery.getName() %>"></span>
					<%} %>
				   <%if(cf.getStatus().equals("15")){%>
					<span>取药人：</span><span> <%=ptDelivery.getTakeDrugPerson() %></span>
					<%} %>
				</div>
			</div>
			<div class="content_inner">
			<form id="form1" runat="server">
				   <div>
					  <div class="pics">
					        <%if(img == null) {%>
					        <img class="pic" src="#" alt="上传图片"  style='display: none;overflow:hidden;'/>
					        <%}else { %>
					         <img class="pic" src="<%=img.getImg()%>" style="width:100%;height:100%;padding:8px;box-sizing:border-box;"/>
					         <%} %>
					  </div>
				</div>  
			</form>
			</div>
			<div class="content_footer">
				 <%if(user.getRole().equals("1003")&&user.getId()==ptDelivery.getCourierId()) { //派送员%>
				     <%if(cf.getStatus().equals("13")){ %><div class="rt_btn sendEndFail"><a id="chooseOther">上传图片</a><input  name=''上传图片  type='file' id="imgInp" " multiple/></div><%}%>
                     <%if(cf.getStatus().equals("13")){ %><div class="rt_btn sendEndFail"><a onclick="sendEndSuccess('<%=cf.getId()%>','15')">配送完成</a></div><%}%>
				 <%}%>
			</div>
		</div>
	</div>
</body>
</html>

<script type="text/javascript">
var rFilter = /^(image\/bmp|image\/gif|image\/jpeg|image\/png|image\/tiff)$/i; //控制格式
var iMaxFilesize = 2097152; //控制大小2M
var iMaxFilesize = 2097152; //控制大小2M

	var target_pic ='';
	function readURL(input) {
	if (input.files && input.files[0]) {
		var file = input.files[0];
		  if (!rFilter.test(file.type)) {
		        alert("文件格式必须为图片");
		        return;
		    }
		    if (file.size > iMaxFilesize) {
		        alert("图片大小不能超过2M");
		        return;
		    }
		var reader = new FileReader();
	   reader.onload = function (e) {
		$('#blah').attr('src', e.target.result);
		$('.pics').html('<div class="pic_box"><img class="pic" src="'+ e.target.result+'" style="width:100%;height:100%;padding:8px;box-sizing:border-box;"/></div>')
		}
			reader.readAsDataURL(input.files[0]);
	    }
	}
	$("#imgInp").change(function(){
		$("#chooseOther").html("重新上传");
	    readURL(this);
	});
//--
function home() {
	  window.location.href ='<%=path%>/mobileAll';
}
  function sendEndSuccess(id,status) {
	  var takeDrugPerson= $("#takeDrugPerson").val();
	  if(takeDrugPerson == null || takeDrugPerson.trim() =="") {
		  alert("请填写取药人");
		  return false;
	  }
	  var img = $(".pic").attr("src");//图片
	  if(img =='#') {
		  alert("请上传图片");
		  return false;
	  }
	  $.ajax({
			type : "post",
			url : '<%=path%>/mobileChangePtCfStatusSuccess',
			dataType : 'json',
			data:{
				'cfId':id,
				'status':status,
				'takeDrugPerson':takeDrugPerson,
				'img':img
				},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
					$("#takeDrugPerson").attr("disabled","disabled");
					$('.sendEndFail').hide();
					$('.statusName').html(message);
				}
			}
		}); 
  }
</script>
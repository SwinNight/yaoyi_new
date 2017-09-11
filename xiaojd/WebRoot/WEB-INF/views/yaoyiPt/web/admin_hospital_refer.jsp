<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDispense"%>
<%@page  import="java.text.DecimalFormat"%>
<%@page  import="java.util.ArrayList"%>
<%@page  import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String path = request.getContextPath();
		response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
		response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
		response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
		EngPtUser orgUser = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
		EngPtCf cf = (EngPtCf)request.getAttribute("cf");
		List<EngPtDrug> drugs = (List<EngPtDrug>)request.getAttribute("drugs");
		EngPtDispense dispense =  (EngPtDispense)request.getAttribute("dispense");
		
		if(drugs == null) {
			drugs = new ArrayList<EngPtDrug>();
		}
		
		Double  moneyNumber =0.0;
	    for(int i=0;i<drugs.size();i++) {
	    	moneyNumber +=drugs.get(i).getMoney();
		 }
	    String money =String .format("%.2f",moneyNumber);
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${cf.id}详情页面</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/refer.css"/>"/>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
</head>
<body>
	<%-- 头部 Start --%>
	<div class="header">
		<h2>医配通</h2>
		<div class="login"><a href='<%=path%>/logoutPt''>注销</a></div>
	</div>
	<%-- 头部 End --%>
	<%-- content --%>
				<div class="now_status" style="color:red;">${cf.statusName}</div>
	<div class="content">
		<div class="inner">
			<%-- 头部 End 状态 --%>
			<div class="hospital w100">
			<fieldset>
			<%-- 处方信息盒子 --%>
				<input id="patientNo"  type="hidden"  value="<%=cf.getPatientNo()%>">
			    <input id="cfId"  type="hidden"  value="<%=cf.getId()%>">
				<legend>处方信息</legend>
				<div class='type'>
					<span>费别：</span>
					<span><%=cf.getPayType()%></span>
				</div>
				<div class='medicare_card_id'>
					<span>医保卡号：</span>
					<span><%=cf.getCardNo()%></span>
				</div>
				<div class='order_num_box'>
					<span>处方号：</span>
					<span><%=cf.getId()%></span>
				</div>
				<div class="name_box">
					<span>姓名：</span>
					<span><%=cf.getName()%></span>
				</div>
				<div class="sex_box">
					<span>性别：</span>
					<span><%=cf.getSex()%></span>
				</div>
				<div class="age_box">
					<span>年龄：</span>
					<span><%=cf.getAge()%></span>
				</div>
		
				<div class="office_box">
					<span>科室：</span>
					<span><%=cf.getDepartment()%></span>
				</div>
				<div class="order_time_box">
					<span>处方日期：</span>
					<span><%=cf.getPresDateTime()%></span>
				</div>
				<div class="doctor_name_box">
					<span>医师：</span>
					<span><%=cf.getDocName()%></span>
				</div>
				<div class="prescription_box">
					<span>临床诊断：</span>
					<span><%=cf.getDiagnose()%></span>
				</div>
			</fieldset>
				</div>
			<div class="user_msg w100">
			<fieldset>
				<legend>取药信息</legend>
					<%-- 药店信息盒子 --%>
				<div class="drugstore_box  get_ways">
				
				   <div class="drugstore_a w40" id="hospital">
					   <span  class="wp80" >社区医院：</span>
					   <span><% if(dispense !=null) { %><%=dispense.getHospital()%> <%} else {%>---<%} %> </span>
					</div>
					<div class="drugstore_a w40" id="department">
					   <span  class="wp80" >发药科室：</span>
					   <span><% if(dispense !=null) { %><%=dispense.getDepartment()%> <%} else {%>---<%} %> </span>
					</div>
					 <div class="drugstore_a w40" id="doctor">
					   <span  class="wp80" >发药医生：</span>
					   <span><% if(dispense !=null) { %><%=dispense.getDoctor()%> <%} else {%>---<%} %> </span>
					</div>
				     	 <div class="drugstore_a w40" id="doctor">
					   <span  class="wp80" >发药备注：</span>
					   <span><% if(dispense !=null) { %><%=dispense.getComment()%> <%} else {%>---<%} %> </span>
					</div>
				</div>
				

				
			</fieldset>
			</div>
			<div class="drug_msg w100">
				<fieldset>
					<legend>药品信息</legend>
					<div class="drug_tit w100">
						<div class="drug_name wp300">药物名称</div>
						<div class="norms wp150">规格</div>
						<div class="usage wp150">用法</div>
						<div class="drug_num wp150">数量</div>
						<div class="price">价格</div>
					</div>
					<div class="drug_detail">
						<%-- 药物循环盒子 --%>
			     
			         <c:forEach var="drug"  items="${drugs}" >
						<div class="drug_detail_box">
							<div class="drug_names wp300">${drug.drugName}</div>
							<div class="drug_size wp150">${drug.spec}</div>
							<div class="usage wp150">${drug.adminRoute}/${drug.adminFrequency}</div>
							<div class="drug_bum wp150">${drug.quantity}/${drug.dispenseUnit}</div>
							<div class="drug_price">${drug.money}元</div>
						</div>
				      </c:forEach>
				       <div class="drug_detail_box">
							<div class="all_money"  money="<%=money%>">总计：<%=money%>元</div>
						</div>
		
						<%-- 循环盒子结束 --%>
					</div>
				</fieldset>
			</div>
			<div class="btn_box">
                  <div class="btn_return" onclick="returnMain();">返回主页</div>
                  <div class="btn_return" onclick="exportCf();">下载处方</div>
			</div>
		</div>
	</div>

<script type="text/javascript">

 function 	 returnMain(){
		window.location.href ='<%=path%>/homePt';
 }
function exportCf() {
	var  cfId  = $("#cfId").val();//处方ID
     window.open('<%=path%>/downCf?id='+cfId);  

}
    
	</script>
</body>
</html>
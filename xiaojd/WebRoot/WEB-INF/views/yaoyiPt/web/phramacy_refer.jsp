<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtPharmacy"%>
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
		List<EngPtUser> userList =(List<EngPtUser>)request.getAttribute("userList");
		EngPtDelivery  ptDelivery = (EngPtDelivery)request.getAttribute("ptDelivery");//配送订单
		EngPtPharmacy  ptPharmacy = (EngPtPharmacy)request.getAttribute("ptPharmacy");//选择的药店
		EngPtUser courier = (EngPtUser) request.getAttribute("courier");//快递员

         if(userList == null) {
        	 userList = new ArrayList<EngPtUser>();
         }
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
	<title>处方${cf.id}详情</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/refer.css"/>"/>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
</head>
<body>
	<%-- 头部 Start --%>
	<div class="header">
		<h2>医配通</h2>
			<div class="login"><a href='<%=path%>/logoutPt''>注销</a></div>
	<!-- 	<div class="set" onclick='TgLayer("design_admin")'><a href="javascript:void(0)">设置</a></div>
		<div class="user_box" onclick='TgLayer("layer_user_center")'>
			<a href="javascript:void(0)" >
				<span><img src="./images/web/timg.jpg" alt="头像"></span>
				<span class="user_msg">白求恩</span>
			</a>
		</div> -->
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
				<%-- 收货人信息 盒子 --%>
				<div class="user_box ">
					<div class="address w40">
						<span  class="wp80">住址：</span>
						<span class='address_detail'  id='delivery_address'>
					 <% if(ptDelivery !=null) { %>
					       <%=ptDelivery.getAddress()%>
					 <%} else{ %><%=cf.getAddress()%><%}%>
						</span>
					</div>
					<div class="user_name w230">
						<span  class="wp80">收货人：</span>
						<span  class="wp80 user_name_detail" id='delivery_name'>
					<% if(ptDelivery !=null) { %>
					       <%=ptDelivery.getName()%>
					 <%} else{ %><%=cf.getName()%><%}%>
						</span>
					</div>
					<div class="usertell w230">
						<span  class="wp80">电话：</span>
						<span  class="wp80 usertell_detail" id='delivery_phone'>
					    <% if(ptDelivery !=null) { %>
					       <%=ptDelivery.getPhoneNo()%>
					   <%} else{ %><%=cf.getPhoneNo()%><%}%>
                          </span>
					</div>				
				</div>
				<%-- 药店信息盒子 --%>
				<div class="drugstore_box ">
					<div class="drugstore_a w40"  id="pharmacy">
					   <span  class="wp80">配送药房：</span>
					   <span><% if(ptPharmacy !=null) { %><%=ptPharmacy.getUrbanArea()%>---<%=ptPharmacy.getPharmacy()%> <%}%></span>
					</div>
					<div class="manager w230">
						<span  class="wp80">店长：</span>
						<span  id="leader" class="wp80">	      
						    <% if(ptPharmacy !=null) { %>
					        <%=ptPharmacy.getLeader()%>
					        <%} else {%>-----<%}%>
					        </span>
					</div>
					<div class="manager_tell w230">
						<span  class="wp80">电话：</span>
						<span id="leader_phone">		    
						   <% if(ptPharmacy !=null) { %>
					        <%=ptPharmacy.getLeaderPhone()%>
					        <%} else {%>-----<%}%></span>
					</div>
				</div>
				
					<%-- 药店信息盒子 --%>
				<div class="drugstore_box  get_ways">
					<div class="drugstore_a w40" id="waysMain">
					   <span  class="wp80" >取药方式：</span>
					   <span><% if(ptDelivery !=null) { %><%=ptDelivery.getWays()%> <%}%></span>
					</div>
			     <% if(cf.getStatus().equals("22")||cf.getStatus().equals("14")||cf.getStatus().equals("23")) { //药店(未付)%>
				  <div class="drugstore_a w40" id="takeDrug">
					   <span  class="wp80" >取药人：</span>
					   <span><input style='width:156px;' id="takeDrugPerson"  value ="<%=ptDelivery.getName() %>"></span>
					</div>
		     	<%} %>
			    <% if(cf.getStatus().equals("15")||cf.getStatus().equals("18")||cf.getStatus().equals("24")) { %>
				  <div class="drugstore_a w40" id="takeDrug">
					   <span  class="wp80" >取药人：</span>
					   <span><% if(ptDelivery !=null) { %><%=ptDelivery.getTakeDrugPerson()%> <%}%> </span>
					</div>
			     <%} %>
			      <% if(cf.getStatus().equals("22")) {//审核人 %>
				  <div class="drugstore_a w40" id="aduitDrug">
					   <span  class="wp80" >审核人：</span>
					   <span><input style='width:156px;' id="aduitDrugPerson" ></span>
					</div>
			       <%}  else {%>
			             <% if(cf.getAduitDrugPerson() !=null &&!cf.getAduitDrugPerson().equals("")) { %>
			      	      <div class="drugstore_a w40" id="aduitDrug1">
					      <span  class="wp80" >审核人：</span>
					      <span><%=cf.getAduitDrugPerson()%></span>
					       </div>
					   <%}%> 
			       <%} %>
			        <% if(cf.getStatus().equals("11") ||cf.getStatus().equals("22") ||cf.getStatus().equals("14")) {//备注 %>
				  <div class="drugstore_a w40" id="cancelRemark">
					   <span  class="wp80" >备注：</span>
					   <span><input   style='width:156px;' id="cancelRemarkWords" ></span>
					</div>
			       <%} %>
			       <% if(cf.getStatus().equals("17")||cf.getStatus().equals("19") ||cf.getStatus().equals("25")   ) { %>
			      	      <div class="drugstore_a w40" id="cancelRemark">
					      <span  class="wp80" >备注：</span>
					      <span><%=cf.getCancelRemark()%></span>
					       </div>
				<%}%> 
				</div>
	             			<% if(cf.getStatus().equals("11")||cf.getStatus().equals("12")) { %>
				<%-- 到快递员 --%>
				<div class="drugstore_box  courierlList">
					<div class="drugstore_a w40"  >
					   <span  class="wp80">配送员：</span>
					   <select class="courier"  width="180px" id="courierId"  >
				           <option value=""></option>
				           <c:forEach var="user"  items="${userList}" >
					        <option value="${user.id}">${user.name}</option>
				           </c:forEach>
				      </select>
					</div>
					</div>
			    <%} %>
			    <% if(cf.getStatus().equals("13")||cf.getStatus().equals("15")||cf.getStatus().equals("16")) { %>
				<%-- 快递员配送--%>
				<div class="drugstore_box ">
					<div class="drugstore_a w40"  id="courierIdList1">
					   <span  class="wp80">配送员：</span>
					   <span  class="wp80"><%=courier.getName()%></span>
					</div>
			    </div>
			    <%} %>
			</fieldset>
			</div>
			<div class="drug_msg w100">
				<fieldset>
					<legend>药品信息</legend>
					<div class="drug_tit w100">
						<div class="drug_name wp300">药物名称</div>
						<div class="norms wp150">规格</div>
						<div class="usage wp150">用法</div>
						<div class="drug_num wp150">医院数量</div>
						<div class="drug_num wp150">实际数量</div>
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
							<div class="drug_bum wp150">
							<input  class="drug_quantity_bak"  type="hidden"    drug_id ="${drug.id}"  quantity="${drug.quantity}" drug_money="${drug.money}"  drug_money_now="${drug.money}" />
							<% if(cf.getStatus().equals("22")) { %><input  class="drug_quantity" style='width:30px;'type="text" value="${drug.realityQuantity}"/><%} else{%>${drug.realityQuantity}<%} %>/${drug.dispenseUnit}</div>
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
			     <% if(cf.getStatus().equals("11")) { //药房(院付)%>
				      <div class="btn_status btn_13" onclick="changePtCfStatusToCour('13')" >到派送员</div>
				      <div class="btn_status btn_17" onclick="changePtCfStatusByAduit('17')" >配送取消</div>
				  <%}%>
<%-- 				<% if(cf.getStatus().equals("12")) { //派送审核通过%>
				      <div class="btn_status btn_13" onclick="changePtCfStatusToCour('13')" >到派送员</div>
				      <div class="btn_status btn_17" onclick="changePtCfStatusByAduit('17')" >配送取消</div>
				  <%}%> --%>
				  <% if(cf.getStatus().equals("14")) { //自取(院付)%>
				      <div class="btn_status btn_18" onclick="changePtCfStatusToOwnPayInHospital('18')" >自取成功(院付)</div>
				  	  <div class="btn_status btn_19" onclick="changePtCfStatusByAduit('19')" >自取失败(院付)</div>
				  <%}%>
				  <% if(cf.getStatus().equals("22")) { //药店(未付)%>
				      <div class="btn_status btn_23" onclick="changePtCfToPayP('23')" >药店付款</div>
				  	  <div class="btn_status btn_24" onclick="changePtCfStatusToOwnPayInPharmacy('24')" >自取(店付)</div>
				  	  <div class="btn_status btn_25" onclick="changePtCfStatusByAduit('25')" >自取取消(未付)</div>
				  <%}%>
				  	<% if(cf.getStatus().equals("23")) { //药店付款%>
				  	  <div class="btn_status btn_26" onclick="changePtCfStatusToOwnPayInPharmacy('24')" >自取(店付)</div>
				  <%}%>
                  <div class="btn_return" onclick="returnMain();">返回主页</div>
                  <div class="btn_return" onclick="exportCf();">下载处方</div>
			</div>
		</div>
	</div>
	<%-- content --%>
	<div class="layer_address layer">
		<div class="layer_tit">修改地址<span class='close'></span></div>
		<div class="layer_content" style='padding:0;margin:0;'>
			<div class="edit">
			<%-- 修改地址提交 --%>
				<form action="">
					<input type="text" class="edit_user_name" placeholder="请输入姓名">
					<input type="text" class="edit_user_tell" placeholder="请输入电话">
					<input type="text" class="edit_user_add" placeholder="请输入地址">
					<a href="javascript:void(0)" class="cancel_a">取消</a>
					<a href="javascript:void(0)" class="save">保存</a>
				</form>
				
			</div>
			<div class="add_new_address">新增地址</div>
		</div>
	</div>
	<%-- 个人中心 STart Layer --%>
   
	<%-- 个人中心 End Layer --%>
	<div class="layer_min">
	     <input id="delete_address_id"  type="hidden" >
		<div class="lm_tit">是否要删除<span></span>?</div>
		<div class="lm_content">
			<div class="lm_btn_box">
				<span class='yes'>是</span>
				<span class='no'>否</span>
			</div>
		</div>
	</div>
	
<%-- 	  	<form method="POST" action="#"  id="cfDown">
	  	   <input name="cfId" value="<%=cf.getId()%>">
	  	</form> --%>
<script type="text/javascript">

//药品数量修改
$(".drug_quantity").keyup(function () {  
	var initial_quantity =  $(this).prev().attr("quantity");//初始数量
	var priceHtml = $(this).parent().next();
	var initial_price = parseFloat($(this).prev().attr("drug_money"));//初始价格
	var now_price = parseFloat($(this).prev().attr("drug_money_now"));//当前价格
	var last_money =0;
	var total_money = parseFloat($(".all_money").attr("money"));
	//
    if(isNaN(initial_price) || isNaN(now_price)) {
    	alert("价格有误");
    	return false;
    }
	var edit_quantity = parseInt($(this).val());
	if (isNaN(edit_quantity) ) {
		$(this).val(0);
		priceHtml.html("0元");
		$(this).prev().attr("drug_money_now",0);
		last_money =0;
	}else if(edit_quantity < 0)  {
		$(this).val(initial_quantity);
		priceHtml.html(initial_price+"元");
		$(this).prev().attr("drug_money_now",initial_price);
		last_money = initial_price;
	} else {	
		if(edit_quantity >initial_quantity) {
			alert("数量不能超过"+initial_quantity);
			$(this).val(initial_quantity);
			priceHtml.html(initial_price+"元");
			last_money =initial_price;
			$(this).prev().attr("drug_money_now",initial_price);
		} else {
			last_money=edit_quantity/initial_quantity*initial_price;
	        last_money=Math.round(last_money*100)/100; 
			priceHtml.html(	last_money+"元");
			$(this).val(edit_quantity);
			$(this).prev().attr("drug_money_now",last_money);
		}
	}

	//alert(total_money +"|" + last_money +"|" +now_price);
	total_money = total_money + last_money - now_price;

	total_money=Math.round(total_money*100)/100; 
	$(".all_money").attr("money",total_money);
	$(".all_money").html("总计："+total_money+"元");
}) 

  	// 确认收款医院收费
    function 	changePtCfToPayP(status) {
	var aduitDrugPerson = $("#aduitDrugPerson").val();
	var cancelRemarkWords = $("#cancelRemarkWords").val();
	if(aduitDrugPerson == null || aduitDrugPerson.trim()=="") {
		if(status =="25" || status=="23") {
			alert("请填写审核人");
			return false;
		}
	}
		 var druglist = "";
		 $(".drug_quantity").each(function(){
			 var drug_id =$(this).prev().attr("drug_id");
			 var drug_quantity = $(this).val();//实际数量
			 druglist += drug_id+":" +drug_quantity +"|";
		 });
		var  cfId  = $("#cfId").val();//处方ID
		$.ajax({
			type : "post",
			url : '<%=path%>/changePtCfToPay',
			dataType : 'json',
			data :  {
				'cfId':cfId,
				'status':status,
				'aduitDrugPerson':aduitDrugPerson.trim(),
                'druglist':druglist
			},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
				$(".drug_quantity").attr("disabled","disabled");
                $(".now_status").html(message);
                $(".btn_status").hide();
                $("#aduitDrugPerson").attr("disabled","disabled");
                if(status =="23") {
             	   $(".btn_24").show();//显示
             	   $("#takeDrug").show();//取药人
             	   $("#cancelRemark").hide();
                }
				}
			},
			error: function(data) {
				alert("保存失败！")
			}
		});
	}
       $("#cfDown").hide();//隐藏  
	 function 	 returnMain(){
			window.location.href ='<%=path%>/homePt';
	 }
	 
	 function exportCf() {
			var  cfId  = $("#cfId").val();//处方ID
		 window.open('<%=path%>/downCf?id='+cfId);  
         <%-- $("#cfDown").attr("action", "<%=path%>/exportCf").submit();  --%>
	 }
	 
	 
     var status =  '<%=cf.getStatus()%>';
     if(status =="22") {
        $("#takeDrug").hide();//取药人
   	    $(".btn_24").hide();//隐藏
      } 

     function 	changePtCfStatusByAduit(status) {//要审核人
		var  cfId  = $("#cfId").val();//处方ID
		var aduitDrugPerson = $("#aduitDrugPerson").val();
		var cancelRemarkWords = $("#cancelRemarkWords").val();
		if(aduitDrugPerson == null || aduitDrugPerson.trim()=="") {
			if(status =="25" ) {
				alert("请填写审核人");
				return false;
			}
		}
		if(cancelRemarkWords == null || cancelRemarkWords.trim() =="") {
			if(status =="17") {
				alert("配送取消请填写备注");
				return false;
			}
			if(status =="19") {
				alert(" 自取失败(院付)请填写备注");
				return false;
			}
			if(status =="25") {
				alert("自取取消(未付)请填写备注");
				return false;
			}		

		}
		$.ajax({
			type : "post",
			url : '<%=path%>/changePtCfStatusByAduit',
			dataType : 'json',
			data :  {
				'cfId':cfId,
				'status':status,
				'aduitDrugPerson':aduitDrugPerson,
				'cancelRemark':cancelRemarkWords
			},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
                   $(".now_status").html(message);
                   $(".btn_status").hide();
                   $("#aduitDrugPerson").attr("disabled","disabled");
                  if(status =="17") {
                      $(".courierlList").hide();//隐藏配送人
                 	  $("#cancelRemarkWords").attr("disabled","disabled");
                  }
                 if(status =="18") {
                	 $("#cancelRemark").attr("disabled","disabled");
                	  $("#takeDrug").hide();//取药人
                 }
                 if(status =="19") {
                	  $("#takeDrug").hide();//取药人
                	 $("#cancelRemarkWords").attr("disabled","disabled");
                 }
                 if(status =="25") {
                  	 $("#cancelRemarkWords").attr("disabled","disabled");
               	    $("#takeDrug").hide();//取药人
                 }
				}
			},
			error: function(data) {
				alert("保存失败！")
			}
		});
	}
     
     
     function 	changePtCfStatusToOwnPayInHospital(status) {
 		var  cfId  = $("#cfId").val();//处方ID
 		var takeDrugPerson =  $("#takeDrugPerson").val();//取药人
 		if(takeDrugPerson ==null || takeDrugPerson=="") {
 			alert("请填写取药人");
 			return;
 		}
 		$.ajax({
 			type : "post",
 			url : '<%=path%>/changePtCfStatusToOwnPayInHospital',
 			dataType : 'json',
 			data :  {
 				'cfId':cfId,
 				'status':status,
 				'takeDrugPerson':takeDrugPerson
 			},
 			async : false,
 			success : function(data) {
 				var  success = data.success;
 				var  message =  data.message;
 				if(success =="false") {
 					alert(message);
 				} else {
                    alert(message);
                 	  $("#cancelRemark").hide();
                    $("#aduitDrugPerson").attr("disabled","disabled");
                    $("#takeDrugPerson").attr("disabled","disabled");
                    $(".now_status").html(message);
                    $(".btn_status").hide();
                    
 				}
 			},
 			error: function(data) {
 				alert("保存失败！")
 			}
 		});
 	}
     
     
     function 	changePtCfStatusToOwnPayInPharmacy(status) {
  		var  cfId  = $("#cfId").val();//处方ID
  		var takeDrugPerson =  $("#takeDrugPerson").val();//取药人

  		if(takeDrugPerson ==null || takeDrugPerson=="") {
  			alert("请填写取药人");
  			return;
  		}
  		$.ajax({
  			type : "post",
  			url : '<%=path%>/changePtCfStatusToOwnPayInPharmacy',
  			dataType : 'json',
  			data :  {
  				'cfId':cfId,
  				'status':status,
  				'takeDrugPerson':takeDrugPerson
  			},
  			async : false,
  			success : function(data) {
  				var  success = data.success;
  				var  message =  data.message;
  				if(success =="false") {
  					alert(message);
  				} else {
                     alert(message);
                  	  $("#cancelRemark").hide();
                     $("#takeDrugPerson").attr("disabled","disabled");
                     $(".now_status").html(message);
                     $(".btn_status").hide();
  				}
  			},
  			error: function(data) {
  				alert("保存失败！")
  			}
  		});
  	}
       
	function changePtCfStatusToCour(status) {
		var cfId  = $("#cfId").val();//处方ID
		var courierId = $("#courierId").val(); 
		if(courierId ==null || courierId =="") {
			 alert("请填写配送人员！");
			 return false;
		}
		$.ajax({
				type : "post",
				url : '<%=path%>/ptDelivertyToCourier',
				dataType : 'json',
				data :  {
					'cfId':cfId,
					'courierId':courierId
				},
				async : false,
				success : function(data) {
					var  success = data.success;
					var  message =  data.message;
					if(success =="false") {
						alert(message);
					}  else {
					 	  $("#cancelRemark").hide();
						 $("#courierId").attr("disabled","disabled");
						 $(".btn_status").hide();
						 $(".now_status").html(message);
					}
				},
				error: function(data) {
					alert("保存失败！")
				}
			});
	}
	  


	</script>
</body>
</html>
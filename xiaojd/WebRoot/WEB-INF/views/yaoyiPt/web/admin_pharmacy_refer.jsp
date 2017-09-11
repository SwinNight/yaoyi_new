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
		List<String> areaList =(List<String>)request.getAttribute("areaList");
		EngPtDelivery  ptDelivery = (EngPtDelivery)request.getAttribute("ptDelivery");//配送订单
		EngPtPharmacy  ptPharmacy = (EngPtPharmacy)request.getAttribute("ptPharmacy");//选择的药店
		EngPtUser courier = (EngPtUser) request.getAttribute("courier");//快递员
	    
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
		<!-- <div class="set" onclick='TgLayer("design_admin")'><a href="javascript:void(0)">设置</a></div>
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
					 <% if(cf.getStatus().equals("21")||cf.getStatus().equals("10") || cf.getStatus().equals("1")) { %><div class="address wp20 user_adress"><img src="./images/web/address.png" alt="修改地址"></div><%} %>
					
				</div>
				<%-- 药店信息盒子 --%>
					 <% if(!cf.getStatus().equals("7")) {%>
				<div class="drugstore_box  pharmacy_box">
					<div class="drugstore_a w40"  id="pharmacy">
					   <span  class="wp80">配送药房：</span>
					   <select class="area"  width="180px" id="district"   onchange="selectPharmacy()"  >
				           <option value=""></option>
				           <c:forEach var="area"  items="${areaList}" >
					        <option value="${area}">${area}</option>
				           </c:forEach>
				      </select>
					  <select class="pharmacy"  width="220px" id="pharmacyList"  onchange="changleader()">
					    <%--药房名  --%>
				      </select>
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
					 <% if(cf.getStatus().equals("10") || cf.getStatus().equals("1")) { %><div class="address wp20 drug_address"><img src="./images/web/address.png" alt="修改地址"></div><%}%>
				</div>
				<%} %>
				
					<%-- 药店信息盒子 --%>
				<div class="drugstore_box  get_ways">
				      <% if(!cf.getStatus().equals("7")) {%>
					<div class="drugstore_a w40" id="waysMain">
					   <span  class="wp80" >取药方式：</span>
					   <select class="way"  width="180px" id="way"   onchange="selectPharmacy()"  >
				            <option value="自取">自取</option>
					        <option value="配送">配送</option>
				      </select>
					</div>
				 <% if(cf.getStatus().equals("15")||cf.getStatus().equals("18")||cf.getStatus().equals("24")) { %>
				  <div class="drugstore_a w40" id="takeDrug">
					   <span  class="wp80" >取药人：</span>
					   <span><% if(ptDelivery !=null) { %><%=ptDelivery.getTakeDrugPerson()%> <%}%> </span>
					</div>
			       <%} %>
			       <%} %>
			       
			          <% if(cf.getStatus().equals("1")) {//审核人 %>
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

			       <% if(cf.getStatus().equals("1")) {//审核人 %>
				  <div class="drugstore_a w40" id="cancelRemark">
					   <span  class="wp80" >取消备注：</span>
					   <span><input style='width:156px;' id="cancelRemarkWords" ></span>
					</div>
			       <%} %>
			       <% if(cf.getStatus().equals("7")||cf.getStatus().equals("16")||cf.getStatus().equals("17")||cf.getStatus().equals("19")||cf.getStatus().equals("25")) { %>
			      	      <div class="drugstore_a w40" id="cancelRemark">
					      <span  class="wp80" >取消备注：</span>
					      <span><%=cf.getCancelRemark()%></span>
					       </div>
				<%}%> 

			       
				</div>
				
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
							<% if(cf.getStatus().equals("1")) { %><input  class="drug_quantity" style='width:30px;'type="text" value="${drug.realityQuantity}"/><%} else{%>${drug.realityQuantity}<%} %>/${drug.dispenseUnit}</div>
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
				  <% if(cf.getStatus().equals("1")) { %><div class="btn_status btn_pay" onclick="changePtCfToPayHospital('10')" >确认收费</div><%}%>
				  <% if(cf.getStatus().equals("1")) { %><div class="btn_status btn_no_pay" onclick="changePtCfStatus('21')" >未收费</div><%}%>
				  <% if(cf.getStatus().equals("1")) { %><div class="btn_status btn_cancel" onclick="changePtCfStatus('7')" >处方取消</div><%}%>
                  <% if(cf.getStatus().equals("1")||cf.getStatus().equals("10")||cf.getStatus().equals("21")) { %> <div class="btn_status btn_delivery" onclick="saveDelivery();" disabled="disabled">确认分配</div><%}%>
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
	total_money = total_money + last_money - now_price;
	total_money=Math.round(total_money*100)/100; 
	$(".all_money").attr("money",total_money);
	$(".all_money").html("总计："+total_money+"元");
}) 
	 function 	 returnMain(){
			window.location.href ='<%=path%>/homePt';
	 }
function exportCf() {
	var  cfId  = $("#cfId").val();//处方ID
 window.open('<%=path%>/downCf?id='+cfId);  
 <%-- $("#cfDown").attr("action", "<%=path%>/exportCf").submit();  --%>
}
     var status =  '<%=cf.getStatus()%>';
	 var area =   '<% if(ptPharmacy !=null) { %><%=ptPharmacy.getUrbanArea()%> <%}%>';
	 var pharmacy = '<% if(ptPharmacy !=null) { %><%=ptPharmacy.getPharmacy()%> <%}%>';
	 var leader_name ='<% if(ptPharmacy !=null) { %><%=ptPharmacy.getLeader()%> <%}%>';
	 var leader_phone ='<% if(ptPharmacy !=null) { %><%=ptPharmacy.getLeaderPhone()%> <%}%>';
	 var ways ='<% if(ptDelivery !=null) { %><%=ptDelivery.getWays()%> <%}%>';
    if(status =="1") {
    	  $(".btn_delivery").hide();//隐藏
    } else {
    	if(status =="21") {//市院未付,只能自取
   		     $(".way").remove();//删除下拉框
    		 $("#waysMain").append("<span>自取</span>");
    	} 
    	$(".btn_delivery").show();//隐藏
    }
	 if(area !="") {
		 $(".area").remove();//删除下拉框
		 $(".pharmacy").remove();//删除下拉框
		 $("#pharmacy").append("<span>"+area+"-"+pharmacy+"</span>");
	 }
	 if(!(status =='1' ||status =='10'||status =="21")) {
		 $(".way").remove();//删除下拉框
		 $("#waysMain").append("<span>"+ways+"</span>");
	 }
	 if(status =='7') {
		 $("#waysMain").hide();
		 $(".pharmacy_box").hide();
	 }
	 
	   	// 确认收款医院收费
    function 	changePtCfToPayHospital(status) {
		var  aduitDrugPerson =$("#aduitDrugPerson").val();
		 if(aduitDrugPerson.trim() =="") {
				   alert("付款收费请填审核人");
				   return false;
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
				   if(status =="10") {
					   $('.btn_pay').hide();
	   				   $('.btn_no_pay').hide();
	   				   $('.btn_delivery').show(); 
	   			       $("#cancelRemark").hide();
	   			       $(".btn_cancel").hide();
	   		           $("#aduitDrugPerson").attr("disabled","disabled");
				   }
				}
			},
			error: function(data) {
				alert("保存失败！")
			}
		});
	}

	   //形成配送订单
	   	// 确认收款
       function 	changePtCfStatus(status) {
		var  aduitDrugPerson =$("#aduitDrugPerson").val();
		var  cancelRemarkWords = $("#cancelRemarkWords").val();
		if(status=='10') {
			   if(aduitDrugPerson.trim() =="") {
				   alert("付款收费请填审核人");
				   return false;
			   }
		 }
		if(status =="7") {
			   if(aduitDrugPerson.trim() =="") {
				   alert("处方取消请填审核人");
				   return false;
			   }
			   if(cancelRemarkWords.trim() =="") {
				   alert("处方取消请填写备注");
				   return false;
			   }
		}
		var  cfId  = $("#cfId").val();//处方ID
		$.ajax({
			type : "post",
			url : '<%=path%>/changePtCfStatus',
			dataType : 'json',
			data :  {
				'cfId':cfId,
				'status':status,
				'aduitDrugPerson':aduitDrugPerson.trim(),
				'cancelRemark':cancelRemarkWords.trim()
			},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
                   $(".now_status").html(message);
   				   if(status =="21") {
   					$(".drug_quantity").attr("disabled","disabled");
   					   $('.btn_pay').hide();
   	   				   $('.btn_no_pay').hide();
   	   				   $('.btn_delivery').show(); 
   	   			       $("#cancelRemark").hide();
   	   		           $(".btn_cancel").hide();
   				       $(".way").remove();
   				       $("#aduitDrug").hide();
   		    		   $("#waysMain").append("<span>自取</span>");
   				   }
   				   if(status =="7") {
   					 $(".drug_quantity").attr("disabled","disabled");
   					   $("#waysMain").hide();
   					   $(".pharmacy_box").hide();
   					   $('.btn_pay').hide();
   	   				   $('.btn_no_pay').hide();
   	   				   $('.btn_delivery').hide();
   	   			       $("#aduitDrugPerson").attr("disabled","disabled");
   					   $("#cancelRemarkWords").attr("disabled","disabled");
   		               $(".btn_status").hide();
   				   }
				}
			},
			error: function(data) {
				alert("保存失败！")
			}
		});
	}
	function saveDelivery() {
		var  cfId  = $("#cfId").val();//处方ID
		var  pharmacyId  = $("#pharmacyList").val();//药房ID
		var  address =  $("#delivery_address").html();//收件人地址
		var  phoneNo =  $("#delivery_phone").html();//收件人联系电话
		var  name =  $("#delivery_name").html();//收件人
		var  remark ="订单生成"//备注
		var way =$("#way").val();//配送方式
		if(way ==null || way =="") {
			way ="自取";
		}
		if(name.trim() =="") {
			alert("请填写收件人");
			return false;
		}
		if(address.trim() =="") {
			alert("请填写收件人地址");
			return false;
		}
		if(phoneNo.trim() =="") {
			alert("请填写收件人手机号");
			return false;
		}
		if(pharmacyId =="" ||pharmacyId == null) {
			alert("请选择药房");
			return false;
		}
		$.ajax({
				type : "post",
				url : '<%=path%>/saveCfDeliverInfo',
				dataType : 'json',
				data :  {
					'cfId':cfId,
					'pharmacyId':pharmacyId,
					'address':address,
					'phoneNo':phoneNo,
					'name':name,
					'remark':remark,
				    'way':way
				},
				async : false,
				success : function(data) {
					var  success = data.success;
					var  message =  data.message;
					if(success =="false") {
						alert(message);
					}  else {
						 $(".area").attr("disabled","disabled");
						 $(".pharmacy").attr("disabled","disabled");
						 $(".way").attr("disabled","disabled");
						 $(".drug_address").hide();
						 $(".user_adress").hide();
						 $(".btn_delivery").hide();
						 $(".now_status").html(message);
					}
				},
				error: function(data) {
					alert("保存失败！")
				}
			});
		
	}
	  
	
	function selectPharmacy() {
		var area =$("#district").val();
		$("#pharmacyList").html("");
		$("#leader").html("");
		$("#leader_phone").html("");
	   if(area !='') {
			$.ajax({
				type : "post",
				url : '<%=path%>/ptGetPharmacyByArea',
				dataType : 'json',
				data :  {
					'area':area
				},
				async : false,
				success : function(data) {
					setPharmacyHtml(data);
				}
			});
	   }
	}
	
	 function setPharmacyHtml(data){
		
		  var pharmacyList = data;
		  for(var i=0;i<pharmacyList.length;i++){
			  var  pharmacy =pharmacyList[i];
			  if(i==0) {
				  $("#leader").html(pharmacy.leader);
				  $("#leader_phone").html(pharmacy.leaderPhone);
				  var html= '<option selected="selected" leader ="'+pharmacy.leader+'" leader_phone ="'+pharmacy.leaderPhone+'" value="'+pharmacy.id+'" id="'+pharmacy.id+'">'+pharmacy.pharmacy+'</option>';
			  }  else {
			       var html= '<option leader ="'+pharmacy.leader+'" leader_phone ="'+pharmacy.leaderPhone+'" value="'+pharmacy.id+'" id="'+pharmacy.id+'">'+pharmacy.pharmacy+'</option>';
			  }
			  $("#pharmacyList").append(html);
		  }
	  }
	 
	 function changleader(){
		  var pharmacyId =$("#pharmacyList").val();
		  $("#leader").html($("#" +pharmacyId).attr("leader"));
		  $("#leader_phone").html($("#" +pharmacyId).attr("leader_phone"));
	 }

	// 点击修改地址
	$('.user_adress').click(function () {
		var patientNo = $("#patientNo").val();
		selectDeliveryAddressBy(patientNo);
		$('.layer_address ').css({'display':"block"});
	})
	
/* 	// 点击修改配送药房
	$('.drug_address').click(function () {
		$('select').attr("disabled",false);
	}) */

	
	// 关闭地址弹层
	$('.close').click(function () {
		$(this).parent().parent().css({'display':"none"})
	})

	//初始时加载病人地址信息
	function selectDeliveryAddressBy(patientNo) {
		 if(patientNo !='') {
				$.ajax({
					type : "post",
					url : '<%=path%>/loadPtDeliveryAddressByPatientNo',
					dataType : 'json',
					data :  {
						'patientNo':patientNo
					},
					async : false,
					success : function(data) {
						setDeliveryAddressHtml(data);
					}
				});
		   }
		  var edit ='<div class="edit">'+
          '<form action="">'+
           '<input   type="hidden"  class="edit_address_id">'+
	       ' <input type="text" class="edit_user_name" placeholder="请输入姓名">'+
	        '<input type="text" class="edit_user_tell" placeholder="请输入电话">'+
	        '<input type="text" class="edit_user_add" placeholder="请输入地址">'+
	         '<a href="javascript:void(0)" class="save">保存</a>'+
	        ' <a href="javascript:void(0)" class="cancel_a">取消</a>'+
           '</form>'+
          ' </div><div class="add_new_address">新增地址</div>';
       $(".layer_content").append(edit);
	}


	function setDeliveryAddressHtml(data) {
		  $(".layer_content").html("");
		  var addressList = data;
		  if(addressList ==null) {
			  return false;
		  }
		  for(var i = 0;i<addressList.length;i++){
			  var  address = addressList[i];
			  var html =
				   '<div class="layer_user_address">' +
			       '	<input class="address_id"   type="hidden"   value="'+address.id+'">'+
					'<span class="layer_user_name">'+address.name+'</span>'+
					'<span class="layer_user_tell">'+address.phoneNo+'</span>'+
					'<span class="layer_user_add">'+address.address+'</span>'+
					'<a href="javascript:void(0)"  class="layer_user_remvap">编辑</a>'+
					'<a href="javascript:void(0)" class="layer_user_remove">删除</a></div>';
			  $(".layer_content").append(html);
		  } 	
	}
	

			$(".layer_content").delegate(".add_new_address","click",function(){
				$('.edit_address_id').val('');
				$('.edit_user_name').val('');
				$('.edit_user_tell').val('');
				$('.edit_user_add').val('');
				$('.edit').css({"top":190})
				$('.edit').toggle();
			});
			// 编辑事件
			$(".layer_content").delegate(".layer_user_remvap","click",function(event){
				var  e = event || window.event;
				$(this).parent().addClass('adx');
				$('.edit').children('div').css({'border-color':'rgb(35, 99, 195)'});
				var nm = $(this).siblings('.layer_user_name').text();
				var tel  = $(this).siblings('.layer_user_tell').text();
				var ads  = $(this).siblings('.layer_user_add').text();
				var address_id =$(this).siblings('.address_id').val();
				$('.edit .edit_address_id').val(address_id);
				$('.edit .edit_user_name').val(nm);
				$('.edit .edit_user_tell').val(tel);
				$('.edit .edit_user_add').val(ads);
				$('.edit').css({'display':"block"});
				$(this).parent().css({'opacity':'.01'});
				$('.edit').css({'top':e.pageY-110,'display':"block"});
				$(this).siblings('.layer_user_name');
				$(this).parent().addClass('check').siblings('').removeClass('check');
				$(this).parent().css({'position':'relative','left':"-9999px"});//防止重叠
			})
			// 保存编辑事件
			$(".layer_content").delegate(".save","click",function(event){
	
				var etTop = $('.edit').css('top');
				var editId =$('.edit .edit_address_id').val();
				var editnm = $('.edit .edit_user_name').val();
				var edittel = $('.edit .edit_user_tell').val();
				var editadd = $('.edit .edit_user_add').val();
			    var patientNo = $("#patientNo").val();
				if(editnm == '')  {
					$('.edit .edit_user_name').css({'border-color':'red'});
					return false;
				};
				if(edittel == '') {
					$('.edit .edit_user_tell').css({'border-color':'red'});
					return false;
				};
				if(editadd == '') {
					$('.edit .edit_user_add').css({'border-color':'red'});
					return false;
				};
				
				if(editId  =='' && $('.layer_user_address').length > 4) {
					alert('该用户已拥有五条地址请删除一条地址');
					return false;
				}
				alert("save");
				$.ajax({
					type : "post",
					url : '<%=path%>/savePtDeliveryAddress',
					dataType : 'json',
					data :  {
						'id':editId,
						'patientNo':patientNo,
					    'phoneNo':edittel,
					    'address':editadd,
					    'name':editnm
					},
					async : false,
					success : function(data) {
						var id = data.id;
						//返回地址的Id
						if(id>0) {
							if(editId !='') {
							   $('.adx').css({'position':'relative','left':"0px"});
							   $('.adx').children('.layer_user_name').text(editnm);
							   $('.adx').children('.layer_user_tell').text(edittel);
							   $('.adx').children('.layer_user_add').text(editadd);
							} else  {
								 $('.layer_user_address').removeClass('check');
								  var html =
									   '<div class="layer_user_address check" style="opacity: 1;"> ' +
								       '	<input class="address_id"   type="hidden"   value="'+data+'">'+
										'<span class="layer_user_name">'+editnm+'</span>'+
										'<span class="layer_user_tell">'+edittel+'</span>'+
										'<span class="layer_user_add">'+editadd+'</span>'+
										'<a href="javascript:void(0)"  class="layer_user_remvap">编辑</a>'+
										'<a href="javascript:void(0)" class="layer_user_remove">删除</a></div>';
								  $(".layer_content").prepend(html);
							}
							$('.edit .edit_address_id').val('');//编辑值为空
							$('.edit').children('form').children("input").css({'border-color':'rgb(35, 99, 195)'});
							$('.edit').css({'display':'none'});
							$('.layer_user_address').css({'opacity':'1'}).removeClass('adx');
						}
					},
					error: function(data) {
						alert("保存失败！")
					}
				});
			})
			// 取消编辑
			$(".layer_content").delegate(".cancel_a","click",function(){
				alert("ok");
				$('.asz').remove();
				$('.edit').toggle(400);
				$('.layer_user_address').css({'opacity':'1'}).removeClass('adx');
				$('.edit').children('form').children("input").css({'border-color':'rgb(35, 99, 195)'});
			})
			
			// 切换选择地址
			$(".layer_content").delegate(".layer_user_address","click",function(){
				$('.address').children('.address_detail').text($(this).children('.layer_user_add').text());
				$('.user_name').children('.user_name_detail').text($(this).children('.layer_user_name').text());
				$('.usertell').children('.usertell_detail').text($(this).children('.layer_user_tell').text());
				$(this).addClass('check').siblings().removeClass('check');
			})	
			
					// 确认删除
				$(".layer_content").delegate(".layer_user_remove","click",function(){
					$('.lm_tit span').text($(this).siblings('.layer_user_add').text());
					$('#delete_address_id').val($(this).siblings('.address_id').val());
					$(this).addClass("delete_address");
					$('.layer_min').show(400);
		})
				// 取消删除事件
		$('.yes').bind('click',function () {
			var addressId = $('#delete_address_id').val();
			$.ajax({
				type : "post",
				url : '<%=path%>/deleteDeliveryAddress',
				dataType : 'json',
				data :  {
					'id':addressId
				},
				async : false,
				success : function(data) {
					$('.delete_address').parent().remove();
					alert(data.message);
				}
			});
			$('.layer_min').hide();
		})
			// 取消
	   $('.no').bind('click',function () {
		        $('#delete_address_id').val('');
		        $('.lm_tit span').text('');
		    	$('.layer_min').hide();
	   })

	</script>
</body>
</html>
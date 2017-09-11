<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDelivery"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtPharmacy"%>
<%@page  import="java.text.DecimalFormat"%>
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
		EngPtDelivery  ptDelivery = (EngPtDelivery)request.getAttribute("ptDelivery");
		EngPtPharmacy  ptPharmacy = (EngPtPharmacy)request.getAttribute("ptPharmacy");
		
		Double  money =0.0;
	    for(int i=0;i<drugs.size();i++) {
		    	money +=drugs.get(i).getMoney();
		 }
		DecimalFormat  moneyF = new DecimalFormat("######0.00");
		moneyF.format(money); 
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>详情页面</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/refer.css"/>"/>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
</head>
<body>
	<%-- 头部 Start --%>
	<div class="header">
		<h2>药易通</h2>
		<div class="login"><a>注销</a></div>
		<div class="set" onclick='TgLayer("design_admin")'><a href="javascript:void(0)">设置</a></div>
		<div class="user_box" onclick='TgLayer("layer_user_center")'>
			<a href="javascript:void(0)" >
				<span><img src="./images/web/timg.jpg" alt="头像"></span>
				<span class="user_msg">白求恩</span>
			</a>
		</div>
	</div>
	<%-- 头部 End --%>
	<%-- content --%>
	<div class="content">
		<div class="inner">
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
				<legend>配送信息</legend>
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
					 <% if(cf.getStatus().equals("10") || cf.getStatus().equals("1")) { %><div class="address wp20 user_adress"><img src="./images/web/address.png" alt="修改地址"></div><%} %>
				</div>
				<%-- 药店信息盒子 --%>
				<div class="drugstore_box ">
					<div class="drugstore_a w40">
					   <span  class="wp80" id="pharmacy">配送药房：</span>
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
			</fieldset>
			</div>
			<div class="drug_msg w100">
				<fieldset>
					<legend>药品信息</legend>
					<div class="drug_tit w100">
						<div class="drug_name wp300">药物名称</div>
						<div class="norms wp100">规格</div>
						<div class="usage wp250">用法</div>
						<div class="drug_num wp80">数量</div>
						<div class="price">价格</div>
					</div>
					<div class="drug_detail">
						<%-- 药物循环盒子 --%>
			     
			         <c:forEach var="drug"  items="${drugs}" >
						<div class="drug_detail_box">
							<div class="drug_names wp300">${drug.drugName}</div>
							<div class="drug_size wp100">${drug.spec}</div>
							<div class="usage wp250">${drug.adminRoute}/${drug.adminFrequency}</div>
							<div class="drug_bum wp80">${drug.quantity}/${drug.dispenseUnit}</div>
							<div class="drug_price">${drug.money}元</div>
						</div>
				      </c:forEach>
				       <div class="drug_detail_box">
							<div class="all_money">总计：<%=money%>元</div>
						</div>
					<div class="drug_detail_box">
							<div class="all_money now_status" style="color:red;">${cf.statusName}</div>
						</div>
						<%-- 循环盒子结束 --%>
					</div>
				</fieldset>
			</div>
			<div class="btn_box">
				  <% if(cf.getStatus().equals("1")) { %><div class="btn_price" onclick="changePtCfStatusToPaid()" >确认收费</div><%}%>
				  <% if(cf.getStatus().equals("10") || cf.getStatus().equals("1")) { %><div class="btn_next" onclick="saveDelivery();">确认分配</div><%}%>
				  <% if(!cf.getStatus().equals("10") && !cf.getStatus().equals("1")) { %><div class="btn_next" onclick="returnMain();">返回主页</div><%}%>
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
	 function 	 returnMain(){
			window.location.href ='<%=path%>/homePt';
	 }
	 var area =   '<% if(ptPharmacy !=null) { %><%=ptPharmacy.getUrbanArea()%> <%}%>';
	 var pharmacy = '<% if(ptPharmacy !=null) { %><%=ptPharmacy.getPharmacy()%> <%}%>';
	 if(area !="") {
		 $(".area").hide();
		 $(".pharmacy").hide();
		 $("#pharmacy").removeClass('wp80').addClass('w230').html("配送药房：" +area+"-"+pharmacy)
		 }
  
	   //形成配送订单
	   	// 确认收款
       function 	changePtCfStatusToPaid() {
		var  cfId  = $("#cfId").val();//处方ID
		$.ajax({
			type : "post",
			url : '<%=path%>/changePtCfStatusToPaid',
			dataType : 'json',
			data :  {
				'cfId':cfId
			},
			async : false,
			success : function(data) {
				var  success = data.success;
				var  message =  data.message;
				if(success =="false") {
					alert(message);
				} else {
                   alert(message);
                   $(".now_status").html("付款成功");
   				   $('.btn_price').hide();
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
					'remark':remark
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
						 $(".drug_address").hide();
						 $(".user_adress").hide(); 
						 $(".btn_next").attr("disabled","disabled");
						 $(".btn_box").append('<div class="btn_next" onclick="returnMain();">返回主页</div>')
						 $(".now_status").html("分配到药房");
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
						//返回地址的Id
						if(data>0) {
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
<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page import="com.xiaojd.entity.util.PTSTATUS"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>
<%@page import=" java.util.Map"%>

<% String path = request.getContextPath();
response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
EngPtUser user = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
Map<String, String> map  = PTSTATUS.getAllStatus();
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/common.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/index.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/jquery.pagination.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/jquery.multiselect.css"/>"/>
		<link rel="stylesheet" type="text/css" href="<c:url value="/styles/web/jquery-ui.css"/>"/>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery.multiselect.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery.pagination.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/web/pag.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/scripts/My97DatePicker/WdatePicker.js"/>"></script>
   <script type="text/javascript" src="<c:url value="/scripts/My97DatePicker/lang/zh-cn.js"/>"></script>
</head>
<title>医配通查询</title>
<body>
	<%-- 头部 Start --%>
	<div class="header">
		<h2>医配通</h2>
		<div class="login"><a href='<%=path%>/logoutPt'>注销</a></div>
		<div class="set" onclick='TgLayer("design_admin")'><a href="javascript:void(0)">设置</a></div>
		<div class="user_box" onclick='TgLayer("layer_user_center")'>
			<a href="javascript:void(0)" >
				<span class='span_index' ><img src="./images/web/timg.jpg"  alt="头像"></span>
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
				<div class="search_box_content">
				  <form id="searchFrom">
					<div style="width:260px"><span class='span_index' >处方日期：</span>
						 <input id="presDateTimeStart" style="width:80px" readonly="readonly" type="text" name="pres_date_time_start" class="Wdate" 
							onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'presDateTimeEnd\')}'})" 
							<%-- value="<%=new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() - 7 * 24 * 3600 * 1000) %>" --%>
							>
						<span class='span_index'  style='width:5px'>-</span>
						<input id="presDateTimeEnd"  style="width:80px"  readonly="readonly" type="text" name="pres_date_time_end" class="Wdate" 
							onfocus="WdatePicker({minDate:'#F{$dp.$D(\'presDateTimeStart\')}'})" 
						<%-- 	value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" --%>
							>
					</div>

		
					<div ><span class='span_index' >开方科室：</span>
						<input name="department"  type="text">
					</div>
					<div ><span class='span_index' >开方医生：</span>
						<input name="doc_name" type="text">
					</div>
                   	<div ><span class='span_index' >药店审方医生：</span><input name="aduit_drug_person" type="text"></div>
                   	 <div ><span class='span_index' >诊断：</span> <input  name="diagnose" type="text"></div>	

                    <div style="width:260px"><span class='span_index' >上传时间：</span>
						<input id="updateDateStart" style="width:80px" readonly="readonly" type="text" name="update_date_start" class="Wdate" 
							onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'updateDateEnd\')}'})" 
<%-- 							value="<%=new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() - 7 * 24 * 3600 * 1000) %>" --%>
							>
						<span class='span_index'  style='width:5px'>-</span>
						<input id="updateDateEnd"  style="width:80px"  readonly="readonly" type="text" name="update_date_end" class="Wdate" 
							onfocus="WdatePicker({minDate:'#F{$dp.$D(\'updateDateStart\')}'})" 
<%-- 							value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>" --%>
							>
					</div>
					<div ><span class='span_index' >处方号：</span><input  name="cf_id" type="text"></div>
					<div ><span class='span_index' >医保卡：</span><input  name="patient_cardNo"  type="text"></div>
					<div ><span class='span_index' >病人姓名：</span><input  name="patient_name" type="text"></div>
					<div ><span class='span_index' >病人电话：</span><input name="patient_phone"  type="text"></div>
  				 	<div ><span class='span_index' >转方医生：</span><input name="tran_doctor" type="text"></div>	
					<div ><span class='span_index' >转方机构：</span>
				 	     <input type="hidden" name="tran_hospital" id="tran_hospital"/>
				 		<select  multiple="multiple"  size="4"  style="display: none;" id="tran_hospital_group">
					 		<option value="1">社区医院1</option>
					 		<option value="2">社区医院2</option>
					 	</select>
					 </div>
					 
					 <div ><span class='span_index' >处方类别：</span>
					  <input type="hidden" name="hospital_or_pharmacy" id="hospital_or_pharmacy"/>
						<select  multiple="multiple"  style="display: none;" size="2" id="hospital_or_pharmacy_group">
					 		<option value="社区">社区</option>
					 		<option value="药店">药店</option>
					 	</select>
						</select>
					</div>
					
                    <div><span class='span_index' >处方状态：</span>
                     <input type="hidden" name="status" id="status"/>
						<select multiple="multiple"  size="4" style="display: none;" id="status_group">
						<%for(String key:map.keySet()) { %>
					 		<option value="<%=key %>"><%=map.get(key)%></option>
					 	 <%}%>	
					 	</select>
					</div>
          
					<span class="span_index serach" onclick="GetData(1)">查询</span>
					</form>
				</div>
		   
			</div>
			<%-- 展示信息 --%>
			<div class="user_msg_a" >
			<table class='msg'>
						<tr class='tab_header'>
						<th>处方号</th>
						<th>处方日期</th>
						<th >接收时间</th>
						<th class='w60'>类别</th>
						<th >科室</th>
						<th class='w60'>医生</th>
						<th class='w70'>病人姓名</th>
						<th>性别/年龄</th>
						<th class='w150'>身份证</th>
						<th class='w100'>医保卡</th>
						<th class='w100'>手机</th>
						<th class='w70'>审方医生</th>
						<th class='w90'>转方单位</th>
						<th class='w70'>转方医生</th>
						<th class='w120'>诊断</th>
						<th>状态</th>
					</tr>
				</table>
			</div>
					<div id="paging0" class="paging"></div>
		</div>
	</div> 
	<%-- 中心 End --%>
	<%-- 个人中心 STart Layer --%>
	<div class="layer_user_center layer">
		<div class="layer_tit">用户中心<span class='close'></span></div>
		<div class="layer_content">
			<form action="" id='' onsubmit="" >
			     <input  type="hidden"  id="user_id"  value="<%=user.getId()%>">
				<div class="user_account wp300">
					<span class='wp80'>账号：</span>
					<input type="text" disabled="disabled"  value="<%=user.getCode()%>">
				</div>
				<div class="user_nicknets wp300">
					<span class='wp80'>用户名：</span>
					<input type="text"  disabled="disabled"  value="<%=user.getName()%>">
				</div>
				<div class="user_password wp300">
					<span class='wp80'>输入密码：</span>
					<input type="password"  id="user_password">
				</div>
				<div class="enter_password wp300">
					<span class='wp80'>确认密码：</span>
					<input type="password" id="user_password_again">
				</div>
				<div class="btn_save_box wp300">
					<div class="btn_save " onclick='SaveUserLayer("layer_user_center")'>确认修改</div>
					<div class="btn_cancel " onclick='TgLayer("layer_user_center")'>取消修改</div>
				</div>
			</form>
		</div>
	</div>
	<%-- 个人中心 End Layer --%>
	<div class="design_admin layer"  >
		<div class="layer_tit">设置中心<span class='close'></span></div>
		<div class="layer_content" style='padding:0;margin:0;height:370px'>
			<div class="design_tit">
				<div class="design_user_check" style='background: #f9f9f9'></div>
				<div class="design_user_num">编号</div>
				<div class="design_user_name">用户名</div>
				<div class="design_user_account">账号</div>
				<div class="design_user_edit">职务</div>
				<div class="design_user_remove">删除</div>
			</div>
			<div class="design_content">
			
           <%--  人员--%>
				
			</div>
			<div class="layer_footer">
				<div class="design_add_user">添加</div>
				<div class="design_edit_user">修改</div>
			</div>
			<div class="design_add_user_content">
				<form id='design_form'>
				    <input  type="hidden"  id="edit_id">
					<div class="add_user_user_name">
						<span class='span_index' >姓名：</span>
						<input type="text" placeholder="请输入用户名"  id="edit_name">
					</div>
					<div class="add_user_account">
						<span class='span_index' >账号：</span>
						<input type="text" placeholder="请输入用账号" id="edit_code">
					</div>
					<div class="add_user_work">
						<span class='span_index' >职务：</span>
						<select name="" id="edit_roleName">
							<option value="管理员">管理员</option>
							<option value="店长">店长</option>
							<option value="配送员">配送员</option>
						</select>
					</div>
					<div class="add_user_password">
						<span class='span_index' >密码：</span>
						<input type="text" placeholder="请输入用密码" id="edit_pwd">
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="layer_min">
		<div class="lm_tit">是否要删除<span class='span_index' ></span>?</div>
		<div class="lm_content">
			<div class="lm_btn_box">
				<span class='yes'>是</span>
				<span class='no'>否</span>
			</div>
		</div>
	</div>
		


<script type="text/javascript">
      var pageCheck =0;
      $("#status_group").multiselect({  
    	    noneSelectedText : "==请选择==",  
    	    checkAllText : "全选",  
    	    uncheckAllText : '全不选',  
    	    selectedList : 3,//在select中显示选中数量，默认不显示（就是显示多以3就只告诉几选中几个不显示名字了）  
    	    Height : 300,//<span id="tran_0" data-aligning="#tran_0,#src_0" class="copied" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">复选框的高度容器(滚动区)以像素为单位。</span><span id="tran_1" data-aligning="#tran_1,#src_1" class="copied highLight" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">如果设置为“自动”,复选框的高度计算基于数量的菜单。</span>  
    	    numberDisplayed : 3,  
    	}); 
      
      $("#hospital_or_pharmacy_group").multiselect({  
  	    noneSelectedText : "==请选择==",  
  	    checkAllText : "全选",  
  	    uncheckAllText : '全不选',  
  	    selectedList : 3,//在select中显示选中数量，默认不显示（就是显示多以3就只告诉几选中几个不显示名字了）  
  	    Height : 300,//<span id="tran_0" data-aligning="#tran_0,#src_0" class="copied" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">复选框的高度容器(滚动区)以像素为单位。</span><span id="tran_1" data-aligning="#tran_1,#src_1" class="copied highLight" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">如果设置为“自动”,复选框的高度计算基于数量的菜单。</span>  
  	    numberDisplayed : 3,  
  	});  
      $("#tran_hospital_group").multiselect({  
    	    noneSelectedText : "==请选择==",  
    	    checkAllText : "全选",  
    	    uncheckAllText : '全不选',  
    	    selectedList : 3,//在select中显示选中数量，默认不显示（就是显示多以3就只告诉几选中几个不显示名字了）  
    	    Height : 300,//<span id="tran_0" data-aligning="#tran_0,#src_0" class="copied" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">复选框的高度容器(滚动区)以像素为单位。</span><span id="tran_1" data-aligning="#tran_1,#src_1" class="copied highLight" style="margin: 0px; padding: 0px; border: 0px; outline: 0px; color: rgb(102, 102, 102); font-family: Tahoma, Arial, 宋体, "Malgun Gothic"; line-height: 24px; text-align: justify; background-color: rgba(255, 255, 255, 0.8);">如果设置为“自动”,复选框的高度计算基于数量的菜单。</span>  
    	    numberDisplayed : 3,  
    	});  
        
      
      
      
		$.fn.serializeJson=function(){
			var serializeObj={};
			$(this.serializeArray()).each(function(){
				serializeObj[this.name]=this.value;
			});
			return serializeObj;
		};
		
		Date.prototype.Format = function(fmt) {
			var o = { 
				"M+" : this.getMonth()+1,                 //月份 
				"d+" : this.getDate(),                    //日 
				"h+" : this.getHours(),                   //小时 
				"m+" : this.getMinutes(),                 //分 
				"s+" : this.getSeconds(),                 //秒 
				"q+" : Math.floor((this.getMonth()+3)/3), //季度 
				"S"  : this.getMilliseconds()             //毫秒 
			}; 
			if(/(y+)/.test(fmt)) 
				fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
			for(var k in o) 
				if(new RegExp("("+ k +")").test(fmt)) 
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
			return fmt; 
		}

	  //  请求数据 
		function GetData (page) {
		   //分页插件每次重新加载时，要先移除以前的分页
  		    $('#paging'+pageCheck).remove();
  		    pageCheck++;
		    $('.inner').append('<div id="paging'+pageCheck+'" class="paging"></div>'); 
		    var status ="'";
		    $("#status_group option:selected").each(function(){
		    	status +=$(this).val()+"','";
	        });
		    if(status !="'") {
		    	   $("#status").val(status.substring(1,status.length-3));
		    }else {$("#status").val("");}
		    var hospital_or_pharmacy="'";
		    $("#hospital_or_pharmacy_group option:selected").each(function(){
		    	hospital_or_pharmacy +=$(this).val()+"','";
	        });
		    if(hospital_or_pharmacy !="'") {
		    	   $("#hospital_or_pharmacy").val(hospital_or_pharmacy.substring(1,hospital_or_pharmacy.length-3));
		    } else {$("#hospital_or_pharmacy").val("");}
		    var tran_hospital ="'";
		    $("#tran_hospital_group option:selected").each(function(){
		    	tran_hospital +=$(this).val()+"','";
	        });
		    if(tran_hospital !="'") {
		    	   $("#tran_hospital").val(tran_hospital.substring(1,tran_hospital.length-3));
		    } else {$("#tran_hospital").val("");}
			var postData= $.extend({'page':page}, {rpt_parameters:$('#searchFrom').serializeJson()}); 
			//console.log(postData);
			$.ajax({
				type : "post",
				url : '<%=path%>/adminSearch',
				dataType : 'json',
				data :  postData,
				async : true,
				success : function(data) {
					var success =data.success;
					var totalPage = 0;
					var currentPage = 0;
					var rowCount = 0;
					if(success =="true")  {
					    totalPage = data.totalPage;
					    currentPage = data.currentPage;
					    rowCount = data.rowCount;
					} 

					$("#paging"+pageCheck).pagination({
					   currentPage: currentPage,// 当前页数
					   totalPage: totalPage,// 总页数
					   isShow: true,// 是否显示首尾页
					   count: 5,// 显示个数
					   homePageText: "首页",// 首页文本
					   endPageText: "尾页",// 尾页文本
					   prevPageText: "上一页",// 上一页文本
					   nextPageText: "下一页",// 下一页文本
					   callback: function(page) {
						  GetData (page);
					   }
					});
			
					$('<span >总:'+totalPage+'页</span><span>'+rowCount+'行</span>').appendTo('#paging'+pageCheck)
			         setInnerHtml(data);
				}
			});
		}

	 function setInnerHtml(data){
		 $('.tab_header').siblings('tr').remove();
		 var dta = data.rows;
		 if(dta== null) {
			 return;
		 }
		 for(var i =0;i<dta.length;i++){		  
			        if(dta[i].tran_hospital ==null) {
			        	dta[i].tran_hospital ="--";
			        }
			        if(dta[i].trans_doctor ==null) {
			        	dta[i].trans_doctor ="--";
			        }
			        if(dta[i].aduit_drug_person ==null) {
			        	dta[i].aduit_drug_person ="--";
			        }
		
				    var item = $( '<tr onclick=loadPtCfById("'+dta[i].id+'")></tr>');
					var msg = '<td>'+dta[i].id+'</td>'+
					 '<td>'+dta[i].pres_date_time+'</td>'+
					 '<td>'+new Date(dta[i].update_date).Format('yyyy-MM-dd HH:mm:ss')+'</td>'+
					 '<td class="w60">'+dta[i].hospital_or_pharmacy+'</td>'+
					 '<td>'+dta[i].department+'</td>'+
					 '<td class="w60">'+dta[i].doc_name+'</td>'+
					 '<td class="w70">'+dta[i].name+'</td>'+
					 '<td>'+dta[i].sex+'/'+dta[i].age+'</td>'+
					 '<td class="w150">'+dta[i].id_card+'</td>'+
					 '<td class="w100">'+dta[i].card_no+'</td>'+
					 '<td class="w100">'+dta[i].phone_no+'</td>'+
					 '<td class="w70">'+dta[i].aduit_drug_person+'</td>'+
					 '<td class="w90">'+dta[i].tran_hospital+'</td>'+
					 '<td class="w70">'+dta[i].trans_doctor+'</td>'+
					 '<td class="w120">'+dta[i].diagnose+'</td>'+
					 '<td>'+dta[i].status_name+'</td>';
					$(msg).appendTo(item);
			     	$(item).appendTo('.msg');
			}
	  }
	 
 function 	 loadPtCfById(cfId){
	 window.open('<%=path%>/loadPtCfByIdToAdmin?id=' + cfId);
 }
 function reloadUser() {
	 $('.design_content').text('');
	 $.ajax({
			type : "post",
			url : '<%=path%>/loadAllUser',
			dataType : 'json',
			async : false,
			success : function(data) {
				console.log(data);
				 for(var i =0;i<data.length;i++){
			  		var item = $('<div class="design_content_detail"></div>');
			  		$("<div class='design_user_check'></div><div class='design_user_num'>"+ data[i].id
			  				+"</div><div class='design_user_name'>"+data[i].name
			  				+"</div><div class='design_user_account'>"+data[i].code
			  				+"</div><div class='design_role_name'>"+data[i].roleName
			  				+"</div><div class='design_user_remove'>删除</div>").appendTo(item);
			  		$(item).appendTo('.design_content');
		  		}
				 addDefaultfunction();//添加绑定事件
			}
		}); 
 }
	// 关闭弹层
	function TgLayer (opt) {
		if(opt == 'design_admin') {		
			reloadUser();
		}
		$('.'+opt).toggle(400);
	};
	
	// 确认修改
	function SaveUserLayer (opt) {
		var id = $('#user_id').val();
		var password = $("#user_password").val();
		var passwordAgain = $("#user_password_again").val();
		if(password != passwordAgain) {
			alert("两次密码不一致");
			return false;
		}
		var pwd =
		$.ajax({
			type : "post",
			url : '<%=path%>/updateOwnPassword',
			dataType : 'json',
			data :  {
				'id':id,
				'pwd':password
			},
			async : false,
			success : function(data) {
				alert(data.message);
			}
		});
		TgLayer (opt);
	};
	function Load (e) {
		var a = e.target;
		//window.location ='refer.html?id=1'
		// $("body").load('refer.html?id=1')
	};
function addDefaultfunction(){
	// 选择用户
	$(".design_user_check").live("click",function(){
		$(this).toggleClass('f60').parent().siblings('div').children('.design_user_check').removeClass('f60');
		var acount_e = $(this).siblings('.design_user_name').text();
		var user_name = $(this).siblings('.design_user_account').text();
		var jurisdiction = $(this).siblings('.design_role_name').text();
		var id = $(this).siblings('.design_user_num').text();
		$('#edit_id').val(id);
		$('.add_user_user_name input').val(acount_e);
		$('.add_user_account input').val(user_name);
		$('.add_user_work select').val(jurisdiction);
	});
	// 删除用户
	$('.design_user_remove').live('click',function () {
		var user_name = $(this).siblings('.design_user_name').text();
	    var removeId = $(this).siblings('.design_user_num').text();
		$('.lm_tit span').text(user_name).removeClass().addClass(removeId);
		$('.layer_min').css({'display':'block'});
	})
}
	
	// 取消删除用户
	
	$('.no').bind('click',function (){
		$('.layer_min').css({'display':'none'});
	})
	// 确认删除用户
		$('.yes').bind('click',function (){
		 var deteteId =$('.lm_tit span').attr('class');
		
		 var operation ='DELETE';
		$.ajax({
			type : "post",
			url : '<%=path%>/deleteOrUpdateUser',
			dataType : 'json',
			data:{'id':deteteId,'operation':operation},
			async : false,
			success : function(data) {
				var success = data.success;
				if(success=="true") {
					reloadUser();
				}
			    alert(data.message);
			}
		}); 
		$('.layer_min').css({'display':"none"})
	})
	// 编辑用户
	$('.design_edit_user').bind('click',function (){
		
		 var id=$('#edit_id').val();
		 var name=$('#edit_name').val().trim();
		 var code=$('#edit_code').val().trim();
		 var roleName =$('#edit_roleName').val().trim();
		 var pwd =$('#edit_pwd').val().trim();
		 if(name =="" ) {
			 alert("请填写姓名");
			 return false;
		 }
		 if(code =="" ) {
			 alert("请填写账号");
			 return false;
		 }
		 if(roleName =="" ) {
			 alert("请填写职务");
			 return false;
		 }
		 if(pwd =="" ) {
			 alert("请填写密码");
			 return false;
		 }
		 var operation ='UPDATE';

			$.ajax({
				type : "post",
				url : '<%=path%>/deleteOrUpdateUser',
				dataType : 'json',
				data:{'id':id,'name':name,'code':code,'roleName':roleName,'pwd':pwd,'operation':operation},
				async : false,
				success : function(data) {
				    alert(data.message);
				    reloadUser();
				}
			}); 

	})
	// 添加用户
	$('.design_add_user').bind('click',function (){
		 var name=$('#edit_name').val();
		 var code=$('#edit_code').val();
		 var roleName =$('#edit_roleName').val();
		 var pwd =$('#edit_pwd').val();
		 var operation ='CREATE';

			$.ajax({
				type : "post",
				url : '<%=path%>/deleteOrUpdateUser',
				dataType : 'json',
				data:{'name':name,'code':code,'roleName':roleName,'pwd':pwd,'operation':operation},
				async : false,
				success : function(data) {
					$('#edit_name').val('');
					$('#edit_code').val('');
					$('#edit_roleName').val('');
					$('#edit_pwd').val('');
					alert(data.message); 
					reloadUser();
				}
			}); 
	})
	// 关闭地址弹层
	$('.close').click(function () {
		$(this).parent().parent().css({'display':"none"})
	})
	</script>
</body>
</html>
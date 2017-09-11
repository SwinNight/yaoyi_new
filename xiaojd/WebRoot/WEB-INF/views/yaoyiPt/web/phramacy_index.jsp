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
Map<String, String> map  = PTSTATUS.getPharmacyStatus();
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
					<div ><span class='span_index' >配送员：</span><input name="del_drug_person"  type="text"></div>
			        <div ><span class='span_index' >取药人：</span><input name="take_drug_person"  type="text"></div>
					
                    <div><span class='span_index' >处方状态：</span>
                     <input type="hidden" name="status" id="status"/>
						<select multiple="multiple"  size="4" style="display: none;" id="status_group">
						<%for(String key:map.keySet()) { %>
					 		<option value="<%=key %>"><%=map.get(key)%></option>
					 	 <%}%>	
					 	</select>
					</div>
					  <div><span class='span_index' >取药方式：</span>
                     <input type="hidden" name="ways" id="ways"/>
						<select multiple="multiple"  size="4" style="display: none;" id="ways_group">
						    <option value="配送">配送</option>
					 		<option value="自取">自取</option>	
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
						<th >取药方式</th>
						<th >科室</th>
						<th class='w70'>医生</th>
						<th>病人姓名</th>
						<th>性别/年龄</th>
						<th class='w150'>身份证</th>
						<th class='w80'>医保卡</th>
						<th class='w100'>手机</th>
						<th class='w70'>审方医生</th>
						<th class='w70'>配送员</th>
						<th class='w70'>取药人</th>
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
      
      $("#ways_group").multiselect({  
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

		    var ways ="'";
		    $("#ways_group option:selected").each(function(){
		    	ways +=$(this).val()+"','";
	        });
		    if(ways !="'") {
		    	   $("#ways").val(ways.substring(1,ways.length-3));
		    } else {$("#ways").val("");}
		    
			var postData= $.extend({'page':page}, {rpt_parameters:$('#searchFrom').serializeJson()}); 
			//console.log(postData);
			$.ajax({
				type : "post",
				url : '<%=path%>/pharmacySearch',
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
			        if(dta[i].aduit_drug_person ==null) {
			        	dta[i].aduit_drug_person ="--";
			        }
			        if(dta[i].take_drug_person ==null) {
			        	dta[i].take_drug_person ="--";
			        }
			        if(dta[i].del_drug_person ==null) {
			        	dta[i].del_drug_person ="--";
			        }
			        
		
				    var item = $( '<tr onclick=loadPtCfById("'+dta[i].id+'")></tr>');
					var msg = '<td>'+dta[i].id+'</td>'+
					 '<td>'+dta[i].pres_date_time+'</td>'+
					 '<td>'+new Date(dta[i].update_date).Format('yyyy-MM-dd HH:mm:ss')+'</td>'+
					 '<td>'+dta[i].ways+'</td>'+
					 '<td>'+dta[i].department+'</td>'+
					 '<td class="w70">'+dta[i].doc_name+'</td>'+
					 '<td>'+dta[i].name+'</td>'+
					 '<td>'+dta[i].sex+'/'+dta[i].age+'</td>'+
					 '<td class="w150">'+dta[i].id_card+'</td>'+
					 '<td class="w80">'+dta[i].card_no+'</td>'+
					 '<td class="w100">'+dta[i].phone_no+'</td>'+
					 '<td class="w70">'+dta[i].aduit_drug_person+'</td>'+
					 '<td class="w70">'+dta[i].del_drug_person+'</td>'+
					 '<td class="w70">'+dta[i].take_drug_person+'</td>'+
					 '<td class="w120">'+dta[i].diagnose+'</td>'+
					 '<td>'+dta[i].status_name+'</td>';
					$(msg).appendTo(item);
			     	$(item).appendTo('.msg');
			}
	  }
	 
 function 	 loadPtCfById(cfId){
	 window.open('<%=path%>/loadPtCfByIdToPharmacy?id=' + cfId);
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


	
	// 关闭地址弹层
	$('.close').click(function () {
		$(this).parent().parent().css({'display':"none"})
	})
	</script>
</body>
</html>
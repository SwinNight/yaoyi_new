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
		<h2>药易通</h2>
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
				<select name="" id="">
					<option value="idCard">身份证</option>
					<option value="cardNo">医保卡</option>
					<option value="phoneNo">电话</option>
					<option value="name">姓名</option>
				</select>
				<input type="text" placeholder="请输入有效信息" id='search'>
				<input type="button" value='查询' class='serach' onclick="searchPtCf();">
			</div>
			<%-- 展示信息 --%>
			<div class="user_msg_a">
			<table class='msg'>
					<tr class="tit">
						<th>姓名</th>
						<th>性别</th>
						<th>年龄</th>
						<th class='w200'>身份证</th>
						<th>科室</th>
						<th class='w150'>临床诊断</th>
						<th class='w150'>时间</th>
						<th>状态</th>
					</tr>
				</table>
			</div>
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
						<span>姓名：</span>
						<input type="text" placeholder="请输入用户名"  id="edit_name">
					</div>
					<div class="add_user_account">
						<span>账号：</span>
						<input type="text" placeholder="请输入用账号" id="edit_code">
					</div>
					<div class="add_user_work">
						<span>职务：</span>
						<select name="" id="edit_roleName">
							<option value="管理员">管理员</option>
							<option value="店长">店长</option>
							<option value="配送员">配送员</option>
						</select>
					</div>
					<div class="add_user_password">
						<span>密码：</span>
						<input type="text" placeholder="请输入用密码" id="edit_pwd">
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="layer_min">
		<div class="lm_tit">是否要删除<span></span>?</div>
		<div class="lm_content">
			<div class="lm_btn_box">
				<span class='yes'>是</span>
				<span class='no'>否</span>
			</div>
		</div>
	</div>
		


<script type="text/javascript">
	var dta, newary;
	searchPtCf();
	 function searchPtCf() {
			var type = $('select').val(); //获取查询类型
			var value = $('#search').val(); //获取查询值
			$.ajax({
				type : "post",
				url : '<%=path%>/loadCfByPatientInfoType',
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
				    var item = $( '<tr onclick=loadPtCfById("'+dta[i].id+'")></tr>');
					var msg = '<td class="name">'+dta[i].name+'</td>'+
					 '<td class="sex">'+dta[i].sex+'</td>'+
					 '<td class="age">'+dta[i].age+'</td>'+
					 '<td class="idCard w200">'+dta[i].idCard+'</td>'+
					 '<td class="department">'+dta[i].department+'</td>'+
					 '<td class="diagnose w150">'+dta[i].diagnose+'</td>'+
					 '<td class="presDateTime w150">'+dta[i].presDateTime+'</td>'+
					 '<td class="statusName">'+dta[i].statusName+'</td>';
					$(msg).appendTo(item);
			     	$(item).appendTo('.msg');
			}
	  }
	 
 function 	 loadPtCfById(cfId){
		window.location.href ='<%=path%>/loadPtCfById?id=' + cfId;
 }
 function reloadUser() {
	 $('.design_content').text('');
	 $.ajax({
			type : "post",
			url : '<%=path%>/loadAllUser',
			dataType : 'json',
			async : false,
			success : function(data) {
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
	$('.no').click(function (){
		$('.layer_min').css({'display':'none'});
	})
	// 确认删除用户
	$('.yes').click(function (){
		 var deteteId =$('.lm_tit span').attr('class');
		
		 var operation ='DELETE';
		$.ajax({
			type : "post",
			url : '<%=path%>/deleteOrUpdateUser',
			dataType : 'json',
			data:{'id':deteteId,'operation':operation},
			async : false,
			success : function(data) {
				reloadUser();
			    alert(data.message);
			}
		}); 
		$('.layer_min').css({'display':"none"})
	})
	// 编辑用户
	$('.design_edit_user').bind('click',function (){
		
		 var id=$('#edit_id').val();
		 var name=$('#edit_name').val();
		 var code=$('#edit_code').val();
		 var roleName =$('#edit_roleName').val();
		 var pwd =$('#edit_pwd').val();
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
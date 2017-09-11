$.extend($.jgrid.defaults,{
	loadError: function(xhr,status,error){
	      if(xhr!= null && xhr.responseText.indexOf("sessionCloseClear") > -1){
	    	  showLoginAlert('登录超时,请重新登录!');
	      }else{
	    	  showAlert('系统出错,请联系管理员');
	      }
	}
});
function showAjaxErrorAlert(xhr,errorMessage){
	if(xhr!= null && xhr.readyState == 4 && xhr.responseText.indexOf("sessionCloseClear") > -1){
  	  showLoginAlert('登录超时,请重新登录!');
    }else{
		showAlert('加载数据失败!');
	 }
}
function showLoginAlert(text){
	var html =
		'<div class="dialog" id="login-dialog-message" style="overflow-y:hidden;padding-left:30px;"><form id="show_loginInfo" action="" method="post">' +
	    '<p>用户名： <input type="text" name="code" id="show_code" /></p>'+
	    '<p style="letter-spacing: 2px;">密&nbsp;码： <input type="password" name="pwd" id="show_pwd" style="margin-right:30px;" onfocus="hidde_text();"/></p>'+
	    '<p style="color:#f00;height:20px;line-height:20px;margin-left:10px;" id="show_text">' +'' + text +''+'  </p>'+
	    '<p style="color:#f00;height:20px;line-height:20px;margin-left:10px;" id="show_result"><c:out value="${error}"/></p>'+
	    '</form></div>';
	 $(html).dialog({
	      resizable: false,
	      modal: true,
	      width:300,
	      height:210,
	      show: {
	        effect: 'blind',
	        duration: 400
	      },
	      close: function() {
	    	  $('#login-dialog-message').remove();
	      },
	      title: "重新登录",
	      buttons: {
	        "登录": function() {
	        	var closedialog = null;
	        		$.ajax({
	                    type: "POST",
	                    url: 'login',
	                    data: {"code":$("#show_code").val(),"pwd":$("#show_pwd").val()},
	                    async: false,
	                    error: function(request) {
	                        $('#show_result').html(request);
	                    },
	                    success: function(data) {
	                    	var datamessage = data.match(/id="result">[\W]+</);
	                    	var msg = '' + datamessage;
	                    	if(datamessage != null){
								var start = msg.indexOf('>') + 1;
								var end = msg.indexOf('<');
								$('#show_result').text(msg.substr(start, end-start)).show();
								closedialog = "登录失败";
	                    	}
	                    }
	                });
	        		if(closedialog == null){
	        			$(this).dialog("close");
	        		}
	        },
	        "取消":function(){
	        	$(this).dialog("close");
	        }
	      }
	    });
	    $('button.ui-button').blur();
    return true;
}
function hidde_text(){
	$("#show_text").hide();
}

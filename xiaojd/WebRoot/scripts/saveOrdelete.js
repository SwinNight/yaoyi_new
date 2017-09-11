function showAlert(text, title, callback){
	var html =
	    '<div class="dialog" id="dialog-message">' +
	    '  <p>' +
	    '    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>' + text +
	    '  </p>' +
	    '</div>';
    return $(html).dialog({
      resizable: false,
      modal: true,
      show: {
        effect: 'blind',
        duration: 400
      },
      close: function() {
    	  $('#dialog-message').remove();
      },
      title: title || "提示信息",
      buttons: {
        "确定": function() {
          var dlg = $(this).dialog("close");
          callback && callback.call(dlg);
        }
      }      
    });
}

function del(str, tmp){
     $().ready(function(){
    	 var temp = -1;
    	 for(var i = 0; i < icp_temp.length; i ++){
    		 if(tmp.indexOf(icp_temp[i]) >= 0){
    			 temp = i;
    			 break;
    		 }
    	 }
    	 if(temp >= 0){
    		 icp_temp.splice(temp, 1);
    		 $('#'+str+'').remove();
    		 getICPMessage();
    	 }
	 });
}
var icp_temp = new Array();

function add(){
	$().ready(function(){
			if($(" #all > div ").length<6){
				if($('#name').val()!=''){
					if($(" #all > div > span ").text().length==0){
						var n2 = parseInt(10000 * Math.random());
	    				var str='';
	    				var til=$("#name").val();
	    				var tmp = til;
	    				if(tmp.indexOf("[") >= 0){
	    					tmp = tmp.substr(tmp, tmp.indexOf("["));
	    				}
	    				if(tmp.length > 17){
	    				   $('#name').val(tmp.substring(0,14) + "...");
	    				   str="<div id=" + n2 + "><span> " + $("#name").val() + "</span> <a  href=# title='" + til + "' onclick='del(" + n2 + ", \"" + til + "\")'><span class='del_arrow'></span></a></div>";
	  	    	           $("#all").append(str);
	  	    	           icp_temp[icp_temp.length] = til;
	  	    	           $("#name").val("");
	    				}else{
	    				   str="<div id = " + n2 + "><span> " + tmp + "</span> <a  href=# title='" + til + "' onclick='del(" + n2 + ", \"" + til + "\")'><span class='del_arrow'></span></a></div>";
	 	    	           $("#all").append(str);
	 	    	           icp_temp[icp_temp.length] = til;
	 	    	           $("#name").val("");
	    				}
	    			}else{
	    				var count = '';
	    				for(var i = 0; i < icp_temp.length ; i++){
	    					if(icp_temp[i] != $("#name").val()){
	    						count = 'y';
	    				    }else{
	    				    	count = 'n';
	    				    }
	    				    	
	    			    }
	    				if(count=='y'){
	    					var n2 = parseInt(10000 * Math.random());
	    				    var str='';
	    				    var til=$("#name").val();
	    				    var tmp = til;
		    				if(tmp.indexOf("[") >= 0){
		    					tmp = tmp.substr(tmp, tmp.indexOf("["));
		    				}
	    				    if($("#name").val().length > 17){
		    		    		$('#name').val(tmp.substring(0,14) + "...");
		    		    		str="<div id=" + n2 + "><span> " + $("#name").val() + "</span> <a  href=# title='" + til + "' onclick='del(" + n2 + ", \"" + til + "\")'><span class='del_arrow'></span></a></div>";
		    		  	    	$("#all").append(str);
		    		  	    	icp_temp[icp_temp.length] = til;
		    		  	    	$("#name").val("");
	    		    	}else{
	    		    		str="<div id=" + n2 + "><span> " + tmp + "</span> <a  href=# title='" + til + "' onclick='del(" + n2 + ", \"" + til + "\")'><span class='del_arrow'></span></a></div>";
	    		 	    	$("#all").append(str);
	    		 	    	icp_temp[icp_temp.length] = $("#name").val();
	    		 	    	$("#name").val("");
	    		    	}
	    			}
	    			if(count=='n'){
	    				$("#name").val("");
	    			}
	    			getICPMessage();
    			}
	    	}else{
	    		showAlert('药品名称不能为空');
	    	}
		}else{
			showAlert("最多不超过6个药品");
		}
	});
}
	
	function getICPMessage(){
		if(icp_temp.length < 2){
			$("#icp_result").html("推断结果内容");
			return;
		}
		var tmp = "";
		for(var i = 0; i < icp_temp.length; i++){
			tmp += icp_temp[i] + " ";
		}
		tmp = tmp.trim();
		$("#icp_result").html();
		$.ajax({
			type: "post",
			url: 'checkPWString',
			dataType: 'text',
			data : {'str':tmp},
			async: true,
			success: function (data) {
				if(data == null || data == '' || data.trim() == '')
					data = "未发现配伍禁忌情况";
				$("#icp_result").html(data.replace("\"", "").replace("\"", "").replace(/\n/g,"<BR>"));
			},
			error: function(data){
				$("#icp_result").html(data.replace("\"", "").replace("\"", "").replace(/\n/g,"<BR>"));
			}
		});
	}
	
	
	$().ready(function(){
		$("#del").click(function(){
			$("#name").val("");
		});
	});

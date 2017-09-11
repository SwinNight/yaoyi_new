var recordsDrugId = null;
var recordsDrugYpmc = null;
if(!String.prototype.trim){ //判断下浏览器是否自带有trim()方法
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	}; 
	String.prototype.ltrim = function() {
		return this.replace(/^\s+/g, '');
	};
	String.prototype.rtrim = function() {
		return this.replace(/\s+$/g, '');
	};
}

$.fn.serializeJson=function(){
	var serializeObj={};
	$(this.serializeArray()).each(function(){
		serializeObj[this.name]=this.value;
	});
	return serializeObj;
};

//对Date的扩展，将 Date 转化为指定格式的String 
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
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

function beMouseOver(el) {
	$(el).removeClass('item_normal').addClass('item_high');
}
function beMouseOut(el) {
	$(el).removeClass('item_high').addClass('item_normal');
}
function beClick(el) {
	var $this = $(el);
	cur_input.val($this.attr('value')).focus();
	$this.parent().hide();
}

function doKeyUp(el,e){
	var $suggest = $('div.search_suggest');
	var $this = $(el);
	cur_input = $this;
	var _val = $this.val().trim();
	//if(e.keyCode == 40){
		var suggestStr = $this.attr('suggestStr');
		if(suggestStr){
			var offset = $this.offset();
			var array = suggestStr.split('|');
			var tmp='';
			var html = '';
			var count = 0;
			for(var i=0; i<array.length; i++){
				tmp = array[i].split('=');
				if(_val.length >0 && (tmp[1].indexOf(_val) != -1 || tmp[0].indexOf(_val.toUpperCase()) != -1)){
					html+='<div class="item_normal" onmouseover="beMouseOver(this)" onmouseout="beMouseOut(this)" onclick="beClick(this)" value="'+tmp[1].trim()+'">'+tmp[1].trim()+'</div>';
					count++;
				} else if(_val.length <= 0){
					html+='<div class="item_normal" onmouseover="beMouseOver(this)" onmouseout="beMouseOut(this)" onclick="beClick(this)" value="'+tmp[1].trim()+'">'+tmp[1].trim()+'</div>';
					count++;
				}
			}
			if(count > 10){
				$suggest.css('height', '180px');
			} else {
				$suggest.css('height', 'auto');
			}
			$suggest.empty().append(html).css('left',offset.left + 'px')
		        .css('top',offset.top + $this.height() + 6 + 'px')
		        .css('width',$this.width() + 2 +'px')
		        .slideDown('fast');
		}
	//}
}



function doSearchLeave(el, e){
	//var $this = $(el);
	//if(!$this.contains(e.toElement()) && !cur_input.contains(e.toElement)){
		$('div.search_suggest').hide();
	//}
}

function cusCheckboxFmatter(cellvalue, options, rowObject){
	if(cellvalue == 'on')
		//return "<input type='checkbox' checked disabled='true'/>";
		return "<img src='images/checkbox_1.gif'/>";
	else if(cellvalue == 'off')
		//return "<input type='checkbox' disabled='true'/>";
		return "<img src='images/checkbox_0.gif'/>";
}

function cusCheckboxUnFmatter(cellvalue, options, cell){
	//return $('input', cell).is(':checked')?'on':'off';
	return $('img', cell).attr('src') == 'images/checkbox_1.gif'?'on':'off';
}

function getConfig(flag, name){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getSysCfgValue',
		type: 'POST',
		async: false,
		data: {"flag":flag,"name":name},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getDepartmentString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getDepartmentString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			if(data.length>0){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

function getAllDepartmentString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getAllDepartmentString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			if(data.length>0){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

function getMZDepartmentString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getMZDepartmentString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getMZDepartmentStringByHospital(hospital){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getMZDepartmentStringByHospital',
		type: 'POST',
		async: false,
		data: {'hospital':hospital},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}
function getOrderDepartmentString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getOrderDepartmentString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}


function getOrderDepartmentStringByHospital(hospital){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getOrderDepartmentStringByHospital',
		type: 'POST',
		async: false,
		data: {'hospital':hospital},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}




function getDocNameString(department){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getDocNameString',
		type: 'POST',
		async: false,
		data: {"department":department},
		success: function(data){
			if(data.length>0){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

function getPresTypeString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getPresTypeString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			if(data.length>0){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

// 获取统计类型
function getStatesTypeString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getStatesTypeString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

// 获取支付类型
function getPayTypeString(statesType){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getPayTypeString',
		type: 'POST',
		async: false,
		data: {"statesType":statesType},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getHisDrugString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getHisDrugString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			ret = data;
		}
	});
	return ret;
}

function getPwData(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getPwData',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getHisProductString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getHisProductString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			if(data.length>0){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

function getSelectOption(defaultValue){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getSelectOption',
		type: 'POST',
		async: false,
		data: {"defaultValue":defaultValue},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getSelectOption2(defaultValue){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getSelectOption2',
		type: 'POST',
		async: false,
		data: {"defaultValue":defaultValue},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getSelectOption3(defaultValue){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getSelectOption3',
		type: 'POST',
		async: false,
		data: {"defaultValue":defaultValue},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getDiagnoseString(){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getDiagnoseString',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			if(data.length){
				ret = "=|"+data;
			}
		}
	});
	return ret;	
}

function getOrderDiagnoseString(department,docGroup,inDate,outDate){
	var ret = '';
	$.ajax({
		url: '/xiaojd/getOrderDiagnoseString',
		type: 'POST',
		async: false,
		data: {"department":department,"docGroup":docGroup,"inDate":inDate,"outDate":outDate},
		success: function(data){
			ret = data;
		}
	});
	return ret;	
}

function getOptions(data) {
	if(data) {
		var str='';
		for(var i=0; i<data.length; i++){
			str += "<li><a href='#' title='"+data[i].alertType+"' value=\" and exists(select 0 from eng_order_message where cf_id=a.id and type='" + data[i].alertType + "')\">" + data[i].alertType + "</a></li>";
		}
		return str;
	}
}

function getSysConfigOptions(data){
	if(data) {
		var str='';
		for(var i=0; i<data.length; i++){
			str += "<li><a  href='#'  value=\"" + data[i] + "\">" + data[i] + "</a></li>";
		}
		return str;
	}
}

function getGridOptions(data, keyAsVal) {
	if(data) {
		var str='';
		var length = data.length;
		for(var i=0; i<length; i++) {
			if(data[i] != null && data[i] != '') {
				if(keyAsVal){
					if(i!=length-1) {
	            		str += data[i] + ":" + data[i] + ";";
		            } else {
		              	str += data[i] + ":" + data[i];
		            }
				} else {
					if(i!=length-1) {
	            		str += i + ":" + data[i] + ";";
		            } else {
		              	str += i + ":" + data[i];
		            }
				}
			}
		}
		return str;
	}
}
function geteobCheckType(){
	var ret;
	$.ajax({
		url: '/xiaojd/loadAlterType',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			var typeItem = data;
			if(typeItem){
				ret = typeItem.alterTypeList;
			}
		},
		error:function(){
			showAlter("查询警示类型错误！");
		}
	});
	return getOptions(ret);
/*	return ret;	
	var data= ["医院规定","不规范","适应症","相互作用","用法用量","配伍","过敏","孕产","给药途径","肾功能","剂型存在","重复用药"];
	return getOptions(data);*/
}

function getCheckType(){
	var ret;
	$.ajax({
		url: '/xiaojd/loadAlterType',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			var typeItem = data;
			if(typeItem){
				ret = typeItem.alterTypeList;
			}
		},
		error:function(){
			showAlter("查询警示类型错误！");
		}
	});
	return getTypeOptions(ret);
}

function getecfbCheckType(){
	var ret;
	$.ajax({
		url: '/xiaojd/loadAlterType',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			var typeItem = data;
			if(typeItem){
				ret = typeItem.alterTypeList;
			}
		},
		error:function(){
			showAlter("查询警示类型错误！");
		}
	});
	return getecfbTypeOptions(ret);
}

function getecfbCheckTypeToComment(){
	var ret;
	var str="=";
	$.ajax({
		url: '/xiaojd/loadAlterType',
		type: 'POST',
		async: false,
		data: {},
		success: function(data){
			var typeItem = data;
			if(typeItem){
				ret = typeItem.alterTypeList;
				for(var i=0; i<ret.length; i++){
					str += "|" +ret[i].alertType +"="+ret[i].alertType;
				}
			}
		},
		error:function(){
			showAlter("查询警示类型错误！");
		}
	});
	return str;
}

function getecfbTypeOptions(data){
	if(data) {
		var str='';
		for(var i=0; i<data.length; i++){
			str += "<li><a href='#' title='"+data[i].alertType+"' value=\" and exists(select 0 from eng_cf_message where cf_id=a.id and type='"+ data[i].alertType + "')\">" + data[i].alertType + "</a></li>";
		}
		return str;
	}
}

function getTypeOptions(data){
	if(data) {
		var str='';
		for(var i=0; i<data.length; i++){
			str += "<li><a href='#' title='"+data[i].alertType+"' value=\"" + data[i].alertType + "\">" + data[i].alertType + "</a></li>";
		}
		return str;
	}
}

function runCfItem(id){
	$.ajax({
		url: '/xiaojd/runCfById',
		type: 'POST',
		async: false,
		data: {"id":id},
		success: function(data){
			showAlertCfMes(data);
		},
		error: function(data){
			showAlert("审核失败：" + data);
		}
	});
	return false;
}

function showAlertCfMes(text, title, callback){
	var html =
		'<div class="dialog" id="dialog-message" style="overflow-y:auto;">' +
	    '  <p>' +
	    '    <span class="ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>' + text +
	    '  </p>' +
	    '</div>';
	$(html).dialog({
	      resizable: false,
	      modal: true,
	      width:450,
	      height:350,
	      /*show: {
	        effect: 'blind',
	        duration: 400
	      },*/
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
	$('button.ui-button').blur();
    return true;
}

function runOrder(id){
	$.ajax({
		url: '/xiaojd/runOrderById',
		type: 'POST',
		async: false,
		data: {"id":id},
		success: function(data){
			showAlertCfMes(data);
		},
		error: function(data){
			showAlert("审核失败：" + data);
		}
	});
	return false;
}

function confirmMessage(t,messageId,tbl,frm){
	$.ajax({
		url: '/xiaojd/changeFlag',
		type: 'POST',
		async: false,
		data: {"id":messageId,"flag":"1"},
		success: function(data){
			if(data && data=='1'){
				/*var postData = $(tbl).jqGrid("getGridParam", "postData"); 
			    $.extend(postData, {rpt_parameters:$(frm).serializeJson()});  
			    $(tbl).jqGrid("setGridParam", "postData", postData).trigger("reloadGrid");*/
				$(t).attr('style','cursor:not-allowed').attr('disabled','disabled')
					.next().attr('style','cursor:allowed').removeAttr('disabled');
			}
		}
	});
}
function cancelMessage(t,messageId,tbl,frm){
	$.ajax({
		url: '/xiaojd/changeFlag',
		type: 'POST',
		async: false,
		data: {"id":messageId,"flag":"0"},
		success: function(data){
			if(data && data=='1'){ 
				/*var postData = $(tbl).jqGrid("getGridParam", "postData"); 
			    $.extend(postData, {rpt_parameters:$(frm).serializeJson()});  
			    $(tbl).jqGrid("setGridParam", "postData", postData).trigger("reloadGrid");*/
				$(t).attr('style','cursor:not-allowed').attr('disabled','disabled')
					.prev().attr('style','cursor:allowed').removeAttr('disabled');
			}
		}
	});
}

function getSelectOptionText(el){
	var ret = [];
	$('#'+el).find('option').each(function () {
	    ret.push($(this).html().trim());
	});
	return ret.join(' ');
}

function showAlert(text, title, callback,mywidht,myheight){
	var html =
		'<div class="dialog" id="dialog-message" style="overflow-y:auto;">' +
	    '  <p>' +
	    '    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>' + text +
	    '  </p>' +
	    '</div>';
    $(html).dialog({
      resizable: false,
      modal: true,
      width:mywidht,
      height:myheight,
      /*show: {
        effect: 'blind',
        duration: 400
      },*/
      close: function() {
    	  $('#dialog-message').remove();
      },
      title: title || "提示信息",
      buttons: {
        "确定": function(a) {
          var dlg = $(this).dialog("close");
          callback && callback.call(dlg);
        }
      }
    });
    $('button.ui-button').blur();
    if(text.indexOf("成功") != -1&&(title == undefined)){
		setTimeout("$('#dialog-message').remove();",1000);
	}
    return true;
}

function adjustTabsArrow(){
	var totalWidth = 0;
	$tabs.find('li[role="tab"]').each(function(){
		totalWidth += $(this).width() + 6;
	});
	if(totalWidth < document.body.clientWidth){
		$('span.tabs-arrow').hide();
		$ul.css('padding-left', '0px');
		$ul.animate({left:0});
	}else{
		$ul.animate({left:(document.body.clientWidth - totalWidth-20)});
	}
}

/*
 * 打开规则设计器
 * */
function openDesigner(url){
	window.open(url);
}

function addTab(title, url) {
	var hasOpened = false, openedIndex=0;
	$("#tabs").find("a.ui-tabs-anchor").each(function(index){
		if(title == $(this).text()){
			hasOpened = true;
			openedIndex = index;
		}
	});
	
	if(hasOpened){
		$tabs.tabs("refresh").tabs('option', 'selected', openedIndex);
		var activeLeft = $tabs.find('li[aria-selected="true"]').position().left;
		var totalWidth = 0;
		$tabs.find('li[role="tab"]').each(function(index, item){
				totalWidth += $(this).width() + 6;
		});
		if(totalWidth - document.body.clientWidth > 0){
			if(totalWidth - activeLeft < document.body.clientWidth){
				$ul.animate({left:(document.body.clientWidth - totalWidth-20)});
			}else{
				$ul.animate({left:( -activeLeft+16)});
			}
			
		}
		
	} else {
//		if(tabCounter >8){
//			showAlert('您打开的选项卡过多，请关闭后重试!');
//			return false;
//		} else {
			var uri = url.replace('.jsp', '');
			var li = $(tabTemplate.replace(/@\{href\}/g,  "/xiaojd/u?param="+uri).replace(/@\{title\}/g, title));
			$tabs.find(".ui-tabs-nav").append(li);
			$tabs.tabs("refresh").tabs('option', 'selected', tabCounter);
			//adjust the height of center tab panel
			$tabs.find(".ui-tabs-panel").height($("div.ui-layout-center").height()-48);
			tabCounter++;
//		}//tabCounter < =8
		

		var totalWidth = 0;
		$tabs.find('li[role="tab"]').each(function(){
			totalWidth += $(this).width() + 6;
		});
		if(totalWidth > document.body.clientWidth){
			$ul.css('padding-left', '15px');
			$('span.tabs-arrow').show();
			$ul.animate({left:(document.body.clientWidth - totalWidth - 20)});
		}			
			
	}// tab id not exists
}

function resizeGrid(){
	var expandedPanel = $('#tabs').find('div[aria-expanded=true]');
	var resizeTbl = expandedPanel.find('table[id$=_tbl]');
	resizeTbl.setGridWidth(expandedPanel.width()-2);
	//resizeTbl.setGridHeight(target.height()-49);
}

function tipsClick(el){
	var _this=$(el);
	if(_this.hasClass("down")){
		_this.parent("div").animate({top:"32"-$(".tabs_menu_content").height()+"px"},200,function(){
			_this.removeClass("down");
			_this.parent("div").css({top:32-$(".tabs_menu_content").height()+"px"});
			if($.browser.msie && $.browser.version=='6.0')$('body select').css("visibility","visible");
		});
	}else{
		if($.browser.msie && $.browser.version=='6.0')$('body select').css("visibility","hidden");
		_this.parent("div").animate({top:"32px"},200,function(){
			_this.addClass("down");
		});
	}
}

function closeCur(){
	var lis = $("#tabs").find('li.ui-tabs-active').first();
	var selectedIndex = $tabs.tabs('option', 'selected');
	if(lis.find('span.ui-icon-close').length > 0){
		$tabs.tabs("remove",selectedIndex);
		tabCounter--;
		$('#close_menu').hide();
	}
	adjustTabsArrow();
}

function closeOthers(){
	var selectedIndex = $tabs.tabs('option', 'selected');
	$("#tabs").find('li[role="tab"]').each(function(index, item){
		if($(item).find('span.ui-icon-close').length > 0 && index != selectedIndex){
			var i = ($('li', $("#tabs")).index( item));
			$tabs.tabs("remove",i);
			tabCounter--;
		}
	});
	$('#close_menu').hide();
	adjustTabsArrow();
}

function closeAll(){
	$("#tabs").find('li[role="tab"]').each(function(index, item){
		if($(item).find('span.ui-icon-close').length > 0){
			var i = ($('li', $("#tabs")).index( item));
			$tabs.tabs("remove",i);
			tabCounter--;
		}
	});
	$('#close_menu').hide();
	adjustTabsArrow();
}

function closeLeft(){
	var selectedIndex = $tabs.tabs('option', 'selected');
	$("#tabs").find('li[role="tab"]').each(function(index, item){
		if(index < selectedIndex){
			if($(item).find('span.ui-icon-close').length > 0){
				var i = ($('li', $("#tabs")).index( item));
				$tabs.tabs("remove",i);
				tabCounter--;
			}
		}
	});
	$('#close_menu').hide();
	adjustTabsArrow();
}

function closeRight(){
	var selectedIndex = $tabs.tabs('option', 'selected');
	$("#tabs").find('li[role="tab"]').each(function(index, item){
		if(index > selectedIndex){
			if($(item).find('span.ui-icon-close').length > 0){
				var i = ($('li', $("#tabs")).index( item));
				$tabs.tabs("remove",i);
				tabCounter--;
			}
		}
	});
	$('#close_menu').hide();
	adjustTabsArrow();
}

function runData(cfid, url){
	$.ajax({
		type: "post",			
		url: url,
		data:{'cfid':cfid},
		dataType: 'json',
		async: true,
		success: function (data) {
			showAlertCfMes(resolve(data));
		},
		error: function(data){
			showAlert('系统出错！');
		}
	});
}

// 解析后台返回的审核信息
function resolve(data){
	var message = data.mes;
	var str = "";
	for(var i = 0; i < message.length; i++){
		str += message[i].drugName + "[" + message[i].severity + "]" + "," + message[i].message + "<br />";
	}
	return str;
}

// 退出系统
function showConfirm(text, title, callback){
	var html =
	    '<div class="dialog" id="dialog-message">' +
	    '  <p>' +
	    '    <span style="float: left; margin: 0 7px 0 0;"></span>' + text +
	    '  </p>' +
	    '</div>';
    return $(html).dialog({
      resizable: false,
      modal: true,
      /*show: {
        effect: 'blind',
        duration: 400
      },*/
      close: function() {
    	  $('#dialog-message').remove();
      },
      title: title || "系统提示",
      buttons: {
        "确定": function() {
          var dlg = $(this).dialog("close");
          callback && callback.call(dlg);
        },
        "取消": function() {
        	$(this).dialog("close");
        }
      }      
    });
}

function logout(){
	//获取当前网址，如： http://localhost:8888/xiaojd/home
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： xiaojd/home
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8888
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/xiaojd
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

	window.location.href = localhostPaht+projectName+'/logout';
}

function myFormatAmount(number) {   //format number like 100,000
	if(number != undefined && number != null && number != "" && number != "null"){
		var step = 3;
		var len = 0;
		if(parseFloat(number)<0){
			step = 4;
		}
		number = number.toString();
		 if(number.indexOf(".") == -1){
		 	 len = number.length;
			 if(len > step) {
				 var c1 = len%step,
					 c2 = parseInt(len/step),
					 arr = [];
					 first = number.substr(0, c1);
				 if(first != '') {
					 arr.push(first);
				 };
				 for(var i=0; i<c2; i++) {
					 arr.push(number.substr(c1 + i*step, step));                                    
				 };
				 number = arr.join(',');
			 };
			 return number;
		 }else{
			 var arrStr = number.split('.');
				 floatPart = arrStr[1];
			 number = arrStr[0];
			 var len = number.length;
			 if(len > step) {
				 var c1 = len%step;
					 c2 = parseInt(len/step);
					 arr = [];
					 first = number.substr(0, c1);
				 if(first != '') {
					 arr.push(first);
				 };
				 for(var i=0; i<c2; i++) {
					 arr.push(number.substr(c1 + i*step, step));                                    
				 };
				 number = arr.join(',');
			 };
			  return number + '.' + floatPart;
		 }
	}else{
		return number;
	}
}

jQuery.fn.showProperty = function(targetId,inputhidden,jsInputOnchange) {
	var _seft = this;

	$(this).live('click', function(){
		var A_top = $(this).position().top + $(this).outerHeight(true);
		var A_left =  $(this).position().left;
		var ul_height = $(targetId).height();
		if(ul_height > 120){
			$(targetId).height(120);
		}
		$(targetId).show().width($(this).width()+5).css({"position":"absolute","top":(A_top-1)+"px" ,"left":A_left+"px"}).find('a').each(function(index, item){
			if($(this).text().replace(/\s*/g, '') == $(_seft).val()){
				$(this).addClass('selected').parent().siblings().children().removeClass('selected');
				return false;
			}
		});
		$(this).css({'background':'url(images/arrow_up.gif) right center no-repeat #FFFFFF'});
		$(this).select();
	}).live('keydown', function(e){
		var id = $(this).attr('id'); //a_xxx
		var $list = $(targetId);
		var $cur = $list.find('a.selected');
		
		if(e.keyCode == 38){ 
			if($cur && $cur.length > 0){
				var index = $list.find('li').index($cur.parent());
				if(index == 0){
					$list.find('a').last().addClass('selected').parent().siblings().children().removeClass('selected');
				}else{
					var $prev = $cur.parent().prev();
					$prev.children().addClass('selected');
					$prev.siblings().children().removeClass('selected');
				}
			}else{
				$list.find('a').eq(0).addClass('selected').parent().siblings().children().removeClass('selected');
			}
			$(this).val($list.find('a.selected').text().replace(/\s*/g, '')).focus().select();
			e.preventDefault();
		}else if(e.keyCode == 40){
			if($cur && $cur.length > 0){
				var $lis = $list.find('li');
				var index = $lis.index($cur.parent());
				if(index < $lis.length-1){
					var $next = $cur.parent().next();
					$next.children().addClass('selected');
					$next.siblings().children().removeClass('selected');
				}else{
					$list.find('a').first().addClass('selected').parent().siblings().children().removeClass('selected');
				}
			}else{
				$list.find('a').eq(0).addClass('selected').parent().siblings().children().removeClass('selected');
			}
			$(this).val($list.find('a.selected').text().replace(/\s*/g, '')).focus().select();
			e.preventDefault();
		}else if(e.keyCode == 13){
			if($list.is(':visible')){
				$list.hide();
			}
		}
			
		
	});

	$(document).live('click', function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			$(_seft.selector).hideProperty(targetId);
		}
	});

	$(targetId).find('li').live('click', function(e){
		var $a = $(this).children();
		$(_seft).val($a.text());
		if(inputhidden != undefined && inputhidden.trim() != '' && inputhidden != null){
			var seftid = $(_seft).attr('id');
			var _backseft = seftid.substr(0,seftid.length-5);
			$("#"+_backseft).val($a.attr('value'));
		}
		if(jsInputOnchange != undefined && jsInputOnchange != null){
			jsInputOnchange();
		}
		$(_seft).attr("title",$a.text());
		return true;
	});
    return this;
};

jQuery.fn.showCheckProperty = function(targetId,inputhidden,src,dept) {
	var _seft = this;
	$(this).live('click', function(){
		var A_top = $(this).position().top + $(this).outerHeight(true);
		var A_left =  $(this).position().left;
		var ul_height = $(targetId).height();
		if(ul_height > 120){
			$(targetId).height(120);
		}
	$(targetId).show().width($(this).width()+5).css({"position":"absolute","top":(A_top-1)+"px" ,"left":A_left+"px"});
	});
	
	$(targetId).find("input[type='checkbox']").click(function(){
		var list = $(targetId).find("input[type='checkbox']");
		var choosed ='';
		var check = 0;
		var choosedAll ='';
		for (var i=0;i<list.length;i++) {
			var temp = list[i];
			 if(i >0)  {
			    choosedAll += "\',\'";//后台sql
			 }
			    choosedAll += temp.value;
			if(temp.checked == true) {
				 if(check >0) {
					choosed +="\',\'";//后台sql
				 }
				 check++;
				 choosed += temp.value;
			} 
		}
		var  showChoosed = choosed.replace(/\'/g,'');
		var _backseft = targetId.substr(0,targetId.length-5);
		$(_backseft).val(choosed);
		$(_backseft+"_back").val(showChoosed);
		
		if(!check) { //没选中，默认所有
			choosed = choosedAll
			$(_backseft).val(choosed);
		}

		if(src =='getOrderDepartmentStringByHospital') {
			if(choosed !='') {
				choosed ="\'" +choosed+"\'"; //首尾添加’
			} else { choosed = null}
			$(dept).attr('suggestStr', getOrderDepartmentStringByHospital(choosed));
		}
		
		if(src =='getMZDepartmentStringByHospital') {
			if(choosed !='') {
				choosed ="\'" +choosed+"\'"; //首尾添加’
			} else {choosed = null}
			$(dept).attr('suggestStr', getMZDepartmentStringByHospital(choosed));
		}
     });
	
	$(document).live('click', function(event){
		if(event.target.id.substring(1) ==_seft.selector.substring(1)) 
			  return
		if(event.target.id!=_seft.selector.substring(1)){
			$(_seft.selector).hideProperty(targetId);
		}
	});
	
    return this;
};
//默认值，选中所有
function defaultChoosedAllHospital(targetId,src,dept) {
	var list = $(targetId).find("input[type='checkbox']");
	var choosed = '';
	for (var i=0;i<list.length;i++) {
		var temp = list[i];
		 if(i >0)  {
			 choosed += "\',\'";//后台sql
		 }
		 choosed += temp.value;
	}
	var _backseft = targetId.substr(0,targetId.length-5);
	$(_backseft).val(choosed);
	if(src =='getOrderDepartmentStringByHospital') {
		if(choosed !='') {
			choosed ="\'" +choosed+"\'"; //首尾添加’
		} else {choosed = null}
		$(dept).attr('suggestStr', getOrderDepartmentStringByHospital(choosed));
	}
	
	if(src =='getMZDepartmentStringByHospital') {
		if(choosed !='') {
			choosed ="\'" +choosed+"\'"; //首尾添加’
		} else { choosed = null}
		$(dept).attr('suggestStr', getMZDepartmentStringByHospital(choosed));
	}	
}
jQuery.fn.hideProperty = function(targetId){
	$(targetId).hide();
	$(this).css('background', 'url(images/arrow_down.gif) right center  no-repeat #FFFFFF');
};
jQuery.fn.showUlProperty = function(targetId,inputhidden,customheight,mypublic) {
	var _seft = this;
	$(this).live('click', function(){
		$(targetId).empty().append($(this).attr('suggestStr'));
		var _commonUltop;
		var _commonUlleft;
		if(mypublic == "home_jsp"){
			_commonUltop = $(this).offset().top;
			_commonUlleft = $(this).offset().left;
		}else{
			_commonUltop = $(this).position().top;
			_commonUlleft = $(this).position().left;
		}
		var A_top = _commonUltop + $(this).outerHeight(true);
		var A_left =  _commonUlleft;
		var ul_height = $(targetId).height();
		if(ul_height > 100){
			if(customheight != undefined && customheight != null){
				$(targetId).height(customheight);
			}else{
				$(targetId).height(100);
			}
		}
		$(targetId).show().width($(this).width()+5).css({"position":"absolute","top":(A_top-1)+"px" ,"left":A_left+"px"}).find('a').each(function(index, item){
			if($(this).text() == $(_seft).val()){
				$(this).addClass('selected').parent().siblings().children().removeClass('selected');
				return false;
			}
		});
		$(this).css({'background':'url(images/arrow_up.gif) right center no-repeat #FFFFFF'});
		$(this).select();
	}).live('keydown', function(e){
		var id = $(this).attr('id'); //a_xxx
		var $list = $(targetId);
		var $cur = $list.find('a.selected');
		
		if(e.keyCode == 38){ 
			if($cur && $cur.length > 0){
				var index = $list.find('li').index($cur.parent());
				if(index == 0){
					$list.find('a').last().addClass('selected').parent().siblings().children().removeClass('selected');
				}else{
					var $prev = $cur.parent().prev();
					$prev.children().addClass('selected');
					$prev.siblings().children().removeClass('selected');
				}
			}else{
				$list.find('a').eq(0).addClass('selected').parent().siblings().children().removeClass('selected');
			}
			$(this).val($list.find('a.selected').text()).focus().select();
			e.preventDefault();
		}else if(e.keyCode == 40){
			if($cur && $cur.length > 0){
				var $lis = $list.find('li');
				var index = $lis.index($cur.parent());
				if(index < $lis.length-1){
					var $next = $cur.parent().next();
					$next.children().addClass('selected');
					$next.siblings().children().removeClass('selected');
				}else{
					$list.find('a').first().addClass('selected').parent().siblings().children().removeClass('selected');
				}
			}else{
				$list.find('a').eq(0).addClass('selected').parent().siblings().children().removeClass('selected');
			}
			$(this).val($list.find('a.selected').text()).focus().select();
			e.preventDefault();
		}else if(e.keyCode == 13){
			if($list.is(':visible')){
				$list.hide();
			}
		}
			
		
	});

	$(document).live('click', function(event){
		if(event.target.id!=_seft.selector.substring(1)){
			$(_seft.selector).hideProperty(targetId);
		}
	});

	$(targetId).find('li').live('click', function(e){
		var $a = $(this).children();
		$(_seft).val($a.text());
		if(inputhidden != undefined && inputhidden.trim() != '' && inputhidden != null){
			var seftid = $(_seft).attr('id');
			var _backseft = seftid.substr(0,seftid.length-5);
			$("#"+_backseft).val($a.attr('value'));
		}
		$(_seft).attr("title",$a.text());
	});
    return this;
};

function banBackSpace(e){
    var ev = e || window.event;//获取event对象
    var obj = ev.target || ev.srcElement;//获取事件源
    var t = obj.type || obj.getAttribute('type');//获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1= ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")&& (vReadOnly==true || vDisabled==true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2= ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea" ;
    //判断
    if(flag2 || flag1)return false;
}

function getDataDepartment(BeginDataJs,getBeginDataSession,endDataJs,getEndDataSession,DepartmentJs,getDepartmentSession){
	getBeginDataParaValue(BeginDataJs,getBeginDataSession);
	getEndDataParaValue(endDataJs,getEndDataSession);
	if(DepartmentJs != undefined && DepartmentJs != null){
		getDepartmentParaValue(DepartmentJs,getDepartmentSession);
	}
}
function getBeginDataParaValue(BeginDataJs,getBeginDataSession){
	if(getBeginDataSession != "null" && getBeginDataSession.trim()!=""){
		$("#"+BeginDataJs).val(getBeginDataSession);
	}
}
function getEndDataParaValue(endDataJs,getEndDataSession){
	if(getEndDataSession != "null" && getEndDataSession.trim() != ""){
		$("#"+endDataJs).val(getEndDataSession);
	}
}
function getDepartmentParaValue(DepartmentJs,getDepartmentSession){
	if(getDepartmentSession != "null" && getDepartmentSession.trim() != "" && DepartmentJs != undefined && DepartmentJs != null){
		$("#"+DepartmentJs).val(getDepartmentSession);
	}
}
function public_changeDocName(departmentValue,docNameValue,roleid){
	if(roleid != "4"){//6临床主任，4临床医生
		var v = $('#'+departmentValue).val();
		if(v && v.length > 0){
			$("#"+docNameValue).val("").attr('suggestStr', getDocNameString(v));
		} else {
			$("#"+docNameValue).val("").attr('suggestStr', '');
					
		}
	}
}


String.prototype.StartWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
		return false;
	if(this.substr(0,str.length)==str)
		return true;
	else
	    return false;
	return true;
}
function getRoleDepatement(orgName,roleid,inputDepatementId){
	if(roleid == "4" || roleid == "6"){//6临床主任，4临床医生
		$("#"+inputDepatementId).val(orgName);
		$("#"+inputDepatementId).attr("readonly","readonly"); 
	}
}
function getRoleDoc(userName,roleid,inputUserName){
	if(roleid == "4"){//6临床主任，4临床医生
		$("#"+inputUserName).val(userName);
		$("#"+inputUserName).attr("readonly","readonly"); 
	}
}
function hidePgSelbox(){
	var ISIE6 = navigator.userAgent.indexOf('MSIE 6') >= 0;
	if(ISIE6){ 
		$(".ui-pg-selbox").hide();
	}
}
function showPgSelbox(){
	var ISIE6 = navigator.userAgent.indexOf('MSIE 6') >= 0;
	if(ISIE6){ 
		$(".ui-pg-selbox").show();
	}  
}
function showAjaxConfirm(context,SubmitAjax,dataValue){
	var html =
	    '<div class="dialog" id="dialog-message">' +
	    '  <p>' +
	    '    <span style="float: left; margin: 0 7px 0 0;"></span>' + context +
	    '  </p>' +
	    '</div>';
     return $(html).dialog({
      resizable: false,
      modal: true,
     /* show: {
        effect: 'blind',
        duration: 400
      },*/
      close: function() {
    	  $('#dialog-message').remove();
      },
      title: "系统提示",
      buttons: {
        "确定": function() {
          var dlg = $(this).dialog("close");
          if(SubmitAjax != undefined && SubmitAjax != null){
        	  if(dataValue != undefined && dataValue != null){
        		  SubmitAjax(dataValue);  
        	  }else{
        		  SubmitAjax();
        	  }  
          }
          
        },
        "取消": function() {
        	$(this).dialog("close");
        }
      }      
    });
}
function myFormatFractionalAmount(number,indexs) {   //格式化数值 100.7999选择保留几位小数
	if(number != undefined && number != null && number != ""){
		number =  parseFloat(number).toFixed(indexs);
	}
	return number;
}
var jqgridWidthView = (document.documentElement.clientWidth || document.body.clientWidth)-10;//控制查询报表选择下一级表格宽度
function formatterServiceDocName(doc_name){
	if(doc_name != null && doc_name.trim() != "" && doc_name != "null"){
		if('1'==ConfigServiceDocName){
			return doc_name;
		}else{
				if(doc_name.indexOf("合计: </div>")>0){
					return doc_name;
				}else{
					return doc_name.substr(0,1)+"**";	
				}
			}
	}else{
		if(doc_name == null || doc_name != "null"){
			return "";
		}
		return doc_name;
	}
}
function myUnFormatAmount(number){
	if(number != undefined && number != null && number != "" && number != "null"){
		var a = parseFloat(number.replace(/,/g,""));
		return a;
	}else{
		return parseFloat(0);
	}
}
(function($,h,c){var a=$([]),e=$.resize=$.extend($.resize,{}),i,k="setTimeout",j="resize",d=j+"-special-event",b="delay",f="throttleWindow";e[b]=250;e[f]=true;$.event.special[j]={setup:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.add(l);$.data(this,d,{w:l.width(),h:l.height()});if(a.length===1){g()}},teardown:function(){if(!e[f]&&this[k]){return false}var l=$(this);a=a.not(l);l.removeData(d);if(!a.length){clearTimeout(i)}},add:function(l){if(!e[f]&&this[k]){return false}var n;function m(s,o,p){var q=$(this),r=$.data(this,d);r.w=o!==c?o:q.width();r.h=p!==c?p:q.height();n.apply(this,arguments)}if($.isFunction(l)){n=l;return m}else{n=l.handler;l.handler=m}}};function g(){i=h[k](function(){a.each(function(){var n=$(this),m=n.width(),l=n.height(),o=$.data(this,d);if(m!==o.w||l!==o.h){n.trigger(j,[o.w=m,o.h=l])}});g()},e[b])}})(jQuery,this);


function processGridValue(tbl){
	var gridTable = tbl;
    var cell;
    var array=gridTable.getDataIDs();
    for ( var i = 0; i < array.length; i++) {
        var rowarray = gridTable.getRowData(array[i]);
        cell = gridTable.getCell(array[i], "drugName");
        if(cell){
        	gridTable.setCell(array[i],"drugName", '<a target="_blank" style="text-decoration: underline;" href="getHisSms?ypmcid=' + (rowarray.drug? rowarray.drug : rowarray.drugId) + '">'+cell+'</a>');	
        }else{
        	cell = gridTable.getCell(array[i], "drug_name");	
        	gridTable.setCell(array[i],"drug_name", '<a target="_blank" style="text-decoration: underline;" href="getHisSms?ypmcid=' + (rowarray.drug? rowarray.drug : rowarray.drug_id) + '">'+cell+'</a>');
        }
        
    }
}

//覆盖toFixed方法  0.01  ie变成0
function myToFixed(myNumber,len)  
{  
    var tempNum = 0,temp1 = 0;  
    var s,temp;  
    var s1 = myNumber + "";  
    var start = s1.indexOf(".");
    if(start == -1){
    	temp1 = myNumber+'.00';
    }else{
    	var arrStr = s1.split('.');
    	if(arrStr[1].length == 1){
    		temp1 = myNumber+'0';
    	}else if(arrStr[1].length > len){
		    //截取小数点后,0之后的数字，判断是否大于5，如果大于5这入为1  
		   if(s1.substr(start+len+1,1)>=5)  
		    tempNum=1;  
		    //计算10的len次方,把原数字扩大它要保留的小数位数的倍数  
		   var temp = Math.pow(10,len);  
		    //求最接近this * temp的最小数字  
		    //floor() 方法执行的是向下取整计算，它返回的是小于或等于函数参数，并且与之最接近的整数  
		    s = Math.floor(myNumber * temp) + tempNum;
		    temp1 = myToFixed(s/temp); 
    	}else{
    		temp1 = myNumber;
    	}
    }
    return temp1;
}

function initDrugNameTooltip($el){
	$el.bind( "input.autocomplete", function(){
		$(this).trigger('keydown.autocomplete');
	});
	$el.autocomplete(path+'/getHisDrugName',{
		minChars: 1, //在触发autoComplete前用户至少需要输入的字符数.Default: 1
		max: 10,     //autoComplete下拉显示项目的个数.Default: 10
		delay: 300,  //击键后激活autoComplete的延迟时间(单位毫秒).Default: 远程为400 本地10
		autoFill: false,    // 要不要在用户选择时自动将用户当前鼠标所在的值填入到input框. Default: false
		multiple: false,   //是否允许搜索框追加
		multipleSeparator: ",", //搜索框追加后缀格式 如：搜索值1,搜索值2
		scroll: true,
		scrollHeight: 300,
		dataType: "json", //json类型
	    parse: function(data) {
	    	return $.map(data, function(row) {
	             return {
	                 data: row,
	                 value: row,
	                 result: row
	             };
	         });
	    },
	    formatItem: function(item) { //对结果中的每一行都会调用这个函数,返回值将用LI元素包含显示在下拉列表中
			return item;
	    },
	    width: 195
	});
}

/**
 * 合并jqGrid单元格
 */
function Merge(gridName, CellNames, flag, prefix, suffix) {
     var mya = $("#" + gridName + "").getDataIDs();
     var length = mya.length;
     for (var i = 0; i < length; i++) {
         var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
         var rowSpanTaxCount = 1;
         for (j = i + 1; j <= length; j++) {
             var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
             if (before[CellNames[0]] == end[CellNames[0]]) {
                 rowSpanTaxCount++;
                 for(var k=0; k<CellNames.length; k++){
                	 $("#" + gridName + "").setCell(mya[j], CellNames[k], '', { display: 'none' });
                 }
             } else {
                 rowSpanTaxCount = 1;
                 if(flag && j==i+1){
                	 for(var k=0; k<CellNames.length; k++){
                    	 var cell = $("#" + CellNames[k] + "" + mya[i] + "");
                    	 var ht = parseInt(cell.html());
                    	 cell.empty().html(ht+' ('+prefix+rowSpanTaxCount+suffix+')');
                     } 
                 }
                 break;
             }
             for(var k=0; k<CellNames.length; k++){
            	 var cell = $("#" + CellNames[k] + "" + mya[i] + "");
            	 cell.attr("rowspan", rowSpanTaxCount);
            	 if(flag){
            		 var ht = parseInt(cell.html());
                	 cell.empty().html(ht+' ('+prefix+rowSpanTaxCount+suffix+')');
            	 }
             }
         }
     }
 }

function showConfirm(text, title, callback,mywidht,myheight){
	var html =
		'<div class="dialog" id="dialog-message" style="overflow-y:auto;">' +
	    '  <p>' +
	    '    <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>' + text +
	    '  </p>' +
	    '</div>';
    $(html).dialog({
      resizable: false,
      modal: true,
      width:mywidht,
      height:myheight,
      close: function() {
    	  $('#dialog-message').remove();
      },
      title: title || "提示信息",
      buttons: {
        "确定": function() {
          $(this).dialog("close");
          callback && callback.call(this,true);
        },
        "取消": function() {
            $(this).dialog("close");
            callback && callback.call(this,false);
          }
      }
    });
    $('button.ui-button').blur();
    if(text.indexOf("成功") != -1&&(title == undefined)){
		setTimeout("$('#dialog-message').remove();",1000);
	}
    return true;
}

function dateLimit(val1, val2, callback){
	var bDate = new Date(val1.replace(/-/g,"/"));
	var eDate = new Date(val2.replace(/-/g,"/"));
	if((eDate - bDate) / (1000* 3600 * 24) > 92 || val1 == "" || val2 == ""){
		showConfirm('确定要查询天数超过3个月的数据？', '', callback);
	}else{
		 callback && callback.call(this,true);
	}
}
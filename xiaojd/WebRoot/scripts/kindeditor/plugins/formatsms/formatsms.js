/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin('formatsms', function(K) {
	var self = this, name = 'formatsms', undefined;
	self.clickToolbar(name, function() {
		var sms = self.text();
		if(pathUrl != null && sms != ''){
			var url = pathUrl+'/formatSms';
			$.ajax({
				type : 'post',
				url : url,
				dataType : 'json',
				data:{'sms':sms},
				async : true,
				success : function(data) {
					if(data != null && data['success'] != null && data['success'] != ''){
						self.html(data['success']);
					}
				},
				error : function(data) {
					alert('初始化说明书格式出错!');
				}
			});
		}
		
	});
});

function post_jspTable(tableid,reportformname,startdata,enddata){
	var ptable = "";
	var tableHtml = "";
	$("#gview_"+tableid).find("table").each(function(i){
		//ptable += $(this)[0].outerHTML;
				ptable += " <table border=1> ";
				$(this).find("tr").each(function(itr){
					if($(this).children().text() != ""){
						ptable +=" <tr> ";
						$(this).children().each(function(itr){
							 if($(this)[0].style.display!="none"){
								 var rowspan = $(this).attr("rowSpan");
								 var colspan = $(this).attr("colSpan");
								 if (rowspan == undefined) {
								 	rowspan = "1";
								 }
								 if(colspan == undefined){
									 colspan = "1";
								 }
								 tableHtml = $(this)[0].innerHTML.replace("&nbsp;","");
								 if($(this)[0].style.textAlign == "left"){
									 tableHtml += "&nbsp;"; 
								 }
								 ptable +="<td rowspan='"+rowspan+"' colspan='"+colspan+"'>"+tableHtml+"</td>";
							}
						});
						ptable +=" </tr> ";
					}
				});
				ptable += " </table> ";
	});
	
    $("#gettable").val(ptable);
    $("#reportformname").val(reportformname);
    $("#startdata").val(startdata);
    $("#enddata").val(enddata);
    document.getElementById('postexcel').submit();
}
function get_jspExcel(_url){
	// _url += "startDate=" + $('#ecfb_begin').val() + "&endDate=" + $('#ecfb_end').val();
	// alert(_url);
	window.location.href = _url;
}
function post_jspTable_cascade(tableid,reportformname,startdata,enddata){
	var ptable = "";
	var tableHtml = "";
	$("#gview_"+tableid).find("table").each(function(i){
		//ptable += $(this)[0].outerHTML;
				ptable += " <table border=1> ";
				var count = this.id;
				if(count.length > 2 && count.substr(count.length-2,count.length) == "_t"){
					
				}else{
					$(this).find("tr").each(function(itr){
						if($(this).children().text() != ""){
							ptable +=" <tr> ";
							$(this).children().each(function(itr){
								 if($(this)[0].style.display!="none"){
									 var rowspan = $(this).attr("rowSpan");
									 var colspan = $(this).attr("colSpan");
									 if (rowspan == undefined) {
									 	rowspan = "1";
									 }
									 if(colspan == undefined){
										 colspan = "1";
									 }
									 var divlength = $(this).find("div[id^='"+tableid+"']");
									 if(divlength.length <= 0){
										 tableHtml = $(this)[0].innerHTML.replace("&nbsp;","");
										 if($(this)[0].style.textAlign == "left"){
											 tableHtml += "&nbsp;"; 
										 }
										 ptable +="<td  rowspan='"+rowspan+"' colspan='"+colspan+"'>"+tableHtml+"</td>";
									 }
								}
							});
							ptable +=" </tr> ";
						}
					});
					ptable += " </table> ";
				}
	});
    $("#gettable").val(ptable);
    $("#reportformname").val(reportformname);
    $("#startdata").val(startdata);
    $("#enddata").val(enddata);
    document.getElementById('postexcel').submit();
}
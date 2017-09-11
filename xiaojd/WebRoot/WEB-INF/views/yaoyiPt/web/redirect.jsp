<%
	String url = (String) request.getSession().getAttribute("XIAOJD-PTURL");
	if(url != null && url.trim().length() > 0){
		request.getSession().removeAttribute("XIAOJD-PTURL");
		response.sendRedirect("loginPt");
	}else{
		response.sendRedirect("homePt");
	}
%>
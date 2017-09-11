<%
	request.getSession().removeAttribute("XIAOJD-USER");
	response.sendRedirect(request.getContextPath());
%>
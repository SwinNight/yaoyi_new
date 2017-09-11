package com.xiaojd.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.entity.util.Config;

public class SessionInterceptor implements HandlerInterceptor {
	
	Logger loger = LoggerFactory.getLogger(SessionInterceptor.class);
	
	private List<String> excludedUrls;  
	   
	public void setExcludedUrls(List<String> excludedUrls) {  
		this.excludedUrls = excludedUrls;  
	}  
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object o) throws Exception {
		String requestUri = request.getRequestURI();
		loger.debug("Request URL: " + requestUri);
		for (String url : excludedUrls) {
			if (requestUri.endsWith(url)) {
				return true;
			}
		}  
		String hospitalName = Config.getValue("server", "hospitalName"); 
		if(hospitalName !=null && "苏州药易".equals(hospitalName)) {	    //苏州药易返回界面
			EngPtUser ptUser = (EngPtUser) request.getSession().getAttribute("XIAOJD-PTUSER");
			if(ptUser != null) {
				loger.debug("用户: " + ptUser.getName());
			}
			if(ptUser == null || ptUser.getName() == null) {
				if(requestUri.indexOf("mobile")>-1) { //手机端
					response.sendRedirect("/xiaojd/mobileLoginPtA");
					return false;
				} else {
				    response.sendRedirect("/xiaojd/loginPtA");
				    return false;
				}
			}
			
		}  
		return true;
	}

}

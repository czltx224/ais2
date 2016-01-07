package com.xbwl.rbac.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ralasafe.WebRalasafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeesionOutFilter implements Filter {
	
	Logger logger=LoggerFactory.getLogger(SeesionOutFilter.class);

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request=(HttpServletRequest)req;
		HttpServletResponse response=(HttpServletResponse)res;
		if(WebRalasafe.getCurrentUser(request)==null){
			
			logger.debug("�û���ʱ��");
			
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with")
					.equalsIgnoreCase("XMLHttpRequest")) {
				response.setStatus(401);
			} else {
	
				/* ��ͨhttp����session��ʱ�Ĵ��� */
				request.getSession().removeAttribute("denyMessage");
				request.setAttribute("denyMessage", "��¼��ʱ�������µ�¼��");
				response.sendRedirect("login.jsp");
	        } 
		}
		chain.doFilter(req, res);

	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

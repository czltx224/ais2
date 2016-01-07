/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
package com.xbwl.rbac.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ralasafe.WebRalasafe;

import com.xbwl.common.log.service.ISysLogService;
import com.xbwl.common.utils.SpringContextHolder;
import com.xbwl.entity.SysLog;


public class LogoutServlet extends HttpServlet {
	
	private SpringContextHolder springContextHolder;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ISysLogService sysLog=springContextHolder.getBean("sysLogService");
		SysLog log=new SysLog();
		log.setClientIp(req.getRemoteAddr());
		log.setCreatedtime(new Date());
		log.setLogType("ÍË³öÏµÍ³");
		log.setOperAccount(WebRalasafe.getCurrentUser(req).get("name").toString());
		
		sysLog.save(log);
		
		WebRalasafe.setCurrentUser(req, null);
		req.getSession().invalidate();
		String toUrl = "login.jsp";
		resp.sendRedirect(toUrl);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}

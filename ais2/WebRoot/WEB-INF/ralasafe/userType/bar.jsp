<%
/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.ralasafe.user.User"%>

<%
	String strShowApp = request.getParameter("showSelectApp");
	String strShowUserType = request.getParameter("showSelectUserType");
	String selectAppUrl = request.getParameter("selectAppUrl");
	String selectUserTypeUrl = request
			.getParameter("selectUserTypeUrl");
	boolean showApp = "true".equalsIgnoreCase(strShowApp);
	boolean showUserType = "true".equalsIgnoreCase(strShowUserType);
	if (selectAppUrl != null) {
		showApp = true;
	}
	if (selectUserTypeUrl != null) {
		showUserType = true;
	}
%>



<div id="header">
<div id="logo">
<h1><a href="#"></a>&nbsp;</h1>
<p>&nbsp;&nbsp;<b>Security&nbsp;&nbsp;&nbsp;&nbsp;Efficiency&nbsp;&nbsp;&nbsp;&nbsp;Innovation</b> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>
</div>
<div id="menu"></div>


<%
	if (showApp) {
%>
<div id="menuleft"><jsp:include page="app/selectApp.jsp">
	<jsp:param name="url" value="<%=selectAppUrl%>" />
</jsp:include></div>
<%
	}
%> <%
 	if (showUserType) {
 %>
<div id="menuleft"><jsp:include page="userType/selectUserType.jsp">
	<jsp:param name="url" value="<%=selectUserTypeUrl%>" />
</jsp:include></div>
<%
	}
%>
</div>
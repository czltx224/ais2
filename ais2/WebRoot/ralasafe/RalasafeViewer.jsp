<%
/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="org.ralasafe.servlet.WebUtil"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="org.ralasafe.WebConstants"%>
<%@page import="org.ralasafe.privilege.Privilege"%>
<%@page import="org.ralasafe.util.Util"%>
<%@page import="org.ralasafe.ResourceConstants"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="gwt:property" content="locale=<%=request.getLocale().getLanguage() %>">
<!--meta name="gwt:property" content="locale=jp"-->

<title>Ralasafe <%=Util.getMessage(request.getLocale(), ResourceConstants.DESIGNER) %></title>
<link rel="icon" href="favicon.ico" mce_href="favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="favicon.ico" mce_href="favicon.ico" type="image/x-icon"> 
<link rel="stylesheet" href="RalasafeViewer.css">
<link rel="stylesheet" type="text/css"	href="js/ext/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css"	href="js/ext/resources/css/ext-patch.css" />
<script>
		// Should show 'User' panel?
		var <%=WebConstants.SHOW_USER_ROLE_PANEL%>="<%=request.getAttribute(WebConstants.SHOW_USER_ROLE_PANEL)%>";
		// Should show 'Role' panel?
		var <%=WebConstants.SHOW_ROLE_ADMIN_PANEL%>="<%=request.getAttribute(WebConstants.SHOW_ROLE_ADMIN_PANEL)%>";
		// Should show 'Privilege', 'User Category', 'Business Data' and 'Query' panels?
		var <%=WebConstants.SHOW_POLICY_ADMIN_PANELS%>="<%=request
									.getAttribute(WebConstants.SHOW_POLICY_ADMIN_PANELS)%>";
		// User fields
		var <%=WebConstants.USER_FIELDS%>="<%=request.getAttribute(WebConstants.USER_FIELDS)%>";
		// Display names of these user fields
		var <%=WebConstants.USER_FIELD_DISPLAY_NAMES%>="<%=request
									.getAttribute(WebConstants.USER_FIELD_DISPLAY_NAMES)%>";
		// What user fields show be displayed?
		var <%=WebConstants.SHOW_USER_FIELDS%>="<%=request.getAttribute(WebConstants.SHOW_USER_FIELDS)%>";
		</script>
</head>

<body>
<!--add loading indicator while the app is being loaded-->
<div id="loading">
<div class="loading-indicator"><img
	src="js/ext/resources/images/default/shared/blue-loading.gif"
	width="32" height="32"
	style="margin-right: 8px; float: left; vertical-align: top;" />Designer<br />
<span id="loading-msg">Loading styles and images...</span></div>
</div>


<!--include the Ext Core API-->
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading Core API...';</script>
<script type="text/javascript" src="js/ext/adapter/ext/ext-base.js"></script>

<!--include Ext -->
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Loading UI Components...';</script>
<script type="text/javascript" src="js/ext/ext-all.js"></script>

<!--                                            -->
<!-- This script is required bootstrap stuff.   -->
<!-- You can put it in the HEAD, but startup    -->
<!-- is slightly faster if you include it here. -->
<!--                                            -->
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = 'Initializing...';</script>
<script language="javascript"
	src="org.ralasafe.ui.RalasafeViewer.nocache.js"></script>

<!--hide loading message-->
<script type="text/javascript">Ext.get('loading').fadeOut({remove: true, duration:.25});</script>

<!-- OPTIONAL: include this if you want history support -->
<iframe id="__gwt_historyFrame" style="width: 0; height: 0; border: 0"></iframe>

</body>
</html>

<%
/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Collection,java.util.Iterator"%>    
<%@page import="org.ralasafe.userType.UserType,org.ralasafe.metadata.user.*;"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link href="css/default.css" rel="stylesheet" type="text/css"/>
<link rel="shortcut icon" href="favicon.ico">
<title>User type definition</title>
</head>
<body>

<jsp:include page="bar.jsp"/>


<div id="page" style="margin-top: 23px">

<div id="content">
<div id="latest-post" class="post">

<h1>User type definition</h1>

<%
UserType userType=(UserType) request.getAttribute("userType" );
UserMetadata umd=userType.getUserMetadata();
TableMetadata tmd=umd.getMainTableMetadata();
Boolean editable=(Boolean) request.getAttribute( "editable" );
%>
<div class="entry">
<table width="400" border="0" cellpadding="5" cellspacing="0">
	<tr>
	
	<tr>
	<td>Description:</td>
	<td><%=userType.getDesc()%></td>
	</tr>
	
	<tr>
	<td>Sql table:</td>
	<td><%=tmd.getSqlTableName()%></td>
	</tr>	
</table>
</div>

<h1>Column info</h1>
<div class="entry">
<table width="700" border="0" cellpadding="5" cellspacing="1"
	bgcolor="#CCCCCC" id="b_g_date">
	<thead>
	<tr bgcolor="#F4F4F4">
	<% if( editable.booleanValue() ) { %>
	<th>Check</th>
	<% } %>
	<th>Name</th>
	<th>Column</th>
	<th>Type</th>
	</tr>
	</thead>
	
	<tbody>
		<%
		FieldMetadata[] fmds=tmd.getFields();
		for( int i=0; i<fmds.length; i++ ) { %>
		<tr  bgcolor="#F4F4F4">
			<% if( editable.booleanValue() ) { %>
			<td><input type="checkbox" name="fields" value="<%=fmds[i].getName() %>"/></td>
			<%}%>
			<td><%=fmds[i].getName() %></td>
			<td><%=fmds[i].getColumnName() %></td>
			<td><%=fmds[i].getSqlType() %></td>
		</tr>
		<%} %>
	</tbody>
</table>

	<% if( editable.booleanValue() ) {
	%>
	<input type="submit" value="Submit"/>&nbsp;&nbsp;
	<input type="reset"/>
	<% } %>
	<input type="button" value="Return" onclick="javascript:window.location='userTypeMng'"/>
</div>
<jsp:include page="foot.jsp"/>
</div>
</div>
</div>



</body>
</html>
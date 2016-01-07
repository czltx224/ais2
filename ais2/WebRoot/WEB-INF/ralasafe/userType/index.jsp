<%
/**
 * Copyright (c) 2010 Wang Jinbao, http://www.ralasafe.com
 * Licensed under the MIT license: http://www.opensource.org/licenses/mit-license.php
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="java.util.Collection,java.util.Iterator"%>
<%@page import="org.ralasafe.userType.UserType"%>
<%@page import="java.util.Random"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<link href="css/default.css" rel="stylesheet" type="text/css"/>
<link rel="icon" href="favicon.ico" mce_href="favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="favicon.ico" mce_href="favicon.ico" type="image/x-icon"/> 
<title>User Type Management</title>
</head>
<body>
<script language="javascript">
	
<%Collection userTypes = (Collection) request
					.getAttribute("userTypes");
			StringBuffer buff = new StringBuffer();
			int i = 0;
			for (Iterator iter = userTypes.iterator(); iter.hasNext();) {
				if (i != 0) {
					buff.append(",");
				}
				i++;
				UserType userType = (UserType) iter.next();
				buff.append("\"").append(userType.getName()).append("\"");
			}%>
	function submitUserTypeForm() {
		var names = new Array(
<%=buff.toString()%>
	);

		var oForm = document.getElementById("userTypeForm");
		var oName = oForm.name.value;
		var oDesc = oForm.desc.value;
		var oFile = oForm.userDefineFile.value;

		if (oName == null || oName.length == 0) {
			alert("Please input name");
			return false;
		}
		if (oDesc == null || oDesc.length == 0) {
			alert("Please input description");
			return false;
		}
		if (oFile == null || oFile.length == 0) {
			alert("Please select a definition file");
			return false;
		}
		for ( var i = 0; i < names.length; i++) {
			if (names[i] == oName) {
				oForm.op.value = "update";
				var oConfirm = window.confirm("Are you sure to update this user type definition?");
				if (oConfirm) {
					//oForm.submit();
					return true;
				} else {
					return false;
				}
			}
		}

		oForm.op.value = "add";
		//oForm.submit();
		return true;
	}

	function ensureDelete( url ) {
		var toDelete=window.confirm( "Are you sure to delete this user type? (Related sql tables of ralasafe will be deleted!!!" );
		if( toDelete ) {
			window.location=url;
		} 
	}
</script>

<jsp:include page="bar.jsp"/>


<div id="page" style="margin-top: 23px">

<div id="content">
<div id="latest-post" class="post">
<h1>Install/update user type definition</h1>
<% Random random = new Random(); %>
<div class="entry">
<form action="userTypeInstall?<%=random.nextDouble() %>" method="post" id="userTypeForm"
	ENCTYPE="multipart/form-data" onsubmit="return submitUserTypeForm()"><input type="hidden" name="op"
	value="add" />
<table width="400" border="0" cellpadding="5" cellspacing="0">
	<INPUT TYPE="hidden" NAME="name" value="ralasafe"/>
	<TR>
		<TD width="100">Description:</TD>
		<TD><INPUT TYPE="text" NAME="desc"/></TD>
	</TR>
	<TR>
		<TD width="100">File:</TD>
		<TD><INPUT TYPE="file" NAME="userDefineFile"/></TD>
	</TR>
	<TR>
		<TD colspan="2"><INPUT TYPE="submit" value="Submit"/>
			<INPUT TYPE="button" value="Return"
			onclick="javascript:window.location='userTypeMng'"/></TD>
	</TR>
</TABLE>
</form>
</div>


<h1>User type list</h1>
<div class="entry">
<table width="700" border="0" cellpadding="5" cellspacing="1"
	bgcolor="#CCCCCC" id="b_g_date">
	<thead>
	<tr bgcolor="#F4F4F4">
		<!--th>Name</th-->
		<th>Description</th>
		<th>Action</th>
	</tr>
	</thead>
	<tbody>
	<%
		for (Iterator iter = userTypes.iterator(); iter.hasNext();) {
			UserType userType = (UserType) iter.next();
	%>
	<tr bgcolor="#F4F4F4">
		<!--td><%=userType.getName()%></td-->
		<td><%=userType.getDesc()%></td>
		<td><a href="userTypeMng?op=view&name=<%=userType.getName()%>&<%=random.nextDouble() %>">Detail</a>
		<a href="javascript:ensureDelete('userTypeMng?op=delete&name=<%=userType.getName()%>&<%=random.nextDouble() %>');">Delete</a></td>
	</tr>
	<%
		}
	%>
	</tbody>
</table>
</div>
<jsp:include page="foot.jsp"/>
</div>
</div>
</div>



</body>
</html>
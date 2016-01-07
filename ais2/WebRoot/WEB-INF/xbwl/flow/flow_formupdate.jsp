<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowFormupdate.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript">
			var formId="${formId}";
			var objType="${objType}";
			var tableName="${tableName}";
		</script>
	</head>
	<body>
		<div id="showView"></div>
	</body>
</html>

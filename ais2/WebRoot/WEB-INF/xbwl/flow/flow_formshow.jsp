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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowFormShow.js?_t=<%=Math.random() %>"></script>
	</head>

	<body>
		<script type="text/javascript">
			var pipeId="${pipeId}";
			var workflowId="${workflowId}";
			var oprType="${oprType}";
			var nodeId="${nodeId}";
			var operateType="${operateType}";
		</script>
		<div id="showView"></div>
	</body>
</html>

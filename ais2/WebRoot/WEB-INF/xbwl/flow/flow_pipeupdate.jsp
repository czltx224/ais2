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
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowNodeinfo.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowRalarule.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowPipeupdate.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript">
			var pipeId="${flowId}";//流程编号
			var formId="${formId}";//表单ID
			var formName="${formName}";//表单名称
			var dicObjType="${dicObjType}";//对象类型
			var pipeName="${flowName}";//流程名称
		</script>
	</head>
	<body>
		<div id="showView"></div>
	</body>
</html>

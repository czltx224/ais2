<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
			 .x-grid-back-red { 
		        background: #FF0000; 
		     }
  		</style>
		<base href="<%=basePath%>">
		
		
		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/myux/gridsummary/Ext.ux.grid.GridSummary.js"></script> 
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fi/fiDeliverycostHandMatch.js?_t=<%=Math.random() %>"></script>
	</head>

	<body>
		
	</body>
</html>

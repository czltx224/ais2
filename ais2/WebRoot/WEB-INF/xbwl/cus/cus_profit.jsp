<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/fileuploadfield/css/fileuploadfield.css"/> 
		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/FusionCharts.js?"/>
  			</script>
  			<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxmedia.js"></script>
		 	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxflash.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxchart.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxfusion.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/reports/util/countRange.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/cus/cusProfit.js?_t=<%=Math.random() %>"></script>
	</head>

	<body>
		<div id="showView"></div>
	</body>
</html>

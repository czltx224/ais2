<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
   <jsp:include page="/common/common.jsp" />
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/view/reports/count/oprKpiReport.js?_t=<%=Math.random() %>"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/FusionCharts.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxmedia.js"></script>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxflash.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxchart.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxfusion.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mycss/backGround.css" />
  </head>
  
  <body>
  	<div id='northDiv'></div>
  	<div id='southDiv' style="width:100%;height:50%"></div>
  </body>
</html>

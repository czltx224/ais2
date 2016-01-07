<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
  <base href="<%=basePath%>">
   <jsp:include page="/common/common.jsp" />
   <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/ColumnHeaderGroup.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/FusionCharts.js?"/>
  			</script>
  			<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxmedia.js"></script>
		 	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxflash.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxchart.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxfusion.js"></script>
		  	<!--  
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/reports/cus/cusTarget.js"></script>
		-->
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/view/reports/cus/daySaleMsg.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/reports/util/countRange.js"></script>
	<style type="text/css">
		td.ux-grid-hd-group-cell {
   			background: url(extjs3.1/resources/images/default/grid/grid3-hrow.gif) repeat-x bottom;
		}
	</style>
  </head>
  
  <body>
  <div id="showView"></div>
  </body>
</html>

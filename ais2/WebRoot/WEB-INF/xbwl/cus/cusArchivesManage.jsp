<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.xbwl.entity.CusRecord"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>部门管理</title>
      <jsp:include page="/common/common.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/FusionCharts.js?"/>
  			</script>
  			<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxmedia.js"></script>
		 	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxflash.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxchart.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxfusion.js"></script>
		 	 <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/DateTimePicker.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/DateTimeField.js"></script>
		 	 <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/cus/MyViewport.js?_t=<%=Math.random() %>"></script>
		 	 <%CusRecord c=(CusRecord)request.getAttribute("cusRecord");%>
		  <script type="text/javascript">
				var cRId='${recordId}';
				var cusI='<%=c.getCusId()%>';
				var cusN='<%=c.getCusName()%>';
				var cusT='<%=c.getIsCq()%>';
				var cusDev='<%=c.getDevelopLevel()%>';
			</script>
		  </head>
  <body>
	<div id="my2DbChart_panel_div" ></div> 
  </body>

</html>


<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
    <jsp:include page="/common/common.jsp" />
   
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/DateTimePicker.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/DateTimeField.js">
	</script>
   
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/fi/fiCarCostManage.js?_t=<%=Math.random() %>">
    
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/myux/datatimefield/DateTimeField.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/myux/datatimefield/SpinnerField.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/myux/datatimefield/Spinner.js"></script>
  </head>
  
  <body>
   <div id='mainGrid'></div>
  </body>
</html>

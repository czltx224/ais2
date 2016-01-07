<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>配送管理系统</title>
    <jsp:include page="/common/common.jsp" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/instock/carArrive.js?_t=<%=Math.random() %>"></script>
  	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/css/DateTimePicker.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/DateTimeField.js"></script>
  <style type="text/css">
  	.x-liuh{
		background:red;
		border-color:#FF0000;
	}
  </style>
  </head>
  <body>
  <div id="showView">
  </div>
  </body>
</html>

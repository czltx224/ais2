<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fax/oprSign.js?_t=<%=Math.random() %>"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mycss/backGround.css" />
  </head>
  <body>
  <div id="showView"></div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
   <jsp:include page="/common/common.jsp" />
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/view/reports/takeGoods.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/reports/util/countRange.js?_t=<%=Math.random() %>"></script>
  </head>
  
  <body>
  <div id="showView"></div>
  </body>
</html>

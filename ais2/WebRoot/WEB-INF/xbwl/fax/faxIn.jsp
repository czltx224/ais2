<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  		<jsp:include page="/common/common.jsp" />
  		<script type="text/javascript" src="${pageContext.request.contextPath}/myux/gridsummary/Ext.ux.grid.GridSummary.js"></script>
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/view/fax/oprFaxInn.js?_t=<%=Math.random() %>"></script>
  </head>
  
  <body>
  	<div id="showView"></div>
  </body>
</html>
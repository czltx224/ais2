<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
   		<jsp:include page="/common/common.jsp" />
   		<script type="text/javascript" src="${pageContext.request.contextPath}/myux/gridsummary/Ext.ux.grid.GridSummary.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/ProgressBarPager.js"></script> 
		<script type="text/javascript" 
			src="${pageContext.request.contextPath}/js/view/reports/transit/oprEdiAgingQuery.js?_t=<%=Math.random() %>"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mycss/backGround.css" />
		<script type="text/javascript">
			var c_gowhereId='${gowhereId}';
			var c_startDate='${startDate}';
			var c_endDate='${endDate}';
			var c_curBussDepart='${curBussDepart}';
			var c_countCheckItems='${countCheckItems}';
		</script>
  </head>
  
  <body>
  </body>
</html>

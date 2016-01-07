<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/mycss/inquery.css" />
		<jsp:include page="/common/common.jsp" />
		<!-- 
			<script type="text/javascript" src="${pageContext.request.contextPath}/myux/gridsummary/Ext.ux.grid.GridSummary.js"></script> 
			<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/ProgressBarPager.js"></script> 
		-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/RowExpander.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/stock/comprehensiveInquiry.js?_t=<%=Math.random() %>"></script>
	</head>
	
		
	<script type="text/javascript">
		var pub_flightMainNo="${pub_flightMainNo}";
	</script>
	<!-- 
	<style>
		*{font-size:12px;font-weight:bold;}
	</style>
	 -->
<style>
	
	*{font-size:12px;}
	
	#grid{
		 font-family:"宋体"; 
		 font-size: 12px;
	}
	
	.x-grid3-cell-inner{
		font-family:"宋体";
		white-space:normal;
		overflow:visible;
	}
</style>

	<body>
	</body>
</html>

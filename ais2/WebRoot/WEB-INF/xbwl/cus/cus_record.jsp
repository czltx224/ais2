<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<jsp:include page="/common/common.jsp"></jsp:include>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/cus/cusRecord.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript">
			var cusRecordId="${recordId}";
			var consigName="${consigName}";
			var consigTel="${consigTel}";
			var mainType="${mainType}";
		</script>
	</head>

	<body>
		<div id="showView"></div>
	</body>
</html>

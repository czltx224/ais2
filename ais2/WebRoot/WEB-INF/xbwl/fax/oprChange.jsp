<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fax/oprFaxChange.js?_t=<%=Math.random() %>"></script>
		<script type="text/javascript">
			var changeDno='${dno}';
		</script>
	<style type="text/css">
	</style>
  </head>
  
  <body>
  	<div id="showView"></div>
  </body>
</html>

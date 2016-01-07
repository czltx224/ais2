<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  		<jsp:include page="/common/common.jsp" />
  		
		<script type="text/javascript">
			var dno='${dno}';
			var rightDepart='${rightDepart}';
 	  	</script>  		
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fax/oprFaxInInfo.js?_t=<%=Math.random() %>">
			</script>
  </head>
  
  <body>
  	<div id="showView"></div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
   <jsp:include page="/common/common.jsp" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fi/basTraShipmentRate.js?_t=<%=Math.random() %>"></script>
  </head>
  
 <script type="text/javascript">
	basTraShipmentRateExcleUrl="${pageContext.request.contextPath}/excel_template/basTraShipmentRate_excel.xlsx";
 </script>
  <body>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
   <jsp:include page="/common/common.jsp" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/js/view/fi/basCqCorporateRate.js?_t=<%=Math.random() %>"></script>
  </head>
  <script type="text/javascript">
	cqCorporateRateExcelUrl="${pageContext.request.contextPath}/excel_template/cqCorporateRate_excel.xlsx";
	
 </script>
  
  <body>
  </body>
</html>

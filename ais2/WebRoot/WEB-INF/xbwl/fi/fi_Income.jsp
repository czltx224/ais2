<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>预付款结算清单</title>
    <jsp:include page="/common/common.jsp" />
    		<script type="text/javascript" src="${pageContext.request.contextPath}/myux/gridsummary/Ext.ux.grid.GridSummary.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/fi/fiIncome.js?_t=<%=Math.random() %>">
    </script>

  </head>
  
  <body>
   <div id='mainGrid'></div>
  </body>
</html>

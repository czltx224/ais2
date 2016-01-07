<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>配送部门</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style/examples.css" />

	<style type="text/css">
	    .complete .x-tree-node-anchor span {
	        font-weight:800;
	    }
	</style>
    <jsp:include page="/common/common.jsp" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/examples.js"></script><!-- EXAMPLES -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/sys/sysCommonMenu.js?_t=<%=Math.random() %>">
    </script>

  </head>
  
  <body>
   <div id='menuGrid'></div>
  </body>
</html>

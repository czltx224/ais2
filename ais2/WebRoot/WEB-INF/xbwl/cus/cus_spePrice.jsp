<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>应收应付管理</title>
    <jsp:include page="/common/common.jsp" />
    <style type="text/css">
		.x-fieldset{
			border:1px solid #B5B8C8;
			padding:0px 5px 0px 5px;
			margin-bottom:0px;
			display:block;
			}
			
		.x-fieldset-s{
			border:0px solid #B5B8C8;
			padding:5px 0px 0px 0px;
			margin-bottom:0px;
			display:block;
			}			
        .x-check-group-alt {
        	background: #D1DDEF;
        	border-top:1px dotted #B5B8C8;
        	border-bottom:1px dotted #B5B8C8;
        	}
  	</style>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/cus/cusSpePrice.js?_t=<%=Math.random() %>">
    </script>

  </head>
  
  <body>
   <div id='showView'></div>
  </body>
</html>

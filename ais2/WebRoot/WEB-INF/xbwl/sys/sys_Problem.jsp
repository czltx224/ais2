<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>配送管理系统</title>
    <jsp:include page="/common/common.jsp" />
    <link href="${pageContext.request.contextPath}/style/startcss.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		Ext.apply(Ext.form.VTypes,{
			// 验证方法
			imgVtype : function(val, field) {
			    try{
					if(/^.+\.(GIF|JPG|JPEG|PNG|BMP)$/.test(val.toUpperCase())){
						return true;
					}else{
						return false;
					}
			    }catch(e){
					return false;
				}

				return true;
			},
			// 验证提示信息
			imgVtypeText : '输入图片格式不正确，图片格式只能为(gif|jpg|jpeg|png|bmp)!'
		});
 	</script>
 	<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/RowExpander.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/view/sys/sysProblem.js?_t=<%=Math.random() %>"></script>
 
  </head>
  <script type="text/javascript">

  </script>
  
  <body>
  	<div id='show'></div>
  </body>
</html>

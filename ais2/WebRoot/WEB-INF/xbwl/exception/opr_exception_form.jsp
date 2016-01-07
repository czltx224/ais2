<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extjs3.1/ux/fileuploadfield/css/fileuploadfield.css"/> 
		<jsp:include page="/common/common.jsp" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/extjs3.1/ux/fileuploadfield/FileUploadField.js?_t=<%=Math.random()%>"></script>
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
		
			var stationIds='${qualityManger}';
			var formId = '${id}';
			var formDno='${dno}';
			var exceptionImagesUrl='${exceptionImagesUrl}';
 	  	</script>
		
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/exception/oprExceptionForm.js?_t=<%=Math.random() %>"></script>
	</head>

	<body>
		
	</body>
</html>

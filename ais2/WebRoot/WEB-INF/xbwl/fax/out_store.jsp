<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<jsp:include page="/common/common.jsp" />
	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/fax/oprOutStore.js?_t=<%=Math.random() %>"></script>
	 
	</head>
		
	<body>
		<div id='idrDiv'  style="height:1px;display:none">
			<OBJECT ID="IDRCoreX1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:622AB367-970D-4CBB-A25C-7EB07D38F627"
					CODEBASE="IDRCoreX">
				<embed width="0" height="0"></embed>
			</OBJECT>
		</div>
		
	</body>
	<!-- 
		<script type="text/javascript">
            var a=document.getElementById("IDRCoreX1");
            if(a!=null){
            	alert("a不为Null");
             	document.getElementById("idrDiv").style.display="none";//隐藏
            }else{
              	alert("a为Null");
				document.getElementById("idrDiv").style.display="block";//显示
            }
    	</script>
	 -->
</html>



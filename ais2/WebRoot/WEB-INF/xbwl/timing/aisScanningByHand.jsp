<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>手工执行定时任务</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <form action="timing/aisScanningByHandAction!doScanningByHand.action" method="post">
   	 <c:if test="${errormsg != null && not empty errormsg}">
			<p class="formt"><font color="red">${errormsg }</font></p>
	 </c:if>
   	要执行的任务:<select name="scanningName" >  
   				<option value="ctEstimateScanning">1.AISUSER.ct_estimate表定时扫描到EDI</option>
				<option value="ctTmdScanning">2.AISUSER.ct_tm_d表定时扫描到EDI</option>
				<option value="ctFaxOutScanning">3.定时任务扫描OPR_FAX_OUT写入EDI主表</option>
				<option value="sysQianshouScanning">4.短信签收定时扫描任务</option>
				<option value="scanningShenZhouShuMa">5.神州数码扫描任务</option>
				<option value="2222">11.错误路径</option>
   		   </select>
   	<input type="submit" value="执行"></input><br>
   </form>
  </body>
</html>

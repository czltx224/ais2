<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>流程图显示</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/sack.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/xmlutil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/showchart.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/graphlib.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/graph.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/corner.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/node.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/edge.js"></script>
	<link href="${pageContext.request.contextPath}/commonjs/flowutil/css/showflowstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/commonjs/flowutil/css/skin.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/commonjs/flowutil/css/velcro.css" rel="stylesheet" type="text/css" />
	<jsp:include page="/common/common.jsp" />
	<script type="text/javascript">
		var workflowId="${workflowId}";
		var pipeId="${pipeId}";
		var nodeId="${nodeId}";
	</script>
  </head>
  
  <body onload = "showChart();">
   <div id="show"
	style="width: 170px; float: left; border: 1px; height: 50px">
	<img src="${pageContext.request.contextPath}/imags/flow/showNode_blue.jpg" width="45" height="39" />
	&nbsp;
	<img src="${pageContext.request.contextPath}/imags/flow/showNode_green.jpg" width="48" height="38" />
	&nbsp;
	<img src="${pageContext.request.contextPath}/imags/flow/showNode_red.jpg" width="45" height="39" />
	&nbsp;
	<div id="rrrrr3" style="width: 55px; float: left; font-size: 10px">
		未操作节点
	</div>
	<div id="rrrrr1" style="width: 55px; float: left; font-size: 10px">
		已操作节点
	</div>
	<div id="rrrrr2" style="width: 60px; float: left; font-size: 10px">
		当前节点
	</div>
	</div>
	<div id="22" style="float: left; width: 70px; height: 50px">
	<div id="4"
		style="width: 70px; height: 15px; float: left; font-size: 10px">
		<img src="${pageContext.request.contextPath}/imags/flow/users_red.gif" width="10" height="16" />
		未操作者
	</div>
	<div id="5"
		style="width: 70px; height: 15px; float: left; font-size: 10px">
		<img src="${pageContext.request.contextPath}/imags/flow/users_green.gif" width="10" height="16" />
		已操作者
	</div>
	<div id="6"
		style="width: 70px; height: 15px; float: left; font-size: 10px">
		<img src="${pageContext.request.contextPath}/imags/flow/users_blue.gif" width="11" height="16" />
		已查看者
	</div>
</div>
<div id="nodesDiv"></div>
<div id="ruleDiv"></div>
<div id="flowLayoutLoadding"><img src="${pageContext.request.contextPath}/commonjs/flowutil/img/loadding.gif" border="0" align="absmiddle"> 正在加载流转信息...</div>
<input type="hidden" name="flowChartXML" id="flowChartXML" value='${flowChartXML}'>
<script type="text/javascript">
var nodesDiv = $("nodesDiv");
</script>
</body>
</html>

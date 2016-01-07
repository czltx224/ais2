<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>My JSP 'workflowlayout.jsp' starting page</title>
		<!-- 
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/main.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/browser.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/language.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/dateutil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/calendar.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/rtxint.js"></script>
		 -->
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/jquery.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/sack.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/xmlutil.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/flowutil/eventutil.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/showchart.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/graphlib.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/graph.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/corner.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/node.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/commonjs/flowutil/edge.js"></script>
		<SCRIPT
			src="${pageContext.request.contextPath}/commonjs/flowutil/layout.js"
			type=text/javascript></SCRIPT>
		<SCRIPT
			src="${pageContext.request.contextPath}/commonjs/flowutil/dragdrop.js"
			type=text/javascript></SCRIPT>
		<link
			href="${pageContext.request.contextPath}/commonjs/flowutil/css/flowstyle.css"
			rel="stylesheet" type="text/css" />
		<link type="text/css" rel="stylesheet"
			href="${pageContext.request.contextPath}/commonjs/flowutil/css/zh.css" />
		<link type="text/css" rel="stylesheet"
			href="${pageContext.request.contextPath}/commonjs/flowutil/css/velcro.css"
			media="all" />
		<link id="css_skin" type="text/css" rel="stylesheet"
			href="${pageContext.request.contextPath}/commonjs/flowutil/css/skin.css" />

			<jsp:include page="/common/common.jsp" />
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowWorklayout.js?_t=<%=Math.random() %>"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowNodeinfo.js?_t=<%=Math.random() %>"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/view/flow/flowRalarule.js?_t=<%=Math.random() %>"></script>
	</head>

	<body onload="doit();">
		<div id="pagemenubar">
		</div>
		<input type="hidden" name="locale" id="locale" value="zh_CN" />
		<div id="flowLayoutLoadding" class="loadding"><img src="${pageContext.request.contextPath}/commonjs/flowutil/img/loadding.gif" border="0" align="absmiddle"> 数据加载中,请稍候...</div>
		<div id="flowLayoutSaving" class="loadding" style="display:none"><img src="${pageContext.request.contextPath}/commonjs/flowutil/img/loadding.gif" border="0" align="absmiddle"> 正在保存数据,请稍候...</div>
		<div id="flowLayoutSaved" class="loadding" style="display:none"><img src="${pageContext.request.contextPath}/commonjs/flowutil/img/info.gif" border="0" align="absmiddle"> 数据保存成功 ! </div>
		<DIV id='nodesDiv'></DIV>
		<DIV id='tempDiv'></DIV>
		<TEXTAREA id='xmlvalue' style="DISPLAY: none" name='xmlvalue' rows=20 cols=120></TEXTAREA>
		<SCRIPT type=text/javascript>
			var nodesDiv = $("nodesDiv");
			var tempDiv = $("tempDiv");
			var xmlvalue = $("xmlvalue");
			var pipeId="${flowId}";//流程ID
			var pipeName="${flowName}";//流程名称
			var formId="${formId}";//表单ID
		</SCRIPT>
	</body>
</html>

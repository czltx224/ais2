<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<HTML>
<HEAD>
<jsp:include page="/common/common.jsp" />
<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/FusionCharts.js?"/>
  			</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxmedia.js"></script>
		 	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxflash.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxchart.js"></script>
		  	<script type="text/javascript" src="${pageContext.request.contextPath}/commonjs/uxfusion.js"></script>
</head>
<body>
    <div id='showView'>FusionCharts</div>
    <script type="text/javascript" language="javascript">
   	  var strXml="<chart caption='Yearly Sales Comparison' XAxisName='Year' palette='1' showValues='1' sNumberSuffix='%' animation='1' formatNumberScale='0' numberPrefix='$' showValues='0' seriesNameInToolTip='0'><categories><category label='1994' /><category label='1995' /><category label='1996' /></categories><dataset seriesname='Revenue' color='50BD4A'><set value='219702' /><set value='682796' /><set value='547248' /></dataset><dataset seriesName='Units Sold' parentYAxis='S'><set value='85' /><set value='90' /><set value='60' /></dataset><styles><definition><style type='font' color='666666' name='CaptionFont' size='15' /><style type='font' name='SubCaptionFont' bold='0' /></definition><application><apply toObject='caption' styles='CaptionFont' /><apply toObject='SubCaption' styles='SubCaptionFont' /></application></styles></chart>";
      var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
						collapsible:false,
						chartCfg:{
							id:'chart1',
							params:{
								flashVars:{
									debugMode:0,
									lang:'EN'
								}
							}
						},
						autoScroll:true,
						id:'chartpanel',
						chartURL:'../chars/fcf/MSColumn3DLineDY.swf',
						dataXML:strXml,
						width:500,
						height:500
					});
		fusionPanel.render('showView');
    </script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
  <head>
  	<jsp:include page="/common/common.jsp" />
  </head>
  
  <body>
  </body>
  <script type="text/javascript">
		parent.putScan();
		mask2=new Ext.LoadMask(Ext.getBody(),{msg:'请在弹出的界面处理图片'});
		mask2.show();
	</script>
</html>

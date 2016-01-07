<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  
    <title>配送管理系统</title>
    <jsp:include page="/common/common.jsp" />
  </head>
  
  <body>
  	<div id='view-div'></div><br><br>
  </body>
  	<script type="text/javascript">
        var parent_id=1027;
        var parent_name="空港配送事业部";
        Ext.onReady(function() {
        var treePanel = new Ext.tree.TreePanel({
                    id:"showmenutree", 
		    	    split: true,
			        minSize: 150,
			        width:160,
			        region:"west",
			        autoScroll: true,
			        title:'岗位菜单',
			        //rootVisible: false,
			        lines: false,
			        useArrows: true,
       			    root: new Ext.tree.AsyncTreeNode({id:parent_id,text:parent_name}),
			        loader: new Ext.tree.TreeLoader({
			            dataUrl:sysPath+'/sys/stationAction!getStationTree.action'
			        }),
			        listeners : {
			    		'click':function(node){
			    		parent_id=node.id;
			    		parent_name=node.text;
			    		window.frames["stationiframe"].parentHref(); 
			    	},
			    	scope:this
			    }
		    });
            new Ext.Viewport({
                layout:"border",
                id:"view",
                items:[
                	treePanel,
               		{
                		id:'showcenter',
                        region:'center',
                        html:'<iframe id="stationiframe" name="stationiframe" class="iframe" src="${pageContext.request.contextPath}/sys/stationAction!findStationList.action" frameborder="0" style="width:100%; height:100%;" />'
                	}
                ]
            });
            treePanel.expand();
        });
        //重新加载系统菜单tree  
        function reloadFunctionTree() {
            treePanel.root.reload();
        } 
  </script>
</html>

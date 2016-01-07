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
        var tree;
        var node;
        var parent_id="0";
        var parent_name="中国";
        var treePanel;
        Ext.onReady(function() {
        treePanel = new Ext.tree.TreePanel({
                    id:"showmenutree", 
		    	    split: true,
			        minSize: 150,
			        width:160,
			        region:"west",
			        autoScroll: true,
			        title:'地区菜单',
			        rootVisible: false,
			        lines: false,
			        useArrows: true,
       			    root: new Ext.tree.AsyncTreeNode({id:parent_id,text:parent_name}),
			        loader: new Ext.tree.TreeLoader({
			            dataUrl:sysPath+'/sys/basAreaAction!getAreaTree.action'
			        }),
			        listeners : {
			    		'click':function(node){
			    		parent_id=node.id;
			    		parent_name=node.text;
			    		window.frames["areaiframe"].parentHref(); 
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
                        html:'<iframe id="areaiframe" name="areaiframe" class="iframe" src="${pageContext.request.contextPath}/sys/basAreaAction!findAreaList.action" frameborder="0" style="width:100%; height:100%;" />'
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

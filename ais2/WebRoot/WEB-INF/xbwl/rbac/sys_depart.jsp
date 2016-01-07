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
        var parent_id=0;
        var parent_name="配送中心";
        var parent_no="1";
        Ext.onReady(function() {
        var treePanel = new Ext.tree.TreePanel({
                    id:"showmenutree", 
		    	    split: true,
			        minSize: 150,
			        width:160,
			        region:"west",
			        autoScroll: true,
			        title:'部门菜单',
			        //rootVisible: false,
			        lines: false,
			        useArrows: true,
       			    root: new Ext.tree.AsyncTreeNode({id:"0",text:'配送中心'}),
			        loader: new Ext.tree.TreeLoader({
			            dataUrl:sysPath+'/sys/departAction!getDepartTree.action'
			        }),
			        listeners : {
			    		'click':function(node){
			    		parent_id=node.id;
			    		parent_name=node.text;
			    		Ext.Ajax.request({
							url : sysPath+ "/sys/departAction!findAll.action",
							params : {
								filter_EQL_departId : parent_id,
								limit:pageSize
							},
							success : function(resp) {
								var respText = Ext.util.JSON.decode(resp.responseText);
								parent_no=respText.result[0].departNo;
							}
						});
			    		window.frames["myiframe"].parentHref(); 
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
                        html:'<iframe id="iframe_id" name="myiframe" class="iframe" src="${pageContext.request.contextPath}/sys/departAction!toDepartList.action" frameborder="0"  style="width:100%; height:100%;"/>'
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

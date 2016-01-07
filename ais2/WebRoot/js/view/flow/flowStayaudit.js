
var privilege=257;
var baseprivilege=252;
var searchUrl="flow/workFlowstepAction!getstayauditFlow.action";//查询地址
var fields=[
			{name:'id',mapping:'ID'},
            {name: 'workflowId',mapping:'WORKFLOW_ID'},
            {name:'operateType',mapping:'OPERATE_TYPE'},
            {name:'stepId',mapping:'STEP_ID'},
            {name: 'nodeId',mapping:'NODE_ID'},
            {name:'isFinished',mapping:'IS_FINISHED'},
            {name: 'nodeType',mapping:'NODE_TYPE'},
            {name: 'receiverId',mapping:'RECEIVER_ID'},
            {name: 'receiveTime',mapping:'RECEIVER_TIME'},
            {name: 'submiterId',mapping:'SUBMITER_ID'},
            {name: 'submitTime',mapping:'SUBMIT_TIME'},
            {name: 'createName',mapping:'CREATE_NAME'},
            {name: 'createTime',mapping:'CREATE_TIME'},
            {name: 'updateName',mapping:'UPDATE_NAME'},
            {name: 'updateTime',mapping:'UPDATE_TIME'},
            {name:'ts',mapping:'ts'},
            {name:'workflowName',mapping:'WORKFLOW_NAME'}
];
Ext.onReady(function() {
    var stayauditStore = new Ext.data.Store({
        storeId:"stayauditStore",
        baseParams:{privilege:privilege,isAlert:'false',operType:1,limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
    stayauditStore.load();
    //start
    var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
    
    var menuGrid = new Ext.grid.GridPanel({
        renderTo:'showView',
        height:Ext.lib.Dom.getViewHeight(), 
        width:Ext.lib.Dom.getViewWidth(),
        viewConfig : {
    		scrollOffset: 0,
			autoScroll:true
		},
		stripeRows : true,
        frame:true, 
        loadMask:true,
        sm:sm,
        cm:new Ext.grid.ColumnModel([rowNum,sm,
        	{header:'流程号',dataIndex:"workflowId"},
        	{header:'流程名称',dataIndex:"workflowName",width:120},
        	{header:'节点ID',dataIndex:"nodeId",hidden:true},
            {header:'流程状态',dataIndex:"isFinished",renderer:function(v){
            	if(v == 1){
            		return "正常";
            	}else if(v ==2){
            		return "归档";
            	}else{
            		return "否决";
            	}
            }},
            {header:'当前操作类型',dataIndex:'operateType',renderer:function(v){
            	if(v == 1){
            		return "审批";
            	}else if(v ==2){
            		return "知会";
            	}else if(v ==3){
            		return "审核";
            	}else if(v==4){
            		return "批注";
            	}
            }},
            {header:'上节点操作人',dataIndex:"createName",width:80},
            {header:'操作时间',dataIndex:"createTime",width:120,sortable:true},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:stayauditStore,
        tbar: [
            {
                text:'<B>流程处理</B>', id:'showForm',tooltip:'审批/查看流程信息',disabled:true,handler:function() {
                	var _record = menuGrid.getSelectionModel().getSelections();
	                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						var workId = _record[0].data.workflowId;
						var nodeId=_record[0].data.nodeId;
						var operateType = _record[0].data.operateType;
						var oprType="";
						var textNode="";
						if(operateType == 1 || operateType ==4 ){
							oprType = "dispose";
							textNode="流程处理";
						}else if(operateType == 2){
							oprType = "see";
							textNode="流程查看";
						}else if(operateType == 3){
							oprType = "audit";
							textNode="流程审核";
						}
						var pipeId;
						Ext.Ajax.request({
							url:sysPath+'/flow/workFlowbaseAction!ralaList.action',
							params:{
								privilege:baseprivilege,
								filter_EQL_id:workId
							},
							success:function(resp){
								var respText = Ext.util.JSON.decode(resp.responseText);
								pipeId=respText.result[0].pipeId;
								var node=new Ext.tree.TreeNode({id:'flowauditList',leaf :false,text:textNode});
						      	node.attributes={href1:"/flow/flowForminfoAction!gotoFormShow.action?nodeId="+nodeId+"&workflowId="+workId+"&pipeId="+pipeId+"&oprType="+oprType+"&operateType="+operateType};
						        parent.toAddTabPage(node,true);
							}
						});
						
					}
            } },
		    '-',
		    {
		    	text:'<b>查看流程图</b>',
		    	id : 'flowshowchar',
				tooltip : '查看当前流程流程图',
				disabled:true,
				handler:function(){
					var _record = menuGrid.getSelectionModel().getSelections();
					showworklayout(_record);
				}
		    }
            ,'-','创建时间:',
			 {
				xtype : 'datefield',
				format:'Y-m-d',
				value:new Date().add(Date.DAY, -30),
				id : 'datefieldstart',
				name : 'startTime',
				width : 100
			} ,'-', '&nbsp;', {
				xtype : 'datefield',
				id:'datefieldend',
				format:'Y-m-d',
				value:new Date(),
				name : 'endTime',
				width : 100
			},
            '-','审批类型',{
				xtype:'combo',
				id:'operType',
				name:'operType',
				value:'1',
				triggerAction : 'all',
    			model : 'local',
				store:[
					['','全部'],
					['1','审批'],
					['2','知会'],
					['4','批注'],
					['3','审核']
				],
				width:50
            },
            '-',
            {
            	text:'<B>搜索</B>',
            	id:'btn', 
            	iconCls: 'btnSearch',
            	hidden:false,
            	handler : searchForminfo
            }
        ],
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:privilege, 
            store: stayauditStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
     function searchForminfo() {
//     	var start=Ext.get("datefieldstart").dom.value;
//    	var end=Ext.get("datefieldend").dom.value;
//
//		Ext.apply(stayauditStore.baseParams, {
//			filter_GED_createTime : start,
//			filter_LED_createTime : end,
//			privilege:privilege,
//			filter_EQL_isDelete:1
//		});
		menuStoreReload();
		
	}
     //查询
     
   menuGrid.render();
   /*
   menuStore.load({
   params:{
   	limit:pageSize,
   	filter_EQL_id:0
   	}
   });*/
    menuGrid.on('click', function() {
        var _record = menuGrid.getSelectionModel().getSelections();
        var updatebtn = Ext.getCmp('showForm');
        var showbtn = Ext.getCmp('flowshowchar');
        if (_record.length==1) {       	
            if(updatebtn){
            	updatebtn.setDisabled(false);
            }
            if(showbtn){
           	 	showbtn.setDisabled(false);
            }
        } else {
            if(updatebtn){
            	updatebtn.setDisabled(true);
            }
            if(showbtn){
           	 	showbtn.setDisabled(true);
            }
        }
    });
	function menuStoreReload(){
		var operType = Ext.getCmp('operType').getValue();
		Ext.apply(stayauditStore.baseParams,{
			isAlert:'false',
			operType:operType
		});
		stayauditStore.reload({
			params:{
				start : 0,
				privilege:privilege,
				limit : pageSize
			}
		});
	}
    //end
});

function showworklayout(_record){
	var pipeId;
	var workflowId=_record[0].data.workflowId;
	var nodeId=_record[0].data.nodeId;
	Ext.Ajax.request({
		url:sysPath+'/flow/workFlowbaseAction!ralaList.action',
		params:{
			filter_EQL_id:workflowId,
			privilege:baseprivilege
		},
		success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			pipeId =respText.result[0].pipeId;
			var node=new Ext.tree.TreeNode({id:'stayshowwork',leaf :false,text:'流程图查看'});
  			node.attributes={href1:"/flow/workFlowbaseAction!charShow.action?workflowId="+workflowId+"&pipeId="+pipeId+"&nodeId="+nodeId};
   		 	parent.toAddTabPage(node,true);
		}
	});
}

var privilege=254;
var baseprivilege=252;
var searchUrl="flow/workFlowstepAction!yetAudit.action";//查询地址
var fields=[
			{name:'id',mapping:'ID'},
            {name: 'workflowId',mapping:'WORKFLOW_ID'},
            {name: 'nodeId',mapping:'NODE_ID'},
            {name:'nodeName',mapping:'NODE_NAME'},
            {name:'isFinished',mapping:'IS_FINISHED'},
            {name: 'nodeType',mapping:'NODE_TYPE'},
            {name: 'receiverId',mapping:'RECEIVER_ID'},
            {name: 'receiveTime',mapping:'RECEIVE_TIME'},
            {name: 'submiterId',mapping:'SUBMITER_ID'},
            {name: 'submitTime',mapping:'SUBMIT_TIME'},
            {name: 'createName',mapping:'CREATE_NAME'},
            {name: 'createTime',mapping:'CREATE_TIME'},
            {name: 'updateName',mapping:'UPDATE_NAME'},
            {name: 'updateTime',mapping:'UPDATE_TIME'},
            {name:'ts'},
            {name:'workflowName',mapping:'WORKFLOW_NAME'}
];
Ext.onReady(function() {
    var alreadauditStore = new Ext.data.Store({
        storeId:"alreadauditStore",
        baseParams:{limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, fields)
    });
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
        	{header:'流程名称',dataIndex:"workflowName"},
        	{header:'流程状态',dataIndex:'isFinished',renderer:function(v){
        		if(v ==1){
        			return "正常";
        		}else if(v ==2){
        			return "归档";
        		}else if(v ==3){
        			return "否决";
        		}
        	}},
        	{header:'节点ID',dataIndex:"nodeId",hidden:true},
        	{header:'审批节点',dataIndex:"nodeName"},
        	{header:'节点类型',dataIndex:"nodeType"},
            {header:'创建人',dataIndex:"createName",width:80},
            {header:'创建时间',dataIndex:"createTime",width:120},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:alreadauditStore,
        tbar: [
            {
                text:'<B>流程查看</B>', id:'showForm',tooltip:'查看流程信息',disabled:true,handler:function() {
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
								var node=new Ext.tree.TreeNode({id:'showAlreadyFlow',leaf :false,text:'流程处理'});
						      	node.attributes={href1:"/flow/flowForminfoAction!gotoFormShow.action?workflowId="+workId+"&pipeId="+pipeId+"&oprType=see"};
						        parent.toAddTabPage(node,true);
							}
						});
					}
            } },
		    '-',
		    {
		    	text:'<b>查看流程图</b>',
		    	disabled:true,
		    	id : 'flowshowchar',
				tooltip : '查看当前流程流程图',
				handler:function(){
					var _record = menuGrid.getSelectionModel().getSelections();
					showworklayout(_record);
				}
		    }
            ,'-','创建时间:',
			 {
				xtype : 'datefield',
				format:'Y-m-d',
				value:new Date().add(Date.DAY, -7),
				id : 'datefieldstart',
				name : 'startTime',
				width : 90
			} ,'-', '&nbsp;', {
				xtype : 'datefield',
				id:'datefieldend',
				format:'Y-m-d',
				value:new Date(),
				name : 'endTime',
				width : 90
			},'-','&nbsp;&nbsp;',
            {
            	xtype : 'textfield',
    			id :'searchContent',
    			name : 'areaName',
    			width : 80,
    			enableKeyEvents:true,
    			listeners : {
			 		keyup:function(textField, e){
		                     if(e.getKey() == 13){
		                     	searchArea();
		                  }
			 		}
	 			}
            },
            '-','&nbsp;&nbsp;',
            {
            	xtype : 'combo',
            	triggerAction : 'all',
    			model : 'local',
    			id : 'checkItems',
    			//hiddenName : 'checkItems',
    			name:'checkItemstext',
    			editable:false,
            	store: [
            			['', '查询全部'], 
    					['EQL_workflowId', '流程号'],
    					['LIKES_workflowName','流程名称']
    				   ],
    			width : 100
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
            store: alreadauditStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
     function searchForminfo() {
     	var start=Ext.get("datefieldstart").dom.value;
    	var end=Ext.get("datefieldend").dom.value;
		var checkItems = Ext.getCmp("checkItems").getValue();
		var itemsValue = Ext.getCmp("searchContent").getValue();
		alreadauditStore.baseParams={};
		if(checkItems=='LIKES_workflowName'){
			Ext.apply(alreadauditStore.baseParams, {
				startTime : start,
				endTime : end,
				workflowName:itemsValue
			});
		}else if(checkItems =='EQL_workflowId'){
			Ext.apply(alreadauditStore.baseParams, {
				startTime : start,
				endTime : end,
				workflowId:itemsValue
			});
		}else{
			Ext.apply(alreadauditStore.baseParams, {
				startTime : start,
				endTime : end
			});
		}
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
		alreadauditStore.reload({
			params:{
				start : 0,
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
			var node=new Ext.tree.TreeNode({id:'alreadyshowwork',leaf :false,text:'流程图查看'});
  			node.attributes={href1:"/flow/workFlowbaseAction!charShow.action?workflowId="+workflowId+"&pipeId="+pipeId+"&nodeId="+nodeId};
   		 	parent.toAddTabPage(node,true);
		}
	});
}
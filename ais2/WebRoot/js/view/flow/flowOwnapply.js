
var privilege=252;
var searchUrl="flow/workFlowbaseAction!ralaList.action";//查询地址
var fields=[
			{name:'id'},
            {name: 'formId'},
            {name:'pipeId'},
            {name: 'workflowName'},
            {name: 'workflowLevel'},
            {name: 'createType'},
            {name: 'createrId'},
            {name: 'isFinished'},
            {name: 'curnodeids'},
            {name: 'nodeName'},
            {name: 'createName'},
            {name: 'createTime'},
            {name: 'updateName'},
            {name: 'updateTime'},
            {name:'ts'}
];
Ext.onReady(function() {
    var ownapplyStore = new Ext.data.Store({
        storeId:"ownapplyStore",
        baseParams:{privilege:privilege,filter_EQL_isDelete:1,filter_EQL_createrId:userId,limit:pageSize},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/"+searchUrl}),
        reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
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
        	{header:'流程号',dataIndex:"id"},
        	{header:'管道ID',dataIndex:"pipeId",hidden:true},
        	{header:'表单ID',dataIndex:"formId",hidden:true},
            {header:'流程名称',dataIndex:"workflowName"},
            {header:'紧急程度',dataIndex:"workflowLevel",renderer:function(v){
            	if(v == 2){
            		return "紧急";
            	}else if(v ==1){
            		return "一般";
            	}
            }},
            {header:'创建类型',dataIndex:"createType",renderer:function(v){
            	if(v == 1){
            		return "自动创建";
            	}else if(v ==2){
            		return "手动创建";
            	}
            }},
            {header:'流程状态',dataIndex:"isFinished",renderer:function(v){
            	if(v == 1){
            		return "正常";
            	}else if(v ==2){
            		return "归档";
            	}else{
            		return "否决";
            	}
            }},
            {header:'创建人',dataIndex:"createName",width:80},
            {header:'创建时间',dataIndex:"createTime",width:120},
            {header:"更新人",dataIndex:"updateName",width:50,hidden:true},
            {header:"更新时间",dataIndex:"updateTime",width:50,hidden:true}
           
        ]),
        store:ownapplyStore,
        tbar: [
            {
                text:'<B>查看流程</B>', id:'showForm',tooltip:'查看流程信息',disabled:true,handler:function() {
                	var _record = menuGrid.getSelectionModel().getSelections();
	                if (_record.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					} else if (_record.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}else{
						var pipeId = _record[0].data.pipeId;
						var workflowId=_record[0].data.id;
						var node=new Ext.tree.TreeNode({id:'showOwnapplyflow',leaf :false,text:'流程查看'});
				      	node.attributes={href1:"/flow/flowForminfoAction!gotoFormShow.action?workflowId="+workflowId+"&pipeId="+pipeId+"&oprType=see"};
				        parent.toAddTabPage(node,true);
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
				value:new Date().add(Date.DAY, -7),
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
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			editable:false,
    			name : 'checkItemstext',
            	store: [
            			['', '查询全部'], 
    					['EQL_id', '流程号'],
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
            store: ownapplyStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        })
    });
     function searchForminfo() {
     	var start=Ext.get("datefieldstart").dom.value;
    	var end=Ext.get("datefieldend").dom.value;

		Ext.apply(ownapplyStore.baseParams, {
			checkItems : Ext.get("checkItems").dom.value,
			itemsValue : Ext.get("searchContent").dom.value,
			filter_GED_createTime : start,
			filter_LED_createTime : end,
			privilege:privilege,
			filter_EQL_isDelete:1
		});
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
		ownapplyStore.reload({
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
	var pipeId=_record[0].data.pipeId;;
	var workflowId=_record[0].data.id;
	var nodeId=_record[0].data.nodeId;
	var node=new Ext.tree.TreeNode({id:'ownshowwork',leaf :false,text:'流程图查看'});
  	node.attributes={href1:"/flow/workFlowbaseAction!charShow.action?workflowId="+workflowId+"&pipeId="+pipeId};
   	parent.toAddTabPage(node,true);
}
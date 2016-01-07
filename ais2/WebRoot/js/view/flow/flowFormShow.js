//var pipeId=1050;
var flowlogprivilege=253;
var mainFormPanel,cusServiceStore,detailFormPanel,mainFormStore,detailFormStore,mainHeadStore,detailHeadStore,panel,flowLogPanel,flowlogStore,auditPanel,toolbar;

var mainHeadField=[
	{name:'ID'},
	{name:'OBJ_TABLENAME'},
	{name:'FIELD_NAME'},
	{name:'LABEL_NAME'}
	];
var workflowlogField=[
	{name:'id',mapping:'ID'},
	{name:'workflowId',mapping:'WORKFLOW_ID'},
	{name:'nodeinfoId',mapping:'NODEINFO_ID'},
	{name:'returnNodeid',mapping:'RETURN_NODEID'},
	{name:'logType',mapping:'LOG_TYPE'},
	{name:'remark',mapping:'REMARK'},
	{name:'createTime',mapping:'CREATE_TIME'},
	{name:'createName',mapping:'CREATE_NAME'},
	{name:'updateTime',mapping:'UPDATE_TIME'},
	{name:'updateName',mapping:'UPDATE_NAME'},
	{name:'ts',mapping:'TS'},
	{name:'nodeName',mapping:'NODE_NAME'},
	{name:'returnNodeName',mapping:'RETURN_NODE_NAME'}
];
Ext.onReady(function() {
	if(oprType == 'dispose' || operateType== 2 ||operateType==3){
		Ext.Ajax.request({
			url:sysPath+'/flow/workFlowbaseAction!flowSubmit.action',
			params:{
				nodeId:nodeId,
				pipeId:pipeId,
				workflowId:workflowId,
				oprType:'see'
			},
			success:function(resp){
				
			}
		});
	}
	var sm = new Ext.grid.CheckboxSelectionModel({});
	var rowNum = new Ext.grid.RowNumberer({
	    header:'序号', width:35, sortable:true
	});
	mainHeadStore=new Ext.data.Store({
        storeId:"mainHeadStore",
        baseParams:{pipeId:pipeId,tableType:'main'},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowForminfoAction!getFormhead.action"}),
        reader: new Ext.data.JsonReader({
            //root: 'resultMap', totalProperty: 'totalCount'
        }, mainHeadField)
    });
    mainHeadStore.load();
    

	detailHeadStore=new Ext.data.Store({
        storeId:"detailHeadStore",
        baseParams:{pipeId:pipeId,tableType:'detail'},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowForminfoAction!getFormhead.action"}),
        reader: new Ext.data.JsonReader({
           // root: 'resultMap', totalProperty: 'totalCount'
        }, mainHeadField)
    });
    detailHeadStore.load();
    
    flowlogStore=new Ext.data.Store({
        storeId:"flowlogStore",
        baseParams:{limit:pageSize,workflowId:workflowId},
        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/workFlowlogAction!getFlowLog.action"}),
        reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
        }, workflowlogField)
    });
    flowlogStore.load();
    
    //人员Store
	cusServiceStore=new Ext.data.Store({
		autoLoad:true,
        storeId:"cusServiceStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/user/userAction!list.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
    	{name:'id',mapping:'id'},
    	{name:'userName',mapping:'userName'}
    	])
    	
	});
	
    mainFormStore=new Ext.data.Store();
    detailFormStore=new Ext.data.Store();
    //主表GridPanel
   mainFormPanel=new Ext.grid.GridPanel({
		region:'north',
    	id : 'mainFormPanel',
    	height : 100,
    	width : Ext.lib.Dom.getViewWidth(),
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum
        ]),
        store:mainFormStore
    });
    //明细表GridPanel
   detailFormPanel=new Ext.grid.GridPanel({
		region:'center',
    	id : 'detailFormPanel',
    	height : 200,
    	width : Ext.lib.Dom.getViewWidth(),
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
        cm:new Ext.grid.ColumnModel([rowNum
        ]),
        store:detailFormStore
    });
    //审批已经panel
    auditPanel=new Ext.form.FormPanel({
    	id:'auditPanel',
		frame : true,
		renderTo:Ext.getBody(),
		labelAlign : "right",
		bodyStyle:'padding:0px 0px 0px 0px',
		labelWidth : 80,
		width:Ext.lib.Dom.getViewWidth(),
		height:110,
		defaults : {
			xtype : "textfield",
			width : 230
		},
		items:[
			{
				xtype:'hidden',
				name:'oprType',
				id:'oprType',
				value:'submit'
			},{
				xtype:'hidden',
				name:'workflowId',
				id:'workflowId',
				value:workflowId
			},{
				xtype:'hidden',
				name:'pipeId',
				id:'pipeId',
				value:pipeId
			},{
				xtype:'hidden',
				name:'nodeId',
				id:'nodeId',
				value:nodeId
			},{
				xtype:'hidden',
				name:'returnNodeId',
				id:'rejectnodeId'
			},
			{	
				xtype:'combo',
				hiddenName:'logType',
				id:'logTypecombo',
				typeAhead : true,
				forceSelection : true,
				allowBlank : false,
				blankText : "审批类型不能为空！",
				triggerAction : 'all',
				store:[
					['2','通过'],
					['3','否决'],
					['4','退回']
				],
				fieldLabel:'审批类型<span style="color:red">*</span>',
				listeners:{
					'render':function(combo){
						if(oprType == 'dispose' && operateType ==1){
							combo.setDisabled(false);
							combo.show();
							combo.getEl().up('.x-form-item').setDisplayed(true);
						}else{
							combo.setDisabled(true);
							combo.hide();
							combo.getEl().up('.x-form-item').setDisplayed(false);
						}
					}
				}
			},{	
				xtype:'combo',
				hiddenName:'logType',
				id:'logTypecombo1',
				typeAhead : true,
				forceSelection : true,
				allowBlank : false,
				blankText : "审批类型不能为空！",
				triggerAction : 'all',
				store:[
					['5','批注']
				],
				fieldLabel:'审批类型<span style="color:red">*</span>',
				listeners:{
					'render':function(combo){
						if(oprType == 'dispose' && operateType ==4){
							combo.setDisabled(false);
							combo.getEl().up('.x-form-item').setDisplayed(true);
							combo.show();
						}else{
							combo.setDisabled(true);
							combo.getEl().up('.x-form-item').setDisplayed(false);
							combo.hide();
						}
					}
				}
			},
			{
				xtype:'textarea',
				name:'auditRemark',
				id:'auditRemark',
				fieldLabel:'审核备注<span style="color:red">*</span>',
				height:60,
				allowBlank : false,
				blankText : "审批备注不能为空！"
			}
		],
		listeners:{
			'render':function(comp){
				if(oprType == 'see' || oprType == 'audit'){
					Ext.getCmp('auditPanel').hide();
					Ext.getCmp('auditPanel').setDisabled(true);
				}else if(oprType == 'dispose'){
					Ext.getCmp('auditPanel').setDisabled(false);
					Ext.getCmp('auditPanel').show();
				}
			}
		}
    });
   //审批意见GridPanel
    flowLogPanel = new Ext.grid.GridPanel({
    	id:'flowLogPanel',
        region:'south',
        height:Ext.lib.Dom.getViewHeight()-340, 
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
        	{header:'ID',dataIndex:"id",hidden: true, hideable: false},
        	{header:'节点ID',dataIndex:"nodeinfoId",hidden: true, hideable: false},
            {header:'节点',dataIndex:"nodeName"},
            {header:'意见',dataIndex:"remark",width:200},
            {header:'操作人',dataIndex:"updateName",width:120},
            {header:'操作时间',dataIndex:"updateTime",width:120},
            {header:'操作',dataIndex:"logType",width:80,renderer:function(v){
            	if(v==1){
            		return "提交";
            	}else if(v==2){
 	           		return "通过";
            	}else if(v == 3){
            		return "否决";
            	}else if(v == 4){
            		return "退回";
            	}else if(v == 5){
            		return "批注";
            	}
            }},
           {header:'退回节点ID',dataIndex:"returnNodeid",hidden: true, hideable: false},
           {header:'退回节点',dataIndex:"returnNodeName",width:80}
        ]),
        store:flowlogStore,
        bbar: new Ext.PagingToolbar({
            pageSize: pageSize,
            privilege:flowlogprivilege, 
            store: flowlogStore, 
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，共 {2} 条', emptyMsg: "没有记录信息显示"
        }),
        listeners:{
        	'render':function(){
        		if(oprType == 'see'){
					Ext.getCmp('flowLogPanel').setHeight(Ext.lib.Dom.getViewHeight()-315);
				}else if(oprType == 'dispose'){
					Ext.getCmp('flowLogPanel').setHeight(Ext.lib.Dom.getViewHeight()-440);
				}
        	}
        }
    });
   toolbar=new Ext.Toolbar({
   		width:Ext.lib.Dom.getViewWidth(),
   		id:'panetbar',
   		items:[
	    {
	    	text:'<b>提交</b>',
	    	id : 'flowsubmit',
			tooltip : '提交流程',
			iconCls : 'save',
			handler:function(){
				if(auditPanel.getForm().isValid()) {
					Ext.Msg.confirm(alertTitle, "确定要提交？", function(btnYes) {
						if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							var logType=Ext.getCmp('logTypecombo').getValue();
							if(logType == '4'){
								Ext.Ajax.request({
									url:sysPath+'/flow/flowNodeinfoAction!list.action',
									params:{
										privilege:245,
										filter_EQL_id:nodeId
									},
									success:function(resp){
										var respText = Ext.util.JSON.decode(resp.responseText);
										if(respText.result.length>0){
											var isReject=respText.result[0].isReject;
											if(isReject != 1){
												Ext.Msg.alert(alertTitle,'该节点不允许退回，请联系系统管理员进行设置！');
												return;
											}else{
												Ext.getCmp('rejectnodeId').setValue(respText.result[0].rejectnodeId);
												auditFormSubmit();
											}
										}
									}
								});
							}else{
								auditFormSubmit();
							}
						}
					});
				}
			}
	    },'-',{
	    	text:'<b>转发</b>',
	    	id : 'flowtransmit',
			tooltip : '流程转发',
			handler:function(){
				flowtransmit();
			}
	    },'-',{
	    	text:'<b>审核</>',
	    	id:'flowaudit',
	    	tooltip:'流程审核',
	    	handler:function(){
				flowAudit();
	    	}
	    },
	    '-',
	    {
	    	text:'<b>查看流程图</b>',
	    	id : 'flowshowchar',
			tooltip : '查看当前流程流程图',
			handler:function(){
				var node=new Ext.tree.TreeNode({id:'editConsterList2',leaf :false,text:'流程图查看'});
		      	node.attributes={href1:"/flow/workFlowbaseAction!charShow.action?workflowId="+workflowId+"&pipeId="+pipeId+"&nodeId="+nodeId};
		        parent.toAddTabPage(node,true);
			}
	    }
	    ]
   });
   panel = new Ext.Panel({
   		id:'forminfoPanel',
        //title: '流程审批',
        renderTo: 'showView',
        width:Ext.lib.Dom.getViewWidth(),
	    height:Ext.lib.Dom.getViewHeight(),
	    defaults: {   
	        autoScroll: true,   
	        autoHeight:true,   
	        style: "padding:0 0 0 0"
	    },
	    tbar:toolbar,
        items:[{
        	layout:'form',
        	items:[mainFormPanel,detailFormPanel,auditPanel,flowLogPanel]
        }],
        listeners:{
        	'render':function(){
        		if(oprType == 'see'){
        			Ext.getCmp('flowsubmit').hide();
        			Ext.getCmp('flowtransmit').hide();
        			Ext.getCmp('flowaudit').hide();
        		}else if(oprType == 'dispose'){
        			if(operateType ==1){
        				Ext.getCmp('flowtransmit').show();
        			}else{
        				Ext.getCmp('flowtransmit').hide();
        			}
        			Ext.getCmp('flowaudit').hide();
        			Ext.getCmp('flowsubmit').show();
        		}else if(oprType == "audit"){
        			Ext.getCmp('flowsubmit').hide();
        			Ext.getCmp('flowaudit').show();
        		}
        	}
        }
    });
    showMainDetail();
    
});
function showMainDetail(){
    
    mainHeadStore.on('load',function(){
    	var cmItems = []; 
		var afields=[];
		for(var i=0;i<mainHeadStore.getCount();i++){
			if(mainHeadStore.getAt(i).get("LABEL_NAME")=='传真信息'){
				cmItems.push({header:mainHeadStore.getAt(i).get("LABEL_NAME"),dataIndex:mainHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(mainHeadStore.getAt(i).get("FIELD_NAME")),width:400});
			}else{
				cmItems.push({header:mainHeadStore.getAt(i).get("LABEL_NAME"),dataIndex:mainHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(mainHeadStore.getAt(i).get("FIELD_NAME"))});
			}
 			afields.push({name:mainHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(mainHeadStore.getAt(i).get("FIELD_NAME")),mapping:mainHeadStore.getAt(i).get("FIELD_NAME").toUpperCase()});
 		} 
       // Ext.apply(carTypeStore.fields,afields); 
        //部门增值收入Store
		mainFormStore = new Ext.data.Store({
	        storeId:"mainFormStore", 
	        baseParams:{pipeId:pipeId,tableType:'main',workflowId:workflowId},
	        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowForminfoAction!getFormvalue.action"}),
	        reader: new Ext.data.JsonReader({
	        }, afields)
	    });
        mainFormPanel.reconfigure(mainFormStore,new Ext.grid.ColumnModel(cmItems));
        mainFormStore.load();
        mainFormPanel.doLayout();
    });
    detailHeadStore.on("load",function(){
		var rowNum = new Ext.grid.RowNumberer({
		    header:'序号', width:35, sortable:true
		});
    	var cmItems = [];
		var afields=[];
		cmItems.push(rowNum);
		for(var i=0;i<detailHeadStore.getCount();i++){
			if(detailHeadStore.getAt(i).get("LABEL_NAME")=='是否系统默认'){
				cmItems.push({header:detailHeadStore.getAt(i).get("LABEL_NAME"),dataIndex:detailHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(detailHeadStore.getAt(i).get("FIELD_NAME")),renderer:function(v){
					if(v ==0){
						return "否";
					}else if(v ==1){
						return "是";
					}else{
						return v;
					}
				}});
			}else{
				cmItems.push({header:detailHeadStore.getAt(i).get("LABEL_NAME"),dataIndex:detailHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(detailHeadStore.getAt(i).get("FIELD_NAME"))});
			}
 			afields.push({name:detailHeadStore.getAt(i).get("OBJ_TABLENAME")+'_'+(detailHeadStore.getAt(i).get("FIELD_NAME")),mapping:detailHeadStore.getAt(i).get("FIELD_NAME").toUpperCase()});
 		} 
       // Ext.apply(carTypeStore.fields,afields);
        
        //部门增值收入Store
		detailFormStore = new Ext.data.Store({
	        storeId:"detailFormStore",
	        baseParams:{pipeId:pipeId,tableType:'detail',workflowId:workflowId},
	        proxy: new Ext.data.HttpProxy({url:sysPath+"/flow/flowForminfoAction!getFormvalue.action"}),
	        reader: new Ext.data.JsonReader({
	        }, afields)
	    });
       
        detailFormPanel.reconfigure(detailFormStore,new Ext.grid.ColumnModel(cmItems));
        detailFormStore.load();
        detailFormPanel.doLayout();
    });
}

function auditFormSubmit(){
	auditPanel.getForm().submit({
		url:sysPath+'/flow/workFlowbaseAction!flowSubmit.action',
		waitMsg : '正在保存数据...',
		success : function(form, action) {
			Ext.Msg.alert(alertTitle,
					action.result.msg);
			Ext.getCmp('flowsubmit').setDisabled(true);
			auditPanel.getForm().reset();
			flowlogStore.reload();
		},
		failure : function(form, action) {
			if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
				Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
			} else {
				Ext.Msg.alert(alertTitle,
					action.result.msg);
				if (action.result.msg) {
					win.hide();
					Ext.Msg.alert(alertTitle,
							action.msg);
				}
			}
		}
	});
}

function flowtransmit(){
	var form = new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelAlign : "left",
			labelWidth : 70,
			width : 400,
			labelAlign : "right",
			labelWidth : 70,
			defaults : {
				xtype : "textfield",
				width : 230
			},
			items:[{xtype:'hidden',name:'nodeId',value:nodeId},
					{xtype:'hidden',name:'workflowId',value:workflowId},
					{xtype:'hidden',name:'pipeId',value:pipeId},
                    {
                   		xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						selectOnFocus : true,
						resizable : true,
						minChars : 0,
						pageSize:pageSize,
						fieldLabel:'指派人<span style="color:red">*</span>',
						queryParam : 'filter_LIKES_userName',
						triggerAction : 'all',
						store : cusServiceStore,
						mode : "remote",// 从服务器端加载值
						valueField : 'id',// value值，与fields对应
						displayField : 'userName',// 显示值，与fields对应
					    hiddenName : 'userId',
					    id:'combouser',
					    allowBlank : false,
					    blankText:'人员不能为空'
                   	}
            ]
		});
		
		var win = new Ext.Window({
			title : '转发流程',
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						form.getForm().submit({
							url : sysPath + "/flow/workFlowbaseAction!flowsend.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg);
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										Ext.Msg.alert(alertTitle,
												action.result.msg);
									}
								}
							}
						});
					}
				}
			}, {
				text : "重置",
				handler : function() {
					form.getForm().reset();
				}
			}, {
				text : "取消",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					form.destroy();
				});
		win.show();
}
function flowAudit(){
	Ext.Ajax.request({
		url:sysPath+'/flow/workFlowbaseAction!flowSubmit.action',
		waitMsg : '正在保存数据...',
		params:{
			logType:6,
			nodeId:nodeId,
			workflowId:workflowId,
			pipeId:pipeId,
			oprType:'audit'
		},success:function(resp){
			var respText = Ext.util.JSON.decode(resp.responseText);
			Ext.Msg.alert(alertTitle,respText.msg);
			flowlogStore.reload();
		}
	});
}
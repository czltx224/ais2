//var pageSize = 1;
var oprTitle='';
var comboxPage = 15;
var privilege = 227;//应收应付
var detailSearchUrl = "stock/requestTypeDetailAction!list.action";
var mainSearchUrl = "stock/requestTypeMainAction!list.action";

var maindateStore,collectionStore,collectionPanel,requestStageStore;
var sm = new Ext.grid.CheckboxSelectionModel();
var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 40,
			sortable : true
		});

var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "requestType", // 需求类型
			mapping : 'requestType'
		}, {
			name : 'createName',
			mapping : 'createName'
		}, {
			name : 'createTime',
			mapping : 'createTime'
		}, {
			name : 'updateName',
			mapping : 'updateName'
		}, {
			name : 'updateTime',
			mapping : 'updateTime'
		}, {
			name : 'ts',
			mapping : 'ts'
		}];
var expander = new Ext.ux.grid.RowExpander({
    	height:280,
        tpl : '<div id="dno_{id}" style="height:280px;width:1126px" ></div>',
        listeners : {
			'expand' : function(record, body, rowIndex){
		 		createTablePanel(body.get("id"));
			 }
		}
});
var collfield=[
	{name:'id'},
	{name:'requestItem'},
	{name:'requestStage'},
	{name:'requestTypeMainId'},
	{name:'createTime'},
	{name:'createName'},
	{name:'updateName'},
	{name:'updateTime'}
];
var cm = new Ext.grid.ColumnModel([expander,rowNum, sm, {
		header : 'id',
		hidden : true,
		hideable:false,
		dataIndex : 'id'
	}, {
		header : '需求类型',
		dataIndex : 'requestType'
	},{
		header : '创建人',
		dataIndex : 'createName'
	}, {
		header : '创建时间',
		dataIndex : 'createTime'
	}, {
		header : '修改人',
		dataIndex : 'updateName'
	}, {
		header : '修改时间',
		dataIndex : 'updateTime'
	}, {
		header : '时间戳',
		dataIndex : 'ts',
		sortable : true,
		hidden : true,
		hideable:false
}]);

Ext.onReady(function() {
	// 查询主列表
	maindateStore = new Ext.data.Store({
		storeId : "maindateStore",
		baseParams : {limit:pageSize},
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + mainSearchUrl
					}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
	});
	//要求阶段Store
	requestStageStore=new Ext.data.Store({
		storeId:"requestStageStore",
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/dictionaryAction!ralaList.action"}),
		baseParams:{filter_EQL_basDictionaryId:5,privilege:16},
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'requestStage',mapping:'typeName'}
        	])
	});
	var tbarsearch = new Ext.Toolbar({
			items : [{
                text:'<B>增加</B>', id:'addbtn',tooltip:'增加类别', iconCls: 'userAdd',handler:function() {
                	collection(null);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updatebtn',disabled:true, tooltip:'修改选择的类别', iconCls: 'userEdit', handler: function() {
                var _record = mainGrid.getSelectionModel().getSelected();
                collection(_record);
            } },'-', {
				text : '<b>搜索</b>',
				id : 'btn',
				iconCls : 'btnSearch',
				handler : searchmain
			}]
		});
	var mainGrid = new Ext.grid.GridPanel({
		renderTo : "showView",
		id : 'mainGrid',
		height : Ext.lib.Dom.getViewHeight(),
		width : Ext.lib.Dom.getViewWidth(),
		viewConfig : {
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序",
			forceFit : false,
			scrollOffset: 0,
			autoScroll:true
			
		},
		plugins : [expander],
		autoScroll : true,
		frame : true,
		loadMask : true,
		sm : sm,
		cm : cm,
		ds : maindateStore,
		tbar : tbarsearch,
		bbar : new Ext.PagingToolbar({
					pageSize : pageSize,
					store : maindateStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
				})
	});
	mainGrid.render();
	mainGrid.on('click', function() {
		var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
		var updatebtn = Ext.getCmp('updatebtn');// 获得更新按钮

		if (_record.length == 1) {
			if (updatebtn) {
				updatebtn.setDisabled(false);
			}
		} else {
			if (updatebtn) {
				updatebtn.setDisabled(true);
			}
		}

	});
});
function collection(record){
	var form = new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelWidth : 90,
			width : 400,
			defaults : {
				xtype : "textfield",
				width : 230
			},
			labelAlign : "right",
			reader : new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, collfield),
			items:[
				{xtype:'hidden',name:'ts',id:'ts'},
				{xtype:'hidden',name:'id',id:'id'},
	          	{
	   				xtype:'textfield',
	   				name:'requestType',
	   				id:'requestType',
	   				fieldLabel:'要求类别'
	   			},{
					xtype:'textfield',
	   				name:'createName',
	   				id:'createName',
	   				value:userName,
	   				readOnly:true,
	   				fieldLabel:'创建人'
	          	},{
	          		xtype:'datefield',
	   				name:'createTime',
	   				id:'createTime',
	   				value:new Date(),
	   				readOnly:true,
	   				format:'Y-m-d',
	   				fieldLabel:'创建时间'
	          	}
            ]
		});
		oprTitle="新增个性化要求类别";
		if(record!=null){
		    var _cid=record.data.id;
			oprTitle="修改个性化要求类别";
			Ext.getCmp("id").setValue(_cid);
			form.load({
				url : sysPath+ "/"+mainSearchUrl,
				params:{filter_EQL_id:_cid,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new Ext.Window({
			title : oprTitle,
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
							url : sysPath + "/stock/requestTypeMainAction!save.action?privilege=227",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										'保存成功');
								dataReload();
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
function searchmain() {
	dataReload();
}

function dataReload() {	
	maindateStore.reload({
		params : {
			start : 0
		}
	});
}
function addRequestItem(record,mainId){
	var form = new Ext.form.FormPanel({
			frame : true,
			border : false,
			bodyStyle : 'padding:5px 5px 5px',
			labelWidth : 90,
			width : 400,
			defaults : {
				xtype : "textfield",
				width : 230
			},
			labelAlign : "right",
			reader : new Ext.data.JsonReader({
	            root: 'result', totalProperty: 'totalCount'
	        }, collfield),
			items:[
				{xtype:'hidden',name:'ts',id:'ts'},
				{xtype:'hidden',name:'requestTypeMainId',id:'requestTypeMainId',value:mainId},
				{xtype:'hidden',name:'id',id:'id'},
	          	{
	   				xtype:'textfield',
	   				name:'requestItem',
	   				id:'requetsItem',
	   				fieldLabel:'要求类型',
	   				allowBlank:false,
	   				blankText:'要求类型不能为空'
	   			},{
	   				xtype:'combo',
    				id:'requeststagecombo',
    				fieldLabel:'要求阶段',
    				name:'requestStage',
    				triggerAction : 'all',
    				emptyText : '选择类型',
    				minChar:0,
    				forceSelection : true,
    				model : 'local',
    				store:requestStageStore,
    				valueField : 'requestStage',
				    displayField : 'requestStage',
				    allowBlank:false,
	   				blankText:'要求阶段不能为空'
	   			},{
					xtype:'textfield',
	   				name:'createName',
	   				id:'createName',
	   				value:userName,
	   				readOnly:true,
	   				fieldLabel:'创建人'
	          	},{
	          		xtype:'datefield',
	   				name:'createTime',
	   				id:'createTime',
	   				value:new Date(),
	   				readOnly:true,
	   				format:'Y-m-d',
	   				fieldLabel:'创建时间'
	          	}
            ]
		});
		oprTitle="新增个性化要求类型";
		if(record!=null){
		    var _cid=record.data.id;
			oprTitle="修改个性化要求类型";
			Ext.getCmp("id").setValue(_cid);
			form.load({
				url : sysPath+ "/"+detailSearchUrl,
				params:{filter_EQL_id:_cid,limit : pageSize},
				success:function(){
				}
			})
		}
		var win = new Ext.Window({
			title : oprTitle,
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
							url : sysPath + "/stock/requestTypeDetailAction!save.action?privilege=227",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										'保存成功');
								collectionStore.load();
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
function createTablePanel(mainId){
	var sm = new Ext.grid.CheckboxSelectionModel({});
    var rowNum = new Ext.grid.RowNumberer({
        header:'序号', width:35, sortable:true
    });
	collectionStore = new Ext.data.Store({
		storeId : "collectionStore",
		baseParams:{
			limit:pageSize,
			filter_EQL_requestTypeMainId:mainId
		},
		proxy : new Ext.data.HttpProxy({
			url : sysPath + "/"+detailSearchUrl 
		}),
		reader : new Ext.data.JsonReader({
			root : 'result',
			totalProperty : 'totalCount'
		}, collfield)
	});
	collectionPanel=new Ext.grid.GridPanel({
		region : "center",
    	id : 'collectionPanel',
    	height : 280,
    	width : 550,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
    	tbar:[{
                text:'<B>增加</B>', id:'addDetailbtn',tooltip:'增加类型', iconCls: 'userAdd',handler:function() {
                	addRequestItem(null,mainId);
            } },
            '-',
            {
                text:'<B>修改</B>',id:'updateDetailbtn',disabled:true, tooltip:'修改选择的类型', iconCls: 'userEdit', handler: function() {
                var _record = collectionPanel.getSelectionModel().getSelected();
                	addRequestItem(_record,mainId);
            } }
		],
		sm:sm,
    	cm : new Ext.grid.ColumnModel([rowNum,sm,
			{
				header : '编号',
				dataIndex : 'id',
				width:80,hidden: true,
				hideable: false
			}, {
				header : '要求类型',
				dataIndex : 'requestItem',
				width:120
			}, {
				header:'要求阶段',
				dataIndex:'requestStage',
				width:80
			}, {
				header : '创建人',
				dataIndex : 'createName',
				width:80
			}, {
				header : '创建时间',
				dataIndex : 'createTime',width:80
			}, {
				header : '修改人',
				hidden : true,
				dataIndex : 'updateName',width:80
			}, {
				header : '修改时间',
				hidden : true,
				dataIndex : 'updateTime',
				width:80
			}

		]),
		ds : collectionStore,
		bbar : new Ext.PagingToolbar({
			pageSize : pageSize, // 默然为25，会在store的proxy后面传limit
			store : collectionStore,
			displayInfo : true,
			displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
			emptyMsg : "没有记录信息显示"
		})
	});
	if(collectionPanel.rendered){
		collectionPanel.doLayout();
	}else{
		collectionPanel.render('dno_'+mainId);
		collectionStore.load();
	}
	collectionPanel.setHeight(280);

	collectionPanel.on('click', function() {
		var _record = collectionPanel.getSelectionModel().getSelections();// 获得所有选中的行
		//var addbtn=Ext.getCmp('addDetailbtn');
		var updatebtn = Ext.getCmp('updateDetailbtn');// 获得更新按钮

		if (_record.length == 1) {
			if (updatebtn) {
				updatebtn.setDisabled(false);
			}
		} else {
			if (updatebtn) {
				updatebtn.setDisabled(true);
			}
		}
	});
}

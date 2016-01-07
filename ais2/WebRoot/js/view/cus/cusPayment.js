//var pageSize = 1;
var comboxPage = 15;
var privilege = 82;//应收应付
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var maingridSearchUrl = "fi/fiReceivablestatementAction!ralaList.action";

var customerStore, departStore,maindateStore,cusStore,collectionPanel;
var sm = new Ext.grid.CheckboxSelectionModel();
var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});

var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "customerId", // 客商id
			mapping : 'customerId'
		}, {
			name : "customerName", // 客商名称
			mapping : 'customerName'
		}, {
			name : "stateDate", // 开始日期
			mapping : 'stateDate'
		}, {
			name : "endDate", // 结束日期
			mapping : 'endDate'
		}, {
			name : "accountsAmount", // 应收金额
			mapping : 'accountsAmount'
		}, {
			name : "copeAmount", // 应付金额
			mapping : 'copeAmount'
		}, {
			name : "openingBalance", // 期初余额
			mapping : 'openingBalance'
		}, {
			name : "accountsBalance", // 应收余额
			mapping : 'accountsBalance'
		}, {
			name : "problemAmount", // 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : "reconciliationUser", // 对账员
			mapping : 'reconciliationUser'
		}, {
			name : "reviewUser", // 审核人
			mapping : 'reviewUser'
		}, {
			name : "reviewDept", // 审核部门
			mapping : 'reviewDept'
		}, {
			name : "reviewDate", // 审核时间
			mapping : 'reviewDate'
		}, {
			name : "reviewRemark", // 审核备注
			mapping : 'reviewRemark'
		}, {
			name : "reconciliationStatus", // 对账状态
			mapping : 'reconciliationStatus'
		}, {
			name : "mailtoNum", // 邮件发送次数
			mapping : 'mailtoNum'
		}, {
			name : "printNum", // 打印次数
			mapping : 'printNum'
		}, {
			name : "exportNum", // 导出次数
			mapping : 'exportNum'
		}, {
			name : 'departName',// 创建部门
			mapping : 'departName'
		}, {
			name : 'departId',// 创建部门ID
			mapping : 'departId'
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
        tpl : '<div id="dno_{customerId}" style="height:280px;width:1126px" ></div>',
        listeners : {
			'expand' : function(record, body, rowIndex){
		 		createTablePanel(body.get("customerId"));
			 }
		}
});
var collfield=[
	{name:'id'},
	{name:'cusId'},
	{name:'cusName'},
	{name:'remark'},
	{name:'money'},
	{name:'repaymentDate'},
	{name:'collectionFile'},
	{name:'collectionLink'},
	{name:'collectionLinkTel'},
	{name:'collectionDtime'},
	{name:'createTime'},
	{name:'createName'},
	{name:'updateName'},
	{name:'updateTime'}
];
function createTablePanel(cusId){
	var collectionStore = new Ext.data.Store({
				storeId : "collectionStore",
				baseParams:{
					limit:pageSize,
					filter_EQL_departId:bussDepart,
					filter_EQL_cusId:cusId
				},
				proxy : new Ext.data.HttpProxy({
					url : sysPath + "/cus/cusPaymentAction!list.action" 
				}),
				reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, collfield)
	});
	collectionPanel=new Ext.grid.GridPanel({
		region : "center",
    	id : 'collectionPanel',
    	height : Ext.lib.Dom.getViewHeight(),
    	width : Ext.lib.Dom.getViewWidth()-1,
    	frame : false,
    	loadMask : true,
    	stripeRows : true,
    	viewConfig:{
    		scrollOffset: 0,
    		autoScroll : true
    	},
    	cm : new Ext.grid.ColumnModel([rowNum,
				{
					header : '编号',
					dataIndex : 'id',
					width:80,hidden: true,
					hideable: false
				}, {
					header : '客商ID',
					dataIndex : 'cusId',
					width:80,
					hidden: true
				}, {
					header:'客商名称',
					dataIndex:'cusName',
					width:80
				},{
					header : '催款金额',
					dataIndex : 'money',width:80
				}, {
					header : '承诺还款日期',
					sortable : true,
					dataIndex : 'repaymentDate',
					width:80
				}, {
					header : '催款联系人',
					dataIndex : 'collectionLink',
					width:80
				}, {
					header : '催款联系电话',
					dataIndex : 'collectionLinkTel',
					width:80
				}, {
					header : '催款时间',
					dataIndex : 'collectionDtime',
					width:80
				}, {
					header : '催款人',
					dataIndex : 'createName',
					width:80
				}, {
					header : '催款时间',
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
		collectionPanel.render('dno_'+cusId);
		collectionStore.load();
	}
	collectionPanel.setHeight(280);
}
var cm = new Ext.grid.ColumnModel([expander,rowNum, sm, {
			header : '对账单号',
			width:100,
			dataIndex : 'id'
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商',
			width:100,
			dataIndex : 'customerName'
		}, {
			header : '开始日期',
			width:80,
			dataIndex : 'stateDate'
		}, {
			header : '结束日期',
			width:80,
			dataIndex : 'endDate'
		}, {
			header : '对账状态',
			width:80,
			dataIndex : 'reconciliationStatus',
			renderer:function(v,metaData){
				if(v==0){
					v='已作废';
				}else if (v==1){
					v='未对账';
				}else if (v==2){
					v='未审核';
				}else if (v==3){
					v='已审核';
				}
				return v;
			}
		}, {
			header : '期初余额',
			width:80,
			dataIndex : 'openingBalance'
		}, {
			header : '应收金额',
			width:80,
			dataIndex : 'accountsAmount'
		}, {
			header : '应付金额',
			width:80,
			dataIndex : 'copeAmount'
		}, {
			header : ' 应收余额',
			width:80,
			dataIndex : 'accountsBalance'
		}, {
			header : '问题账款金额',
			width:120,
			dataIndex : 'problemAmount'
		}, {
			header : '对账员',
			width:80,
			dataIndex : 'reconciliationUser'
		}, {
			header : '审核部门',
			width:80,
			dataIndex : 'reviewDept',
			hidden : true
		}, {
			header : '审核人',
			width:80,
			dataIndex : 'reviewUser'
		}, {
			header : '审核时间',
			width:80,
			dataIndex : 'reviewDate'
		}, {
			header : ' 邮件发送次数',
			width:120,
			dataIndex : 'mailtoNum'
		}, {
			header : '打印次数',
			width:80,
			dataIndex : 'printNum'
		}, {
			header : '导出次数',
			width:80,
			dataIndex : 'exportNum'
		}, {
			header : '所属部门',
			width:80,
			dataIndex : 'departName'
		}, {
			header : '所属部门ID',
			dataIndex : 'departId',
			hidden : true
		}, {
			header : '创建人',
			width:80,
			dataIndex : 'createName'
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
			hidden : true
		}, {
			header : '修改人',
			dataIndex : 'updateName',
			hidden : true
		}, {
			header : '修改时间',
			dataIndex : 'updateTime',
			hidden : true
		}, {
			header : '时间戳',
			dataIndex : 'ts',
			sortable : true,
			hidden : true,
			hideable:false
}]);

Ext.onReady(function() {
	    cusStore=new Ext.data.Store({
		storeId:"cusStore",
		baseParams:{privilege:61,filter_EQS_custprop:'发货代理'},
		proxy:new Ext.data.HttpProxy({url:sysPath+"/sys/customerAction!ralaList.action"}),
		reader:new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'
        },[
        	{name:'id'},
        	{name:'cusName'}
        	])
	});
	reconstatusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [/*['1', '未对账'], */['2', '未审核'], ['3', '已审核']/*, ['4', '已收银']*/, ['5', '已作废']],
				fields : ["reconciliationStatus", "typeName"]
			});
	// 客商列表
	customerStore = new Ext.data.Store({
				storeId : "customerStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + customerGridSearchUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'cusName',
									mapping : 'cusName'
								}, {
									name : 'customerId',
									mapping : 'id'
								}])
			});

	// 部门列表
	departStore = new Ext.data.Store({
				storeId : "departStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + departGridSearchUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'departName',
									mapping : 'departName'
								}, {
									name : 'departId',
									mapping : 'departId'
								}])
			});

	// 查询主列表
	maindateStore = new Ext.data.Store({
				storeId : "maindateStore",
				baseParams : {limit:pageSize,privilege : privilege,filter_EQL_customerId:parent.cusId},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + maingridSearchUrl
							}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fields)
			});
	if(parent.cusId!=null){
		//maindateStore.load();
	}
	var tbarsearch = new Ext.Toolbar({
			items : [{
				text:'<B>催款</B>', id:'collectionbtn',iconCls: 'userAdd',disabled:true,handler:function() {
					var _record = mainGrid.getSelectionModel().getSelected();
					if(parent.cusRecord==null){
						collection(_record);
					}else{
						collectionnoNull(_record);
					}
					/*
					var node=new Ext.tree.TreeNode({id:'addConsterList',leaf :false,text:'催款'});
			      	node.attributes={href1:"/cus/cusCollection.jsp"};
			        parent.toAddTabPage(node,true);*/
		            }
			},'单据日期', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date().add(Date.DAY, -7),
						blankText : "[单据日期从]不能为空！",
						hiddenId : 'stateDate',
						hiddenName : 'stateDate',
						id : 'stateDate'
					}, '至', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date(),
						blankText : "[单据日期至]不能为空！",
						hiddenId : 'endDate',
						hiddenName : 'endDate',
						id : 'endDate'
					}, '-', '客商', {
						xtype : 'combo',
						typeAhead : false,
						forceSelection : true,
						minChars : 1,
						triggerAction : 'all',
						store : customerStore,
						pageSize : comboxPage,
						queryParam : 'filter_LIKES_cusName',
						id : 'comboCustomer',
						valueField : 'customerId',
						displayField : 'cusName',
						name : 'cusName',
						width : 120,
						enableKeyEvents : true,
						listeners : {
							'select' : function(textField, e) {
								var custprop = Ext.getCmp("comboCustprop"); // 客商类型
								var billingCycle = Ext
										.getCmp("comboBillingCycle"); // 客商类型
								billingCycle.setDisabled(true);
								custprop.setDisabled(true);
							},
							keyup : function(textField, e) {
								var customerId = Ext.get('comboCustomer').dom.value;
								if (customerId == '') {
									var custprop = Ext.getCmp("comboCustprop"); // 客商类型
									var billingCycle = Ext
											.getCmp("comboBillingCycle"); // 客商类型
									billingCycle.setDisabled(false);
									custprop.setDisabled(false);
								}
							}
						}

					}, '-', {
						text : '<b>搜索</b>',
						id : 'btn',
						iconCls : 'btnSearch',
						handler : searchmain
					}]
		});
	var mainGrid = new Ext.grid.GridPanel({
				region : "center",
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

	// 布局
	var mainpanel = new Ext.Panel({
				id : 'view',
				el : 'mainPanel',
				labelAlign : 'left',
				height : Ext.lib.Dom.getViewHeight()-4,
				width : Ext.lib.Dom.getViewWidth(),
				bodyStyle : 'padding:1px',
				layout : "border",
				frame : false,
				items : [mainGrid]
			});
	mainpanel.on('render', function() {
	});
	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();
	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var updatebtn = Ext.getCmp('collectionbtn');// 获得更新按钮

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
					width : 600,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, collfield),
					items:[
                           {
					        layout : 'column',
					        items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					{xtype:'hidden',name:'cusName',id:'cusName'},
						   					{xtype:'hidden',name:'collectionDtime',value:new Date().format('Y-m-d')},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
								          	{
								   				xtype:'combo',
				                            	store:cusStore,
				                            	displayField:'cusName',
				                            	valueField:'id',
				                            	pageSize:pageSize,
				                            	queryParam:'filter_LIKES_cusName',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	id:'cuscombo',
				                            	emptyText:'请选择',
				                            	fieldLabel:'客商名称<span style="color:red">*</span>',
				                            	hiddenName:'cusId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客商名称不能为空！',
				                            	listeners:{
				                            		'select':function(){
				                            			Ext.getCmp('cusName').setValue(Ext.get('cuscombo').dom.value);
				                            		 }
				                            	}
								   			},{
												xtype:'textfield',
												fieldLabel:'催款联系人<span style="color:red">*</span>',
												name:'collectionLink',
												id:'collectionLink',
												allowBlank:false,
								          		blankText:'催款金额不能为空！',
								          		maxLength:100,
								          		maxLengthText:'长度不能大于100个汉字',
								          		width:160
								          	},{
								          		xtype:'textfield',
						                        fieldLabel:'附件',
						                        width:180,
						                        text:'浏览',
						                        name:'upFile',
						                        inputType:'file'  
								          	},{
								          		xtype:'textarea',
								          		fieldLabel:'催款备注',
								          		width:180,
								          		height:60,
								          		name:'remark'
								          	}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
										          	{
										          		xtype:'numberfield',
										          		fieldLabel:'催款金额<span style="color:red">*</span>',
										          		name:'money',
										          		allowBlank:false,
										          		blankText:'催款金额不能为空！',
										          		maxValue:99999999.99,
														maxLength:11,
										          		width:160
										          	},
										          	{
										          		xtype:'textfield',
										          		fieldLabel:'催款电话<span style="color:red">*</span>',
										          		name:'collectionLinkTel',
										          		allowBlank:false,
										          		blankText:'电话不能为空！',
										          		maxLength:50,
										          		maxLengthText:'长度不能大于50个字符',
										          		width:160
										          	},{
										          		xtype:'datefield',
										          		name:'repaymentDate',
										          		id:'repaymentDate',
										          		format:'Y-m-d',
										          		blankText:'承诺还款日期不能为空！',
										          		allowBlank:false,
										          		fieldLabel:'承诺还款日期<span style="color:red">*</span>',
										          		width:160
										          	}

				                    ]
				            }]
                    }
                    ]
                    
		});
		var win = new Ext.Window({
			title : '催款',
			width : 650,
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
							url : sysPath + "/cus/cusPaymentAction!save.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,
										'保存成功');
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
		Ext.getCmp('cuscombo').setValue(record.data.customerId);
		Ext.getCmp('cusName').setValue(record.data.customerName);
		Ext.getCmp('cuscombo').setRawValue(record.data.customerName);
}
function collectionnoNull(record){
	var form = new top.Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					labelWidth : 90,
					width : 600,
					labelAlign : "right",
					reader : new Ext.data.JsonReader({
			            root: 'result', totalProperty: 'totalCount'
			        }, collfield),
					items:[
                           {
					        layout : 'column',
					        items:[{
					        		layout:'form',
					        		columnWidth :.5,
					        		items:[
					        				{xtype:'hidden',name:'ts',id:'ts'},
						   					{xtype:'hidden',name:'id',id:'id'},
						   					{xtype:'hidden',name:'cusName',id:'cusName'},
						   					{xtype:'hidden',name:'collectionDtime',value:new Date().format('Y-m-d')},
						   					{xtype:'hidden',name:'departId',value:bussDepart},
								          	{
								   				xtype:'combo',
				                            	store:cusStore,
				                            	displayField:'cusName',
				                            	valueField:'id',
				                            	pageSize:pageSize,
				                            	queryParam:'filter_LIKES_cusName',
				                            	triggerAction : 'all',
				                            	model:'local',
				                            	id:'cuscombo',
				                            	emptyText:'请选择',
				                            	fieldLabel:'客商名称<span style="color:red">*</span>',
				                            	hiddenName:'cusId',
				                            	width:160,
				                            	allowBlank:false,
				                            	blankText:'客商名称不能为空！',
				                            	listeners:{
				                            		'select':function(){
				                            			top.Ext.getCmp('cusName').setValue(top.Ext.get('cuscombo').dom.value);
				                            		 }
				                            	}
								   			},{
												xtype:'textfield',
												fieldLabel:'催款联系人<span style="color:red">*</span>',
												name:'collectionLink',
												id:'collectionLink',
												allowBlank:false,
								          		blankText:'催款金额不能为空！',
								          		maxLength:100,
								          		maxLengthText:'长度不能大于100个汉字',
								          		width:160
								          	},{
								          		xtype:'textfield',
						                        fieldLabel:'附件',
						                        width:180,
						                        text:'浏览',
						                        name:'upFile',
						                        inputType:'file'  
								          	},{
								          		xtype:'textarea',
								          		fieldLabel:'催款备注',
								          		width:180,
								          		height:60,
								          		name:'remark'
								          	}
		                           	]
				                    },{
				                    layout:'form',
				                    columnWidth :.5,
				                    items:[
										          	{
										          		xtype:'numberfield',
										          		fieldLabel:'催款金额<span style="color:red">*</span>',
										          		name:'money',
										          		allowBlank:false,
										          		blankText:'催款金额不能为空！',
										          		maxValue:99999999.99,
														maxLength:11,
										          		width:160
										          	},
										          	{
										          		xtype:'textfield',
										          		fieldLabel:'催款电话<span style="color:red">*</span>',
										          		name:'collectionLinkTel',
										          		allowBlank:false,
										          		blankText:'电话不能为空！',
										          		maxLength:50,
										          		maxLengthText:'长度不能大于50个字符',
										          		width:160
										          	},{
										          		xtype:'datefield',
										          		name:'repaymentDate',
										          		id:'repaymentDate',
										          		format:'Y-m-d',
										          		blankText:'承诺还款日期不能为空！',
										          		allowBlank:false,
										          		fieldLabel:'承诺还款日期<span style="color:red">*</span>',
										          		width:160
										          	}

				                    ]
				            }]
                    }
                    ]
                    
		});
		var win = new top.Ext.Window({
			title : '催款',
			width : 650,
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
							url : sysPath + "/cus/cusPaymentAction!save.action",
							waitMsg : '正在保存数据...',
							success : function(form, action) {
								win.hide(), 
								top.Ext.Msg.alert(alertTitle,
										'保存成功');
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									top.Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
									if (action.result.msg) {
										win.hide();
										top.Ext.Msg.alert(alertTitle,
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
		top.Ext.getCmp('cuscombo').setValue(record.data.customerId);
		top.Ext.getCmp('cusName').setValue(record.data.customerName);
		top.Ext.getCmp('cuscombo').setRawValue(record.data.customerName);
}
function searchmain() {
	dataReload();
}

function dataReload() {
	var stateDate = Ext.get("stateDate").dom.value;
	var endDate = Ext.get("endDate").dom.value;
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_customerId : customerId,
		filter_EQS_reconciliationStatus : 3
	});
	maindateStore.reload({
		params : {
			start : 0
		}
	});
}



//var pageSize = 1;
var comboxPage = 15;
var privilege = 86;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiProblemreceivableAction!ralaList.action";
var saveReceivableStatementUrl = "fi/fiReceivabledetailAction!saveReceivableStatement.action";
var saveUrl = "fi/fiProblemreceivableAction!save.action";
var delUrl = "fi/fiProblemreceivableAction!delete.action";
var auditUrl= "fi/fiProblemreceivableAction!audit.action"; //核销问题账款;
var dateStore, customerStore, departStore, billingCycleStore;
//撤销问题账款登记URL
var problemreceivableUrl="fi/fiProblemreceivableAction!revocationRegister.action";

//撤销问题账款审核
var problemreceivableRegisterUrl="fi/fiProblemreceivableAction!problemreceivableRegister.action";


reconciliationStatusStore=new Ext.data.SimpleStore({ 
	 auteLoad : true,
     fields : [ 'reconciliationStatusId',  'reconciliationStatus'],  
     data   : [['0', '已作废'], ['1', '未对账'], ['2', '未审核'], ['3', '已审核']]  
 });
	 
var sm = new Ext.grid.CheckboxSelectionModel({
		width : 25,
		sortable : true
});

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
			name : 'customerName',// 客商名称
			mapping : 'customerName'
		}, {
			name : 'dno',// 配送单号
			mapping : 'dno'
		}, {
			name : 'problemType',// 问题账款类型
			mapping : 'problemType'
		}, {
			name : 'problemAmount',// 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'problemRemark',//问题账款备注
			mapping : 'problemRemark'
		}, {
			name : 'status',// 问题账款状态
			mapping : 'status'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'piece',// 欠款明细表：件数
			mapping : 'piece'
		}, {
			name : 'cusWeight',// 欠款明细表：计费重量
			mapping : 'cusWeight'
		}, {
			name : 'bulk',// 欠款明细表：体积
			mapping : 'bulk'
		}, {
			name : 'amount',// 欠款明细表：金额
			mapping : 'amount'
		}, {
			name : 'costType',// 欠款明细表：费用类型
			mapping : 'costType'
		}, {
			name : 'reconciliationNo',// 欠款明细表：对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'reconciliationStatus',// 欠款明细表：对账状态
			mapping : 'reconciliationStatus'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
		}, {
			name : 'departId',// 部门名称id
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
		}, {
			name : 'verificationAmount',
			mapping : 'verificationAmount'
		}, {
			name : 'verificationTime',
			mapping : 'verificationTime'
		}, {
			name : 'verificationStatus',
			mapping : 'verificationStatus'
		}, {
			name : 'verificationuser',
			mapping : 'verificationuser'
		},'verRemark'];

		var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : 'ID',
			dataIndex : 'id',
			width:60,
			sortable : true
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商',
			width:100,
			dataIndex : 'customerName'
		}, {
			header : '配送单号',
			width:70,
			dataIndex : 'dno'
		}, {
			header : '对账单号',
			width:55,
			dataIndex : 'reconciliationNo'
		}, {
			header : '对账状态',
			width:70,
			dataIndex : 'reconciliationStatus',
			renderer:function(v,metaData){
				if(v==0){
					return '已作废';
				}else if (v==1){
					return '未对账';
				}else if (v==2){
					return '已生成账单';
				}else if (v==3){
					return '账单已审核';
				}else if (v==3){
					return '已收银';
				}
			}
		}, {
			header : '问题账款类型',
			width:80,
			dataIndex : 'problemType'
		}, {
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		}, {
			header : '问题账款备注',
			width:120,
			dataIndex : 'problemRemark',
			hidden : true
		}, {
			header : '问题账款状态',
			width:80,
			dataIndex : 'status',
			renderer:function(v,metaData){
				if(v==0||v==null){
					return '已作废';
				}else if (v==1){
					return '已登记';
				}else if (v==2){
					return '已审核';
				}
			}
		},{
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
		},{
			header : '核销金额',
			width:80,
			dataIndex : 'verificationAmount'
		},{
			header : '核销状态',
			width:80,
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if (v==1){
					return '未核销';
				}else if (v==2){
					return '部分核销';
				}else if (v==3){
					return '已核销';
				}
			}
		},{
			header : '核销人',
			width:80,
			dataIndex : 'verificationuser'
		},{
			header : '核销时间',
			width:80,
			dataIndex : 'verificationTime'
		},{
			header : '核销备注',
			width:80,
			dataIndex : 'verRemark'
		}/*, {
			header : '件数',
			width:40,
			dataIndex : 'piece'
		}, {
			header : '重量',
			width:40,
			dataIndex : 'cusWeight'
		}, {
			header : '体积',
			width:40,
			dataIndex : 'bulk'
		}*/, {
			header : '应收金额',
			width:55,
			dataIndex : 'amount'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'departName'
		}, {
			header : '创建部门ID',
			dataIndex : 'departId',
			hidden : true
		}, {
			header : '创建人',
			width:55,
			dataIndex : 'createName'
		}, {
			header : '创建时间',
			dataIndex : 'createTime'
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

var tbar = [{
			text : '<b>撤销登记</b>',
			iconCls : 'save',
			id : 'revocationRegister',
			tooltip : '撤销登记',
			handler : revocationRegister
		}, '-',{
			text : '<b>审核</b>',
			iconCls : 'save',
			id : 'Verificationonfirm',
			tooltip : '审核',
			handler : problemreceivableAudit
		}, '-',{
			text : '<b>撤销审核</b>',
			iconCls : 'save',
			id : 'problemreceivableRegister',
			tooltip : '撤销审核',
			handler : problemreceivableRegister
		}, '-', {
			text:'<b>导出</b>',
			iconCls:'sort_down',
			handler:function() {
                parent.exportExl(Ext.getCmp('mainGrid'));
              }
        }/*,'-',{
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}*/, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}

/*
 * , '-', { xtype : 'textfield', blankText : '查询数据', id : 'searchContent' },
 * '-', { xtype : "combo", width : 100, triggerAction : 'all',
 * 
 * model : 'local', hiddenId : 'checkItems', hiddenName : 'checkItems', name :
 * 'checkItemstext', store : [['', '查询全部'], // ['EQS_id', 'ID'],
 * ['LIKES_cusName', '客商名称'], ['LIKES_departName', '部门名称']], emptyText : '选择类型',
 * forceSelection : true, listeners : { 'select' : function(combo, record,
 * index) { if (combo.getValue() == '') { Ext.getCmp("searchContent").disable();
 * Ext.getCmp("searchContent").show(); Ext.getCmp("searchContent").setValue(""); }
 * else { Ext.getCmp("searchContent").enable();
 * Ext.getCmp("searchContent").show(); } } } }, '-', { text : '<b>搜索</b>', id :
 * 'btn', iconCls : 'btnSearch', handler : searchCar } , /*{text : '<b>高级查询</b>',iconCls :
 * 'btnSearch', handler : searchCar }
 */
];
Ext.onReady(function() {
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
	var departStore = new Ext.data.Store({ 
            storeId:"departStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!ralaList.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'departName',type:'string'},
               {name:'departId', mapping:'rightDepartid',type:'string'}             
              ])    
    });

	// 查询主列表
	dateStore = new Ext.data.Store({
				storeId : "dateStore",
				baseParams:{limit:pageSize,privilege : privilege},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + gridSearchUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fields)
			});

	var mainGrid = new Ext.grid.GridPanel({
				// renderTo : Ext.getBody(),
				region : "center",
				id : 'mainGrid',
				height : Ext.lib.Dom.getViewHeight(),
				width : Ext.lib.Dom.getViewWidth(),
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					scrollOffset: 0,
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				ds : dateStore,
				// tbar : tbar,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : dateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});

	// 布局
	var mainpanel = new Ext.Panel({
				// title : "问题账款",
				id : 'view',
				el : 'mainGrid',
				labelAlign : 'left',
				height : Ext.lib.Dom.getViewHeight(),
				width : Ext.lib.Dom.getViewWidth(),
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [mainGrid]
			});

	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['单据日期从', {
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
					}, '-', '所属部门1', {
						xtype : 'combo',
						id:'departId',
						triggerAction : 'all',
						store : departStore,
						mode:'local',
						minChars : 1,
						listWidth:245,
						allowBlank : false,
						emptyText : "请选择部门名称",
						forceSelection : false,
						fieldLabel:'部门',
						editable : false,
						pageSize:500,
						displayField : 'departName',//显示值，与fields对应
						valueField : 'departId',//value值，与fields对应
						anchor : '95%',
						width : 80,
						enableKeyEvents:true,
						listeners : {
							afterRender:function(combo){
					 		departStore.load({
									params : {
										start : 0,
										limit : 500
									},callback :function(v){
										var flag=true;
										for(var i=0;i<departStore.getCount();i++){
											if(departStore.getAt(i).get("departId")==bussDepart){
												flag=false;
											}
										}
										if(flag){
											var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
											var record=new store();
											record.set("departId",bussDepart);
											record.set("departName",bussDepartName);
											departStore.add(record);		
										}
											combo.setValue(bussDepart);
									}
							});}
						}
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
						width : 120
					}, '-', '对账单号', {
						xtype : 'textfield',
						width : 45,
						id : 'reconciliationNo'
					}, '-', '配送单号', {
						xtype : 'textfield',
						width : 45,
						id : 'dno'
					}, '-', '流程号', {
						xtype : 'textfield',
						width : 45,
						id : 'workflowNo'
					}]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();


	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var revocationRegisterbtn = Ext.getCmp('revocationRegister');// 撤销登记
				var verificationonfirmtn = Ext.getCmp('Verificationonfirm');// 审核
				var problemreceivableRegisterbtn = Ext.getCmp("problemreceivableRegister"); // 撤销审核
				if (_record.length == 1) {
					if (revocationRegisterbtn&&_record[0].data.status==1) {
						revocationRegisterbtn.setDisabled(false);
					}
					if (verificationonfirmtn&&_record[0].data.status==1) {
						verificationonfirmtn.setDisabled(false);
					}
					if (problemreceivableRegisterbtn&&_record[0].data.status==2) {
						problemreceivableRegisterbtn.setDisabled(false);
					}
				} else if (_record.length > 1) {
					if (revocationRegisterbtn) {
						revocationRegisterbtn.setDisabled(true);
					}
					if (verificationonfirmtn) {
						verificationonfirmtn.setDisabled(true);
					}
					if (problemreceivableRegisterbtn) {
						problemreceivableRegisterbtn.setDisabled(true);
					}
				} else {
					if (revocationRegisterbtn) {
						revocationRegisterbtn.setDisabled(true);
					}
					if (verificationonfirmtn) {
						verificationonfirmtn.setDisabled(true);
					}
					if (problemreceivableRegisterbtn) {
						problemreceivableRegisterbtn.setDisabled(true);
					}
				}
			});
});

/**
 * 查询对账信息
 */
function searchmain() {
	dataReload();
}

function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商id
	var reconciliationNo=Ext.getCmp("reconciliationNo").getValue();//对账单号
	var dno=Ext.getCmp("dno").getValue();//配送单号
	var workflowNo=Ext.getCmp("workflowNo").getValue();//对账状态
	var departId=Ext.getCmp("departId").getValue();//对账状态
	
	Ext.apply(dateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_reconciliationNo : reconciliationNo,
		filter_EQS_dno : dno,
		filter_EQS_workflowNo : workflowNo,
		filter_EQS_customerId : customerId,
		filter_EQS_departId : departId,
		privilege:privilege
	});
	dateStore.reload({
		params : {
			start : 0
		}
	});
}

/**
 * 问题账款审核
 */

function problemreceivableAudit(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择单号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	
	
	//问题账款类型
	 var problemTypeStore = new Ext.data.Store({
					storeId : "problemTypeStore",
					proxy : new Ext.data.HttpProxy({
								url : sysPath
										+ "/sys/dictionaryAction!ralaList.action"
							}),
					baseParams : {
						filter_EQL_basDictionaryId : 162,
						privilege : 16
					},
					reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
										name : 'typeid',
										mapping : 'id'
									}, {
										name : 'type',
										mapping : 'typeName'
									}])
				});
	
		var form = new Ext.form.FormPanel({
				labelAlign : 'left',
				frame : true,
				//bodyStyle : 'padding:5px 5px 0',
				labelWidth : 100,
				width : 500,
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fields),
				labelAlign : "right",

				items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [{
													xtype : 'hidden',
													fieldLabel : 'id',
													name : 'id'
												}, {
													name : "problemStatus",
													fieldLabel : 'problemStatus',
													xtype : "hidden"
												}, {
													name : "ts",
													xtype : "hidden"
												}, {
													xtype : 'numberfield',
													fieldLabel : '配送单号',
													name : 'dno',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													fieldLabel : '对账单号',
													name : 'reconciliationNo',
													disabled : true, 
													anchor : '95%'
												}/*, {
													xtype : 'textfield',
													fieldLabel : '对账状态',
													name : 'reconciliationStatus',
													disabled : true, 
													anchor : '95%'
												}*/,{
													xtype : 'combo',
													typeAhead : true,
													forceSelection : true,
													queryParam : 'filter_LIKES_reconciliationStatus',
													minChars : 0,
													fieldLabel : '核销状态',
													store : reconciliationStatusStore,
													triggerAction : 'all',
													valueField : 'reconciliationStatusId',
													displayField : 'reconciliationStatus',
													hiddenName : 'reconciliationStatus',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '客商名称',
													disabled : true, 
													name : 'customerName',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '费用类型',
													disabled : true, 
													name : 'costType',
													anchor : '95%'
												},{
													xtype : 'numberfield',
													fieldLabel : '欠款金额',
													disabled : true, 
													name : 'amount',
													anchor : '95%'
												}]

									},{
										columnWidth : .5,
										layout : 'form',
										items : [{
													xtype : 'combo',
													typeAhead : true,
													forceSelection : true,
													queryParam : 'filter_LIKES_typeName',
													minChars : 0,
													fieldLabel : '问题账款类型<span style="color:red">*</span>',
													store : problemTypeStore,
													triggerAction : 'all',
													valueField : 'problemType',
													displayField : 'problemType',
													hiddenName : 'problemType',
													emptyText : "请选择问题账款类型",
													allowBlank : false,
													blankText : "问题账款类型不能为空！",
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '问题账款备注',
													maxLength:500,
													emptyText : "请输入问题账款备注",
													decimalPrecision :0,
													allowBlank:false,
													name : 'problemRemark',
													disabled : true, 
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '流程号',
													allowBlank : false,
													blankText : "流程不能为空！",
													name : 'workflowNo',
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													fieldLabel : '问题账款总金额',
													emptyText : "请输入问题账款总金额",
													allowBlank:false,
													id:'problemAmount',
													name : 'problemAmount',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '核销备注',
													maxLength:250,
													allowBlank : false,
													blankText : "核销备注不能为空！",
													name : 'verRemark',
													anchor : '95%'
												}]

									}]
						}]
			});
	form.load({
			url : sysPath + "/" + gridSearchUrl,
			params : {
				filter_EQL_id : _records[0].id,
				privilege : privilege
			}
		});	
	var win = new Ext.Window({
				title : '问题账款审核',
				width : 517,
				//height:350,
				closeAction : 'hide',
				plain : true,
				modal : true,
				items : form,
				buttonAlign : "center",
				buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
			Ext.Msg.confirm(alertTitle,'您确认要审核吗?',function(btnYes) {
			var problemAmount=_records[0].data.problemAmount;//问题账款金额
			var problemAmount1=Ext.getCmp('problemAmount').getValue();//审核问题账款金额
			if(problemAmount1>problemAmount){
				Ext.Msg.alert("友情提示","问题账款审核金额不能大于登记金额");
				return;
			}
			
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						if (form.getForm().isValid()) {
							this.disabled = true;// 只能点击一次
						
							form.getForm().submit({
								url : sysPath + '/' + auditUrl,
								params : {
									privilege : privilege
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
									win.hide(), Ext.Msg.alert("友情提示",
											action.result.msg, function() {
												dateStore.load();
											});
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
									} else {
										if (action.result.msg) {
											win.hide();
											Ext.Msg.alert("友情提示", action.result.msg);
		
										}
									}
								}
							});
						}
					}
				});
			}
		},{
			text : "关闭",
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


/**
 * 撤销问题账款登记
 * @return {Boolean}
 */
function revocationRegister(){
	var problemreceivableNos="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要撤销的问题账款！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "每次只能撤销一条问题账款数据！");
		return false;
	}
	Ext.Msg.confirm(alertTitle,'您确认要撤销吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + problemreceivableUrl,
						params : {
							id : _records[0].data.id,
							ts: _records[0].data.ts,
							privilege : privilege
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg);
								dataReload();
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
		
						}
					});
			}
		});
}

//撤销审核
function problemreceivableRegister(){
	var problemreceivableNos="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要撤销审核的问题账款！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "每次只能撤销一条问题账款数据！");
		return false;
	}

	Ext.Msg.confirm(alertTitle,'您确认要撤销审核吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + problemreceivableRegisterUrl,
						params : {
							id : _records[0].data.id,
							ts: _records[0].data.ts,
							privilege : privilege
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg);
								dataReload();
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
		
						}
					});
			}
		});
}
/**
 * 导出按钮事件
 */
function exportinfo(){
	Ext.Msg.alert("提示","正在开发中...");
}

/**
 * 打印按钮事件
 */
function printinfo(){
	Ext.Msg.alert("提示","正在开发中...");
}
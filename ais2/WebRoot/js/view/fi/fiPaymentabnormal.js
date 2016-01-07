
//var pageSize = 1;
var comboxPage = 15;
var privilege = 130;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiPaymentabnormalAction!ralaList.action";
var saveReceivableStatementUrl = "fi/fiPaymentabnormalAction!saveReceivableStatement.action";
var saveUrl = "fi/fiPaymentabnormalAction!save.action";
var delUrl = "fi/fiPaymentabnormalAction!delete.action";
var dateStore, customerStore, departStore, billingCycleStore,reviewStatusStore;

//撤销问题账款登记URL
var problemreceivableUrl="fi/fiPaymentabnormalAction!revocationException.action";

//撤销审核
var verificationRegisterUrl="fi/fiPaymentabnormalAction!verificationRegister.action";

//权限部门
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
			name : "fiPaymentId", // 收付款单号
			mapping : 'fiPaymentId'
		}, {
			name : "paymentType", // 收付类型(收款单/付款单)
			mapping : 'paymentType'
		}, {
			name : 'costType',// 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
			mapping : 'costType'
		}, {
			name : 'documentsType',// 单据大类:收入\成本\对账\预存款\代收货款
			mapping : 'documentsType'
		}, {
			name : 'documentsSmalltype',// 单据小类：配送单/对账单/配送单/预存款单
			mapping : 'documentsSmalltype'
		}, {
			name : 'documentsNo',// 单据小类对应的单号
			mapping : 'documentsNo'
		}, {
			name : 'penyType',//结算类型(现结、月结)
			mapping : 'penyType'
		}, {
			name : 'contacts',// 往来单位:没在客商档案中的客商，内部客户。
			mapping : 'contacts'
		}, {
			name : 'customerId',//客商id
			mapping : 'customerId'
		}, {
			name : 'customerName',// 客商表名称
			mapping : 'customerName'
		}, {
			name : 'sourceData',// 数据来源
			mapping : 'sourceData'
		}, {
			name : 'sourceNo',// 来源单号
			mapping : 'sourceNo'
		}, {
			name : 'typeName',// 异常类型
			mapping : 'typeName'
		}, {
			name : 'amount',//异常金额
			mapping : 'amount'
		}, {
			name : 'remark',// 备注
			mapping : 'remark'
		}, {
			name : 'verRemark',// 核销备注
			mapping : 'verRemark'
		}, {
			name : 'status',// 处理状态(状态(0:作废,1:未处理,2:已处理)
			mapping : 'status'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
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
		}];

		var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : 'id',
			dataIndex : 'id',
			width:80,
			sortable : true
		}, {
			header : '应收付单号',
			dataIndex : 'fiPaymentId'
		}, {
			header : '配送单号',
			width:80,
			dataIndex : 'documentsNo'
		}, {
			header : '异常类型',
			width:80,
			dataIndex : 'typeName'
		}, {
			header : '异常金额',
			width:80,
			dataIndex : 'amount'
		}, {
			header : '备注',
			width:120,
			dataIndex : 'remark'
		}, {
			header : '状态',
			width:80,
			dataIndex : 'status',
			renderer:function(v,metaData){
				if(v==0||v==null){
					v='已作废';
				}else if (v==1){
					v='已登记';
				}else if (v==2){
					v='已审核';
				}
				return v;
			}
		}, {
			header : '往来单位',
			width:80,
			dataIndex : 'contacts'
		}, {
			header : '客商ID',
			width:120,
			dataIndex : 'customerId',
			hidden : true,
			hideable:false
		}, {
			header : '客商',
			width:120,
			dataIndex : 'customerName'
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
					v='未核销';
				}else if (v==2){
					v='已核销';
				}
				return v;
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
			header : '审核备注',
			width:80,
			dataIndex : 'verRemark'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'departName'
		}, {
			header : '创建部门ID',
			dataIndex : 'departId',
			hidden : true,
			hideable:true
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
			id : 'abnormalRegister',
			tooltip : '撤销登记',
			handler : abnormalRegister
		}, '-',{
			text : '<b>审核</b>',
			iconCls : 'save',
			id : 'Verificationonfirm',
			tooltip : '审核',
			handler : saveReceivableStatement
		}, '-',{
			text : '<b>撤销审核</b>',
			iconCls : 'save',
			id : 'verificationRegister',
			tooltip : '撤销审核',
			handler : verificationRegister
		}, '-',{
			text:'<b>导出</b>',
			iconCls:'sort_down',
			handler:function() {
                parent.exportExl(Ext.getCmp('mainGrid'));
                }
        },'-',{
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}, '-', {
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
	//核销状态
	reviewStatusStore=new Ext.data.SimpleStore({ 
		 auteLoad : true,
	     fields : [ 'reviewStatusId',  'reviewStatus'],  
	     data   : [['1', '未核销'], ['2', '已核销']]  
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
			items : ['登记日期', {
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
					}, '-', '所属部门', {
						xtype : 'combo',
						id:'combodepartId',
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
						width : 100,
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
					}, '-', '核销状态', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_reviewStatus',
						minChars : 0,
						store : reviewStatusStore,
						mode : 'local',
						triggerAction : 'all',
						id : 'reviewStatusCombo',
						valueField : 'reviewStatusId',
						displayField : 'reviewStatus',
						emptyText : "请选择",
						width : 60,
						anchor : '95%'
					}, '-', '收付单号', {
						xtype : 'numberfield',
						width : 45,
						id : 'fiPaymentId'
					}, '-', '配送单号', {
						xtype : 'numberfield',
						width : 45,
						id : 'documentsNo'
					}]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var updatebtn = Ext.getCmp('basCarEdit');// 获得更新按钮
				var deletebtn = Ext.getCmp('basCarDelete');// 获得删除按钮
				var reviewbtn = Ext.getCmp("basReview"); // 获得审核按钮

				if (_record.length == 1) {
					if (updatebtn) {
						updatebtn.setDisabled(false);
					}
					if (deletebtn) {
						deletebtn.setDisabled(false);
					}
					if (deletebtn) {
						reviewbtn.setDisabled(false);
					}
				} else if (_record.length > 1) {
					if (updatebtn) {
						updatebtn.setDisabled(true);
					}
					if (deletebtn) {
						deletebtn.setDisabled(false);
					}
				} else {
					if (updatebtn) {
						updatebtn.setDisabled(true);
					}
					if (deletebtn) {
						deletebtn.setDisabled(true);
					}
					if (reviewbtn) {
						reviewbtn.setDisabled(true);
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
	var documentsNo = Ext.get("documentsNo").dom.value; // 单据号
	var fiPaymentId=Ext.get("fiPaymentId").dom.value;//应收付单号
	var reviewStatus=Ext.getCmp("reviewStatusCombo").getValue();//审核状态
	Ext.apply(dateStore.baseParams,{
					start : 0,
					filter_GED_createTime : stateDate,
					filter_LED_createTime : endDate,
					filter_EQL_documentsNo : documentsNo,
					filter_EQL_verificationStatus : reviewStatus,
					filter_EQL_fiPaymentId : fiPaymentId
			});

	dateStore.reload();
}

/**
 * 审核
 */
function saveReceivableStatement() {
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
	
	var gridSearchUrl = "fi/fiPaymentabnormalAction!ralaList.action";
	var saveUrl = "fi/fiPaymentabnormalAction!verificationException.action"; //核销异常到付款
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
													name : "ts",
													xtype : "hidden"
												}, {
													xtype : 'textfield',
													fieldLabel : '收付类型',
													name : 'paymentType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '费用类型',
													name : 'costType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '单据大类',
													name : 'documentsType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '单据小类',
													disabled : true, 
													name : 'documentsSmalltype',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '客商名称',
													disabled : true, 
													name : 'customerName',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '往来单位',
													disabled : true, 
													name : 'contacts',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '数据来源',
													disabled : true, 
													name : 'sourceData',
													anchor : '95%'
												}]

									},{
										columnWidth : .5,
										layout : 'form',
										items : [ {
													xtype : 'textfield',
													fieldLabel : '来源单号',
													disabled : true, 
													name : 'sourceNo',
													anchor : '95%'
												},{
													xtype : 'combo',
													typeAhead : true,
													forceSelection : true,
													queryParam : 'filter_LIKES_typeName',
													minChars : 0,
													fieldLabel : '异常类型<span style="color:red">*</span>',
													store : problemTypeStore,
													triggerAction : 'all',
													valueField : 'typeid',
													displayField : 'type',
													hiddenName : 'typeid',
													emptyText : "请选择异常类型",
													allowBlank : false,
													blankText : "异常类型不能为空！",
													disabled : true, 
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '异常备注',
													disabled : true, 
													name : 'remark',
													anchor : '95%'
												},{
													xtype : 'numberfield',
													fieldLabel : '异常金额',
													id : 'amount',
													name : 'amount',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '流程号',
													allowBlank : false,
													blankText : "流程不能为空！",
													name : 'workflowNo',
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
				title : '异常到付款审核',
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
			Ext.Msg.confirm(alertTitle,'您确认要核销吗?',function(btnYes) {
			var problemAmount=_records[0].data.amount;//问题账款金额
			var problemAmount1=Ext.getCmp('amount').getValue();//审核问题账款金额
			if(problemAmount1>problemAmount){
				Ext.Msg.alert("友情提示","异常到付款审核金额不能大于登记金额");
				return;
			}
			
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						if (form.getForm().isValid()) {
							this.disabled = true;// 只能点击一次
							form.getForm().submit({
								url : sysPath + '/' + saveUrl,
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
function abnormalRegister(){
	var problemreceivableNos="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择异常单单号！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一个异常单号！");
		return false;
	}

	Ext.Msg.confirm(alertTitle,'您确认要撤销吗?',function(btnYes) {
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
			});
}

//撤销审核
function verificationRegister(){
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
						url : sysPath + "/" + verificationRegisterUrl,
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
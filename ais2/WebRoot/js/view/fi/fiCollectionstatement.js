//var pageSize = 1;
var comboxPage = 15;
var privilege = 101;//对账单
var privilegetail=99
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var maingridSearchUrl = "fi/fiCollectionstatementAction!ralaList.action";
var saveUrl = "fi/fiCollectionstatementAction!save.action";
var delUrl = "fi/fiCollectionstatementAction!delete.action";
//对账单审核请求URL
var confirmReviewUrl="fi/fiCollectionstatementAction!confirmReview.action";

//对账单撤销审核请求URL
revocationReviewUrl="fi/fiCollectionstatementAction!revocationReview.action";

//账单明细查询请求URL
var griddetailUrl = "fi/fiCollectiondetailAction!ralaList.action";


var dateStore, customerStore, departStore,maindateStore;
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
			name : "amount", // 应收金额
			mapping : 'amount'
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
			name : 'createDept',// 创建部门
			mapping : 'createDept'
		}, {
			name : 'createDeptId',// 创建部门ID
			mapping : 'createDeptId'
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

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '对账单号',
			dataIndex : 'id'
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商',
			dataIndex : 'customerName'
		}, {
			header : '开始日期',
			dataIndex : 'stateDate'
		}, {
			header : '结束日期',
			dataIndex : 'endDate'
		}, {
			header : '对账状态',
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
			header : ' 金额',
			dataIndex : 'amount'
		}, {
			header : '对账员',
			dataIndex : 'reconciliationUser',
			hidden : true
		}, {
			header : '审核部门',
			dataIndex : 'reviewDept'
		}, {
			header : '审核人',
			dataIndex : 'reviewUser'
		}, {
			header : '审核时间',
			dataIndex : 'reviewDate'
		}, {
			header : ' 邮件发送次数',
			dataIndex : 'mailtoNum'
		}, {
			header : '打印次数',
			dataIndex : 'printNum'
		}, {
			header : '导出次数',
			dataIndex : 'exportNum'
		}, {
			header : '创建部门',
			dataIndex : 'createDept'
		}, {
			header : '创建部门ID',
			dataIndex : 'createDeptId',
			hidden : true
		}, {
			header : '创建人',
			dataIndex : 'createName',
			hidden : true
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

var tbar = [{
			text : '<b>对账审核</b>',
			iconCls : 'save',
			id : 'confirmReview',
			tooltip : '对账审核',
			handler : confirmReview
		}, '-', {
			text : '<b>撤销审核</b>',
			iconCls : 'table',
			id : 'revocationReview',
			tooltip : '撤销审核',
			handler : revocationReview
		}, '-', {
			text : '<b>账单明细</b>',
			iconCls : 'table',
			id : 'ReceivableDetailList',
			tooltip : '账单明细',
			handler : ReceivableDetailList
		}, '-', {
			text : '<b>作废</b>',
			iconCls : 'table',
			id : 'invalid',
			tooltip : '作废',
			handler : printinfo
		}, '-', {
			text : '<b>发邮件</b>',
			iconCls : 'table',
			id : 'mailAdd',
			tooltip : '发邮件',
			handler : printinfo
		}, '-', {
			text : '<b>导出</b>',
			iconCls : 'table',
			id : 'exportbtn1',
			tooltip : '导出',
			handler : printinfo
		}, '-', {
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}, '-', {
			text : '<b>图表</b>',
			iconCls : 'table',
			id : 'chat',
			tooltip : '图表',
			handler : chatinfo
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
	// 对账状态
	reconstatusStore = new Ext.data.Store({
				storeId : "reconstatusStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 42,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'reconciliationStatus',
									mapping : 'typeName'
								}])
			});
	reconstatusStore.load();

	
					
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
				baseParams : {limit:pageSize,privilege : privilege},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + maingridSearchUrl
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
					forceFit : true
				},
				autoScroll : true,
				autoExpandColumn : 1,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				ds : maindateStore,
				// tbar : tbar,
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
				// title : "供应商往来明细",
				id : 'view',
				el : 'mainPanel',
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
					}, '-', '审核状态', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : reconstatusStore,
						mode : 'local',
						triggerAction : 'all',
						id : 'reconciliationStatus',
						valueField : 'reconciliationStatus',
						displayField : 'reconciliationStatus',
						emptyText : "请选择",
						width : 80,
						anchor : '95%'
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

					}, '-', '对账单号', {
						xtype : 'textfield',
						width : 45,
						id : 'id'
					}, '-', '对账员', {
						xtype : 'textfield',
						width : 45,
						id : 'reconciliationUser'
					}, '-', {
						text : '<b>搜索</b>',
						id : 'btn',
						iconCls : 'btnSearch',
						handler : searchmain
					}]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	maindateStore.load();

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
	/*var customerText = Ext.get('comboCustomer').dom.value; // 客商文本值
	var customerId = Ext.getCmp("comboCustomer");// 客商
	var custprop = Ext.getCmp("comboCustprop");// 客商类型
	var billingCycle = Ext.getCmp("comboBillingCycle");// 结算周期
	if (customerText != '') {
		custprop.setValue('');
		billingCycle.setValue('');

	} else {
		if (!custprop.isValid()) {
			custprop.setValue('');
			return;
		} else if (!billingCycle.isValid()) {
			billingCycle.setValue('');
			return;
		}
	}*/
	dataReload();
}

/**
 * 查询对账单后刷新
 */
function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var reconciliationStatus = Ext.getCmp("reconciliationStatus").getValue(); // 审核状态
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var id = Ext.getCmp("id").getValue(); //对账单
	var reconciliationUser = Ext.get("reconciliationUser").dom.value; // 对账员
	maindateStore.reload({
				params : {
					start : 0,
					filter_GED_createTime : stateDate,
					filter_LED_createTime : endDate,
					filter_EQS_customerId : customerId,
					filter_EQS_reconciliationStatus : reconciliationStatus,
					filter_LIKES_reconciliationUser : reconciliationUser
				}
			});
}

/**
 * 对账单审核
 */
function confirmReview() {
	var reconciliationNos="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择对账单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		reconciliationNos=reconciliationNos+_records[i].id+',';
	}

	Ext.Ajax.request({
				url : sysPath + "/" + confirmReviewUrl,
				params : {
					reconciliationNos : reconciliationNos,
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


/**
 * 查看账单明细函数
 */
function ReceivableDetailList(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择对账单号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	
	var rowNum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 25,
				sortable : true
			});
		
	var sm = new Ext.grid.CheckboxSelectionModel();

var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "customerId", // 客商id
			mapping : 'customerId'
		}, {
			name : 'custprop',// 客商表：客商类型
			mapping : 'custprop'
		}, {
			name : 'departName',// 所属部门
			mapping : 'departName'
		}, {
			name : 'reconciliationStatus',// 对账状态
			mapping : 'reconciliationStatus'
		}, {
			name : 'customerName',// 客商名称
			mapping : 'customerName'
		}, {
			name : 'flightmainno',// 航空主单号
			mapping : 'flightmainno'
		}, {
			name : 'sourceData',// 数据来源
			mapping : 'sourceData'
		}, {
			name : 'sourceNo',// 来源单号
			mapping : 'sourceNo'
		}, {
			name : 'dno',// 配送单号
			mapping : 'dno'
		}, {
			name : 'piece',// 件数
			mapping : 'piece'
		}, {
			name : 'cusWeight',// 计费重量
			mapping : 'cusWeight'
		}, {
			name : 'bulk',// 体积
			mapping : 'bulk'
		}, {
			name : 'amount',// 金额
			mapping : 'amount'
		}, {
			name : 'billingCycle',// 代收货款欠款设置:对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationNo',// 对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'verificationStatus',// 核销状态
			mapping : 'verificationStatus'
		}, {
			name : 'verificationAmount',// 核销金额
			mapping : 'verificationAmount'
		}, {
			name : 'createDept',// 部门名称
			mapping : 'createDept'
		}, {
			name : 'createDeptId',// 部门名称id
			mapping : 'createDeptId'
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

var cm = new Ext.grid.ColumnModel([rowNum/*, sm(每行复选框)*/, {
			header : 'id',
			dataIndex : 'id',
			width:80,
			sortable : true
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '所属部门',
			width:100,
			dataIndex : 'departName'
		}, {
			header : '客商',
			width:100,
			dataIndex : 'customerName'
		}, {
			header : '客商类型',
			width:60,
			dataIndex : 'custprop'
		}, {
			header : '对账状态',
			width:60,
			renderer:function(v,metaData){
				if(v==0){
					v='已作废';
				}else if (v==1){
					v='未对账';
				}else if (v==2){
					v='未审核';
				}else if (v==3){
					v='已审核';
				}else if(v==4){
					v='已收银';
				}
				return v;
			},
			dataIndex : 'reconciliationStatus'
		}, {
			header : '航空主单号',
			width:80,
			dataIndex : 'flightmainno'
		}, {
			header : '配送单号',
			width:70,
			dataIndex : 'dno'
		}, {
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
		}, {
			header : '金额',
			width:50,
			dataIndex : 'amount'
		}, {
			header : '结算周期',
			width:60,
			dataIndex : 'billingCycle'
		}, {
			header : '对账单号',
			width:80,
			dataIndex : 'reconciliationNo'
		}, {
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		}, {
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
		}, {
			header : '核销状态',
			width:80,
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if(v==1){
					v='未核销';
				}else if (v==2){
					v='已审核';
				}
				return v;
			}
		}, {
			header : '核销金额',
			width:80,
			dataIndex : 'verificationAmount'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'createDept'
		}, {
			header : '创建部门ID',
			dataIndex : 'createDeptId',
			hidden : true
		}, {
			header : '创建人',
			dataIndex : 'createName',
			hidden : true
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
			hidden : true
		}]);

	var tbar = [{
			xtype : "combo",
			width : 100,
			triggerAction : 'all',
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],
					// ['EQS_id', 'ID'],
					['EQS_id', '应收单号'], ['LIKES_costType', '费用类型']],
			emptyText : '选择查询类型',
			forceSelection : true,
			listeners : {
				'select' : function(combo, record, index) {
					if (combo.getValue() == '') {
						Ext.getCmp("searchContent").disable();
						Ext.getCmp("searchContent").show();
						Ext.getCmp("searchContent").setValue("");
					} else {
						Ext.getCmp("searchContent").enable();
						Ext.getCmp("searchContent").show();

					}
				}
			}

		}, '-', {
			xtype : 'textfield',
			emptyText :'查询数据',
			blankText : '查询数据',
			id : 'searchContent'
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchtail}];

	// 查询主列表
	dateStore = new Ext.data.Store({
		storeId : "dateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + griddetailUrl,
					method:'POST'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
		});

	var Grid = new Ext.grid.GridPanel({
				region : "center",
				id : 'detailCenter',
				height : 330,
				width : 800,
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					scrollOffset: 0,
					//forceFit : true,
					autoScroll:true
				},
				autoScroll : true,
				autoExpandColumn : 1,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				tbar : tbar,
				ds : dateStore,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : dateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});
	
	//Grid渲染后请求数据。
	Grid.on('render',function(){
			dateStore.load({params : {
				start : 0,
				limit : pageSize,
				filter_EQL_reconciliationNo:_records[0].id,
				privilege:privilegetail
				}
			});
		});
	
	var win = new Ext.Window({
			title : '查看账单明细',
			width : 815,
			height:400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : Grid,
			buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					Grid.destroy();
				});
		win.show();
}



/**
 * 对账单明细快捷查询
 */
function searchtail() {
	dateStore.baseParams = {
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value
	}
	dataReloadtail();
}

/**
 * 对账单明细快捷查询后重新载入
 */
function dataReloadtail(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	dateStore.reload({
				params : {
					start : 0,
					privilege : privilegetail,
					filter_EQL_reconciliationNo:_records[0].id,
					limit : pageSize
				}
			});	
}


/**
 * 对账单撤销审核
 */
function revocationReview(){
	var reconciliationNos="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择对账单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		reconciliationNos=reconciliationNos+_records[i].id+',';
	}

	Ext.Ajax.request({
				url : sysPath + "/" + revocationReviewUrl,
				params : {
					reconciliationNos : reconciliationNos,
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

function chatinfo(){

var strXml="{success:true,flag:0,strXML:\"<set name='动力TEAM' value='1' /><set name='行政人事部' value='1' /><set name='研发中心' value='117' /><set name='运管部' value='38' /><set name='制造部' value='34' /><set name='资产财务部' value='1' />'\"}";
strXml="<chart caption='发布文件统计' subCaption='按发布单位统计' xAxisName='单位' yAxisName='文件数'  rotateYAxisName='1' showValues='1'  decimalPrecision='0' showNames='1'  baseFontSize='12' outCnvBaseFontSiz='20' numberSuffix=' 个'  pieSliceDepth='30' formatNumberScale='0'>"+strXml+"</chart>";  //构造FusionChart需要的XML格式数据，这里面属性，可参考官方文件。


	var fusionPanel=new Ext.ux.Chart.Fusion.Panel({
		collapsible:false,
		chartCfg:{
					id:'chart1',
					params:{
							flashVars:{
										debugMode:0,
										lang:'EN'
										}
							}
				},
		autoScroll:true,
		id:'chartpanel',
		chartURL:'chars/fcf/Column3D.swf',//定义图表显示类型，例如：直方，饼图等 dataXML:strXml,
		dataXML:strXml,
		width:800,
		height:310
	});


	var win = new Ext.Window({
			title : '查看图表',
			id:'showView',
			width : 815,
			height:400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : [fusionPanel],
			buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					//Grid.destroy();
				});
		win.show();
}
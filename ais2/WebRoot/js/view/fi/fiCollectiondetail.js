//var pageSize = 2;
var comboxPage = 15;
var privilege = 99;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiCollectiondetailAction!ralaList.action";
var saveUrl = "fi/fiCollectiondetailAction!save.action";
var delUrl = "fi/fiCollectiondetailAction!delete.action";

//生成对账单URL
var savefiCollectionstatementUrl = "fi/fiCollectiondetailAction!saveCollectionstatement.action";
var dateStore, customerStore, departStore, billingCycleStore;


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
			hidden : true,
			hideable:false
		}]);

var tbar = [{
			text : '<b>生成对账单</b>',
			iconCls : 'save',
			id : 'basCarAdd',
			tooltip : '生成对账单',
			handler : savefiCollectionstatement
		}, '-', {
			text : '<b>导出</b>',
			iconCls : 'table',
			id : 'exportbtn',
			tooltip : '导出',
			handler : exportinfo
		}, '-', {
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
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
									name : 'createDeptid',
									mapping : 'departId'
								}])
			});

	// 查询主列表
	dateStore = new Ext.data.Store({
				storeId : "dateStore",
				baseParams : {limit:pageSize,privilege : privilege},
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
				id : 'carCenter',
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
				autoExpandColumn : 1,
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
				// title : "供应商往来明细",
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
					}, '-', {
							xtype : 'combo',
						fieldLabel : '所属部门',
						typeAhead : true,
						forceSelection : true,
						minChars : 1,
						triggerAction : 'all',
						store : departStore,
						pageSize : comboxPage,
						queryParam : 'filter_LIKES_departName',
						id : 'combodepartStore',
						valueField : 'createDeptid',
						displayField : 'departName',
						name : 'departName',
						anchor : '95%',
						emptyText : "请选择所属部门"
					}, '-', '对账状态', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : reconstatusStore,
						mode : 'local',
						triggerAction : 'all',
						id : 'comboreconstatusStore',
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
								custprop.setDisabled(true);
							},
							keyup : function(textField, e) {
								var customerId = Ext.get('comboCustomer').dom.value;
								if (customerId == '') {
									var custprop = Ext.getCmp("comboCustprop"); // 客商类型
									var billingCycle = Ext
											.getCmp("comboBillingCycle"); // 客商类型
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
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	dateStore.load();

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
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商id
	var reconciliationStatus=Ext.getCmp("comboreconstatusStore").getValue();//对账状态
	var createDeptid=Ext.getCmp("combodepartStore").getValue();//所属部门
	dateStore.reload({
				params : {
					start : 0,
					privilege : privilege,
					filter_GED_createTime : stateDate,
					filter_LED_createTime : endDate,
					filter_EQL_createDeptid  : createDeptid,
					filter_EQL_customerId : customerId,
					filter_EQS_reconciliationStatus : reconciliationStatus
				}
			});
}

/**
 * 生成对账单事件
 */
function savefiCollectionstatement() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商id
	var createDeptid=Ext.getCmp("combodepartStore").getValue();//所属部门
	
	Ext.Ajax.request({
				url : sysPath + "/" + savefiCollectionstatementUrl,
				params : {
					stateDate : stateDate,
					endDate : endDate,
					customerId : customerId,
					createDeptid : createDeptid,
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
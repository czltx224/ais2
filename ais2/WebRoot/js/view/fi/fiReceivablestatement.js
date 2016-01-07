//var pageSize = 1;
var comboxPage = 15;
var privilege = 82;//对账单
var privilegetail=70
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var maingridSearchUrl = "fi/fiReceivablestatementAction!ralaList.action";
var saveReceivableStatementUrl = "fi/fiReceivabledetailAction!saveReceivableStatement.action";
var saveUrl = "fi/fiReceivabledetailAction!save.action";
var delUrl = "fi/fiReceivabledetailAction!delete.action";
//对账单审核请求URL
var confirmReviewUrl="fi/fiReceivablestatementAction!confirmReview.action";

//对账单撤销审核请求URL
var revocationReviewUrl="fi/fiReceivablestatementAction!revocationReview.action";

//对账单作废请求URL
var invalidUrl="fi/fiReceivablestatementAction!invalid.action";

//账单明细查询请求URL
var griddetailUrl = "fi/fiReceivabledetailAction!ralaList.action";

//踢出对账
var eliminateUrl="fi/fiReceivabledetailAction!eliminate.action"

//对账单增加往来明细
var receivabledetailAddUrl="fi/fiReceivabledetailAction!receivabledetailAdd.action";

//查询问题账款历史请求URL
var historyGridSearchUrl = "fi/fiProblemreceivableAction!ralaList.action";
var Historyprivilege=86
var HistorydateStore;

var saveInvoiceUrl="fi/fiInvoiceAction!save.action";
var privilegeInvoice = 66;

var customerId="";
var fiReceivablestatementId="";//对账单号
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
			name : "eliminationAmount", // 冲销金额
			mapping : 'eliminationAmount'
		}, {
			name : "problemAmount", // 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : "verificationStatus", // 核销金额
			mapping : 'verificationStatus'
		}, {
			name : "verificationAmount", // 核销金额
			mapping : 'verificationAmount'
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

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
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
			header : '已收付金额',
			width:120,
			dataIndex : 'verificationAmount'
		}, {
			header : '冲销金额',
			width:80,
			dataIndex : 'eliminationAmount'
		}, {
			header : '核销状态',
			width:120,
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if (v==1){
					v='<font color="#FF0000">未核销</font>';
				}else if (v==2){
					v='<font color="#FF0000">部分核销</font>';
				}else if (v==3){
					v='已核销';
				}
				return v;
			}
		}, {
			header : '问题账款金额',
			width:120,
			dataIndex : 'problemAmount'
		}, {
			header : '对账员',
			width:80,
			dataIndex : 'reconciliationUser',
			renderer:function(v,metaData){
				if (v=='null'){
					v='';
				}
				return v;
			}
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
			width:100,
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
		

var receivingmenu = new Ext.menu.Menu({
				    id: 'receivingmenu1',
				    items: [{
								text : '<b>查看账单明细</b>',
								iconCls : 'table',
								id : 'receivinginfo',
								tooltip : '查看账单明细',
								handler : ReceivableDetailList
							},{
								text : '<b>剔除账单明细</b>',
								iconCls : 'table',
								id : 'receivingAdd1',
								tooltip : '剔除账单明细',
								handler : ReceivableDetailList
							}, {
								text : '<b>增加账单明细</b>',
								iconCls : 'table',
								id : 'paymentAdd1',
								tooltip : '增加账单明细',
								handler : fiStatementAdd
							}]
				    });
				    
var receivingBtn=new Ext.Button({
			text : '<B>账单调整</B>',
			id : 'receivingBtn1',
			tooltip : '账单调整',
			iconCls : 'group',
			menu: receivingmenu
		});	
		
var menu = new Ext.menu.Menu({
				    id: 'receiptMenu',
				    items: [{
								text : '<b>登记</b>',
								iconCls : 'table',
								id : 'problemreceivableAdd',
								tooltip : '登记',
								handler : problemreceivableAdd
					    	},{
								text : '<b>查看历史</b>',
								iconCls : 'table',
								id : 'problemreceivableList',
								tooltip : '查看历史',
								handler : problemreceivableHistory
				    		}]
				    });
				    
 var probleBtn=new Ext.Button({
			text : '<B>问题账款</B>',
			id : 'probleBtn',
			tooltip : '问题账款',
			iconCls : 'group',
			menu: menu
		});	
   
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
		}, '-',probleBtn
		, '-',receivingBtn, /*{
			text : '<b>调整账单</b>',
			iconCls : 'table',
			id : 'ReceivableDetailList',
			tooltip : '调整账单',
			handler : ReceivableDetailList
		},*/ '-', {
			text : '<b>作废</b>',
			iconCls : 'table',
			id : 'invalid',
			tooltip : '作废',
			handler : invalid
		}, '-', {
			text : '<b>发票申请</b>',
			iconCls : 'table',
			id : 'InvoiceAdd',
			tooltip : '发票申请',
			handler : function() {
				var mainConter = Ext.getCmp('mainGrid');
				var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				InvoiceAdd(_records[0].data.id);
			}
		}, '-', {
			text : '<b>发邮件</b>',
			iconCls : 'table',
			id : 'mailAdd',
			tooltip : '发邮件',
			handler : printinfo
		}, '-', {text:'<b>导出对账单</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('mainGrid'));
        } }, '-', {
					text : '<b>导出账单明细</b>',
					id : 'outbtn',
					iconCls : 'sort_down',
					handler : function(){  //formex
						var mainConter = Ext.getCmp('mainGrid');
						var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
						if (_records.length != 1) {
							Ext.Msg.alert(alertTitle, "请选择你要导出的记录，一次只能导出一行记录");
							return false;
						}
						
						
						Ext.Msg.confirm(alertTitle,'您确定需要导出Excle吗？',function(btnYes) {
							var formex = new Ext.form.FormPanel({
								id : 'formex',
								frame : true,
								width : 100,
								fileUpload : true,
								cls : 'displaynone',
								hidden : true,
								items : [],
								buttons : []
							});
							formex.render(document.body);
	
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.getCmp('outbtn').disable();
								formex.getForm().doAction('submit', {
									url : sysPath+'/fi/fiReceivabledetailAction!exportExcel.action',
									method : 'post',
									params:{
										id:_records[0].id
									},
									success : function(form1, action) {
										Ext.getCmp('outbtn').enable();
									},
									failure : function(form1, action) {
									}
								});
								 setTimeout(function() {
								 	Ext.getCmp('outbtn').enable();
										}, 10000); 
							}
						});
					}
		},'-', {
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : function(){
				var mainConter = Ext.getCmp('mainGrid');
				var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能打印一行！");
					return false;
				}
				parent.print('9',{print_rid:_records[0].data.id});
			}
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
	// 对账状态
	/**reconstatusStore = new Ext.data.Store({
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
	reconstatusStore.load();**/
	   
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

	//权限部门
	departStore = new Ext.data.Store({ 
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
					scrollOffset: 0,
					autoScroll:true
				},
				autoScroll : true,
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
			items : ['单据日期', {
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
						displayField : 'typeName',
						emptyText : "请选择",
						width : 60,
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

					}, '-', '对账单', {
						xtype : 'textfield',
						width : 45,
						id : 'userid'
					}, '-', '对账员', {
						xtype : 'textfield',
						width : 45,
						id : 'reconciliationUser'
					}]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	//maindateStore.load();

	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var confirmReviewbtn = Ext.getCmp("confirmReview"); // 审核按钮
				var revocationReviewbtn=Ext.getCmp("revocationReview");//撤销审核按钮
				var problemreceivableAddbtm=Ext.getCmp("problemreceivableAdd");//问题账款登记按钮
				var InvoiceAddbtn=Ext.getCmp("InvoiceAdd");//发票申请按钮
				var invalidbtn=Ext.getCmp("invalid");//作废按钮
				
				if (_record.length == 1) {
					if(_record[0].data.reconciliationStatus==0){//作废
						confirmReviewbtn.setDisabled(true);
						revocationReviewbtn.setDisabled(true);
						problemreceivableAddbtm.setDisabled(true);
						InvoiceAddbtn.setDisabled(true);
						invalidbtn.setDisabled(true);
					}else if(_record[0].data.reconciliationStatus==2){//未审核
						confirmReviewbtn.setDisabled(false);
						revocationReviewbtn.setDisabled(true);
						problemreceivableAddbtm.setDisabled(true);
						InvoiceAddbtn.setDisabled(true);
						invalidbtn.setDisabled(false);
					}else if(_record[0].data.reconciliationStatus==3){//已审核
						confirmReviewbtn.setDisabled(true);
						revocationReviewbtn.setDisabled(false);
						problemreceivableAddbtm.setDisabled(false);
						InvoiceAddbtn.setDisabled(false);
						invalidbtn.setDisabled(true);
					}else if(_record[0].data.reconciliationStatus==4){//已收银
						confirmReviewbtn.setDisabled(false);
						revocationReviewbtn.setDisabled(false);
						problemreceivableAddbtm.setDisabled(false);
						InvoiceAddbtn.setDisabled(true);
						invalidbtn.setDisabled(false);
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

function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var reconciliationStatus = Ext.getCmp("reconciliationStatus").getValue(); // 审核状态
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var reconciliationUser = Ext.getCmp("reconciliationUser").getValue(); // 对账员
	var id=Ext.getCmp("userid").getValue(); // 对账员
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_departId : departId,
		filter_EQS_customerId : customerId,
		filter_EQS_id : id,
		filter_EQS_reconciliationStatus : reconciliationStatus,
		filter_LIKES_reconciliationUser : reconciliationUser
	});
	maindateStore.reload({
		params : {
			start : 0
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

	Ext.Msg.confirm(alertTitle,'您确认要审核吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
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
		});
}


/**
 * 问题账款登记
 */
function problemreceivableAdd(){
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
			name : 'problemAmount',// 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'problemStatus',// 问题账款状态
			mapping : 'problemStatus'
		}, {
			name : 'problemRemark',// 问题账款备注
			mapping : 'problemRemark'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'billingCycle',// 客商欠款设置:对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationUser',// 客商欠款设置:对账员
			mapping : 'reconciliationUser'
		}, {
			name : 'reconciliationNo',// 对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'costType',// 费用类型
			mapping : 'costType'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
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
	var cm = new Ext.grid.ColumnModel([rowNum,sm, {//, sm
			header : '应收单号',
			dataIndex : 'id',
			width:80,
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
			header : '客商类型',
			width:60,
			dataIndex : 'custprop'
		}, {
			header : '对账状态',
			width:60,
			dataIndex : 'reconciliationStatus'
		}, {
			header : '航空主单号',
			width:80,
			dataIndex : 'flightmainno'
		}, {
			header : '费用类型',
			width:60,
			dataIndex : 'costType'
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
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		}, {
			header : '问题账款状态',
			width:80,
			dataIndex : 'problemStatus',
			renderer:function(v,metaData){
			if(v==0||v==null){
					v='未登记';
				}else if (v==1){
					v='已登记';
				}else if (v==2){
					v='已审核';
				}
				return v;
			}
		}, {
			header : '问题账款备注',
			width:120,
			dataIndex : 'problemRemark',
			hidden : true
		}, {
			header : '结算周期',
			width:60,
			dataIndex : 'billingCycle'
		}, {
			header : '对账员',
			width:60,
			dataIndex : 'reconciliationUser'
		}, {
			header : '对账单号',
			width:80,
			dataIndex : 'reconciliationNo'
		}, {
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
		}, {
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		}, {
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
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
			text : '<b>登记问题账</b>',
			iconCls : 'save',
			id : 'basCarAdd',
			tooltip : '登记问题账',
			handler : saveProblemreceivable
		}, '-',{
			xtype : "combo",
			width : 100,
			triggerAction : 'all',
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],
					// ['EQS_id', 'ID'],
					['EQS_id', '应收单号'], ['LIKES_costType', '费用类型'],, ['EQS_dno', '配送单号']],
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
			id : 'btn4',
			iconCls : 'btnSearch',
			handler : problemdetailsearchtail
		}];

	// 查询主列表
	probledateStore = new Ext.data.Store({
		storeId : "probledateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + griddetailUrl,
					method:'POST'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
		});

	var problemdetailCenter = new Ext.grid.GridPanel({
				region : "center",
				id : 'problemdetailCenter',
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
				//autoExpandColumn : 1,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				tbar : tbar,
				ds : probledateStore,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : probledateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});
	
	//Grid渲染后请求数据。
	problemdetailCenter.on('render',function(){
			probledateStore.load({params : {
				start : 0,
				limit : pageSize,
				filter_EQL_reconciliationNo:_records[0].id,
				privilege:privilegetail
				}
			});
		});
	
	var win = new Ext.Window({
			title : '问题账款登记',
			width : 815,
			height:400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : problemdetailCenter,
			buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					problemdetailCenter.destroy();
				});
		win.show();
}

/**
 * 登记问题账-保存问题账
 */
function saveProblemreceivable(){	
	
	var mainConter = Ext.getCmp('problemdetailCenter');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择单号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	
	var mainConter = Ext.getCmp('problemdetailCenter');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var gridSearchUrl = "fi/fiReceivabledetailAction!ralaList.action";
	var saveUrl = "fi/fiReceivabledetailAction!saveProble.action"; //保存问题账款
	var privilege=70;
	//if(_records[0].get("problemStatus")!=""&&_records[0].get("problemStatus")!=null){
	//	Ext.Msg.alert("友情提示","此单号已登记过问题账款，不允许再登记！");
	///	return;
	//};
	
	
			//问题账款类型
		 var problemTypeStore = new Ext.data.Store({
						storeId : "billingCycleStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath
											+ "/sys/dictionaryAction!ralaList.action"
								}),
						baseParams : {
							filter_EQL_basDictionaryId : 64,
							privilege : 16
						},
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
											name : 'id'
										}, {
											name : 'problemType',
											mapping : 'typeName'
										}])
					});
		problemTypeStore.load();
		
		
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
				name : 'problemType',// 问题账款类型
				mapping : 'problemType'
			}, {
				name : 'problemAmount',// 问题账款金额
				mapping : 'problemAmount'
			}, {
				name : 'problemStatus',// 问题账款状态
				mapping : 'problemStatus'
			}, {
				name : 'problemRemark',// 问题账款备注
				mapping : 'problemRemark'
			}, {
				name : 'workflowNo',// 流程号
				mapping : 'workflowNo'
			}, {
				name : 'billingCycle',// 客商欠款设置:对账/结算周期
				mapping : 'billingCycle'
			}, {
				name : 'reconciliationUser',// 客商欠款设置:对账员
				mapping : 'reconciliationUser'
			}, {
				name : 'reconciliationNo',// 对账单号
				mapping : 'reconciliationNo'
			}, {
				name : 'costType',// 费用类型
				mapping : 'costType'
			}, {
				name : 'departName',// 部门名称
				mapping : 'departName'
			}, {
				name : 'createDeptId',// 部门名称id
				mapping : 'createDeptId'
			}, {
				name : 'verificationAmount',// 核销金额
				mapping : 'verificationAmount'
			}, {
				name : 'verificationStatus',// 核销状态
				mapping : 'verificationStatus'
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
												}, {
													xtype : 'textfield',
													fieldLabel : '对账状态',
													name : 'reconciliationStatus',
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
										items : [  {
													xtype : 'numberfield',
													fieldLabel : '核销金额',
													disabled : true, 
													name : 'verificationAmount',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '核销状态',
													disabled : true, 
													decimalPrecision :0,
													allowBlank:false,
													name : 'verificationStatus',
													anchor : '95%'
												},{
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
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													fieldLabel : '问题账款总金额',
													emptyText : "请输入问题账款总金额",
													allowBlank:false,
													name : 'problemAmount',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '问题账款备注',
													maxLength:500,
													emptyText : "请输入问题账款备注",
													decimalPrecision :0,
													allowBlank:false,
													name : 'problemRemark',
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
				title : '问题账款登记',
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
									var mainConter = Ext.getCmp('problemdetailCenter');
									var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
									var reconciliationNo=_records[0].get('reconciliationNo');
									probledateStore.reload({
												params : {
													start : 0,
													privilege : privilege,
													filter_EQL_reconciliationNo:reconciliationNo,
													limit : pageSize
												}
											});	

			
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									//alert(action.result.msg);
									win.hide();
									Ext.Msg.alert("友情提示", action.result.msg,
											function() {
												var mainConter = Ext.getCmp('problemdetailCenter');
												var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
												var reconciliationNo=_records[0].get('reconciliationNo');
												probledateStore.reload({
															params : {
																start : 0,
																privilege : privilege,
																filter_EQL_reconciliationNo:reconciliationNo,
																limit : pageSize
															}
														});	
											});

								}
							}
						}
					});
				}
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
 * 查看账单明细
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
			name : 'paymentType',// 收付类型
			mapping : 'paymentType'
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
			name : 'problemAmount',// 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'problemStatus',// 问题账款状态
			mapping : 'problemStatus'
		}, {
			name : 'problemRemark',// 问题账款备注
			mapping : 'problemRemark'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'billingCycle',// 客商欠款设置:对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationUser',// 客商欠款设置:对账员
			mapping : 'reconciliationUser'
		}, {
			name : 'reconciliationNo',// 对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'costType',// 费用类型
			mapping : 'costType'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
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
		

	var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '应收单号',
			dataIndex : 'id',
			width:80,
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
			header : '客商类型',
			width:60,
			dataIndex : 'custprop'
		}, {
			header : '收付类型',
			width:80,
			dataIndex : 'paymentType',
			renderer:function(v,metaData){
				if(v==0){
					v='作废';
				}else if (v==1){
					v='应收';
				}else if (v==2){
					v='应付';
				}
				return v;
			}
		}, {
			header : '对账状态',
			width:60,
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
				}else if(v==4){
					v='已收银';
				}
				return v;
			}
		}, {
			header : '航空主单号',
			width:80,
			dataIndex : 'flightmainno'
		}, {
			header : '费用类型',
			width:60,
			dataIndex : 'costType'
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
			header : '对账员',
			width:60,
			dataIndex : 'reconciliationUser'
		}, {
			header : '对账单号',
			width:80,
			dataIndex : 'reconciliationNo'
		}, {
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		}, {
			header : '问题账款状态',
			width:80,
			dataIndex : 'problemStatus'
		}, {
			header : '问题账款备注',
			width:120,
			dataIndex : 'problemRemark',
			hidden : true
		}, {
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
		}, {
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		}, {
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'departName'
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
					text : '<b>确认剔除</b>',
					iconCls : 'save',
					id : 'basCarAdd',
					tooltip : '确认剔除',
					handler : eliminate
				}/*, '-', {
					text : '<b>增加账单明细</b>',
					id : 'fiStatementAdd',
					iconCls : 'userEdit',
					tooltip : '增加账单明细',
					handler : fiStatementAdd
				}*/, '-', {
					text : '<b>导出</b>',
					id : 'outbtn',
					iconCls : 'sort_down',
					handler : function(){  //formex
						Ext.Msg.confirm(alertTitle,'您确定需要导出Excle吗？',function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
								Ext.getCmp('outbtn').disable();
								var formex = new Ext.form.FormPanel({
									id : 'formex',
									frame : true,
									width : 100,
									fileUpload : true,
									cls : 'displaynone',
									hidden : true,
									items : [],
									buttons : []
								});
								formex.render(document.body);
								
								formex.getForm().doAction('submit', {
									url : sysPath+'/fi/fiReceivabledetailAction!exportExcel.action',
									method : 'post',
									params:{
										id:_records[0].id
									},
									success : function(form1, action) {
									},
									failure : function(form1, action) {
									}
								});
								 setTimeout(function() {
								 	Ext.getCmp('outbtn').enable();
										}, 10000); 
							}
						});
					}
				}, '-', {
					text : '<b>搜索</b>',
					id : 'btn9',
					iconCls : 'btnSearch',
					handler : searchtail
				}];

	// 查询主列表
	dateStore = new Ext.data.Store({
		storeId : "dateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + griddetailUrl,
					method:'POST'
				}),
		baseParams:{
			limit : pageSize,
			filter_EQL_reconciliationNo:_records[0].id,
			privilege:privilegetail
		},
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
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				//tbar : tbar,
				ds : dateStore,
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
				//el : 'mainGrid',
				labelAlign : 'left',
				height : 330,
				width : 800,
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [Grid]
			});
			
	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['配送单号', {
						xtype : 'textfield',
						width : 80,
						id : 'dno1'
					}, '-','其它查询',{
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
				}]
		})
		tbarsearch.render(mainpanel.tbar);
	});
		
	//Grid渲染后请求数据。
	/*Grid.on('render',function(){
			dateStore.load();
		});*/
		
	
	
	var win = new Ext.Window({
			title : '查看账单明细',
			width : 815,
			height:400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : mainpanel,
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
 * 问题账款明细快捷查询
 */
function problemdetailsearchtail() {
	probledateStore.baseParams = {
		privilege : privilegetail,
		filter_EQL_reconciliationNo:_records[0].id,
		limit : pageSize,
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value
	}
	problemdetaildataReloadtail();
}

/**
 * 问题账款明细快捷查询后重新载入
 */
function problemdetaildataReloadtail(){
	probledateStore.reload({
				params : {
					start : 0,
					filter_LIKES_dno:dno
				}
			});	
}



/**
 * 对账单明细快捷查询
 */
function searchtail() {

	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var dno=Ext.getCmp("dno1").getValue();
	dateStore.baseParams = {
		privilege : privilegetail,
		filter_EQL_reconciliationNo:_records[0].id,
		limit : pageSize,
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
	var dno=Ext.getCmp("dno1").getValue();
	dateStore.reload({
				params : {
					start : 0,
					filter_LIKES_dno:dno
				}
			});	
}

/**
 * 问题账款历史快捷查询
 */
function searchHistory() {
	HistorydateStore.baseParams = {
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value
	}
	dataReloadHistory();
}


/**
 * 问题账款历史快捷查询后重新载入
 */
function dataReloadHistory(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	HistorydateStore.reload({
				params : {
					start : 0,
					privilege : Historyprivilege,
					filter_EQL_reconciliationNo:_records[0].id,
					limit : pageSize
				}
			});	
}



/**
 * 查看问题账款历史
 */
function problemreceivableHistory(){
	
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

		var cm = new Ext.grid.ColumnModel([rowNum,/* sm,*/ {
			header : 'id',
			dataIndex : 'id',
			width:20,
			hidden:true
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
			dataIndex : 'status'
		},{
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
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
			header : '应收金额',
			width:55,
			dataIndex : 'amount'
		}, {
			header : '费用类型',
			width:55,
			dataIndex : 'costType'
		}, {
			header : '对账单号',
			width:55,
			dataIndex : 'reconciliationNo'
		}, {
			header : '对账状态',
			width:55,
			dataIndex : 'reconciliationStatus'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'departName'
		}, {
			header : '创建部门ID',
			dataIndex : 'createDeptId',
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
					['EQS_id', '问题账款单号'], ['LIKES_dno', '配送单号']],
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
			id : 'btn7',
			iconCls : 'btnSearch',
			handler : searchHistory}];

	// 查询主列表
	HistorydateStore = new Ext.data.Store({
		storeId : "dateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + historyGridSearchUrl,
					method:'POST'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
		});

	var historyGrid = new Ext.grid.GridPanel({
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
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				tbar : tbar,
				ds : HistorydateStore,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : HistorydateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});
	
	//Grid渲染后请求数据。
	historyGrid.on('render',function(){
			HistorydateStore.load({params : {
				start : 0,
				limit : pageSize,
				filter_EQL_reconciliationNo:_records[0].id,
				privilege:Historyprivilege
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
			items : historyGrid,
			buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					historyGrid.destroy();
				});
		win.show();
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

	Ext.Msg.confirm(alertTitle,'您确认要撤销审核吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
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
		});
}


/**
 * 对账单作废
 */
function invalid() {
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


	Ext.Msg.confirm(alertTitle,'您确认要作废吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				Ext.Ajax.request({
					url : sysPath + "/" + invalidUrl,
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
		});
}

/**
 * 发票申请
 */
function InvoiceAdd(cid){
	
var fieldsInvoice = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "customerId", // 客商id
			mapping : 'customerId'
		}, {
			name : "cusName", // 客商名称
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
			name : "amount", // 应付金额
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
		
	var form = new Ext.form.FormPanel({
				labelAlign : 'left',
				frame : true,

				bodyStyle : 'padding:5px 5px 0',
				width : 600,
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fieldsInvoice),
				labelAlign : "right",

				items : [{
							layout : 'column',
							items : [{
										columnWidth : .5,
										layout : 'form',
										items : [
												{xtype:'hidden',id:'customerId',name:'customerId'},
												{xtype:'hidden',id:'departId',name:'departId'},
												{xtype:'hidden',id:'id',name:'sourceNo'},
												{xtype:'hidden',id:'sourceData',name:'sourceData',value:'对账单'},
												{
													xtype : 'hidden',
													fieldLabel : 'id',
													name : 'id'
												}, {
													name : "ts",
													xtype : "hidden"
												},{
													xtype : 'combo',
													fieldLabel: '客商名称<span style="color:red">*</span>',
													allowBlank : false,
													typeAhead:false,
													forceSelection : true,
													minChars : 1,
													triggerAction : 'all',
													store: customerStore,
													pageSize : comboxPage,
													queryParam : 'filter_LIKES_cusName',
													displayField : 'cusName',
													name:'cusName',
													blankText : "客商名称为空！",
													anchor : '95%',
													emptyText : "请选择客商名称",
													listeners : {
														'select' : function(combo, record, index) {
																Ext.getCmp('customerId').setValue(record.get("customerId"));
														}
													}
												}, {
													xtype : 'combo',
													id:'departName',
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
																			if (cid == null) {
																				Ext.getCmp('departId').setValue(departStore.getAt(i).get("departId"));
																			}
																		}
																	}
																	if(flag){
																		var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
																		var record=new store();
																		record.set("departId",bussDepart);
																		record.set("departName",bussDepartName);
																		departStore.add(record);	
																		Ext.getCmp('departId').setValue(bussDepart);	
																	}
																		combo.setValue(bussDepart);
																}
														});},'select' : function(combo, record, index) {
																Ext.getCmp('departId').setValue(record.get("departId"));
															}
													}
												},{
													xtype : 'combo',
													triggerAction : 'all',
													store : new Ext.data.SimpleStore({ // 填充的数据  
												                fields : [ 'invoiceType',  'invoiceType_text'],  
												                data   : [['运输发票', '运输发票'], ['服务发票', '服务发票'], ['定额发票', '定额发票']]  
												            }),  
													allowBlank : false,
													emptyText : "请选择发票类型",
													forceSelection : true,
													fieldLabel:'发票类型',
													editable : false,
													mode : "local",//获取本地的值
													displayField : 'invoiceType_text',//显示值，与fields对应
													valueField : 'invoiceType',//value值，与fields对应
													hiddenName : 'invoiceType',
													anchor : '95%'
												},{
													xtype : 'combo',
													triggerAction : 'all',
													store : new Ext.data.SimpleStore({ // 填充的数据  
												                fields : [ 'paymentType',  'paymentType_text'],  
												                data   : [['应收发票', '应收发票'], ['应付发票', '应付发票']]  
												            }),  
													allowBlank : false,
													emptyText : "请选择收付类型",
													forceSelection : true,
													fieldLabel:'收付类型',
													editable : false,
													mode : "local",//获取本地的值
													displayField : 'paymentType_text',//显示值，与fields对应
													valueField : 'paymentType',//value值，与fields对应
													hiddenName : 'paymentType',
													anchor : '95%'
												}, {
													xtype : 'numberfield',
													fieldLabel : '开票金额',
													decimalPrecision :0,
													allowNegative: false,//不允许负数
													allowBlank:false,
													blankText : "开票金额只能为数字！",
													name : 'amount',
													maxLength : 10,
													anchor : '95%'
												},{
													xtype : 'numberfield',
													fieldLabel : '税金',
													decimalPrecision :0,
													allowNegative: false,//不允许负数
													allowBlank:false,
													blankText : "税金只能为数字！",
													name : 'taxes',
													maxLength : 10,
													anchor : '95%'
												}]

									},{
										columnWidth : .5,
										layout : 'form',
										items : [ {
													xtype : 'textfield',
													fieldLabel : '单号',
													name : 'singleNumber',
													maxLength : 5,
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '申请人',
													name : 'applicant',
													maxLength : 10,
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '经手人',
													name : 'handled',
													maxLength : 10,
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '开票人',
													name : 'drawer',
													maxLength : 10,
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '备注',
													name : 'remark',
													maxLength : 200,
													anchor : '95%'
												}]

									}]
						}]
			});

	form.load({
				url : sysPath + "/" + maingridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});

	var win = new Ext.Window({
		title : '增加发票',
		width : 600,
		closeAction : 'hide',
		plain : true,
		resizable : false,

		modal : true,
		items : form,
		buttonAlign : "center",
		buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if (form.getForm().isValid()) {
					this.disabled = true;// 只能点击一次
					form.getForm().submit({
						url : sysPath + '/' + saveInvoiceUrl,
						params : {
							privilege : privilegeInvoice
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide(), Ext.Msg.alert("友情提示",
									action.result.msg, function() {
										dataReload();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert("友情提示", action.result.msg,
											function() {
												dataReload();
											});

								}
							}
						}
					});
				}
			}
		}, {

			text : "重置",
			iconCls : 'refresh',
			handler : function() {
					form.load({
								url : sysPath + "/" + maingridSearchUrl,
								params : {
									filter_EQL_id : cid,
									privilege : privilege
								}
							})

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
/**
 * 导出按钮事件
 */
function exportinfo(){
				var car = Ext.getCmp('mainGrid');
				parent.exportExl(car);
}

/**
 * 打印按钮事件
 */
function printinfo(){
	//Ext.Msg.alert("提示","正在开发中...");
}

/**
 * 剔除对账
 */
function eliminate(){
	var ids="";
	var mainConter = Ext.getCmp('detailCenter');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择往来明细单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		ids=ids+_records[i].id+',';
	}
	
	Ext.Msg.confirm(alertTitle,'您确认要剔除对账吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + eliminateUrl,
						params : {
							ids : ids,
							privilege : privilege
		
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg);
								searchtail();
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
 * 增加账单明细
 */
function fiStatementAdd(){
	
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择对账单号！");
		return false;
	} else {
		customerId=_records[0].data.customerId;
		fiReceivablestatementId=_records[0].data.id;
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
			name : 'paymentType',// 收付类型
			mapping : 'paymentType'
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
			name : 'problemAmount',// 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'problemStatus',// 问题账款状态
			mapping : 'problemStatus'
		}, {
			name : 'problemRemark',// 问题账款备注
			mapping : 'problemRemark'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'billingCycle',// 客商欠款设置:对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationUser',// 客商欠款设置:对账员
			mapping : 'reconciliationUser'
		}, {
			name : 'reconciliationNo',// 对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'costType',// 费用类型
			mapping : 'costType'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
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
		

	var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '应收单号',
			dataIndex : 'id',
			width:80,
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
			header : '客商类型',
			width:60,
			dataIndex : 'custprop'
		}, {
			header : '收付类型',
			width:80,
			dataIndex : 'paymentType',
			renderer:function(v,metaData){
				if(v==0){
					v='作废';
				}else if (v==1){
					v='应收';
				}else if (v==2){
					v='应付';
				}
				return v;
			}
		}, {
			header : '对账状态',
			width:60,
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
				}else if(v==4){
					v='已收银';
				}
				return v;
			}
		}, {
			header : '航空主单号',
			width:80,
			dataIndex : 'flightmainno'
		}, {
			header : '费用类型',
			width:60,
			dataIndex : 'costType'
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
			header : '对账员',
			width:60,
			dataIndex : 'reconciliationUser'
		}, {
			header : '对账单号',
			width:80,
			dataIndex : 'reconciliationNo'
		}, {
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		}, {
			header : '问题账款状态',
			width:80,
			dataIndex : 'problemStatus'
		}, {
			header : '问题账款备注',
			width:120,
			dataIndex : 'problemRemark',
			hidden : true
		}, {
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
		}, {
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		}, {
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
		}, {
			header : '创建部门',
			width:80,
			dataIndex : 'departName'
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
					text : '<b>确认添加</b>',
					iconCls : 'save',
					id : 'basCarAdd',
					tooltip : '确认添加',
					handler : receivabledetailAdd
				},'-', {
					text : '<b>搜索</b>',
					id : 'btn12',
					iconCls : 'btnSearch',
					handler : searchFiStatement
				}];

	// 查询主列表
	fiStatementDateStore = new Ext.data.Store({
		storeId : "fiStatementDateStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + griddetailUrl,
					method:'POST'
				}),
		baseParams:{
			limit : pageSize,
			filter_EQL_customerId:customerId,
			filter_EQL_reconciliationStatus:1,
			privilege:privilegetail
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
		});
	
	var Grid = new Ext.grid.GridPanel({
				region : "center",
				id : 'detailCenterAdd',
				height : 310,
				width : 760,
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				//tbar : tbar,
				ds : fiStatementDateStore,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : fiStatementDateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});
			
	// 布局
	var mainpanel = new Ext.Panel({
				// title : "供应商往来明细",
				id : 'view1',
				//el : 'mainGrid',
				labelAlign : 'left',
				height : 310,
				width : 780,
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [Grid]
			});
			
	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['配送单号', {
						xtype : 'textfield',
						width : 80,
						id : 'dno'
					}, '-','其它查询',{
					xtype : "combo",
					width : 80,
					triggerAction : 'all',
					model : 'local',
					hiddenId : 'checkFiStatementItems',
					hiddenName : 'checkFiStatementItems',
					name : 'checkItemstext',
					store : [['', '查询全部'],
							// ['EQS_id', 'ID'],
							['EQS_id', '应收单号'], ['LIKES_costType', '费用类型']],
					emptyText : '选择查询类型',
					forceSelection : true,
					listeners : {
						'select' : function(combo, record, index) {
							if (combo.getValue() == '') {
								Ext.getCmp("searchFiStatementContent").disable();
								Ext.getCmp("searchFiStatementContent").show();
								Ext.getCmp("searchFiStatementContent").setValue("");
							} else {
								Ext.getCmp("searchFiStatementContent").enable();
								Ext.getCmp("searchFiStatementContent").show();
		
							}
						}
					}
		
				}, '-', {
					xtype : 'textfield',
					width : 80,
					emptyText :'查询数据',
					blankText : '查询数据',
					id : 'searchFiStatementContent'
				}]
		})
		tbarsearch.render(mainpanel.tbar);
	});
		
	//Grid渲染后请求数据。
	/*Grid.on('render',function(){
			fiStatementDateStore.load();
		});*/
		
	
	
	var win = new Ext.Window({
			title : '客商未对账往来明细',
			width : 800,
			height:380,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : mainpanel,
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
function searchFiStatement() {
	fiStatementDateStore.baseParams = {
		privilege : privilegetail,
		filter_EQL_reconciliationStatus:1,
		filter_EQL_customerId:customerId,
		limit : pageSize,
		checkItems : Ext.get("checkFiStatementItems").dom.value,
		itemsValue : Ext.get("searchFiStatementContent").dom.value
	}
	dataReloadFiStatement();
}

/**
 * 对账单明细快捷查询后重新载入
 */
function dataReloadFiStatement(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var dno=Ext.getCmp("dno").getValue()
	fiStatementDateStore.reload({
				params : {
					start : 0,
					filter_LIKES_dno:dno
				}
			});	
}



/**
 * 确认添加
 */
function receivabledetailAdd(){
	var ids="";
	var mainConter = Ext.getCmp('detailCenterAdd');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择往来明细单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		ids=ids+_records[i].id+',';
	}
	Ext.Msg.confirm(alertTitle,'您确认要添加到对账单中吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + receivabledetailAddUrl,
						params : {
							ids : ids,
							fiReceivablestatementId:fiReceivablestatementId,
							privilege : privilege
		
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg);
								//searchtail();
								dataReload();
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
		
						}
					});
			}
		});
}
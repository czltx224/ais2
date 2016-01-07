
//var pageSize = 1;
var comboxPage = 15;
var privilege = 260;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiIncomeAccountAction!ralaList.action";
var saveReceivableStatementUrl = "fi/fiPaymentabnormalAction!saveReceivableStatement.action";
var saveUrl = "fi/fiIncomeAccountAction!save.action";
var delUrl = "fi/fiIncomeAccountAction!delete.action";
var dateStore, customerStore, departStore, billingCycleStore,reviewStatusStore;
var mainGrid;
//生成交账单URL
var addAccountSingleUrl="fi/fiIncomeAccountAction!addAccountSingle.action";

//作废交账单URL
var revocationUrl="fi/fiIncomeAccountAction!revocation.action";

//交账确认
var confirmAccountSingleUrl="fi/fiIncomeAccountAction!confirmAccountSingle.action";

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
			name : "batchNo", // 交账单号
			mapping : 'batchNo'
		}, {
			name : "typeName", //类型(收入\收银)
			mapping : 'typeName'
		}, {
			name : 'cpFee',//预付收入
			mapping : 'cpFee'
		}, {
			name : 'consigneeFee',//到付收入
			mapping : 'consigneeFee'
		}, {
			name : 'incomeAmount',//收入总额
			mapping : 'incomeAmount'
		}, {
			name : 'cashAmount',//现金
			mapping : 'cashAmount'
		}, {
			name : 'posAmount',//POS
			mapping : 'posAmount'
		}, {
			name : 'checkAmount',//支票
			mapping : 'checkAmount'
		}, {
			name : 'intecollectionAmount',//内部代收
			mapping : 'intecollectionAmount'
		}, {
			name : 'eliminationAmount',//到付冲销
			mapping : 'eliminationAmount'
		}, {
			name : 'collectionAmount',//代收货款
			mapping : 'collectionAmount'
		}, {
			name : 'consigneeAmount',//到付款
			mapping : 'consigneeAmount'
		}, {
			name : 'accountStatus',//交账状态(0：未交账,1：已交账)
			mapping : 'accountStatus'
		}, {
			name : 'status',//状态(0：作废,1：正常)
			mapping : 'status'
		}, {
			name : 'accountData',//日期
			mapping : 'accountData'
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
		}];

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '所属部门',
			width:80,
			dataIndex : 'departName'
		},{
			header : '交账日期',
			width:80,
			dataIndex : 'accountData'
		}, {
			header : '交账单号',
			width:60,
			dataIndex : 'batchNo'
		}, {
			header : '类型',
			width:40,
			dataIndex : 'typeName',
			renderer:function(v,metaData){
				if(v=="收入"){
					v='<font color="#FF0000">'+v+'</font>';
				}else if(v=="收银"){
					v='<font color="#0000FF">'+v+'</font>';
				}
				return v;
			}
		}, {
			header : '预付收入',
			width:60,
			dataIndex : 'cpFee',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收入"){
					value='<font color="#FF0000">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : '到付收入',
			width:60,
			dataIndex : 'consigneeFee',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收入"){
					value='<font color="#FF0000">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : '收入总额',
			width:60,
			dataIndex : 'incomeAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收入"){
					value='<font color="#FF0000">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : '现金',
			width:60,
			dataIndex : 'cashAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : 'POS',
			width:60,
			dataIndex : 'posAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : '支票',
			width:60,
			dataIndex : 'checkAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		},{
			header : '到付冲销',
			width:60,
			dataIndex : 'eliminationAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		}, {
			header : '内部代收',
			width:60,
			dataIndex : 'intecollectionAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		},{
			header : '代收货款',
			width:60,
			dataIndex : 'collectionAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		},{
			header : '到付款',
			width:60,
			dataIndex : 'consigneeAmount',
			renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
				if(record.data.typeName=="收银"){
					value='<font color="#0000FF">'+value+'</font>';
				}else{
					value="";
				}
				return value;
			}
		},{
			header : '交账状态',
			width:60,
			dataIndex : 'accountStatus',
			renderer:function(v,metaData){
				if (v==0){
					v='未交账';
				}else if (v==1){
					v='已交账';
				}
				return v;
			}
		},{
			header : '状态',
			width:60,
			dataIndex : 'status',
			renderer:function(v,metaData){
				if (v==0){
					v='作废';
				}else if (v==1){
					v='正常';
				}
				return v;
			}
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

var continentGroupRow=[
  	{ header:'',align: 'center',colspan: 6},
  	{ header:'总收入',align: 'center',colspan: 3},
  	{ header:'收银类型',align: 'center',colspan: 4},
  	{ header:'业务类型',align: 'center',colspan: 3},
  	{ header:'',align: 'center',colspan: 8}
  ];
var group = new Ext.ux.grid.ColumnHeaderGroup({
      rows: [continentGroupRow]
  });
    
    
var tbar = [{
			text : '<b>生成交账单</b>',
			iconCls : 'save',
			id : 'addAccountSingle',
			tooltip : '生成交账单',
			handler : addAccountSingle
		}, '-',{
			text : '<b>作废</b>',
			iconCls : 'save',
			id : 'revocation',
			tooltip : '作废',
			handler : revocation
		}, '-',{
			text : '<b>查看交账明细</b>',
			iconCls : 'save',
			id : 'Verificationonfirm',
			tooltip : '查看交账明细',
			handler : showIncomeAccountInfo
		}, '-',{
			text : '<b>交账确认</b>',
			iconCls : 'save',
			id : 'confirmAccountSingle',
			tooltip : '交账确认',
			handler : confirmAccountSingle
		}, '-',{
			text:'<b>导出</b>',
			iconCls:'sort_down',
			handler:function() {
                parent.exportExl(mainGrid);
                }
        },'-',{
			text : '<b>打印</b>',
			iconCls : 'printBtn',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}];
		
Ext.onReady(function() {
	//核销状态
	reviewStatusStore=new Ext.data.SimpleStore({ 
		 auteLoad : true,
	     fields : [ 'reviewStatusId',  'reviewStatus'],  
	     data   : [['0', '未交账'], ['1', '已交账']]  
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

	mainGrid = new Ext.grid.GridPanel({
				// renderTo : Ext.getBody(),
				region : "center",
				id : 'mainGrid',
				height : Ext.lib.Dom.getViewHeight(),
				width : Ext.lib.Dom.getViewWidth(),
				plugins: group,
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
			items : ['交账日期', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date(),//.add(Date.DAY, -7),
						hiddenId : 'accountData',
						hiddenName : 'accountData',
						id : 'accountData'
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
					}, '-', '交账状态', {
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
					}, '-', '交账单号', {
						xtype : 'numberfield',
						width : 45,
						id : 'batchNo'
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
	var accountData = Ext.getCmp("accountData").getValue().format('Y-m-d');
	var batchNo=Ext.get("batchNo").dom.value;//应收付单号
	var reviewStatus=Ext.getCmp("reviewStatusCombo").getValue();//审核状态
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	Ext.apply(dateStore.baseParams,{
					start : 0,
					filter_EQD_accountData : accountData,
					filter_EQL_departId : departId,
					filter_EQL_accountStatus : reviewStatus,
					filter_EQL_batchNo : batchNo
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
	Ext.Msg.alert("系统消息", "开发中！");
}

/**
 * 生成交账单
 * @return
 */
function addAccountSingle(){
	var toData=new Date().format('Y-m-d');
	var accountData=Ext.getCmp('accountData').getValue().format('Y-m-d');
	var departId=Ext.getCmp('combodepartId').getValue();
	if(accountData>=toData){
		Ext.Msg.alert("系统消息","只允许生成今天之前的交账报表!");
		return;
	}
	Ext.Msg.confirm(alertTitle,'您确认要生成<font color="#FF0000">'+accountData+'</font>的交账报表吗?',function(btnYes) {
		Ext.Ajax.request({
					url : sysPath + "/" + addAccountSingleUrl,
					params : {
						accountData : accountData,
						departId : departId,
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


/**
 * 撤销交账单
 * @return
 */
function revocation(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var batchNo="";
	if (_records.length >= 1) {
		for(var i=0;i<_records.length;i++){
			if(batchNo==""){
				batchNo=_records[i].data.batchNo;
			}else{
				if(batchNo!=_records[i].data.batchNo){
					Ext.Msg.alert("系统消息", "不同交账单号请分开作废！");
					return;
				}
			}
			
		}
	}else{
		Ext.Msg.alert("系统消息", "请选择交账单号！");
		return;
	}
	
	Ext.Msg.confirm(alertTitle,'您确认要作废交账单<font color="#FF0000">'+batchNo+'</font>的收入、收银报表吗?',function(btnYes) {
		Ext.Ajax.request({
			url : sysPath + "/" + revocationUrl,
			params : {
				batchNo : batchNo,
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


//交账审核
function confirmAccountSingle(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var batchNo="";
	if (_records.length >= 1) {
		for(var i=0;i<_records.length;i++){
			if(batchNo==""){
				batchNo=_records[i].data.batchNo;
			}else{
				if(batchNo!=_records[i].data.batchNo){
					Ext.Msg.alert("系统消息", "不同交账单号请分开审核！");
					return;
				}
			}
			
		}
	}else{
		Ext.Msg.alert("系统消息", "请选择交账单号！");
		return;
	}
	
	Ext.Msg.confirm(alertTitle,'您确认要审核交账单<font color="#FF0000">'+batchNo+'</font>的收入、收银报表吗?',function(btnYes) {
		Ext.Ajax.request({
			url : sysPath + "/" + confirmAccountSingleUrl,
			params : {
				batchNo : batchNo,
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

/**
*查看交账明细
*/
function showIncomeAccountInfo(){
	var accountId="";//交账单号
	var mainGrid = Ext.getCmp('mainGrid');
	var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行

	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择您要修改的行！");
		return false;
	} else{
		for(var i=0;i<_records.length;i++){
			if(accountId==""){
				accountId=_records[i].data.batchNo;
			}else{
				if(accountId!=_records[i].data.batchNo){
					Ext.Msg.alert("系统消息", "不同交账单号请分开查询！");
					return false;
				}
			}
		}
	}

//收入明细开始
	// 费用类型
	var costTypeStore = new Ext.data.Store({
				storeId : "reconstatusStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 222,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'costType',
									mapping : 'typeName'
								}])
			});
	var userIds,departIds;
	var incomeprivilege = 118;
	var incomeSearchUrl="fi/fiIncomeVoAction!findFiIncomeDetail.action";
	var incomesm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var incomerowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
		var incomefields = [{
					name : "id",
					mapping : 'id'
				}, {
					name : "dno",
					mapping : 'dno'
				}, {
					name : "sourceData", 
					mapping : 'sourceData'
				}, {
					name : "sourceNo",
					mapping : 'sourceNo'
				}, {
					name : "amountType",
					mapping : 'amountType'
				}, {
					name : "amount",
					mapping : 'amount'
				}, {
					name : "customerId",
					mapping : 'customerId'
				}, {
					name : "customerName",
					mapping : 'customerName'
				}, {
					name : "accountId",
					mapping : 'accountId'
				}, {
					name : "accountStatus",
					mapping : 'accountStatus'
				}, {
					name : "departId",
					mapping : 'departId'
				}, {
					name : "incomeDepart",
					mapping : 'incomeDepart'
				}, {
					name : "departName",
					mapping : 'departName'
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
		
		var incomecm = new Ext.grid.ColumnModel([rowNum, sm, {
					header : '配送单号',
					dataIndex : 'dno',
					width:65
				}, {
					header : '费用类型',
					dataIndex : 'amountType',
					width:65
				}, {
					header : '金额',
					dataIndex : 'amount',
					width:65
				}, {
					header : '数据来源',
					dataIndex : 'sourceData',
					width:65
				}, {
					header : '来源单号',
					dataIndex : 'sourceNo',
					width:65
				},{
					header : '交账单号',
					dataIndex : 'accountId',
					width:65
				}, {
					header : '交账状态',
					dataIndex : 'accountStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='未交账';
						}else if (v==1){
							v='已交账';
						}
						return v;
					},
					width:65
				},  {
					header : '收入部门',
					dataIndex : 'incomeDepart',
					width:65
				}, {
					header : '创建部门',
					dataIndex : 'departName',
					width:65
				}, {
					header : '客商ID',
					hidden : true,
					dataIndex : 'customerId',
					width:65
				}, {
					header : '客商',
					dataIndex : 'customerName',
					width:65
				}, {
					header : '创建部门',
					dataIndex : 'departName',
					width:65
				}, {
					header : '创建部门ID',
					dataIndex : 'departId',
					hidden : true
				}, {
					header : '创建人',
					dataIndex : 'createName',
					width:65
				}, {
					header : '创建时间',
					dataIndex : 'createTime',
					width:80
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
				
var incomeTbar = ['费用类型',
			{
				xtype : 'combo',
				typeAhead : true,
				forceSelection : true,
				queryParam : 'filter_LIKES_typeName',
				minChars : 0,
				store : costTypeStore,
				triggerAction : 'all',
				id : 'comCostType',
				valueField : 'costType',
				displayField : 'costType',
				emptyText : "请选择",
				width : 80,
				anchor : '95%'
			},'-','配送单号',{
				xtype:'numberfield',
	 	        id : 'comDno',
	 	        width : 60,
		        name : 'dno'
	        }, '-', {
				text : '<b>搜索</b>',
				id : 'incomebtn',
				iconCls : 'btnSearch',
				handler :  function() {
                		var costType=Ext.getCmp('comCostType').getValue();
                		var dno=Ext.getCmp('comDno').getValue();
						incomedateStore.baseParams = {
							costType:costType,
							dno:dno,
							accountId:accountId,
							limit : pageSize
						}
						incomedateStore.reload();	
					}
			}, '-', {
			text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('incomeGrid'));}
			}];
				
		// 查询主列表
		var incomedateStore = new Ext.data.Store({
			storeId : "incomedateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + incomeSearchUrl,
						method:'POST'
					}),
			baseParams:{
				//filter_EQL_fiincomeiercollectionId:cid,
				privilege:incomeprivilege,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, incomefields)
			});
		var incomeGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'incomeGrid',
					height : 450,
					width : 733,
					viewConfig : {
						columnsText : "显示的列",
						sortAscText : "升序",
						sortDescText : "降序",
						scrollOffset: 0,
						//forceFit : true,
						autoScroll:true
					},
					autoScroll : true,
					frame : false,
					loadMask : true,
					sm : incomesm,
					cm : incomecm,
					tbar : incomeTbar,
					ds : incomedateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : incomedateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//收入明细结束


//收银明细开始
	var paidprivilege = 118;
	var paidSearchUrl="fi/fiPaidAction!findAccountSingle.action";
	var paidsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var paidrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
		var paidfields = [{
							name : "id",
							mapping : 'ID'
						},{
							name : "paidId",
							mapping : 'PAID_ID'
						}, {
							name : "paymentType", // 收付类型(收款单/付款单)
							mapping : 'PAYMENT_TYPE'
						}, {
							name : "costType", // 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
							mapping : 'COST_TYPE'
						}, {
							name : "documentsType", // 单据大类
							mapping : 'DOCUMENTS_TYPE'
						}, {
							name : "documentsSmalltype", // 单据小类
							mapping : 'DOCUMENTS_SMALLTYPE'
						}, {
							name : "documentsNo", //单据小类
							mapping : 'DOCUMENTS_NO'
						}, {
							name : "penyJenis", // 结算方式
							mapping : 'PENY_JENIS'
						}, {
							name : "settlementAmount", // 已收付金额
							mapping : 'SETTLEMENT_AMOUNT'
						}, {
							name : 'createName',
							mapping : 'CREATE_NAME'
						}, {
							name : 'createTime',
							mapping : 'CREATE_TIME'
						}];
		
		var paidcm = new Ext.grid.ColumnModel([rowNum, sm, {
						header : '序号',
						dataIndex : 'id',
						width:65
					}, {
						header : '实收付单号',
						dataIndex : 'paidId',
						width:65
					}, {
						header : '收付类型',
						dataIndex : 'paymentType',
						renderer:function(v,metaData){
							if(v==0){
								v='异常';
							}else if (v==1){
								v='收款单';
							}else if (v==2){
								v='付款单';
							}
							return v;
						},
						width:65
					}, {
						header : '实收金额',
						dataIndex : 'settlementAmount',
						width:65
					}, {
						header : '费用类型',
						dataIndex : 'costType',
						width:65
					}, {
						header : '单据大类',
						dataIndex : 'documentsType',
						width:65
					}, {
						header : '单据小类',
						dataIndex : 'documentsSmalltype',
						width:65
					}, {
						header : '单据号',
						dataIndex : 'documentsNo',
						width:65
					}, {
						header : '结算方式',
						dataIndex : 'penyJenis',
						width:65
					}, {
						header : '创建人',
						dataIndex : 'createName',
						width:65
					}, {
						header : '创建时间',
						dataIndex : 'createTime',
						dateFormat:'Y-m-d H:i:s'
					}]);
				
var paidTbar = ['费用类型',
			{
				xtype : 'combo',
				typeAhead : true,
				forceSelection : true,
				queryParam : 'filter_LIKES_typeName',
				minChars : 0,
				store : costTypeStore,
				triggerAction : 'all',
				id : 'comCostType',
				valueField : 'costType',
				displayField : 'costType',
				emptyText : "请选择",
				width : 80,
				anchor : '95%'
			},'-','配送单号',{
				xtype:'numberfield',
	 	        id : 'comDno',
	 	        width : 60,
		        name : 'dno'
	        }, '-', {
				text : '<b>搜索</b>',
				id : 'paidbtn',
				iconCls : 'btnSearch',
				handler :  function() {
                		var costType=Ext.getCmp('comCostType').getValue();
                		var dno=Ext.getCmp('comDno').getValue();
						paiddateStore.baseParams = {
							costType:costType,
							dno:dno,
							accountId:accountId,
							limit : pageSize
						}
						paiddateStore.reload();	
					}
			}, '-', {
			text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('paidGrid'));}
			}];
				
		// 查询主列表
		var paiddateStore = new Ext.data.Store({
			storeId : "paiddateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + paidSearchUrl,
						method:'POST'
					}),
			baseParams:{
				//filter_EQL_fipaidiercollectionId:cid,
				privilege:paidprivilege,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'resultMap',
						totalProperty : 'totalCount'
					}, paidfields)
			});
		var paidGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'paidGrid',
					height : 450,
					width : 733,
					viewConfig : {
						columnsText : "显示的列",
						sortAscText : "升序",
						sortDescText : "降序",
						scrollOffset: 0,
						//forceFit : true,
						autoScroll:true
					},
					autoScroll : true,
					frame : false,
					loadMask : true,
					sm : paidsm,
					cm : paidcm,
					tbar : paidTbar,
					ds : paiddateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : paiddateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//收银明细结束

var tabs = new Ext.TabPanel({
    width:733,
    activeTab: 0,
    frame:true,
    //plain:true,
    defaults:{autoHeight: true},
    items:[{
	    		title: '收入明细',
	    		id:'1',
	        	items:[incomeGrid]
        	},{
	    		title: '收银明细',
	    		id:'2',
	        	items:[paidGrid]
        	}
    	]
    });
    
	var win = new Ext.Window({
		title : '交账明细',
		width : 750,
		closeAction : 'hide',
		plain : true,
		resizable : false,

		modal : true,
		items : tabs,
		buttonAlign : "center",
		buttons : [{
			text : "关闭",
			handler : function() {
				win.close();
			}
		}]

	})

	win.on('hide', function() {
				tabs.destroy();
			});
	
	win.show();
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
	var _records = Ext.getCmp('mainGrid').getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length==0){
		Ext.Msg.alert(alertTitle,"请选择相应的记录打印");
	}else{
		var did="";
		for(var i=0;i<_records.length;i++){
			if(i==0){
				did=_records[i].data.batchNo;
			}else{
				if(did!=_records[i].data.batchNo){
					Ext.Msg.alert(alertTitle,"请选择同一交账单号的记录进行打印");
					return ;
				}
			}
		}
	
		parent.print('19',{print_accountId:did});
	}
}
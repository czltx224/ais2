
//var pageSize = 1;
var comboxPage = 15;
var privilege = 261;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiRepaymentAction!ralaList.action";
var saveUrl = "fi/fiIncomeAccountAction!save.action";
var delUrl = "fi/fiIncomeAccountAction!delete.action";
var dateStore, customerStore, departStore, billingCycleStore,reviewStatusStore;

//交账确认
var confirmAccountSingleUrl="fi/fiRepaymentAction!confirmAccountSingle.action";

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
			name : "customerId", //客商ID
			mapping : 'customerId'
		}, {
			name : 'customerName',//客商
			mapping : 'customerName'
		}, {
			name : 'accountsBalance',//欠款金额
			mapping : 'accountsBalance'
		}, {
			name : 'eliminationAccounts',//还款金额
			mapping : 'eliminationAccounts'
		}, {
			name : 'eliminationCope',//代收货款金额
			mapping : 'eliminationCope'
		}, {
			name : 'problemAmount',//问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'sourceData',//数据来源
			mapping : 'sourceData'
		}, {
			name : 'sourceNo',//来源单号
			mapping : 'sourceNo'
		}, {
			name : 'accountStatus',//交账状态(0：未交账,1：已交账)
			mapping : 'accountStatus'
		}, {
			name : 'status',//状态(0：作废,1：正常)
			mapping : 'status'
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
		},'batchNo',{
			name : 'ts',
			mapping : 'ts'
		}];

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '所属部门',
			width:80,
			dataIndex : 'departName'
		},{
			header : '交账单号',
			width:80,
			dataIndex : 'batchNo'
		},{
			header : '客商',
			width:80,
			dataIndex : 'customerName'
		},{
			header : '欠款金额',
			width:80,
			dataIndex : 'accountsBalance'
		},{
			header : '还款金额',
			width:80,
			dataIndex : 'eliminationAccounts'
		},{
			header : '代收货款金额',
			width:80,
			dataIndex : 'eliminationCope'
		},{
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		},{
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		},{
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
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

    
var tbar = [{
			text : '<b>交账确认</b>',
			iconCls : 'save',
			id : 'confirmAccountSingle',
			tooltip : '交账确认',
			handler : confirmAccountSingle
		}, '-',{
			text:'<b>导出</b>',
			iconCls:'sort_down',
			handler:function() {
                parent.exportExl(Ext.getCmp('mainGrid'));
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
			items : ['日期', {
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
						width : 70,
						listWidth:245,
						anchor : '95%'
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
					}, '-', '来源单号', {
						xtype : 'numberfield',
						width : 40,
						id : 'combosourceNo'
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
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var reviewStatus=Ext.getCmp("reviewStatusCombo").getValue();//审核状态
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	var sourceNo = Ext.getCmp("combosourceNo").getValue(); // 所属部门
	Ext.apply(dateStore.baseParams,{
					start : 0,
					filter_GED_accountData : stateDate,
					filter_LED_accountData : endDate,
					filter_EQL_departId : departId,
					filter_EQS_customerId : customerId,
					filter_EQL_accountStatus : reviewStatus,
					filter_EQL_sourceNo : sourceNo
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


//交账审核
function confirmAccountSingle(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	var ids="";
	if (_records.length >= 1) {
		for(var i=0;i<_records.length;i++){
				ids=ids+_records[i].data.id+",";
		}
	}else{
		Ext.Msg.alert("系统消息", "请选择交账单号！");
		return;
	}
	
	Ext.Msg.confirm(alertTitle,'您确认交账确认选中数据吗?',function(btnYes) {
		Ext.Ajax.request({
			url : sysPath + "/" + confirmAccountSingleUrl,
			params : {
				ids : ids,
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
	if (_records.length != 1) {
		Ext.Msg.alert(alertTitle,"请选择对应的数据或者选择一条数据再打印（同一交账单号数据会同时打印）");
		return ;
	}else{
		if(_records[0].data.accountStatus!=1){
			Ext.Msg.alert(alertTitle,"未交账的数据不能打印");
			return ;
		}else{
			parent.print('20',{print_batchNo:_records[0].data.batchNo});
		}
	}
}
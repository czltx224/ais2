//var pageSize = 2;
var comboxPage = 15;
var privilege = 60;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var reconciliationUserGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 对账员
var gridSearchUrl = "fi/fiArrearsetAcion!ralaList.action";
var saveUrl = "fi/fiArrearsetAcion!save.action";
var delUrl = "fi/fiArrearsetAcion!delete.action";
var dateStore, customerStore, departStore, reconciliationUserStore, billingCycleStore;

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


var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "customerId", // 客商id
			mapping : 'customerId'
		}, {
			name : "deptId",// 部门id
			mapping : 'deptId'
		}, {
			name : 'contractType',// 合同类型
			mapping : 'contractType'
		}, {
			name : 'contractStartTime',// /合同开始时间
			mapping : 'contractStartTime'
		}, {
			name : 'contractEndTime',// 合同结束时间
			mapping : 'contractEndTime'
		}, {
			name : 'contractNo',// 合同号
			mapping : 'contractNo'
		}, {
			name : 'contractExtensionDay',// 合同到期延期(天)
			mapping : 'contractExtensionDay'
		}, {
			name : 'contractExtensionDate',// 合同延期日期
			mapping : 'contractExtensionDate'
		}, {
			name : 'billingCycle',// 对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'openingBalance',// 期初余额
			mapping : 'openingBalance'
		}, {
			name : 'credit',// 信用额度
			mapping : 'credit'
		}, {
			name : 'limit',// 信用期限
			mapping : 'limit'
		}, {
			name : 'extensionLimit',// 延期信用期限
			mapping : 'extensionLimit'
		}, {
			name : 'extensionEndDate',// 延期信用截止日期
			mapping : 'extensionEndDate'
		}, {
			name : 'additionalAmount',// 追加额度
			mapping : 'additionalAmount'
		}, {
			name : 'additionalAmountDate',// 追加额度截止日期
			mapping : 'additionalAmountDate'
		}, {
			name : 'ispaytoarrears',// 能否到付转欠款
			mapping : 'ispaytoarrears'
		}, {
			name : 'isautoreconciliation',// 能否自动对账
			mapping : 'isautoreconciliation'
		}, {
			name : 'reconciliationUser',// 对账员
			mapping : 'reconciliationUser'
		}, {
			name : 'remark',// 备注
			mapping : 'remark'
		}, {
			name : 'departName',// 部门名：部门名称
			mapping : 'departName'
		}, {
			name : 'cusName',// 客商表：客商名称
			mapping : 'cusName'
		}, {
			name : 'custprop',// 客商表：客商类型
			mapping : 'custprop'
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

var tbar = ['&nbsp;',{
			text : '<b>导出</b>',
			iconCls : 'table',
			id : 'exportbtn',
			tooltip : '导出',
			handler : function(){
				parent.exportExl(Ext.getCmp('carCenter'));			
			}
		},'-',{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'basCarAdd',
			tooltip : '新增客商欠款设置',
			handler : function() {
				saveCar(null);
			}
		}, '-', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'basCarEdit',
			disabled : true,
			tooltip : '修改客商欠款设置',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				saveCar(_records[0].data.id);
			}
		}, '-', {
			text : '<b>删除</b>',
			iconCls : 'userDelete',
			id : 'basCarDelete',
			disabled : true,
			tooltip : '删除客商欠款设置',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要操作的行！");
					return false;
				}
				Ext.Msg.confirm("系统提示", "确定要删除所选的记录吗？", function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'
									|| btnYes == true) {
								var ids = "";
								for (var i = 0; i < _records.length; i++) {

									ids = ids + _records[i].data.id + ',';
								}
								Ext.Ajax.request({
											url : sysPath + '/' + delUrl,
											params : {
												ids : ids,
												privilege : privilege
											},
											success : function(resp) {
												var respText = Ext.util.JSON
														.decode(resp.responseText);
												Ext.Msg.alert("友情提示", "删除成功!");
												dataReload();
											}
										});
							}
						});
			}
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
					}, '-', {
			xtype : 'textfield',
			blankText : '查询数据',
			id : 'searchContent'
		}, '-', {
			xtype : "combo",
			width : 100,
			triggerAction : 'all',

			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],
					// ['EQS_id', 'ID'],
					['LIKES_cusName', '客商名称'], ['LIKES_departName', '部门名称']],
			emptyText : '选择类型',
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
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchCar
		}/*
			 * , /*{text : '<b>高级查询</b>',iconCls : 'btnSearch', handler :
			 * searchCar }
			 */
];
Ext.onReady(function() {

			// 结算周期
			billingCycleStore = new Ext.data.Store({
						storeId : "billingCycleStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath
											+ "/sys/dictionaryAction!ralaList.action"
								}),
						baseParams : {
							filter_EQL_basDictionaryId : 24,
							privilege : 16
						},
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
											name : 'id'
										}, {
											name : 'billingCycle',
											mapping : 'typeName'
										}])
					});
			billingCycleStore.load();

			// 欠款客商列表
			customerStore = new Ext.data.Store({
						storeId : "customerStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath + "/" + customerGridSearchUrl
								}),
					            baseParams:{
					            	filter_EQL_status:1,
					            	filter_EQS_settlement:'月结'
					            },
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


			// 对账员
			reconciliationUserStore = new Ext.data.Store({
						storeId : "reconciliationUserStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath + "/"
											+ reconciliationUserGridSearchUrl
								}),
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
									name : 'reconciliationUser',
									mapping : 'userName'
								}/*
									 * , {name:'reconciliationUserid',mapping
									 * :'id'}
									 */
								])
					});

			// 合同类型
			contractType = new Ext.data.SimpleStore({
						auteLoad : true, // 此处设置为自动加载
						data : [['合同', '合同'], ['协议', '协议']],
						fields : ["contractType", "contractName"]
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

			var sm = new Ext.grid.CheckboxSelectionModel();
			var rowNum = new Ext.grid.RowNumberer({
						header : '序号',
						width : 25,
						sortable : true
					});
			var carGrid = new Ext.grid.GridPanel({
						renderTo : Ext.getBody(),
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
						//autoExpandColumn : 1,
						frame : true,
						loadMask : true,
						sm : sm,
						cm : new Ext.grid.ColumnModel([rowNum, sm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								}, {
									header : '客商',
									sortable : true,
									dataIndex : 'cusName',
									width : 100,
									sortable : true
								}, {
									header : '客商类型',
									dataIndex : 'custprop',
									width : 60
								}, {
									header : '所属部门',
									dataIndex : 'departName',
									width : 60
								}, {
									header : '合同类型',
									dataIndex : 'contractType',
									width : 60
								}, {
									header : '合同开始时间',
									dataIndex : 'contractStartTime',
									width : 60
								}, {
									header : '合同结束时间',
									dataIndex : 'contractEndTime',
									width : 60
								}, {
									header : '合同编号',
									dataIndex : 'contractNo',
									width : 60
								}, {
									header : '合同到期延期(天)',
									dataIndex : 'contractExtensionDay',
									width : 60,
									hidden : true
								}, {
									header : '合同延期日期',
									dataIndex : 'contractExtensionDate',
									width : 60,
									hidden : true
								}, {
									header : '对账/结算周期',
									dataIndex : 'billingCycle',
									width : 60
								},{
									header : '期初余额',
									dataIndex : 'openingBalance',
									width : 60
								}, {
									header : '信用额度',
									dataIndex : 'credit',
									width : 60
								}, {
									header : '信用期限',
									dataIndex : 'limit',
									width : 60
								}, {
									header : '延期信用期限',
									dataIndex : 'extensionLimit',
									width : 60
								}, {
									header : '延期信用期限截止日期',
									dataIndex : 'extensionEndDate',
									format:'Y-m-d',
									width : 80
								}, {
									header : '追加额度',
									dataIndex : 'additionalAmount',
									width : 60,
									hidden : true
								}, {
									header : '追加额度截止日期',
									dataIndex : 'additionalAmountDate',
									format:'Y-m-d',
									width : 80
								}, {
									header : '能否到付转欠款',
									dataIndex : 'ispaytoarrears',
									width : 60
								}, {
									header : '能否自动对账',
									dataIndex : 'isautoreconciliation',
									width : 60
								}, {
									header : '对账员',
									dataIndex : 'reconciliationUser',
									width : 60
								}, {
									header : '备注',
									dataIndex : 'remark',
									width : 60
								}, {
									header : '创建人',
									dataIndex : 'createName',
									width : 60,
									hidden : true
								}, {
									header : '创建时间',
									dataIndex : 'createTime',
									width : 60,
									hidden : true
								}, {
									header : '修改人',
									dataIndex : 'updateName',
									width : 60,
									hidden : true
								}, {
									header : '修改时间',
									dataIndex : 'updateTime',
									width : 60,
									hidden : true
								}, {
									header : '时间戳',
									dataIndex : 'ts',
									width : 60,
									sortable : true,
									hidden : true,
									hideable:false
								}]),
						store : dateStore,

						tbar : tbar,
						bbar : new Ext.PagingToolbar({
									pageSize : pageSize,
									store : dateStore,
									displayInfo : true,
									displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
									emptyMsg : "没有记录信息显示"
								})
					});
			carGrid.render();
			//dateStore.load();

			carGrid.on('click', function() {
						var _record = carGrid.getSelectionModel()
								.getSelections();// 获得所有选中的行
						var updatebtn = Ext.getCmp('basCarEdit');// 获得更新按钮
						var deletebtn = Ext.getCmp('basCarDelete');// 获得删除按钮
						if (_record.length == 1) {
							if (updatebtn) {
								updatebtn.setDisabled(false);
							}
							if (deletebtn) {
								deletebtn.setDisabled(false);
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
						}

					});
		});

function searchCar() {
	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + gridSearchUrl
			});

	dateStore.baseParams = {
		start : 0,
		limit:pageSize,
		privilege : privilege,
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value
	}
	var userrolebtn = Ext.getCmp('basCarAdd');
	var updatebtn = Ext.getCmp('basCarEdit');

	userrolebtn.setDisabled(false);
	updatebtn.setDisabled(false);

	dataReload();

}

function saveCar(cid) {
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,

		bodyStyle : 'padding:5px 5px 0',
		width : 700,
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
							id : 'customerId',
							name : 'customerId'
						},
						// {xtype:'hidden',id:'reconciliationUserid',name:'reconciliationUserid'},
						{
							xtype : 'hidden',
							id : 'departId',
							name : 'departId'
						}, {
							xtype : 'hidden',
							fieldLabel : 'id',
							name : 'id'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'combo',
							fieldLabel : '客商名称<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'cusName',
							name : 'cusName',
							blankText : "客商名称为空！",
							anchor : '95%',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('customerId').setValue(record
											.get("customerId"));
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
						fieldLabel:'客商部门',
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
												//if (cid == null) {
													Ext.getCmp('departId').setValue(departStore.getAt(i).get("departId"));
												//}
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
					}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : contractType,
							allowBlank : false,
							emptyText : "请选择合同类型",
							forceSelection : true,
							fieldLabel : '合同类型',
							editable : false,
							mode : "local",// 获取本地的值
							displayField : 'contractName',// 显示值，与fields对应
							valueField : 'contractType',// value值，与fields对应
							name : 'contractType',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '合同编号',
							name : 'contractNo',
							maxLength : 30,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '合同开始',
							format : 'Y-m-d',
							name : 'contractStartTime',
							blankText : "合同开始时间日期不能为空！",
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '合同结束',
							name : 'contractEndTime',
							format : 'Y-m-d',
							blankText : "合同结束日期不能为空！",
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '合同到期延期',
							decimalPrecision : 0,
							allowNegative : false,// 不允许负数
							// allowBlank:false,
							blankText : "合同到期延期只能为数字！",
							name : 'contractExtensionDay',
							maxLength : 10,
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '期初余额',
							decimalPrecision : 0,
							id:'openingBalance',
							name : 'openingBalance',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 5,
							anchor : '95%'
						}]

			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'numberfield',
							fieldLabel : '信用额度<span style="color:red">*</span>',
							name : 'credit',
							allowBlank : false,
							blankText : "信用额度不能为空！",
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '追加额度',
							name : 'additionalAmount',
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '追加额度截止日期',
							format : 'Y-m-d',
							name : 'additionalAmountDate',
							blankText : "追加额度截止日期不能为空！",
							anchor : '95%'
						},{
							xtype : 'combo',
							id:'billingCyclecombo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '对账/结算周期<span style="color:red">*</span>',
							store : billingCycleStore,
							triggerAction : 'all',
							editable:false,
							valueField : 'billingCycle',
							displayField : 'billingCycle',
							hiddenName : 'billingCycle',
							emptyText : "请选择对账/结算周期",
							allowBlank : false,
							blankText : "对账/结算周期不能为空！",
							anchor : '95%',
							listeners : {
								'select' : function(combo, record, index) {
									var billingCycle=Ext.getCmp('billingCyclecombo').getValue();
									if(billingCycle==7){
										Ext.getCmp('limit').setValue(15);
									}else if(billingCycle==15){
										Ext.getCmp('limit').setValue(20);
									}else if(billingCycle==30){
										Ext.getCmp('limit').setValue(50);
									}
								}
							}
						}, {
							xtype : 'numberfield',
							id:'limit',
							fieldLabel : '信用期限<span style="color:red">*</span>',
							name : 'limit',
							allowBlank : false,
							blankText : "信用期限不能为空！",
							anchor : '95%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '延期信用期限',
							name : 'extensionLimit',
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '延期期限截止日期',
							id : 'extensionEndDate',
							name : 'extensionEndDate',
							format : 'Y-m-d',
							blankText : "延期期限截止日期不能为空！",
							anchor : '95%'
						}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({ // 填充的数据
								fields : ['ispaytoarrears',
										'ispaytoarrears_text'],
								data : [['能', '能'], ['不能', '不能']]
							}),
							allowBlank : false,
							emptyText : "能否到付转欠款",
							forceSelection : true,
							fieldLabel : '能否到付转欠款',
							editable : false,
							mode : "local",// 获取本地的值
							displayField : 'ispaytoarrears_text',// 显示值，与fields对应
							valueField : 'ispaytoarrears',// value值，与fields对应
							hiddenName : 'ispaytoarrears',
							anchor : '95%'
						}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : new Ext.data.SimpleStore({ // 填充的数据
								fields : ['isautoreconciliation',
										'isautoreconciliation_text'],
								data : [['能', '能'], ['不能', '不能']]
							}),
							allowBlank : false,
							emptyText : "能否自动对账不能为空",
							forceSelection : true,
							fieldLabel : '能否自动对账',
							editable : false,
							mode : "local",// 获取本地的值
							displayField : 'isautoreconciliation_text',// 显示值，与fields对应
							valueField : 'isautoreconciliation',// value值，与fields对应
							hiddenName : 'isautoreconciliation',
							anchor : '95%'
						}, {
							xtype : 'combo',
							fieldLabel : '对账员<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : reconciliationUserStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_userName',
							displayField : 'reconciliationUser',
							name : 'reconciliationUser',
							blankText : "对账员不能为空！",
							anchor : '95%',
							emptyText : "请选择对账员"/*
												 * , listeners : { 'select' :
												 * function(combo, record,
												 * index) {
												 * alert(record.get("reconciliationUserid"));
												 * Ext.getCmp('reconciliationUserid').setValue(record.get("reconciliationUserid")); } }
												 */
						}]

			}]
		}]
	});

	carTitle = '添加客商欠款设置';
	if (cid != null) {
		carTitle = '修改客商欠款设置';
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
		//Ext.getCmp('openingBalance').disable();
	}

	var win = new Ext.Window({
		title : carTitle,
		width : 700,
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
						url : sysPath + '/' + saveUrl,
						params : {
							privilege : privilege
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

				if (cid == null) {

					form.getForm().reset();

				}
				if (cid != null) {
					carTitle = '修改客商欠款设置';
					form.load({
								url : sysPath + "/" + gridSearchUrl,
								params : {
									filter_EQL_id : cid,
									privilege : privilege
								}
							})

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
function dataReload() {
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	Ext.apply(dateStore.baseParams, {
		filter_EQS_departId : departId,
		privilege:privilege
	});
	
	dateStore.reload();
}

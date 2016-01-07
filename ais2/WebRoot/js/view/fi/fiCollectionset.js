//var pageSize = 2;
var comboxPage = 15;
var privilege = 98;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var reconciliationUserGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 对账员
var gridSearchUrl = "fi/fiCollectionsetAction!ralaList.action";
var saveUrl = "fi/fiCollectionsetAction!save.action";
var delUrl = "fi/fiCollectionsetAction!delete.action";
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
			name : "deptId",// 部门id
			mapping : 'deptId'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
		}, {
			name : 'cusName',// /客商名称
			mapping : 'cusName'
		}, {
			name : 'billingCycle',// 对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationDate',// 最后对账日期
			mapping : 'reconciliationDate'
		}, {
			name : 'remark',// 备注
			mapping : 'remark'
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
var cm=new Ext.grid.ColumnModel([rowNum, sm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								}, {
									header : '客商',
									sortable : true,
									dataIndex : 'cusName',
									width : 60,
									sortable : true
								}, {
									header : '所属部门',
									dataIndex : 'departName',
									width : 60
								},{
									header : '对账/结算周期',
									dataIndex : 'billingCycle',
									width : 60
								},{
									header : '最后对账日期',
									dataIndex : 'reconciliationDate',
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
								}])
var tbar = [{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'fiCollectionsetAdd',
			tooltip : '新增应付货款结算设置',
			handler : function() {
				fiCollectionsetSave(null);
			}
		}, '-', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'fiCollectionsetEdit',
			disabled : true,
			tooltip : '修改应付货款结算设置',
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
				fiCollectionsetSave(_records[0].data.id);
			}
		}, '-', {
			text : '<b>删除</b>',
			iconCls : 'userDelete',
			id : 'fiCollectionsetDelete',
			disabled : true,
			tooltip : '删除应付货款结算设置',
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
											name : 'deptId',
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

		var carGrid = new Ext.grid.GridPanel({
						renderTo : Ext.getBody(),
						id : 'carCenter',
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
			dateStore.load();

			carGrid.on('click', function() {
						var _record = carGrid.getSelectionModel()
								.getSelections();// 获得所有选中的行
						var updatebtn = Ext.getCmp('fiCollectionsetEdit');// 获得更新按钮
						var deletebtn = Ext.getCmp('fiCollectionsetDelete');// 获得删除按钮
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
		limit:pageSize,
		privilege : privilege,
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value
	}
	var userrolebtn = Ext.getCmp('fiCollectionsetAdd');
	var updatebtn = Ext.getCmp('fiCollectionsetEdit');

	userrolebtn.setDisabled(false);
	updatebtn.setDisabled(false);

	dataReload();

}

function fiCollectionsetSave(cid) {
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 350,
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields),
		labelAlign : "right",
		items : [{
			layout : 'column',
			items : [{
				//columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'hidden',
							id : 'customerId',
							name : 'customerId'
						},{
							xtype : 'hidden',
							id : 'deptId',
							name : 'deptId'
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
							fieldLabel : '所属部门<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : departStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_departName',
							displayField : 'departName',
							name : 'departName',
							blankText : "所属部门为空！",
							anchor : '95%',
							emptyText : "请选择所属部门",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('deptId').setValue(record
											.get("deptId"));
								}
							}
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '对账/结算周期<span style="color:red">*</span>',
							store : billingCycleStore,
							triggerAction : 'all',
							valueField : 'billingCycle',
							displayField : 'billingCycle',
							hiddenName : 'billingCycle',
							emptyText : "请选择对账/结算周期",
							allowBlank : false,
							blankText : "对账/结算周期不能为空！",
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel : '最后对账日期',
							format : 'Y-m-d',
							name : 'reconciliationDate',
							blankText : "最后对账日期不能为空！",
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 5,
							anchor : '95%'
						}]

			}]
		}]
	});

	carTitle = '添加应付货款结算设置';
	if (cid != null) {
		carTitle = '修改应付货款结算设置';
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
	}
	var win = new Ext.Window({
		title : carTitle,
		width : 350,
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
					carTitle = '修改应付货款结算设置';
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
	dateStore.reload();
}

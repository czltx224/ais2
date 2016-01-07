//var pageSize = 2;
var comboxPage=15;
var privilege = 62;
var customerprivilege=61;
var customerGridSearchUrl="sys/customerAction!list.action";
var gridSearchUrl = "sys/basFlightAction!ralaList.action";
var saveUrl = "sys/basFlightAction!save.action";
var delUrl = "sys/basFlightAction!delete.action";
var dateStore;

var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "flightNumber",
			mapping : 'flightNumber'
		}, {
			name : "svo",
			mapping : 'svo'
		}, {
			name : 'startCity',
			mapping : 'startCity'
		}, {
			name : 'endCity',
			mapping : 'endCity'
		}, {
			name : 'standardEndtime',
			mapping : 'standardEndtime'
		}, {
			name : 'standardStarttime',
			mapping : 'standardStarttime'
		}, {
			name : "customerId",
			mapping : 'customerId'
		}, {
			name : 'cusName',
			mapping : 'cusName'
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
		
		//航班落地城市
		loadingCity=new Ext.data.SimpleStore({
			 autoLoad:true, //此处设置为自动加载
  			  data:[['1','广州市'],['2','深圳市'],['3','珠海市']],
   			 fields:["loadId","loadName"]
		});
var tbar = [{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'basCarAdd',
			tooltip : '新增航班信息',
			handler : function() {
				saveCar(null);
			}
		}, '-', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'basCarEdit',
			disabled : true,
			tooltip : '修改航班信息',
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
			tooltip : '删除航班信息',
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
		},'-',{
			text : '<b>导出</b>',
			iconCls : 'userEdit',
			tooltip : '导出',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				
				parent.exportExl(car);
				
			}
		}, '-', {
			xtype : 'textfield',
			blankText : '查询数据',
			id : 'searchContent',
			enableKeyEvents : true,
			listeners : {
				keyup : function(textField, e) {
					if (e.getKey() == 13) {
						searchCar();
					}
				}
			}
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
					['LIKES_flightNumber', '航班号'],
					['LIKES_svo', '航班三字码'],
					['EQS_startCity', '起飞城市'],
					['EQS_endCity', '落地城市'],
					['LIKES_cusName', '客商名称'],
					['EQS_standardStarttime', '起飞时间'],
					['EQS_standardEndtime', '落地时间'],
					['LIKES_createName', '创建人'],
					['LIKES_updateName', '修改人']],
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
			menuStore = new Ext.data.Store({
						storeId : "menuStore",
						baseParams:{filter_EQS_custprop:'提货公司'},
						proxy : new Ext.data.HttpProxy({
									url : sysPath + "/" + customerGridSearchUrl
								}),
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [    
				                   {name : 'cusName',mapping :'cusName'}, 
				                   {name:'customerId',mapping :'id'}
				                 ])
					});
					
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
							forceFit : true
						},
						autoScroll : true,
						autoExpandColumn : 1,
						frame : true,
						loadMask : true,
						sm : sm,
						cm : new Ext.grid.ColumnModel([ rowNum,sm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								}, {
									header : '航班号',
									dataIndex : 'flightNumber',
									width : 60,
									sortable : true
								}, {
									header : '航班三字代码',
									dataIndex : 'svo',
									width : 60,
									sortable : true
								}, {
									header : '起飞城市',
									dataIndex : 'startCity',
									width : 60,
									sortable : true
								}, {
									header : '落地城市',
									dataIndex : 'endCity',
									width : 60,
									sortable : true
								}, {
									header : '起飞时间',
									dataIndex : 'standardStarttime',
									width : 60,
									sortable : true
								}, {
									header : '落地时间',
									dataIndex : 'standardEndtime',
									width : 60,
									sortable : true
								},
								 {
									 header: '客商id', 
									 dataIndex:'customerId',
									 width:60,
									 hidden : true
								 },{
									header: '客商名称', 
									dataIndex:'cusName',
								 	width:60,
									 sortable : true
								 },{
									header : '创建人',
									dataIndex : 'createName',
									width : 60,
									sortable : true
								}, {
									header : '创建时间',
									dataIndex : 'createTime',
									width : 60,
									sortable : true
								}, {
									header : '修改人',
									dataIndex : 'updateName',
									width : 60,
									sortable : true
								}, {
									header : '修改时间',
									dataIndex : 'updateTime',
									width : 60,
									sortable : true
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
				width : 600,
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
										items : [
												{xtype:'hidden',id:'customerId',name:'customerId'},
												{
													xtype : 'hidden',
													labelAlign : 'left',
													fieldLabel : 'id',
													name : 'id'
												}, {
													name : "ts",
													xtype : "hidden"
												}, {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '航班号',
													name : 'flightNumber',
													maxLength : 10,
													allowBlank : false,
													blankText : "航班号不能为空！",
													anchor : '95%'
												}, {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '三字代码',
													name : 'svo',
													maxLength : 5,
													anchor : '95%'
												}, {
													xtype : 'textfield',
													labelAlign : 'left',
													fieldLabel : '起飞城市',
													name : 'startCity',
													maxLength : 5,
													
													allowBlank : false,
													blankText : "起飞城市不能为空！",
													anchor : '95%'
												}, {
													xtype : 'combo',
													triggerAction : 'all',
													store : loadingCity,
													allowBlank : false,
													emptyText : "请选择车源落地城市",
													forceSelection : true,
													fieldLabel:'落地城市<font style="color:red;">*</font>',
													editable : false,
													mode : "local",//获取本地的值
													displayField : 'loadName',//显示值，与fields对应
													valueField : 'loadName',//value值，与fields对应
													name : 'endCity',
													anchor : '95%'
												}, {
													xtype : 'timefield',
													labelAlign : 'left',
													fieldLabel : '起飞时间',
													format:'H:i',
													name : 'standardStarttime',
													allowBlank : false,
													blankText : "起飞时间不能为空！",
													anchor : '95%'
												}, {
													xtype : 'timefield',
													labelAlign : 'left',
													fieldLabel : '落地时间',
													format:'H:i',
													name : 'standardEndtime',
													allowBlank : false,
													blankText : "落地时间不能为空！",
													anchor : '95%'
												},{
													xtype : 'combo',
													fieldLabel: '提货公司<span style="color:red">*</span>',
													allowBlank : false,
													typeAhead:false,
													forceSelection : true,
													minChars : 1,
													triggerAction : 'all',
													store: menuStore,
													pageSize : comboxPage,
													queryParam : 'filter_LIKES_cusName',
													displayField : 'cusName',
													name:'cusName',
													blankText : "提货公司为空！",
													anchor : '95%',
													emptyText : "请选择提货公司",
													listeners : {
														'select' : function(combo, record, index) {
																Ext.getCmp('customerId').setValue(record.get("customerId"));
														}
													}
								
												}]

									}]
						}]
			});

	carTitle = '添加航班信息';
	if (cid != null) {
		carTitle = '修改航班信息';
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
					carTitle = '修改航班信息';
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

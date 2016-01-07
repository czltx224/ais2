//var pageSize = 2;
var comboxPage = 15;
var privilege = 110;
var departGridSearchUrl = "sys/departAction!findAll.action";
var responsibleGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 负责人
var gridSearchUrl = "fi/fiCapitaaccountsetAction!ralaList.action";
var saveUrl = "fi/fiCapitaaccountsetAction!saveFiCapitaaccountset.action";
var delUrl = "fi/fiCapitaaccountsetAction!delete.action";
var dateStore,departStore,accountTypeStore,paymentTypeStore,responsibleStore,isDeleteStore,ownedBankStore,natureStore,internetBankStore;

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
			name : "departId", // 部门id
			mapping : 'departId'
		}, {
			name : "paymentType",// 收支类型
			mapping : 'paymentType'
		}, {
			name : 'accountType',// 账号类型
			mapping : 'accountType'
		}, {
			name : 'paymentTypeName',// 收支类型
			mapping : 'paymentTypeName'
		}, {
			name : 'accountTypeName',// 账号类型
			mapping : 'accountTypeName'
		}, {
			name : 'departName',// 所属部门名称
			mapping : 'departName'
		}, {
			name : 'accountNum',// /账号
			mapping : 'accountNum'
		}, {
			name : 'accountName',// 账号名称
			mapping : 'accountName'
		}, {
			name : 'bank',// 开户行
			mapping : 'bank'
		}, {
			name : 'ownedBank',// 所属银行
			mapping : 'ownedBank'
		}, {
			name : 'ownedBankName',// 所属银行
			mapping : 'ownedBankName'
		}, {
			name : 'nature',// 账号性质(对公、对私)
			mapping : 'nature'
		}, {
			name : 'internetBank',// 是否开通网银(未开通,已开通)
			mapping : 'internetBank'
		}, {
			name : 'openingBalance',//余额
			mapping : 'openingBalance'
		}, {
			name : 'responsibleId',// 负责人id
			mapping : 'responsibleId'
		}, {
			name : 'responsible',// 负责人
			mapping : 'responsible'
		}, {
			name : 'isDelete',// 是否可用
			mapping : 'isDelete'
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
		},'remark'];
var cm=new Ext.grid.ColumnModel([rowNum, sm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								}, {
									header : '部门id',
									sortable : true,
									dataIndex : 'departId',
									hidden : true
								}, {
									header : '收支类型id',
									dataIndex : 'paymentType',
									hidden : true
								},{
									header : '账号类型id',
									dataIndex : 'accountType',
									hidden : true
								}, {
									header : '所属部门名称',
									dataIndex : 'departName',
									width : 60
								},{
									header : '账号类型',
									dataIndex : 'accountTypeName',
									width : 60
								},{
									header : '收支类型',
									dataIndex : 'paymentTypeName',
									width : 60
								},{
									header : '账号性质',
									dataIndex : 'nature',
									width : 60
								},{
									header : '所属银行',
									dataIndex : 'ownedBank',
									hidden : true,
									hideable:false
								},{
									header : '所属银行',
									dataIndex : 'ownedBankName',
									width : 60
								}, {
									header : '开户行',
									dataIndex : 'bank',
									width : 60
								}, {
									header : '账号名称',
									dataIndex : 'accountName',
									width : 60
								}, {
									header : '账号',
									dataIndex : 'accountNum',
									width : 60
								}, {
									header : '余额',
									dataIndex : 'openingBalance',
									width : 60
								},{
									header : '是否开通网银',
									dataIndex : 'internetBank',
									width : 60
								}, {
									header : '负责人id',
									dataIndex : 'responsibleId',
									width : 60,
									hidden : true
								},{
									header : '负责人',
									dataIndex : 'responsible',
									width : 60
								}, {
									header : '备注',
									dataIndex : 'remark',
									width : 60
								}, {
									header : '状态',
									dataIndex : 'isDelete',
									width : 60,
									renderer:function(v,metaData){
										if(v==0){
											v='冻结';
										}else if (v==1){
											v='正常';
										}
										return v;
									}
								}, {
									header : '创建人',
									dataIndex : 'createName',
									width : 60,
									hidden : true
								}, {
									header : '创建时间',
									dataIndex : 'createTime',
									width : 80
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
var tbar = ['&nbsp;&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'table',
				id : 'exportbtn',
				tooltip : '导出',
				handler : function(){
					parent.exportExl(Ext.getCmp('mainGrid'));			
				}
			},'-','&nbsp;&nbsp;',{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'fiCapitaaccountsetAdd',
			tooltip : '新增',
			handler : function() {
				fiCapitaaccountsetSave(null);
			}
		}, '-','&nbsp;&nbsp;', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'fiCapitaaccountsetEdit',
			disabled : true,
			tooltip : '修改',
			handler : function() {
				var car = Ext.getCmp('mainGrid');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				fiCapitaaccountsetSave(_records[0].data.id);
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
			xtype : "combo",
			width : 100,
			triggerAction : 'all',

			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],['LIKES_accountTypeName', '帐号类别'],['LIKES_responsible', '负责人']],
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
			xtype : 'textfield',
			blankText : '查询数据',
			id : 'searchContent'
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : search
		}/*
			 * , /*{text : '<b>高级查询</b>',iconCls : 'btnSearch', handler :
			 * search }
			 */
];
Ext.onReady(function() {

			// 账号类型
			accountTypeStore = new Ext.data.Store({
						storeId : "accountTypeStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath
											+ "/sys/dictionaryAction!ralaList.action"
								}),
						baseParams : {
							filter_EQL_basDictionaryId : 103,
							privilege : 16
						},
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
										name : 'id',
										mapping : 'id'
									}, {
										name : 'accountTypeName',
										mapping : 'typeName'
									}])
					});
			accountTypeStore.load();
			
			// 收支类型
			paymentTypeStore = new Ext.data.Store({
						storeId : "paymentTypeStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath
											+ "/sys/dictionaryAction!ralaList.action"
								}),
						baseParams : {
							filter_EQL_basDictionaryId : 104,
							privilege : 16
						},
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
										name : 'id',
										mapping : 'id'
									}, {
										name : 'paymentTypeName',
										mapping : 'typeName'
									}])
					});
			paymentTypeStore.load();
					
			// 负责人
			responsibleStore = new Ext.data.Store({
						storeId : "responsibleStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath + "/"
											+ responsibleGridSearchUrl
								}),
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
									name : 'responsible',
									mapping : 'userName'
								}, {
									name:'responsibleId',
									mapping:'id'
								}
								])
					});
				
			//所属银行
			ownedBankStore = new Ext.data.Store({
						storeId : "ownedBankStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath
											+ "/sys/dictionaryAction!ralaList.action"
								}),
						baseParams : {
							filter_EQL_basDictionaryId : 202,
							privilege : 16
						},
						reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
										name : 'id',
										mapping : 'id'
									}, {
										name : 'ownedBankName',
										mapping : 'typeName'
									}])
					});
					
			// 账号性质
			natureStore = new Ext.data.SimpleStore({
						auteLoad : true, // 此处设置为自动加载
						data : [['对公', '对公'], ['对私', '对私']],
						fields : ["nature", "natureName"]
					});
					
			// 是否开通网银
			internetBankStore = new Ext.data.SimpleStore({
						auteLoad : true, // 此处设置为自动加载
						data : [['未开通', '未开通'], ['已开通', '已开通']],
						fields : ["internetBank", "internetBankName"]
					});
					
					
			// 冻结状态
			isDeleteStore = new Ext.data.SimpleStore({
						auteLoad : true, // 此处设置为自动加载
						data : [['1', '正常'], ['0', '冻结']],
						fields : ["isDelete", "isDeleteName"]
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
						renderTo : Ext.getBody(),
						id : 'mainGrid',
						height : Ext.lib.Dom.getViewHeight()-4,
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
			mainGrid.render();

			mainGrid.on('click', function() {
						var _record = mainGrid.getSelectionModel()
								.getSelections();// 获得所有选中的行
						var updatebtn = Ext.getCmp('fiCapitaaccountsetEdit');// 获得更新按钮
						var deletebtn = Ext.getCmp('fiCapitaaccountsetDelete');// 获得删除按钮
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

function search() {
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
	var userrolebtn = Ext.getCmp('fiCapitaaccountsetAdd');
	var updatebtn = Ext.getCmp('fiCapitaaccountsetEdit');

	userrolebtn.setDisabled(false);
	updatebtn.setDisabled(false);

	dataReload();

}

function fiCapitaaccountsetSave(cid) {
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
							id : 'accountType',
							name : 'accountType'
						},{
							xtype : 'hidden',
							id : 'paymentType',
							name : 'paymentType'
						},{
							xtype : 'hidden',
							id : 'departId',
							name : 'departId'
						},{
							xtype : 'hidden',
							id : 'responsibleId',
							name : 'responsibleId'
						},{
							xtype : 'hidden',
							id : 'ownedBank',
							name : 'ownedBank'
						}, {
							xtype : 'hidden',
							fieldLabel : 'id',
							name : 'id'
						}, {
							name : "ts",
							xtype : "hidden"
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
						anchor : '98%',
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
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '账号类型<span style="color:red">*</span>',
							store : accountTypeStore,
							triggerAction : 'all',
							valueField : 'accountTypeName',
							displayField : 'accountTypeName',
							hiddenName : 'accountTypeName',
							emptyText : "请选择账号类型",
							allowBlank : false,
							blankText : "账号类型不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('accountType').setValue(record
											.get("id"));
								}
							}
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '收支类型<span style="color:red">*</span>',
							store : paymentTypeStore,
							triggerAction : 'all',
							valueField : 'paymentTypeName',
							displayField : 'paymentTypeName',
							hiddenName : 'paymentTypeName',
							emptyText : "请选择收支类型",
							allowBlank : false,
							blankText : "收支类型不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('paymentType').setValue(record
											.get("id"));
								}
							}
						}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : natureStore,
							allowBlank : false,
							emptyText : "请选择账号性质",
							forceSelection : true,
							fieldLabel : '账号性质',
							editable : false,
							mode : "local",// 获取本地的值
							displayField : 'natureName',
							valueField : 'nature',
							name : 'nature',
							anchor : '98%'
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '所属银行<span style="color:red">*</span>',
							store : ownedBankStore,
							triggerAction : 'all',
							valueField : 'ownedBankName',
							displayField : 'ownedBankName',
							hiddenName : 'ownedBankName',
							emptyText : "请选择所属银行",
							allowBlank : false,
							blankText : "所属银行不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('ownedBank').setValue(record
											.get("id"));
								}
							}
						}, {
							xtype : 'textfield',
							fieldLabel : '账号',
							name : 'accountNum',
							maxLength : 50,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '账户名称',
							name : 'accountName',
							maxLength : 20,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '开户行名称',
							name : 'bank',
							maxLength : 50,
							anchor : '98%'
						}, {
							xtype : 'numberfield',
							id:'openingBalance',
							fieldLabel : '期初余额',
							name : 'openingBalance',
							maxLength : 50,
							anchor : '98%'
						}, {
							xtype : 'combo',
							fieldLabel : '负责人<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : responsibleStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_userName',
							displayField : 'responsible',
							valueField : 'responsible',// value值，与fields对应
							hiddenName : 'responsible',
							id:'comboresponsibleId',
							blankText : "负责人不能为空！",
							anchor : '98%',
							emptyText : "请选择负责人",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('responsibleId').setValue(record
											.get("responsibleId"));
								}
							}
						}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : internetBankStore,
							allowBlank : false,
							emptyText : "请选择是否开通网银",
							forceSelection : true,
							fieldLabel : '是否开通网银',
							editable : false,
							mode : "local",
							displayField : 'internetBankName',
							valueField : 'internetBank',
							name : 'internetBank',
							anchor : '98%'
						}, {
							xtype : 'combo',
							triggerAction : 'all',
							store : isDeleteStore,
							allowBlank : false,
							emptyText : "请选择合同类型",
							forceSelection : true,
							fieldLabel : '状态',
							editable : false,
							mode : "local",// 获取本地的值
							displayField : 'isDeleteName',// 显示值，与fields对应
							valueField : 'isDelete',// value值，与fields对应
							hiddenName:'isDelete',
							anchor : '98%'
							//name : 'isDelete',
							
							//id:'isDelete',
							/*anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('isDelete').setValue(record
											.get("isDelete"));
								}
							}*/
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 500,
							anchor : '98%'
						}]

			}]
		}]
	});

	Title = '添加资金账号';
	if (cid != null) {
		Ext.getCmp('openingBalance').disabled=true;
		Title = '修改资金账号';
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
	}
	var win = new Ext.Window({
		title : Title,
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
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	Ext.apply(dateStore.baseParams, {
		filter_EQS_departId : departId,
		privilege:privilege
	});
	
	dateStore.reload({
				params : {
					start : 0
				}
			});
}

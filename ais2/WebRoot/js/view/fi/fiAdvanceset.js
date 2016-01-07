//var pageSize = 2;
var comboxPage = 15;
var privilege = 127;
var departGridSearchUrl = "sys/departAction!findAll.action";
var responsibleGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 负责人
var gridSearchUrl = "fi/fiAdvancesetAction!ralaList.action";
var saveUrl = "fi/fiAdvancesetAction!save.action";
var customerGridSearchUrl = "sys/customerAction!list.action";
var delUrl = "fi/fiAdvancesetAction!delete.action";
var dateStore,departStore,accountTypeStore,paymentTypeStore,responsibleStore,isDeleteStore;

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
			name : "departId", // 部门id
			mapping : 'departId'
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
			name : 'openingBalance',//期初余额
			mapping : 'openingBalance'
		}, {
			name : 'isdelete',// 是否可用
			mapping : 'isdelete'
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
			name : 'remark',
			mapping : 'remark'
		}];
		
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
									name : 'customerName',
									mapping : 'cusName'
								}, {
									name : 'customerId',
									mapping : 'id'
								}])
			});
		
					//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
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
			
						// 客商列表
			customerStore2 = new Ext.data.Store({
				storeId : "customerStore2",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + customerGridSearchUrl
						}),
			            baseParams:{
			            	filter_EQS_settlement:'预付'
			            },
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'customerName',
									mapping : 'cusName'
								}, {
									name : 'customerId',
									mapping : 'id'
								}])
			});
		
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
									header : '所属部门名称',
									dataIndex : 'departName',
									width : 60
								}, {
									header : '客商id',
									dataIndex : 'customerId',
									hidden : true
								}, {
									header : '客商名称',
									dataIndex : 'customerName',
									width:65
								}, {
									header : '账号',
									dataIndex : 'accountNum',
									width : 60
								}, {
									header : '账号名称',
									dataIndex : 'accountName',
									width : 60
								}, {
									header : '开户行',
									dataIndex : 'bank',
									width : 60
								}, {
									header : '余额',
									dataIndex : 'openingBalance',
									width : 60
								}, {
									header : '备注',
									dataIndex : 'remark',
									width : 60
								}, {
									header : '状态',
									dataIndex : 'isdelete',
									width : 60,
									renderer:function(v,metaData){
										if(v==1){
											return '正常';
										}else{
											return '冻结';
										}
									}
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
var tbar = ['&nbsp;',
			{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'fiCapitaaccountsetAdd',
			tooltip : '新增',
			handler : function() {
				fiCapitaaccountsetSave(null);
			}
		}, '-','&nbsp;', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'fiCapitaaccountsetEdit',
			disabled : true,
			tooltip : '修改',
			handler : function() {
				var grid  = Ext.getCmp('mainGrid');
				var _records = grid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					 fiCapitaaccountsetSave(_records[0]);
				}else if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				}else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
			}
		}, '-','&nbsp;','所属部门:',{
			xtype : 'combo',
			id:'comboRightDepart2',
			hiddenId : 'dictionaryName',
   			hiddenName : 'olist[0].rightDepartId',
			triggerAction : 'all',
			store : rightDepartStore2,
			mode:'local',
			width : 100,
//			queryParam : 'filter_LIKES_departName',
			minChars : 1,
			listWidth:245,
			allowBlank : false,
			emptyText : "请选择创建部门名称",
			forceSelection : false,
			fieldLabel:'部门',
			editable : false,
			pageSize:500,
			displayField : 'departName',//显示值，与fields对应
			valueField : 'departId',//value值，与fields对应
			anchor : '95%',
			enableKeyEvents:true,
			listeners : {
				 render:function(combo){
				 		rightDepartStore2.load({
								params : {
									start : 0,
									limit : 500
								},callback :function(v){
									var flag=true;
									for(var i=0;i<rightDepartStore2.getCount();i++){
										if(rightDepartStore2.getAt(i).get("departId")==bussDepart){
											flag=false;
										}
									}
									if(flag){
										var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
										var record=new store();
										record.set("departId",bussDepart);
										record.set("departName",bussDepartName);
										rightDepartStore2.add(record);		
									}
										combo.setValue(bussDepart);
								}
						});
				 },
		 		 keyup:function(numberfield, e){
		             if(e.getKey() == 13 ){
							searchLog();
		              }
		 		 }
		 	}
   },'-','&nbsp;', {
			xtype : 'textfield',
			blankText : '查询数据',
			width : 100,
			id : 'searchContent'
		},'&nbsp;',{
			xtype : 'combo',
			typeAhead : false,
			forceSelection : true,
			width : 100,
			id:'cusId',
			hidden:true,
			listWidth:245,
			disabled:true,
			minChars : 1,
			triggerAction : 'all',
			store : customerStore,
			pageSize : comboxPage,
			queryParam : 'filter_LIKES_cusName',
			displayField : 'customerName',
			valueField : 'customerId',
			name : 'cusName',
			emptyText : "请选择客商名称",
			listeners : {
				'select' : function(combo, record, index) {
				
				}
			}
		}, '-', {
			xtype : "combo",
			width : 100,
			triggerAction : 'all',
			id:'checkItemstext',
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],
					['EQL_customerId', '客商名称'],
					['LIKES_accountNum', '帐号'],
					['LIKES_accountName', '帐号名称']],
			emptyText : '请选择查询类型',
			forceSelection : true,
			listeners : {
				'select' : function(combo, record, index) {
					if (combo.getValue() == '') {
						Ext.getCmp("searchContent").disable();
						Ext.getCmp("searchContent").show();
						Ext.getCmp("searchContent").setValue("");
						
						Ext.getCmp("cusId").disable();
						Ext.getCmp("cusId").hide();
						Ext.getCmp("cusId").setValue("");
					}else if(combo.getValue() == 'EQL_customerId'){
						Ext.getCmp("cusId").enable();
						Ext.getCmp("cusId").show();
						Ext.getCmp("cusId").setValue("");
					
						Ext.getCmp("searchContent").disable();
						Ext.getCmp("searchContent").hide();
						Ext.getCmp("searchContent").setValue("");
					} else {
						Ext.getCmp("searchContent").enable();
						Ext.getCmp("searchContent").show();
						
						Ext.getCmp("cusId").disable();
						Ext.getCmp("cusId").hide();
						Ext.getCmp("cusId").setValue("");
					}
				}
			}

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
	
			// 部门列表
			departStore = new Ext.data.Store({
						storeId : "departStore",
						proxy : new Ext.data.HttpProxy({
									url : sysPath + "/" + departGridSearchUrl
								}),
						baseParams:{
			               privilege:53,
			               filter_EQL_isBussinessDepa:1
			            },
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

		var carGrid = new Ext.grid.GridPanel({
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
						frame : false,
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

			carGrid.on('click', function() {
						var _record = carGrid.getSelectionModel().getSelections();// 获得所有选中的行
						var updatebtn = Ext.getCmp('fiCapitaaccountsetEdit');// 获得更新按钮
						if (_record.length == 1) {
							updatebtn.setDisabled(false);
						} else if (_record.length > 1) {
							updatebtn.setDisabled(true);
						} else {
							updatebtn.setDisabled(true);
						}
				

			});
			
			carGrid.on('rowdblclick',function(grid,index,e){
		        var _records = carGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					 fiCapitaaccountsetSave(_records[0]);
				}
			 	
		});
		});

function search() {
	dateStore.proxy = new Ext.data.HttpProxy({
				method : 'POST',
				url : sysPath + "/" + gridSearchUrl
			});
	
	if (Ext.getCmp('checkItemstext').getValue() == 'EQL_customerId') {
		Ext.apply(dateStore.baseParams, {
					limit:pageSize,
					privilege : privilege,
					filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
					filter_EQL_customerId : Ext.getCmp('cusId').getValue(),
					checkItems :'',
					itemsValue :''
			});
	}else{
		Ext.apply(dateStore.baseParams, {
					limit:pageSize,
					privilege : privilege,
					checkItems : Ext.getCmp("checkItemstext").getValue(),
					filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
					itemsValue : Ext.get("searchContent").dom.value
			});
	}
	
	dateStore.reload({
					params : {
						start : 0,
						limit : pageSize
					}
				});
}

function fiCapitaaccountsetSave(_record) {

	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle:'padding:0px 0px 0px 5px',
		labelWidth : 75,
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
							fieldLabel : 'id',
							name : 'id'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'combo',
							fieldLabel : '所属部门',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							anchor:'92%',
							mode:'local',
							id:'bussDepart',
							minChars : 1,
							editable : false,
							triggerAction : 'all',
							store : rightDepartStore2,
							pageSize : comboxPage,
							displayField : 'departName',
							valueField : 'departId',
							name : 'departName',
							hiddenName:'departId',
							blankText : "所属部门为空！",
							emptyText : "请选择所属部门",
							listeners : {
								 render:function(combo){
								 		rightDepartStore2.load({
												params : {
													start : 0,
													limit : 500
												},callback :function(v){
													var flag=true;
													for(var i=0;i<rightDepartStore2.getCount();i++){
														if(rightDepartStore2.getAt(i).get("departId")==bussDepart){
															flag=false;
														}
													}
													if(flag){
														var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
														var record=new store();
														record.set("departId",bussDepart);
														record.set("departName",bussDepartName);
														rightDepartStore2.add(record);		
													}
														combo.setValue(bussDepart);
												}
										});
								 }
							}
						},{
							xtype : 'combo',
							typeAhead : false,
							fieldLabel : '客商名称<span style="color:red">*</span>',
							forceSelection : true,
							anchor:'92%',
							allowBlank : false,
							id:'customerId2',
							listWidth:245,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore2,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'customerName',
							valueField : 'customerId',
							name : 'customerName',
							hiddenName:'customerId',
							emptyText : "请选择客商名称",
							listeners : {
								'focus' : function(combo, record, index) {
									var bus=Ext.getCmp('bussDepart');
									if(bus.getValue()==''){
										Ext.Msg.alert(alertTitle,"在选择客商名称前，请先选择所属部门",function(){
											combo.setValue('');
											bus.markInvalid("在选择客商名称前，请先选择所属部门");
											bus.focus();
										});
									}
									
								},
								select:function(combo){
									Ext.Ajax.request({
			 							url:sysPath+"/fi/fiAdvancesetAction!ralaList.action",
										params:{
											privilege:privilege,
											filter_EQL_departId:Ext.getCmp('bussDepart').getValue(),
											filter_EQL_customerId:Ext.getCmp('customerId2').getValue()
										},
										success : function(response) { // 回调函数有1个参数
											if(Ext.decode(response.responseText).result.length!=0){
												Ext.Msg.alert(alertTitle,"此客商的预付款账号记录已经存在，不允许再输入",function(){
													combo.setValue('');
													combo.markInvalid("此客商的预付款账号记录已经存在，不允许再输入");
													combo.focus();
												});
											}
										},
										failure : function(response) {
											
										}
									});	
								
								}
							}
						}, 
						 {
							xtype : 'numberfield',
							fieldLabel : '账号<span style="color:red">*</span>',
							name : 'accountNum',
							maxLength : 50,
							allowBlank : false,
							anchor:'92%'
						}, {
							xtype : 'textfield',
							fieldLabel : '账户名称<span style="color:red">*</span>',
							name : 'accountName',
							maxLength : 20,
							allowBlank : false,
							anchor:'92%'
						}, {
							xtype : 'textfield',
							fieldLabel : '开户行名称<span style="color:red">*</span>',
							name : 'bank',
							allowBlank : false,
							maxLength : 50,
							anchor:'92%'
						}, {
							xtype : 'numberfield',
							id:'openingBalance',
							fieldLabel : '余额<span style="color:red">*</span>',
							name : 'openingBalance',
							maxLength : 50,
							allowBlank : false,
							anchor:'92%'
						},{
							xtype: 'radiogroup',
			                fieldLabel: '状态<span style="color:red">*</span>',
			                id:"isDelete",
			                columns: 3,
			                defaults: {
			                    name: 'isdelete' 
			                },
			                items: [{
			                    inputValue: '0',
			                    boxLabel: '冻结',
			                   	checked:_record==null?true:(_record.data.isdelete=="0"?true:false)
			                }, {
			                    inputValue: '1',
			                    boxLabel: '正常',
			                    checked:_record==null?false:(_record.data.isdelete=="1"?true:false)
			                }]
						},{
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 500,
							anchor:'92%'
						}]

			}]
		}]
	});
	Title = '添加预付款账号';
	if (_record != null) {
		Title = '修改预付款账号';
		customerStore2.load();
		departStore.load();
		form.load({
			url : sysPath + "/" + gridSearchUrl,
			params : {
				filter_EQL_id : _record.get('id'),
				privilege : privilege
			},
			success : function(response) { // 回调函数有1个参数
				Ext.getCmp("isDelete").reset();
			}
		});
		Ext.getCmp('openingBalance').disable();
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
							customerName:Ext.getCmp('customerId2').getRawValue(),
							privilege : privilege
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide();
						 	Ext.Msg.alert( alertTitle,action.result.msg, function() {
										dataReload();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert(alertTitle, action.result.msg,
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

				if (_record == null) {
					form.getForm().reset();
				}else{
					customerStore2.load();
					departStore.load();
					carTitle = '修改预付款设置';
					form.load({
								url : sysPath + "/" + gridSearchUrl,
								params : {
									filter_EQL_id : _record.get('id'),
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

var pageSize = 20;
var comboxPage=15;
var privilege = 66;
var customerGridSearchUrl="sys/customerAction!list.action";
var departGridSearchUrl="sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiInvoiceAction!ralaList.action";
var saveUrl = "fi/fiInvoiceAction!save.action";
var invalidUrl = "fi/fiInvoiceAction!invalid.action";
var confirmReviewUrl="fi/fiInvoiceAction!review.action";

var dateStore,customerStore,departStore,reviewStatusStore;

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
 
var fields = [{
			name : "id",
			mapping : 'id'
		}, {
			name : "customerId", //客商id
			mapping : 'customerId'
		}, {
			name : "departId",//部门id
			mapping : 'departId'
		}, {
			name : "sourceData", // 数据来源
			mapping : 'sourceData'
		}, {
			name : "sourceNo", //来源单号
			mapping : 'sourceNo'
		}, {
			name : 'status',//发票状态
			mapping : 'status'
		}, {
			name : 'reviewStatus',//审核状态
			mapping : 'reviewStatus'
		}, {
			name : 'amount',//开票金额
			mapping : 'amount'
		}, {
			name : 'taxes',//税金
			mapping : 'taxes'
		}, {
			name : 'invoiceType',//发票类型
			mapping : 'invoiceType'
		}, {
			name : 'paymentType',//收付类型
			mapping : 'paymentType'
		}, {
			name : 'applicant',//申请人
			mapping : 'applicant'
		}, {
			name : 'handled',//经手人
			mapping : 'handled'
		}, {
			name : 'drawer',//开票人
			mapping : 'drawer'
		}, {
			name : 'remark',//备注
			mapping : 'remark'
		}, {
			name : 'departName',//部门名：部门名称
			mapping : 'departName'
		}, {
			name : 'cusName',//客商表：客商名称
			mapping : 'cusName'
		}, {
			name : 'createName',
			mapping : 'createName'
		}, {
			name : 'reviewUser',
			mapping : 'reviewUser'
		}, {
			name : 'expressNo',
			mapping : 'expressNo'
		}, {
			name : 'reviewRemark',
			mapping : 'reviewRemark'
		}, {
			name : 'reviewTime',
			mapping : 'reviewTime'
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

var tbar = [{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('carGrid'));
        } },'-',{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'basCarAdd',
			tooltip : '新增发票',
			handler : function() {
				saveCar(null);
			}
		}, '-', {
			text : '<b>修改</b>',
			iconCls : 'userEdit',
			id : 'basCarEdit',
			disabled : true,
			tooltip : '修改发票',
			handler : function() {
				var car = Ext.getCmp('carGrid');
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
			text : '<b>作废</b>',
			iconCls : 'userDelete',
			id : 'basCarDelete',
			disabled : true,
			tooltip : '作废发票',
			handler : function() {
				var car = Ext.getCmp('carGrid');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要操作的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				Ext.Msg.confirm("系统提示", "确定要作废所选的记录吗？", function(btnYes) {
							if (btnYes == 'yes' || btnYes == 'ok'
									|| btnYes == true) {
								var ids = "";
								for (var i = 0; i < _records.length; i++) {

									ids = ids + _records[i].data.id + ',';
								}
								Ext.Ajax.request({
											url : sysPath + '/' + invalidUrl,
											params : {
												ids : ids,
												privilege : privilege
											},
											success : function(resp) {
												var respText = Ext.util.JSON
														.decode(resp.responseText);
												Ext.Msg.alert("友情提示", "作废成功!");
												dataReload();
											}
										});
							}
						});
			}
		}, '-', {
			text : '<b>审核</b>',
			iconCls : 'userEdit',
			id : 'basReview',
			disabled : true,
			tooltip : '审核发票',
			handler : confirmReview
		}, '-', {
			text : '<b>邮寄发票</b>',
			iconCls : 'MailEdit',
			id : 'basMail',
			disabled : true,
			tooltip : '邮寄发票',
			handler : function() {
				var car = Ext.getCmp('carGrid');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				confirmBasMail(_records[0].data.id);
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

	reviewStatusStore=new Ext.data.SimpleStore({ // 填充的数据  
		 auteLoad : true,
	     fields : [ 'reviewStatusId',  'reviewStatus'],  
	     data   : [['1', '未审核'], ['2', '已审核']]  
	 });
	//客商列表
	customerStore = new Ext.data.Store({
				storeId : "customerStore",
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
	
	//查询主列表
	dateStore = new Ext.data.Store({
				storeId : "dateStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + gridSearchUrl,
							params : {
								privilege : privilege
							}
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
				region : "center",
				id : 'carGrid',
				height : Ext.lib.Dom.getViewHeight(),
				width : Ext.lib.Dom.getViewWidth(),
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					forceFit : false,
					scrollOffset: 0,
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : new Ext.grid.ColumnModel([rowNum,sm,  {
							header : '单号',
							dataIndex : 'id',
							sortable : true,
							width:60
						}, {
							header : '客商',
							dataIndex : 'cusName',
							width:140,
							sortable : true
						}, {
							header : '所属部门',
							dataIndex : 'departName',
							width:120
						}, {
							header : '发票类型',
							dataIndex : 'invoiceType',
							width:80
						}, {
							header : '收付类型',
							dataIndex : 'paymentType',
							width:80
						},{
							header : '发票状态',
							dataIndex : 'status',
							renderer:function(v,metaData){
								if(v==0){
									v='作废';
								}else if (v==1){
									v='正常';
								}else if (v==2){
									v='已寄出';
								}else if (v==2){
									v='已收到';
								}
								return v;
							},width:80
						}, {
							header : '审核状态',
							dataIndex : 'reviewStatus',
							renderer:function(v,metaData){
								if(v==1){
									v='未审核';
								}else if (v==2){
									v='已审核';
								}
								return v;
							},width:65
						},
						 {	
						 	header: '开票金额', 
						 	dataIndex:'amount',
							width:80
						 },
						{
							header : '税金',
							dataIndex : 'taxes',
							width:60
						},{
							header : '数据来源',
							dataIndex : 'sourceData',
							width:80
						},{
							header : '来源单号',
							dataIndex : 'sourceNo',
							width:80
						},{
							header : '快递单号',
							dataIndex : 'expressNo',
							width:70
						},{
							header : '申请人',
							dataIndex : 'applicant',
							width:70
						},{
							header : '经手人',
							dataIndex : 'handled',
							width:70
						},{
							header : '开票人',
							dataIndex : 'drawer',
							width:70
						},{
							header : '备注',
							dataIndex : 'remark',
							width:120
						}, {
							header : '创建人',
							dataIndex : 'createName',
							width:70
						}, {
							header : '创建时间',
							dataIndex : 'createTime',
							width:100
						}, {
							header : '审核人',
							dataIndex : 'reviewUser',
							width:70
						}, {
							header : '审核时间',
							dataIndex : 'reviewTime',
							width:70
						}, {
							header : '审核备注',
							dataIndex : 'reviewRemark',
							width:120
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
						}]),
				store : dateStore,
	
				//tbar : tbar,
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
		id : 'view',
		el : 'mainPanel',
		labelAlign : 'left',
		height : Ext.lib.Dom.getViewHeight()-4,
		width : Ext.lib.Dom.getViewWidth(),
		bodyStyle : 'padding:1px',
		layout : "border",
		tbar : tbar,
		frame : false,
		items : [carGrid]
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
						width : 80,
						anchor : '95%'
					}, '-', '审核状态', {
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
					}, '-', {
						xtype : "combo",
						width : 70,
						triggerAction : 'all',
						model : 'local',
						hiddenId : 'checkItems',
						hiddenName : 'checkItems',
						name : 'checkItemstext',
						store : [['', '查询全部'],
								 ['EQS_id', '单号'],
								 ['LIKES_sourceNo', '来源单号']],
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
						width : 60,
						id : 'searchContent'
					}]
		});
		tbarsearch.render(mainpanel.tbar);
	});
	
	mainpanel.render();
	mainpanel.doLayout();
	carGrid.render();

	carGrid.on('click', function() {
		var _record = carGrid.getSelectionModel()
				.getSelections();// 获得所有选中的行
		var createbtn = Ext.getCmp('basCarAdd');// 获得新建按钮
		var updatebtn = Ext.getCmp('basCarEdit');// 获得更新按钮
		var deletebtn = Ext.getCmp('basCarDelete');// 获得删除按钮
		var reviewbtn = Ext.getCmp("basReview"); //获得审核按钮
		var basMailbtn = Ext.getCmp("basMail"); //邮寄发票按钮
		
		
		
		if (_record.length == 1) {
			if (updatebtn) {
				createbtn.setDisabled(true);
			}
			if (updatebtn) {
				updatebtn.setDisabled(false);
			}
			if (deletebtn) {
				deletebtn.setDisabled(false);
			}
			if (deletebtn) {
				reviewbtn.setDisabled(false);
			}
			if(_record[0].data.status==0){
				createbtn.setDisabled(true);
				updatebtn.setDisabled(true);
				deletebtn.setDisabled(true);
				reviewbtn.setDisabled(true);
				basMailbtn.setDisabled(true);
			};
			
			
			if(_record[0].data.reviewStatus==2){
				createbtn.setDisabled(true);
				updatebtn.setDisabled(true);
				deletebtn.setDisabled(true);
				reviewbtn.setDisabled(true);
				basMailbtn.setDisabled(false);
			}else{
				createbtn.setDisabled(false);
				updatebtn.setDisabled(false);
				deletebtn.setDisabled(false);
				reviewbtn.setDisabled(false);
				basMailbtn.setDisabled(true);
			};
			
		} else if (_record.length > 1) {
			if (updatebtn) {
				createbtn.setDisabled(true);
			}
			if (updatebtn) {
				updatebtn.setDisabled(true);
			}
			if (deletebtn) {
				deletebtn.setDisabled(false);
			}
		} else {
			if (updatebtn) {
				createbtn.setDisabled(false);
			}
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

function searchCar() {
	dateStore.baseParams = {
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value,
		privilege : privilege
	}
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
										{xtype:'hidden',id:'departId',name:'departId'},
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
											fieldLabel:'所属部门<span style="color:red">*</span>',
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
														Ext.getCmp('departId').setValue(record
																.get("departId"));
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
										},{
											xtype : 'combo',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({ // 填充的数据  
										                fields : [ 'sourceData',  'sourceData_text'],  
										                data   : [['对账单', '对账单'], ['配送单', '配送单']]  
										            }),  
											allowBlank : false,
											emptyText : "请选择数据来源",
											forceSelection : true,
											fieldLabel:'数据来源',
											editable : false,
											mode : "local",//获取本地的值
											displayField : 'sourceData_text',//显示值，与fields对应
											valueField : 'sourceData',//value值，与fields对应
											hiddenName : 'sourceData',
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '来源单号',
											name : 'sourceNo',
											maxLength : 20,
											anchor : '95%'
										}]

							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
											xtype : 'numberfield',
											fieldLabel : '开票金额<span style="color:red">*</span>',
											decimalPrecision :0,
											allowNegative: false,//不允许负数
											allowBlank:false,
											blankText : "开票金额只能为数字！",
											name : 'amount',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'numberfield',
											fieldLabel : '税金<span style="color:red">*</span>',
											decimalPrecision :0,
											allowNegative: false,//不允许负数
											allowBlank:false,
											blankText : "税金只能为数字！",
											name : 'taxes',
											maxLength : 10,
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

	carTitle = '添加发票';
	if (cid != null) {
		carTitle = '修改发票';
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
					carTitle = '修改发票';
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

	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID
	var reviewStatus=Ext.getCmp("reviewStatusCombo").getValue();//审核状态
	
	Ext.apply(dateStore.baseParams, {
		start : 0,
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_customerId : customerId,
		filter_EQS_departId : departId,
		filter_EQS_reviewStatus :reviewStatus,
		privilege:privilege
	});
	dateStore.reload();

}

//审核
function confirmReview(){
	var ids="";
	var mainConter = Ext.getCmp('carGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择您要修改的行！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		ids=ids+_records[i].id+',';
	}

	Ext.Msg.confirm(alertTitle,'您确认要审核吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + confirmReviewUrl,
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
			}
		});
}

function confirmBasMail(cid){
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
										{xtype:'hidden',id:'departId',name:'departId'},
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
											disabled:true,
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
											disabled:true,
											fieldLabel:'所属部门<span style="color:red">*</span>',
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
														Ext.getCmp('departId').setValue(record
																.get("departId"));
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
											disabled:true,
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
											disabled:true,
											emptyText : "请选择收付类型",
											forceSelection : true,
											fieldLabel:'收付类型',
											editable : false,
											mode : "local",//获取本地的值
											displayField : 'paymentType_text',//显示值，与fields对应
											valueField : 'paymentType',//value值，与fields对应
											hiddenName : 'paymentType',
											anchor : '95%'
										},{
											xtype : 'combo',
											triggerAction : 'all',
											store : new Ext.data.SimpleStore({ // 填充的数据  
										                fields : [ 'sourceData',  'sourceData_text'],  
										                data   : [['对账单', '对账单'], ['配送单', '配送单']]  
										            }),  
											allowBlank : false,
											emptyText : "请选择数据来源",
											disabled:true,
											forceSelection : true,
											fieldLabel:'数据来源',
											editable : false,
											mode : "local",//获取本地的值
											displayField : 'sourceData_text',//显示值，与fields对应
											valueField : 'sourceData',//value值，与fields对应
											hiddenName : 'sourceData',
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '来源单号',
											disabled:true,
											name : 'sourceNo',
											maxLength : 20,
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '快递单号<span style="color:red">*</span>',
											allowBlank : false,
											emptyText : "请选择快递单号",
											name : 'expressNo',
											maxLength : 50,
											anchor : '95%'
										}]

							},{
								columnWidth : .5,
								layout : 'form',
								items : [ {
											xtype : 'numberfield',
											fieldLabel : '开票金额',
											decimalPrecision :0,
											allowNegative: false,//不允许负数
											allowBlank:false,
											blankText : "开票金额只能为数字！",
											disabled:true,
											name : 'amount',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'numberfield',
											fieldLabel : '税金',
											decimalPrecision :0,
											disabled:true,
											allowNegative: false,//不允许负数
											allowBlank:false,
											blankText : "税金只能为数字！",
											name : 'taxes',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '申请人',
											disabled:true,
											name : 'applicant',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '经手人',
											disabled:true,
											name : 'handled',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '开票人',
											disabled:true,
											name : 'drawer',
											maxLength : 10,
											anchor : '95%'
										},{
											xtype : 'textfield',
											fieldLabel : '备注',
											disabled:true,
											name : 'remark',
											maxLength : 200,
											anchor : '95%'
										}]

							}]
				}]
	});

	carTitle = '添加发票';
	if (cid != null) {
		carTitle = '修改发票';
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
					carTitle = '修改发票';
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
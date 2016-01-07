//var pageSize = 2;
var comboxPage = 15;
var privilege = 256;
var departGridSearchUrl = "sys/departAction!findAll.action";
var responsibleGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 负责人
var gridSearchUrl = "fi/fiHeadquarterAccountAction!ralaList.action";
var saveUrl = "fi/fiHeadquarterAccountAction!save.action";
var delUrl = "fi/fiHeadquarterAccountAction!delete.action";
var verificationUrl="fi/fiHeadquarterAccountAction!verification.action";//核销
var revocationUrl="fi/fiHeadquarterAccountAction!revocation.action";//作废
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
			name : "receiptNo", //收据号
			mapping : 'receiptNo'
		}, {
			name : "receiptDate",//收据日期
			mapping : 'receiptDate'
		}, {
			name : 'amount',//收据金额
			mapping : 'amount'
		}, {
			name : 'remark',//备注
			mapping : 'remark'
		}, {
			name : 'verificationCapId',//核销账号ID
			mapping : 'verificationCapId'
		}, {
			name : 'verificationUser',//核销人
			mapping : 'verificationUser'
		}, {
			name : 'verificationDate',//核销日期
			mapping : 'verificationDate'
		}, {
			name : 'verificationRemark',//核销备注
			mapping : 'verificationRemark'
		}, {
			name : 'status',//状态(0作废,1正常)
			mapping : 'status'
		}, {
			name : 'verificationStatus',//核销状态(1未核销,2已核销)
			mapping : 'verificationStatus'
		}, {
			name : 'accountNum',//账号
			mapping : 'accountNum'
		}, {
			name : 'accountName',//账号名称
			mapping : 'accountName'
		}, {
			name : 'bank',//开户行
			mapping : 'bank'
		}, {
			name : 'responsible',//负责人
			mapping : 'responsible'
		}, {
			name : 'responsibleId',//负责人ID
			mapping : 'responsibleId'
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
									header : '序号',
									dataIndex : 'id',
									sortable : true,
									width : 50
								}, {
									header : '收据号',
									dataIndex : 'receiptNo',
									width : 60
								},{
									header : '收据日期',
									dataIndex : 'receiptDate',
									width : 60
								},{
									header : '金额',
									dataIndex : 'amount',
									width : 60
								},{
									header : '备注',
									dataIndex : 'remark',
									width : 60
									}, {
									header : '核销状态',
									dataIndex : 'verificationStatus',
									width : 60,
									renderer:function(v,metaData){
										if (v==1){
											v='<font color="#FF0000">未核销</font>';
										}else if (v==2){
											v='已核销';
										}
										return v;
									}
								}, {
									header : '状态',
									dataIndex : 'status',
									width : 60,
									renderer:function(v,metaData){
										if(v==0){
											v='作废';
										}else if (v==1){
											v='正常';
										}
										return v;
									}
								},{
									header : '核销账号ID',
									dataIndex : 'verificationCapId',
									hidden : true,
									hideable:false
								},{
									header : '核销人',
									dataIndex : 'verificationUser',
									width : 60
								},{
									header : '核销日期',
									dataIndex : 'verificationDate',
									width : 60
								},{
									header : '核销备注',
									dataIndex : 'verificationRemark',
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
								},{
									header : '负责人',
									dataIndex : 'responsible',
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
			text : '<b>录入收据</b>',
			iconCls : 'userAdd',
			id : 'fiFiHeadquarterAccountAdd',
			tooltip : '录入收据',
			handler : function() {
				fiFiHeadquarterAccountSave(null);
			}
		}, '-','&nbsp;&nbsp;', {
			text : '<b>作废</b>',
			iconCls : 'userEdit',
			id : 'revocation',
			disabled : true,
			tooltip : '作废',
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
				revocation(_records[0].data.id);
			}
		}, '-','&nbsp;&nbsp;', {
			text : '<b>核销账户余额</b>',
			iconCls : 'userEdit',
			id : 'verificationSave',
			disabled : true,
			tooltip : '核销账户余额',
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
				verificationSave(_records[0].data.id);
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
						var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
						var revocationbtn = Ext.getCmp('revocation');// 获得作废按钮
						var verificationSavebtn=Ext.getCmp('verificationSave');//核销
						
						if (_record.length == 1) {
							verificationSavebtn.setDisabled(false);
							if(_record[0].data.status==0){
								revocationbtn.setDisabled(true);
								verificationSavebtn.setDisabled(true);
							}else if(_record[0].data.verificationStatus==2){
								revocationbtn.setDisabled(true);
								verificationSavebtn.setDisabled(true);
							}else{
								revocationbtn.setDisabled(false);
								verificationSavebtn.setDisabled(false);
							}
							
							/*if(_record.data[0].verificationStatus==2L){
								
							}*/
							
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
	var userrolebtn = Ext.getCmp('fiFiHeadquarterAccountAdd');
	var revocationbtn = Ext.getCmp('revocation');

	userrolebtn.setDisabled(false);
	revocationbtn.setDisabled(false);

	dataReload();

}

function fiFiHeadquarterAccountSave(cid) {
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 320,
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
							xtype : 'textfield',
							fieldLabel : '收据号',
							name : 'receiptNo',
							allowBlank : false,
            				blankText : "收据号不能为空！",
            				emptyText : "请输入收据号",
							maxLength : 50,
							anchor : '100%'
						}, {
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '收据日期',
							allowBlank : false,
            				blankText : "收据日期不能为空！",
            				emptyText : "请输入收据日期",
							name : 'receiptDate',
							maxLength : 20,
							anchor : '100%'
						}, {
							xtype : 'numberfield',
							id:'amount',
							fieldLabel : '收据金额',
							allowBlank : false,
            				blankText : "收据金额不能为空！",
            				emptyText : "请输入收据金额",
							name : 'amount',
							maxLength : 50,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 500,
							anchor : '100%'
						}]

			}]
		}]
	});

	Title = '添加收据';
	if (cid != null) {
		Title = '修改收据';
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
		width : 320,
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

/**核销账号余额*/
function verificationSave(cid) {
		var userIds,departIds;
	var cashprivilege = 110;
	var cashSearchUrl="fi/fiCapitaaccountsetAction!findAccountList.action";
	
	var cashsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var cashrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
	var cashfields = [{
						name : "id",
						mapping : 'id'
					}, {
						name : 'accountType',// 账号类型
						mapping : 'accountType'
					}, {
						name : 'accountType',// 账号类型
						mapping : 'accountType'
					}, {
						name : 'paymentType',// 收支类型
						mapping : 'paymentType'
					}, {
						name : 'accountTypeName',// 账号类型
						mapping : 'accountTypeName'
					},{
						name : 'accountNum',// /账号
						mapping : 'accountNum'
					}, {
						name : 'accountName',// 账号名称
						mapping : 'accountName'
					}, {
						name : 'bank',// 开户行
						mapping : 'bank'
					}, {
						name : 'responsible',// 负责人
						mapping : 'responsible'
					}];
	var cashcm=new Ext.grid.ColumnModel([cashrowNum, cashsm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								},{
									header : '账号类型id',
									dataIndex : 'accountType',
									hidden : true
								},{
									header : '账号类型',
									dataIndex : 'paymentType',
									renderer:function(v,metaData){
										if(v==14250){
											v='收入';
										}else if (v==14251){
											v='支出';
										}else if (v==27300){
											v='收支';
										}
										return v;
									},
									width : 80
								}, {
									header : '账号',
									dataIndex : 'accountNum',
									width : 80
								}, {
									header : '账号名称',
									dataIndex : 'accountName',
									width : 80
								}, {
									header : '开户行',
									dataIndex : 'bank',
									width : 80
								}, {
									header : '负责人',
									dataIndex : 'responsible',
									width : 60
								}])
		var cashTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'cashcheckItems',
				hiddenName : 'cashcheckItems',
				name : 'checkItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['LIKES_accountNum', '账号'], ['LIKES_accountName', '账号名称'], ['LIKES_bank', '开户行']],
				emptyText : '选择查询类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) {
						if (combo.getValue() == '') {
							Ext.getCmp("cashsearchSearch").disable();
							Ext.getCmp("cashsearchSearch").show();
							Ext.getCmp("cashsearchSearch").setValue("");
						} else {
							Ext.getCmp("cashsearchSearch").enable();
							Ext.getCmp("cashsearchSearch").show();
	
						}
					}
				}
	
			}, '-', {
				xtype : 'textfield',
				emptyText :'快捷查询',
				blankText : '快捷查询',
				id : 'cashsearchSearch'
			}, '-', {
				text : '<b>搜索</b>',
				id : 'cashbtn',
				iconCls : 'btnSearch',
				handler :  function() {
						cashdateStore.baseParams = {
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value
						}
	                	
						cashdateStore.reload({
									params : {
										filter_EQL_responsibleId:userId,
										filter_EQL_paymentType:14251,//付款账号:支出
										filter_EQL_isDelete:1,
										limit : pageSize,
										privilege:cashprivilege
									}
								});	
						}

				}];
		// 查询主列表
		var cashdateStore = new Ext.data.Store({
			storeId : "cashdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + cashSearchUrl,
						method:'POST'
					}),
			baseParams:{
				paymentType:14250//收款账号:收入
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'cashGrid',
					//title: '选择现金账号',
					height : 249,
					width : 500,
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
					sm : cashsm,
					cm : cashcm,
					tbar : cashTbar,
					ds : cashdateStore,
					listeners:{
	    				rowdblclick:function(grid, rowindex, e){
	    				var record=grid.getSelectionModel().getSelected();
	    				Ext.getCmp('accountName').setValue(record.data.accountName);
	    				//Ext.getCmp('bank').setValue(record.data.bank);
	    				Ext.getCmp('responsible').setValue(record.data.responsible);
	    				Ext.getCmp('verificationCapId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				Ext.getCmp('accountName').setValue(record.data.accountName);
		    				//Ext.getCmp('bank').setValue(record.data.bank);
		    				Ext.getCmp('responsible').setValue(record.data.responsible);
		    				Ext.getCmp('verificationCapId').setValue(record.data.id);
		    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
		    				Ext.getCmp('accountNum').collapse();
		    				//alert(record);
		    			}
    				}
    			});
    				
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
				items : [{
							xtype : 'hidden',
							fieldLabel : 'id',
							name : 'id'
						}, {
							id : "verificationCapId",
							name : 'verificationCapId',
							xtype : "hidden"
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'textfield',
							fieldLabel : '收据号',
							name : 'receiptNo',
							allowBlank : false,
            				blankText : "收据号不能为空！",
            				emptyText : "请输入收据号",
							maxLength : 50,
							disabled:true,
							anchor : '100%'
						}, {
							xtype : 'datefield',
							format : 'Y-m-d',
							fieldLabel : '收据日期',
							allowBlank : false,
            				blankText : "收据日期不能为空！",
            				emptyText : "请输入收据日期",
							name : 'receiptDate',
							maxLength : 20,
							disabled:true,
							anchor : '100%'
						}, {
							xtype : 'numberfield',
							id:'amount',
							fieldLabel : '收据金额',
							allowBlank : false,
            				blankText : "收据金额不能为空！",
            				emptyText : "请输入收据金额",
							name : 'amount',
							disabled:true,
							maxLength : 50,
							anchor : '100%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							disabled:true,
							name : 'remark',
							maxLength : 500,
							anchor : '100%'
						}]

			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype:'combo',
							mode: 'local',
							fieldLabel : '核销账号<span style="color:red">*</span>',
							id:'accountNum',
							name : 'appAccountName',
							maxLength : 20,
							anchor : '98%',
							width:500,
							queryParams:'accountNum',
							minChars:30,
							triggerAction:'all',
							maxHeight: 500, 
							listWidth:500,
							forceSelection:false,
							emptyText:'请输入核销账号',
							allowBlank:false,
							blankText:'核销账号不能为空!!',
							tpl: '<div id="panel" style="width:500px;height:249px"></div>',
								store:new Ext.data.SimpleStore({fields:["accountNum","accountNum"],data:[[]]}),
								enableKeyEvents:true,
								listeners : {
							 		keyup:function(combo, e){
						               if(e.getKey() == 13){
						                   //Ext.getCmp('consignee').focus(true,true);
						               }
						               else if(e.getKey()==40){
						            	   cashGrid.getSelectionModel().selectFirstRow();
						            	   var row=cashGrid.getView().getRow(0); 
											Ext.get(row).focus();
						               }else{
						               	   if((Ext.get('accountNum').dom.value).length>2){
						               	   		if((Ext.get('accountNum').dom.value).length<11)
						               	   		{
							               	   		combo.expand();
								               		cashdateStore.load({
								               			params:{
								               				filter_LIKES_accountNum:Ext.get('accountNum').dom.value
								               			}
								               		});
						               	   		}
						               	   }
						               }
							 		}
					 			}
						}, {
							xtype : 'textfield',
							fieldLabel : '核销备注',
							name : 'verificationRemark',
							maxLength : 500,
							anchor : '100%'
						},{
							xtype : 'textfield',
							fieldLabel : '账号名称',
							id:'accountName',
							name : 'accountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}/*, {
							xtype : 'textfield',
							fieldLabel : '开户行',
							id:'bank',
							name : 'bank',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}*/, {
							xtype : 'textfield',
							fieldLabel : '负责人',
							id:'responsible',
							name : 'responsible',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			}]
		}]
	});

	Title = '核销账户余额';
	if (cid != null) {
		Title = '核销账户余额';
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
						url : sysPath + '/' + verificationUrl,
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

	Ext.getCmp("accountNum").on('expand',function(){  
			if(cashGrid.rendered){
				cashGrid.doLayout();
			}else{
    			cashGrid.render('panel');
    			cashdateStore.load();
    		}
    });
	Ext.getCmp("accountNum").on('collapse',function(){
    	return false;
    }); 
}


/**
 * 作废
 */
function revocation(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择收款账号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一个收款账号！");
		return false;
	}

	Ext.Msg.confirm(alertTitle,'您确认要作废吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + revocationUrl,
						params : {
							id : _records[0].data.id,
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

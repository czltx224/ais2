//var pageSize = 1;
var privilege = 125;//出纳收款单
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiCashiercollectionAction!ralaList.action";
var saveUrl = "fi/fiCashiercollectionAction!saveCashiercollection.action";//保存出纳收款单
var delUrl = "fi/fiCashiercollectionAction!delete.action";
var saveVerificationUrl="fi/fiCashiercollectionAction!saveVerification.action";//保存出纳收款单核销
var saveManualVerificationUrl="fi/fiCashiercollectionAction!manualVerification.action";//保存手工核销
var invalidCashiercollectionUrl="fi/fiCashiercollectionAction!invalidCashiercollection.action"//作废出纳收款单核销

var departStore,maindateStore,verificationStatusStore;
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
			name : "penyJenis", // 结算方式
			mapping : 'penyJenis'
		}, {
			name : "collectionTime", // 到账时间
			mapping : 'collectionTime',
			dateFormat:'y-m-d'
		}, {
			name : "fiCapitaaccountsetId", // 到账账号
			mapping : 'fiCapitaaccountsetId'
		}, {
			name : "collectionAmount", // 到账金额
			mapping : 'collectionAmount'
		}, {
			name : "verificationAmount", //核销金额
			mapping : 'verificationAmount'
		}, {
			name : "verificationTime", // 核销时间
			mapping : 'verificationTime'
		}, {
			name : "verificationUser", // 核销人
			mapping : 'verificationUser'
		}, {
			name : "verificationDept", // 核销部门
			mapping : 'verificationDept'
		}, {
			name : "verificationStatus", // 核销状态
			mapping : 'verificationStatus'
		}, {
			name : "entrustAmount", // 委托确认金额
			mapping : 'entrustAmount'
		}, {
			name : "entrustUser", // 委托确认人
			mapping : 'entrustUser'
		}, {
			name : "entrustTime", // 委托确认时间
			mapping : 'entrustTime'
		}, {
			name : "entrustRemark", // 委托确认备注 
			mapping : 'entrustRemark'
		}, {
			name : "departName", // 所属部门表名称
			mapping : 'departName'
		}, {
			name : "accountNum", //资金账号设置表:账号
			mapping : 'accountNum'
		}, {
			name : "accountName", //资金账号设置表:账号名称
			mapping : 'accountName'
		}, {
			name : "bank", // 资金账号设置表开户行
			mapping : 'bank'
		}, {
			name : "responsible", // 负责人
			mapping : 'responsible'
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
			name : 'createRemark',
			mapping : 'createRemark'//备注
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
			name : 'status',
			mapping : 'status'
		}, {
			name : 'FiReceiptId',
			mapping : 'FiReceiptId'//单据号
		}, {
			name : 'verificationRemark',
			mapping : 'verificationRemark'//核销备注
		}];

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '收款单号',
			dataIndex : 'id',
			width:60
		}, {
			header : '账号类型',
			dataIndex : 'penyJenis',
			width:60
		}, {
			header : ' 收款账号',
			dataIndex : 'accountNum',
			width:60
		}, {
			header : '账号名称',
			dataIndex : 'accountName',
			width:80
		}, {
			header : '收款开户行',
			dataIndex : 'bank',
			width:80
		}, {
			header : '收款负责人',
			dataIndex : 'responsible',
			width:80
		}, {
			header : '到账日期',
			dataIndex : 'collectionTime',
			width:80
		}, {
			header : '到账金额',
			dataIndex : 'collectionAmount',
			width:80
		}, {
			header : '委托确认金额',
			dataIndex : 'entrustAmount',
			width:80
		}, {
			header : '收据号',
			dataIndex : 'FiReceiptId',
			width:60
		},{
			header : '核销金额',
			dataIndex : 'verificationAmount',
			width:80
		}, {
			header : ' 核销状态',
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if(v==0){
					v='<span style="color:red">未核销</span>';
				}else if (v==1){
					v='<span style="color:green">已核销</span>';
				}else{
					v="";
				}
				return v;
			},
			width:80
		}, {
			header : ' 状态',
			dataIndex : 'status',
			renderer:function(v,metaData){
				if(v==0){
					v='<span style="color:red">作废</span>';
				}else if (v==1){
					v='<span style="color:green">正常</span>';
				}else{
					v="";
				}
				return v;
			},
			width:80
		}, {
			header : '委托确认时间',
			dataIndex : 'entrustTime',
			width:80,
			hidden : true
		}, {
			header : '委托确认人',
			dataIndex : 'entrustUser',
			width:80,
			hidden : true
		}, {
			header : '委托确认备注',
			dataIndex : 'entrustRemark',
			hidden : true,
			width:80
		}, {
			header : '核销时间',
			dataIndex : 'verificationTime',
			width:80
		}, {
			header : '核销人',
			dataIndex : 'verificationUser',
			width:80
		}, {
			header : '核销备注',
			dataIndex : 'verificationRemark',
			width:80
		}, {
			header : '核销部门',
			dataIndex : 'verificationDept',
			width:80
		}, {
			header : '收款部门',
			dataIndex : 'departName',
			width:80
		}, {
			header : ' 收款账号id',
			dataIndex : 'fiCapitaaccountsetId',
			width:80,
			hidden : true
		}, {
			header : '创建部门',
			dataIndex : 'departName',
			width:80,
			hidden : true
		}, {
			header : '创建备注',
			dataIndex : 'createRemark',
			width:120
		}, {
			header : '创建部门ID',
			dataIndex : 'departId',
			hidden : true,
			hideable:false
		}, {
			header : '创建人',
			dataIndex : 'createName',
			width:80
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
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
		}]);

var tbar = [{
			text : '<b>新增</b>',
			iconCls : 'table',
			id : 'basAdd',
			tooltip : '新增',
			handler : function(){
				fiCashiercollectionSave();
			}
		}, '-', {
			text : '<b>修改</b>',
			iconCls : 'table',
			id : 'basView',
			tooltip : '修改',
			handler : function() {
				var mainGrid = Ext.getCmp('mainGrid');
				var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要修改的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能修改一行！");
					return false;
				}
				fiCashiercollectionSave(_records[0].data.id);
			}
		}, '-', {
			text : '<b>作废</b>',
			iconCls : 'table',
			id : 'invalidCashiercollection',
			tooltip : '作废',
			handler : function() {
					var mainGrid = Ext.getCmp('mainGrid');
					var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
					if (_records.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}
					Ext.Msg.confirm("系统提示", "确定要作废所选的记录吗？", function(btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									Ext.Ajax.request({
												url : sysPath + '/' + invalidCashiercollectionUrl,
												params : {
													id : _records[0].data.id,
													status:0,
													ts:_records[0].data.ts,
													privilege : privilege
												},
												success : function(resp) {
													var respText = Ext.util.JSON
															.decode(resp.responseText);
													Ext.Msg.alert("友情提示", respText.msg);
													dataReload();
												}
											});
								}
							});
			}
		}, '-', {
			text : '<b>核销单据</b>',
			iconCls : 'table',
			id : 'Verification',
			tooltip : '核销单据',
			handler : function() {
				var mainGrid = Ext.getCmp('mainGrid');
				var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要核销的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能核销一行！");
					return false;
				}
				Verification(_records[0].data.id,_records[0].data.fiCapitaaccountsetId);
			}
		}, '-', {
			text : '<b>手工核销</b>',
			iconCls : 'table',
			id : 'manualVerification',
			tooltip : '手工核销',
			handler : function() {
					var mainGrid = Ext.getCmp('mainGrid');
					var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
					if (_records.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要修改的行！");
						return false;
					} else if (_records.length > 1) {
						Ext.Msg.alert("系统消息", "一次只能修改一行！");
						return false;
					}
					manualVerification(_records[0].data.id);
			}
		}, '-', {
			text:'<b>导入</b>',iconCls:'sort_down',handler:function() {
                inExcel();
			}
		}, '-', {
			text:'<b>导入POS</b>',iconCls:'sort_down',handler:function() {
                inPosExcel();
			}
		}, '-', {
			text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('mainGrid'));}
		}, '-', {
			text : '<b>打印收据</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印收据',
			handler:function() {
				var mainConter = Ext.getCmp('mainGrid');
				var records = mainConter.getSelectionModel().getSelections();
				var paymentType="";//收付类型
				if(records.length<1){
					Ext.Msg.alert(alertTitle,"请选择一条需要打印的数据！");
					return;
				}else if(records.length>1){
					Ext.Msg.alert(alertTitle,"请分开打印的数据！");
					return;
				}
				ids=records[0].data.id;
	            parent.print('18',{print_ids:ids});

           	} 
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}];
Ext.onReady(function() {

	// 核销状态
	verificationStatusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['0', '未核销'], ['1', '已核销']],
				fields : ["verificationStatus", "verificationStatusName"]
			});
			
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

	// 查询主列表
	maindateStore = new Ext.data.Store({
				storeId : "maindateStore",
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
				//autoExpandColumn : 1,
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
				id : 'view',
				el : 'mainPanel',
				labelAlign : 'left',
				height : Ext.lib.Dom.getViewHeight()-4,
				width : Ext.lib.Dom.getViewWidth(),
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [mainGrid]
			});

	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['到账日期从', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date().add(Date.DAY, -7),
						blankText : "[到账日期从]不能为空！",
						hiddenId : 'stateDate',
						hiddenName : 'stateDate',
						id : 'stateDate'
					}, '至', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date(),
						blankText : "[到账日期至]不能为空！",
						hiddenId : 'endDate',
						hiddenName : 'endDate',
						id : 'endDate'
					}, '-', '所属部门', {
						xtype : 'combo',
						id:'departId',
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
					}, '-', '核销状态', {
						xtype : 'combo',
						id : 'comboverificationStatus',
						triggerAction : 'all',
						store : verificationStatusStore,
						allowBlank : false,
						emptyText : "请选择",
						forceSelection : true,
						editable : false,
						mode : "local",// 获取本地的值
						displayField : 'verificationStatusName',// 显示值，与fields对应
						valueField : 'verificationStatus',// value值，与fields对应
						hiddenName:'verificationStatus',
						width : 60,
						anchor : '95%'
					},'-','到账金额',
					   {xtype:'numberfield',
			 	        id : 'collectionAmount',
			 	        width : 30,
				        name : 'collectionAmount'
			        }, '-', {
						xtype : "combo",
						width : 100,
						triggerAction : 'all',
						model : 'local',
						hiddenId : 'checkItems',
						hiddenName : 'checkItems',
						name : 'checkItemstext',
						store : [['', '查询全部'],[ 'LIKES_id','出纳收款单号'],['LIKES_accountNum', '收款账号'],['LIKES_createName','录入人']],
						emptyText : '选择类型',
						forceSelection : true
					},'-','&nbsp;',
					   {xtype:'textfield',
			 	        id : 'itemsValue',
			 	        width : 70,
				        name : 'itemsValue',
			            enableKeyEvents:true
			        }]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();


	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var basViewbtn = Ext.getCmp('basView');//修改
				var invalidCashiercollectionbtn = Ext.getCmp('invalidCashiercollection');// 作废
				var verificationbtn = Ext.getCmp("Verification"); // 核销
				
				if (_record.length == 1) {
					if (basViewbtn&&_record[0].data.status==1&&_record[0].data.status==1&&_record[0].data.verificationStatus==0) {
						basViewbtn.setDisabled(false);
					}
					if (invalidCashiercollectionbtn&&_record[0].data.status==1&&_record[0].data.verificationStatus==0) {
						invalidCashiercollectionbtn.setDisabled(false);
					}
					if (verificationbtn&&_record[0].data.status==1&&_record[0].data.verificationStatus==0) {
						verificationbtn.setDisabled(false);
					}
				} else if (_record.length > 1) {
					if (basViewbtn) {
						basViewbtn.setDisabled(true);
					}
					if (invalidCashiercollectionbtn) {
						invalidCashiercollectionbtn.setDisabled(true);
					}
					if (verificationbtn) {
						verificationbtn.setDisabled(true);
					}
				} else {
					if (basViewbtn) {
						basViewbtn.setDisabled(true);
					}
					if (invalidCashiercollectionbtn) {
						invalidCashiercollectionbtn.setDisabled(true);
					}
					if (verificationbtn) {
						verificationbtn.setDisabled(true);
					}
				}
			});
});


function fiCashiercollectionSave(cid){
	var accountTypeStore;
	
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

		
	//现金收款开始
				
	//现金收款
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
						mapping : 'ID'
					}, {
						name : 'accountType',// 账号类型
						mapping : 'ACCOUNT_TYPE'
					}, {
						name : 'paymentTypeName',// 收支类型
						mapping : 'PAYMENT_TYPE_NAME'
					}, {
						name : 'accountTypeName',// 账号类型
						mapping : 'ACCOUNT_TYPE_NAME'
					},{
						name : 'accountNum',// /账号
						mapping : 'ACCOUNT_NUM'
					}, {
						name : 'accountName',// 账号名称
						mapping : 'ACCOUNT_NAME'
					}, {
						name : 'bank',// 开户行
						mapping : 'BANK'
					}, {
						name : 'openingBalance',//余额
						mapping : 'OPENING_BALANCE'
					}, {
						name : 'responsible',// 负责人
						mapping : 'RESPONSIBLE'
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
									dataIndex : 'paymentTypeName',
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
								},{
									header : '账号类型',
									dataIndex : 'accountTypeName',
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
									header : '余额',
									dataIndex : 'openingBalance',
									width : 60
								}, {
									header : '负责人',
									dataIndex : 'responsible',
									width : 60
								}])
								
		var cashTbar = ['账号', {
				xtype : 'textfield',
				width : 50,
				id : 'comboAccountNum'
			},'-','账号名称',{
				xtype : 'textfield',
				width : 50,
				id : 'comboAccountName'
			},'-', {
				text : '<b>搜索</b>',
				id : 'cashbtn',
				iconCls : 'btnSearch',
				handler :  function() {

                		//userIds=userId;
                		var accountNum=Ext.getCmp('comboAccountNum').getValue();
                		var accountName=Ext.getCmp('comboAccountName').getValue();
						cashdateStore.reload({
									params : {
										//departId:bussDepart,
										//departId:departIds,
										userId:userId,
										paymentType:14250,//收款账号:收入
										accountNum:accountNum,
										//accountType:14252,//现金
										accountName:accountName,
										privilege:cashprivilege,
										limit : pageSize
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
				userId:userId,
				paymentType:14250,//收款账号:收入
				//accountType:14252,//现金
				limit : pageSize,
				privilege:cashprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'resultMap',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		var cashGrid = new Ext.grid.GridPanel({
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
	    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('accountName').setValue(record.data.accountName);
	    				Ext.getCmp('bank').setValue(record.data.bank);
	    				Ext.getCmp('responsible').setValue(record.data.responsible);
	    				Ext.getCmp('fiCapitaaccountsetId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
		    				Ext.getCmp('accountName').setValue(record.data.accountName);
		    				Ext.getCmp('bank').setValue(record.data.bank);
		    				Ext.getCmp('responsible').setValue(record.data.responsible);
		    				Ext.getCmp('fiCapitaaccountsetId').setValue(record.data.id);
		    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
		    				Ext.getCmp('accountNum').collapse();
		    				//alert(record);
		    			}
    				}
					/*bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : cashdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})*/
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		cashdateStore.on('load',function(){
	    		if(cashGrid.getStore().getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});

		//现金收款结束
			
			
		var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 633,
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
							id : 'accountType',
							name : 'accountType'
						},{
							xtype : 'hidden',
							fieldLabel : 'id',
							name : 'id'
						},{
							xtype : 'hidden',
							id : 'fiCapitaaccountsetId',
							name : 'fiCapitaaccountsetId'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'numberfield',
							fieldLabel : '到账金额<span style="color:red">*</span>',
							name : 'collectionAmount',
							maxLength : 50,
							emptyText:'请输入到账金额',
							allowBlank:false,
							blankText:'到账金额不能为空!!',
							anchor : '98%'
						},{
							xtype : 'datefield',
							fieldLabel : '到账日期<span style="color:red">*</span>',
							format : 'Y-m-d',
							name : 'collectionTime',
							maxLength : 20,
							emptyText:'请输入到账日期',
							allowBlank:false,
							blankText:'到账日期不能为空!!',
							anchor : '98%'
						}, {
							xtype:'combo',
							mode: 'local',
							fieldLabel : '收款账号<span style="color:red">*</span>',
							id:'accountNum',
							name : 'accountNum',
							maxLength : 20,
							anchor : '98%',
							width:500,
							queryParams:'accountNum',
							minChars:30,
							triggerAction:'all',
							maxHeight: 500, 
							listWidth:500,
							forceSelection:false,
							emptyText:'请输入收款账号',
							allowBlank:false,
							blankText:'收款账号不能为空!!',
							//hideTrigger:true,
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
							fieldLabel : '到账备注',
							name : 'createRemark',
							maxLength : 50,
							
							anchor : '98%'
						}]

			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
							xtype : 'textfield',
							fieldLabel : '账号类型',
							id:'penyJenis',
							name : 'penyJenis',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '账号名称',
							id:'accountName',
							name : 'accountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '开户行',
							id:'bank',
							name : 'bank',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
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

	Title = '添加出纳收款单';
	if (cid != null) {
		Title = '修改出纳收款单';
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
		width : 650,
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
					var penyJenis=Ext.getCmp('penyJenis').getValue();
					var fiCapitaaccountsetId=Ext.getCmp('fiCapitaaccountsetId').getValue();
					if(fiCapitaaccountsetId==""){
						Ext.Msg.alert("友情提示","收款账号没有选中，请重新选择！");
						return;
					}

					form.getForm().submit({
						url : sysPath + '/' + saveUrl,
						params : {
							penyJenis:penyJenis,
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

	})

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
function dataReload() {
	dateStore.reload();
}

//出纳收款单核销
function Verification(cid,fiCashiercollectionId){
	var verificationType;
	var accountTypeStore;
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
		
//委托收款单开始
	var userIds,departIds;
	var cashprivilege = 118;
	var cashSearchUrl="fi/fiPaidAction!ralaList.action";
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
					name : "paymentType", // 收付类型(收款单/付款单)
					mapping : 'paymentType'
				}, {
					name : "paymentStatus", // 客商id
					mapping : 'paymentStatus'////收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常
				}, {
					name : "costType", // 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
					mapping : 'costType'
				}, {
					name : "documentsType", // 单据大类
					mapping : 'documentsType'
				}, {
					name : "documentsSmalltype", // 单据小类
					mapping : 'documentsSmalltype'
				}, {
					name : "documentsNo", //单据小类
					mapping : 'documentsNo'
				}, {
					name : "penyType", // 结算类型
					mapping : 'penyType'
				}, {
					name : "penyJenis", // 结算方式
					mapping : 'penyJenis'
				}, {
					name : "settlementAmount", // 已收付金额
					mapping : 'settlementAmount'
				}, {
					name : "contacts", // 往来单位
					mapping : 'contacts'
				}, {
					name : "customerId", // 客商id
					mapping : 'customerId'
				}, {
					name : "customerName", // 客商名称
					mapping : 'customerName'
				}, {
					name : "fiPaymentId", // 应收单号
					mapping : 'fiPaymentId'
				}, {
					name : "accountNum", // 收付款账号
					mapping : 'accountNum'
				}, {
					name : "accountName", // 账号名称
					mapping : 'accountName'
				}, {
					name : "bank", // 开户行
					mapping : 'bank'
				}, {
					name : "fiInvoiceId", //收据号
					mapping : 'fiInvoiceId'
				}, {
					name : "verificationAmount", // 核销金额
					mapping : 'verificationAmount'
				}, {
					name : "verificationStatus", // 核销状态
					mapping : 'verificationStatus'
				}, {
					name : "verificationDept", // 核销部门
					mapping : 'verificationDept'
				}, {
					name : "verificationUser", // 核销人
					mapping : 'verificationUser'
				}, {
					name : "verificationTime", // 核销时间
					mapping : 'verificationTime'
				}, {
					name : 'createDept',// 创建部门
					mapping : 'createDept'
				}, {
					name : 'createDeptid',// 创建部门ID
					mapping : 'createDeptid'
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
		
		var cashcm = new Ext.grid.ColumnModel([rowNum, sm, {
					header : '实收付单号',
					dataIndex : 'id',
					width:65
				}, {
					header : '应收付单号',
					dataIndex : 'fiPaymentId',
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
					header : '收付状态',
					dataIndex : 'paymentStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='作废';
						}else if (v==1){
							v='未收款';
						}else if (v==2){
							v='已收款';
						}else if (v==3){
							v='部分收款';
						}else if(v==4){
							v='未付款';
						}else if(v==5){
							v='已付款';
						}else if(v==6){
							v='部分付款';
						}else if(v==7){
							v='到付转欠款';
						}else if(v==8){
							v='异常';
						}
						return v;
					},
					width:65
				}, {
					header : '实收付金额',
					dataIndex : 'settlementAmount',
					width:65
				}, {
					header : '核销金额',
					dataIndex : 'verificationAmount',
					width:65
				}, {
					header : '核销状态',
					dataIndex : 'verificationStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='未核销';
						}else if (v==1){
							v='<font color="#FF0000">已核销</font>';
						}
						return v;
					},
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
					header : '结算类型',
					dataIndex : 'penyType',
					width:65
				}, {
					header : '结算方式',
					dataIndex : 'penyJenis',
					width:65
				}, {
					header : '往来单位',
					dataIndex : 'contacts',
					width:65
				}, {
					header : '客商id',
					dataIndex : 'customerId',
					hidden : true
				}, {
					header : '客商名称',
					dataIndex : 'customerName',
					width:65
				}, {
					header : '收付账号',
					dataIndex : 'accountNum',
					width:65
				}, {
					header : '账号名称',
					dataIndex : 'accountName',
					width:65
				}, {
					header : '开户行',
					dataIndex : 'bank',
					width:65
				}, {
					header : '收据号',
					dataIndex : 'fiInvoiceId',
					width:65
				}, {
					header : '核销部门',
					dataIndex : 'verificationDept',
					width:65
				}, {
					header : '核销人',
					dataIndex : 'verificationUser',
					width:65
				}, {
					header : '核销时间',
					dataIndex : 'verificationTime',
					width:65
				}, {
					header : '创建部门',
					dataIndex : 'createDept',
					width:65
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
					hidden : true,
					hideable:false
				}]);
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
						['EQL_settlementAmount', '金额'], ['LIKES_createDept', '收款部门']],
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
							filter_EQL_fiCashiercollectionId:cid,
							privilege:cashprivilege,
							limit : pageSize,
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value
						}
						cashdateStore.reload();	
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
				filter_EQL_fiCashiercollectionId:cid,
				privilege:cashprivilege,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		var cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'cashGrid',
					height : 240,
					width : 608,
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
					sm : cashsm,
					cm : cashcm,
					tbar : cashTbar,
					ds : cashdateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : cashdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//委托收款单结束


//资金交接单核销开始
	var userIds,departIds,selectIds;
	var fundstransferprivilege = 132;
	var fundstransferSearchUrl="fi/fiFundstransferAction!ralaList.action";
	
	var fundstransfersm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var fundstransferrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
	var fundstransferfields = [{
								name : "id",
								mapping : 'id'
							}, {
								name : 'paymentaccountId',// 付款账号ID
								mapping : 'paymentaccountId'
							}, {
								name : 'receivablesaccountId',// 收款账号ID
								mapping : 'receivablesaccountId'
							}, {
								name : 'receivablesaccountDeptid',// 收款部门ID
								mapping : 'receivablesaccountDeptid'
							}, {
								name : "paymentAank", //付款开户行
								mapping : 'paymentAank'
							}, {
								name : "paymentAccountName", //付款账号名称
								mapping : 'paymentAccountName'
							}, {
								name : 'paymentAccountNum',//付款账号
								mapping : 'paymentAccountNum'
							}, {
								name : 'receivablesaccountDept',// 收款部门
								mapping : 'receivablesaccountDept'
							}, {
								name : 'receivablesAccountNum',// 收款账号
								mapping : 'receivablesAccountNum'
							}, {
								name : 'receivablesAccountName',//收款账号名称
								mapping : 'receivablesAccountName'
							}, {
								name : 'receivablesAank',// 收款开户行
								mapping : 'receivablesAank'
							}, {
								name : 'receivablesResponsible',//收款负责人
								mapping : 'receivablesResponsible'
							}, {
								name : 'amount',//结算金额
								mapping : 'amount'
							}, {
								name : 'remark',//备注
								mapping : 'remark'
							}, {
								name : 'status',//状态：1:未核销、2:已核销、0:已作废
								mapping : 'status'
							}, {
								name : 'createDept',
								mapping : 'createDept'
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
	var fundstransfercm=new Ext.grid.ColumnModel([fundstransferrowNum, fundstransfersm, {
									header : 'id',
									dataIndex : 'id',
									sortable : true,
									hidden : true
								},{
									header : 'paymentaccountId',
									dataIndex : 'paymentaccountId',
									hidden : true,
									hideable:false
								},{
									header : 'receivablesaccountId',
									dataIndex : 'receivablesaccountId',
									hidden : true,
									hideable:false
								},{
									header : 'receivablesaccountDeptid',
									dataIndex : 'receivablesaccountDeptid',
									hidden : true,
									hideable:false
								}, {
									header : '付款账号',
									dataIndex : 'paymentAccountNum',
									width : 120
								},{
									header : '付款账号名称',
									dataIndex : 'paymentAccountName',
									width : 80
								},{
									header : '付款开户行',
									dataIndex : 'paymentAank',
									width : 80
								}, {
									header : '付款金额',
									dataIndex : 'amount',
									width : 60
								}, {
									header : '收款部门',
									dataIndex : 'receivablesaccountDept',
									width : 80
								}, {
									header : '收款账号',
									dataIndex : 'receivablesAccountNum',
									width : 120
								}, {
									header : '收款账号名称',
									dataIndex : 'receivablesAccountName',
									width : 80
								}, {
									header : '收款开户行',
									dataIndex : 'receivablesAank',
									width:80
								}, {
									header : ' 收款负责人',
									dataIndex : 'receivablesResponsible',
									width:80
								}, {
									header : '备注',
									dataIndex : 'remark',
									width : 80
								}, {
									header : '状态',
									dataIndex : 'status',//状态：1:未核销、2:已核销、0:已作废
									renderer:function(v,metaData){
										if(v==0){
											v='作废';
										}else if (v==1){
											v='未核销';
										}else if (v==2){
											v='已核销';
										}
										return v;
									},width : 60
								}, {
									header : '创建部门',
									dataIndex : 'createDept',
									width : 80,
									hidden : true
								}, {
									header : '创建人',
									dataIndex : 'createName',
									width : 60
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
								}]);
		var fundstransferTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'fundstransferItems',
				hiddenName : 'fundstransferItems',
				name : 'fundstransferItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['LIKES_paymentAccountNum', '付款账号'], ['LIKES_paymentAccountName', '付款账号名称'], ['EQS_amount', '上交金额'], ['LIKES_createName', '上交人']],
				emptyText : '选择查询类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) {
						if (combo.getValue() == '') {
							Ext.getCmp("fundstransfersearchSearch").disable();
							Ext.getCmp("fundstransfersearchSearch").show();
							Ext.getCmp("fundstransfersearchSearch").setValue("");
						} else {
							Ext.getCmp("fundstransfersearchSearch").enable();
							Ext.getCmp("fundstransfersearchSearch").show();
	
						}
					}
				}
	
			}, '-', {
				xtype : 'textfield',
				emptyText :'快捷查询',
				blankText : '快捷查询',
				id : 'fundstransfersearchSearch'
			}, '-', {
				text : '<b>搜索</b>',
				id : 'fundstransferbtn',
				iconCls : 'btnSearch',
				handler :  function() {
						fundstransferdateStore.baseParams = {
							filter_EQL_receivablesaccountId:fiCashiercollectionId,
							filter_EQL_status:1,
							filter_EQL_paymentStatus:1,
							privilege:fundstransferprivilege,
							limit : pageSize,
							checkItems : Ext.get("fundstransferItems").dom.value,
							itemsValue : Ext.get("fundstransfersearchSearch").dom.value
						}
						fundstransferdateStore.reload();	
						}

				}];
		// 查询主列表
		var fundstransferdateStore = new Ext.data.Store({
			storeId : "fundstransferdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + fundstransferSearchUrl,
						method:'POST'
					}),
			baseParams:{
				filter_EQL_receivablesaccountId:fiCashiercollectionId,
				filter_EQL_status:1,
				privilege:fundstransferprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, fundstransferfields)
			});
		var fundstransferGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'fundstransferGrid',
					height : 240,
					width : 608,
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
					sm : fundstransfersm,
					cm : fundstransfercm,
					tbar : fundstransferTbar,
					ds : fundstransferdateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : fundstransferdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//资金交接单核销
				
//支票核销开始
	var userIds,departIds;
	var checkprivilege = 118;
	var checkSearchUrl="fi/fiPaidAction!ralaList.action";
	var checksm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var checkrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
		var checkfields = [{
					name : "id",
					mapping : 'id'
				}, {
					name : "paymentType", // 收付类型(收款单/付款单)
					mapping : 'paymentType'
				}, {
					name : "paymentStatus", // 客商id
					mapping : 'paymentStatus'////收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常
				}, {
					name : "costType", // 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
					mapping : 'costType'
				}, {
					name : "documentsType", // 单据大类
					mapping : 'documentsType'
				}, {
					name : "documentsSmalltype", // 单据小类
					mapping : 'documentsSmalltype'
				}, {
					name : "documentsNo", //单据小类
					mapping : 'documentsNo'
				}, {
					name : "penyType", // 结算类型
					mapping : 'penyType'
				}, {
					name : "penyJenis", // 结算方式
					mapping : 'penyJenis'
				}, {
					name : "settlementAmount", // 已收付金额
					mapping : 'settlementAmount'
				}, {
					name : "contacts", // 往来单位
					mapping : 'contacts'
				}, {
					name : "customerId", // 客商id
					mapping : 'customerId'
				}, {
					name : "customerName", // 客商名称
					mapping : 'customerName'
				}, {
					name : "fiPaymentId", // 应收单号
					mapping : 'fiPaymentId'
				}, {
					name : "accountNum", // 收付款账号
					mapping : 'accountNum'
				}, {
					name : "accountName", // 账号名称
					mapping : 'accountName'
				}, {
					name : "bank", // 开户行
					mapping : 'bank'
				}, {
					name : "fiInvoiceId", //收据号
					mapping : 'fiInvoiceId'
				}, {
					name : "verificationAmount", // 核销金额
					mapping : 'verificationAmount'
				}, {
					name : "verificationStatus", // 核销状态
					mapping : 'verificationStatus'
				}, {
					name : "verificationDept", // 核销部门
					mapping : 'verificationDept'
				}, {
					name : "verificationUser", // 核销人
					mapping : 'verificationUser'
				}, {
					name : "verificationTime", // 核销时间
					mapping : 'verificationTime'
				}, {
					name : 'createDept',// 创建部门
					mapping : 'createDept'
				}, {
					name : 'createDeptid',// 创建部门ID
					mapping : 'createDeptid'
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
		
		var checkcm = new Ext.grid.ColumnModel([rowNum, sm, {
					header : '实收付单号',
					dataIndex : 'id',
					width:65
				}, {
					header : '应收付单号',
					dataIndex : 'fiPaymentId',
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
					header : '收付状态',
					dataIndex : 'paymentStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='作废';
						}else if (v==1){
							v='未收款';
						}else if (v==2){
							v='已收款';
						}else if (v==3){
							v='部分收款';
						}else if(v==4){
							v='未付款';
						}else if(v==5){
							v='已付款';
						}else if(v==6){
							v='部分付款';
						}else if(v==7){
							v='到付转欠款';
						}else if(v==8){
							v='异常';
						}
						return v;
					},
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
					header : '结算类型',
					dataIndex : 'penyType',
					width:65
				}, {
					header : '结算方式',
					dataIndex : 'penyJenis',
					width:65
				}, {
					header : '实收付金额',
					dataIndex : 'settlementAmount',
					width:65
				}, {
					header : '往来单位',
					dataIndex : 'contacts',
					width:65
				}, {
					header : '客商id',
					dataIndex : 'customerId',
					hidden : true
				}, {
					header : '客商名称',
					dataIndex : 'customerName',
					width:65
				}, {
					header : '收付账号',
					dataIndex : 'accountNum',
					width:65
				}, {
					header : '账号名称',
					dataIndex : 'accountName',
					width:65
				}, {
					header : '开户行',
					dataIndex : 'bank',
					width:65
				}, {
					header : '收据号',
					dataIndex : 'fiInvoiceId',
					width:65
				}, {
					header : '核销金额',
					dataIndex : 'verificationAmount',
					width:65
				}, {
					header : '核销状态',
					dataIndex : 'verificationStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='未核销';
						}else if (v==1){
							v='已核销';
						}
						return v;
					},
					width:65
				}, {
					header : '核销部门',
					dataIndex : 'verificationDept',
					width:65
				}, {
					header : '核销人',
					dataIndex : 'verificationUser',
					width:65
				}, {
					header : '核销时间',
					dataIndex : 'verificationTime',
					width:65
				}, {
					header : '创建部门',
					dataIndex : 'createDept',
					width:65
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
					hidden : true,
					hideable:false
				}]);
		var checkTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'checkcheckItems',
				hiddenName : 'checkcheckItems',
				name : 'checkItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['EQS_settlementAmount', '实收金额'], ['LIKES_createDept', '收款部门']],
				emptyText : '选择查询类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) {
						if (combo.getValue() == '') {
							Ext.getCmp("checksearchSearch").disable();
							Ext.getCmp("checksearchSearch").show();
							Ext.getCmp("checksearchSearch").setValue("");
						} else {
							Ext.getCmp("checksearchSearch").enable();
							Ext.getCmp("checksearchSearch").show();
	
						}
					}
				}
	
			}, '-', {
				xtype : 'textfield',
				emptyText :'快捷查询',
				blankText : '快捷查询',
				id : 'checksearchSearch'
			}, '-', {
				text : '<b>搜索</b>',
				id : 'checkbtn',
				iconCls : 'btnSearch',
				handler :  function() {
						checkdateStore.baseParams = {
							filter_EQS_penyJenis:'支票',
							privilege:checkprivilege,
							limit : pageSize,
							checkItems : Ext.get("checkcheckItems").dom.value,
							itemsValue : Ext.get("checksearchSearch").dom.value
						}
						checkdateStore.reload();	
						}

				}];
		// 查询主列表
		var checkdateStore = new Ext.data.Store({
			storeId : "checkdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + checkSearchUrl,
						method:'POST'
					}),
			baseParams:{
				//filter_EQL_ficheckiercollectionId:cid,
				filter_EQS_penyJenis:'支票',
				privilege:checkprivilege,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, checkfields)
			});
		var checkGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'checkGrid',
					height : 240,
					width : 608,
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
					sm : checksm,
					cm : checkcm,
					tbar : checkTbar,
					ds : checkdateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : checkdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//支票核销结束

			
//POS核销开始
	var posprivilege = 118;
	var posSearchUrl="fi/fiPaidAction!ralaList.action";
	var possm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var posrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
		var posfields = [{
					name : "id",
					mapping : 'id'
				}, {
					name : "paymentType", // 收付类型(收款单/付款单)
					mapping : 'paymentType'
				}, {
					name : "paymentStatus", // 客商id
					mapping : 'paymentStatus'////收付状态：0作废、1未收款、2已收款、3部分收款、4未付款、5已付款、6部分付款、7到付转欠款、8异常
				}, {
					name : "costType", // 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
					mapping : 'costType'
				}, {
					name : "documentsType", // 单据大类
					mapping : 'documentsType'
				}, {
					name : "documentsSmalltype", // 单据小类
					mapping : 'documentsSmalltype'
				}, {
					name : "documentsNo", //单据小类
					mapping : 'documentsNo'
				}, {
					name : "penyType", // 结算类型
					mapping : 'penyType'
				}, {
					name : "penyJenis", // 结算方式
					mapping : 'penyJenis'
				}, {
					name : "settlementAmount", // 已收付金额
					mapping : 'settlementAmount'
				}, {
					name : "contacts", // 往来单位
					mapping : 'contacts'
				}, {
					name : "customerId", // 客商id
					mapping : 'customerId'
				}, {
					name : "customerName", // 客商名称
					mapping : 'customerName'
				}, {
					name : "fiPaymentId", // 应收单号
					mapping : 'fiPaymentId'
				}, {
					name : "accountNum", // 收付款账号
					mapping : 'accountNum'
				}, {
					name : "accountName", // 账号名称
					mapping : 'accountName'
				}, {
					name : "bank", // 开户行
					mapping : 'bank'
				}, {
					name : "fiInvoiceId", //收据号
					mapping : 'fiInvoiceId'
				}, {
					name : "verificationAmount", // 核销金额
					mapping : 'verificationAmount'
				}, {
					name : "verificationStatus", // 核销状态
					mapping : 'verificationStatus'
				}, {
					name : "verificationDept", // 核销部门
					mapping : 'verificationDept'
				}, {
					name : "verificationUser", // 核销人
					mapping : 'verificationUser'
				}, {
					name : "verificationTime", // 核销时间
					mapping : 'verificationTime'
				}, {
					name : 'createDept',// 创建部门
					mapping : 'createDept'
				}, {
					name : 'createDeptid',// 创建部门ID
					mapping : 'createDeptid'
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
		
		var poscm = new Ext.grid.ColumnModel([rowNum, sm, {
					header : '实收付单号',
					dataIndex : 'id',
					width:65
				}, {
					header : '应收付单号',
					dataIndex : 'fiPaymentId',
					width:65,
					hidden : true
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
					header : '费用类型',
					dataIndex : 'costType',
					width:65
				}, {
					header : '金额',
					dataIndex : 'settlementAmount',
					width:65
				}, {
					header : '已核销金额',
					dataIndex : 'verificationAmount',
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
					header : '结算类型',
					dataIndex : 'penyType',
					width:65
				}, {
					header : '结算方式',
					dataIndex : 'penyJenis',
					width:65
				}, {
					header : '往来单位',
					dataIndex : 'contacts',
					width:65
				}, {
					header : '客商id',
					dataIndex : 'customerId',
					hidden : true
				}, {
					header : '客商名称',
					dataIndex : 'customerName',
					width:65
				}, {
					header : '收付账号',
					dataIndex : 'accountNum',
					width:65
				}, {
					header : '账号名称',
					dataIndex : 'accountName',
					width:65
				}, {
					header : '开户行',
					dataIndex : 'bank',
					width:65
				}, {
					header : '收据号',
					dataIndex : 'fiInvoiceId',
					width:65
				}, {
					header : '核销状态',
					dataIndex : 'verificationStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='未核销';
						}else if (v==1){
							v='已核销';
						}
						return v;
					},
					width:65
				}, {
					header : '核销部门',
					dataIndex : 'verificationDept',
					width:65
				}, {
					header : '核销人',
					dataIndex : 'verificationUser',
					width:65
				}, {
					header : '核销时间',
					dataIndex : 'verificationTime',
					width:65
				}, {
					header : '收付状态',
					dataIndex : 'paymentStatus',
					renderer:function(v,metaData){
						if(v==0){
							v='作废';
						}else if (v==1){
							v='未收款';
						}else if (v==2){
							v='已收款';
						}else if (v==3){
							v='部分收款';
						}else if(v==4){
							v='未付款';
						}else if(v==5){
							v='已付款';
						}else if(v==6){
							v='部分付款';
						}else if(v==7){
							v='到付转欠款';
						}else if(v==8){
							v='异常';
						}
						return v;
					},
					width:65
				}, {
					header : '创建部门',
					dataIndex : 'createDept',
					width:65
				}, {
					header : '创建部门ID',
					dataIndex : 'createDeptId',
					hidden : true
				}, {
					header : '创建人',
					dataIndex : 'createName'
				}, {
					header : '创建时间',
					dataIndex : 'createTime',
					width:110,
					sortable : true
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
		var posTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'posposItems',
				hiddenName : 'posposItems',
				name : 'posItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['EQS_settlementAmount', '实收金额'], ['LIKES_createName', '收款人'], ['LIKES_createDept', '收款部门']],
				emptyText : '选择查询类型',
				forceSelection : true,
				listeners : {
					'select' : function(combo, record, index) {
						if (combo.getValue() == '') {
							Ext.getCmp("possearchSearch").disable();
							Ext.getCmp("possearchSearch").show();
							Ext.getCmp("possearchSearch").setValue("");
						} else {
							Ext.getCmp("possearchSearch").enable();
							Ext.getCmp("possearchSearch").show();
	
						}
					}
				}
	
			}, '-', {
				xtype : 'textfield',
				emptyText :'快捷查询',
				blankText : '快捷查询',
				id : 'possearchSearch'
			}, '-', {
				text : '<b>搜索</b>',
				id : 'posbtn',
				iconCls : 'btnSearch',
				handler :  function() {
						posdateStore.baseParams = {
							filter_EQS_penyJenis:'POS',
							filter_EQS_paymentType:1,
							filter_EQS_verificationStatus:0,
							privilege:posprivilege,
							limit : pageSize,
							checkItems : Ext.get("posposItems").dom.value,
							itemsValue : Ext.get("possearchSearch").dom.value
						}
						posdateStore.reload();	
						}

				}];
		// 查询主列表
		var posdateStore = new Ext.data.Store({
			storeId : "posdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + posSearchUrl,
						method:'POST'
					}),
			baseParams:{
				//filter_EQL_fiposiercollectionId:cid,
				filter_EQS_penyJenis:'POS',
				filter_EQS_verificationStatus:1,
				privilege:posprivilege,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, posfields)
			});
		var posGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'posGrid',
					height : 240,
					width : 608,
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
					sm : possm,
					cm : poscm,
					tbar : posTbar,
					ds : posdateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : posdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
//POS核销结束
			
	 var tabs = new Ext.TabPanel({
	    width:610,
	    activeTab: 0,
	    frame:true,
	    //plain:true,
	    defaults:{autoHeight: true},
	    items:[{
		    		title: '委托收款单',
		    		id:'1',
		        	items:[cashGrid]
	        	},{ 
		        	title: '支票',
		        	id:'2',
		        	items:[checkGrid]
	        	},{ 
		        	title: '资金上交单',
		        	id:'3',
		        	items:[fundstransferGrid]
	        	},{ 
		        	title: 'POS核销',
		        	id:'4',
		        	items:[posGrid]
	        	}
	    	]
	    });
		var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 633,
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields),
		labelAlign : "right",
		items : [{
			xtype:'fieldset',
            title: '出纳收款单',
            labelWidth: 80,
            baseCls:"x-fieldset",
            autoHeight: true,
            items: [{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'hidden',
									id : 'accountType',
									name : 'accountType'
								},{
									xtype : 'hidden',
									fieldLabel : 'id',
									name : 'id'
								},{
									xtype : 'hidden',
									id : 'fiCapitaaccountsetId',
									name : 'fiCapitaaccountsetId'
								}, {
									name : "ts",
									xtype : "hidden"
								}, {
									xtype : 'numberfield',
									fieldLabel : '到账金额',
									name : 'collectionAmount',
									maxLength : 50,
									emptyText:'请输入到账金额',
									allowBlank:false,
									blankText:'到账金额不能为空!!',
									disabled:true,
									anchor : '98%'
								},{
									xtype : 'datefield',
									fieldLabel : '到账日期',
									format : 'Y-m-d',
									name : 'collectionTime',
									maxLength : 20,
									emptyText:'请输入到账日期',
									allowBlank:false,
									blankText:'到账日期不能为空!!',
									disabled:true,
									anchor : '98%'
								}, {
									xtype:'combo',
									mode: 'local',
									fieldLabel : '收款账号',
									id:'accountNum',
									name : 'accountNum',
									maxLength : 20,
									anchor : '98%',
									width:500,
									queryParams:'accountNum',
									minChars:30,
									triggerAction:'all',
									maxHeight: 500, 
									listWidth:500,
									forceSelection:false,
									emptyText:'请输入收款账号',
									allowBlank:false,
									blankText:'收款账号不能为空!!',
									disabled:true
								}, {
									xtype : 'textfield',
									fieldLabel : '到账备注',
									name : 'createRemark',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '核销摘要',
									id : 'verificationRemark',
									name : 'verificationRemark',
									emptyText:'请输入核销摘要',
									allowBlank:false,
									blankText:'核销摘要不能为空!!',
									maxLength : 50,
									anchor : '98%'
								}]
		
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
									xtype : 'textfield',
									fieldLabel : '账号类型',
									id:'penyJenis',
									name : 'penyJenis',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '账号名称',
									id:'accountName',
									name : 'accountName',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '开户行',
									id:'bank',
									name : 'bank',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
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
		},tabs]
	});
	
	form.load({
				url : sysPath + "/" + gridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});

			
	var win = new Ext.Window({
		title : '出纳收款单核销',
		width : 650,
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
				if(tabs.getActiveTab().id=="1"){
					verificationType="委托收款单";
					var _records = cashGrid.getSelectionModel().getSelections();// 获取所有选中行
					selectIds=_records[0].data.id;//选择的出纳收款单
				}else if(tabs.getActiveTab().id=="3"){
					verificationType="资金上交单";
					var _records = fundstransferGrid.getSelectionModel().getSelections();// 获取所有选中行
					selectIds=_records[0].data.id;//选择的资金上交单
				}else if(tabs.getActiveTab().id=="2"){
					verificationType="支票";
					var _records = checkGrid.getSelectionModel().getSelections();// 获取所有选中行
					selectIds=_records[0].data.id;//选择的支票
				}else if(tabs.getActiveTab().id=="4"){
					verificationType="POS";
					var _records = posGrid.getSelectionModel().getSelections();// 获取所有选中行
					selectIds=_records[0].data.id;//选择的支票
				}
				
				//核销备注
				var verificationRemark=Ext.getCmp('verificationRemark').getValue();
				
				if (form.getForm().isValid()) {
					this.disabled = true;// 只能点击一次
					form.getForm().submit({
						url : sysPath + '/' + saveVerificationUrl,
						params : {
							verificationType:verificationType,//核销单据类型
							verificationRemark:verificationRemark,//核销备注
							fiCashiercollectionId:cid,//收款单ID
							ids:selectIds,//被核销单据ID
							privilege : privilege
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							if(action.result.success==true){
								win.hide();
							}else{
								this.disabled =false;
							}
							Ext.Msg.alert("友情提示",
									action.result.msg, function() {
										if(action.result.success==true){
												dataReload();
											}
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									//win.hide();
									this.disabled =false;
									Ext.Msg.alert("友情提示", action.result.msg,
											function() {
												//dataReload();
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

	})

	win.on('hide', function() {
				form.destroy();
			});
	
	win.show();
}
/**
 * 查询对账信息
 */
function searchmain() {
	maindateStore.baseParams = {
		limit:pageSize,
		privilege : privilege,
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("itemsValue").dom.value
	}
	
	dataReload();
}

function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var collectionAmount=Ext.getCmp("collectionAmount").getValue();
	var comboverificationStatus = Ext.getCmp("comboverificationStatus").getValue(); // 核销状态
	
	var departId = Ext.getCmp("departId").getValue(); // 所属部门
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_collectionTime : stateDate,
		filter_LED_collectionTime : endDate,
		filter_EQS_departId : departId,
		filter_EQS_collectionAmount : collectionAmount,
		filter_EQS_verificationStatus:comboverificationStatus,
		privilege:privilege
	});
	maindateStore.reload();
}
	
function inExcel(){
		batchNo='';
    	var fields1=[
			{name:'id'},//主单号
			{name:'doMoneyData'},         //件数
			{name:'accountNum'},        //重量
			{name:'companyName'},   // 车次号自动增长
			{name:'amount'},       //签单号
			{name:'batchNo'},
			{name:'matchStatus'},
			{name:'remark'}];
    	
    	var jsonread1= new Ext.data.JsonReader({
              root:'result',
              totalProperty:'totalCount'},
              fields1);
    	
    	var totalStore = new Ext.data.Store({
    			id:'totalStore',
                proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiCashiercollectionExcelAction!list.action"}),
                baseParams:{
                	limit: pageSize,
					filter_EQL_batchNo:batchNo
                },
                reader:jsonread1
        });
        
    	var oneBar = new Ext.Toolbar({
					id:'onebar',
					    items :  ['&nbsp;&nbsp;',{
					    				text:'<B>导出模版</B>',
										id : 'total',
										iconCls:'userEdit',
										handler : function(){
											window.location.href=cashExcelUrl;
										}
									},'-','&nbsp;&nbsp;',{
					    				text:'<B>导入Excel</B>',
										id : 'submitbtn5',
										//disabled : true,
										iconCls:'sort_down',
										handler : function(){
											doExcel();
										}
									},'-','&nbsp;&nbsp;',{
										text:'<B>清  空</B>',
										id : 'submitbtn4',
										//disabled : true,
										iconCls:'refresh',
										handler : function (){
											Ext.Msg.confirm(alertTitle,'清空后数据，只能重新导入Excel。您确定要清空所有数据吗?',function(btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													batchNo="-1";
													
													totalStore.proxy = new Ext.data.HttpProxy({
														url:sysPath+"/fi/fiCashiercollectionExcelAction!list.action"
													});
													
													Ext.apply(totalStore.baseParams={
														limit : pageSize,
										 	 			filter_EQL_batchNo:batchNo
													});
													
													totalStore.reload({
														params : {
															start : 0,
															limit : pageSize
														}
													});
												}
											});
									}
									},'-','&nbsp;&nbsp;',{
										text:'<B>保  存</B>',
										id : 'dnoInfo',
										disabled : false,
										iconCls:'userAdd',
										handler : function (){
											Ext.Ajax.request({
												url:sysPath+"/fi/fiCashiercollectionExcelAction!list.action",
												params:{
													filter_EQL_batchNo:batchNo,
													filter_EQL_matchStatus:0
												},
												success : function(response) { // 回调函数有1个参数
													 if(Ext.decode(response.responseText).result==''){
													 		form.getForm().submit({
																url : sysPath + "/fi/fiCashiercollectionAction!saveExcelData.action",
																params:{
																	privilege:privilege,
																	batchNo:batchNo,
																	limit : pageSize
																},
																waitMsg : '正在保存数据...',
																success : function(form, action) {
																	win.hide();
																	Ext.Msg.alert(alertTitle,"保存成功!",function(){
																		win.close();
																		maindateStore.reload();
																	});
																},
																failure : function(form, action) {
																	if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																		Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																	} else {
																		if (action.result.msg) {
																			win.hide();
																			Ext.Msg.alert(alertTitle,
																					action.result.msg);
																		}
																	}
																}
															});
													 
													 
													 }else{
													 	Ext.MessageBox.alert(alertTitle, '存在未匹配的数据，不能保存到数据库(或者在下一页)。');
													 }
												},
												failure : function(response) {
													Ext.MessageBox.alert(alertTitle, '数据匹配状态验证失败');
												}
											});
										}
									},'-','&nbsp;&nbsp;',{
										text:'<B>退  出</B>',
										id : 'submitbtn3',
										//disabled : true,
										iconCls:'btnSearch',
										handler : function (){
											win.close();
										}
									},'&nbsp;&nbsp;',{
					    			xtype:'label',
					    			id:'showMsg',
					    			width:200
					    		}
				]
				
				
	});
    	
    	
    	var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 35,
			sortable : true
		});
		
    	var addArriveRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'myrecordGrid1',
    				height:435,
    				width : 630,
    				border:true,
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				tbar:oneBar,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				  // forceFit: true,
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([
    						rowNum, 
    						{header : 'ID',dataIndex:'id',width:35}, 
    						{header : '到账日期',dataIndex : 'doMoneyData',width:80}, 
    						{header : '账号',dataIndex : 'accountNum',width:80},
    						{header : '客户名称',dataIndex : 'companyName',width:80},
    						{header : '金额',dataIndex : 'amount',width:60},
    						{header : '匹配状态',dataIndex : 'matchStatus',width:60,
    							renderer:function(v){
										if(v=='0'){
											return '<span style="color:red" >未匹配</span>';
										}else if(v=='1'){
											return '<span style="color:green" >已匹配</span>';
										}else{
											return '出错，无状态';
										}
								}
    						}, 
    						{header : '批次号',dataIndex : 'batchNo',width:80},
    						{header : '备注',dataIndex:'remark',width:80},
    						{header : '操作',width:60,dataIndex:'id' ,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
	    							//删除一条记录
	    							return "<a href='#' onclick='delTotalStore("+rowIndex+");'>删除</a>";
	    						}
    						}
    						
    				]),
    				ds : totalStore,
    				bbar : new Ext.PagingToolbar({
						pageSize : pageSize, 
						store : totalStore,
						displayInfo : true,
						displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
						emptyMsg : "没有记录信息显示"
					})
    			});
    	   
    		var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					border : false,
					bodyStyle : 'padding:0px 0px 0px',
					labelAlign : "right",
					labelWidth : 80,
					width : 640,
					reader :jsonread1,
					height:480,
					items:[{
							layout:'form',
							items:[
								addArriveRecord
							]}]
    		});
    		
    		var win = new Ext.Window({
			title : '出纳收款单导入',
			width : 650,
			height:480,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center"
		});
		win.on('hide', function() {
					form.destroy();
				});
		win.show();
		
		function doExcel(){
			
			var form2 = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								fileUpload : true,
								bodyStyle : 'padding:1px 1px 1px',
							    width : 480,
							    labelWidth :100,
					            labelAlign : "right",
						        items : [
									{xtype : 'textfield',
									 inputType : 'file',
									 fieldLabel : '导入出纳收款单',
									 name : 'upLoadExcel',
									 id:'upLoadExcel',
									 anchor : '100%'
									}]
										});
			
				var win = new Ext.Window({
					title : 'Excel导入',
					width : 500,
					closeAction : 'hide',
					plain : true,
				    resizable : false,	
					modal : true,
					items : form2,
					buttonAlign : "center",	buttons : [{
						text : "保存",
						id:'savebtn',
						iconCls : 'groupSave',
						handler : function() {
											if (form2.getForm().isValid()) {
														this.disabled = true;//只能点击一次
														form2.getForm().submit({
															url : sysPath+ '/fi/fiCashiercollectionExcelAction!saveExcel.action',
															method : 'post',
															params:{
																privilege:privilege
															},
															waitMsg : '正在处理数据...',
															success : function(form, action) {
																win.hide(), 
																Ext.Msg.alert(alertTitle,action.result.msg, function(){
																	batchNo=action.result.batchNo;
																	
																	totalStore.proxy = new Ext.data.HttpProxy({
																		url:sysPath+"/fi/fiCashiercollectionExcelAction!list.action"
																	});
																	
																	Ext.apply(totalStore.baseParams={
																		limit : pageSize,
														 	 			filter_EQL_batchNo:batchNo
																	});
																	
																	totalStore.reload({
																		params : {
																			start : 0,
																			limit : pageSize
																		},callback:function(){
																			batchNo=action.result.batchNo;
																		}
																	});
																});
															},
															failure : function(form, action) {
																if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																	Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																} else {
																	if (action.result.msg) {
																		win.hide();
																		Ext.Msg.alert(alertTitle,action.result.msg, function() {
																				//	dataStore.reload();
																				});
									
																	}
																}
															}
														});
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
							form2.destroy();
						});
				
				win.show();
			}
    	
}

//导入POS
function inPosExcel(){
		batchNo='';
    	var fields1=[
			{name:'id'},
			{name:'posNo'},         //终端编号
			{name:'transactionNumber'},        //交易笔数
			{name:'amount'},   // 交易金额
			{name:'feeAmount'},       //手续费
			{name:'settlemenAmount'},//结算金额
			{name:'collectionDept'},//部门
			{name:'collectionTime'},//日期
			{name:'accountNum'},//入帐卡号
			{name:'accountName'},//户名
			{name:'merchanCode'},//银行商户编号
			{name:'batchNo'}];
    	
    	var jsonread1= new Ext.data.JsonReader({
              root:'result',
              totalProperty:'totalCount'},
              fields1);
    	
    	var totalStore = new Ext.data.Store({
    			id:'totalStore',
                proxy: new Ext.data.HttpProxy({url:sysPath+"/fi/fiExcelPosAction!list.action"}),
                baseParams:{
                	limit: pageSize,
					filter_EQL_batchNo:batchNo
                },
                reader:jsonread1
        });
        
    	var oneBar = new Ext.Toolbar({
					id:'onebar',
					    items :  ['&nbsp;&nbsp;',{
					    				text:'<B>导出模版</B>',
										id : 'total',
										iconCls:'userEdit',
										handler : function(){
											window.location.href=posExcelUrl;
										}
									},'-','&nbsp;&nbsp;',{
					    				text:'<B>导入Excel</B>',
										id : 'submitbtn5',
										//disabled : true,
										iconCls:'sort_down',
										handler : function(){
											doPosExcel();
										}
									},'-','&nbsp;&nbsp;',{
										text:'<B>清  空</B>',
										id : 'submitbtn4',
										//disabled : true,
										iconCls:'refresh',
										handler : function (){
											Ext.Msg.confirm(alertTitle,'清空后数据，只能重新导入Excel。您确定要清空所有数据吗?',function(btnYes) {
												if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
													batchNo="-1";
													
													totalStore.proxy = new Ext.data.HttpProxy({
														url:sysPath+"/fi/fiExcelPosAction!list.action"
													});
													
													Ext.apply(totalStore.baseParams={
														limit : pageSize,
										 	 			filter_EQL_batchNo:batchNo
													});
													
													totalStore.reload({
														params : {
															start : 0,
															limit : pageSize
														}
													});
												}
											});
									}
									},'-','&nbsp;&nbsp;',{
										text:'<B>保  存</B>',
										id : 'dnoInfo',
										disabled : false,
										iconCls:'userAdd',
										handler : function (){
													 		form.getForm().submit({
																url : sysPath + "/fi/fiCashiercollectionAction!saveExcelPosData.action",
																params:{
																	privilege:privilege,
																	batchNo:batchNo,
																	limit : pageSize
																},
																waitMsg : '正在保存数据...',
																success : function(form, action) {
																	win.hide();
																	Ext.Msg.alert(alertTitle,"保存成功!",function(){
																		win.close();
																		maindateStore.reload();
																	});
																},
																failure : function(form, action) {
																	if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																		Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																	} else {
																		if (action.result.msg) {
																			win.hide();
																			Ext.Msg.alert(alertTitle,
																					action.result.msg);
																		}
																	}
																}
															});
										}
									},'-','&nbsp;&nbsp;',{
										text:'<B>退  出</B>',
										id : 'submitbtn3',
										//disabled : true,
										iconCls:'btnSearch',
										handler : function (){
											win.close();
										}
									},'&nbsp;&nbsp;',{
					    			xtype:'label',
					    			id:'showMsg',
					    			width:200
					    		}
				]
				
				
	});
    	
    	
    	var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 35,
			sortable : true
		});
		
    	var addArriveRecord = new Ext.grid.GridPanel({
    				region : "center",
    				id : 'myrecordGrid1',
    				height:435,
    				width : 630,
    				border:true,
    				autoScroll : true,
    				//autoSizeColumns: true,
    				frame : false,
    				tbar:oneBar,
    				loadMask : true,
    				stripeRows : true,
    				viewConfig:{
    				  // forceFit: true,
    				   scrollOffset: 0
    				},
    				cm : new Ext.grid.ColumnModel([
    						rowNum, 
    						{header : 'ID',dataIndex:'id',width:35}, 
    						{header : '终端编号',dataIndex : 'posNo',width:80}, 
    						{header : '交易笔数',dataIndex : 'transactionNumber',width:80},
    						{header : '交易金额',dataIndex : 'amount',width:80},
    						{header : '手续费',dataIndex : 'feeAmount',width:60},
    						{header : '结算金额',dataIndex : 'settlemenAmount',width:60},
    						{header : '部门',dataIndex : 'collectionDept',width:60},
    						{header : '日期',dataIndex : 'collectionTime',width:60},
    						{header : '入帐卡号',dataIndex : 'accountNum',width:60},
    						{header : '户名',dataIndex : 'accountName',width:60},
    						{header : '银行商户编号',dataIndex : 'merchanCode',width:60},
    						{header : '批次号',dataIndex : 'batchNo',width:80},
    						{header : '备注',dataIndex:'remark',width:80},
    						{header : '操作',width:60,dataIndex:'id' ,renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
	    							//删除一条记录
	    							return "<a href='#' onclick='delTotalStore("+rowIndex+");'>删除</a>";
	    						}
    						}
    						
    				]),
    				ds : totalStore,
    				bbar : new Ext.PagingToolbar({
						pageSize : pageSize, 
						store : totalStore,
						displayInfo : true,
						displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
						emptyMsg : "没有记录信息显示"
					})
    			});
    	   
    		var form = new Ext.form.FormPanel({
    				id:'addForm',
					frame : true,
					border : false,
					bodyStyle : 'padding:0px 0px 0px',
					labelAlign : "right",
					labelWidth : 80,
					width : 640,
					reader :jsonread1,
					height:480,
					items:[{
							layout:'form',
							items:[
								addArriveRecord
							]}]
    		});
    		
    		var win = new Ext.Window({
			title : '导入POS',
			width : 650,
			height:480,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center"
		});
		win.on('hide', function() {
					form.destroy();
				});
		win.show();
		
		function doPosExcel(){
			
			var form2 = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
								fileUpload : true,
								bodyStyle : 'padding:1px 1px 1px',
							    width : 480,
							    labelWidth :100,
					            labelAlign : "right",
						        items : [
									{xtype : 'textfield',
									 inputType : 'file',
									 fieldLabel : '导入POS',
									 name : 'upLoadExcel',
									 id:'upLoadExcel',
									 anchor : '100%'
									}]
										});
			
				var win = new Ext.Window({
					title : 'Excel导入',
					width : 500,
					closeAction : 'hide',
					plain : true,
				    resizable : false,	
					modal : true,
					items : form2,
					buttonAlign : "center",	buttons : [{
						text : "保存",
						id:'savebtn',
						iconCls : 'groupSave',
						handler : function() {
											if (form2.getForm().isValid()) {
														this.disabled = true;//只能点击一次
														form2.getForm().submit({
															url : sysPath+ '/fi/fiExcelPosAction!saveExcel.action',
															method : 'post',
															params:{
																privilege:privilege
															},
															waitMsg : '正在处理数据...',
															success : function(form, action) {
																win.hide(), 
																Ext.Msg.alert(alertTitle,action.result.msg, function(){
																	batchNo=action.result.batchNo;
																	
																	totalStore.proxy = new Ext.data.HttpProxy({
																		url:sysPath+"/fi/fiExcelPosAction!list.action"
																	});
																	
																	Ext.apply(totalStore.baseParams={
																		limit : pageSize,
														 	 			filter_EQL_batchNo:batchNo
																	});
																	
																	totalStore.reload({
																		params : {
																			start : 0,
																			limit : pageSize
																		},callback:function(){
																			batchNo=action.result.batchNo;
																		}
																	});
																});
															},
															failure : function(form, action) {
																if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
																	Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
																} else {
																	if (action.result.msg) {
																		win.hide();
																		Ext.Msg.alert(alertTitle,action.result.msg, function() {
																				//	dataStore.reload();
																				});
									
																	}
																}
															}
														});
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
							form2.destroy();
						});
				
				win.show();
			}
    	
}
	

	function delTotalStore(rowIndex,totalStore){
			Ext.Msg.confirm(alertTitle, "确定要删除这条数据吗？", function(btnYes) {
			   	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			    	var totalStore=Ext.StoreMgr.get("totalStore");
					Ext.Ajax.request({
						url : sysPath+'/fi/fiCashiercollectionExcelAction!delete.action',
						params:{
							ids:totalStore.getAt(rowIndex).get('id')+","
						},
						success : function(response) { // 回调函数有1个参数
							totalStore.reload({
								params : {
									start : 0,
									limit : pageSize
								}
							});
						},
						failure : function(response) {
							Ext.MessageBox.alert(alertTitle, '数据删除失败');
						}
					});
			   }
			});
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
		Ext.Msg.alert("提示","正在开发中...");
	}
	
	

//出纳收款单核销
function manualVerification(cid){
	var verificationType;
	var accountTypeStore;
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

		var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,
		bodyStyle : 'padding:5px 5px 0',
		width : 633,
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields),
		labelAlign : "right",
		items : [{
			xtype:'fieldset',
            title: '出纳收款单',
            labelWidth: 80,
            baseCls:"x-fieldset",
            autoHeight: true,
            items: [{
					layout : 'column',
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'hidden',
									id : 'accountType',
									name : 'accountType'
								},{
									xtype : 'hidden',
									fieldLabel : 'id',
									name : 'id'
								},{
									xtype : 'hidden',
									id : 'fiCapitaaccountsetId',
									name : 'fiCapitaaccountsetId'
								}, {
									name : "ts",
									xtype : "hidden"
								}, {
									xtype : 'numberfield',
									fieldLabel : '到账金额',
									name : 'collectionAmount',
									maxLength : 50,
									emptyText:'请输入到账金额',
									allowBlank:false,
									blankText:'到账金额不能为空!!',
									disabled:true,
									anchor : '98%'
								},{
									xtype : 'datefield',
									fieldLabel : '到账日期',
									format : 'Y-m-d',
									name : 'collectionTime',
									maxLength : 20,
									emptyText:'请输入到账日期',
									allowBlank:false,
									blankText:'到账日期不能为空!!',
									disabled:true,
									anchor : '98%'
								}, {
									xtype:'combo',
									mode: 'local',
									fieldLabel : '收款账号',
									id:'accountNum',
									name : 'accountNum',
									maxLength : 20,
									anchor : '98%',
									width:500,
									queryParams:'accountNum',
									minChars:30,
									triggerAction:'all',
									maxHeight: 500, 
									listWidth:500,
									forceSelection:false,
									emptyText:'请输入收款账号',
									allowBlank:false,
									blankText:'收款账号不能为空!!',
									disabled:true
								}, {
									xtype : 'textfield',
									fieldLabel : '到账备注',
									name : 'createRemark',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '核销摘要',
									id : 'verificationRemark',
									name : 'verificationRemark',
									emptyText:'请输入核销摘要',
									allowBlank:false,
									blankText:'核销摘要不能为空!!',
									maxLength : 50,
									anchor : '98%'
								}]
		
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
									xtype : 'textfield',
									fieldLabel : '账号类型',
									id:'penyJenis',
									name : 'penyJenis',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '账号名称',
									id:'accountName',
									name : 'accountName',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
									xtype : 'textfield',
									fieldLabel : '开户行',
									id:'bank',
									name : 'bank',
									maxLength : 50,
									disabled:true,
									anchor : '98%'
								}, {
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
		}]
	});
	
	form.load({
				url : sysPath + "/" + gridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});

			
	var win = new Ext.Window({
		title : '出纳收款单手工核销',
		width : 650,
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
				//核销备注
				var verificationRemark=Ext.getCmp('verificationRemark').getValue();
				
Ext.Msg.confirm("系统提示", "确定要手工核销所选的记录吗？", function(btnYes) {
	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				if (form.getForm().isValid()) {
					this.disabled = true;// 只能点击一次
					form.getForm().submit({
						url : sysPath + '/' + saveManualVerificationUrl,
						params : {
							//verificationRemark:verificationRemark,//核销备注
							privilege : privilege
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							if(action.result.success==true){
								win.hide();
							}else{
								this.disabled =false;
							}
							Ext.Msg.alert("友情提示",
									action.result.msg, function() {
										if(action.result.success==true){
												dataReload();
											}
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									//win.hide();
									this.disabled =false;
									Ext.Msg.alert("友情提示", action.result.msg,
											function() {
												//dataReload();
											});

								}
							}
						}
					});
				}
		}
	});
			}
		}, {

			text : "重置",
			iconCls : 'refresh',
			handler : function() {

				if (cid == null) {
					form.getForm().reset();
				}
				if (cid != null) {
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

	})

	win.on('hide', function() {
				form.destroy();
			});
	
	win.show();
}
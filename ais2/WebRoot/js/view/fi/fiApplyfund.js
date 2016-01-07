//var pageSize = 1;
var privilege = 237;//资金申请
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiApplyfundAction!ralaList.action";
var saveUrl = "fi/fiApplyfundAction!saveApplyfund.action";//保存
var delUrl = "fi/fiApplyfundAction!delete.action";
var auditApplyfundUrl="fi/fiApplyfundAction!auditApplyfund.action";//审核
var invalidApplyfundUrl="fi/fiApplyfundAction!invalidApplyfund.action";//作废
var fundstransferSitUrl="fi/fiApplyfundAction!fundstransferSit.action";//坐支
var receivablesConfirmationUrl="fi/fiApplyfundAction!receivablesConfirmation.action";

var cashGrid;
var departStore,maindateStore,statusStore,fundTypeStore;
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
			name : "fundType", //申请类型id
			mapping : 'fundType'
		}, {
			name : "fundTypeName", //申请类型
			mapping : 'fundTypeName'
		}, {
			name : "isSit", // 是否坐支(1:是、2:否)
			mapping : 'isSit'
		}, {
			name : "paymentAccountId", //拨付账号id
			mapping : 'paymentAccountId'
		}, {
			name : "paymentAccountName", // 拨付账号
			mapping : 'paymentAccountName'
		}, {
			name : "amount", //申请金额
			mapping : 'amount'
		}, {
			name : "paymentAmount", //审批金额
			mapping : 'paymentAmount'
		}, {
			name : "appRemark", //申请备注
			mapping : 'appRemark'
		}, {
			name : "paymentRemark", //审批备注
			mapping : 'paymentRemark'
		}, {
			name : "appAccountId", //申请账号ID
			mapping : 'appAccountId'
		}, {
			name : "appAccountName", //申请账号
			mapping : 'appAccountName'
		}, {
			name : "status", //状态：0:已作废、1:已否决、2:已申请、3、已审核、4:已坐支
			mapping : 'status'
		}, {
			name : "auditStatus", //审核状态(1:未审核\2:已审核)
			mapping : 'auditStatus'
		}, {
			name : "receivablesStatus", //收款确认状态：1:未确认、2:已确认
			mapping : 'receivablesStatus'
		}, {
			name : "receivablesTime", //收款确认时间
			mapping : 'receivablesTime'
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
		}];

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : 'ID',
			dataIndex : 'id',
			width:40
		}, {
			header : '申请类型',
			dataIndex : 'fundTypeName',
			width:60
		}, {
			header : ' 拨付类型',
			renderer:function(v,metaData){
				if(v==1){
					v='坐支';
				}else if (v==2){
					v='直接拨付';
				}else{
					v="";
				}
				return v;
			},
			dataIndex : 'isSit',
			width:60
		}, {
			header : ' 审核状态',
			renderer:function(v,metaData){
				if(v==1){
					v='<font color="#FF0000">未审核</font>';
				}else if (v==2){
					v='已审核';
				}else{
					v="异常";
				}
				return v;
			},
			dataIndex : 'auditStatus',
			width:60
		}, {
			header : ' 确收状态',
			renderer:function(v,metaData){
				if(v==1){
					v='<font color="#FF0000">未确收</font>';
				}else if (v==2){
					v='已确收';
				}else{
					v="异常";
				}
				return v;
			},
			dataIndex : 'receivablesStatus',
			width:60
		}, {
			header : '确收时间',
			dataIndex : 'receivablesTime',
			hidden : true,
			width:80
		}, {
			header : '拨付账号',
			dataIndex : 'paymentAccountName',
			width:80
		}, {
			header : '申请金额',
			dataIndex : 'amount',
			width:80
		}, {
			header : '审批金额',
			dataIndex : 'paymentAmount',
			width:80
		}, {
			header : '申请备注',
			dataIndex : 'appRemark',
			width:80
		}, {
			header : '审批备注',
			dataIndex : 'paymentRemark',
			width:80
		}, {
			header : '申请账号',
			dataIndex : 'appAccountName',
			width:80
		}, {
			header : '状态',
			dataIndex : 'status',
			renderer:function(v,metaData){
				if(v==0){
					v='<span style="color:red">已作废</span>';
				}else if (v==1){
					v='<span style="color:red">已否决</span>';
				}else if (v==2){
					v='已申请';
				}else if (v==4){
					v='已确收';
				}else if (v==5){
					v='已坐支';
				}else{
					v="异常";
				}
				return v;
			},
			width:80
		}, {
			header : '创建部门',
			dataIndex : 'departName',
			width:80,
			hidden : true
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
			header : '审核人',
			dataIndex : 'auditName',
			width:80
		}, {
			header : '审核时间',
			dataIndex : 'auditTime',
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
				fiApplyfundSave(null);
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
				fiApplyfundSave(_records);
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
												url : sysPath + '/' + invalidApplyfundUrl,
												params : {
													id : _records[0].data.id,
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
			iconCls : 'table',
			id : 'verification',
			tooltip : '审核',
			handler : function() {
				var mainGrid = Ext.getCmp('mainGrid');
				var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要审核的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能核销一行！");
					return false;
				}
				verification(_records);
			}
		}, '-', {
			text:'<b>生成资金交接坐支单</b>',
			iconCls:'table',
			id : 'fundstransferSit',
			handler:function() {
				var mainGrid = Ext.getCmp('mainGrid');
				var _records = mainGrid.getSelectionModel().getSelections();// 获取所有选中行
				if (_records.length < 1) {
					Ext.Msg.alert("系统消息", "请选择您要审核的行！");
					return false;
				} else if (_records.length > 1) {
					Ext.Msg.alert("系统消息", "一次只能核销一行！");
					return false;
				}
                fundstransferSit(_records);
			}
		},{
			text : '<b>收款确认</b>',
			iconCls : 'table',
			id : 'receivablesConfirmation',
			//disabled : true,
			tooltip : '收款确认',
			handler : function() {
					var car = Ext.getCmp('mainGrid');
					var _records = car.getSelectionModel().getSelections();// 获取所有选中行
					receivablesConfirmation(_records);
				}
			}, '-', {
			text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('mainGrid'));}
		},'-',
			 {
				text : '<b>打印</b>',
				iconCls : 'table',
				id : 'printbtn',
				tooltip : '打印',
				handler : function(){
					var records = Ext.getCmp('mainGrid').getSelectionModel().getSelections();
									
					if(records.length<1){
						Ext.Msg.alert(alertTitle,"请选择要打印的资金交接单据！");
						return;
					}else if (records.length>1){
						Ext.Msg.alert(alertTitle,"一次只允许打印一个资金交接单！");
						return;
					}
					
					if(records[0].data.auditStatus==2){
						parent.print('16',{print_id:records[0].data.id});
					}else{
						Ext.Msg.alert(alertTitle,"请审核后再打印！");
						return;
					}
				}
			}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}];
Ext.onReady(function() {

	// 核销状态
	statusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['0', '已作废'], ['1', '已否决'], ['2', '已申请'], ['3', '已审核'], ['4', '已坐支']],
				fields : ["status", "statusName"]
			});
			
	// 申请类型
	fundTypeStore = new Ext.data.Store({
				storeId : "fundTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 262,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'fundType',
									mapping : 'id'
								}, {
									name : 'fundTypeName',
									mapping : 'typeName'
								}])
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
			items : ['创建时间从', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date().add(Date.DAY, -7),
						blankText : "[创建时间从]不能为空！",
						hiddenId : 'stateDate',
						hiddenName : 'stateDate',
						id : 'stateDate'
					}, '至', {
						xtype : 'datefield',
						format : 'Y-m-d',
						width : 82,
						value : new Date(),
						blankText : "[创建时间至]不能为空！",
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
					}, '-', '状态', {
						xtype : 'combo',
						id : 'comboStatus',
						triggerAction : 'all',
						store : statusStore,
						emptyText : "请选择",
						mode : "local",// 获取本地的值
						displayField : 'statusName',// 显示值，与fields对应
						valueField : 'status',// value值，与fields对应
						hiddenName:'status',
						width : 60,
						anchor : '95%'
					},'-','申请金额',
					   {xtype:'numberfield',
			 	        id : 'amount',
			 	        width : 30,
				        name : 'amount'
			        }, '-', {
						xtype : "combo",
						width : 70,
						triggerAction : 'all',
			
						model : 'local',
						hiddenId : 'checkItems',
						hiddenName : 'checkItems',
						name : 'checkItemstext',
						store : [['', '查询全部'],['LIKES_id', '申请单号'],['LIKES_createName', '申请人']],
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
				var addbtn = Ext.getCmp('basAdd');// 获得新增按钮
				var updatebtn = Ext.getCmp("basView"); // 获得修改按钮
				var invalidCashiercollectionbtn=Ext.getCmp('invalidCashiercollection');//作废
				var verificationbtn=Ext.getCmp('verification');//审核
				var fundstransferSitbtn=Ext.getCmp('fundstransferSit');//生成坐支单
				var receivablesConfirmationbtn=Ext.getCmp('receivablesConfirmation');//收款确认
				if (_record.length == 1) {
					if(addbtn) addbtn.setDisabled(false);
					
					if(_record[0].data.status==0){
						updatebtn.setDisabled(true);//修改;
						invalidCashiercollectionbtn.setDisabled(true);//作废;
						verificationbtn.setDisabled(true);//审核;
						fundstransferSitbtn.setDisabled(true);//生成坐支单;
						receivablesConfirmationbtn.setDisabled(true);//收款确认;
					}else if(_record[0].data.auditStatus==1){//未审核
						updatebtn.setDisabled(false);//修改;
						invalidCashiercollectionbtn.setDisabled(false);//作废;
						verificationbtn.setDisabled(false);//审核;
						fundstransferSitbtn.setDisabled(true);//生成坐支单;
						receivablesConfirmationbtn.setDisabled(true);//收款确认;
					}else if(_record[0].data.receivablesStatus==1){//未确收
						updatebtn.setDisabled(true);//修改;
						invalidCashiercollectionbtn.setDisabled(true);//作废;
						verificationbtn.setDisabled(true);//审核;
						if(_record[0].data.isSit==1){//坐支
							fundstransferSitbtn.setDisabled(false);//生成坐支单;
							receivablesConfirmationbtn.setDisabled(true);//收款确认
						}else{
							fundstransferSitbtn.setDisabled(true);//生成坐支单;
							receivablesConfirmationbtn.setDisabled(false);//收款确认
						}
					}else{
						updatebtn.setDisabled(true);//修改;
						invalidCashiercollectionbtn.setDisabled(true);//作废;
						verificationbtn.setDisabled(true);//审核;
						fundstransferSitbtn.setDisabled(true);//生成坐支单;
						receivablesConfirmationbtn.setDisabled(true);//收款确认;
					}
					/*
					if(updatebtn &&(_record[0].data.status==0||receivablesConfirmationbtn&&_record[0].data.auditStatus==2||_record[0].data.receivablesStatus==2)){
						updatebtn.setDisabled(true);
						invalidCashiercollectionbtn.setDisabled(true);
					} else{
						updatebtn.setDisabled(false);
						invalidCashiercollectionbtn.setDisabled(false);
					}
					
					if(verificationbtn &&(_record[0].data.status==0&&_record[0].data.auditStatus==2||_record[0].data.receivablesStatus==2)){
						verificationbtn.setDisabled(true);
					} else{
						verificationbtn.setDisabled(false);
					}
					
					if(fundstransferSitbtn &&_record[0].data.auditStatus==2&&_record[0].data.isSit==1){
						fundstransferSitbtn.setDisabled(false);
					} else{
						fundstransferSitbtn.setDisabled(true);
					}
					
					if(receivablesConfirmationbtn&&_record[0].data.auditStatus==2&&_record[0].data.receivablesStatus==1){
						receivablesConfirmationbtn.setDisabled(false);
					}else{
						receivablesConfirmationbtn.setDisabled(true);
					}
*/
					
				}
				
			});
});


function fiApplyfundSave(records){
	var accountTypeStore;
	var cid,appAccountId;
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

		
	var userIds,departIds;
	var cashprivilege = 110;
	var cashSearchUrl="fi/fiCapitaaccountsetAction!ralaList.action";
	
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
						name : 'paymentTypeName',// 收支类型
						mapping : 'paymentTypeName'
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
						name : 'openingBalance',//余额
						mapping : 'openingBalance'
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
				filter_EQL_responsibleId:userId,
				filter_EQL_paymentType:14251,//付款账号:支出
				filter_EQL_isDelete:1,
				limit : pageSize,
				privilege:cashprivilege
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
	    				Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
	    				Ext.getCmp('accountName').setValue(record.data.accountName);
	    				Ext.getCmp('bank').setValue(record.data.bank);
	    				Ext.getCmp('responsible').setValue(record.data.responsible);
	    				Ext.getCmp('appAccountId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
		    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
		    				Ext.getCmp('accountName').setValue(record.data.accountName);
		    				Ext.getCmp('bank').setValue(record.data.bank);
		    				Ext.getCmp('responsible').setValue(record.data.responsible);
		    				Ext.getCmp('appAccountId').setValue(record.data.id);
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
		/*cashdateStore.on('load',function(){
	    		if(cashdateStore.getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});*/

			
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
							fieldLabel : 'id',
							name : 'id'
						},{
							xtype : 'hidden',
							id : 'appAccountId',
							name : 'appAccountId'
						}, {
							name : "ts",
							xtype : "hidden"
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '申请类型<span style="color:red">*</span>',
							store : fundTypeStore,
							triggerAction : 'all',
							valueField : 'fundType',
							displayField : 'fundTypeName',
							hiddenName : 'fundType',
							emptyText : "请选择申请类型",
							allowBlank : false,
							blankText : "申请类型不能为空！",
							anchor : '98%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '申请金额<span style="color:red">*</span>',
							name : 'amount',
							maxLength : 50,
							emptyText:'请输入申请金额',
							allowBlank:false,
							blankText:'申请金额不能为空!!',
							anchor : '98%'
						}, {
							xtype:'combo',
							mode: 'local',
							fieldLabel : '申请账号<span style="color:red">*</span>',
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
							emptyText:'请输入申请账号',
							allowBlank:false,
							blankText:'申请账号不能为空!!',
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
							fieldLabel : '申请备注',
							name : 'appRemark',
							maxLength : 50,
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '账号类型',
							id:'accountTypeName',
							name : 'accountTypeName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
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
							fieldLabel : '余额',
							id:'openingBalance',
							name : 'openingBalance',
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

	Title = '新增资金申请单';
	if (records != null) {
		Title = '修改资金申请单';
		cid = records[0].data.id;// 获取所有选中行
		appAccountId=records[0].data.appAccountId;//申请账号
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
				
				
		cashdateStore.load({
			params : {
				filter_EQS_id : appAccountId
			},callback:function(r,options,success){
				if (success) {
					for (var i = 0; i < r.length; i++) {
						var record = r[i];
	    				Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
	    				Ext.getCmp('accountName').setValue(record.data.accountName);
	    				Ext.getCmp('bank').setValue(record.data.bank);
	    				Ext.getCmp('responsible').setValue(record.data.responsible);
	    				Ext.getCmp('appAccountId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
			        }
				}
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
					var fiCapitaaccountsetId=Ext.getCmp('appAccountId').getValue();
					if(fiCapitaaccountsetId==""){
						Ext.Msg.alert("友情提示","申请账号没有选中，请重新选择！");
						return;
					}

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

//审批
function verification(records){
	var cid = records[0].data.id;// 获取所有选中行
	var appAccountId=records[0].data.appAccountId;//申请账号

	var userIds;
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
										departId:bussDepart,
										userId:userId,
										paymentType:14251,//收款账号:支出
										limit : pageSize
									}
								});	
						}

				}];
				
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
										departId:bussDepart,
										userId:userId,
										paymentType:14250,//收款账号:收入
										accountNum:accountNum,
										accountType:14252,//现金
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
				departId:bussDepart,
				userId:userId,
				paymentType:14251//收款账号:支出
			},
			reader : new Ext.data.JsonReader({
						root : 'resultMap',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'cashGrid',
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
	    				Ext.getCmp('accountTypeName1').setValue(record.data.accountTypeName);
	    				Ext.getCmp('openingBalance1').setValue(record.data.openingBalance);
	    				Ext.getCmp('accountName1').setValue(record.data.accountName);
	    				Ext.getCmp('bank1').setValue(record.data.bank);
	    				Ext.getCmp('responsible1').setValue(record.data.responsible);
	    				Ext.getCmp('paymentAccountId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    				Ext.getCmp('paymentAmount').setValue(Ext.getCmp('amount').getValue());
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				Ext.getCmp('accountTypeName1').setValue(record.data.accountTypeName);
		    				Ext.getCmp('openingBalance1').setValue(record.data.openingBalance);
		    				Ext.getCmp('accountName1').setValue(record.data.accountName);
		    				Ext.getCmp('bank1').setValue(record.data.bank);
		    				Ext.getCmp('responsible1').setValue(record.data.responsible);
		    				Ext.getCmp('paymentAccountId').setValue(record.data.id);
		    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
		    				Ext.getCmp('accountNum').collapse();
		    				Ext.getCmp('paymentAmount').setValue(Ext.getCmp('amount').getValue());
		    				//alert(record);
		    			}
    				}
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		/*cashdateStore.on('load',function(){
	    		if(cashdateStore.getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});*/

			
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
							fieldLabel : 'id',
							name : 'id'
						},{
							xtype : 'hidden',
							id : 'paymentAccountId',
							name : 'paymentAccountId'
						},{
							xtype : 'hidden',
							id : 'status',
							name : 'status'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'numberfield',
							fieldLabel : '申请金额<span style="color:red">*</span>',
							id:'amount',
							name : 'amount',
							maxLength : 50,
							emptyText:'请输入申请金额',
							allowBlank:false,
							blankText:'申请金额不能为空!!',
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '申请账号',
							name : 'appAccountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '申请备注',
							name : 'appRemark',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '账号类型',
							id:'accountTypeName',
							name : 'accountTypeName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
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
							fieldLabel : '余额',
							id:'openingBalance',
							name : 'openingBalance',
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

			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
			                xtype: 'radiogroup',
			                fieldLabel: '类型',
			                items: [
			                    {boxLabel: '是否坐支',
			                     name: 'isSit',
			                     inputValue: 1,
			                     listeners : {
				                    'check' : function(checkbox, checked){ 
				                    	 if(checked){//只有在点击时触发
				                    	 	Ext.getCmp('accountNum').disable();
				                    	 	Ext.getCmp('paymentAmount').setValue(Ext.getCmp('amount').getValue());
				                    	 }
				                    	}
				                    }
			                     },
			                    {boxLabel: '是否拨付',
			                     name: 'isSit',
			                     inputValue: 2,
			                     //checked: true,
			                     listeners : {
				                    'check' : function(checkbox, checked){ 
				                    	 if(checked){//只有在点击时触发
				                    	 	Ext.getCmp('accountNum').enable();
				                    	 	Ext.getCmp('paymentAmount').setValue(Ext.getCmp('amount').getValue());
				                    	 }
				                    }
			                     }
			                }]
			            },{
							xtype:'combo',
							mode: 'local',
							fieldLabel : '拨付账号<span style="color:red">*</span>',
							id:'accountNum',
							name : 'paymentAccountName',
							maxLength : 20,
							anchor : '98%',
							width:500,
							queryParams:'accountNum',
							minChars:30,
							triggerAction:'all',
							maxHeight: 500, 
							listWidth:500,
							forceSelection:false,
							emptyText:'请输入拨付账号',
							allowBlank:false,
							blankText:'拨付账号不能为空!!',
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
							xtype : 'numberfield',
							fieldLabel : '拨付金额<span style="color:red">*</span>',
							id:'paymentAmount',
							name : 'paymentAmount',
							maxLength : 50,
							emptyText:'请输入拨付金额',
							allowBlank:false,
							blankText:'拨付金额不能为空!!',
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '拨付账号类型',
							id:'accountTypeName1',
							name : 'accountTypeName1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '拨付账号名称',
							id:'accountName1',
							name : 'accountName1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付开户行',
							id:'bank1',
							name : 'bank1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付余额',
							id:'openingBalance1',
							name : 'openingBalance1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付负责人',
							id:'responsible1',
							name : 'responsible1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			}]
		}]
	});
	Title = '审核资金申请单';
	form.load({
				url : sysPath + "/" + gridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});
			
	cashdateStore.load({
		params : {
			filter_EQS_id : appAccountId
		},callback:function(r,options,success){
			if (success) {
				for (var i = 0; i < r.length; i++) {
					var record = r[i];
    				Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
    				Ext.getCmp('accountName').setValue(record.data.accountName);
    				Ext.getCmp('bank').setValue(record.data.bank);
    				Ext.getCmp('responsible').setValue(record.data.responsible);
    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
		        }
			}
		}
	});

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

				Ext.Msg.confirm(alertTitle,'本次拨付金额为<font color="#FF0000">'+Ext.getCmp('paymentAmount').getValue()+'</font>元,您确定要审核吗?',function(btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
						if (form.getForm().isValid()) {
							this.disabled = true;// 只能点击一次
							var fiCapitaaccountsetId=Ext.getCmp('paymentAccountId').getValue();
							
							if(fiCapitaaccountsetId==""){
								Ext.Msg.alert("友情提示","拨付账号没有选中，请重新选择！");
								return;
							}
							form.getForm().submit({
								url : sysPath + '/' + auditApplyfundUrl,
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
				});
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


//现金坐支单
function fundstransferSit(records){
	var cid = records[0].data.id;// 获取所有选中行
	var appAccountId=records[0].data.appAccountId;//申请账号
	var responsibleId=0;//申请人ID

	var userIds;
	var cashprivilege = 110;
	var cashSearchUrl="fi/fiCapitaaccountsetAction!ralaList.action";
	
	
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
						name : 'paymentTypeName',// 收支类型
						mapping : 'paymentTypeName'
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
						name : 'openingBalance',//余额
						mapping : 'openingBalance'
					}, {
						name : 'responsible',// 负责人
						mapping : 'responsible'
					}, {
						name : 'responsibleId',// 负责人ID
						mapping : 'responsibleId'
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
										//filter_EQL_responsibleId:userId,
										filter_EQL_isDelete:1,
										privilege:cashprivilege,
										limit : pageSize
									}
								});	
						}

				}];
		// 查询主列表
		//alert(Ext.getCmp('responsibleId').getValue());
		var cashdateStore = new Ext.data.Store({
			storeId : "cashdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + cashSearchUrl,
						method:'POST'
					}),
			baseParams:{
				//filter_EQL_responsibleId:userId,
				filter_EQL_isDelete:1,
				privilege:cashprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'cashGrid',
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
	    				Ext.getCmp('accountTypeName1').setValue(record.data.accountTypeName);
	    				Ext.getCmp('openingBalance1').setValue(record.data.openingBalance);
	    				Ext.getCmp('accountName1').setValue(record.data.accountName);
	    				Ext.getCmp('bank1').setValue(record.data.bank);
	    				Ext.getCmp('responsible1').setValue(record.data.responsible);
	    				Ext.getCmp('paymentAccountId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				Ext.getCmp('accountTypeName1').setValue(record.data.accountTypeName);
		    				Ext.getCmp('openingBalance1').setValue(record.data.openingBalance);
		    				Ext.getCmp('accountName1').setValue(record.data.accountName);
		    				Ext.getCmp('bank1').setValue(record.data.bank);
		    				Ext.getCmp('responsible1').setValue(record.data.responsible);
		    				Ext.getCmp('paymentAccountId').setValue(record.data.id);
		    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
		    				Ext.getCmp('accountNum').collapse();
		    				//alert(record);
		    			}
    				}
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		/*cashdateStore.on('load',function(){
	    		if(cashdateStore.getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});*/

			
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
							fieldLabel : 'id',
							name : 'id'
						},{
							xtype : 'hidden',
							id : 'isSit',
							name : 'isSit'
						},{
							xtype : 'hidden',
							id : 'paymentAccountId',
							name : 'paymentAccountId'
						},{
							xtype : 'hidden',
							id : 'status',
							name : 'status'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'numberfield',
							fieldLabel : '申请金额',
							id:'amount',
							name : 'amount',
							maxLength : 50,
							emptyText:'请输入申请金额',
							allowBlank:false,
							blankText:'申请金额不能为空!!',
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '申请账号',
							name : 'appAccountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '申请备注',
							name : 'appRemark',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '账号类型',
							id:'accountTypeName',
							name : 'accountTypeName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
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
							fieldLabel : '余额',
							id:'openingBalance',
							name : 'openingBalance',
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

			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
			                xtype: 'radiogroup',
			                fieldLabel: '现金交接类型',
			                allowBlank:false,
			                blankText:'请选择现金交接类型!',
			                items: [{
					                    boxLabel: '现金交接单',
					                    name: 'capitalTypeId',
					                    inputValue: 40250
				                    },{
					                     boxLabel: '银行转账单',
					                     name: 'capitalTypeId',
					                     inputValue: 40251
				             	   }]
			            },{
							xtype:'combo',
							mode: 'local',
							fieldLabel : '拨付账号<span style="color:red">*</span>',
							id:'accountNum',
							name : 'paymentAccountName',
							maxLength : 20,
							anchor : '98%',
							width:500,
							queryParams:'accountNum',
							minChars:30,
							triggerAction:'all',
							maxHeight: 500, 
							listWidth:500,
							forceSelection:false,
							emptyText:'请输入拨付账号',
							allowBlank:false,
							blankText:'拨付账号不能为空!',
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
								               				filter_EQS_responsibleId:Ext.get('responsibleId').dom.value,
								               				filter_LIKES_accountNum:Ext.get('accountNum').dom.value
								               			}
								               		});
						               	   		}
						               	   }
						               }
							 		}
					 			}
						}, {
							xtype : 'numberfield',
							fieldLabel : '拨付金额<span style="color:red">*</span>',
							id:'paymentAmount',
							name : 'paymentAmount',
							maxLength : 50,
							emptyText:'请输入拨付金额',
							allowBlank:false,
							disabled:true,
							blankText:'拨付金额不能为空!!',
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '拨付账号类型',
							id:'accountTypeName1',
							name : 'accountTypeName1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '拨付账号名称',
							id:'accountName1',
							name : 'accountName1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付开户行',
							id:'bank1',
							name : 'bank1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付余额',
							id:'openingBalance1',
							name : 'openingBalance1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '拨付负责人',
							id:'responsible1',
							name : 'responsible1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			}]
		}]
	});
	
	Title = '生成资金交接坐支单';
	form.load({
				url : sysPath + "/" + gridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});
			
	cashdateStore.load({
		params : {
			filter_EQS_id : appAccountId
		},callback:function(r,options,success){
			if (success) {
				for (var i = 0; i < r.length; i++) {
					var record = r[i];
    				Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
    				Ext.getCmp('accountName').setValue(record.data.accountName);
    				Ext.getCmp('bank').setValue(record.data.bank);
    				Ext.getCmp('responsible').setValue(record.data.responsible);
    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
    				responsibleId=record.data.responsibleId;
		        }
			}
		}
	});

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
					var fiCapitaaccountsetId=Ext.getCmp('paymentAccountId').getValue();
					if(fiCapitaaccountsetId==""){
						Ext.Msg.alert("友情提示","拨付账号没有选中，请重新选择！");
						return;
					}

					form.getForm().submit({
						url : sysPath + '/' + fundstransferSitUrl,
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
    			cashdateStore.load({
    				params:{
    						filter_EQL_paymentType:14250,//付款账号:收款账号
    						filter_EQS_responsibleId:responsibleId //申请人ID
	               			}
	               		});
    		}
    });
	Ext.getCmp("accountNum").on('collapse',function(){
    	return false;
    }); 
   
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
	
	var departId = Ext.getCmp("departId").getValue(); // 所属部门
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_departId : departId,
		privilege:privilege
	});
	maindateStore.reload({
				params : {
					start : 0
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

/**
收款确认
*/
function receivablesConfirmation(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要核销付款单！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "不同交接不允许同时付款确认！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'收款确认后,您的申请账号余额将会增加<font color="#FF0000">'+_records[0].data.paymentAmount+'</font>元,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + receivablesConfirmationUrl,
						params : {
							id : _records[0].data.id,
							ts:_records[0].data.ts,
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

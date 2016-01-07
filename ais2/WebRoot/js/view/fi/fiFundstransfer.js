var comboxPage = 15;
var privilege = 132;
var departGridSearchUrl = "sys/departAction!findAll.action";
var responsibleGridSearchUrl = "user/userAction!ralaList.action?privilege=23"; // 负责人
var gridSearchUrl = "fi/fiFundstransferAction!ralaList.action";
var saveUrl = "fi/fiFundstransferAction!save.action";
var revocationUrl = "fi/fiFundstransferAction!revocation.action";//作废
var paymentConfirmationUrl="fi/fiFundstransferAction!paymentConfirmation.action";//付款确认
var paymentRevokeUrl="fi/fiFundstransferAction!paymentRevoke.action";//付款撤销
var receivablesConfirmationUrl="fi/fiFundstransferAction!receivablesConfirmation.action";//收款确认


var dateStore,departStore,accountTypeStore,paymentTypeStore,responsibleStore,isDeleteStore;
var receivablesaccountDeptid;

var sm = new Ext.grid.CheckboxSelectionModel();
var cashGrid;
var rowNum = new Ext.grid.RowNumberer({
		header : '序号',
		width : 25,
		sortable : true
	});

	var auditStatus=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['2','已核销'],
  	  		  ['1','未核销']],
   		fields:["id","name"]
	});	
	

var fields = [{
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
			name : 'paymentStatus',//付款状态：0:未付款、1:已付款
			mapping : 'paymentStatus'
		}, {
			name : 'paymentTime',//付款时间
			mapping : 'paymentTime'
		}, {
			name : 'receivablesStatus',//收款状态：0:未确收,1:已确收
			mapping : 'receivablesStatus'
		}, {
			name : 'receivablesTime',//收款时间
			mapping : 'receivablesTime'
		}, {
			name : 'verificationType',//核销类型
			mapping : 'verificationType'
		}, {
			name : 'verificationNo',//核销单号
			mapping : 'verificationNo'
		}, {
			name : 'departName',
			mapping : 'departName'
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
									width : 60
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
								},{
									header : '付款状态',
									dataIndex : 'paymentStatus',
									renderer:function(v,metaData){
										if(v==0){
											v='<font color="#FF0000">未付款<font>';
										}else if (v==1){
											v='已付款';
										}else{
											v='<font color="#0000FF">异常<font>';
										}
										return v;
									},
									width : 60
								},{
									header : '收款状态',
									dataIndex : 'receivablesStatus',
									renderer:function(v,metaData){
										if(v==0){
											v='<font color="#FF0000">未确收<font>';
										}else if (v==1){
											v='已确收';
										}else{
											v='<font color="#0000FF">异常<font>';
										}
										return v;
									},
									width : 60
								}, {
									header : '金额',
									dataIndex : 'amount',
									width : 60
								}, {
									header : '付款账号',
									dataIndex : 'paymentAccountNum',
									width : 80
								},{
									header : '付款账号名称',
									dataIndex : 'paymentAccountName',
									width : 80
								},{
									header : '付款开户行',
									dataIndex : 'paymentAank',
									width : 80
								}, {
									header : '收款部门',
									dataIndex : 'receivablesaccountDept',
									width : 80
								}, {
									header : '收款账号',
									dataIndex : 'receivablesAccountNum',
									width : 80
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
											v='<font color="#0000FF">作废</font>';
										}else if (v==1){
											v='未核销';
										}else if (v==2){
											v='已核销';
										}
										return v;
									},width : 60
								}, {
									header : '创建部门',
									dataIndex : 'departName',
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
									header : '付款确认时间',
									dataIndex : 'paymentTime',
									width : 80
								}, {
									header : '收款确认时间',
									dataIndex : 'receivablesTime',
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

var receivingmenu = new Ext.menu.Menu({
				    id: 'receivingmenu',
				    items: [{
								text : '<b>现金交接单</b>',
								iconCls : 'table',
								id : 'receivingAdd',
								tooltip : '现金交接单',
								handler : function() {
										fundstransferAdd(40250);
									}
							}, {
								text : '<b>银行转账单</b>',
								iconCls : 'table',
								id : 'paymentAdd',
								tooltip : '银行转账单',
								handler : function() {
										fundstransferAdd(40251);
									}
							}]
				    });
				    
var receivingBtn=new Ext.Button({
			text : '<B>新增</B>',
			id : 'receivingBtn',
			tooltip : '新增',
			iconCls : 'group',
			menu: receivingmenu
		});	
		
var tbar = [receivingBtn,{
			text : '<b>作废</b>',
			iconCls : 'table',
			id : 'fundstransferDel',
			disabled : true,
			tooltip : '作废',
			handler : function() {
					var car = Ext.getCmp('carCenter');
					var _records = car.getSelectionModel().getSelections();// 获取所有选中行
					if (_records.length < 1) {
						Ext.Msg.alert("系统消息", "请选择您要操作的行！");
						return false;
					}
					Ext.Msg.confirm("系统提示", "确定要作废所选的记录吗？", function(btnYes) {
								if (btnYes == 'yes' || btnYes == 'ok'
										|| btnYes == true) {
									Ext.Ajax.request({
												url : sysPath + '/' + revocationUrl,
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
			},{
			text : '<b>付款确认</b>',
			iconCls : 'table',
			id : 'paymentConfirmation',
			disabled : true,
			tooltip : '付款确认',
			handler : function() {
					var car = Ext.getCmp('carCenter');
					var _records = car.getSelectionModel().getSelections();// 获取所有选中行
					paymentConfirmation(_records);
				}
			},{
			text : '<b>付款撤销</b>',
			iconCls : 'table',
			id : 'paymentRevoke',
			disabled : false,
			tooltip : '付款撤销',
			handler : function() {
					var car = Ext.getCmp('carCenter');
					var _records = car.getSelectionModel().getSelections();// 获取所有选中行
					paymentRevoke(_records);
				}
			},{
			text : '<b>收款确认</b>',
			iconCls : 'table',
			id : 'receivablesConfirmation',
			disabled : true,
			tooltip : '收款确认',
			handler : function() {
					var car = Ext.getCmp('carCenter');
					var _records = car.getSelectionModel().getSelections();// 获取所有选中行
					receivablesConfirmation(_records);
				}
			},{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('carCenter'));
			        } },'-',
			 {
				text : '<b>打印</b>',
				iconCls : 'table',
				id : 'printbtn',
				tooltip : '打印',
				handler : function(){
					var records = Ext.getCmp('carCenter').getSelectionModel().getSelections();
									
					if(records.length<1){
						Ext.Msg.alert(alertTitle,"请选择要打印的资金交接单据！");
						return;
					}else if (records.length>1){
						Ext.Msg.alert(alertTitle,"一次只允许打印一个资金交接单！");
						return;
					}
					
					printinfo(records[0]);
				}
			},'-', {
					text : '<b>搜索</b>',
					id : 'btn',
					iconCls : 'btnSearch',
					handler : searchmain
				}];
		
Ext.onReady(function() {

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
			items : ['单据日期从', {
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
					},'-','&nbsp;','状态:',{
						xtype : 'combo',
						typeAhead : false,
						forceSelection : true,
						width : 82,
						id:'auditStatus',
						mode:'local',
						triggerAction : 'all',
						store : auditStatus,
						displayField : 'name',
						valueField : 'id',
						emptyText : "请选择核销状态"
							
					},'-','&nbsp;',
					   {xtype:'textfield',
			 	        id : 'itemsValue',
			 	        width : 70,
				        name : 'itemsValue',
				        disabled:true,
			            enableKeyEvents:true,
			            listeners : {
			 				keyup:function(textField, e){
			                     if(e.getKey() == 13){
			                     	searchLog();
			                     }
			 				}
			 			}
			        },'-',{
		                xtype : "combo",
		    			id:"comboselect",
		   				width : 70,
		 				triggerAction : 'all',
		    			model : 'local',
		    			hiddenId : 'checkItems',
		    			hiddenName : 'checkItems',
		    			name : 'checkItemstext',
		    			store : [['', '查询全部'],
		    					['LIKES_paymentAccountNum', '付款账号'],
		    					['LIKES_receivablesAccountName','收款账号']],
		    			emptyText : '选择查询类型',
		    			forceSelection : true,
		    			listeners : {
		    				'select' : function(combo, record, index) { 
		    					if (combo.getValue() == '') {
		    						Ext.getCmp("itemsValue").disable();
		    						Ext.getCmp("itemsValue").setValue("");
		    					}else{
		    					    Ext.getCmp("itemsValue").setValue("");
		    						Ext.getCmp("itemsValue").enable();
		    					}
		    				}
		    			 }
    		}]
		});
		tbarsearch.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var fundstransferDel=Ext.getCmp('fundstransferDel');//获取作废按钮
				var paymentConfirmation=Ext.getCmp('paymentConfirmation');//获取付款确认按钮
				var receivablesConfirmationbtn=Ext.getCmp('receivablesConfirmation');//获取收款确认按钮
				var printbtn=Ext.getCmp('printbtn');//获取打印按钮

				if (_record.length == 1) {
					if (fundstransferDel) {
						fundstransferDel.setDisabled(false);
					}
					if (paymentConfirmation) {
						paymentConfirmation.setDisabled(false);
					}
					if (receivablesConfirmationbtn) {
						receivablesConfirmationbtn.setDisabled(false);
					}
					if (receivablesConfirmationbtn) {
						printbtn.setDisabled(false);
					}
					if(_record[0].data.paymentStatus==1){
						paymentConfirmation.setDisabled(true);
						fundstransferDel.setDisabled(true);
					}else{
						receivablesConfirmationbtn.setDisabled(true);
						printbtn.setDisabled(true);
					}
					if(_record[0].data.receivablesStatus==1||_record[0].data.status==0){
						paymentConfirmation.setDisabled(true);
						fundstransferDel.setDisabled(true);
						receivablesConfirmationbtn.setDisabled(true);
					}
					
					

				} else if (_record.data.length > 1) {
					if (fundstransferDel) {
						fundstransferDel.setDisabled(true);
						paymentConfirmation.setDisabled(true);
						receivablesConfirmationbtn.setDisabled(true);
						printbtn.setDisabled(true);
					}

				} else {
					if (fundstransferDel) {
						fundstransferDel.setDisabled(true);
					}
					if (paymentConfirmation) {
						paymentConfirmation.setDisabled(true);
					}
					if (receivablesConfirmationbtn) {
						receivablesConfirmationbtn.setDisabled(true);
					}
					if (printbtn) {
						printbtn.setDisabled(true);
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
	var departId = Ext.getCmp("departId").getValue(); // 所属部门
	var status= Ext.getCmp('auditStatus').getValue();
	if(Ext.getCmp('comboselect').getValue()==''){
	 	Ext.apply(dateStore.baseParams={
           	filter_GED_createTime : stateDate,
			filter_LED_createTime : endDate,
			filter_EQL_departId : departId,
			privilege : privilege,
			limit:pageSize,
			filter_EQL_status:status,
			checkItems :Ext.getCmp('comboselect').getValue(),
			itemsValue :Ext.getCmp('itemsValue').getValue()
  		});
   	}else{
   		Ext.apply(dateStore.baseParams={
			filter_GED_createTime : stateDate,
			filter_LED_createTime : endDate,
			privilege : privilege,
			limit:pageSize,
			filter_EQL_status:status,
			checkItems :Ext.getCmp('comboselect').getValue(),
			itemsValue :Ext.getCmp('itemsValue').getValue(),
			filter_EQL_departId : departId
  		});
   	}
	
	dateStore.reload({
				params : {
					start : 0,
					limit:pageSize
				}
			});
}

/**
 * 资金交接单
 */
function fundstransferAdd(capitalTypeId){
	var accountType; //账号类型
	
	var accountTypeStore;
	
	if(capitalTypeId==40250){
		Title = '添加现金交接单';
		accountType=14252;//现金
	}else if(capitalTypeId==40251){
		Title = '添加银行转账单';
		accountType=14253;//银行
	}else{
		Title="";
	}
	
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
										departId:departIds,
										//userId:userId,
										paymentType:14250,//收款账号:收入
										accountType:accountType,//账号类型
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
				paymentType:14250,//收款账号:收入
				accountType:accountType,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'resultMap',
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
	    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('penyJenis').setValue(record.data.accountTypeName);
	    				Ext.getCmp('accountName').setValue(record.data.accountName);
	    				Ext.getCmp('bank').setValue(record.data.bank);
	    				Ext.getCmp('responsible').setValue(record.data.responsible);
	    				Ext.getCmp('receivablesaccountId').setValue(record.data.id);
	    				Ext.getCmp('accountNum').setValue(record.data.accountNum);
	    				Ext.getCmp('accountNum').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
		    				Ext.getCmp('penyJenis').setValue(record.data.accountTypeName);
		    				Ext.getCmp('accountName').setValue(record.data.accountName);
		    				Ext.getCmp('bank').setValue(record.data.bank);
		    				Ext.getCmp('responsible').setValue(record.data.responsible);
		    				Ext.getCmp('receivablesaccountId').setValue(record.data.id);
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
			
			
			
	//现金付款开始
	var userIds,departIds;
	var paymentprivilege = 110;
	var paymentSearchUrl="fi/fiCapitaaccountsetAction!ralaList.action";
	
	var paymentsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
	var paymentrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
	var paymentfields = [{
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
	var paymentcm=new Ext.grid.ColumnModel([paymentrowNum, paymentsm, {
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
									width : 60
								}, {
									header : '负责人',
									dataIndex : 'responsible',
									width : 60
								}])
		var paymentTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'paymentcheckItems',
				hiddenName : 'paymentcheckItems',
				name : 'checkItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['LIKES_accountNum', '账号'], ['LIKES_accountName', '账号名称'], ['LIKES_bank', '开户行']],
				emptyText : '选择查询类型',
				forceSelection : true
			}, '-', {
				xtype : 'textfield',
				emptyText :'快捷查询',
				blankText : '快捷查询',
				id : 'paymentsearchSearch'
			}, '-', {
				text : '<b>搜索</b>',
				id : 'paymentbtn',
				iconCls : 'btnSearch',
				handler :  function() {
						paymentdateStore.baseParams = {
							checkItems : Ext.get("paymentcheckItems").dom.value,
							itemsValue : Ext.get("paymentsearchSearch").dom.value
						}
                		userIds=userId;
                		departIds="";
						paymentdateStore.reload({
									params : {
										filter_EQL_responsibleId:userIds,
										filter_EQL_departId:departIds,
										filter_EQL_paymentType:14250,//付款账号:收入
										filter_EQL_isDelete:1,
										privilege:paymentprivilege,
										limit : pageSize
									}
								});	
						}

				}];
		// 查询主列表
		var paymentdateStore = new Ext.data.Store({
			storeId : "paymentdateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + paymentSearchUrl,
						method:'POST'
					}),
			baseParams:{
				filter_EQL_responsibleId:userId,
				filter_EQL_paymentType:14250,//收入账号
				filter_EQL_isDelete:1,
				privilege:paymentprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, paymentfields)
			});
		
		var paymentGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'paymentGrid',
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
					sm : paymentsm,
					cm : paymentcm,
					tbar : paymentTbar,
					ds : paymentdateStore,
					listeners:{
	    				rowdblclick:function(grid, rowindex, e){
	    				var record=grid.getSelectionModel().getSelected();
	    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
	    				Ext.getCmp('paymentPenyJenis').setValue(record.data.accountTypeName);
	    				Ext.getCmp('paymentAccountName').setValue(record.data.accountName);
	    				Ext.getCmp('paymentbank').setValue(record.data.bank);
	    				Ext.getCmp('paymentaccountId').setValue(record.data.id);
	    				Ext.getCmp('paymentresponsible').setValue(record.data.responsible);
	    				Ext.getCmp('paymentAccountNo').setValue(record.data.accountNum);
	    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
	    				Ext.getCmp('paymentAccountNo').collapse();
	    			
	    				//Ext.getCmp('goods').focus(true,true);
	    				}
	    			},keydown:function(e){
		    			if(e.getKey()==13){//回车
		    				var record=grid.getSelectionModel().getSelected();
		    				//Ext.getCmp('accountTypeName').setValue(record.data.accountTypeName);
		    				Ext.getCmp('paymentPenyJenis').setValue(record.data.accountTypeName);
		    				Ext.getCmp('paymentAccountName').setValue(record.data.accountName);
		    				Ext.getCmp('paymentbank').setValue(record.data.bank);
		    				Ext.getCmp('paymentaccountId').setValue(record.data.id);
		    				Ext.getCmp('paymentresponsible').setValue(record.data.responsible);
		    				Ext.getCmp('paymentAccountNo').setValue(record.data.accountNum);
		    				Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
		    				Ext.getCmp('paymentAccountNo').collapse();
		    				//alert(record);
		    			}
    				}
					/*bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : paymentdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})*/
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		paymentdateStore.on('load',function(){
	    		if(paymentGrid.getStore().getTotalCount()==1){
	    			paymentGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});

		//现金付款结束
			
			
			
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
							id : 'paymentaccountId',
							name : 'paymentaccountId'
						},{
							xtype : 'hidden',
							id : 'receivablesaccountId',
							name : 'receivablesaccountId'
						},{
							xtype : 'hidden',
							id : 'receivablesaccountDept',
							name : 'receivablesaccountDept'
						},{
							xtype : 'hidden',
							id : 'receivablesaccountDeptid',
							name : 'receivablesaccountDeptid'
						},{
							xtype : 'hidden',
							id : 'capitalTypeId',
							name : 'capitalTypeId',
							value:capitalTypeId
						}, {
							name : "ts",
							xtype : "hidden"
						},{
							xtype:'combo',
							mode: 'local',
							fieldLabel : '付款账号<span style="color:red">*</span>',
							id:'paymentAccountNo',
							name : 'paymentAccountNo',
							maxLength : 20,
							anchor : '98%',
							width:500,
							queryParams:'paymentAccountNo',
							minChars:30,
							triggerAction:'all',
							maxHeight: 500, 
							listWidth:500,
							forceSelection:false,
							emptyText:'请输入付款账号',
							allowBlank:false,
							blankText:'付款账号不能为空!!',
							tpl: '<div id="paymentpanel" style="width:500px;height:249px"></div>',
								store:new Ext.data.SimpleStore({fields:["paymentAccountNo","paymentAccountNo"],data:[[]]}),
								enableKeyEvents:true,
								listeners : {
							 		keyup:function(combo, e){
						               if(e.getKey() == 13){
						                   //Ext.getCmp('consignee').focus(true,true);
						               }
						               else if(e.getKey()==40){
						            	   paymentdateStore.getSelectionModel().selectFirstRow();
						            	   var row=paymentdateStore.getView().getRow(0); 
											Ext.get(row).focus();
						               }else{
						               	   if((Ext.get('paymentAccountNo').dom.value).length>2){
						               	   		if((Ext.get('paymentAccountNo').dom.value).length<11)
						               	   		{
							               	   		combo.expand();
								               		paymentdateStore.load({
								               			params:{
								               				filter_LIKES_accountNum:Ext.get('paymentAccountNo').dom.value
								               			}
								               		});
						               	   		}
						               	   }
						               }
							 		}
					 			}
						}, {
							xtype : 'numberfield',
							fieldLabel : '结算金额<span style="color:red">*</span>',
							name : 'amount',
							maxLength : 50,
							emptyText:'请输入结算金额',
							allowBlank:false,
							blankText:'结算金额不能为空!!',
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 50,
							
							anchor : '98%'
						},{
							xtype : 'textfield',
							fieldLabel : '付款账号类型',
							id:'paymentPenyJenis',
							name : 'paymentPenyJenis',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '付款账号名称',
							id:'paymentAccountName',
							name : 'paymentAccountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '付款开户行',
							id:'paymentbank',
							name : 'paymentbank',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '付款账号余额',
							id:'openingBalance',
							name : 'openingBalance',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '付款责任人',
							id:'paymentresponsible',
							name : 'paymentresponsible',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
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
						fieldLabel:'收款部门',
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
													Ext.getCmp('receivablesaccountDeptid').setValue(departStore.getAt(i).get("departId"));
													Ext.getCmp('receivablesaccountDept').setValue(departStore.getAt(i).get("departName"));
											}
										}
										if(flag){
											var store=new Ext.data.Record.create([{name:'departName'},{name:'departId'}]);
											var record=new store();
											record.set("departId",bussDepart);
											record.set("departName",bussDepartName);
											departStore.add(record);
											Ext.getCmp('receivablesaccountDeptid').setValue(record.get("departId"));
											Ext.getCmp('receivablesaccountDept').setValue(record.get("departName"));	
										}
											combo.setValue(bussDepart);
									}
							});},'select' : function(combo, record, index) {
									Ext.getCmp('receivablesaccountDeptid').setValue(record.get("departId"));
									Ext.getCmp('receivablesaccountDept').setValue(record.get("departName"));
								}
						}
					},{
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
							tpl: '<div id="panel" style="width:500px;height:249px"></div>',
								store:new Ext.data.SimpleStore({fields:["accountNum","accountNum"],data:[[]]}),
								enableKeyEvents:true,
								listeners : {
									'focus' : function(combo, record, index) {
									    			
						   				Ext.apply(cashdateStore.baseParams, {
											departId : Ext.getCmp('receivablesaccountDeptid').getValue()
										});
										cashdateStore.load();
									},
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
						},{
							xtype : 'textfield',
							fieldLabel : '收款账号类型',
							id:'penyJenis',
							name : 'penyJenis',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '收款账号名称',
							id:'accountName',
							name : 'accountName',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '收款开户行',
							id:'bank',
							name : 'bank',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}, {
							xtype : 'textfield',
							fieldLabel : '收款负责人',
							id:'responsible',
							name : 'responsible',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}]

			}]
		}]
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
					var receivablesaccountId=Ext.getCmp('receivablesaccountId').getValue();
					var paymentaccountId=Ext.getCmp('paymentaccountId').getValue();
					//alert("receivablesaccountId="+receivablesaccountId+",paymentaccountId="+paymentaccountId);
					//return;
					if(receivablesaccountId==""){
						Ext.Msg.alert("友情提示","收款账号没有选中，请重新选择！");
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

				if (capitalTypeId == null) {
					form.getForm().reset();
				}
				if (capitalTypeId != null) {
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

	Ext.getCmp("paymentAccountNo").on('expand',function(){  
			if(paymentGrid.rendered){
				paymentGrid.doLayout();
			}else{
    			paymentGrid.render('paymentpanel');
    			paymentdateStore.load();
    		}
    });
	Ext.getCmp("paymentAccountNo").on('collapse',function(){
    	return false;
    }); 
    
	Ext.getCmp("accountNum").on('expand',function(){  
			if(cashGrid.rendered){
				cashGrid.doLayout();
			}else{
    			cashGrid.render('panel');
    			
   				Ext.apply(cashdateStore.baseParams, {
					departId : Ext.getCmp('receivablesaccountDeptid').getValue()
				});
				
    			cashdateStore.load();
    		}
    });
	Ext.getCmp("accountNum").on('collapse',function(){
    	return false;
    }); 

}
/**
 * 资金交接单作废
 */
function fundstransferDel(){
	Ext.Msg.alert("作废成功！");
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
function printinfo(record){
	parent.print('8',{print_id:record.data.id});
}

/**
付款确认
*/
function paymentConfirmation(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要核销付款单！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "不同交接不允许同时付款确认！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'付款确认后,你的付款账号余额将会减少,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + paymentConfirmationUrl,
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

/**
付款撤销
*/
function paymentRevoke(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要核销付款单！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "不同交接不允许同时付款确认！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'撤销付款确认后,你的付款账号余额将会增少,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + paymentRevokeUrl,
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
	
	Ext.Msg.confirm(alertTitle,'收款确认后,你的收款账号余额将会增加,确认要操作吗?',function(btnYes) {
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

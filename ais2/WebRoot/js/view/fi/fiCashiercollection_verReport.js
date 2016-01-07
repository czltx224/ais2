//var pageSize = 1;
var privilege = 125;//出纳收款单
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiCashiercollectionAction!ralaList.action";
var saveUrl = "fi/fiCashiercollectionAction!saveCashiercollection.action";//保存出纳收款单

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
		},{
			header : '核销时间',
			dataIndex : 'verificationTime',
			width:80
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
			text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('mainGrid'));}
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
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_departId : departId,
		filter_EQS_collectionAmount : collectionAmount,
		filter_EQS_verificationStatus:comboverificationStatus,
		privilege:privilege
	});
	maindateStore.reload();
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

//var pageSize = 1;
var comboxPage = 15;
var privilege = 118;//实收实付
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var maingridSearchUrl = "fi/fiPaidAction!ralaList.action";
var paymentVerificationUrl="/fi/fiPaidAction!paymentVerification.action";//付款单出纳核销
var searchHandInAmountUrl="/fi/fiPaidAction!searchHandInAmount.action";//资金上交单总金额查询
var handInConfirmationUrl="/fi/fiPaidAction!handInConfirmation.action"; //资金上交单提交

//撤销收付款
var revocationUrl="fi/fiPaidAction!revocation.action";

var dateStore, customerStore, departStore,maindateStore,sourceDataStore,paymentTypeStore,verificationStatusStore,fiFundstransferStatusStore;
var documentsTypeStore,documentsSmalltypeStore,costTypeStore;
var sm = new Ext.grid.CheckboxSelectionModel({
	//	singleSelect :true,
		listeners : {
			selectionchange:function(){
				setTotalCount();
			}
		}
	});

function setTotalCount(){
		var vnetmusicRecord = Ext.getCmp('mainGrid').getSelectionModel().getSelections();
		var count_total=0;
			
		for(var j=0;j<vnetmusicRecord.length;j++){;
			count_total+=parseFloat(vnetmusicRecord[j].data.settlementAmount);
	 	}

	 	Ext.getCmp('count').setValue(vnetmusicRecord.length);
	 	Ext.getCmp('count_total').setValue(count_total);
	}


var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});

var fields = [{
			name : "id",
			mapping : 'id'
		},{
			name : "paidId",
			mapping : 'paidId'
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
			name : "fiFundstransferId", // 上交单号
			mapping : 'fiFundstransferId'
		}, {
			name : "fiFundstransferStatus", // 上交状态
			mapping : 'fiFundstransferStatus'
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
		},'fiReceiptId'];

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '序号',
			dataIndex : 'id',
			width:65
		}, {
			header : '实收付单号',
			dataIndex : 'paidId',
			width:65,
			sortable:true
		}, {
			header : '应收付单号',
			dataIndex : 'fiPaymentId',
			width:65,
			sortable:true
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
			width:65,
			sortable:true
		}, {
			header : '实收付金额',
			dataIndex : 'settlementAmount',
			width:65,
			sortable:true
		}, {
			header : '核销状态',
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if(v==0){
					v='<font color="#FF0000">未核销</font>';
				}else if (v==1){
					v='已核销';
				}
				return v;
			},
			width:65,
			sortable:true
		}/*, {
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
		}*/, {
			header : '上交单号',
			dataIndex : 'fiFundstransferId',
			renderer:function(v,metaData){
				if(v==0){
					v='';
				}
				return v;
			},
			width:65,
			sortable:true
		}, {
			header : '上交状态',
			dataIndex : 'fiFundstransferStatus',
			renderer:function(v,metaData){
				if(v==0){
					v='<font color="#FF0000">未上交</font>';
				}else if (v==1){
					v='已上交';
				}else{
					v='异常';
				}
				return v;
			},
			width:65,
			sortable:true
		}, {
			header : '费用类型',
			dataIndex : 'costType',
			width:65,
			sortable:true
		}, {
			header : '单据大类',
			dataIndex : 'documentsType',
			width:65,
			sortable:true
		}, {
			header : '单据小类',
			dataIndex : 'documentsSmalltype',
			width:65,
			sortable:true
		}, {
			header : '单据号',
			dataIndex : 'documentsNo',
			width:65,
			sortable:true
		}, {
			header : '结算类型',
			dataIndex : 'penyType',
			width:65,
			sortable:true
		}, {
			header : '结算方式',
			dataIndex : 'penyJenis',
			width:65,
			sortable:true
		}, {
			header : '往来单位',
			dataIndex : 'contacts',
			width:65,
			sortable:true
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商名称',
			dataIndex : 'customerName',
			width:65,
			sortable:true
		}, {
			header : '收付账号',
			dataIndex : 'accountNum',
			width:65,
			sortable:true
		}, {
			header : '账号名称',
			dataIndex : 'accountName',
			width:65,
			sortable:true
		}, {
			header : '开户行',
			dataIndex : 'bank',
			width:65,
			sortable:true
		}, {
			header : '收据号',
			dataIndex : 'fiInvoiceId',
			width:65,
			sortable:true
		}, {
			header : '核销金额',
			dataIndex : 'verificationAmount',
			width:65,
			sortable:true
		}, {
			header : '核销部门',
			dataIndex : 'verificationDept',
			width:65,
			sortable:true
		}, {
			header : '核销人',
			dataIndex : 'verificationUser',
			width:65,
			sortable:true
		}, {
			header : '核销时间',
			dataIndex : 'verificationTime',
			width:65,
			sortable:true
		}, {
			header : '所属部门',
			dataIndex : 'departName',
			width:65
		}, {
			header : '创建部门ID',
			dataIndex : 'departId',
			hidden : true
		}, {
			header : '创建人',
			dataIndex : 'createName',
			sortable:true
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
			sortable:true
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
			text : '<b>撤销收付款</b>',
			iconCls : 'save',
			id : 'revocation',
			tooltip : '撤销收付款',
			handler : revocation
		},'-', {
			text : '<b>资金上交</b>',
			iconCls : 'save',
			id : 'handIn',
			tooltip : '资金上交',
			handler : function(){
				var mainConter = Ext.getCmp('mainGrid');
				var records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
				handInConfirmation(records);
			}
		},'-', {
			text : '<b>出纳付款核销</b>',
			iconCls : 'save',
			id : 'paymentVerification',
			tooltip : '出纳付款核销',
			handler : function(){
				var mainConter = Ext.getCmp('mainGrid');
				var records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
				paymentVerification(records);
			}
		}, '-', {
			text : '<b>录入收据</b>',
			iconCls : 'save',
			id : 'receiptAdd',
			tooltip : '录入收据',
			handler : function(){
				var mainConter = Ext.getCmp('mainGrid');
				var records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
				if(records.length==1){
					receiptAdd(records[0]);
				}else{
					Ext.Msg.alert(alertTitle,"请选择一条数据录入收据");
				}
			}
		},'-',{text:'<b>导出</b>',iconCls:'sort_down',handler:function() {
                	parent.exportExl(Ext.getCmp('mainGrid'));
                }
        },'-',{
			text:'<b>打印</b>',
			iconCls:'printBtn',
			handler:function() {
				var mainConter = Ext.getCmp('mainGrid');
				var records = mainConter.getSelectionModel().getSelections();
				var paidIds="";
				var paidid="";
				var paymentType="";//收付类型
				if(records.length<1){
					Ext.Msg.alert(alertTitle,"请选择一条需要打印的数据！");
					return;
				}
				
				for(var i=0;i<records.length;i++){
					
					if(paidid!=records[i].data.paidId){
						paidIds=paidIds+records[i].data.paidId+',';
						
					}
					
					if(paymentType==""){
						paymentType=records[i].data.paymentType;
					}else if(paymentType!=records[i].data.paymentType){
						Ext.Msg.alert(alertTitle,"收款单和付款单请分开打印！");
						return;
					}
					paidid=records[i].data.paidId;
				}
	            parent.print('13',{print_paidIds:paidIds});

           	} 
		  }, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
	}];
Ext.onReady(function() {
	// 收付类型
	paymentTypeStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['1', '收款单'], ['2', '付款单']],
				fields : ["paymentType", "paymentTypeName"]
			});

	// 核销状态
	verificationStatusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['0', '未核销'], ['1', '已核销']],
				fields : ["verificationStatus", "verificationStatusName"]
			});
	fiFundstransferStatusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['0', '未上交'], ['1', '已上交']],
				fields : ["fiFundstransferStatus", "fiFundstransferStatusName"]
			});
	// 单据大类
	documentsTypeStore = new Ext.data.Store({
				storeId : "reconstatusStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 122,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'documentsType',
									mapping : 'typeName'
								}])
			});
	//documentsTypeStore.load();
	
	// 单据小类
	documentsSmalltypeStore = new Ext.data.Store({
				storeId : "reconstatusStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 123,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'documentsSmalltype',
									mapping : 'typeName'
								}])
			});
	//documentsSmalltypeStore.load();

	// 费用类型
	costTypeStore = new Ext.data.Store({
				storeId : "reconstatusStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 124,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'costType',
									mapping : 'typeName'
								}])
			});
	//costTypeStore.load();
	
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

			
	// 数据来源
	sourceDataStore = new Ext.data.Store({
				storeId : "sourceDataStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 182,
					limit:50,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'sourceData',
									mapping : 'typeName'
								}])
			});
	// 查询主列表
	maindateStore = new Ext.data.Store({
				storeId : "maindateStore",
				baseParams : {
								limit:pageSize,
								privilege : privilege
								},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + maingridSearchUrl
							}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, fields)
			});
	
	
	 var fourbar = new Ext.Toolbar({	frame : true,
		items : ['&nbsp;','<B>统计信息</B>','&nbsp;','-',/*'&nbsp;'
				,'总票数:',
		    	{xtype : 'numberfield',
                id:'count_dno',
   			    readOnly:true,
                width:49,
                value:0
              },*/'&nbsp;'
				,'总行数:',
		    	{xtype : 'numberfield',
                id:'count',
   			    readOnly:true,
                width:49,
                value:0
              },'&nbsp;','实收付总额:',
		    	{xtype : 'numberfield',
                id:'count_total',
   			    readOnly:true,
                width:49,
                 value:0
              }]
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
					forceFit : false,
					scrollOffset: 0,
					autoScroll:true
				},
				tbar:fourbar,
				autoScroll : true,
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
						width : 70,
						listWidth:245,
						anchor : '95%'
					}, '-', '部门', {
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
						width : 70,
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
					}, '-','收付类型', {
						xtype : 'combo',
						id : 'combopaymentType',
						triggerAction : 'all',
						store : paymentTypeStore,
						//allowBlank : false,
						emptyText : "请选择",
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'paymentTypeName',// 显示值，与fields对应
						valueField : 'paymentType',// value值，与fields对应
						hiddenName:'paymentType',
						width : 60,
						anchor : '95%'
					}, '-',  '小类', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : documentsSmalltypeStore,
						triggerAction : 'all',
						id : 'combodocumentsSmalltype',
						valueField : 'documentsSmalltype',
						displayField : 'documentsSmalltype',
						emptyText : "请选择",
						width : 60,
						anchor : '95%'
					}, '-', '单据号', {
						xtype : 'textfield',
						width : 40,
						id : 'documentsNo',
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					}/*, '-', '单据大类', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : documentsTypeStore,
						triggerAction : 'all',
						id : 'combodocumentsType',
						valueField : 'documentsType',
						displayField : 'documentsType',
						emptyText : "请选择",
						width : 60,
						anchor : '95%'
					}*/]
		});
		
		var tbarsearch1 = new Ext.Toolbar({
			items : ['上交状态', {
						xtype : 'combo',
						id : 'combofiFundstransferStatus',
						triggerAction : 'all',
						store : fiFundstransferStatusStore,
						//allowBlank : false,
						emptyText : "请选择",
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'fiFundstransferStatusName',// 显示值，与fields对应
						valueField : 'fiFundstransferStatus',// value值，与fields对应
						hiddenName:'fiFundstransferStatus',
						width : 55,
						anchor : '95%'
					}, '-', '核销状态', {
						xtype : 'combo',
						id : 'comboverificationStatus',
						triggerAction : 'all',
						store : verificationStatusStore,
						//allowBlank : false,
						emptyText : "请选择",
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'verificationStatusName',// 显示值，与fields对应
						valueField : 'verificationStatus',// value值，与fields对应
						hiddenName:'verificationStatus',
						width : 55,
						anchor : '95%'
					}, '-', '数据来源', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : sourceDataStore,
						triggerAction : 'all',
						id : 'combosourceData',
						valueField : 'sourceData',
						displayField : 'sourceData',
						emptyText : "请选择",
						width : 70,
						anchor : '95%'
					}, '-', '来源单号', {
						xtype : 'numberfield',
						width : 40,
						id : 'combosourceNo',
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					}, '-', '收款人', {
						xtype : 'textfield',
						width : 40,
						id : 'combocreateName',
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					}, '-', '往来单位', {
						xtype : 'textfield',
						width : 40,
						id : 'combocontacts',
						enableKeyEvents:true,
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					}, '-', '上交单号', {
						xtype : 'textfield',
						width : 40,
						id : 'comboFiFundstransferId',
						enableKeyEvents:true,
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					}]
				});
		tbarsearch.render(mainpanel.tbar);
		tbarsearch1.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	//maindateStore.load();

	mainGrid.on('click', function() {
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var updatebtn = Ext.getCmp('basCarEdit');// 获得更新按钮
				var deletebtn = Ext.getCmp('basCarDelete');// 获得删除按钮
				var reviewbtn = Ext.getCmp("basReview"); // 获得审核按钮

				if (_record.length == 1) {
					if (updatebtn) {
						updatebtn.setDisabled(false);
					}
					if (deletebtn) {
						deletebtn.setDisabled(false);
					}
					if (deletebtn) {
						reviewbtn.setDisabled(false);
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
					if (reviewbtn) {
						reviewbtn.setDisabled(true);
					}
				}

			});
});

/**
 * 查询对账信息
 */
function searchmain() {
	/*var customerText = Ext.get('comboCustomer').dom.value; // 客商文本值
	var customerId = Ext.getCmp("comboCustomer");// 客商
	var custprop = Ext.getCmp("comboCustprop");// 客商类型
	var billingCycle = Ext.getCmp("comboBillingCycle");// 结算周期
	if (customerText != '') {
		custprop.setValue('');
		billingCycle.setValue('');

	} else {
		if (!custprop.isValid()) {
			custprop.setValue('');
			return;
		} else if (!billingCycle.isValid()) {
			billingCycle.setValue('');
			return;
		}
	}*/
	dataReload();
}

/**
 * 查询对账单后刷新
 */
function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var documentsNo = Ext.getCmp("documentsNo").getValue(); // 单据号
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var departId=Ext.getCmp("departId").getValue(); // 业务部门ID
	//var documentsType = Ext.getCmp("combodocumentsType").getValue(); // 单据大类
	var documentsSmalltype = Ext.getCmp("combodocumentsSmalltype").getValue(); // 单据小类
	var verificationStatus= Ext.getCmp("comboverificationStatus").getValue(); // 单据小类
	var sourceData = Ext.getCmp("combosourceData").getValue(); 
	var sourceNo = Ext.getCmp("combosourceNo").getValue(); 
	var paymentType = Ext.getCmp("combopaymentType").getValue(); // 收付类型
	var createName=Ext.getCmp("combocreateName").getValue(); // 创建人
	var fiFundstransferStatus= Ext.getCmp("combofiFundstransferStatus").getValue(); // 上交状态
	var contacts = Ext.getCmp("combocontacts").getValue(); //往来单位
	var fiFundstransferId=Ext.getCmp("comboFiFundstransferId").getValue(); //上交单号
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		//filter_EQS_documentsType : documentsType,
		filter_EQS_documentsSmalltype : documentsSmalltype,
		filter_EQS_customerId : customerId,
		filter_EQS_paymentType : paymentType,
		filter_EQL_departId : departId,
		filter_EQS_fiFundstransferStatus : fiFundstransferStatus,
		filter_EQS_verificationStatus : verificationStatus,
		filter_EQS_documentsNo:documentsNo,
		filter_EQS_sourceData:sourceData,
		filter_EQS_sourceNo:sourceNo,
		filter_EQS_fiFundstransferId:fiFundstransferId,
		filter_LIKES_createName:createName,
		filter_LIKES_contacts:contacts,
		privilege:privilege
	});
	maindateStore.reload({
				params : {
					start : 0
				}
			});
}

/**
 * 撤销收付款
 */
function revocation(){
	var fiPaidId="";
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	
	for (var i=0;i<_records.length;i++){
		if(fiPaidId!=""&&fiPaidId!=_records[i].data.paidId){
			Ext.Msg.alert("系统消息", "不同实收单请分开撤销！");
			return false;
		}
		fiPaidId=_records[i].data.paidId;
	}

	Ext.Msg.confirm(alertTitle,'您确认要撤销收付款吗?',function(btnYes) {
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

	/**
	 * 收据录入
	 */
	function receiptAdd(record){
	var amount=record.data.settlementAmount;
		var userFormPanel = new Ext.form.FormPanel({
				defaultType : 'textfield',
				labelAlign : 'right',
				labelWidth : 80,
				frame : true,
				bodyStyle : 'padding:5 5 0',
				items : [{	id:"fiPaidId",
							name : "fiPaidId",
							value: record.get('id'),
							xtype : "hidden"
						}, {fieldLabel: '收据日期<span style="color:red">*</span>',
						    name: 'receiptData',
						    id:'receiptData',
						    labelAlign : 'left',
						    xtype:'datefield',
						    format : 'Y-m-d',
						    width : 150,
						    value:new Date(),
							allowBlank : false,
							anchor : '95%'
						}, {
							fieldLabel : '金额<span style="color:red">*</span>',
							name : 'amount',
							id : 'amount',
							allowNegative:false,
                           	decimalPrecision:2,
							maxLength : 50,
							value:amount,
							allowBlank : false,
							anchor : '95%'
						},{
						///	labelAlign : 'top',
							xtype : 'textarea',
							name : 'remark',
							maxLength:500,
							allowBlank : false,
							fieldLabel : '摘要<span style="color:red">*</span>',
							height : 45,
							width:'95%'
						}]
			});
	
			var userWindow = new Ext.Window({
				layout : 'fit',
				width : 320,
				height : 200,
				resizable : false,
				draggable : true,
				closeAction : 'hide',
				modal : true,
				title : '<span class="commoncss">录入收据</span>',
				iconCls : 'groupNotPass',
				collapsible : true,
				titleCollapse : true,
				maximizable : false,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				listeners : {
					'show' : function(obj) {
						Ext.getCmp('amount').focus(true,200);
					}
				},
				items : [userFormPanel],
				buttons : [{
					text : '保存',
					iconCls : 'groupSave',
					handler : function() {
						
						if (userFormPanel.getForm().isValid()) {
							userFormPanel.getForm().submit({
								url : sysPath+ "/fi/fiReceiptAction!save.action",
								params:{
									privilege:privilege
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
											userWindow.close();
											Ext.Msg.alert(alertTitle,action.result.msg);
								},
								failure : function(form, action) {
									if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
										Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
									} else {
										if (!action.result.success) {
											Ext.Msg.alert(alertTitle,action.result.msg, function() {
													});
		
										}
									}
								}
						});
						
						}
						this.disabled = false;//只能点击一次
					}
				}, {
					text : '关闭',
					handler : function() {
						userWindow.close();
					}
				}]
			});
	
		userWindow.on('hide', function() {
					userFormPanel.destroy();
				});

		userWindow.show();

	}
	
/**
资金上交
*/	
function handInConfirmation(_records){
	var cid;
	var accountTypeStore;
	var paidid="";
	var paidIds="";
	
	for(var i=0;i<_records.length;i++){
		if(paidid!=_records[i].data.paidId){
			paidIds=paidIds+_records[i].data.paidId+',';
		}
		paidid=_records[i].data.paidId;
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
						mapping : 'accountType'
					}, {
						name : 'paymentType',// 收支类型
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
									width : 120
								}, {
									header : '账号名称1',
									dataIndex : 'accountName',
									width : 120
								}, {
									header : '负责人',
									dataIndex : 'responsible',
									width : 60
								}, {
									header : '开户行',
									dataIndex : 'bank',
									width : 80
								}])
		var cashTbar = [/*{
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
			},*/ '账号', {
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
						/*cashdateStore.baseParams = {
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value
						}*/

                		//userIds=userId;
                		var accountNum=Ext.getCmp('comboAccountNum').getValue();
                		var accountName=Ext.getCmp('comboAccountName').getValue();
						cashdateStore.reload({
									params : {
										//filter_EQL_responsibleId:userIds,
										departId:departIds,
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
				departId:departIds,
				paymentType:14250,//收入
				accountType:14252,//现金
				limit : pageSize,
				privilege:cashprivilege
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
	    				//Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
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
		    				//Ext.getCmp('openingBalance').setValue(record.data.openingBalance);
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
										filter_EQS_accountTypeName:'现金',
										filter_EQL_paymentType:14250,//付款账号:支出
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
				filter_EQS_accountTypeName:'现金',
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
	    				//Ext.getCmp('sumAmount').setValue(record.data.openingBalance);//上交总额
	    				//Ext.getCmp('sumAmount1').setValue(record.data.openingBalance);
	    				//Ext.getCmp('amount1').setValue(record.data.openingBalance-Ext.getCmp('amount').getValue());//交班金额
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
		    				//Ext.getCmp('sumAmount1').setValue(record.data.openingBalance);
		    				//Ext.getCmp('sumAmount').setValue(record.data.openingBalance);
		    				
		    				//Ext.getCmp('amount1').setValue(record.data.openingBalance-Ext.getCmp('amount').getValue());
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
							id : 'sumAmount',
							name : 'sumAmount'
						},{
							xtype : 'hidden',
							id : 'settlementAmount',
							name : 'settlementAmount'
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
							fieldLabel : '本人实收总额',
							id:'amount',
							name : 'amount',
							maxLength : 50,
							disabled:true,
							emptyText:'请输入上交金额',
							allowBlank:false,
							blankText:'上交金额不能为空!!',
							anchor : '98%'
						}/*, {
							xtype : 'numberfield',
							fieldLabel : '交班金额<span style="color:red">*</span>',
							id:'amount1',
							name : 'amount1',
							maxLength : 50,
							disabled:true,
							anchor : '98%'
						}*/, {
							xtype : 'numberfield',
							fieldLabel : '<span style="color:red">上交总额</span>',
							id:'sumAmount1',
							name : 'sumAmount1',
							maxLength : 50,
							disabled:true,
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
												if (cid == null) {
													Ext.getCmp('receivablesaccountDeptid').setValue(departStore.getAt(i).get("departId"));
													Ext.getCmp('receivablesaccountDept').setValue(departStore.getAt(i).get("departName"));
												}
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
								               				accountNum:Ext.get('accountNum').dom.value					               				
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

	Title = '添加资金交接单';
	if (cid != null) {
		Title = '修改资金交接单';
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
	}
	
	//上交金额查询
	Ext.Ajax.request({
	url : sysPath + "/" +  searchHandInAmountUrl,
	params : {
		paidIds : paidIds,
		privilege : privilege
	},success : function(res) {
		var resText = Ext.util.JSON.decode(res.responseText);
		if(resText.success=="false"){
			Ext.Msg.alert("友情提示",resText.msg);
		}else{
			var settlementAmount=resText.SETTLEMENT_AMOUNT;
			Ext.getCmp("amount").setValue(settlementAmount);
			Ext.getCmp("settlementAmount").setValue(settlementAmount);//计算本人未上交金额
			Ext.getCmp('sumAmount').setValue(settlementAmount);//上交总额
			Ext.getCmp('sumAmount1').setValue(settlementAmount);
		}

		},failure : function(form, action) {
				if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
					Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
				} else {
					if (action.result.msg) {
						win.hide();
						Ext.Msg.alert("友情提示", action.result.msg,
								function() {
									//dataReload();
								});

					}
				}
			}
	})

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
			
			var receivablesaccountId=Ext.getCmp('receivablesaccountId').getValue();//收款账号ID 
			var paymentaccountId=Ext.getCmp('paymentaccountId').getValue();//付款账号ID
			if(paymentaccountId==""){
				Ext.Msg.alert("友情提示","付款账号没有选中，请重新选择！");
				return;
			}
			
			if(receivablesaccountId==""){
				Ext.Msg.alert("友情提示","收款账号没有选中，请重新选择！");
				return;
			}
			
			//Ext.getCmp("sumAmount").setValue(Ext.getCmp("sumAmount").getValue());//计算上交总金额
						
			Ext.Msg.confirm(alertTitle,'您本次上交总额为：<font color="#FF0000">'+Ext.getCmp("settlementAmount").getValue()+'</font>元,确认要上交吗?',function(btnYes) {
				if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
					if (form.getForm().isValid()) {
						this.disabled = true;// 只能点击一次

						
						form.getForm().submit({
							url : sysPath + handInConfirmationUrl,
							params : {
								paidIds : paidIds,
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
付款单核销*/
function paymentVerification(_records){
	var ids="";
	var settlementAmount=0.0;
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要核销付款单！");
		return false;
	}
	
	for(var i=0;i<_records.length;i++){
		if(_records[i].data.verificationStatus==1){
			Ext.Msg.alert("系统消息", "核销失败，实收实付单号"+_records[i].data.id+"已核销！");
			return false;
		}
		if(_records[i].data.paymentType==1){
			Ext.Msg.alert("系统消息", "核销失败，实收实付单号"+_records[i].data.id+"为收款单，不允许手工核销！");
			return false;
		}
		
		settlementAmount=settlementAmount+_records[i].data.settlementAmount;
		ids=ids+_records[i].data.id+',';
	}
	
	Ext.Msg.confirm(alertTitle,'您本次将共核销<font color="#FF0000">'+settlementAmount+'</font>元,确认要核销吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + paymentVerificationUrl,
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
	






//var pageSize = 1;
var comboxPage = 15;
var privilege = 117;//应收应付
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var maingridSearchUrl = "fi/fiPaymentAcion!ralaList.action";

//收款确认弹出窗口时加载(应收总额、未收总额等信息)
var searchReceivingUrl="fi/fiPaymentAcion!searchReceiving.action";

//收款确认保存
var saveReceivingUrl="fi/fiPaymentAcion!saveReceiving.action";

//付款确认弹出窗口时加载(应付总额、未付总额等信息)
var searchPaymentUrl="fi/fiPaymentAcion!searchPayment.action";

//付款确认保存
var savePaymentUrl="fi/fiPaymentAcion!savePayment.action";

//委托收付款保存
var saveEntrustUrl="fi/fiPaymentAcion!saveEntrust.action";

//挂账保存
var saveLossesUrl="fi/fiPaymentAcion!saveLosses.action";

//审核单据
var auditUrl="fi/fiPaymentAcion!audit.action";

//撤销审核
var revocationAuditUrl="fi/fiPaymentAcion!revocationAudit.action";

var saveUrl = "fi/fiPaymentAcion!save.action";
var delUrl = "fi/fiPaymentAcion!delete.action";

var dateStore, customerStore, departStore,maindateStore,bussStore;
var paymentTypeStore,documentsTypeStore,documentsSmalltypeStore,costTypeStore,fiArrearsetStore,paymentStatusStore;
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
		var count_do=0;
		var count_exception=0;
		var count_chong=0;
			
		for(var j=0;j<vnetmusicRecord.length;j++){;
			count_total+=parseFloat(vnetmusicRecord[j].data.amount)-parseFloat(vnetmusicRecord[j].data.settlementAmount)
							-parseFloat(vnetmusicRecord[j].data.abnormalAmount)-parseFloat(vnetmusicRecord[j].data.eliminationAmount);
			count_do+=parseFloat(vnetmusicRecord[j].data.settlementAmount);
			count_exception+=parseFloat(vnetmusicRecord[j].data.abnormalAmount);
			count_chong+=parseFloat(vnetmusicRecord[j].data.eliminationAmount);
	 	}
	
	 	Ext.getCmp('count').setValue(vnetmusicRecord.length);
	 	Ext.getCmp('count_total').setValue(count_total);
	 	Ext.getCmp('count_do').setValue(count_do);
	 	Ext.getCmp('count_exception').setValue(count_exception);
	 	Ext.getCmp('count_chong').setValue(count_chong);
	}

	
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
              },'&nbsp;','应收付总额:',
		    	{xtype : 'numberfield',
                id:'count_total',
   			    readOnly:true,
                width:49,
                 value:0
              },'&nbsp;','已收付总额:',
		    	{xtype : 'numberfield',
   	            id:'count_do',
   			    readOnly:true,
   			    value:0,
                width:49
              },'&nbsp;','异常到付总额:',
		    	{xtype : 'numberfield',
   	            id:'count_exception',
   			    readOnly:true,
   			    value:0,
                width:49
              },'&nbsp;','冲销总额:',
		    	{xtype : 'numberfield',
   	            id:'count_chong',
   			    readOnly:true,
   			    value:0,
                width:49
              }]
		});

var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});

var fields = [{
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
			name : "amount", // 应收付金额
			mapping : 'amount'
		}, {
			name : "settlementAmount", // 已收付金额
			mapping : 'settlementAmount'
		}, {
			name : "abnormalAmount", // 异常到付款金额
			mapping : 'abnormalAmount'
		}, {
			name : "eliminationAmount", // 冲销金额
			mapping : 'eliminationAmount'
		}, {
			name : "workflowNo", // 流程号
			mapping : 'workflowNo'
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
			name : "sourceData", // 数据来源
			mapping : 'sourceData'
		}, {
			name : "sourceNo", //来源单号
			mapping : 'sourceNo'
		}, {
			name : "collectionUser", // 收款责任人
			mapping : 'collectionUser'
		}, {
			name : "paymentMark", // 收款级别
			mapping : 'paymentMark'
		}, {
			name : "entrustDept", // 委托部门
			mapping : 'entrustDept'
		}, {
			name : "entrustUser", // 委托人
			mapping : 'entrustUser'
		}, {
			name : "entrustTime", // 委托时间
			mapping : 'entrustTime'
		}, {
			name : "entrustRemark", // 委托备注
			mapping : 'entrustRemark'
		}, {
			name : "entrustDeptid", // 委托部门ID
			mapping : 'entrustDeptid'
		}, {
			name : 'departName',// 部门名称
			mapping : 'departName'
		}, {
			name : 'departId',// 部门名称id
			mapping : 'departId'
		}, {
			name : 'createRemark',// 创建摘要
			mapping : 'createRemark'
		}, {
			name : 'reviewStatus',// 审核状态
			mapping : 'reviewStatus'
		}, {
			name : 'reviewUser',// 审核人
			mapping : 'reviewUser'
		}, {
			name : 'reviewDate',// 审核时间
			mapping : 'reviewDate'
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

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '应收付单号',
			dataIndex : 'id',
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
			width:65,
			sortable:true
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
					v='异常到付款';
				}else if(v==9){
					v='已挂账';
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
			header : '应收付金额',
			dataIndex : 'amount',
			width:65,
			sortable:true
		}, {
			header : '已收付金额',
			dataIndex : 'settlementAmount',
			width:65,
			sortable:true
		}, {
			header : '异常到付款金额',
			dataIndex : 'abnormalAmount',
			width:65
		}, {
			header : '冲销金额',
			dataIndex : 'eliminationAmount',
			width:65,
			sortable:true
		}, {
			header : '流程号',
			dataIndex : 'workflowNo',
			width:65,
			sortable:true
		}, {
			header : '往来单位',
			dataIndex : 'contacts',
			width:80,
			sortable:true
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商名称',
			dataIndex : 'customerName',
			width:65
		}, {
			header : '审核状态',
			dataIndex : 'reviewStatus',
			renderer:function(v,metaData){
				if(v==0){
					v='<font color="#0000FF">未审核</font>';
				}else if (v==1){
					v='已审核';
				}
				return v;
			},
			width:65,
			sortable:true
		}, {
			header : '审核人',
			dataIndex : 'reviewUser',
			hidden : true
		}, {
			header : '审核时间',
			dataIndex : 'reviewDate',
			hidden : true
		}, {
			header : '数据来源',
			dataIndex : 'sourceData',
			width:80,
			sortable:true
		}, {
			header : ' 来源单号',
			dataIndex : 'sourceNo',
			width:65,
			sortable:true
		}, {
			header : '收款责任人',
			dataIndex : 'collectionUser',
			width:65
		}, {
			header : '收款级别',
			dataIndex : 'paymentMark',
			width:65,
			sortable:true
		}, {
			header : '委托部门',
			dataIndex : 'entrustDept',
			width:65,
			sortable:true
		}, {
			header : '委托人',
			dataIndex : 'entrustUser',
			hidden : true,
			width:65
		}, {
			header : ' 委托时间',
			dataIndex : 'entrustTime',
			hidden : true,
			width:65,
			sortable:true
		}, {
			header : '委托备注',
			dataIndex : 'entrustRemark',
			width:65,
			sortable:true
		}, {
			header : '委托部门ID',
			dataIndex : 'entrustDeptid',
			hidden : true,
			hideable:false
		}, {
			header : '所属部门',
			dataIndex : 'departName',
			width:100
		}, {
			header : '创建摘要',
			dataIndex : 'createRemark',
			width:200
		}, {
			header : '创建部门ID',
			dataIndex : 'departId',
			hidden : true,
			hideable:true
		}, {
			header : '创建人',
			dataIndex : 'createName',
			width:70
		}, {
			header : '创建时间',
			dataIndex : 'createTime',
			width:100
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
			hideable:true
		}]);

var menu = new Ext.menu.Menu({
				    id: 'receiptMenu',
				    items: [{
								text : '<b>登记</b>',
								iconCls : 'table',
								id : 'exceptionAdd',
								tooltip : '登记',
								handler : exceptionAdd
					    	}/*,{
								text : '<b>查看历史</b>',
								iconCls : 'table',
								id : 'exceptionList',
								tooltip : '查看历史',
								handler : exceptionList
				    		}*/]
				    });
				    
var exceptionBtn=new Ext.Button({
			text : '<B>异常</B>',
			id : 'exceptionBtn',
			tooltip : '异常到付款',
			iconCls : 'group',
			menu: menu
		});	
	
var receivingmenu = new Ext.menu.Menu({
				    id: 'receivingmenu',
				    items: [{
								text : '<b>其它收款单</b>',
								iconCls : 'table',
								id : 'receivingAdd',
								tooltip : '录入其它收款单',
								handler : receivingAdd
							}, {
								text : '<b>其它付款单</b>',
								iconCls : 'table',
								id : 'paymentAdd',
								tooltip : '录入其它付款单',
								handler : paymentAdd
							}]
				    });
				    
var receivingBtn=new Ext.Button({
			text : '<B>录入</B>',
			id : 'receivingBtn',
			tooltip : '录入',
			iconCls : 'group',
			menu: receivingmenu
		});	
		
		
var tbar = [{
			text : '<b>收款</b>',
			iconCls : 'save',
			id : 'confirmReceiving',
			tooltip : '收款',
			handler : confirmReceiving
		}, '-', {
			text : '<b>付款</b>',
			iconCls : 'save',
			id : 'confirmPayment',
			tooltip : '付款',
			handler : confirmPayment
		}, '-', {
			text : '<b>委托</b>',
			iconCls : 'table',
			id : 'entrustPayment',
			tooltip : '跨部门委托',
			handler : entrustPayment
		}, '-', receivingBtn, '-', {
			text : '<b>挂账</b>',
			iconCls : 'table',
			id : 'losses',
			tooltip : '挂账',
			handler : losses
		}, '-', exceptionBtn, '-', {
			text : '<b>收付记录</b>',
			iconCls : 'table',
			id : 'paidList',
			tooltip : '收付记录',
			handler : paidList
		}, '-', {
			text : '<b>审核</b>',
			iconCls : 'save',
			tooltip : '审核',
			handler : function() {
				var car = Ext.getCmp('mainGrid');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				audit(_records);
				}	
		}, '-', {
			text : '<b>撤销审核</b>',
			iconCls : 'save',
			tooltip : '撤销审核',
			handler : function() {
				var car = Ext.getCmp('mainGrid');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				revocationAudit(_records);
				}	
		}, '-', {
			text : '<b>单票添加</b>',
			iconCls : 'groupAdd',
			tooltip : '单票添加',
			handler : function() {
				addcolumn();
				}	
		},'-', {
			text : '<b>导出</b>',
			iconCls : 'table',
			id : 'outExcel',
			handler : outExcel
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}

/*
 * , '-', { xtype : 'textfield', blankText : '查询数据', id : 'searchContent' },
 * '-', { xtype : "combo", width : 100, triggerAction : 'all',
 * 
 * model : 'local', hiddenId : 'checkItems', hiddenName : 'checkItems', name :
 * 'checkItemstext', store : [['', '查询全部'], // ['EQS_id', 'ID'],
 * ['LIKES_cusName', '客商名称'], ['LIKES_departName', '部门名称']], emptyText : '选择类型',
 * forceSelection : true, listeners : { 'select' : function(combo, record,
 * index) { if (combo.getValue() == '') { Ext.getCmp("searchContent").disable();
 * Ext.getCmp("searchContent").show(); Ext.getCmp("searchContent").setValue(""); }
 * else { Ext.getCmp("searchContent").enable();
 * Ext.getCmp("searchContent").show(); } } } }, '-', { text : '<b>搜索</b>', id :
 * 'btn', iconCls : 'btnSearch', handler : searchCar } , /*{text : '<b>高级查询</b>',iconCls :
 * 'btnSearch', handler : searchCar }
 */
];

Ext.onReady(function() {
	
	// 收付类型
	paymentTypeStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['1', '收款单'], ['2', '付款单']],
				fields : ["paymentType", "paymentTypeName"]
			});
			
	paymentStatusStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['1', '未收款'], ['2', '已收款'], ['4', '未付款'], ['5', '已付款'], ['7', '到付转欠款'], ['8', '异常']],
				fields : ["paymentStatus", "paymentStatusName"]
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
			
			
	// 能到付转欠款客商
	fiArrearsetStore = new Ext.data.Store({
				storeId : "fiArrearsetStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/fi/fiArrearsetAcion!ralaList.action"
						}),
				baseParams : {
					filter_LIKES_ispaytoarrears:'能',//能否到付转欠款
					privilege : 60
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'customerId',
								mapping : 'customerId'
							}, {
								name : 'cusName',
								mapping : 'cusName'
							}])
			});

	// 部门列表
	/*departStore = new Ext.data.Store({
				storeId : "departStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + departGridSearchUrl
						}),
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
			});*/
	//业务部门
	bussStore = new Ext.data.Store({ 
            storeId:"bussStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!ralaList.action",method:'post'}),
            baseParams:{
               privilege:53,
               filter_EQL_isBussinessDepa:1
               },
            reader: new Ext.data.JsonReader({
                        root: 'result', totalProperty:'totalCount'
                    },[ {name:'departName', mapping:'departName'},    
                        {name:'departId', mapping: 'departId'}
            ]),                                      
            sortInfo:{field:'departId',direction:'ASC'}
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

	// 查询主列表
	maindateStore = new Ext.data.Store({
				storeId : "maindateStore",
				baseParams : {limit:pageSize,privilege : privilege},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + maingridSearchUrl
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
			items : ['单据日期', {
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
						listWidth:245,
						emptyText : "请选择",
						width : 80,
						anchor : '95%'
					}, '-', '收付类型', {
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
					}, '-', '单据大类', {
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
					}]
		});
		var tbarsearch1 = new Ext.Toolbar({
			items : ['收款状态', {
						xtype : 'combo',
						id : 'comboPaymentStatus',
						triggerAction : 'all',
						store : paymentStatusStore,
						//allowBlank : false,
						emptyText : "请选择",
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'paymentStatusName',// 显示值，与fields对应
						valueField : 'paymentStatus',// value值，与fields对应
						hiddenName:'paymentStatus',
						width : 55,
						anchor : '95%'
					}, '-', '单据小类', {
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
						xtype : 'numberfield',
						width : 40,
						id : 'fielddocumentsNo',
						enableKeyEvents:true,
						listeners : {
							keypress:function(textField, e){
								if(e.getKey() == 13){
									dataReload();
								}
							}
						}
					},'数据来源', {
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
						width : 80,
						anchor : '95%'
					}, '-', '来源单号', {
						xtype : 'numberfield',
						width : 40,
						id : 'combosourceNo',
						enableKeyEvents:true,
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
					},'&nbsp;',{
						text : '<B>清空记录</B>',
						iconCls:'groupClose',
						handler : deleteRecordStore
					}]
				});
		tbarsearch.render(mainpanel.tbar);
		tbarsearch1.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();


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
 * 应收应付查询
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
 * 应收应付查询后刷新
 */
function dataReload() {
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var documentsType = Ext.getCmp("combodocumentsType").getValue(); // 单据大类
	var documentsSmalltype = Ext.getCmp("combodocumentsSmalltype").getValue(); // 单据小类
	var documentsNo = Ext.getCmp("fielddocumentsNo").getValue(); // 单据号
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商ID
	var paymentType = Ext.getCmp("combopaymentType").getValue(); // 收付类型
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID
	var sourceData = Ext.getCmp("combosourceData").getValue(); // 收付类型
	var sourceNo = Ext.getCmp("combosourceNo").getValue(); // 收付类型
	var contacts = Ext.getCmp("combocontacts").getValue(); //往来单位
	var paymentStatus = Ext.getCmp("comboPaymentStatus").getValue(); //收款状态
	
	Ext.apply(maindateStore.baseParams, {
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_documentsType : documentsType,
		filter_EQS_documentsSmalltype : documentsSmalltype,
		filter_EQS_customerId : customerId,
		filter_EQS_paymentType : paymentType,
		departId1 : departId,
		filter_EQS_documentsNo:documentsNo,
		filter_EQS_sourceData:sourceData,
		filter_EQS_sourceNo:sourceNo,
		filter_EQS_paymentStatus:paymentStatus,
		filter_LIKES_contacts:contacts,
		
		privilege:privilege
	});
	
	maindateStore.reload({
				params : {
					start : 0
				}
			});
}

function deleteRecordStore(){
	var grid1=Ext.getCmp('mainGrid');
	var vnetmusicRecord = grid1.getSelectionModel().getSelections();
	for(var i=0;i<vnetmusicRecord.length;i++){
		grid1.getStore().remove(vnetmusicRecord[i]);
	}
}

/**
 * 收款确认
 */
function confirmReceiving(){
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID	
	//现金收款
	var cashprivilege = 110;
	var cashSearchUrl="fi/fiCapitaaccountsetAction!ralaList.action";
	
	//委托收款
	var entrustprivilege = 125;
	var entrustSearchUrl="fi/fiCashiercollectionAction!ralaList.action";
	
	//付款单URL
	var paymentprivilege = 117;//应收应付
	var paymentSearchUrl="fi/fiPaymentAcion!ralaList.action";
	
	//预付冲销URL
	var advanceprivilege = 127;//预付冲销
	var advanceSearchUrl="fi/fiAdvancesetAction!ralaList.action";
	

	//获取选中的行
	var ids="";
	var car = Ext.getCmp('mainGrid');
	var _records = car.getSelectionModel().getSelections();// 获取所有选中行
	var documentsSmalltype="";//单据小类
	var customerId="";//客商ID
	var penyType="";//结算方式
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择应收单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		
		ids=ids+_records[i].data.id+',';
		if(_records[i].data.paymentType!=1){
			Ext.Msg.alert("系统消息","只能选择收款单进行收款，请重新选择！");
			return false;
		}
		
		if(_records[i].data.paymentStatus==0){
			var alt="应收单["+_records[i].data.id+"]已作废，不能收款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		
		if(_records[i].data.paymentStatus==2){
			var alt="应收单["+_records[i].data.id+"]已收款，不能重复收款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		if(i>0&&_records[i].data.documentsSmalltype!=documentsSmalltype){
			Ext.Msg.alert("系统消息", "不同[单据小类]请分开收银！");
			return false;
		}
		if(i>0&&_records[i].data.customerId!=customerId){
			Ext.Msg.alert("系统消息", "不同客商请分开收银！");
			return false;
		}
		if(i>0&&_records[i].data.penyType!=penyType){
			Ext.Msg.alert("系统消息", "不同客商请分开收银！");
			return false;
		}
		penyType=_records[i].data.penyType;
		documentsSmalltype=_records[i].data.documentsSmalltype;
		customerId=_records[i].data.customerId;
	}
	
	if(customerId==0){
		customerId="";
	}
	//现金收款开始
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
						name : 'openingBalance',// 开户行
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
						var penyJenis=receivingForm.getForm().getValues()["penyJenis"]; //收款方式
		                	var thesettlementAmount=Ext.getCmp("thesettlementAmount").getValue();
		                	if(penyJenis==undefined){
		                		Ext.Msg.alert("系统消息", "请选择收款方式！");
								return false;
		                	}
		                	if (penyJenis=="现金"||penyJenis=="POS"){
		                		userIds=userId;
		                		departIds="";
		                	}else if(penyJenis=="银行"){
		                		departIds=departId;
		                		userIds="";
		                	}
			                	
						cashdateStore.reload({
									params : {
										filter_EQL_responsibleId:userIds,
										filter_EQL_departId:departIds,
										filter_LIKES_accountTypeName:penyJenis,
										filter_EQL_paymentType:14250,//收款账号:收入
										filter_EQL_isDelete:1,
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
				//filter_EQL_responsibleId:userId,
				filter_EQL_isDelete:1,
				privilege:cashprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		var cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'cashGrid',
					title: '选择现金账号',
					height : 249,
					width : 620,
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
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : cashdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		cashdateStore.on('load',function(){
	    		if(cashGrid.getStore().getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});

		//现金收款结束
				
	//委托收款开始
	var entrustsm = new Ext.grid.CheckboxSelectionModel();
	var entrustrowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});
		var entrustfields = [{
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
								name : "bank", // 资金账号设置表开户行
								mapping : 'bank'
							}, {
								name : "responsible", // 负责人
								mapping : 'responsible'
							}, {
								name : 'createDept',// 创建部门
								mapping : 'createDept'
							}, {
								name : 'createDeptId',// 创建部门ID
								mapping : 'createDeptId'
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
		var entrustcm=new Ext.grid.ColumnModel([entrustrowNum, entrustsm, {
								header : '收款单号',
								dataIndex : 'id',
								width : 60
							}, {
								header : '结算方式',
								dataIndex : 'penyJenis',
								width : 60
							}, {
								header : '到账日期',
								dataIndex : 'collectionTime',
								width : 60
							}, {
								header : '到账金额',
								dataIndex : 'collectionAmount',
								width : 60
							}, {
								header : '委托确认金额',
								dataIndex : 'entrustAmount',
								width : 60
							},{
								header : '核销金额',
								dataIndex : 'verificationAmount',
								width : 60
							}, {
								header : '收款部门',
								dataIndex : 'departName',
								width : 80
							}, {
								header : ' 收款账号id',
								dataIndex : 'fiCapitaaccountsetId',
								hidden : true
							}, {
								header : ' 收款账号',
								dataIndex : 'accountNum',
								width : 80
							}, {
								header : '收款开户行',
								dataIndex : 'bank',
								width : 80
							}, {
								header : '收款负责人',
								dataIndex : 'responsible',
								width : 60
							}, {
								header : ' 核销状态',
								dataIndex : 'verificationStatus',
								renderer:function(v,metaData){
									if(v==0){
										v='未核销';
									}else if (v==1){
										v='已核销';
									}
									return v;
								}
							}, {
								header : '核销时间',
								dataIndex : 'verificationTime',
								width : 60
							}, {
								header : '核销人',
								dataIndex : 'verificationUser',
								width : 60
							}, {
								header : '核销部门',
								dataIndex : 'verificationDept',
								width : 60
							}, {
								header : '委托确认时间',
								dataIndex : 'entrustTime',
								width : 60
							}, {
								header : '委托确认人',
								dataIndex : 'entrustUser',
								hidden : true
							}, {
								header : '委托确认备注',
								dataIndex : 'entrustRemark'
							}, {
								header : '时间戳',
								dataIndex : 'ts',
								sortable : true,
								hidden : true,
								hideable:false
							}])
		var entrustTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'cashcheckItems',
				hiddenName : 'cashcheckItems',
				name : 'checkItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['EQL_collectionAmount', '收款金额']],
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
				id : 'btn1',
				iconCls : 'btnSearch',
				handler :  function() {
						entrustdateStore.baseParams = {
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value,
					 		filter_EQL_verificationStatus:0,//核销状态=未核销
					 		filter_EQL_status:1,//状态为正常
		                	privilege:entrustprivilege,
							limit : pageSize
						}

						entrustdateStore.reload();	
						}
				}];
		// 查询主列表
		var entrustdateStore = new Ext.data.Store({
			storeId : "dateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + entrustSearchUrl,
						method:'POST'
					}),
			baseParams:{
			 		filter_EQL_verificationStatus:0,//核销状态=未核销
			 		filter_EQL_status:1,//状态为正常
                	privilege:entrustprivilege,
					limit : pageSize
                },
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, entrustfields)
			});
		
		var entrustGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'entrustGrid',
					title: '选择出纳收款单',
					height : 249,
					width : 620,
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
					sm : entrustsm,
					cm : entrustcm,
					tbar : entrustTbar,
					ds : entrustdateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : entrustdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
		//委托收款结束
	 	
		//收付对冲开始
		var paymentsm = new Ext.grid.CheckboxSelectionModel();
		var paymentrowNum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 25,
				sortable : true
			});
		var paymentfields = [{
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
								name : "amount", // 应收付金额
								mapping : 'amount'
							}, {
								name : "settlementAmount", // 已收付金额
								mapping : 'settlementAmount'
							}, {
								name : "eliminationAmount", // 冲销金额
								mapping : 'eliminationAmount'
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
								name : "sourceData", // 数据来源
								mapping : 'sourceData'
							}, {
								name : "sourceNo", //来源单号
								mapping : 'sourceNo'
							}, {
								name : "collectionUser", // 收款责任人
								mapping : 'collectionUser'
							}, {
								name : "paymentMark", // 收款级别
								mapping : 'paymentMark'
							}, {
								name : "entrustDept", // 委托部门
								mapping : 'entrustDept'
							}, {
								name : "entrustUser", // 委托人
								mapping : 'entrustUser'
							}, {
								name : "entrustTime", // 委托时间
								mapping : 'entrustTime'
							}, {
								name : "entrustRemark", // 委托备注
								mapping : 'entrustRemark'
							}, {
								name : "entrustDeptid", // 委托部门ID
								mapping : 'entrustDeptid'
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
		var paymentcm=new Ext.grid.ColumnModel([paymentrowNum, paymentsm, {
								header : '应付单号',
								dataIndex : 'id',
								width:65
							}, {
								header : '收付类型',
								dataIndex : 'paymentType',
								width:65,
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
								hidden : true
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
								},width:65
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
								header : '应付金额',
								dataIndex : 'amount',
								width:65
							}, {
								header : '已付金额',
								dataIndex : 'settlementAmount',
								width:65
							}, {
								header : '冲销金额',
								dataIndex : 'eliminationAmount',
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
								header : '数据来源',
								dataIndex : 'sourceData',
								width:80
							}, {
								header : ' 来源单号',
								dataIndex : 'sourceNo',
								width:65
							}, {
								header : '收款责任人',
								dataIndex : 'collectionUser',
								width:65,
								hidden : true
							}, {
								header : '收款级别',
								dataIndex : 'paymentMark',
								width:65,
								hidden : true
							}, {
								header : '委托部门',
								dataIndex : 'entrustDept',
								width:65,
								hidden : true
							}, {
								header : '委托人',
								dataIndex : 'entrustUser',
								width:65,
								hidden : true
							}, {
								header : ' 委托时间',
								dataIndex : 'entrustTime',
								width:65,
								hidden : true
							}, {
								header : '委托备注',
								dataIndex : 'entrustRemark',
								width:65,
								hidden : true
							}, {
								header : '委托部门ID',
								dataIndex : 'entrustDeptid',
								width:65,
								hidden : true
							}, {
								header : '创建部门',
								dataIndex : 'createDept',
								width:65,
								hidden : true
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
							}])
		var paymentTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'cashcheckItems',
				hiddenName : 'cashcheckItems',
				name : 'cashcheckItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['EQL_amount', '付款金额']],
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
				id : 'btn2',
				iconCls : 'btnSearch',
				handler :  function() {
						paymentdateStore.baseParams = {
							filter_EQL_customerId:customerId,
							filter_LIKES_paymentType:2,//付款单
							departId1 : departId,
							privilege:paymentprivilege,
							limit : pageSize,
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value
						}
						paymentdateStore.reload();	
						}
				}];
		// 查询主列表
		var paymentdateStore = new Ext.data.Store({
			storeId : "dateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + paymentSearchUrl,
						method:'POST'
					}),
			baseParams:{
			 		filter_EQL_customerId:customerId,
					filter_LIKES_paymentType:2,//付款单
					departId1 : departId,
					privilege:paymentprivilege,
					limit : pageSize
                },
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, paymentfields)
			});
		
		var paymentGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'paymentGrid',
					title: '选择付款单',
					height : 249,
					width : 620,
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
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : paymentdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
				
	paymentGrid.on('click', function() {
		var amount=_records[0].data.amount;
		var settlementAmount=_records[0].data.settlementAmount;
		var eliminationAmount=_records[0].data.eliminationAmount;
		amount=amount-(settlementAmount+eliminationAmount);
		
		var paymentGrid = Ext.getCmp('paymentGrid');
		var _recordpayment = paymentGrid.getSelectionModel().getSelections();// 获得所有选中的行
		if (_recordpayment.length > 1) {
			Ext.Msg.alert("提示","只能选择一个付款单");
		}
		var amount1=_recordpayment[0].data.amount;
		var settlementAmount1=_recordpayment[0].data.settlementAmount;
		var eliminationAmount1=_recordpayment[0].data.eliminationAmount;
		amount1=amount1-(settlementAmount1+eliminationAmount1);
		if(amount>=amount1){
			amount=amount1;
		}
		Ext.getCmp("thesettlementAmount").setValue(amount);
		Ext.getCmp("thesettlementAmount").setDisabled(true);
		
	});
		//收付对冲结束
		
				
		//预付冲销开始
		var advancesm = new Ext.grid.CheckboxSelectionModel();
		var advancerowNum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 25,
				sortable : true
			});
		var advancefields = [{
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
							}];
		var advancecm=new Ext.grid.ColumnModel([advancerowNum, advancesm, {
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
		var advanceTbar = [{
				xtype : "combo",
				width : 100,
				triggerAction : 'all',
				model : 'local',
				hiddenId : 'cashcheckItems',
				hiddenName : 'cashcheckItems',
				name : 'checkItemstext',
				store : [['', '查询全部'],
						// ['EQS_id', 'ID'],
						['LIKES_collectionAmount', '收款金额']],
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
				id : 'btn3',
				iconCls : 'btnSearch',
				handler :  function() {
						cashdateStore.baseParams = {
							filter_EQL_customerId:customerId,
							filter_EQL_isdelete:1,
							privilege:advanceprivilege,
							departId1:departId,
							limit : pageSize,
							checkItems : Ext.get("cashcheckItems").dom.value,
							itemsValue : Ext.get("cashsearchSearch").dom.value
						}
						advancedateStore.reload();	
						}
				}];
		// 查询主列表
		var advancedateStore = new Ext.data.Store({
			storeId : "dateStore",
			proxy : new Ext.data.HttpProxy({
						url : sysPath + "/" + advanceSearchUrl,
						method:'POST'
					}),
			baseParams:{
				filter_EQL_customerId:customerId,
				filter_EQL_isdelete:1,
				privilege:advanceprivilege,
				departId1:departId,
				limit : pageSize
			},
			reader : new Ext.data.JsonReader({
						root : 'result',
						totalProperty : 'totalCount'
					}, advancefields)
			});
		
		var advanceGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'advanceGrid',
					title: '选择预存账号',
					height : 249,
					width : 620,
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
					sm : advancesm,
					cm : advancecm,
					tbar : advanceTbar,
					ds : advancedateStore,
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : advancedateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
		//预付冲销结束
				
		//documentsSmalltype
				
				
	 	//收款主Form
	    var receivingForm = new Ext.FormPanel({
        frame: true,
        labelWidth: 20,
        labelAlign:'right',
        width: 633,
        items: [{
        	xtype:'fieldset',
            title: '收款信息',
            labelWidth: 80,
            baseCls:"x-fieldset",
            autoHeight: true,
            items: [{
                xtype: 'radiogroup',
                fieldLabel: '收款类型',
                itemCls: 'x-check-group-alt',
                anchor: '95%',
                items: [{
                    	boxLabel: '现金',
                    	name: 'penyJenis',
                    	inputValue: "现金",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").hide();
    								cashGrid.render('cashInfo');
    								//searchCash();
    								/*cashdateStore.reload({
									params : {
											filter_EQL_responsibleId:userId,
											privilege:cashprivilege,
											limit : pageSize
										}
									});	*/
    							}
    						}
    					}
                    }/*,{
                    	boxLabel: '银行',
                    	name: 'penyJenis',
                    	inputValue: "银行",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").hide();
    								cashGrid.render('cashInfo');
    							}
    						}
    					}
                    }*/,{
                    	boxLabel: 'POS机',
                    	name: 'penyJenis',
                    	inputValue: "POS",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").hide();
    								cashGrid.render('cashInfo');
    							}
    						}
    					}
                    },{
                    	boxLabel: '支票',
                    	name: 'penyJenis',
                    	inputValue: "支票",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").hide();
    								Ext.getCmp("checkInfo").show();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").hide();
    							}
    						}
    					}
                    },{
                    	boxLabel: '委托确收',
                    	name: 'penyJenis',
                    	inputValue: "委托确收",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").hide();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").show();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").hide();
    								entrustGrid.render('entrustInfo');
    								
    							}
    						}
    					}
                    },{
                    	boxLabel: '收付对冲',
                    	name: 'penyJenis',
                    	inputValue: "收付对冲",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").hide();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").show();
    								Ext.getCmp("advanceInfo").hide();
    								paymentGrid.render('paymentInfo');
    							}
    						}
    					}
                    },{
                    	boxLabel: '预付冲销',
                    	name: 'penyJenis',
                    	inputValue: "预付冲销",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								if(penyType!="预付"){
			                		Ext.Msg.alert("系统消息","只有结算方式为预付才能选择预付冲销！");
			                		return false;
    								}
    								Ext.getCmp("cashInfo").hide();
    								Ext.getCmp("checkInfo").hide();
    								Ext.getCmp("entrustInfo").hide();
    								Ext.getCmp("paymentInfo").hide();
    								Ext.getCmp("advanceInfo").show();
    								advanceGrid.render('advanceInfo');
    							}
    						}
    					}
                    }
                    
                ]
            },{
            	layout:'column',
            	labelWidth: 80,
            	items:[{
	            		layout:'form',
	            		columnWidth:.30,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'结算总金额',
            				width:80,
            				id:'Amount',
            				name:'Amount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'本次结算金额',
            				width:80,
            				id:"thesettlementAmount",
            				name:'thesettlementAmount'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.30,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'已结算金额',
            				width:80,
            				id:'settlementAmount',
            				name:'settlementAmount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				width:80,
            				id:"Remark",
            				name:'Remark'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.40,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'未结算金额',
            				width:80,
            				id:"unsettlementAmount",
            				name:'unsettlementAmount',
            				disabled:true
            			}]
            		}]
                
            }]
        },{
        	xtype:'fieldset',
        	id:'checkInfo',
            name:'checkInfo',
            title: '支票信息',
            hidden:true,
            labelWidth: 80,
            baseCls:"x-fieldset",
            //autoHeight: true,
            layout:'column',
            items:[{
            	//layout:'column',
            	//labelWidth: 80,
            	//items:[{
	            		layout:'form',
	            		columnWidth:.3,
	            			items:[{
								xtype : 'hidden',
								id : 'checkcustomerId',
								name : 'checkcustomerId'
							},{
							xtype : 'combo',
							fieldLabel : '客商名称<span style="color:red">*</span>',
							allowBlank : true,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							width:80,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'cusName',
							name : 'cusName',
							listWidth:245,
							blankText : "客商名称为空！",
							anchor : '95%',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('checkcustomerId').setValue(record
											.get("customerId"));
								}
							}
						},{
            				xtype:'textfield',
            				fieldLabel:'支票号',
            				width:80,
            				id:"checkNo",
            				name:'checkNo'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.3,
            			items:[{
            				xtype:'numberfield',
            				fieldLabel:'开票金额',
            				width:80,
            				id:'checkamount',
            				name:'checkamount'
            			},{
            				xtype:'datefield',
            				fieldLabel:'开票日期',
            				width:80,
            				format : 'Y-m-d',
            				id:"checkDate",
            				name:'checkDate'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.3,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'出票人',
            				width:80,
            				id:"checkUser",
            				name:'checkUser'
            			},{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				width:80,
            				id:"checkRemark",
            				name:'checkRemark'
            			}]
            		}]
            //}]
       },{
        	xtype:'fieldset',
        	//hidden:true,
        	id:'cashInfo',
            name:'cashInfo',
            //title: '现金付款',
            labelWidth: 80,
            baseCls:"x-fieldset-s",
            autoHeight: true
       },{
        	xtype:'fieldset',
        	//hidden:true,
        	id:'entrustInfo',
            name:'entrustInfo',
            //title: '委托确收',
            labelWidth: 80,
            baseCls:"x-fieldset-s",
            autoHeight: true
       },{
        	xtype:'fieldset',
        	//hidden:true,
        	id:'paymentInfo',
            name:'paymentInfo',
            //title: '收付对冲',
            labelWidth: 80,
            baseCls:"x-fieldset-s",
            autoHeight: true
       },{
        	xtype:'fieldset',
        	//hidden:true,
        	id:'advanceInfo',
            name:'advanceInfo',
            //title: '预付冲销',
            labelWidth: 80,
            baseCls:"x-fieldset-s",
            autoHeight: true
       }]
     });
     
  
	Ext.Ajax.request({
		url : sysPath + "/" +  searchReceivingUrl,
		params : {
			ids : ids,
			documentsSmalltype:documentsSmalltype,//单据小类
			privilege : privilege
		},success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				var Amount=0;//结算总金额
				var settlementAmount=0;//已结算总金额
				var eliminationAmount=0;//已冲销
				var unsettlementAmount=0;//未结算总金额
				var thesettlementAmount=0;//本次结算总金额

				//unsettlementAmount=Amount-settlementAmount;
				///unsettlementAmount=Math.round(unsettlementAmount*100)/100;
				Amount=resText.amount;
				settlementAmount=resText.settlementAmount;
				thesettlementAmount=resText.thesettlementAmount;
				eliminationAmount=resText.eliminationAmount;
				
				
				unsettlementAmount=Amount-settlementAmount-eliminationAmount;
				Ext.getCmp("Amount").setValue(Amount);
				Ext.getCmp("settlementAmount").setValue(settlementAmount);
				Ext.getCmp("unsettlementAmount").setValue(unsettlementAmount);
				Ext.getCmp("thesettlementAmount").setValue(thesettlementAmount);
				ids1=ids.split(",");
				if(ids1.length>2){
					Ext.getCmp("thesettlementAmount").setDisabled(true);
				}
			}
	}) 
	
	var win = new Ext.Window({
			title : '收款确认',
			width : 650,
			height:450,
			closeAction : 'hide',
			bodyStyle: 'padding:0px 1px 0px 0px;',
			plain : true,
			modal : true,
			items: [{
			            xtype: 'panel',
			            bodyStyle: 'border-bottom:0px;',
			            height : 29,
			            tbar: [{
			                text: '确认收款',
			                iconCls : 'save',
			                handler:function(){
			                	//searchCash(receivingForm);
			                	var selectIds="";//选中行ID
			                	var penyJenis=receivingForm.getForm().getValues()["penyJenis"]; //收款方式
			                	var thesettlementAmount=parseFloat(Ext.getCmp("thesettlementAmount").getValue());//本次结算金额
			                	var unsettlementAmount=parseFloat(Ext.getCmp("unsettlementAmount").getValue());//未结金额
			                	//if(thesettlementAmount>unsettlementAmount){
			                	//	Ext.Msg.alert("系统消息","本次结算金额不能大于未结算金额！");
			                	//	return false;
			                	//}
			                	
			                	if(penyJenis==undefined){
			                		Ext.Msg.alert("系统消息", "请选择收款方式！");
									return false;
			                	}
			                	/*if(thesettlementAmount<=0){
			                		Ext.Msg.alert("系统消息", "收款金额不能小于零！");
									return false;
			                	}*/
			                	
			                	var accountNumId;
			                	if (penyJenis=="现金"||penyJenis=="银行"||penyJenis=="POS"){//现金
			                		var cashGrid = Ext.getCmp('cashGrid');
									var _records = cashGrid.getSelectionModel().getSelections();// 获取所有选中行
									selectIds=_records[0].data.id//银行账号(现金、银行、POS付款账号)
									if (_records.length < 1) {
										Ext.Msg.alert("系统消息", "请选择收款账号！");
										return false;
									} else if (_records.length > 1) {
										Ext.Msg.alert("系统消息", "一次只能选择一个收款账号！");
										return false;
									}
			                	}else if(penyJenis=="支票"){
			                		if (Ext.getCmp("checkcustomerId").getValue()==""){
			                			Ext.Msg.alert("系统消息", "请选择客商名称！");
										return false;
			                		}
			                		if (Ext.getCmp("checkNo").getValue()==""){
			                			Ext.Msg.alert("系统消息", "请输入支票号！");
										return false;
			                		}
			                		if (Ext.getCmp("checkDate").getValue()==""){
			                			Ext.Msg.alert("系统消息", "请选择开票日期！");
										return false;
			                		}
			                		if (Ext.getCmp("checkamount").getValue()<=0){
			                			Ext.Msg.alert("系统消息", "请输入支票金额！");
										return false;
			                		}
			                		if (Ext.getCmp("checkUser").getValue()<=0){
			                			Ext.Msg.alert("系统消息", "请输入开票人！");
										return false;
			                		}
			                	}else if (penyJenis=="收付对冲"){
			                		var _records = paymentGrid.getSelectionModel().getSelections();// 获取所有选中行
			                		for(var i=0;i<_records.length;i++){
			                			selectIds=selectIds+_records[i].data.id+",";
			                		}
	    							var ida=ids.split(",");
	    							if(ida.length>2){
	    								Ext.Msg.alert("系统消息","收款失败，收付对账一次只能选择一个收款单号。");
	    								return;
	    							}
	    							
			                		
			                	}else if(penyJenis=="委托确收"){
			                		var entrustGrid = Ext.getCmp('entrustGrid');
									var _records = entrustGrid.getSelectionModel().getSelections();// 获取所有选中行
									selectIds=_records[0].data.id;//选择的出纳收款单
									if (_records.length < 1) {
										Ext.Msg.alert("系统消息", "请选择出纳收款单！");
										return false;
									} else if (_records.length > 1) {
										Ext.Msg.alert("系统消息", "一次只能选择一个出纳收款单！");
										return false;
									}
			                	}else if(penyJenis=="预付冲销"){
			                		var advanceGrid = Ext.getCmp('advanceGrid');
									var _records = advanceGrid.getSelectionModel().getSelections();// 获取所有选中行
									selectIds=_records[0].data.id;//选择的预付款账号
									if (_records.length < 1) {
										Ext.Msg.alert("系统消息", "请选择预存款账号！");
										return false;
									} else if (_records.length > 1) {
										Ext.Msg.alert("系统消息", "一次只能选择一个预存款账号！");
										return false;
									}
			                	}
								Ext.Msg.confirm(alertTitle,'本次收款金额为：<font color="#FF0000">'+Ext.getCmp("thesettlementAmount").getValue()+'</font>元,您确定要收款吗?',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
										if (receivingForm.getForm().isValid()) {
											this.disabled = true;// 只能点击一次
											receivingForm.getForm().submit({
												url : sysPath + '/' + saveReceivingUrl,
												params : {
													ids : ids,
													settlementAmount:thesettlementAmount,
													documentsSmalltype:documentsSmalltype,//单据小类
													selectIds:selectIds,
													privilege : privilege
												},
												waitMsg : '正在保存数据...',
												success : function(form, action) {
														Ext.Msg.alert("友情提示",
															action.result.msg, function() {
																win.close();
																maindateStore.load();
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
			            },
			            '-', {
			                text: '关闭',
			                iconCls : 'save',
			                handler : function() {
								win.close();
							}
			            }]
		            },receivingForm]//,
			//items : receivingForm,
			/*buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]*/
		});
		win.on('show', function() {
					//Ext.getCmp("cashInfo").hide(); //现金
    				//Ext.getCmp("entrustInfo").hide();//委托收款
				});
		win.on('hide', function() {
					receivingForm.destroy();
				});
		win.show();
}


/**
 * 付款确认
 */
function confirmPayment(){
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID
	//现金收款
	var cashprivilege = 110;
	var cashSearchUrl="fi/fiCapitaaccountsetAction!findAccountList.action";

	//获取选中的行
	var ids="";
	var car = Ext.getCmp('mainGrid');
	var _records = car.getSelectionModel().getSelections();// 获取所有选中行
	var documentsSmalltype="";//单据小类
	var customerId="";//客商
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择应收单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		
		ids=ids+_records[i].data.id+',';
		if(_records[i].data.paymentType!=2){
			Ext.Msg.alert("系统消息","只能选择付款单进行付款，请重新选择！");
			return false;
		}
		
		if(_records[i].data.paymentStatus==0){
			var alt="付收单["+_records[i].data.id+"]已作废，不能付款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		
		if(_records[i].data.paymentStatus==5){
			var alt="付收单["+_records[i].data.id+"]已付款，不能重复付款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		if(i>0&&_records[i].data.documentsSmalltype!=documentsSmalltype){
			Ext.Msg.alert("系统消息", "不同[单据小类]请分开收银！");
			return false;
		}
		
		if(i>0&&_records[i].data.customerId!=customerId){
			Ext.Msg.alert("系统消息", "不同客商请分开收银！");
			return false;
		}
		documentsSmalltype=_records[i].data.documentsSmalltype;
		customerId=_records[i].data.customerId;
	}
	
	//现金付款开始
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
						var penyJenis=receivingForm.getForm().getValues()["penyJenis"]; //收款方式
		                	var thesettlementAmount=Ext.getCmp("thesettlementAmount").getValue();
		                	if(penyJenis==undefined){
		                		Ext.Msg.alert("系统消息", "请选择收款方式！");
								return false;
		                	}
			            if(penyJenis=="现金"){
			            	accountType="14252";
			            }else if(penyJenis=="银行"){
			            	accountType="14253";
			            }else if(penyJenis=="POS"){
			            	accountType="14254";
			            }
						cashdateStore.reload({
									params : {
										userId:userId,
										departId:departId,
										accountType:accountType,//账号类型
										paymentType:14251,//付款账号: 支出
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
				//filter_EQL_responsibleId:userId,
				filter_EQL_isDelete:1,
				privilege:cashprivilege
			},
			reader : new Ext.data.JsonReader({
						root : 'resultMap',
						totalProperty : 'totalCount'
					}, cashfields)
			});
		
		var cashGrid = new Ext.grid.GridPanel({
					region : "center",
					id : 'paymentGrid',
					title: '选择现金账号',
					height : 249,
					width : 620,
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
					bbar : new Ext.PagingToolbar({
								pageSize : pageSize,
								store : cashdateStore,
								displayInfo : true,
								displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
								emptyMsg : "没有记录信息显示"
							})
				});
		//store的load事件(如果store返回只有一行，默认为选中)
		cashdateStore.on('load',function(){
	    		if(cashGrid.getStore().getTotalCount()==1){
	    			cashGrid.getSelectionModel().selectFirstRow(0);
	    		}
			});

		//现金付款结束
				

				
	 	//付款主Form
	    var receivingForm = new Ext.FormPanel({
        frame: true,
        labelWidth: 20,
        labelAlign:'right',
        width: 633,
        items: [{
        	xtype:'fieldset',
            title: '付款信息',
            labelWidth: 80,
            baseCls:"x-fieldset",
            autoHeight: true,
            items: [{
                xtype: 'radiogroup',
                fieldLabel: '付款类型',
                itemCls: 'x-check-group-alt',
                anchor: '40%',
                items: [{
                    	boxLabel: '现金',
                    	name: 'penyJenis',
                    	inputValue: "现金",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								cashGrid.render('cashInfo');
    							}
    						}
    					}
                    },{
                    	boxLabel: '银行',
                    	name: 'penyJenis',
                    	inputValue: "银行",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								cashGrid.render('cashInfo');
    							}
    						}
    					}
                    },{
                    	boxLabel: 'POS',
                    	name: 'penyJenis',
                    	inputValue: "POS",
                    	listeners:{
    						'check' : function(checkbox, checked) {
    							if(checked){
    								Ext.getCmp("cashInfo").show();
    								cashGrid.render('cashInfo');
    							}
    						}
    					}
                    }]
            },{
            	layout:'column',
            	labelWidth: 80,
            	items:[{
	            		layout:'form',
	            		columnWidth:.30,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'结算总金额',
            				width:80,
            				id:'Amount',
            				name:'Amount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'本次结算金额',
            				width:80,
            				id:"thesettlementAmount",
            				name:'thesettlementAmount'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.30,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'已结算金额',
            				width:80,
            				id:'settlementAmount',
            				name:'settlementAmount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				width:80,
            				id:"Remark",
            				name:'Remark'
            			}]
            		},{
	            		layout:'form',
	            		columnWidth:.40,
            			items:[{
            				xtype:'textfield',
            				fieldLabel:'未结算金额',
            				width:80,
            				id:"unsettlementAmount",
            				name:'unsettlementAmount',
            				disabled:true
            			}]
            		}]
                
            }]
        },{
        	xtype:'fieldset',
        	//hidden:true,
        	id:'cashInfo',
            name:'cashInfo',
            //title: '现金付款',
            labelWidth: 80,
            baseCls:"x-fieldset-s",
            autoHeight: true
       }]
     });
     
    
	Ext.Ajax.request({
		url : sysPath + "/" +  searchPaymentUrl,
		params : {
			ids : ids,
			documentsSmalltype:documentsSmalltype,//单据小类
			privilege : privilege
		},success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				var Amount=0;//结算总金额
				var settlementAmount=0;//已结算总金额
				var unsettlementAmount=0;//未结算总金额
				var thesettlementAmount=0;//本次结算总金额
				
				for(var i=0;i<resText.resultMap.length;i++){
					Amount=Amount+resText.resultMap[i].AMOUNT;
					settlementAmount=settlementAmount+resText.resultMap[i].SETTLEMENT_AMOUNT;
				}
				unsettlementAmount=Amount-settlementAmount;
				Ext.getCmp("Amount").setValue(Amount);
				Ext.getCmp("settlementAmount").setValue(settlementAmount);
				Ext.getCmp("unsettlementAmount").setValue(unsettlementAmount);
				Ext.getCmp("thesettlementAmount").setValue(unsettlementAmount);
				if(resText.resultMap.length>1){
					Ext.getCmp("thesettlementAmount").setDisabled(true);
				}
			}
	}) 
	
	var win = new Ext.Window({
			title : '付款确认',
			width : 650,
			height:450,
			closeAction : 'hide',
			bodyStyle: 'padding:0px 1px 0px 0px;',
			plain : true,
			modal : true,
			items: [{
			            xtype: 'panel',
			            bodyStyle: 'border-bottom:0px;',
			            height : 29,
			            tbar: [{
			                text: '确认付款',
			                iconCls : 'save',
			                handler:function(){
			                	//searchCash(receivingForm);
			                	var selectIds="";//选中行ID
			                	var penyJenis=receivingForm.getForm().getValues()["penyJenis"]; //收款方式
			                	var thesettlementAmount=Ext.getCmp("thesettlementAmount").getValue();
			                	if(penyJenis==undefined){
			                		Ext.Msg.alert("系统消息", "请选择付款方式！");
									return false;
			                	}
			                	if(thesettlementAmount<=0){
			                		Ext.Msg.alert("系统消息", "付款金额不能小于零！");
									return false;
			                	}
			                	
			                	var accountNumId;
			                	if (penyJenis=="现金"||penyJenis=="银行"||penyJenis=="POS"){//现金
			                		var cashGrid = Ext.getCmp('paymentGrid');
									var _records = cashGrid.getSelectionModel().getSelections();// 获取所有选中行
									if (_records.length < 1) {
										Ext.Msg.alert("系统消息", "请选择付款账号！");
										return false;
									} else if (_records.length > 1) {
										Ext.Msg.alert("系统消息", "一次只能选择 一个付款账号！");
										return false;
									}
									selectIds=_records[0].data.id;//银行账号(现金、银行、POS付款账号)
			                	}

								Ext.Msg.confirm(alertTitle,'本次付款金额为：<font color="#FF0000">'+Ext.getCmp("thesettlementAmount").getValue()+'</font>元,您确定要付款吗?',function(btnYes) {
									if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
										if (receivingForm.getForm().isValid()) {
											this.disabled = true;// 只能点击一次
											receivingForm.getForm().submit({
												url : sysPath + '/' + savePaymentUrl,
												params : {
													ids : ids,
													settlementAmount:thesettlementAmount,
													documentsSmalltype:documentsSmalltype,//单据小类
													selectIds:selectIds,
													privilege : privilege
												},
												waitMsg : '正在保存数据...',
												success : function(form, action) {
														Ext.Msg.alert("友情提示",
															action.result.msg, function() {
																win.close();
																maindateStore.load();
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
			            },
			            '-', {
			                text: '关闭',
			                iconCls : 'save',
			                handler : function() {
								win.close();
							}
			            }]
		            },receivingForm]
		});
		win.on('hide', function() {
					receivingForm.destroy();
				});
		win.show();
}


/**
 * 委托收付款
 */
function entrustPayment(){
	//获取选中的行
	var ids="";
	var car = Ext.getCmp('mainGrid');
	var paymentType="";//选中收付款单类型
	var title="";
	var _records = car.getSelectionModel().getSelections();// 获取所有选中行
	var documentsSmalltype="";//单据小类
	var departStore="";
	var departGridSearchUrl = "sys/departAction!findAll.action";
	
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择应收单号！");
		return false;
	}
	for(var i=0;i<_records.length;i++){
		ids=ids+_records[i].data.id+',';
		if (i>0&&_records[i].data.paymentType!=paymentType){
			Ext.Msg.alert("系统消息","收款单与付款单不能同时委托，请重新选择！");
			return false;
		}
		
		if(_records[i].data.paymentStatus==2){
			var alt="付收单["+_records[i].data.id+"]已收款，不能重复收款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		
		if(_records[i].data.paymentStatus==5){
			var alt="付收单["+_records[i].data.id+"]已付款，不能重复付款!";
			Ext.Msg.alert("系统消息", alt);
			return false;
		}
		
		paymentType=_records[i].data.paymentType;
		documentsSmalltype=_records[i].data.documentsSmalltype;
	}
	
	// 部门列表
	departStore = new Ext.data.Store({
				storeId : "departStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + departGridSearchUrl
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'departName',
									mapping : 'departName'
								}, {
									name : 'deptId',
									mapping : 'departId'
								}])
			});
					
					
	if (paymentType==2){
		title="委托付款";
		
	}else{
		title="委托收款";
		//SelectUrl=searchReceivingUrl;
	}
	SelectUrl=searchPaymentUrl;
				
	 	//付款主Form
	    var receivingForm = new Ext.FormPanel({
        frame: true,
        labelWidth: 40,
        labelAlign:'right',
        width: 400,
            items: [{
            	layout:'column',
            	labelWidth: 120,
            	items:[{
	            		layout:'form',
	            		//columnWidth:.30,
            			items:[{
							xtype : 'hidden',
							id : 'entrustDeptid',
							name : 'entrustDeptid'
						},{
							xtype : 'hidden',
							id : 'entrustUser',
							name : 'entrustUser',
							value:userName
						},{
            				xtype:'textfield',
            				fieldLabel:'结算总金额',
            				anchor : '99%',
            				id:'Amount',
            				name:'Amount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'已结算金额',
            				anchor : '99%',
            				id:'settlementAmount',
            				name:'settlementAmount',
            				disabled:true
            			},{
            				xtype:'textfield',
            				fieldLabel:'未结算金额',
            				anchor : '99%',
            				id:"unsettlementAmount",
            				name:'unsettlementAmount',
            				disabled:true
            			}/*,{
            				xtype:'textfield',
            				fieldLabel:'委托总金额<span style="color:red">*</span>',
            				anchor : '99%',
							allowBlank : false,
							typeAhead : false,
            				id:"thesettlementAmount",
            				name:'thesettlementAmount'
            			}, {
							xtype : 'combo',
							fieldLabel : '委托部门<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : departStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_departName',
							displayField : 'departName',
							name : 'entrustDept',
							blankText : "所属部门为空！",
							anchor : '98%',
							emptyText : "请选择所属部门",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('entrustDeptid').setValue(record
											.get("deptId"));
								}
							}
						}*/,{
							xtype : 'combo',
							typeAhead:true,
							selectOnFocus:true,
							//hiddenId : 'dictionaryName',
			    			//hiddenName : 'dictionaryName',
							triggerAction : 'all',
							store : bussStore,
							width:80,
							listWidth:245,
							minChars : 1,
							allowBlank : true,
							queryParam : 'filter_LIKES_departName',
							emptyText : "请选择部门名称",
							forceSelection : true,
							blankText : "所属部门为空！",
							fieldLabel:'委托部门<span style="color:red">*</span>',
							editable : true,
							anchor : '98%',
							pageSize : comboxPage,
							displayField : 'departName',//显示值，与fields对应
							//valueField : 'departId',//value值，与fields对应
							name : 'entrustDept',
							enableKeyEvents:true,
				    		listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('entrustDeptid').setValue(record
											.get("departId"));
								}
				    		}
					    },{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				anchor : '99%',
            				id:"entrustRemark",
            				name:'entrustRemark'
            			}]
            		}]
                
            }]
     });
     
    
	Ext.Ajax.request({
		url : sysPath + "/" +  SelectUrl,
		params : {
			ids : ids,
			documentsSmalltype:documentsSmalltype,//单据小类
			privilege : privilege
		},success : function(res) {
				var resText = Ext.util.JSON.decode(res.responseText);
				var Amount=0;//结算总金额
				var settlementAmount=0;//已结算总金额
				var unsettlementAmount=0;//未结算总金额
				
				for(var i=0;i<resText.resultMap.length;i++){
					Amount=Amount+resText.resultMap[i].AMOUNT;
					settlementAmount=settlementAmount+resText.resultMap[i].SETTLEMENT_AMOUNT;
				}
				unsettlementAmount=Amount-settlementAmount;
				Ext.getCmp("Amount").setValue(Amount);
				Ext.getCmp("settlementAmount").setValue(settlementAmount);
				Ext.getCmp("unsettlementAmount").setValue(unsettlementAmount);
			}
	}) 
	
	var win = new Ext.Window({
			title : title+'确认',
			width : 400,
			height:240,
			closeAction : 'hide',
			bodyStyle: 'padding:0px 1px 0px 0px;',
			plain : true,
			modal : true,
			items: [{
			            xtype: 'panel',
			            bodyStyle: 'border-bottom:0px;',
			            height : 29,
			            tbar: [{
			                text: "委托确认",
			                iconCls : 'save',
			                handler:function(){
			                	/*var thesettlementAmount=Ext.getCmp("thesettlementAmount").getValue();
			                	if(thesettlementAmount<=0){
			                		Ext.Msg.alert("系统消息", "委托金额不能小于零！");
									return false;
			                	}*/
			                	
								if (receivingForm.getForm().isValid()) {
									this.disabled = true;// 只能点击一次
									receivingForm.getForm().submit({
										url : sysPath + '/' + saveEntrustUrl,
										params : {
											ids : ids,
											privilege : privilege,
											documentsSmalltype:documentsSmalltype
										},
										waitMsg : '正在保存数据...',
										success : function(form, action) {
												Ext.Msg.alert("友情提示",
													action.result.msg, function() {
														win.close();
														maindateStore.load();
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
																//dataReload();
															});
				
												}
											}
										}
									});
								}
			                	
			                }
			            },
			            '-', {
			                text: '关闭',
			                iconCls : 'save',
			                handler : function() {
								win.close();
							}
			            }]
		            },receivingForm]
		});
		win.on('hide', function() {
					receivingForm.destroy();
				});
		win.show();
}

/**
 * 异常到付款登记
 */
function exceptionAdd(){
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择单号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	if(_records[0].data.paymentType!=1){
		Ext.Msg.alert("系统消息", "只有收款单才允许登记！");
		return false;
	}
	if(_records[0].data.documentsSmalltype!="配送单"){
		Ext.Msg.alert("系统消息", "只有单据小类等于[配送单]才允许登记！");
		return false;
	}
	if(_records[0].data.paymentStatus!=1&&_records[0].data.paymentStatus!=3){
		Ext.Msg.alert("系统消息", "只有未收款的单据才可以登记！");
		return false;
	}
	
	var gridSearchUrl = "fi/fiPaymentAcion!ralaList.action";
	var saveUrl = "fi/fiPaymentabnormalAction!saveException.action"; //保存问题账款
	var privilege=117;
	
	//异常类型
	 var problemTypeStore = new Ext.data.Store({
					storeId : "problemTypeStore",
					proxy : new Ext.data.HttpProxy({
								url : sysPath
										+ "/sys/dictionaryAction!ralaList.action"
							}),
					baseParams : {
						filter_EQL_basDictionaryId : 162,
						privilege : 16
					},
					reader : new Ext.data.JsonReader({
									root : 'result',
									totalProperty : 'totalCount'
								}, [{
										name : 'typeid',
										mapping : 'id'
									}, {
										name : 'type',
										mapping : 'typeName'
									}])
				});
	//problemTypeStore.load();
		
		
	var fields = [{
			name : "fiPaymentId",
			mapping : 'id'
		}, {
			name : "paymentType", // 收付类型(收款单/付款单)
			mapping : 'paymentType'
		}, {
			name : 'costType',// 费用类型:代收货款/到付提送费/到付增值费/预付提送费/预付增值费/其它收入/对账
			mapping : 'costType'
		}, {
			name : 'documentsType',// 单据大类:收入\成本\对账\预存款\代收货款
			mapping : 'documentsType'
		}, {
			name : 'documentsSmalltype',// 单据小类：配送单/对账单/配送单/预存款单
			mapping : 'documentsSmalltype'
		}, {
			name : 'documentsNo',// 单据小类对应的单号
			mapping : 'documentsNo'
		}, {
			name : 'penyType',//结算类型(现结、月结)
			mapping : 'penyType'
		}, {
			name : 'contacts',// 往来单位:没在客商档案中的客商，内部客户。
			mapping : 'contacts'
		}, {
			name : 'customerId',//客商id
			mapping : 'customerId'
		}, {
			name : 'customerName',// 客商表名称
			mapping : 'customerName'
		}, {
			name : 'sourceData',// 数据来源
			mapping : 'sourceData'
		}, {
			name : 'sourceNo',// 来源单号
			mapping : 'sourceNo'
		}, {
			name : 'type',// 异常类型
			mapping : 'type'
		}, {
			name : 'paymentAmount',//异常金额
			mapping : 'amount'
		}, {
			name : 'remark',// 备注
			mapping : 'remark'
		}, {
			name : 'status',// 处理状态(状态(0:作废,1:未处理,2:已处理)
			mapping : 'status'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'createDept',// 部门名称
			mapping : 'createDept'
		}, {
			name : 'createDeptId',// 部门名称id
			mapping : 'createDeptId'
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
		
		var form = new Ext.form.FormPanel({
				labelAlign : 'left',
				frame : true,
				labelWidth : 100,
				width : 500,
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
													fieldLabel : 'fiPaymentId',
													name : 'fiPaymentId'
												}, {
													name : "ts",
													xtype : "hidden"
												}, {
													xtype : 'textfield',
													fieldLabel : '收付类型',
													name : 'paymentType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '费用类型',
													name : 'costType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '单据大类',
													name : 'documentsType',
													disabled : true, 
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '单据小类',
													disabled : true, 
													name : 'documentsSmalltype',
													anchor : '95%'
												}, {
													xtype : 'textfield',
													fieldLabel : '客商名称',
													disabled : true, 
													name : 'customerName',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '往来单位',
													disabled : true, 
													name : 'contacts',
													anchor : '95%'
												}]

									},{
										columnWidth : .5,
										layout : 'form',
										items : [ {
													xtype : 'textfield',
													fieldLabel : '数据来源',
													disabled : true, 
													name : 'sourceData',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '来源单号',
													disabled : true, 
													name : 'sourceNo',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '应收付金额',
													disabled : true, 
													id:'paymentAmount',
													name : 'paymentAmount',
													anchor : '95%'
												},{
													xtype : 'combo',
													typeAhead : true,
													forceSelection : true,
													queryParam : 'filter_LIKES_typeName',
													minChars : 0,
													fieldLabel : '异常类型<span style="color:red">*</span>',
													store : problemTypeStore,
													triggerAction : 'all',
													valueField : 'typeid',
													displayField : 'type',
													hiddenName : 'typeid',
													emptyText : "请选择异常类型",
													allowBlank : false,
													blankText : "异常类型不能为空！",
													anchor : '95%'
												},{
													xtype : 'numberfield',
													fieldLabel : '异常金额',
													id:'amount',
													name : 'amount',
													anchor : '95%'
												},{
													xtype : 'textfield',
													fieldLabel : '备注',
													name : 'remark',
													anchor : '95%'
												}]

									}]
						}]
			});
	
	
	form.load({
			url : sysPath + "/" + gridSearchUrl,
			params : {
				filter_EQL_id :_records[0].id,
				departId1 : departId,
				privilege : privilege
			}
		});	
					
	var win = new Ext.Window({
				title : '异常到付款登记',
				width : 517,
				//height:350,
				closeAction : 'hide',
				plain : true,
				modal : true,
				items : form,
				buttonAlign : "center",
				buttons : [{
			text : "保存",
			iconCls : 'groupSave',
			handler : function() {
				if(Ext.getCmp("amount").getValue()>Ext.getCmp("paymentAmount").getValue()){
					Ext.Msg.alert("保存失败", "异常金额不能大于应收金额！");
					return;
				}
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
										maindateStore.load();
									});
						},
						failure : function(form, action) {
							if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
								Ext.Msg.alert("友情提示", "服务器连接异常，请联系管理员！");
							} else {
								if (action.result.msg) {
									win.hide();
									Ext.Msg.alert("友情提示", action.result.msg);

								}
							}
						}
					});
				}
			}
		},{
			text : "关闭",
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

/**
 * 异常到付款记录
 */
function exceptionList(){
	Ext.Msg.alert("提示","异常到付款记录...");
}

/**
 * 收付款记录
 */
function paidList(){
	var gridPaidUrl="fi/fiPaidAction!ralaList.action";
	var paidPrivilege = 118;//实收实付
	
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择对账单号！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	
	var rowNum = new Ext.grid.RowNumberer({
				header : '序号',
				width : 25,
				sortable : true
			});
		
	var sm = new Ext.grid.CheckboxSelectionModel();


var fields = [{
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

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
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

	var tbar = [{
			xtype : "combo",
			width : 100,
			triggerAction : 'all',
			model : 'local',
			hiddenId : 'checkItems',
			hiddenName : 'checkItems',
			name : 'checkItemstext',
			store : [['', '查询全部'],
					// ['EQS_id', 'ID'],
					['EQS_id', '应收单号'], ['LIKES_costType', '费用类型']],
			emptyText : '选择查询类型',
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
			emptyText :'查询数据',
			blankText : '查询数据',
			id : 'searchContent'
		}, '-', {
			text : '<b>搜索</b>',
			id : 'btn4',
			iconCls : 'btnSearch',
			handler : function(){
				dateStore.load();
			}
			}];

	// 查询主列表
	dateStore = new Ext.data.Store({
		storeId : "dateStore",
		baseParams : {
					privilege : paidPrivilege,
					filter_EQL_fiPaymentId:_records[0].id
				},
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + gridPaidUrl,
					method:'POST'
				}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, fields)
		});

	var Grid = new Ext.grid.GridPanel({
				region : "center",
				id : 'detailCenter',
				height : 330,
				width : 800,
				viewConfig : {
					columnsText : "显示的列",
					sortAscText : "升序",
					sortDescText : "降序",
					scrollOffset: 0,
					//forceFit : true,
					autoScroll:true
				},
				autoScroll : true,
				//autoExpandColumn : 1,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				//tbar : tbar,
				ds : dateStore,
				bbar : new Ext.PagingToolbar({
							pageSize : pageSize,
							store : dateStore,
							displayInfo : true,
							displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
							emptyMsg : "没有记录信息显示"
						})
			});
	
	//Grid渲染后请求数据。
	Grid.on('render',function(){
			dateStore.load({params : {
				start : 0,
				limit : pageSize,
				filter_EQL_fiPaymentId:_records[0].id,
				privilege:paidPrivilege
				}
			});
		});
	
	var win = new Ext.Window({
			title : '查看收付款明细',
			width : 815,
			height:400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : Grid,
			buttonAlign : "center",
			buttons : [{
				text : "关闭",
				handler : function() {
					win.close();
				}
			}]
		});
		win.on('hide', function() {
					Grid.destroy();
				});
		win.show();
}

/**
 * 录入其他应收单
 */
function receivingAdd(){

	//获取选中的行
	var ids="";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var car = Ext.getCmp('mainGrid');
	
	// 其它收入类型
	documentsSmalltypeStore = new Ext.data.Store({
				storeId : "documentsSmalltypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 142,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'documentsSmalltypeName',
								mapping : 'typeName'
							}])
			});
			
	// 结算方式
	penyTypeStore = new Ext.data.Store({
				storeId : "penyTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 82,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'penyTypeName',
								mapping : 'typeName'
							}])
			});
					
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
	
				
	 	//付款主Form
	    var receivingForm = new Ext.FormPanel({
        frame: true,
        labelWidth: 40,
        labelAlign:'right',
        width: 400,
            items: [{
            	layout:'column',
            	labelWidth: 120,
            	items:[{
	            		layout:'form',
	            		//columnWidth:.30,
            			items:[{
							xtype : 'hidden',
							id : 'paymentType',
							name : 'paymentType',
							value:'1'
						},{
							xtype : 'hidden',
							id : 'documentsType',
							name : 'documentsType',
							value:'其它收入'
						},{
							xtype : 'hidden',
							id : 'paymentStatus',
							name : 'paymentStatus',
							value:'1'//收付状态
						},{
							xtype : 'hidden',
							id : 'sourceData',
							name : 'sourceData',
							value:'手工录入'//数据来源
						},{
							xtype : 'hidden',
							id : 'costType',
							name : 'costType'//费用类型
						},{
							xtype : 'hidden',
							id : 'documentsSmalltype',
							name : 'documentsSmalltype'
						},{
							xtype : 'hidden',
							id : 'penyType',
							name : 'penyType'
						},{
							xtype : 'hidden',
							id : 'customerId',
							name : 'customerId'
						},{
							xtype : 'hidden',
							id : 'settlementAmount',
							name : 'settlementAmount',//已付金额
							value:0
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '收入类型<span style="color:red">*</span>',
							store : documentsSmalltypeStore,
							triggerAction : 'all',
							valueField : 'documentsSmalltypeName',
							displayField : 'documentsSmalltypeName',
							hiddenName : 'documentsSmalltypeName',
							emptyText : "请选择收入类型",
							allowBlank : false,
							blankText : "收入类型不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('documentsSmalltype').setValue(record
											.get("documentsSmalltypeName"));
									Ext.getCmp('costType').setValue(record
											.get("documentsSmalltypeName"));
								}
							}
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '结算方式<span style="color:red">*</span>',
							store : penyTypeStore,
							triggerAction : 'all',
							valueField : 'penyTypeName',
							displayField : 'penyTypeName',
							hiddenName : 'penyTypeName',
							emptyText : "请选择结算方式",
							allowBlank : false,
							blankText : "结算方式不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('penyType').setValue(record
											.get("penyTypeName"));
								}
							}
						}, {
							xtype : 'combo',
							fieldLabel : '客商名称',
							//allowBlank : false,
							//typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_customerName',
							displayField : 'customerName',
							name : 'customerName',
							blankText : "客商名称不能为空！",
							anchor : '98%',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('customerId').setValue(record
											.get("customerId"));
								}
							}
						},{
            				xtype:'numberfield',
            				fieldLabel:'收入金额<span style="color:red">*</span>',
            				allowBlank : false,
            				blankText : "收入金额不能为空！",
            				emptyText : "请输入收入金额",
            				anchor : '98%',
            				id:"Amount",
            				name:'Amount'
            			},{
            				xtype:'numberfield',
            				fieldLabel:'单据号',
            				anchor : '98%',
            				id:"documentsNo",
            				name:'documentsNo'
            			},{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				anchor : '98%',
            				id:"remark",
            				name:'remark'
            			},{
            				xtype:'hidden',
            				name:'departId',
            				id:'departId',
            				value:bussDepart
            			},{
            				xtype:'hidden',
            				name:'departName',
            				id:'departName',
            				value:bussDepartName
            			}]
            		}]
                
            }]
     });

	
	var win = new Ext.Window({
			title : '录入其它应收单',
			width : 400,
			height:240,
			closeAction : 'hide',
			bodyStyle: 'padding:0px 1px 0px 0px;',
			plain : true,
			modal : true,
			items: [{
			            xtype: 'panel',
			            bodyStyle: 'border-bottom:0px;',
			            height : 29,
			            tbar: [{
			                text: "确认",
			                iconCls : 'save',
			                handler:function(){
								if (receivingForm.getForm().isValid()) {
									this.disabled = true;// 只能点击一次
									receivingForm.getForm().submit({
										url : sysPath + '/' + saveUrl,
										params : {
											ids : ids,
											privilege : privilege
										},
										waitMsg : '正在保存数据...',
										success : function(form, action) {
												Ext.Msg.alert("友情提示",
													action.result.msg, function() {
														win.close();
														maindateStore.load();
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
																//dataReload();
															});
				
												}
											}
										}
									});
								}
			                	
			                }
			            },
			            '-', {
			                text: '关闭',
			                iconCls : 'save',
			                handler : function() {
								win.close();
							}
			            }]
		            },receivingForm]
		});
		win.on('hide', function() {
					receivingForm.destroy();
				});
		win.show();
}

/**
 * 录入其他应付单
 */
function paymentAdd(){

	//获取选中的行
	var ids="";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var car = Ext.getCmp('mainGrid');
	
	// 其它收入类型
	documentsSmalltypeStore = new Ext.data.Store({
				storeId : "documentsSmalltypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 142,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'documentsSmalltypeName',
								mapping : 'typeName'
							}])
			});
			
	// 结算方式
	penyTypeStore = new Ext.data.Store({
				storeId : "penyTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 82,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
								name : 'id',
								mapping : 'id'
							}, {
								name : 'penyTypeName',
								mapping : 'typeName'
							}])
			});
					
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
	
				
	 	//付款主Form
	    var receivingForm = new Ext.FormPanel({
        frame: true,
        labelWidth: 40,
        labelAlign:'right',
        width: 400,
            items: [{
            	layout:'column',
            	labelWidth: 120,
            	items:[{
	            		layout:'form',
	            		//columnWidth:.30,
            			items:[{
							xtype : 'hidden',
							id : 'paymentType',
							name : 'paymentType',
							value:'2'
						},{
							xtype:'hidden',
							id:'departId',
							name:'departId',
							value:bussDepart
						},{
							xtype:'hidden',
							id:'departName',
							name:'departName',
							value:bussDepartName
						},{
							xtype : 'hidden',
							id : 'documentsType',
							name : 'documentsType',
							value:'其它支出'
						},{
							xtype : 'hidden',
							id : 'reviewStatus',
							value:'0',
							name : 'reviewStatus'//审核状态
						},{
							xtype : 'hidden',
							id : 'paymentStatus',
							name : 'paymentStatus',
							value:'4'//收付状态
						},{
							xtype : 'hidden',
							id : 'sourceData',
							name : 'sourceData',
							value:'手工录入'//数据来源
						},{
							xtype : 'hidden',
							id : 'costType',
							name : 'costType'//费用类型
						},{
							xtype : 'hidden',
							id : 'documentsSmalltype',
							name : 'documentsSmalltype'
						},{
							xtype : 'hidden',
							id : 'penyType',
							name : 'penyType'
						},{
							xtype : 'hidden',
							id : 'customerId',
							name : 'customerId'
						},{
							xtype : 'hidden',
							id : 'settlementAmount',
							name : 'settlementAmount',//已付金额
							value:0
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '支付类型<span style="color:red">*</span>',
							store : documentsSmalltypeStore,
							triggerAction : 'all',
							valueField : 'documentsSmalltypeName',
							displayField : 'documentsSmalltypeName',
							hiddenName : 'documentsSmalltypeName',
							emptyText : "请选择支付类型",
							allowBlank : false,
							blankText : "支付类型不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('documentsSmalltype').setValue(record
											.get("documentsSmalltypeName"));
									Ext.getCmp('costType').setValue(record
											.get("documentsSmalltypeName"));
								}
							}
						},{
							xtype : 'combo',
							typeAhead : true,
							forceSelection : true,
							queryParam : 'filter_LIKES_typeName',
							minChars : 0,
							fieldLabel : '结算方式<span style="color:red">*</span>',
							store : penyTypeStore,
							triggerAction : 'all',
							valueField : 'penyTypeName',
							displayField : 'penyTypeName',
							hiddenName : 'penyTypeName',
							emptyText : "请选择结算方式",
							allowBlank : false,
							blankText : "结算方式不能为空！",
							anchor : '98%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('penyType').setValue(record
											.get("penyTypeName"));
								}
							}
						}, {
							xtype : 'combo',
							fieldLabel : '客商名称',
							//allowBlank : false,
							//typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_customerName',
							displayField : 'customerName',
							name : 'customerName',
							blankText : "客商名称不能为空！",
							anchor : '98%',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('customerId').setValue(record
											.get("customerId"));
								}
							}
						},{
            				xtype:'textfield',
            				fieldLabel:'往来单位',
            				anchor : '98%',
            				id:"contacts",
            				name:'contacts'
            			},{
            				xtype:'numberfield',
            				fieldLabel:'付款金额<span style="color:red">*</span>',
            				allowBlank : false,
            				blankText : "付款金额不能为空！",
            				emptyText : "请输入付款金额",
            				anchor : '98%',
            				id:"Amount",
            				name:'Amount'
            			},{
            				xtype:'numberfield',
            				fieldLabel:'单据号',
            				anchor : '98%',
            				id:"documentsNo",
            				name:'documentsNo'
            			},{
            				xtype:'textfield',
            				fieldLabel:'备注',
            				anchor : '98%',
            				id:"remark",
            				name:'remark'
            			}]
            		}]
                
            }]
     });

	
	var win = new Ext.Window({
			title : '录入其它应付单',
			width : 400,
			height:260,
			closeAction : 'hide',
			bodyStyle: 'padding:0px 1px 0px 0px;',
			plain : true,
			modal : true,
			items: [{
			            xtype: 'panel',
			            bodyStyle: 'border-bottom:0px;',
			            height : 29,
			            tbar: [{
			                text: "确认",
			                iconCls : 'save',
			                handler:function(){
								if (receivingForm.getForm().isValid()) {
									this.disabled = true;// 只能点击一次
									receivingForm.getForm().submit({
										url : sysPath + '/' + saveUrl,
										params : {
											ids : ids,
											privilege : privilege
										},
										waitMsg : '正在保存数据...',
										success : function(form, action) {
												Ext.Msg.alert("友情提示",
													action.result.msg, function() {
														win.close();
														maindateStore.load();
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
																//dataReload();
															});
				
												}
											}
										}
									});
								}
			                	
			                }
			            },
			            '-', {
			                text: '关闭',
			                iconCls : 'save',
			                handler : function() {
								win.close();
							}
			            }]
		            },receivingForm]
		});
		win.on('hide', function() {
					receivingForm.destroy();
				});
		win.show();
}

	function outExcel(){
		  parent.exportExl(Ext.getCmp('mainGrid'));
	}


/**
 * 挂账
 */
function losses(){
	var mainConter = Ext.getCmp('mainGrid');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择收款单！");
		return false;
	} else if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能选择一行！");
		return false;
	}
	if(_records[0].data.paymentStatus!=1){
		Ext.Msg.alert("系统消息", "挂账失败,只有未收款单据才允许挂账！");
		return false;
	}
	if(_records[0].data.documentsSmalltype!='配送单'){
		Ext.Msg.alert("系统消息", "挂账失败,只有配送单才允许挂账！");
		return false;
	}
	var ids=_records[0].data.id;
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
						},{
							xtype : 'hidden',
							id : 'departId',
							name : 'departId'
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
						fieldLabel:'客商部门',
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
												Ext.getCmp('departId').setValue(departStore.getAt(i).get("departId"));
												
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
							typeAhead : false,
							fieldLabel : '挂账客商<span style="color:red">*</span>',
							forceSelection : true,
							anchor:'98%',
							allowBlank : false,
							id:'customerId2',
							listWidth:245,
							minChars : 1,
							triggerAction : 'all',
							store : fiArrearsetStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'cusName',
							valueField : 'customerId',
							name : 'cusName',
							hiddenName:'customerId',
							emptyText : "请选择客商名称",
							listeners : {
								'focus' : function(combo, record, index) {
						   				Ext.apply(fiArrearsetStore.baseParams, {
											filter_EQS_departId : Ext.getCmp('departId').getValue()
									});
									fiArrearsetStore.load();
									}
								}
						},{
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 500,
							anchor:'98%'
						}]

			}]
		}]
	});

	var win = new Ext.Window({
		title : '挂账',
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
						url : sysPath + '/' + saveLossesUrl,
						params : {
							ids:ids,
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
					form.getForm().reset();
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

/*
var bodymap = new Ext.KeyMap(Ext.getBody(), {
     key: 113, 
     fn: function(){
		addcolumn();
     }
 });*/
 function addcolumn(){
 	var recd;
 	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID
	var form = new Ext.form.FormPanel({
			labelAlign : 'left',
			frame : true,
			width : 400,
			bodyStyle : 'padding:5px 5px 0',
			reader : new Ext.data.JsonReader({
            root: 'result', totalProperty: 'totalCount'
        }, fields),
		labelAlign : "right",
				items : [{
							layout : 'column',
							items : [{
							columnWidth : .95,
							layout : 'form',
								items : [{
											xtype : 'combo',
											typeAhead : true,
											forceSelection : true,
											queryParam : 'filter_LIKES_typeName',
											minChars : 0,
											fieldLabel : '单据小类',
											store : documentsSmalltypeStore,
											triggerAction : 'all',
											id : 'combodocumentsSmalltype1',
											valueField : 'documentsSmalltype',
											displayField : 'documentsSmalltype',
											emptyText : "请选择",
											width : 80,
											anchor : '95%',
											listeners:{
												render:function(textField, e){
													textField.setValue('配送单');
												}
											}
										},{
											xtype : 'numberfield',
											id : 'fielddocumentsNo1',
											fieldLabel : '单据号',
											anchor : '95%',
											selectOnFocus:true,
											enableKeyEvents:true,
											listeners : {
												keypress:function(textField, e){
													if(e.getKey() == 13){
														doSearch();
														//form.getForm().submit();
													}
												}
											}
										},{
											xtype : 'combo',
											typeAhead : true,
											forceSelection : true,
											queryParam : 'filter_LIKES_typeName',
											minChars : 0,
											fieldLabel : '数据来源<font style="color:red;">*</font>',
											store : sourceDataStore,
											triggerAction : 'all',
											id : 'combosourceDatafield',
											valueField : 'sourceData',
											displayField : 'sourceData',
											emptyText : "请选择",
											width : 80,
											anchor : '95%'
										},{
											xtype : 'numberfield',
											labelAlign : 'left',
											id:'sourceNofield',
											fieldLabel : '来源单号<font style="color:red;">*</font>',
											name : 'dno',
											anchor : '95%',
											enableKeyEvents:true
									       }]
								}]
							}]
						});
						
		var win = new Ext.Window({
		title : '单票添加',
		width : 400,
		closeAction : 'hide',
		plain : true,
		modal : true,
		items : form,
		buttonAlign : "center",

		buttons : [{
			text : "确定",
			iconCls : 'groupSave',
			id:'reportBtn',
			handler : function() {
				//if (form.getForm().isValid()) {
					doSearch();
					/* dateStore.insert(0,recd);
					 form.getForm().reset(function(){
						 Ext.getCmp('singleDno').focus();
					 });*/
				//}
			}
		},  {
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

	setTimeout(function(){
		Ext.getCmp('fielddocumentsNo1').focus();	
	},500);
}
function doSearch(){
	var departId=Ext.getCmp("combodepartId").getValue(); // 业务部门ID	
	Ext.Ajax.request({//sourceData="+Ext.getCmp('sourceNofield').getValue()+"&
		url : sysPath+'/'+ maingridSearchUrl,
		params : {
			filter_LIKES_sourceData:Ext.getCmp('combosourceDatafield').getValue(),
			filter_EQS_sourceNo : Ext.getCmp('sourceNofield').getValue(),
			filter_EQS_documentsSmalltype : Ext.getCmp("combodocumentsSmalltype1").getValue(),
			filter_EQS_documentsNo:Ext.getCmp("fielddocumentsNo1").getValue(),
			departId1:departId,
			privilege : privilege
		},
		success : function(resp) {
			aa(resp);
			Ext.getCmp('fielddocumentsNo1').focus();
			Ext.getCmp('fielddocumentsNo1').selectText();
		}
	});
	
	//全选文本框
}
function aa(resp){
var jdata = Ext.util.JSON.decode(resp.responseText);
							if(null != jdata.success && !jdata.success){
								Ext.Msg.alert(alertTitle,jdata.msg);
							}else if(jdata.result.length==0){
								Ext.Msg.alert("系统消息", "来源单号不存在！",function(){
									Ext.getCmp('fielddocumentsNo1').focus();
									Ext.getCmp('fielddocumentsNo1').selectText();
								});
								return false;
							}else {
								Ext.getCmp('mainGrid').getSelectionModel().clearSelections();  //清空所有选择记录
								for (var i = 0; i < jdata.result.length; i++) {
								
								//获取选中的行
								/*var car = Ext.getCmp('mainGrid');
								var _records = car.getSelectionModel().getSelections();// 获取所有选中行
								alert(_records.length);
									for(var j=0;j<_records.length;j++){
										if(jdata.result[i].id==_records[j].id){
											Ext.Msg.alert("系统消息", "此数据已经存在，不能重复添加！");
											return false;
										}
									}*/
									var isbug=0;
									recd=(new Ext.data.Record({
										id:jdata.result[i].id,
										paymentType:jdata.result[i].paymentType,
										paymentStatus:jdata.result[i].paymentStatus,
										costType:jdata.result[i].costType,
										documentsType:jdata.result[i].documentsType,
										documentsSmalltype:jdata.result[i].documentsSmalltype,
										documentsNo:jdata.result[i].documentsNo,
										penyType:jdata.result[i].penyType,
										amount:jdata.result[i].amount,
										settlementAmount:jdata.result[i].settlementAmount,
										abnormalAmount:jdata.result[i].abnormalAmount,
										eliminationAmount:jdata.result[i].eliminationAmount,
										workflowNo:jdata.result[i].workflowNo,
										contacts:jdata.result[i].contacts,
										customerId:jdata.result[i].customerId,
										customerName:jdata.result[i].customerName,
										sourceData:jdata.result[i].sourceData,
										sourceNo:jdata.result[i].sourceNo,
										collectionUser:jdata.result[i].collectionUser,
										paymentMark:jdata.result[i].paymentMark,
										entrustDept:jdata.result[i].entrustDept,
										entrustUser:jdata.result[i].entrustUser,
										entrustTime:jdata.result[i].entrustTime,
										entrustRemark:jdata.result[i].entrustRemark,
										entrustDeptid:jdata.result[i].entrustDeptid,
										departName:jdata.result[i].departName,
										departId:jdata.result[i].departId,
										createRemark:jdata.result[i].createRemark,
										createName:jdata.result[i].createName,
										createTime:jdata.result[i].createTime,
										updateName:jdata.result[i].updateName,
										updateTime:jdata.result[i].updateTime,
										ts:jdata.result[i].ts
									}));
									if(maindateStore.data.length>0){
										for(var j=0;j<maindateStore.data.length;j++){
											if(maindateStore.getAt(j).get('id')==jdata.result[i].id){
												isbug=1;
											}
										}
										if(isbug==0){
											maindateStore.insert(0,recd);
											Ext.getCmp('mainGrid').getSelectionModel().selectRow(0,true);
										}else{
											isbug=0;
										}
									}else{
										maindateStore.insert(0,recd);
										Ext.getCmp('mainGrid').getSelectionModel().selectRow(0,true);
									}//maindateStore.getAt(0).get('id')+"="+
									/*for(var j=0;j<maindateStore.length;j++){
										if(maindateStore.getAt(0).get('id')==jdata.result[i].id){
											alert('1');	
										}
									}*/
									
								}
								//alert(maindateStore.getAt(0).get('id'));
								//alert(jdata.result[0].departName);
							}
}


/**
审核单据
*/
function audit(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要审核的单据！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能审核一张单据！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'单据审核,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + auditUrl,
						params : {
							id : _records[0].data.id,
							audit:'yes',//审核权限控制
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
撤销审核单据
*/
function revocationAudit(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要审核的单据！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能审核一张单据！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'单据审核,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + revocationAuditUrl,
						params : {
							id : _records[0].data.id,
							ts:_records[0].data.ts,
							audit:'no',
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

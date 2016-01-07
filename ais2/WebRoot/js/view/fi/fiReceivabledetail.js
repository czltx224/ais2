
var comboxPage = 15;
var privilege = 70;
var customerGridSearchUrl = "sys/customerAction!list.action";
var departGridSearchUrl = "sys/departAction!findAll.action";
var gridSearchUrl = "fi/fiReceivabledetailAction!ralaList.action";
var saveReceivableStatementUrl = "fi/fiReceivabledetailAction!saveReceivableStatement.action";
var saveUrl = "fi/fiReceivabledetailAction!save.action";
var delUrl = "fi/fiReceivabledetailAction!delete.action";

//手工新增往来明细
var saveReceivabledetailUrl="fi/fiReceivabledetailAction!saveReceivabledetail.action";

//删除往来明细
var invalidUrl="fi/fiReceivabledetailAction!invalid.action";


//手工审核
var auditUrl="fi/fiReceivabledetailAction!audit.action";

//撤销审核
var revocationAuditUrl="fi/fiReceivabledetailAction!revocationAudit.action";


var dateStore, customerStore, departStore, billingCycleStore;
var form1;

var sm = new Ext.grid.CheckboxSelectionModel({
	//	singleSelect :true,
		listeners : {
			selectionchange:function(){
				setTotalCount();
			}
		}
	});

function setTotalCount(){
	var vnetmusicRecord = Ext.getCmp('carCenter').getSelectionModel().getSelections();
	var count_total=0;
	var count_do=0;
	var count_exception=0;
	var count_chong=0;
		
	for(var j=0;j<vnetmusicRecord.length;j++){;
		count_total+=parseFloat(vnetmusicRecord[j].data.amount);
		count_do+=parseFloat(vnetmusicRecord[j].data.verificationAmount);
		count_exception+=parseFloat(vnetmusicRecord[j].data.problemAmount);
		count_chong+=parseFloat(vnetmusicRecord[j].data.eliminationAmount);
 	}

 	Ext.getCmp('count').setValue(vnetmusicRecord.length);
 	Ext.getCmp('count_total').setValue(count_total);
 	Ext.getCmp('count_do').setValue(count_do);
 	Ext.getCmp('count_exception').setValue(count_exception);
 	Ext.getCmp('count_chong').setValue(count_chong);
}


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
			name : 'custprop',// 客商表：客商类型
			mapping : 'custprop'
		}, {
			name : 'reconciliationStatus',// 对账状态
			mapping : 'reconciliationStatus'
		}, {
			name : 'customerName',// 客商名称
			mapping : 'customerName'
		}, {
			name : 'flightmainno',// 航空主单号
			mapping : 'flightmainno'
		}, {
			name : 'collectionStatus',// 状态
			mapping : 'collectionStatus'
		}, {
			name : 'remark',// 状态
			mapping : 'remark'
		}, {
			name : 'sourceData',// 数据来源
			mapping : 'sourceData'
		}, {
			name : 'sourceNo',// 来源单号
			mapping : 'sourceNo'
		}, {
			name : 'dno',// 配送单号
			mapping : 'dno'
		}, {
			name : 'piece',// 件数
			mapping : 'piece'
		}, {
			name : 'cusWeight',// 计费重量
			mapping : 'cusWeight'
		}, {
			name : 'bulk',// 体积
			mapping : 'bulk'
		}, {
			name : 'amount',// 金额
			mapping : 'amount'
		}, {
			name : 'problemAmount',// 问题账款金额
			mapping : 'problemAmount'
		}, {
			name : 'problemStatus',// 问题账款状态
			mapping : 'problemStatus'
		}, {
			name : 'problemRemark',// 问题账款备注
			mapping : 'problemRemark'
		}, {
			name : 'workflowNo',// 流程号
			mapping : 'workflowNo'
		}, {
			name : 'billingCycle',// 客商欠款设置:对账/结算周期
			mapping : 'billingCycle'
		}, {
			name : 'reconciliationUser',// 客商欠款设置:对账员
			mapping : 'reconciliationUser'
		}, {
			name : 'reconciliationNo',// 对账单号
			mapping : 'reconciliationNo'
		}, {
			name : 'costType',// 费用类型
			mapping : 'costType'
		}, {
			name : 'paymentType',// 收付类型
			mapping : 'paymentType'
		}, {
			name : 'verificationStatus',// 核销状态
			mapping : 'verificationStatus'
		}, {
			name : 'verificationAmount',// 核销金额
			mapping : 'verificationAmount'
		}, {
			name : "eliminationAmount", // 冲销金额
			mapping : 'eliminationAmount'
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
		}, {
			name : 'reviewStatus',
			mapping : 'reviewStatus'
		}, {
			name : 'reviewUser',
			mapping : 'reviewUser'
		}, {
			name : 'reviewDate',
			mapping : 'reviewDate'
		}, {
			name : 'reviewRemark',
			mapping : 'reviewRemark'
		}];

		var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : '应收单号',
			dataIndex : 'id',
			width:80,
			sortable : true
		}, {
			header : '客商id',
			dataIndex : 'customerId',
			hidden : true
		}, {
			header : '客商',
			width:100,
			dataIndex : 'customerName'
		}, {
			header : '客商类型',
			width:60,
			dataIndex : 'custprop'
		}, {
			header : '收付类型',
			width:60,
			dataIndex : 'paymentType',
			renderer:function(v,metaData){
				if(v==0){
					v='作废';
				}else if (v==1){
					v='应收';
				}else if (v==2){
					v='应付';
				}
				return v;
			}
		}, {
			header : '对账状态',
			width:60,
			renderer:function(v,metaData){
				if(v==0){
					v='已作废';
				}else if (v==1){
					v='未对账';
				}else if (v==2){
					v='已生成账单';
				}else if (v==3){
					v='账单已审核';
				}else if(v==4){
					v='已收银';
				}
				return v;
			},
			dataIndex : 'reconciliationStatus'
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
			header : '对账单号',
			width:80,
			dataIndex : 'reconciliationNo'
		}, {
			header : '航空主单号',
			width:80,
			dataIndex : 'flightmainno'
		}, {
			header : '费用类型',
			width:60,
			dataIndex : 'costType'
		}, {
			header : '配送单号',
			width:70,
			dataIndex : 'dno'
		}, {
			header : '件数',
			width:40,
			dataIndex : 'piece'
		}, {
			header : '重量',
			width:40,
			dataIndex : 'cusWeight'
		}, {
			header : '体积',
			width:40,
			dataIndex : 'bulk'
		}, {
			header : '金额',
			width:50,
			dataIndex : 'amount'
		}, {
			header : '备注',
			width:100,
			dataIndex : 'remark'
		}, {
			header : '结算周期',
			width:60,
			dataIndex : 'billingCycle'
		}, {
			header : '对账员',
			width:60,
			dataIndex : 'reconciliationUser'
		}, {
			header : '代收货款收银状态',
			width:80,
			dataIndex : 'collectionStatus',
			renderer:function(v,metaData,record){
				var paymentType=record.data['paymentType'];//2付款单
				var costType=record.data['costType'];//费用类型
				if (v==1&&paymentType==2&&costType=='代收货款'){
					v='<font color="#FF0000">未收银</font>';
				}else if (v==2&&paymentType==2&&costType=='代收货款'){
					v='已收银';
				}else{
					v='';
				}
				return v;
			}
		}, {
			header : '核销状态',
			width:80,
			dataIndex : 'verificationStatus',
			renderer:function(v,metaData){
				if(v==1){
					v='未核销';
				}else if (v==2){
					v='部分核销';
				}else if (v==3){
					v='已核销';
				}
				return v;
			}
		}, {
			header : '实收付金额',
			width:80,
			dataIndex : 'verificationAmount'
		}, {
			header : '冲销金额',
			width:80,
			dataIndex : 'eliminationAmount'
		}, {
			header : '问题账款金额',
			width:80,
			dataIndex : 'problemAmount'
		}, {
			header : '流程号',
			width:80,
			dataIndex : 'workflowNo'
		}, {
			header : '数据来源',
			width:80,
			dataIndex : 'sourceData'
		}, {
			header : '来源单号',
			width:80,
			dataIndex : 'sourceNo'
		}, {
			header : '所属部门',
			width:80,
			dataIndex : 'departName'
		}, {
			header : '所属部门ID',
			dataIndex : 'departId',
			hidden : true
		}, {
			header : '创建人',
			dataIndex : 'createName',
			hidden : true
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
			header : '审核人',
			dataIndex : 'reviewUser'
		}, {
			header : '审核时间',
			dataIndex : 'reviewDate',
			hidden : true
		}, {
			header : '审核备注',
			dataIndex : 'reviewRemark'
		}, {
			header : '时间戳',
			dataIndex : 'ts',
			sortable : true,
			hidden : true,
			hideable:false
		}]);

var tbar = [{
			text : '<b>新增</b>',
			iconCls : 'userAdd',
			id : 'basCarAdd',
			tooltip : '新增客商欠款设置',
			handler : function() {
				addReceivebledetail(null);
			}
		}, '-', {
			text : '<b>作废</b>',
			iconCls : 'save',
			tooltip : '作废',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				invalid(_records);
				}	
		}, '-', {
			text : '<b>审核</b>',
			iconCls : 'save',
			tooltip : '审核',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				audit(_records);
				}	
		}, '-', {
			text : '<b>撤销审核</b>',
			iconCls : 'save',
			tooltip : '撤销审核',
			handler : function() {
				var car = Ext.getCmp('carCenter');
				var _records = car.getSelectionModel().getSelections();// 获取所有选中行
				revocationAudit(_records);
				}	
		},'-',{
			text : '<b>生成对账单</b>',
			iconCls : 'save',
			id : 'bassave',
			tooltip : '生成对账单',
			handler : saveReceivableStatement
		}, '-', {
			text:'<b>导出</b>',
			iconCls:'sort_down',
			handler:function(){
                parent.exportExl(Ext.getCmp('carCenter'));
                }
        }/*, '-', {
			text : '<b>打印</b>',
			iconCls : 'table',
			id : 'printbtn',
			tooltip : '打印',
			handler : printinfo
		}*/,'-', {
			text : '<b>搜索</b>',
			id : 'btn',
			iconCls : 'btnSearch',
			handler : searchmain
		}];

Ext.onReady(function() {
	form1 = new Ext.form.FormPanel({
				id : 'form1',
				frame : true,
				width : 100,
				cls : 'displaynone',
				hidden : true,
				items : [],
				buttons : []
			});
	form1.render(document.body);

	// 结算周期
	billingCycleStore = new Ext.data.Store({
				storeId : "billingCycleStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 24,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'billingCycle',
									mapping : 'typeName'
								}])
			});
	
	// 费用类型
	costTypeStore = new Ext.data.Store({
				storeId : "costTypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 222,
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

	// 客商类型
	customertypeStore = new Ext.data.Store({
				storeId : "customertypeStore",
				proxy : new Ext.data.HttpProxy({
							url : sysPath
									+ "/sys/dictionaryAction!ralaList.action"
						}),
				baseParams : {
					filter_EQL_basDictionaryId : 23,
					privilege : 16
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCount'
						}, [{
									name : 'id'
								}, {
									name : 'custprop',
									mapping : 'typeName'
								}])
			});
	customertypeStore.load();


	reconstatusStore = new Ext.data.SimpleStore({
			auteLoad : true, // 此处设置为自动加载
			data : [['1', '未对账'],['2', '已生成账单'], ['3', '账单已审核'], ['4', '已收银'], ['0', '已作废']],
			fields : ["reconciliationStatus", "typeName"]
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
				baseParams : {limit:pageSize,privilege : privilege},
				proxy : new Ext.data.HttpProxy({
							url : sysPath + "/" + gridSearchUrl
							
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
              },'&nbsp;','总金额:',
		    	{xtype : 'numberfield',
                id:'count_total',
   			    readOnly:true,
                width:49,
                 value:0
              },'&nbsp;','实收付总额:',
		    	{xtype : 'numberfield',
   	            id:'count_do',
   			    readOnly:true,
   			    value:0,
                width:49
              },'&nbsp;','冲销总额:',
		    	{xtype : 'numberfield',
   	            id:'count_chong',
   			    readOnly:true,
   			    value:0,
                width:49
              },'&nbsp;','问题账款总额:',
		    	{xtype : 'numberfield',
   	            id:'count_exception',
   			    readOnly:true,
   			    value:0,
                width:49
              }]
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
					autoScroll:true
				},
				autoScroll : true,
				frame : true,
				loadMask : true,
				sm : sm,
				cm : cm,
				ds : dateStore,
				tbar : fourbar,
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
				// title : "供应商往来明细",
				id : 'view',
				el : 'mainGrid',
				labelAlign : 'left',
				height : Ext.lib.Dom.getViewHeight()-2,
				width : Ext.lib.Dom.getViewWidth()-2,
				bodyStyle : 'padding:1px',
				layout : "border",
				tbar : tbar,
				frame : false,
				items : [mainGrid]
			});

	mainpanel.on('render', function() {
		var tbarsearch = new Ext.Toolbar({
			items : ['录入日期', {
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
					}, '-', '客商类型', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : customertypeStore,
						mode : 'local',
						triggerAction : 'all',
						id : 'comboCustprop',
						valueField : 'custprop',
						displayField : 'custprop',
						emptyText : "请选择",
						allowBlank : false,
						blankText : "客商类型不能为空！",
						width : 60,
						editable : true,
						anchor : '95%',
						listeners : {
							'select' : function(combo) {
								 	customerStore.baseParams={
						               filter_EQS_custprop:combo.getValue()
						            }
							        customerStore.load();
							        Ext.getCmp('comboCustomer').setValue('');
							}
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
						width : 100
					}, '-', '对账状态', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : reconstatusStore,
						mode : 'local',
						triggerAction : 'all',
						id : 'comboreconstatusStore',
						valueField : 'reconciliationStatus',
						displayField : 'typeName',
						emptyText : "请选择",
						width : 60,
						anchor : '95%'
					}/* ,'-', '对账员', {
						xtype : 'textfield',
						width : 45,
						id : 'reconciliationUser'
					},'-', '对账单', {
						xtype : 'numberfield',
						width : 45,
						id : 'reconciliationNo'
					}*/]
		});
		
		var twoTbar=new Ext.Panel({
			border: false,
			layout:'column',
			frame:true,
            labelWidth: 80,
	    	items:[{
	        		layout:'form',
	        		columnWidth:.3,
	    			items:[{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			},{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			}]
				},{
	        		layout:'form',
	        		columnWidth:.3,
	    			items:[{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			},{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			}]
				},{
	        		layout:'form',
	        		columnWidth:.4,
	    			items:[{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			},{
	    				xtype:'textfield',fieldLabel:'开始日期',width:80,name:'sonderzugPrice'
	    			}]
				}]
		});
		
		var tbarsearch1 = new Ext.Toolbar({
			items : ['费用类型', {
						xtype : 'combo',
						typeAhead : true,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						store : costTypeStore,
						triggerAction : 'all',
						id : 'combocostType',
						valueField : 'costType',
						displayField : 'costType',
						emptyText : "请选择",
						width : 80,
						anchor : '95%'
					},'代收货款状态', {//
						xtype : "combo",
						width : 70,
						triggerAction : 'all',
						model : 'local',
						id:'combcollectionStatus',
						hiddenId : 'collectionStatus',
						hiddenName : 'collectionStatus',
						name : 'collectionStatustext',
						store : [['1', '未收银'],['2', '已收银']],
						emptyText : '选择类型',
						forceSelection : true
					}, '-', {
						xtype : "combo",
						width : 70,
						triggerAction : 'all',
						model : 'local',
						hiddenId : 'checkItems',
						hiddenName : 'checkItems',
						name : 'checkItemstext',
						store : [['', '查询全部'],['LIKES_reconciliationUser', '对账员'],['LIKES_reconciliationNo', '对账单'],['LIKES_sourceNo', '来源单号'],['LIKES_dno', '配送单号']],
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
						width : 80,
						id : 'searchContent'
					}]
				});
		tbarsearch.render(mainpanel.tbar);
		tbarsearch1.render(mainpanel.tbar);
		tbarsearch.render(mainpanel.tbar);
		//twoTbar.render(mainpanel.tbar);
	});

	mainpanel.render();
	mainpanel.doLayout();
	mainGrid.render();

	// 进入界面加载
	//dateStore.load();

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
	var customerText = Ext.get('comboCustomer').dom.value; // 客商文本值
	var customerId = Ext.getCmp("comboCustomer");// 客商
	var custprop = Ext.getCmp("comboCustprop");// 客商类型
	
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d');
	var custprop = Ext.getCmp("comboCustprop").getValue(); // 客商类型
	var combocostType = Ext.getCmp("combocostType").getValue(); // 费用类型
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商id
	var reconciliationStatus=Ext.getCmp("comboreconstatusStore").getValue();//对账状态
	//var reconciliationUser = Ext.get("reconciliationUser").dom.value; // 对账员
	var departId = Ext.getCmp("combodepartId").getValue(); // 所属部门
	var collectionStatus=Ext.getCmp("combcollectionStatus").getValue();//代收货款收银状态
	dateStore.baseParams = {
		limit:pageSize,
		privilege : privilege,
		checkItems : Ext.get("checkItems").dom.value,
		itemsValue : Ext.get("searchContent").dom.value,
		filter_EQS_departId : departId,
		filter_GED_createTime : stateDate,
		filter_LED_createTime : endDate,
		filter_EQS_custprop : custprop,
		filter_EQS_customerId : customerId,
		filter_EQS_costType : combocostType,
		filter_EQS_collectionStatus : collectionStatus,
		filter_EQS_reconciliationStatus : reconciliationStatus
	}
	
	dateStore.reload();
}


/**
 * 生成对账单事件
 */
function saveReceivableStatement() {

	var ids="";
	var mainConter = Ext.getCmp('carCenter');
	var _records = mainConter.getSelectionModel().getSelections();// 获取所有选中行
	if (_records.length >= 1) {
		for(var i=0;i<_records.length;i++){
			ids=ids+_records[i].id+',';
		}
	}


	
	var stateDate = Ext.getCmp("stateDate").getValue().format('Y-m-d');
	var endDate = Ext.getCmp("endDate").getValue().format('Y-m-d 23:59:59');
	var custprop = Ext.getCmp("comboCustprop").getValue(); // 客商类型
	//var billingCycle = Ext.getCmp("comboBillingCycle").getValue(); // 结算周期
	var customerId = Ext.getCmp("comboCustomer").getValue(); // 客商id
	var departId=Ext.getCmp("combodepartId").getValue(); // 部门id
	//var reconciliationUser = Ext.get("reconciliationUser").dom.value; // 对账员

	if(custprop==""){
		Ext.Msg.alert("系统提示", "请选择客商类型！");
		return;
	}
	
	if(departId==""){
		Ext.Msg.alert("系统提示", "请选择所属部门！");
		return;
	}
	
	Ext.Msg.confirm(alertTitle,'您确定要生成对账单吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true){
						if (form1.getForm().isValid()) {
							form1.getForm().submit({
								url : sysPath + "/" + saveReceivableStatementUrl,
								params : {
									ids : ids,
									stateDate : stateDate,
									endDate : endDate,
									custprop : custprop,
									customerId : customerId,
									departId : departId,
									privilege : privilege
				
								},
								waitMsg : '正在保存数据...',
								success : function(form, action) {
										if (action.result.msg) {
											Ext.Msg.alert(alertTitle, action.result.msg);
											dateStore.load();
										}
								},
								failure : function(form, action) {
										if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
											Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
										} else {
											if (action.result.msg) {
												Ext.Msg.alert(alertTitle,action.result.msg);
											}
										}
									}
							});
						};
		/*
			Ext.Ajax.request({
						url : sysPath + "/" + saveReceivableStatementUrl,
						params : {
							ids : ids,
							stateDate : stateDate,
							endDate : endDate,
							custprop : custprop,
							customerId : customerId,
							privilege : privilege
		
						},
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg);
								dateStore.load();
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
		
						}
					});*/
		}
	});
}

//手工新增
function addReceivebledetail(cid) {
	// 收付类型
	var paymentTypeStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['1', '收款单'], ['2', '付款单']],
				fields : ["paymentTypeId", "paymentTypeName"]
			});
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,

		bodyStyle : 'padding:5px 5px 0',
		width : 700,
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
							id : 'customerId',
							name : 'customerId'
						}, {
							xtype : 'hidden',
							id : 'customerName',
							name : 'customerName'
						}, {
							xtype : 'hidden',
							id : 'departId',
							name : 'departId'
						}, {
							xtype : 'hidden',
							id : 'paymentType',
							name : 'paymentType'
						}, {
							xtype : 'hidden',
							id : 'costType',
							name : 'costType'
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
						allowBlank : false,
						typeAhead : false,
						forceSelection : false,
						fieldLabel:'所属部门',
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
									Ext.getCmp('departId').setValue(record.get("departId"));
								}
						}
					}, {
							xtype : 'combo',
							fieldLabel : '客商名称<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'cusName',
							name : 'cusName',
							blankText : "客商名称为空！",
							anchor : '95%',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('customerId').setValue(record
											.get("customerId"));
									Ext.getCmp('customerName').setValue(record
											.get("cusName"));
								}
							}
					}, {
						xtype : 'combo',
						id : 'combopaymentType',
						triggerAction : 'all',
						store : paymentTypeStore,
						//allowBlank : false,
						emptyText : "请选择",
						fieldLabel:'收付类型',
						allowBlank : false,
						typeAhead : false,
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'paymentTypeName',// 显示值，与fields对应
						valueField : 'paymentTypeId',// value值，与fields对应
						hiddenName:'paymentTypeId',
						width : 60,
						anchor : '95%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('paymentType').setValue(record
											.get("paymentTypeId"));
								}
							}
					}]

			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
						xtype : 'combo',
						typeAhead : true,
						allowBlank : false,
						typeAhead : false,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						fieldLabel:'费用类型',
						store : costTypeStore,
						triggerAction : 'all',
						id : 'combocostTypeAd',
						valueField : 'costType',
						displayField : 'costType',
						emptyText : "请选择",
						anchor : '95%',
							listeners : {
								'select' : function(combo, record, index) {
									Ext.getCmp('costType').setValue(record
											.get("costType"));
								}
							}
					}, {
						xtype : 'numberfield',
						fieldLabel : '金额',
						name : 'amount',
						allowBlank : false,
						typeAhead : false,
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						name : 'remark',
						maxLength : 250,
						anchor : '95%'
					}]

			}]
		}]
	});

	carTitle = '手工增加往来对账明细';
	if (cid != null) {
		carTitle = '修改往来对账明细';
		form.load({
					url : sysPath + "/" + gridSearchUrl,
					params : {
						filter_EQL_id : cid,
						privilege : privilege
					}
				});
		//Ext.getCmp('openingBalance').disable();
	}

	var win = new Ext.Window({
		title : carTitle,
		width : 700,
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
						url : sysPath + '/' + saveReceivabledetailUrl,
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
					carTitle = '修改客商欠款设置';
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
	cid=_records[0].id;
	
	// 收付类型
	var paymentTypeStore = new Ext.data.SimpleStore({
				auteLoad : true, // 此处设置为自动加载
				data : [['1', '收款单'], ['2', '付款单']],
				fields : ["paymentType", "paymentTypeName"]
			});
	var form = new Ext.form.FormPanel({
		labelAlign : 'left',
		frame : true,

		bodyStyle : 'padding:5px 5px 0',
		width : 700,
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
							id : 'customerId',
							name : 'customerId'
						}, {
							xtype : 'hidden',
							id : 'departId',
							name : 'departId'
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
						allowBlank : false,
						typeAhead : false,
						forceSelection : false,
						fieldLabel:'所属部门',
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
						},
						disabled:true
					}, {
						xtype : 'combo',
						fieldLabel : '客商名称<span style="color:red">*</span>',
						allowBlank : false,
						typeAhead : false,
						forceSelection : true,
						minChars : 1,
						triggerAction : 'all',
						store : customerStore,
						pageSize : comboxPage,
						queryParam : 'filter_LIKES_cusName',
						displayField : 'cusName',
						name : 'customerName',
						blankText : "客商名称为空！",
						anchor : '95%',
						emptyText : "请选择客商名称",
						listeners : {
							'select' : function(combo, record, index) {
								Ext.getCmp('customerId').setValue(record
										.get("customerId"));

							}
						},
						disabled:true
					}, {
						xtype : 'combo',
						id : 'combopaymentType',
						triggerAction : 'all',
						store : paymentTypeStore,
						emptyText : "请选择",
						fieldLabel:'收付类型',
						allowBlank : false,
						typeAhead : false,
						forceSelection : true,
						//editable : false,
						mode : "local",// 获取本地的值
						displayField : 'paymentTypeName',// 显示值，与fields对应
						//valueField : 'paymentTypeId',// value值，与fields对应
						name : 'paymentType',
						width : 60,
						anchor : '95%',
						disabled:true
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						name : 'reviewRemark',
						maxLength : 250,
						anchor : '95%'
					}]

			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
						xtype : 'combo',
						typeAhead : true,
						allowBlank : false,
						typeAhead : false,
						forceSelection : true,
						queryParam : 'filter_LIKES_typeName',
						minChars : 0,
						fieldLabel:'费用类型',
						store : costTypeStore,
						triggerAction : 'all',
						id : 'combocostTypeAd',
						displayField : 'costType',
						name:'costType',
						emptyText : "请选择",
						anchor : '95%',
						disabled:true
					}, {
						xtype : 'numberfield',
						fieldLabel : '金额',
						name : 'amount',
						allowBlank : false,
						typeAhead : false,
						anchor : '95%',
						disabled:true
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						name : 'remark',
						maxLength : 250,
						anchor : '95%',
						disabled:true
					}]

			}]
		}]
	});

	carTitle = '审核往来对账明细';
	form.load({
				url : sysPath + "/" + gridSearchUrl,
				params : {
					filter_EQL_id : cid,
					privilege : privilege
				}
			});

	var win = new Ext.Window({
		title : carTitle,
		width : 700,
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
				Ext.Msg.confirm(alertTitle,'单据审核,确认要操作吗?',function(btnYes) {
					if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
						if (form.getForm().isValid()) {
							this.disabled = true;// 只能点击一次
							form.getForm().submit({
								url : sysPath + '/' + auditUrl,
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

			text : "重置",
			iconCls : 'refresh',
			handler : function() {
				if (cid == null) {
					form.getForm().reset();
				}
				if (cid != null) {
					carTitle = '修改客商欠款设置';
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

/**
撤销审核单据
*/
function revocationAudit(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要撤销审核的单据！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能撤销审核一张单据！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'单据撤销审核,确认要操作吗?',function(btnYes) {
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

/**
作废往来明细（手工新增的才允许）
*/
function invalid(_records){
	if (_records.length < 1) {
		Ext.Msg.alert("系统消息", "请选择需要作废的单据！");
		return false;
	}
	if (_records.length > 1) {
		Ext.Msg.alert("系统消息", "一次只能作废一张单据！");
		return false;
	}
	
	Ext.Msg.confirm(alertTitle,'作废往来明细,确认要操作吗?',function(btnYes) {
		if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + invalidUrl,
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


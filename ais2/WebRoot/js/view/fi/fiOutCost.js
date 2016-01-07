var privilege = 129;
var custprop='外发';
	var fields = [
			{name : "id"}, 
			{name : 'dno'}, 
			{name : 'outcostNo'}, 
			{name : 'customerId'}, 
			{name : 'customerName'}, 
			{name : 'amount'}, 
			{name : 'outuserId'}, 
			{name : 'isdelete'}, 
			{name : 'remark'},'cusDepartCode','cusDepartName',
			{name : 'createName'},
			{name:'takeMode'},
			{name:'addr'},
			'sourceData','batchNo',
			'sourceNo','payStatus',
			{name:'cpFee'},'reviewDate','reviewUser',
			{name:'piece'},
			{name:'cusWeight'},
			{name:'paymentCollection'},
			{name:'distributionMode'},
			{name : "createTime"}, 
			{name : "updateTime"}, 
			{name : 'updateName'}, 
			{name:'cpName'},
			{name : 'createDeptid'}, 
			{name:'createDept'},
			{name:'filghtMainNo'},
			{name:'conginee'},
			{name:'subNo'},
			{name : 'departId'}, 
			{name:'departName'},
			{name:'remark'},
			'totalFee','outStockDate',
			'deficitFee',
			{name:'status'},
			{name:'cusFee'},
			{name : 'ts'}];
	//外发客商Store
	var customerStore = new Ext.data.Store({
		storeId : "customerStore",
		baseParams : {
			limit : pageSize,
			privilege : 61,
			filter_EQL_status:1,
			filter_EQS_custprop:custprop
		},
		proxy : new Ext.data.HttpProxy({
			url : sysPath + "/sys/customerAction!list.action" 
		}),
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
		}, [
		{name:'cusName'},
		{name:'id'}
		])
	});
	customerStore.load();
		//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/basRightDepartAction!findDepartName.action",method:'post'}),
            baseParams:{
            	privilege:63,
            	filter_EQL_userId:userId
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty: 'totalCount'
           }, [{name:'departName', mapping:'DEPARTNAME',type:'string'},
               {name:'departId', mapping:'RIGHTDEPARTID',type:'string'}             
              ])    
    });
	
	var auditStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已审核'],
  	  		  ['0','未审核']],
   		fields:["id","name"]
	});
	
			// 查询主列表
	var dateStore = new Ext.data.Store({
			storeId : "dateStore",
			baseParams : {
				limit : pageSize,
				privilege : privilege
			},
			proxy : new Ext.data.HttpProxy({
				url : sysPath + "/fi/fiOutCostAction!ralaList.action" 
			}),
			reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
			}, fields),
			 sortInfo:{field:'dno',direction:'DESC'}
	});
	
	var fiPayStatusStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已支付'],['0','未支付']],
   		fields:["id","name"]
	});
	
	
Ext.onReady(function() {
	var sm = new Ext.grid.CheckboxSelectionModel({
		moveEditorOnEnter:true, 
		listeners : {
			selectionchange:function(){
				setTotalCount();
			}
		}
	});
	
			//客服部门
	var serviceDepartStore = new Ext.data.Store({ 
            storeId:"serviceDepartStore",                        
            proxy: new Ext.data.HttpProxy({url:sysPath+"/sys/departAction!findDepart.action",method:'post'}),
            baseParams:{
               filter_EQL_isCusDepart:1
            },
            reader: new Ext.data.JsonReader({
            root: 'resultMap', totalProperty:'totalCount'
           }, [  
                 {name:'departName', mapping:'DEPARTNAME'},    
                 {name:'departId', mapping: 'DEPARTNO'}
              ]),                                    
            sortInfo:{field:'departId',direction:'ASC'}
     });
	
	function setTotalCount(){
		var vnetmusicRecord = mainGrid.getSelectionModel().getSelections();
		var cost=0;
		var returnCost=0;
		var changeCost=0;
			
		for(var j=0;j<vnetmusicRecord.length;j++){;
	 		if(vnetmusicRecord[j].data.sourceData=='更改申请'){
	 			changeCost+=parseFloat(vnetmusicRecord[j].data.amount);
	 			cost+=parseFloat(vnetmusicRecord[j].data.amount);
	 		}else if(vnetmusicRecord[j].data.sourceData=='返货登记'){
				returnCost+=parseFloat(vnetmusicRecord[j].data.amount);
				cost+=parseFloat(vnetmusicRecord[j].data.amount);
	 		}else{
				cost+=parseFloat(vnetmusicRecord[j].data.amount);
	 		}
	 	}
	 	Ext.getCmp('count').setValue(vnetmusicRecord.length);
	 	
	 	Ext.getCmp('totalCost').setValue(cost);
	 	Ext.getCmp('totalReturnCost').setValue(returnCost);
	 	Ext.getCmp('totalChangeCost').setValue(changeCost);
			 	 
	}
	
	 	var fourbar = new Ext.Toolbar({	frame : true,
		items : ['&nbsp;','<B>统计信息</B>','&nbsp;','-','&nbsp;'
				,'总行数:',
		    	{xtype : 'numberfield',
                id:'count',
   			    name: 'count',
   			    readOnly:true,
   			    maxLength : 10,
                width:49,
                value:0,
                enableKeyEvents:true
              },'&nbsp;'
				,'总金额:',
		    	{xtype : 'numberfield',
                fieldLabel: '总金额',
                id:'totalCost',
   			    name: 'totalCost',
   			    readOnly:true,
   			    maxLength : 10,
                width:49,
                 value:0,
                enableKeyEvents:true
              },'&nbsp;','返货金额:',
		    	{xtype : 'numberfield',
   	            id:'totalReturnCost',
   			    name: 'totalReturnCost',
   			    readOnly:true,
   			    maxLength : 10,
   			    value:0,
                width:49,
                enableKeyEvents:true
              },'&nbsp;','更改金额:',
		    	{xtype : 'numberfield',
   	            id:'totalChangeCost',
   			    name: 'totalChangeCost',
   			    readOnly:true,
   			    maxLength : 10,
                width:49,
                 value:0,
                enableKeyEvents:true
              }]
		});
	
	
var rowNum = new Ext.grid.RowNumberer({
			header : '序号',
			width : 25,
			sortable : true
		});

var cm = new Ext.grid.ColumnModel([rowNum, sm, {
			header : 'id',
			dataIndex : 'id',
			sortable : true,
			hidden : true
		}, {
			header : '配送单号',
			dataIndex : 'dno',
			width : 80
		}, {
			header : '外发客商ID',
			dataIndex : 'customerId',
			width : 60,
			hidden:true
		},{
			header : '外发客商',
			dataIndex : 'customerName',
			width : 60
		}, {
			header : '外发成本',
			dataIndex : 'amount',
			width : 55,
			renderer:function(v){
				if(v==0){
					return 0;
				}else{
					return '<span style="color:red">'+v+'</span>';
				}
			}
		},{
			header : '数据来源',
			dataIndex : 'sourceData',
			width : 68
		},{
			header : '审核状态',
			dataIndex : 'status',
			renderer:function(v){
				if(v==0){
					return '未审核';
				}else if(v==1){
					return '已审核';
				}
			},
			width : 60
		},{  //'reviewDate','reviewUser',
			header : '审核人',
			dataIndex : 'reviewUser',
			width : 50
		},{  //'reviewDate','reviewUser',
			header : '审核时间',
			dataIndex : 'reviewDate',
			width : 100
		},{  //'reviewDate','reviewUser',
			header : '批次号',
			dataIndex : 'batchNo',
			width : 50
		},{ 
			header : '支付状态',
			dataIndex : 'payStatus',
			renderer:function(v){
				if(v==0){
					return '未支付';
				}else if(v==1){
					return '已支付';
				}else{
					return v;
				}
			},
			width : 60
		},{
			header : '备注',
			dataIndex : 'remark',
			width : 100
		},{
			header : '发货代理',
			dataIndex : 'cpName',
			width : 80
		},{
			header : '件数',
			dataIndex : 'piece',
			width : 50
		},{
			header : '重量',
			dataIndex : 'cusWeight',
			width : 50
		},{
			header : ' 代收货款',
			dataIndex : 'paymentCollection',
			width : 68
		},{
			header : '预付提送费',
			dataIndex : 'cpFee',
			width : 68
		},{
			header : '到付提送费',
			dataIndex : 'cusFee',
			width : 68
		},{
			header : '收入合计',
			dataIndex : 'totalFee',
			width : 68
		},{
			header : '毛利',
			dataIndex : 'deficitFee',
			width : 68
		},{
			header : '返货单号',
			dataIndex : 'sourceNo',
			hidden:true,
			width : 68
		},{
			header : '外发单号',
			dataIndex : 'outcostNo',
			width : 60
		},{
			header : '外发员',
			dataIndex : 'outuserId',
			width : 60
		},{
			header : '出库时间',
			dataIndex : 'outStockDate',
			width : 60
		},{
			header : '主单号',
			dataIndex : 'filghtMainNo',
			width : 60
		},{
			header : '分单号',
			dataIndex : 'subNo',
			width : 60
		},{
			header : '收货人',
			dataIndex : 'conginee',
			width : 60
		},{
			header : '配送方式',
			dataIndex : 'distributionMode',
			width : 60
		},{
			header : '提货方式',
			dataIndex : 'takeMode',
			width : 60
		},
		  {header: '客服部门编码', dataIndex: 'cusDepartCode',width:80,sortable : true},
		  {header: '客服部门', dataIndex: 'cusDepartName',width:110,sortable : true},
		
		{
			header : '收货人地址',
			dataIndex : 'addr',
			width : 120
		},	{header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
	        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
	        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},
	        {header: '创建部门', dataIndex: 'departName',width:80,hidden:true,sortable : true},
	    {
			header : '时间戳',
			dataIndex : 'ts',
			width : 60,
			sortable : true,
			hidden : true,
			hideable : false
		}]);

	var form1 = new Ext.form.FormPanel({
				id : 'form1',
				frame : true,
				width : 100,
				cls : 'displaynone',
				hidden : true,
				items : [],
				buttons : []
			});
	form1.render(document.body);

var tbar = ['&nbsp;',{
			text : '<b>成本录入</b>',
			iconCls : 'groupAdd',
			id : 'costin',
			handler : outCostIn
			},'-','&nbsp;',{
			text : '<b>成本作废</b>',
			iconCls : 'groupClose',
			id : 'deltin',
			handler : function(){
					var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
					var deletebtn = Ext.getCmp('deltin');// 获得删除按钮
	
					if(_record.length == 1){
						if(_record[0].get('status')=='0'){
							Ext.Msg.confirm(alertTitle,'您确定要作废这条数据吗?',function(btnYes) {
						    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
										form1.getForm().doAction('submit', {
											url : sysPath+ "/fi/fiOutCostAction!delData.action",
											method : 'post',
											params : {
												id:_record[0].data.id,
												privilege:privilege,
												ts:_record[0].data.ts
											},
											waitMsg : '正在保存数据...',
											success : function(form1, action) {
												Ext.Msg.alert(alertTitle,action.result.msg,
														function() {
															dateStore.reload()
														});
											},
											failure : function(form1, action) {
												Ext.Msg.alert(alertTitle,action.result.msg,function() {
															dateStore.reload();
															 select();
												});
											}
										});
								}
							});
						}else{
							Ext.Msg.alert(alertTitle, '审核过的数据不能进行成本作废');
						}
					}else{
						 Ext.Msg.alert(alertTitle, '只能选择一条语句进行外发成本作废');
					}
			
				}
			},'-','&nbsp;',{
			text : '<b>会计审核</b>',
			iconCls : 'groupPass',
			disabled:false,
			id : 'accountaduit',
			handler : function(){
				var _records = mainGrid.getSelectionModel().getSelections();
				if (_records.length == 0) {
					 Ext.Msg.alert(alertTitle, '请选择一条数据进行审核');
				}else{
					var cusId =_records[0].get('customerId');
					for(var i=0;i<_records.length;i++){
						if(cusId!=_records[i].get('customerId')){
							Ext.Msg.alert(alertTitle,"只能选择同一外发客商进行会计审核",function(){});
							return ;						
						}
						
						if(_records[i].data.status=='1'){
							Ext.Msg.alert(alertTitle,"存在已审核的数据，不能重复审核",function(){});
							return;
						}
					}
					accountAduit(_records);
				}
			  }
			},'-','&nbsp;',{
			text : '<b>审核撤销</b>',
			iconCls : 'groupNotPass',
//			hidden:true,
//			disabled:true,
			id : 'qXAccountaduit',
			handler : function(){
				var _record = mainGrid.getSelectionModel().getSelections();// 获得所有选中的行
				var deletebtn = Ext.getCmp('qXAccountaduit');// 获得删除按钮

				if(_record.length == 1){
					if(_record[0].get('status')=='1'){
						if(_record[0].get('payStatus')=='1'){
							Ext.Msg.alert(alertTitle, '成本已支付，不能撤销审核状态');
							return;
						}
					}else{
						Ext.Msg.alert(alertTitle, '只有审核过的数据才能进行撤销成本审核');
						return;
					}
				}else{
					 Ext.Msg.alert(alertTitle, '请选择您要撤销审核的数据,只能选择一条记录');
					 return;
				}
				qxAccountAduit(_record);			
			}
			},'-','&nbsp;',{
			text : '<b>导出</b>',
			iconCls : 'sort_down',
			id : 'exportbtn',
			handler : exportinfo
		}, {
			text : '<b>打印</b>',
			iconCls : 'table',
			hidden:true,
			id : 'printbtn',
			handler : printinfo
		},'-','&nbsp;',{
			text : '<b>查询</b>',
			iconCls : 'btnSearch',
			id : 'btnSearch',
			handler : searchContent
		},'-','&nbsp;','客服部门:',{
			   xtype : 'combo',
			   id:'serviceDepartId',
     		   fieldLabel: '客服部门',
	           queryParam : 'filter_LIKES_departName',
		       minChars : 1,
		       triggerAction : 'all',
		       forceSelection : true,
			   store: serviceDepartStore,
			   pageSize : 50,
			   listWidth:245,
			   displayField : 'departName',
			   valueField : 'departId',
			   name : 'olist[0].serviceDepartId',
			   anchor : '95%',
			   emptyText : "请选择客服部门名称"
     		}];
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
							scrollOffset : 0,
							autoScroll : true
						},
						autoScroll : true,
						frame : true,
						loadMask : true,
						sm : sm,
						cm : cm,
						store : dateStore,
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
						// title : "问题账款",
						id : 'view',
						el : 'showView',
						labelAlign : 'left',
						height : Ext.lib.Dom.getViewHeight() - 4,
						width : Ext.lib.Dom.getViewWidth(),
						bodyStyle : 'padding:1px',
						layout : "border",
						tbar : tbar,
						frame : false,
						items : [mainGrid]
					});

			mainpanel.on('render', function() {
						var tbarsearch = new Ext.Toolbar({
									items : ['&nbsp;','录入日期', {
												xtype : 'datefield',
												format : 'Y-m-d',
												selectOnFocus:true,
												width : 80,
												value : new Date().add(
														Date.DAY, -7),
												blankText : "录入日期不能为空！",
												name:'startDate',
												id : 'startDate'
											},'&nbsp;', '至','&nbsp;', {
												xtype : 'datefield',
												format : 'Y-m-d',
												selectOnFocus:true,
												width : 80,
												value : new Date(),
												blankText : "录入日期不能为空！",
												name:'endDate',
												id : 'endDate'
											},'-','&nbsp;','创建部门:',{
													xtype : 'combo',
													id:'comboRightDepart2',
													hiddenId : 'dictionaryName',
									    			hiddenName : 'olist[0].rightDepartId',
													triggerAction : 'all',
													store : rightDepartStore2,
													mode:'local',
													width:70,
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
																	searchContent();
												              }
												 		 }
												 	}
									    }, '-','&nbsp;', '客商:',{
												xtype : 'combo',
												typeAhead : false,
												forceSelection : true,
												minChars : 1,
												listWidth:245,
												triggerAction : 'all',
												selectOnFocus:true,
												store : customerStore,
												pageSize : pageSize,
												queryParam : 'filter_LIKES_cusName',
												id : 'combocus',
												valueField : 'id',
												displayField : 'cusName',
												hiddenName : 'cusName',
												emptyText:'请选择客商名称',
												width:70,
												enableKeyEvents : true,
												listeners : {
													keyup:function(numberfield, e){
											             if(e.getKey() == 13 ){
																searchContent();
											              }
											 		 }
											 	}
											}, '-','&nbsp;','配送单号<span style="color:red">*</span>:',{
												xtype : 'numberfield',
												labelAlign : 'right',
												name : 'dno2',
												width:50,
												anchor : '95%',
												selectOnFocus:true,
												decimalPrecision:0,
												allowNegative:false,
												id:'dno2',
												maxLength:12,
												allowBlank : true,
												enableKeyEvents:true,
									    		listeners : {
									    		     keyup:function(numberfield, e){
									                     if(e.getKey() == 13 ){
															searchContent();
									                     }
									 				 }
									    		}
											},/* '-','&nbsp;','外发单号:',{
												xtype:'textfield',
												width:50,
												name:'outno',
												id:'outno',
												enableKeyEvents : true,
												listeners : {
													keyup:function(numberfield, e){
											             if(e.getKey() == 13 ){
																searchContent();
											              }
											 		 }
											 	}
											}, */'-','&nbsp;','审核状态:',{
												xtype:'combo',
												width:60,
												store:auditStore,
												name:'auditstatus',
												id:'auditstatus',
												emptyText : "选择审核状态",
												typeAhead : false,
												forceSelection : true,
												mode:'local',
												triggerAction : 'all',
												displayField : 'name',
												valueField : 'id',
												enableKeyEvents : true,
												listeners : {
													keyup:function(numberfield, e){
											             if(e.getKey() == 13 ){
																searchContent();
											              }
											 		 }
											 	}
											},'-','&nbsp;&nbsp;',{
								                xtype : "combo",
								    			id:"comboselect",
								   				width : 70,
								 				triggerAction : 'all',
								    			model : 'local',
								    			hiddenId : 'checkItems',
								    			hiddenName : 'checkItems',
								    			name : 'checkItemstext',
								    			store : [['', '查询全部'],
								    					['EQL_batchNo', '批次号'],
								    					['EQL_payStatus', '支付状态'],
								    					['LIKES_outcostNo,','外发单号']],
								    			emptyText : '选择查询类型',
								    			forceSelection : true,
								    			listeners : {
								    				'select' : function(combo, record, index) { 
								    					if (combo.getValue() == '') {
								    						Ext.getCmp("itemsValue").disable();
								    						Ext.getCmp("itemsValue").setValue("");
								    						Ext.getCmp("itemsValue").show();
								    						
								    						Ext.getCmp("fiPayStatusSelect").disable();
    														Ext.getCmp("fiPayStatusSelect").hide();
    														Ext.getCmp("fiPayStatusSelect").setValue("");
								    					}else if(combo.getValue() == 'EQL_payStatus'){  //
								    				    	Ext.getCmp("itemsValue").disable();
								    						Ext.getCmp("itemsValue").hide();
								    						
								    						Ext.getCmp("fiPayStatusSelect").enable();
    														Ext.getCmp("fiPayStatusSelect").show();
								    					}else{  //
								    						Ext.getCmp("fiPayStatusSelect").disable();
    														Ext.getCmp("fiPayStatusSelect").hide();
    														Ext.getCmp("fiPayStatusSelect").setValue("");
								    					
								    				    	Ext.getCmp("itemsValue").enable();
								    						Ext.getCmp("itemsValue").show();
								    					}
								    				}
								    			 }
								    		},'-',{
													xtype : 'combo',
													typeAhead : false,
													forceSelection : true,
													width : 70,
													id:'fiPayStatusSelect',
													hidden:true,
													diabled:true,
													mode:'local',
													triggerAction : 'all',
													store : fiPayStatusStore,
													displayField : 'name',
													valueField : 'id',
													emptyText : "请选择付款状态"
														
												},{	xtype:'textfield',
									 	        id : 'itemsValue',
									 	        width : 70,
										        name : 'itemsValue',
										        disabled:true,
									            enableKeyEvents:true,
									            listeners : {
									 				keyup:function(textField, e){
									                     if(e.getKey() == 13){
									                     	searchContent();
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

	function outCostIn(){
	    var form = new Ext.form.FormPanel({
					frame : true,
					border : false,
					bodyStyle : 'padding:5px 5px 5px',
					width : 400,
					labelAlign : "right",
					labelWidth : 80,
					defaults : {
						xtype : "textfield",
						width : 230
					},
					items:[
						   {xtype:'hidden',name:'departId',id:'departId',value:bussDepart},
						   {xtype:'hidden',name:'sourceData',id:'sourceData',value:'传真录入'},
						   {xtype:'hidden',name:'status',id:'status',value:0},
						   {xtype:'hidden',name:'isdelete',id:'isdelete',value:1},
						   {xtype:'hidden',name:'outuserId',id:'outuserId',value:userId},
                           {xtype:'numberfield',
                           	name:'dno',
                           	id:'dno23',
                           	fieldLabel:'配送单号<span style="color:red">*</span>',
                           	allowBlank:false,
                           	 allowNegative:false,
                           	 decimalPrecision:0,
                           	blankText:'配送单号不能为空！',
                           	enableKeyEvents:true,
                           	maxLength : 20,
							listeners : {
								'focus':function(v){
										v.selectText();
              					},
              					blur:function(v){
//              						if(v.getValue()!=''){
//              							Ext.Ajax.request({
//				 							url:sysPath+"/fax/oprFaxInAction!list.action",
//											params:{
//												filter_EQL_dno:v.getValue(),
//												filter_EQS_distributionMode:'外发'
//											},
//											success : function(response) { // 回调函数有1个参数
//												if(Ext.decode(response.responseText).result.length==0){
//													Ext.getCmp('showMsg').getEl().update('<span style="color:red">您录入的配送单号（'+v.getValue()+'）不存在或者不是外发的货物，请重新录入</span>');
//													Ext.getCmp('dno23').focus();
//													Ext.getCmp('dno23').markInvalid("配送单号不存在");
//												}else{
//													Ext.Ajax.request({
//							 							url:sysPath+"/stock/oprStatusAction!list.action",
//														params:{
//															filter_EQL_dno:v.getValue()
//														},
//														success : function(response2) { 
//															if(Ext.decode(response2.responseText).result.length==0){
//																Ext.getCmp('showMsg').getEl().update('<span style="color:red">此配送单号（'+v.getValue()+'）的状态记录不存在，无法验证</span>');
//																Ext.getCmp('dno23').focus();
//																Ext.getCmp('dno23').markInvalid("配送单号的出库状态不存在");
//															}else{
//																if(Ext.decode(response2.responseText).result[0].feeAuditStatus!='1'){
//																	if(Ext.decode(response2.responseText).result[0].outStatus!='1'){
//																		Ext.getCmp('showMsg').getEl().update('<span style="color:red">此配送单号（'+v.getValue()+'）的货物未出库，不允许录入</span>');
//																		Ext.getCmp('dno23').focus();
//																		Ext.getCmp('dno23').markInvalid("此配送单不允许录入");
//																	}else{
//																		Ext.Ajax.request({
//												 							url:sysPath+"/fi/fiOutCostAction!list.action",
//																			params:{
//																				filter_EQL_dno:v.getValue(),
//																				filter_EQS_sourceData:'传真录入',
//																				filter_EQL_status:0,
//																				filter_EQL_returnStatus:0,
//																				filter_EQL_isdelete:1
//																			},
//																			success : function(response3){
//																				if(Ext.decode(response3.responseText).result.length!=0){
//																					Ext.getCmp('showMsg').getEl().update('<span style="color:red">此配送单号（'+v.getValue()+'）已外发成本录入，不能重复录入</span>');
//																					Ext.getCmp('dno23').focus();
//																					Ext.getCmp('dno23').markInvalid("配送单号的出库状态不存在");
//																				}else{
//																					Ext.getCmp('showMsg').getEl().update('<span style="color:red"></span>');
//																					Ext.getCmp('combocus1').setValue(Ext.decode(response.responseText).result[0].goWhereId);
//																				}
//																			},
//																			failure : function(response) {
//																				Ext.getCmp('showMsg').getEl().update("数据库出错，不允许重复录入");
//																			}
//																		});
//																	}
//																}else{
//																	Ext.getCmp('showMsg').getEl().update('<span style="color:red">此配送单号（'+v.getValue()+'）的货物已做成本录入和审核，不允许多次录入</span>');
//																	Ext.getCmp('dno23').focus();
//																	Ext.getCmp('dno23').markInvalid("不允许重复录入");
//																}
//															}
//														},
//														failure : function(response) {
//															Ext.getCmp('showMsg').getEl().update(vstr1);
//														}
//													});
//												}
//											},
//											failure : function(response) {
//												Ext.getCmp('showMsg').getEl().update('<span style="color:red">配送单号验证失败，请重新输入</span>');
//												Ext.getCmp('dno23').focus();
//												Ext.getCmp('dno23').markInvalid("配送单号不存在");
//											}
//										});	
//              						}
              					},
				 				keyup:function(numberfield, e){
				                     if(e.getKey() == 13 ){
										Ext.getCmp('outcostNo2').focus();
				                     }
				 				}
				 			}
                           	},
                           {xtype:'textfield',
                            id:'outcostNo2',
                            name:'outcostNo',
                            maxLength : 20,
                            fieldLabel:'外发单号<span style="color:red">*</span>',
                           	allowBlank:false,blankText:'外发单号不能为空！',
                           	enableKeyEvents:true,
                           	listeners : {
								'focus':function(v){  
										v.selectText();
              											  	}, 
				 				keyup:function(numberfield, e){
				                     if(e.getKey() == 13 ){
										Ext.getCmp('combocus1').focus();
				                     }
				 				}
				 			}
                           	},
                           {
                           		xtype : 'combo',
                           		fieldLabel:'客商名称',
								typeAhead : false,
								forceSelection : true,
								minChars : 1,
								triggerAction : 'all',
								store : customerStore,
								pageSize : pageSize,
								queryParam : 'filter_LIKES_cusName',
								id : 'combocus1',
								valueField : 'id',
								displayField : 'cusName',
								hiddenName : 'customerId',
								emptyText:'请选择',
								enableKeyEvents:true,
								listeners : {
									'focus':function(v){  
											v.selectText();
	              											  	}, 
					 				keyup:function(numberfield, e){
					                     if(e.getKey() == 13 ){
											Ext.getCmp('amount').focus();
					                     }
					 				}
					 			}
                           	},
                           	{xtype : 'numberfield',
                           	 name:'amount',
                           	 id:'amount',
                           	 allowNegative:true,
                           	 decimalPrecision:2,
                           	 maxLength : 20,
                           	 fieldLabel:'外发成本<span style="color:red">*</span>',
                           	 allowBlank:false,blankText:'外发成本不能为空！',
                           	 emptyText:'请选择',
								enableKeyEvents:true,
								listeners : {
									'focus':function(v){  
											v.selectText();
	              											  	}, 
					 				keyup:function(numberfield, e){
					                     if(e.getKey() == 13 ){
											Ext.getCmp('remark').focus();
					                     }
					 				}
					 			}},
                           	{xtype:'textfield',name:'outuser',fieldLabel:'外发员',
                           	 disabled:true,
                           	 value:userName},
                           	{xtype:'textarea',allowBlank:false,maxLength : 500,fieldLabel:'备注<span style="color:red">*</span>',name:'remark',id:'remark'},
                           	{
				   			 xtype:'label',
				   			 id:'showMsg',
				   			 width:380
							}
                    ]
				});
		var win = new Ext.Window({
			title : '成本录入',
			width : 400,
			closeAction : 'hide',
			plain : true,
			modal : true,
			items : form,
			buttonAlign : "center",
			buttons : [{
				text : "保存",
				iconCls : 'save',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath + "/fi/fiOutCostAction!save.action?privilege=129",
							waitMsg : '正在保存数据...',
							params : {
								customerName:Ext.getCmp('combocus1').getRawValue()
							},
							success : function(form, action) {
								win.hide(), 
								Ext.Msg.alert(alertTitle,action.result.msg,function(){
									dateStore.reload();
								});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								}else{
									if (action.result.msg) {
										Ext.Msg.alert(alertTitle,action.result.msg);
									}
								}
							}
						});
						this.disabled = false;
					}
				}
			}, {
				text : "重置",
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
	
	function qxAccountAduit(records){
		Ext.Ajax.request({
			url:sysPath+"/fi/fiOutCostAction!qxAmountCheck.action",
			params:{
				id:records[0].data.id
			},
			success : function(response3){
				Ext.Msg.confirm(alertTitle,response3.responseText+",您确定要撤销审核这条数据吗?",function(btnYes) {
			    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
							form1.getForm().doAction('submit', {
								url : sysPath+ "/fi/fiOutCostAction!qxFiAudit.action",
								method : 'post',
								params : {
									id:records[0].data.id,
									privilege:privilege,
									ts:records[0].data.ts
								},
								waitMsg : '正在保存数据...',
								success : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,
											function() {
												dateStore.reload()
											});
								},
								failure : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,function() {
												dateStore.reload();
												 select();
									});
								}
							});
					}
				});
			},
			failure : function(response) {
				Ext.Msg.alert(alertTitle,"信息提示出错，无法提交撤销");
			}
		});
	}
	
	function accountAduit(records){
		var ids="";
		for(var i=0;i<records.length;i++){
			if(i==0){
				ids += "aa["+i+"].dno="+records[i].get("dno")+
				"&aa["+i+"].ts="+records[i].get("ts")+
				"&aa["+i+"].id="+records[i].get("id")+
				"&aa["+i+"].payStatus=0"+
				"&aa["+i+"].returnStatus=0"+
				"&aa["+i+"].customerId="+records[i].get("customerId");
			}else{
				ids += "&aa["+i+"].dno="+records[i].get("dno")+
				"&aa["+i+"].ts="+records[i].get("ts")+
				"&aa["+i+"].id="+records[i].get("id")+
				"&aa["+i+"].payStatus=0"+
				"&aa["+i+"].returnStatus=0"+
				"&aa["+i+"].customerId="+records[i].get("customerId");
			}
		}	
		Ext.Ajax.request({
			url:sysPath+"/fi/fiOutCostAction!aduitAmountCheck.action",
			params : ids,
			success : function(response){
				var batchNo=Ext.decode(response.responseText).batchNo;
			    Ext.Msg.confirm(alertTitle,Ext.decode(response.responseText).auditInfo+'请确定！<span  style="color:red">您确实要审核这些数据吗?</span>',function(btnYes) {
			    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			    			ids+="&privilege="+privilege;
			    			ids+="&batchNo="+batchNo;
							form1.getForm().doAction('submit', {
								url : sysPath+ "/fi/fiOutCostAction!accountAduit.action",
								method : 'post',
								params : ids,
								waitMsg : '正在审核数据...',
								success : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,
											function() {dateStore.reload();});
								},
								failure : function(form1, action) {
									Ext.Msg.alert(alertTitle,action.result.msg,
											function() {dateStore.reload();select();});
								}
							});
					}
				});
			},
			failure : function(response) {
				Ext.Msg.alert(alertTitle,"信息提示出错，无法提交");
			}
		});
	}
});
	function searchContent(){
		var dId= Ext.getCmp('comboRightDepart2').getValue();
	    var startDate=Ext.get('startDate').dom.value;
	    var endDate=Ext.get('endDate').dom.value;
	    var dno=Ext.getCmp('dno2').getValue();
	    var cusId=Ext.getCmp('combocus').getValue();
	    var auditstatus=Ext.getCmp('auditstatus').getValue();
	  //  var outno=Ext.getCmp('outno').getValue();
	    
	    if(dno==""){
	    	if(Ext.get("checkItems").dom.value=="EQL_payStatus"){
	    		Ext.apply(dateStore.baseParams={
					filter_GED_createTime : startDate,
					filter_LED_createTime : endDate,
					privilege:privilege,
					filter_EQL_departId:dId,
					filter_LIKES_cusDepartCode:Ext.getCmp('serviceDepartId').getValue(),
					checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.getCmp("fiPayStatusSelect").getValue(),
			//		filter_LIKES_outcostNo:outno,
					filter_EQL_customerId:cusId,
					filter_EQL_status:auditstatus
			    });
	    	}else{
			    Ext.apply(dateStore.baseParams={
					filter_GED_createTime : startDate,
					filter_LED_createTime : endDate,
					privilege:privilege,
					filter_LIKES_cusDepartCode:Ext.getCmp('serviceDepartId').getValue(),
					filter_EQL_departId:dId,
					checkItems : Ext.get("checkItems").dom.value,
					itemsValue : Ext.get("itemsValue").dom.value,
			//		filter_LIKES_outcostNo:outno,
					filter_EQL_customerId:cusId,
					filter_EQL_status:auditstatus
			    });
	    	}
	    }else{
	    	Ext.apply(dateStore.baseParams={
				filter_EQL_dno:dno,
				privilege:privilege,
				filter_EQL_departId:dId
		    });
	    }
	    
		dateStore.reload({
			params : {
				start : 0,
				limit : pageSize
			}
		});
	}
/**
 * 导出按钮事件
 */
function exportinfo() {
	parent.exportExl(Ext.getCmp('mainGrid'));
}

/**
 * 打印按钮事件
 */
function printinfo() {
	Ext.Msg.alert("提示", "正在开发中...");
}
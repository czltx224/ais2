	Ext.QuickTips.init();
	var privilege=232;
	var comboxPage=50;
	var saveUrl="fi/fiAdvancebpAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="fi/fiAdvancebpAction!ralaList.action";
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";	
	var reviewConfirmationUrl="/fi/fiAdvancebpAction!reviewConfirmation.action";//审核
	var reviewRegisterUrl="/fi/fiAdvancebpAction!reviewRegister.action";//撤销审核
	
	var booleanId =false; 
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25,
		sortable : true
		
	});
	var fields=[
		'id',
		'customerId',
		'customerName',
		'settlementType',
		'settlementAmount',
		'amount',
		'settlementBalance',
		'sourceData',
		'sourceNo',
		'remark',
		'updateName',
		'updateTime',
		'createName',
		'createTime',
		'departId',
		'departName',
		'fiAdvanceId',
		'departId',
		'accountNum',
		'accountName',
		'bank',
		'ts',
		'payStatus',
		'status',
		'departName',
		'fiAdvanceId',
		'reviewUser',
		'reviewDate',
		'reviewRemark',
		'reviewStatus'];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
	
				// 客商列表
	customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl
				}),
	            baseParams:{
	            	filter_EQS_settlement:'预付'
	            },
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
	
	customerStore2 = new Ext.data.Store({
		storeId : "customerStore2",
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
	
			//权限部门
	var rightDepartStore2 = new Ext.data.Store({ 
            storeId:"rightDepartStore2",                        
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
	
	// 部门列表
	departStore = new Ext.data.Store({
		storeId : "departStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + departGridSearchUrl
				}),
		baseParams:{
              privilege:53,
              filter_EQL_isBussinessDepa:1
           },
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
	});
	
	var statusDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['0','已作废'],['1','正常']],
   		fields:["id","name"]
	});
	
	var sourceDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','未审核'],['2','已审核']],
   		fields:["id","name"]
	});
	
	var dataStore = new Ext.data.Store({
                proxy: new Ext.data.HttpProxy({url:sysPath+"/"+ralaListUrl}),
                baseParams:{
                	limit: pageSize,
                	privilege:privilege
                },
                sortInfo : {field: "status", direction: "DESC"},
                reader:jsonread
    });
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','创建日期:',
						 {
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		selectOnFocus:true,
				    		emptyText : "选择开始时间",
				    		value : new Date().add(Date.DAY, -7),
				    		width : 80,
				    		listeners : {
				    			'select' : function() {
				    			   var start = Ext.getCmp('startDate').getValue()
				    			      .format("Y-m-d");
				    			   Ext.getCmp('endDate').setMinValue(start);
				    		     }
			    		    }
						},'&nbsp;','至','&nbsp;',{
							xtype : 'datefield',
				    		id : 'endDate',
				    		selectOnFocus:true,
				    		value : new Date(),
				    		format : 'Y-m-d',
				    		emptyText : "选择结束时间",
				    		width : 80
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
																	searchLog();
												              }
												 		 }
												 	}
									    },'-','&nbsp;','客商名称:',{
							xtype : 'combo',
							typeAhead : false,
							forceSelection : true,
							id:'cusId',
							selectOnFocus:true,
							listWidth:245,
							width : 80,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_cusName',
							displayField : 'customerName',
							valueField : 'customerId',
							name : 'cusName',
							emptyText : "请选择客商名称",
							listeners : {
								'select' : function(combo, record, index) {
								
								}
							}
						},'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 80,
   				selectOnFocus:true,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['', '查询全部'],
    					['EQL_reviewStatus', '审核状态'],
    					['EQL_status', '状态'],
    					['LIKES_sourceNo', '来源单号']],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == '') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("reviewStatus").disable();
    						Ext.getCmp("reviewStatus").hide();
    						Ext.getCmp("reviewStatus").setValue("");
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					}else if(combo.getValue() == 'EQL_status'){
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("status").enable();
    						Ext.getCmp("status").show();
    						Ext.getCmp("status").setValue();
    						
    						Ext.getCmp("reviewStatus").disable();
    						Ext.getCmp("reviewStatus").hide();
    						Ext.getCmp("reviewStatus").setValue("");
    					
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					
    					}else if (combo.getValue() == 'EQL_reviewStatus') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").hide();
    						Ext.getCmp("itemsValue").setValue("");
    						
    						Ext.getCmp("reviewStatus").enable();
    						Ext.getCmp("reviewStatus").show();
    						Ext.getCmp("reviewStatus").setValue();
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					}else{  //
    						Ext.getCmp("reviewStatus").disable();
    						Ext.getCmp("reviewStatus").hide();
    						Ext.getCmp("reviewStatus").setValue("")
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    				    	Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("comboTypeDepart").disable();
    						Ext.getCmp("comboTypeDepart").hide();
    						Ext.getCmp("comboTypeDepart").setValue("");
    					}
    				}
    			 }
    		},'-','&nbsp;',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
	 	        width : 80,
	 	        selectOnFocus:true,
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
	        },{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 80,
				selectOnFocus:true,
				id:'reviewStatus',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : sourceDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择审核状态"
					
			},{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 80,
				selectOnFocus:true,
				id:'status',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : statusDataStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择状态"
					
			}]
		});
     
	var recordGrid=new Ext.grid.EditorGridPanel({
		renderTo:'recordGrid',
		renderTo:Ext.getBody(),
	//	el : 'recordGrid',
		id:'userCenter',
		height : Ext.lib.Dom.getViewHeight()-1,
		width : Ext.lib.Dom.getViewWidth()-1,
	//	autoScroll : true,
		viewConfig : {
			scrollOffset: 0,
			//forceFit : true,
			autoScroll:true,
			columnsText : "显示的列",
			sortAscText : "升序",
			sortDescText : "降序"
		},
		//autoExpandColumn : 1,
		frame : false,
	//	clicksToEdit:1,
		loadMask : true,
		sm:sm,
		stripeRows : true,
		tbar:['&nbsp;&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'table',
				id : 'exportbtn',
				tooltip : '导出',
				handler : function(){
					parent.exportExl(recordGrid);			
				}
			},'-','&nbsp;&nbsp;',{
				text : '<B>新&nbsp;增</B>',
				id : 'newbtn',
				iconCls:'userAdd',
				handler : function() {
					fiCapitaaccountsetSave();
				}
			},'-','&nbsp;&nbsp;',{
				text : '<B>作&nbsp;废</B>',
				id : 'delbtn',
				disabled : true,
				iconCls:'userDelete',
				handler : function() {
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要提交的数据');
							return false;
						}else if(_records.length >1 ){
							Ext.Msg.alert(alertTitle, '一次只能作废一条数据');
							return false;
						}else{
							deleteStore(_records[0]);
						}
				}
			},'-','&nbsp;',{
				text : '<b>审核</b>',
				iconCls : 'table',
				disabled:false,
				id : 'accountaduit',
				handler : function(){
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_records.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要提交的数据');
							return false;
						}else if(_records.length >1 ){
							Ext.Msg.alert(alertTitle, '一次只能审核一条数据');
							return false;
						}else{
							reviewConfirmation(_records[0]);
						}
				}
			},'-','&nbsp;',{
				text : '<b>撤销审核</b>',
				iconCls : 'table',
				disabled:false,
				id : 'reviewRegister',
				handler : function(){
						var _records = recordGrid.getSelectionModel().getSelections();
						if (_reconnnnnnnnnnnnnnrds.length == 0) {
							Ext.Msg.alert(alertTitle, '请选择一条您需要提交的数据');
							return false;
						}else if(_records.length >1 ){
							Ext.Msg.alert(alertTitle, '一次只能撤销一条数据');
							return false;
						}else{
							reviewRegister(_records[0]);
						}
				}
			},'-','&nbsp;&nbsp;',{
    				     text : '<b>查询</b>',
    				     id : 'btn',
    				     iconCls : 'btnSearch',
    					 handler : searchLog
    				}],
			columns:[ rownum,sm,
        			{header: 'ID', dataIndex: 'id',width:55},
			        {header: '客商名称', dataIndex: 'customerName',width:90},
			        {header: '付款类型', dataIndex: 'settlementType',width:60,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '存款';
			        		}else if(v=='2'){ 
			        			return '取款';
			        		}else{
			        			return '无数据';
			        		}
			        	}
			        },
			         {header: '金额', dataIndex: 'amount',width:60},
			        {header: '结算金额', dataIndex: 'settlementAmount',width:60},
			        {header: '收付状态', dataIndex: 'payStatus',width:60,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '未收付';
			        		}else if(v=='2'){ 
			        			return '已收付';
			        		}else{
			        			return '无数据';
			        		}
			        	}
			        },
			        {header: '审核状态', dataIndex: 'reviewStatus',width:60,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '<font color="#FF0000">未审核</font>';
			        		}else if(v=='2'){ 
			        			return '已审核';
			        		}else{
			        			return '无数据';
			        		}
			        	}
			        },
			        {header: '数据来源', dataIndex: 'sourceData',width:60,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '手工录入';
			        		}else if(v=='2'){ 
			        			return '收付款单';
			        		}else{
			        			return '无数据';
			        		}
			        	}
			        },　
			        {header: '来源单号', dataIndex: 'sourceNo',width:60},
					{header: '账户余额', dataIndex: 'accountNum',width:60},
					{header: '账户名', dataIndex: 'accountName',width:70},
					{header: '开户行', dataIndex: 'bank',width:80},
					{header: '业务部门', dataIndex: 'departName',width:90},
					{header: '部门ID', dataIndex: 'departId',width:60,hidden:true},
			        {header: '结算设置ID', dataIndex: 'fiAdvanceId',width:80,hidden:true},
			        {header: '状态', dataIndex: 'status',width:80,
			        	renderer:function(v){
			        		if(v=='1'){ 
			        			return '正常';
			        		}else{
			        			return '<font color="#FF0000">已作废</font>';
			        		}
			        	}
			        },
			        {header: '备注', dataIndex: 'remark',width:80},
			        {header: '修改人', dataIndex: 'updateName',width:80,hidden:true},
			        {header: '修改时间', dataIndex: 'updateTime',width:80,hidden:true},
			        {header: '创建人', dataIndex: 'createName',width:80,hidden:true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true},
			        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true},
			        {header: '创建部门', dataIndex: 'departName',width:80,hidden:true},
			        {header: '审核人', dataIndex: 'reviewUser',width:80},
			        {header: '审核时间', dataIndex: 'reviewDate',width:80}
			        
			    ],
			store : dataStore,
			listeners: {
                    render: function(){
                        twobar.render(this.tbar);
                    }
            },
			bbar : new Ext.PagingToolbar({
					pageSize : pageSize, 
					store : dataStore,
					displayInfo : true,
					displayMsg : '显示第 {0} 条到 {1} 条记录，共 {2} 条',
					emptyMsg : "没有记录信息显示"
			})
	});

	
	recordGrid.render();
	
	
		function searchLog() {
	    	var start='';
	    	var end ='';
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('comboselect').getValue()==''){
			 	Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LED_createTime : end,
	            	privilege:privilege,
	            	filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
	            	filter_EQL_customerId : Ext.getCmp('cusId').getValue(),
					limit : pageSize
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_status'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LED_createTime : end,
	            	privilege:privilege,
	            	filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
	            	filter_EQL_status:Ext.getCmp('status').getValue(),
	            	filter_EQL_customerId:Ext.getCmp('cusId').getValue(),
					limit : pageSize
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_reviewStatus'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LED_createTime : end,
	            	privilege:privilege,
	            	filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
	            	filter_EQL_reviewStatus:Ext.getCmp('reviewStatus').getValue(),
	            	filter_EQL_customerId:Ext.getCmp('cusId').getValue(),
					limit : pageSize
	   			});
	    	}else{
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LED_createTime : end,
	            	privilege:privilege,
	            	filter_EQL_departId:Ext.getCmp('comboRightDepart2').getValue(),
	            	filter_EQL_customerId:Ext.getCmp('cusId').getValue(),
					checkItems :Ext.getCmp('comboselect').getValue(),
					itemsValue :Ext.getCmp('itemsValue').getValue(),
					limit : pageSize
	   			});
	    	}
			    

			dataStore.reload({
					params : {
						start : 0,
						privilege:privilege,
						limit : pageSize
					}
			});
		
	}
		
		recordGrid.on('click', function() {
			select();
		});
		
		recordGrid.on('rowdblclick',function(grid,index,e){
				var _records = recordGrid.getSelectionModel().getSelections();
				if (_records.length ==1) {
					deleteStore(_records[0]);
				}
		});
		
		
		function select(){
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
			var updatebtn = Ext.getCmp('delbtn');//作废
			var accountaduit = Ext.getCmp('accountaduit');//审核
			var reviewRegister= Ext.getCmp('reviewRegister');//撤销审核
			if (vnetmusicRecord.length == 1&&vnetmusicRecord[0].data.status!=0&&vnetmusicRecord[0].data.payStatus!=2&&vnetmusicRecord[0].data.reviewStatus!=2) {
				updatebtn.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				updatebtn.setDisabled(true);
			} else {
				updatebtn.setDisabled(true);
			}
			
			//审核
			if (vnetmusicRecord.length == 1&&vnetmusicRecord[0].data.reviewStatus==1&&vnetmusicRecord[0].data.status==1) {
				accountaduit.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				accountaduit.setDisabled(true);
			} else {
				accountaduit.setDisabled(true);
			}
			
			//撤销审核
			if (vnetmusicRecord.length == 1&&vnetmusicRecord[0].data.payStatus==1&&vnetmusicRecord[0].data.status==1) {
				reviewRegister.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				reviewRegister.setDisabled(true);
			} else {
				reviewRegister.setDisabled(true);
			}
		}
		
  function fiCapitaaccountsetSave() {
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
							id:'fiAdvanceId',
							name : 'fiAdvanceId'
						}, {
							name : "ts",
							xtype : "hidden"
						}, {
							xtype : 'combo',
							fieldLabel : '所属部门<span style="color:red">*</span>',
							allowBlank : false,
							typeAhead : false,
							forceSelection : true,
							anchor:'92%',
							id:'bussDepart',
							minChars : 1,
							triggerAction : 'all',
							store : departStore,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_departName',
							displayField : 'departName',
							valueField : 'departId',
							name : 'departName',
							hiddenName:'departId',
							blankText : "所属部门为空！",
							emptyText : "请选择所属部门",
							listeners : {
								select:function(combo){
									customerStore2
									customerStore2.proxy=new Ext.data.HttpProxy({
								 		url:sysPath+"/fi/fiAdvancesetAction!ralaList.action",
								 		method:'post'
								 	});
								 	customerStore2.baseParams={
						               privilege:127,
						               filter_EQL_departId:combo.getValue(),
						               filter_EQL_isdelete:1
						            }
						            customerStore2.reader=new Ext.data.JsonReader({
							                     root: 'result', totalProperty:'totalCount'
							                },[ {name:'customerName', mapping:'customerName'},    
							                     {name:'customerId', mapping: 'customerId'}
							            ])
							        customerStore2.load();
								
								}
							}
						},{
							xtype : 'combo',
							typeAhead : false,
							fieldLabel : '客商名称<span style="color:red">*</span>',
							forceSelection : true,
							anchor:'92%',
							allowBlank : false,
							id:'customerId2',
							listWidth:245,
							minChars : 1,
							triggerAction : 'all',
							store : customerStore2,
							pageSize : comboxPage,
							queryParam : 'filter_LIKES_customerName',
							displayField : 'customerName',
							valueField : 'customerId',
							name : 'customerName',
							hiddenName:'customerId',
							emptyText : "请选择客商名称",
							listeners : {
								'focus' : function(combo, record, index) {
									var bus=Ext.getCmp('bussDepart');
									if(bus.getValue()==''){
										Ext.Msg.alert(alertTitle,"在选择客商名称前，请先选择所属部门",function(){
											combo.setValue('');
											bus.markInvalid("在选择客商名称前，请先选择所属部门");
											bus.focus();
										});
									}
								},
								select:function(combo){
									Ext.Ajax.request({
			 							url:sysPath+"/fi/fiAdvancesetAction!ralaList.action",
										params:{
											privilege:127,
											filter_EQL_departId:Ext.getCmp('bussDepart').getValue(),
											filter_EQL_customerId:Ext.getCmp('customerId2').getValue()
										},
										success : function(response) { // 回调函数有1个参数
											if(Ext.decode(response.responseText).result.length!=0){
												Ext.getCmp('accountNum').setValue(Ext.decode(response.responseText).result[0].accountNum);
												Ext.getCmp('accountName').setValue(Ext.decode(response.responseText).result[0].accountName);
												Ext.getCmp('bank').setValue(Ext.decode(response.responseText).result[0].bank);
												Ext.getCmp('openingBalance').setValue(Ext.decode(response.responseText).result[0].openingBalance);
												Ext.getCmp('fiAdvanceId').setValue(Ext.decode(response.responseText).result[0].id);
											}else{
												Ext.Msg.alert(alertTitle,"此客商的预付款账号记录不存在，请在账号设置模块录入",function(){
													combo.setValue('');
													combo.markInvalid("此客商的预付款账号记录不存在，请在账号设置模块录入");
													combo.focus();
												});
											
											}
										},
										failure : function(response) {
											
										}
									});	
								
								}
							}
						}, 
						 {
							xtype : 'numberfield',
							fieldLabel : '账号',
							name : 'accountNum',
							id:'accountNum',
							maxLength : 50,
							disabled:true,
							allowBlank : false,
							anchor:'92%'
						}, {
							xtype : 'textfield',
							fieldLabel : '账户名称',
							name : 'accountName',
							id:'accountName',
							maxLength : 20,
							disabled:true,
							allowBlank : false,
							anchor:'92%'
						}, {
							xtype : 'textfield',
							fieldLabel : '开户行名称',
							name : 'bank',
							id:'bank',
							disabled:true,
							allowBlank : false,
							maxLength : 50,
							anchor:'92%'
						}, {
							xtype : 'numberfield',
							fieldLabel : '余额',
							name : 'openingBalance',
							id:'openingBalance',
							maxLength : 50,
							disabled:true,
							allowBlank : false,
							anchor:'92%'
						},{xtype: 'radiogroup',
			                fieldLabel: '结算类型<span style="color:red">*</span>',
			                id:"settlementType",
			                columns: 3,
			                defaults: {
			                    name: 'settlementType' 
			                },
			                listeners : {
								change:function(c){
									var combo=Ext.getCmp('settlementAmount');
									var va =Ext.getCmp('openingBalance').getValue();
									if(combo.getValue()!=''){
										if(form.getForm().getValues()["settlementType"]==2){
											if(parseFloat(va)<parseFloat(combo.getValue())){
												Ext.Msg.alert(alertTitle,"取出的金额不能比余额大，请更改",function(){
													combo.selectText();
													combo.markInvalid("取出的金额不能比余额大，请更改");
													combo.focus();
												});
											}
										
										}
									}
								}
							},
			                items: [{
			                    inputValue: '1',
			                    boxLabel: '存款',
			                   	checked:true
			                }, {
			                    inputValue: '2',
			                    boxLabel: '取款'
			                }]
						},{
							xtype : 'numberfield',
							fieldLabel : '金额<span style="color:red">*</span>',
							name : 'amount',
							id:'amount',
							maxLength : 50,
							allowBlank : false,
							anchor:'92%',
							listeners : {
								blur:function(combo){
									var va =Ext.getCmp('openingBalance').getValue();
									if(form.getForm().getValues()["settlementType"]==2){
										if(parseFloat(va)<parseFloat(combo.getValue())){
											Ext.Msg.alert(alertTitle,"取出的金额不能比余额大，请更改",function(){
												combo.selectText();
												combo.markInvalid("取出的金额不能比余额大，请更改");
												combo.focus();
											});
										}
									
									}
								}
							}
						},{
							xtype : 'textfield',
							fieldLabel : '来源类型',
							name : 'uu',
							value:'手工录入',
							disabled:true,
							maxLength : 500,
							anchor:'92%'
						},{
							xtype : 'textfield',
							fieldLabel : '备注',
							name : 'remark',
							maxLength : 500,
							anchor:'92%'
						}]

			}]
		}]
	});

	var win = new Ext.Window({
		title : "新增预付结算清单",
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
						url : sysPath + '/' + saveUrl,
						params : {
							customerName:Ext.getCmp('customerId2').getRawValue(),
							sourceData:1,
							privilege : privilege
						},
						waitMsg : '正在保存数据...',
						success : function(form, action) {
							win.hide();
						 	Ext.Msg.alert( alertTitle,action.result.msg, function() {
										dataStore.reload();
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
												dataStore.reload();
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

//作废
function deleteStore(_record){
	if(_record.data.status!='1'){
		Ext.Msg.alert(alertTitle,"数据已作废，不能重复操作",
								function() {
									
								});
		return;
	}
	Ext.Msg.confirm(alertTitle,'您确定要作废这行数据吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url : sysPath+ "/fi/fiAdvancebpAction!addRegister.action",
					method : 'post',
					params : {
						privilege:privilege,
						id:_record.data.id
					},
					waitMsg : '正在作废数据...',
					success : function(form1, action) {
						Ext.Msg.alert(
								alertTitle,
								action.result.msg,
								function() {
									dataStore.reload();
								});
					},
					failure : function(form1, action) {
						Ext.Msg.alert(
								alertTitle,
								action.result.msg,
								function() {
									dataStore.reload();
									select();
								});
					}
				});
		 }
	});
}



});

//审核
function reviewConfirmation(records){
	var id=records.data.id;
	var ts=records.data.ts;
    Ext.Msg.confirm("友情提示",'<span  style="color:red">您确实要审核吗?</span>',function(btnYes) {
    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + reviewConfirmationUrl,
						params : {
							id : id,
							ts : ts,
							privilege : privilege
						},
						waitMsg : '正在审核数据...',
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg,
								function() {dateStore.reload();});
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
			
						}
					});
		}
	});
}

//撤销审核
function reviewRegister(records){
	var id=records.data.id;
	var ts=records.data.ts;
    Ext.Msg.confirm("友情提示",'<span  style="color:red">您确实要撤销审核吗?</span>',function(btnYes) {
    	if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
			Ext.Ajax.request({
						url : sysPath + "/" + reviewRegisterUrl,
						params : {
							id : id,
							ts : ts,
							privilege : privilege
			
						},
						waitMsg : '正在审核数据...',
						success : function(resp) {
							var respText = Ext.util.JSON.decode(resp.responseText);
							if (respText.success) {
								Ext.Msg.alert(alertTitle, respText.msg,
								function() {dateStore.reload();});
							} else {
								Ext.Msg.alert(alertTitle, respText.msg);
							}
			
						}
					});
		}
	});
}


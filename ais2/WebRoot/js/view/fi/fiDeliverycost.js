	Ext.QuickTips.init();
	var privilege=137;
	var comboxPage=50;
	var departGridSearchUrl = "sys/departAction!findAll.action";
	var saveUrl="fi/fiAdvanceAction!save.action";
	var customerGridSearchUrl="sys/customerAction!list.action";
	var areaListUrl="stock/oprStoreAreaAction!list.action";
	var ralaListUrl="fi/fiDeliverycostAction!ralaList.action";
	var customerGridSearchUrl = "sys/customerAction!list.action";
	var dictionaryUrl='sys/dictionaryAction!ralaList.action'; 
	
	
Ext.onReady(function(){
	Ext.QuickTips.init();
	var rownum=new Ext.grid.RowNumberer({
		header:'No.',
        width : 25,
		sortable : true
	});
		
	var fields=[
	'id',
	'faxMainNo',
	'matStatus',
	'flightMainNo',
	'customerId',
	'customerName',
	'faxPiece',
	'flightPiece',
	'faxWeight',
	'flightWeight',
	'flightAmount',
	'boardAmount',
	'amount','payTime',
	'price','payStatus',
	'isLowestStatus',
	'remark',
	'batchNo',
	'diffWeight',
	'diffAmount',
	'startcity',
	'status',
	'goodsType',
	'createName',
	'createTime',
	'departId',
	'departName',
	'reviewDept',
	'reviewUser',
	'reviewDate',
	'reviewRemark',
	'ts',
	'feeType'];
    			
    var jsonread= new Ext.data.JsonReader({
                    root:'result',
                    totalProperty:'totalCount'},
                    fields);
                              
	var sm = new Ext.grid.CheckboxSelectionModel({
		width:25,
		//singleSelect :true,
		moveEditorOnEnter:true, 
		id:'checkId',
		name:'checkId'
	});
 
 	goodsTypeStore= new Ext.data.Store({
		storeId : "goodsTypeStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/fi/fiDeliveryPriceAction!list.action",
					method:'post'
				}),
		baseParams:{
			filter_EQL_departId:bussDepart
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'goodsType',
							mapping : 'goodsType'
						}, {
							name : 'id',
							mapping : 'id'
						}])
	});
 
		// 客商列表
	customerStore = new Ext.data.Store({
		storeId : "customerStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl,
					method:'post'
				}),
		baseParams:{
			filter_EQS_custprop:'提货公司'
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'cusName',
							mapping : 'cusName'
						}, {
							name : 'id',
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
	
	// 客商列表
	searchCusNameStore = new Ext.data.Store({
		storeId : "searchCusNameStore",
		proxy : new Ext.data.HttpProxy({
					url : sysPath + "/" + customerGridSearchUrl,
					method:'post'
				}),
		baseParams:{
			filter_EQS_custprop:'提货公司'
		},
		reader : new Ext.data.JsonReader({
					root : 'result',
					totalProperty : 'totalCount'
				}, [{
							name : 'cusName',
							mapping : 'cusName'
						}, {
							name : 'id',
							mapping : 'id'
						}])
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
		
	var sourceDataStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已审核'],['0','未审核']],
   		fields:["id","name"]
	});
	
	var autoStatus=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','自动匹配'],['2','手工匹配'],['3','多对一匹配'],['0','未匹配']],
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
 	
 	var payStatusStore=new Ext.data.SimpleStore({
		auteLoad:true, //此处设置为自动加载
  	  	data:[['1','已支付'],['0','未支付']],
   		fields:["id","name"]
	});
 	
 	var twobar = new Ext.Toolbar({
		    	items : ['&nbsp;','日期:',
						 {
							xtype : 'datefield',
			    			id : 'startDate',
				    		format : 'Y-m-d',
				    		emptyText : "选择开始时间",
				    		anchor : '95%',
				    		width : 100,
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
				    		format : 'Y-m-d',
				    		emptyText : "选择结束时间",
				    		width : 100,
				    		anchor : '95%'
						},'-','&nbsp;','创建部门:',{
							xtype : 'combo',
							id:'comboRightDepart2',
							hiddenId : 'dictionaryName',
				   			hiddenName : 'olist[0].rightDepartId',
							triggerAction : 'all',
							store : rightDepartStore2,
							mode:'local',
							width : 100,
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
								 afterRender:function(combo){
								 		
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
					    },'-','&nbsp;','提货处:',  //
							{
							xtype : 'combo',
							id:'comboTypeDepart',
							triggerAction : 'all',
							store : searchCusNameStore,
							width : 100,
							queryParam : 'filter_LIKES_cusName',
							listWidth:245,
							minChars : 1,
							allowBlank : true,
							emptyText : "请选择提货处名称",
							forceSelection : true,
							editable : true,
							pageSize:comboxPage,
							displayField : 'cusName',//显示值，与fields对应
							valueField : 'id',//value值，与fields对应
							name : 'cusId',
							anchor : '100%'
					    },'-','&nbsp;',
			{	xtype:'textfield',
	 	        id : 'itemsValue',
	 	        width : 100,
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
				width : 100,
				id:'status',
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
				width : 100,
				id:'statuspi',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : autoStatus,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择匹配状态"
					
			},{
				xtype : 'combo',
				typeAhead : false,
				forceSelection : true,
				width : 100,
				id:'payStatus',
				hidden:true,
				diabled:true,
				mode:'local',
				triggerAction : 'all',
				store : payStatusStore,
				displayField : 'name',
				valueField : 'id',
				emptyText : "请选择匹配状态"
					
			},'-',{
                xtype : "combo",
    			id:"comboselect",
   				width : 100,
 				triggerAction : 'all',
    			model : 'local',
    			hiddenId : 'checkItems',
    			hiddenName : 'checkItems',
    			name : 'checkItemstext',
    			store : [['', '查询全部'],
    					['LIKES_flightMainNo', '黄单号'],
    					['LIKES_faxMainNo', '主单号'],
    					['LIKES_startcity', '始发站'],
    					['EQL_status', '审核状态'],
    					['LIKES_batchNo', '批次号'],
    					['EQL_payStatus', '支付状态'],
    					['EQL_matStatus', '匹配状态']],
    			emptyText : '选择查询类型',
    			forceSelection : true,
    			listeners : {
    				'select' : function(combo, record, index) { 
    					if (combo.getValue() == '') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").show();
    						
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("statuspi").disable();
    						Ext.getCmp("statuspi").hide();
    						Ext.getCmp("statuspi").setValue("");
    			
    						Ext.getCmp("payStatus").disable();
    						Ext.getCmp("payStatus").hide();
    						Ext.getCmp("payStatus").setValue("");
    			
    					}else if(combo.getValue() == 'EQL_status'){
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						
    						Ext.getCmp("statuspi").disable();
    						Ext.getCmp("statuspi").hide();
    						Ext.getCmp("statuspi").setValue("");
    						
    						Ext.getCmp("status").enable();
    						Ext.getCmp("status").show();
    						Ext.getCmp("status").setValue();
    						
    						Ext.getCmp("payStatus").disable();
    						Ext.getCmp("payStatus").hide();
    						Ext.getCmp("payStatus").setValue("");
    					}else if (combo.getValue() == 'EQL_matStatus') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    								
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("statuspi").enable();
    						Ext.getCmp("statuspi").show();
    						Ext.getCmp("statuspi").setValue();
    						
    						Ext.getCmp("payStatus").disable();
    						Ext.getCmp("payStatus").hide();
    						Ext.getCmp("payStatus").setValue("");
    					}else if (combo.getValue() == 'EQL_payStatus') {
    						Ext.getCmp("itemsValue").disable();
    						Ext.getCmp("itemsValue").setValue("");
    						Ext.getCmp("itemsValue").hide();
    						
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    						Ext.getCmp("statuspi").disable();
    						Ext.getCmp("statuspi").hide();
    						Ext.getCmp("statuspi").setValue("");
    						
    						Ext.getCmp("payStatus").enable();
    						Ext.getCmp("payStatus").show();
    						Ext.getCmp("payStatus").setValue();
    						
    					}else{  //
    						Ext.getCmp("payStatus").disable();
    						Ext.getCmp("payStatus").hide();
    						Ext.getCmp("payStatus").setValue("");
    					
    						Ext.getCmp("status").disable();
    						Ext.getCmp("status").hide();
    						Ext.getCmp("status").setValue("");
    						
    				    	Ext.getCmp("itemsValue").enable();
    						Ext.getCmp("itemsValue").show();
    						
    						Ext.getCmp("statuspi").disable();
    						Ext.getCmp("statuspi").hide();
    						Ext.getCmp("statuspi").setValue("");
    					}
    				}
    			 }
    		}]
		});
     
     function searchLog() {
	      	var start='';
	    	var end ='';
	    	var deId = Ext.getCmp('comboRightDepart2').getValue();
	    	if(Ext.getCmp('startDate').getValue()!=""){
	    		var start = Ext.getCmp('startDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('endDate').getValue()!=""){
	    		var end = Ext.getCmp('endDate').getValue().format("Y-m-d");
	    	}
	    	if(Ext.getCmp('comboselect').getValue()==''){
			 	Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	privilege:privilege,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_matStatus'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	privilege:privilege,
	            	filter_EQL_matStatus:Ext.getCmp('statuspi').getValue(),
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_status'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	privilege:privilege,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
	            	filter_EQL_status:Ext.getCmp('status').getValue()
	   			});
	    	}else if(Ext.getCmp('comboselect').getValue()=='EQL_payStatus'){
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
	            	filter_EQL_payStatus:Ext.getCmp('payStatus').getValue()
	   			});
	    	}else{
	    		Ext.apply(dataStore.baseParams={
					filter_GED_createTime : start,
	            	filter_LTD_createTime : end,
	            	filter_EQL_departId:deId,
	            	privilege:privilege,
	            	filter_EQL_customerId : Ext.getCmp('comboTypeDepart').getValue(),
					checkItems :Ext.getCmp('comboselect').getValue(),
					itemsValue :Ext.getCmp('itemsValue').getValue()
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
		tbar:['&nbsp;',{
				text : '<b>新增</b>',
				iconCls : 'userAdd',
				id : 'costin',
				handler : function(){
					 addData(null);
				}
			},'-','&nbsp;',
			{
				text : '<b>修改</b>',
				iconCls : 'userEdit',
				id : 'updateAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
						Ext.Msg.alert(alertTitle,'请选择一条未审核过的数据进行操作');
					}else{
						if(vnetmusicRecord[0].get('status')=='0'&&vnetmusicRecord[0].get('matStatus')=='0'){
							addData(vnetmusicRecord[0]);
						}else{
							Ext.Msg.alert(alertTitle,'未审核未匹配过的数据才能进行成本修改操作');
							return;
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>删除</b>',
				iconCls : 'userDelete',
				id : 'deleteAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length != 1) {
						Ext.Msg.alert(alertTitle,'请选择一条未审核的数据再进行删除操作');
					}else{
						if(vnetmusicRecord[0].get('status')=='0'){
							deleteCost(vnetmusicRecord[0]);
						}else{
							Ext.Msg.alert(alertTitle,'审核过的数据不能进行删除操作，必须先做审核撤消操作');
							return;
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>手工匹配</b>',
				iconCls : 'table',
				id : 'carAduit',
				handler : function(){
					var node=new Ext.tree.TreeNode({id:'keel',leaf :false,text:"手工匹配"});
		      		node.attributes={href1:'fi/fiDeliverycostAction!show.action'};
		       		parent.toAddTabPage(node,true);
				}
			},'-','&nbsp;',
			{
				text : '<b>取消匹配</b>',
				iconCls : 'groupClose',
				id : 'changeAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length == 0) {
						Ext.Msg.alert(alertTitle,'请选择您需要取消匹配的数据进行操作');
					}else{
	
						if(vnetmusicRecord[0].get('status')=='0'){
							if(vnetmusicRecord[0].get('matStatus')=='0'||vnetmusicRecord[0].get('matStatus')==''){
								Ext.Msg.alert(alertTitle,'只有已匹配过的数据才能进行取消手工匹配操作。');
								return;
							}else{
							 qxAduit(vnetmusicRecord);
							}
						}else{
							Ext.Msg.alert(alertTitle,'只有未审核过的数据才能进行取消手工匹配操作。');
							return;
						}

					}
				}
			},'-','&nbsp;',
			{
				text : '<b>会计审核</b>',
				iconCls : 'groupPass',
				id : 'fiAduit',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length == 0) {
						Ext.Msg.alert(alertTitle,'请选择一条记录进行会计审核');
					}else{
						var customerId=vnetmusicRecord[0].get('customerId');
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(customerId!=vnetmusicRecord[i].get('customerId')){
								Ext.Msg.alert(alertTitle,'请选择同一个提货公司的数据进行审核');
								return;
							}
							if(vnetmusicRecord[i].get('matStatus')=='0'){
								Ext.Msg.alert(alertTitle,'黄单号为'+vnetmusicRecord[i].get('flightMainNo')+'的记录没有匹配主单数据，不能审核');
								return;
							}else{
								if(vnetmusicRecord[i].get('status')=='1'){
									Ext.Msg.alert(alertTitle,'主单号为'+vnetmusicRecord[i].get('faxMainNo')+'的记录已审核，不能重复审核');
									return;
								}else{
								
									fiAudit(vnetmusicRecord);
								
								}
							}
						}
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>撤销审核</b>',
				iconCls : 'groupNotPass',
				id : 'deleteFi',
				handler : function(){
					var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			
					if (vnetmusicRecord.length ==0 ) {
						Ext.Msg.alert(alertTitle,'请选择一条或多条记录进行撤销会计审核');
					}else{
						var batchNo=vnetmusicRecord[0].get('batchNo');
						for(var i=0;i<vnetmusicRecord.length;i++){
							if(batchNo!=vnetmusicRecord[i].get('batchNo')){
								Ext.Msg.alert(alertTitle,'同一批次号的数据才能进行撤销会计审核');
								return;
							}
		
							if(vnetmusicRecord[i].get('status')!='1'){
								Ext.Msg.alert(alertTitle,'黄单号为'+vnetmusicRecord[i].get('flightMainNo')+'的记录没有进行会计审核，不能进行审核撤销');
								return;
							}
						}
						qxFiAudit(vnetmusicRecord);
					}
				}
			},'-','&nbsp;',
			{
				text : '<b>账单对账</b>',
				iconCls : 'outExcel',
				id : 'doOutDan',
				handler : function(){
					showExcel();						
				}
			},'-','&nbsp;',{
				text : '<b>导出</b>',
				iconCls : 'sort_down',
				id : 'exportbtn',
				handler : exportinfo
			},'-','&nbsp;',{
		     text : '<b>查询</b>',
		     id : 'btn',
		     iconCls : 'btnSearch',
			 handler : searchLog
    		}],
			columns:[ rownum,sm,
        			{header: '编号', dataIndex: 'id',width:55,sortable : true},
			        {header: '黄单号', dataIndex: 'flightMainNo',width:70,sortable : true},
			        {header: '主单号', dataIndex: 'faxMainNo',width:70,sortable : true},
			        {header: '匹配状态', dataIndex: 'matStatus',width:60,sortable : true,
				       		 renderer:function(v){
				        		if(v=='1'){ 
				        			return '自动匹配';
				        		}else if(v=='2'){
				        			return '手工匹配';
				        		}else if(v=='0'){
				        			return '未匹配';
				        		}else if(v=='3'){
				        			return '多票匹配';
				        		}else{
				        			return v;
				        		}
				        	}
				     },
			        {header: '客商ID', dataIndex: 'customerId',width:70,hidden:true,sortable : true},
			        {header: '客商名称', dataIndex: 'customerName',width:80,sortable : true},　
					{header: '黄单件数', dataIndex: 'flightPiece',width:70,sortable : true},
					{header: '黄单重量', dataIndex: 'flightWeight',width:70,sortable : true},					
					{header: '黄单金额', dataIndex: 'flightAmount',width:70,sortable : true},			        
			        {header: '单价', dataIndex: 'price',width:70,sortable : true},
			        {header: '货物类型', dataIndex: 'goodsType',width:70,sortable : true},
					{header: '板费', dataIndex: 'boardAmount',width:70,sortable : true},
			        {header: '总金额', dataIndex: 'amount',width:70,sortable : true},  
			        {header: '主单表件数', dataIndex: 'faxPiece',width:70,sortable : true},
					{header: '主单表重量', dataIndex: 'faxWeight',width:70,sortable : true},
			        {header: '结算方式', dataIndex: 'feeType',width:70,sortable : true},
			      /*  {header: '是否最低一票', dataIndex: 'isLowestStatus',width:70,sortable : true,
			        		 renderer:function(v){
				        		if(v=='1'){ 
				        			return '是';
				        		}else if(v=='0'){
				        			return '否';
				        		}else{
				        			return '';
				        		}
				        	}},*/
			        {header: '重量差异', dataIndex: 'diffWeight',width:80,sortable : true},
			        {header: '金额差异', dataIndex: 'diffAmount',width:80,sortable : true},
			        
			       	{header: '始发站', dataIndex: 'startcity',width:80,sortable : true},
			       	{header: '审核状态', dataIndex: 'status',width:80,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '已审核';
				        		}else if(v=='0'){
				        			return '未审核';
				        		}else{
				        			return '';
				        		}
				        	}},
				    {header: '批次号', dataIndex: 'batchNo',width:80,sortable : true},
					{header: '支付状态', dataIndex: 'payStatus',width:80,sortable : true,renderer:function(v){
				        		if(v=='1'){ 
				        			return '已支付';
				        		}else if(v=='0'){
				        			return '未支付';
				        		}else{
				        			return '';
				        		}
				        	}},
				    {header: '支付时间', dataIndex: 'payTime',width:80,sortable : true},
			       	{header: '创建人', dataIndex: 'createName',width:80,hidden:true,sortable : true},
			        {header: '创建时间', dataIndex: 'createTime',width:80,hidden:true,sortable : true},
			        {header: '创建部门ID', dataIndex: 'departId',width:80,hidden:true,sortable : true},
			        {header: '创建部门', dataIndex: 'departName',width:80,hidden:true,sortable : true},
			        {header: '审核部门', dataIndex: 'reviewDept',width:60,hidden:true,sortable : true},
			        {header: '审核人', dataIndex: 'reviewUser',width:60,hidden:true,sortable : true},
			        {header: '审核时间', dataIndex: 'reviewDate',width:60,hidden:true,sortable : true},
			        {header: '审核备注', dataIndex: 'reviewRemark',width:60,hidden:true,sortable : true},
			        {header: '时间戳', dataIndex: 'ts',width:55,hidden:true,sortable : true},  
			        {header: '备注', dataIndex: 'remark',width:80,sortable : true}
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
		
		
		recordGrid.on('click', function() {
			select();
		});
			
		function select(){ /*
			var vnetmusicRecord = recordGrid.getSelectionModel().getSelections();
			var costin = Ext.getCmp('costin');
			var fiAduit = Ext.getCmp('fiAduit');
			var carAduit = Ext.getCmp('carAduit');
			var exportbtn = Ext.getCmp('exportbtn');
			var printbtn = Ext.getCmp('printbtn');

			if (vnetmusicRecord.length == 1) {
				if(vnetmusicRecord[0].get('status')=='1'){
					costin.setDisabled(false);
				}else{
					costin.setDisabled(true);
				}
				
				if(vnetmusicRecord[0].get('status')=='2'){
					carAduit.setDisabled(false);
				}else{
					carAduit.setDisabled(true);
				}
				
				if(vnetmusicRecord[0].get('status')=='3'){
					fiAduit.setDisabled(false);
				}else{
					fiAduit.setDisabled(true);
				}
				
				exportbtn.setDisabled(false);
				printbtn.setDisabled(false);
			} else if (vnetmusicRecord.length > 1) {
				costin.setDisabled(true);
				fiAduit.setDisabled(true);
				carAduit.setDisabled(true);
				exportbtn.setDisabled(true);
				printbtn.setDisabled(true);
			} else {
				costin.setDisabled(true);
				fiAduit.setDisabled(true);
				carAduit.setDisabled(true);
				exportbtn.setDisabled(true);
				printbtn.setDisabled(true);
			}*/
		}
		
		function addData(record) {
			var flag=false;
			var board1=0;
			var board2=0;
			var board3=0;
			var board4=0;
			var board5=0;
			var board6=0;
			var flag2='';
					 var form = new Ext.form.FormPanel({
								labelAlign : 'left',
								frame : true,
							//	url:sysPath+'/user/userAction!ralaList.action?privilege=17',
							
								bodyStyle : 'padding:5px 5px 5px',
							    width : 600,
							    labelWidth : 70,
								reader :jsonread,
					            labelAlign : "right",
						        items : [{
											layout : 'column',
											items : [{
														columnWidth : .5,
														layout : 'form',
														items : [{	id:"id",
																	name : "id",
																	xtype : "hidden"
																},{
																	name : "ts",
																	xtype : "hidden"
																},{
																   xtype : 'combo',
										        				   fieldLabel: '提货公司<span style="color:red">*</span>',
														           allowBlank : false,
														           queryParam : 'filter_LIKES_cusName',
															       minChars : 1,
															       triggerAction : 'all',
																   typeAhead:false,
															       forceSelection : true,
																   store: customerStore,
																   pageSize : 50,
																   id:'customer',
																   hiddenName:'customerId',
																   hiddenId:'cuds',
																   displayField : 'cusName',
																   valueField : 'id',
																   blankText : "提货公司不能为空！",
																   anchor : '95%',
																   emptyText : "请选择提货公司",
																   enableKeyEvents:true,
																   listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
																				Ext.getCmp('flightMainNo').focus();
																				Ext.getCmp('flightMainNo').selectText();
														                 	}
														 				},
																		'select':function(v){
																			 if(v.getValue()!=''){
																			 	Ext.getCmp('showMsg2').getEl().update('<span style="color:red"></span>');
															
																				goodsTypeStore.baseParams={
																	             	filter_EQL_customerId:v.getValue(),
																	             	filter_EQL_departId:bussDepart,
																	             	filter_EQL_isdelete:1
																	            }
																				goodsTypeStore.load();
																				
																				if(Ext.getCmp('goodsType').getValue()!=''){
																				 	Ext.Ajax.request({
															 							url:sysPath+"/fi/fiDeliveryPriceAction!list.action",
																						params:{
																							filter_EQL_customerId:v.getValue(),
																							filter_EQS_goodsType:Ext.getCmp('goodsType').getValue(),
																							filter_EQL_departId:bussDepart,
																							filter_EQL_isdelete:1
																						},
																						success : function(response) { // 回调函数有1个参数
																							if(Ext.decode(response.responseText).result.length==1){
																								var str=v.getRawValue()+' ';
																								if(Ext.decode(response.responseText).result[0].lowest==0){
																									str+='无最低一票';
																								}else{
																									str+='最低一票：'+Ext.decode(response.responseText).result[0].lowest;
																									flag2=Ext.decode(response.responseText).result[0].lowest;
																								}
																								if(Ext.decode(response.responseText).result[0].isBoardStatus==0){
																									str+='，无板费';
																									Ext.getCmp('boardAmount').setValue(0);
																									Ext.getCmp('flightWeight').setValue(0);
																								}else{
																									str+='，需要交纳板费';
																									flag=true;
																									board1=Ext.decode(response.responseText).result[0].board1;
																									board2=Ext.decode(response.responseText).result[0].board2;
																									board3=Ext.decode(response.responseText).result[0].board3;
																									board4=Ext.decode(response.responseText).result[0].board4;
																									board5=Ext.decode(response.responseText).result[0].board5;
																									board6=Ext.decode(response.responseText).result[0].board6;
																									Ext.getCmp('boardAmount').setValue(0);
																									Ext.getCmp('flightWeight').setValue(0);
																								}
																								Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+str+'</span>');
																								//Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
																								Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
																							}else{
																								Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+v.getRawValue()+'的协议价不存在，请在提货协议价管理模块新增</span>');
																								//v.selectText();
																								v.setValue('');
																								v.focus();
																							}
																						},
																						failure : function(response) {
																							Ext.getCmp('showMsg2').getEl().update('<span style="color:red">提货公司协议价查询失败</span>');
																							v.focus();
																						}
																					});	
																				}
																			 }												                   
														 				 }
																	}
										        				},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '始发站<span style="color:red">*</span>',
																	name : 'startcity',
																	id:'startcity',
																	maxLength:50,
																	allowBlank : false,
																	blankText : "始发站不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,
																   	listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
																				Ext.getCmp('flightPiece').focus();
																				Ext.getCmp('flightPiece').selectText();
														                 	}
														 				}
														 			}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '计费重量<span style="color:red">*</span>',
																	name : 'flightWeight',
																	id:'flightWeight',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "计费重量不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,
																	listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
																				Ext.getCmp('remark').focus();
																				Ext.getCmp('remark').selectText();
														                 	}
														 				},
																		'blur':function(vs){
																			var cv=parseFloat(vs.getValue().toFixed(2));
																			vs.setValue(cv);
																			var v=Ext.getCmp('boardAmount');
																			if(flag){
																				if(cv<=100){
																					v.setValue(0);
																				}else if(100<cv&&cv<=200){
																					v.setValue(board1);
																				}else if(200<cv&&cv<=300){
																					v.setValue(board2);
																				}else if(300<cv&&cv<=400){
																					v.setValue(board3);
																				}else if(400<cv&&cv<=500){
																					v.setValue(board4);
																				}else if(500<cv&&cv<=1000){
																					v.setValue(board5);
																				}else if(cv>1000){
																					v.setValue(board6);
																				}
																				if(flag2!=''){
																					if(parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())<parseFloat(flag2)){
																						var vf=parseFloat(flag2);
																						Ext.getCmp('flightAmount').setValue(vf.toFixed(2));
																						Ext.getCmp('amount').setValue((parseFloat(flag2)+v.getValue()).toFixed(2));
																					}else{
																						Ext.getCmp('flightAmount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())).toFixed(2));
																						Ext.getCmp('amount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())+v.getValue()).toFixed(2));
																					}
																				}else{
																					Ext.getCmp('flightAmount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())).toFixed(2));
																					Ext.getCmp('amount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())+v.getValue()).toFixed(2));
																				}
																					
																			}else{
																				Ext.getCmp('flightAmount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())).toFixed(2));
																				Ext.getCmp('amount').setValue((parseFloat(vs.getValue())*parseFloat(Ext.getCmp('price').getValue())).toFixed(2));
																			
																			}
																		}
																	}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '单价',
																	name : 'price',
																	id:'price',
																	readOnly:true,
																	maxLength:12,
																	allowBlank : false,
																	blankText : "单价不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '黄单总金额',
																	name : 'amount',
																	id:'amount',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "金额不能为空！",
																	anchor : '95%'
																}/*,{xtype: 'radiogroup',
													                fieldLabel: '最低一票<span style="color:red">*</span>',
													                id:"isLowestStatus",
													                defaults: {
													                    name: 'isLowestStatus' 
													                },
													                items: [{
													                    inputValue: '0',
													                    boxLabel: '否',
													                    checked:true
													                }, {
													                    inputValue: '1',
													                    boxLabel: '是'
													                 }]
																}*/]

													}, {
														columnWidth : .5,
														layout : 'form',
														items : [{
																   xtype : 'combo',
										        				   fieldLabel: '货物类型<span style="color:red">*</span>',
														           allowBlank : false,
															       triggerAction : 'all',
															       forceSelection : true,
																   store: goodsTypeStore,
																   pageSize : 50,
																   name:'goodsType',
																   id:'goodsType',
																   displayField : 'goodsType',
																   valueField : 'goodsType',
																   blankText : "货物类型不能为空！",
																   anchor : '95%',
																   emptyText : "请选择货物类型",
																   enableKeyEvents:true,
																   listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
//																				Ext.getCmp('flightMainNo').focus();
//																				Ext.getCmp('flightMainNo').selectText();
														                 	}
														 				},
														 				focus:function(n){
														                	if(Ext.getCmp('customer').getValue()==''){
																				Ext.Msg.alert(alertTitle,"请先选择提货公司，再选择货物类型",function(){
																					n.blur();
																					Ext.getCmp('customer').focus();
																				});
														                 	}
														 				},
																		'select':function(v){
																			 if(v.getValue()!=''&&Ext.getCmp('customer').getValue()!=''){
																			 	Ext.getCmp('showMsg2').getEl().update('<span style="color:red"></span>');
																			 	Ext.Ajax.request({
														 							url:sysPath+"/fi/fiDeliveryPriceAction!list.action",
																					params:{
																						filter_EQL_customerId:Ext.getCmp('customer').getValue(),
																						filter_EQS_goodsType:v.getValue(),
																						filter_EQL_departId:bussDepart,
																						filter_EQL_isdelete:1
																					},
																					success : function(response) { // 回调函数有1个参数
																						if(Ext.decode(response.responseText).result.length==1){
																							var str=Ext.getCmp('customer').getRawValue()+v.getRawValue()+' ';
																							if(Ext.decode(response.responseText).result[0].lowest==0){
																								str+='无最低一票';
																							}else{
																								str+='最低一票：'+Ext.decode(response.responseText).result[0].lowest;
																								flag2=Ext.decode(response.responseText).result[0].lowest;
																							}
																							if(Ext.decode(response.responseText).result[0].isBoardStatus==0){
																								str+='，无板费';
																								Ext.getCmp('boardAmount').setValue(0);
																								Ext.getCmp('flightWeight').setValue(0);
																							}else{
																								str+='，需要交纳板费';
																								flag=true;
																								board1=Ext.decode(response.responseText).result[0].board1;
																								board2=Ext.decode(response.responseText).result[0].board2;
																								board3=Ext.decode(response.responseText).result[0].board3;
																								board4=Ext.decode(response.responseText).result[0].board4;
																								board5=Ext.decode(response.responseText).result[0].board5;
																								board6=Ext.decode(response.responseText).result[0].board6;
																								Ext.getCmp('boardAmount').setValue(0);
																								Ext.getCmp('flightWeight').setValue(0);
																							}
																							Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+str+'</span>');
																							//Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
																							Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
																						}else{
																							Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+Ext.getCmp('customer').getRawValue()+v.getRawValue()+'的协议价不存在，请在提货协议价管理模块新增</span>');
																							//v.selectText();
																							v.setValue('');
																							v.focus();
																						}
																					},
																					failure : function(response) {
																						Ext.getCmp('showMsg2').getEl().update('<span style="color:red">提货公司协议价查询失败</span>');
																						v.focus();
																					}
																				});	
																			 }												                   
														 				 }
																	}
										        				},{
																	xtype : 'textfield',
																	labelAlign : 'left',
																	fieldLabel : '黄单号<span style="color:red">*</span>',
																	name : 'flightMainNo',
																	id:'flightMainNo',
																	maxLength:200,
																	allowBlank : false,
																	blankText : "黄单号不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,
																   	listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
																				Ext.getCmp('startcity').focus();
																				Ext.getCmp('startcity').selectText();
														                 	}
														 				}
														 			}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '件数<span style="color:red">*</span>',
																	name : 'flightPiece',
																	id:'flightPiece',
																	allowNegative:false,
																	maxLength:12,
																	decimalPrecision:0,
																	allowBlank : false,
																	blankText : "件数不能为空！",
																	anchor : '95%',
																	enableKeyEvents:true,
																   	listeners : {
																		keyup:function(numberfield, e){
														                	if(e.getKey() == 13 ){
																				Ext.getCmp('flightWeight').focus();
																				Ext.getCmp('flightWeight').selectText();
														                 	}
														 				}
														 			}
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '板费',
																	readOnly:true,
																	value:0,
																	name : 'boardAmount',
																	id:'boardAmount',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "板费不能为空！",
																	anchor : '95%'
																},{
																	xtype : 'numberfield',
																	labelAlign : 'left',
																	fieldLabel : '金额',
																	name : 'flightAmount',
																	id:'flightAmount',
																	maxLength:12,
																	allowBlank : false,
																	blankText : "金额不能为空！",
																	anchor : '95%'
																},{
																	xtype:'label',
																	id:'showMsg2',
																	width:380,
																	enableKeyEvents:true,
																	listeners : {
																		render:function(v){
																			Ext.getCmp('showMsg2').getEl().update('<span style="color:red">金额：单价x计费重量，黄单总金额：金额+板费</span>');
																		}
																	}
														        }]
													}]
													
										},{
											labelAlign : 'top',
											xtype : 'textarea',
											name : 'remark',
											id:'remark',
											maxLength:250,
											fieldLabel : '备注',
											height : 50,
											width:'97%',
											enableKeyEvents:true,
										   	listeners : {
												keyup:function(numberfield, e){
								                	if(e.getKey() == 13 ){
														Ext.getCmp('savebtn').focus();
								                 	}
								 				}
								 			}
										}]
										});
			
		carTitle='提货成本录入';
		if(record!=null){
			carTitle='提货成本修改';
			customerStore.load();
			form.load({
				url : sysPath+ "/"+ralaListUrl,
				params:{filter_EQL_id: record.data.id, privilege:privilege,limit : pageSize},
				success : function(form, action) {
					var v=Ext.getCmp('customer');
					if(v.getValue()!=''){
					 	Ext.getCmp('showMsg2').getEl().update('<span style="color:red"></span>');
					 	Ext.Ajax.request({
								url:sysPath+"/fi/fiDeliveryPriceAction!list.action",
							params:{
								filter_EQL_customerId:v.getValue(),
								filter_EQS_goodsType:record.data.goodsType,
								filter_EQL_departId:bussDepart,
								filter_EQL_isdelete:1
							},
							success : function(response) { // 回调函数有1个参数
								if(Ext.decode(response.responseText).result.length==1){
									var str=v.getRawValue()+' '+record.data.goodsType+' ';
									if(Ext.decode(response.responseText).result[0].lowest==0){
										str+='无最低一票';
									}else{
										str+='最低一票：'+Ext.decode(response.responseText).result[0].lowest;
										flag2=Ext.decode(response.responseText).result[0].lowest;
									}
									if(Ext.decode(response.responseText).result[0].isBoardStatus==0){
										str+='，无板费';
										Ext.getCmp('boardAmount').setValue(0);
									}else{
										str+='，需要交纳板费';
										flag=true;
										board1=Ext.decode(response.responseText).result[0].board1;
										board2=Ext.decode(response.responseText).result[0].board2;
										board3=Ext.decode(response.responseText).result[0].board3;
										board4=Ext.decode(response.responseText).result[0].board4;
										board5=Ext.decode(response.responseText).result[0].board5;
										board6=Ext.decode(response.responseText).result[0].board6;
									}
									Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+str+'</span>');
									//Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
									Ext.getCmp('price').setValue(Ext.decode(response.responseText).result[0].rates);
								}else{
									Ext.getCmp('showMsg2').getEl().update('<span style="color:red">'+v.getRawValue()+'的协议价不存在，请在提货协议价管理模块新增</span>');
									//v.selectText();
									v.setValue('');
									v.focus();
								}
							},
							failure : function(response) {
								Ext.getCmp('showMsg2').getEl().update('<span style="color:red">提货公司协议价查询失败</span>');
								v.focus();
							}
						});	
					 }	
				},
				failure : function(form, action) {
					Ext.Msg.alert(alertTitle,"加载失败，请重新加开口窗口");		
				}
			})
		}
		
		var win = new Ext.Window({
			title : carTitle,
			width : 600,
			closeAction : 'hide',
			plain : true,
		    resizable : false,	
			modal : true,
			items : form,
			buttonAlign : "center",	buttons : [{
				text : "保存",
				id:'savebtn',
				iconCls : 'groupSave',
				handler : function() {
					if (form.getForm().isValid()) {
						this.disabled = true;//只能点击一次
						form.getForm().submit({
							url : sysPath+ '/fi/fiDeliverycostAction!save.action',
							params:{
								privilege:privilege,
								customerName:Ext.getCmp('customer').getRawValue()
							},
							waitMsg : '正在保存数据...',
							success : function(form, action) {
										win.hide(), 
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												dataStore.reload();
										});
							},
							failure : function(form, action) {
								if (action.faliureType == Ext.form.Action.SERVER_INVALID) {
									Ext.Msg.alert(alertTitle, "服务器连接异常，请联系管理员！");
								} else {
										Ext.Msg.alert(alertTitle,action.result.msg, function() {
												});
	
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
					form.destroy();
				});
		
		win.show();
		
 }
 	/**
	 * 会计审核
	 */
	function fiAudit(records){
		Ext.Msg.confirm(alertTitle,'您确定要审核这<span style="color:red">'+records.length+'</span>条件数据吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids="";
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}else{
						ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}
				}	
				ids+="&privilege="+privilege; 	
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiDeliverycostAction!fiAudit.action",
					method : 'post',
					params:ids ,
					waitMsg : '正在保存数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg,function(){
								dataStore.reload({
									params : {
										start : 0,
										limit : pageSize,
										privilege:privilege
									}
								});
							});
								
							
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
	
	function qxAduit(records){
		Ext.Msg.confirm(alertTitle,'您确定要取消这条数据的手工匹配吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids="";
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}else{
						ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}
				}
				ids+="&privilege="+privilege;
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiDeliverycostAction!qxAduit.action",
					method : 'post',
					params : ids,
					waitMsg : '正在保存数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg,function(){
								dataStore.reload();
							});
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
	
	function qxFiAudit(records){
		Ext.Msg.confirm(alertTitle,'你确定要撤销这'+records.length+'条数据的会计审核吗？',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				var ids="";
				for(var i=0;i<records.length;i++){
 					if(i==0){
						ids += "aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}else{
						ids += "&aa["+i+"].id="+records[i].get("id")+"&aa["+i+"].ts="+records[i].get("ts");
					}
				}	
				ids+="&privilege="+privilege;
				
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiDeliverycostAction!qxFiAudit.action",
					method : 'post',
					params : ids,
					waitMsg : '正在处理数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据撤销成功",function(){
								dataStore.reload();
							});
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
	
	function deleteCost(record){
		Ext.Msg.confirm(alertTitle,'删除数据后将不能恢复，您确定要删除这条数据吗?',function(btnYes) {
			if (btnYes == 'yes' || btnYes == 'ok'|| btnYes == true) {
				form1.getForm().doAction('submit', {
					url:sysPath+"/fi/fiDeliverycostAction!deleteData.action",
					method : 'post',
					params : {
						id : record.get('id'),
						ts:record.get('ts'),
						privilege:privilege
   					},
					waitMsg : '正在保存数据...',
					success : function(form1, action) {
							Ext.Msg.alert(alertTitle,"数据删除成功",function(){
								dataStore.reload();
							});
					},
					failure : function(form1, action) {
							Ext.Msg.alert(alertTitle,action.result.msg);
					}
				});
			}
		});
	}
	
	function showExcel(){
  		var node=new Ext.tree.TreeNode({id:'keel_'+(Math.random()*10+1),leaf :false,text:"账单对账"});
      	node.attributes={href1:'fi/fiDeliverycostAction!sexcel.action'};
        parent.toAddTabPage(node,true);
	}
	
	/**
	 * 导出按钮事件
	 */
	function exportinfo() {
		parent.exportExl(recordGrid);
	}
	
	/**
	 * 打印按钮事件
	 */
	function printinfo() {
		Ext.Msg.alert("提示", "正在开发中...");
	}


});



    	 


